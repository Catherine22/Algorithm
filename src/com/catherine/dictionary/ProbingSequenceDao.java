package com.catherine.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，每个散列地址（之后简称为桶）
 * 可存放一个LinkedList， 让重复的关键码存在同一个桶中。<br>
 * bucket(key1-key2-key3)<br>
 * |<br>
 * bucket(key4-key5)<br>
 * |<br>
 * bucket(key6)<br>
 * 
 * 
 * 如此会有一个重大缺陷，CPU缓存依赖连续内存地址，一旦已链构成桶，整个散列地址并非连续地址，直接影响效能。<br>
 * 因而对散列进一步改进，桶不以LinkedList的形式存在，而是加上固定数量的备用桶，让整个散列列表为桶串联起来的一长串连续地址。<br>
 * 
 * bucket(key1)<br>
 * |<br>
 * spareBucket(key2)<br>
 * |<br>
 * spareBucket(key3)<br>
 * |<br>
 * bucket(key4)<br>
 * |<br>
 * spareBucket(key5)<br>
 * |<br>
 * spareBucket()<br>
 * |<br>
 * bucket(key6)<br>
 * |<br>
 * spareBucket()<br>
 * |<br>
 * spareBucket()<br>
 * 
 * @author Catherine
 *
 */
public class ProbingSequenceDao extends HashingDaoTemplate {

	private static class InstanceHolder {
		private static ProbingSequenceDao instance = new ProbingSequenceDao();
	}

	public static ProbingSequenceDao getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	protected ProbingSequenceDao() {

	}

	@Override
	protected void createRandomTable(String TABLE, int capacity, float loadFactor, int from, int to, boolean isUnique) {
		synchronized (getInstance()) {
			List<Integer> randomList = getRandomIntList(capacity, loadFactor, from, to, isUnique);

			try {
				Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
				Statement stmt = c.createStatement();

				StringBuilder insertion = new StringBuilder();
				for (int i = 0; i < randomList.size(); i++) {
					Integer data = randomList.get(i);
					if (data != null)
						insertion.append(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								i, data + "", getInstance().md5(data + "") + "", 0));
					else
						insertion.append(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								i, "", "", 0));
				}
				stmt.executeUpdate(insertion.toString());
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}

	@Override
	protected void insert(String TABLE, int SEAT_ID, String STUDENT_ID) {
		synchronized (getInstance()) {
			try {
				Class.forName("org.sqlite.JDBC");
				Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
				Statement stmt = c.createStatement();

				// 检查该座位是否已存在学生
				ResultSet rs0 = stmt
						.executeQuery(String.format("SELECT EXISTS(SELECT * From STUDENTS WHERE seat_id=%d)", SEAT_ID));

				boolean hasCollision = false;
				while (rs0.next()) {
					hasCollision = rs0.getInt(1) == 1;
				}
				rs0.close();

				int collisions = 0;
				if (hasCollision) {
					// 座位上有学生，collision+1
					ResultSet rs1 = stmt
							.executeQuery(String.format("SELECT collisions FROM STUDENTS WHERE seat_id=%d", SEAT_ID));
					collisions = rs1.getInt(1) + 1;
					rs1.close();

					// 座位给新学生（覆盖旧数据，等同于直接遗失一个关键码）
					stmt.executeUpdate(String.format(
							"UPDATE STUDENTS SET seat_id=%d, student_id='%s', student_name='%s', collisions=%d WHERE seat_id=%d",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + "") + "", collisions, SEAT_ID));
				} else {
					stmt.executeUpdate(String.format(
							"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d)",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + "") + "", collisions));
				}
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}

	/**
	 * 建立数据库，为了简化逻辑，seat_id为座位id，student_id作为学生ID（唯一识别码），学生名就用随机数(student_id)
	 * 的MD5。<br>
	 * 每个id都有对应的seat_id，但是不一定每个座位都有学生。<br>
	 * 每个student_id都对应一个student_name。<br>
	 * collisions代表进行hash时该栏位发生多少次碰撞，同一个座位被重复塞入学生就+1，初始值为0。<br>
	 * 
	 */
	protected static void initialize(String TABLE) {
		synchronized (getInstance()) {
			try {
				Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
				Statement stmt = c.createStatement();

				// if (SHOW_DEBUG_LOG)
				// System.out.println("Opened database successfully");

				String droping = "DROP TABLE IF EXISTS STUDENTS";
				stmt.executeUpdate(droping);

				String creation = "CREATE TABLE STUDENTS (" + "id 				INTEGER 	PRIMARY KEY AUTOINCREMENT, "
						+ "seat_id   		INT  		NOT NULL, " + "student_id  		TEXT  		NOT NULL, "
						+ "student_name		TEXT  		NOT NULL, " + "collisions 		INT  		NOT NULL)";
				stmt.executeUpdate(creation);
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}

			// if (SHOW_DEBUG_LOG)
			// System.out.println("Table created successfully");
		}
	}
}
