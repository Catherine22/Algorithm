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
 * 以spareBuckets==2为例：<br>
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
	private int spareBuckets;

	private static class InstanceHolder {
		private static ProbingSequenceDao instance = new ProbingSequenceDao();
	}

	public static ProbingSequenceDao getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	/**
	 * 
	 * @param spareBuckets
	 *            备用桶的数量
	 */
	protected ProbingSequenceDao(int spareBuckets) {
		this.spareBuckets = spareBuckets;
		getInstance();
	}

	private ProbingSequenceDao() {
		getInstance();
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
					if (data != null) {
						insertion.append(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								i, data + "", getInstance().md5(data + "") + "", 0));
					} else {
						insertion.append(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								i, "", "", 0));
					}
					// 每个桶后面都有spareBuckets个备用桶
					for (int j = 0; j < spareBuckets; j++) {
						insertion.append(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								i, "", "", 0));
					}
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

				// 首先检查是否已有座位，若没有则创建（同时创建备用桶）
				ResultSet rs0 = stmt.executeQuery(String.format("SELECT id From STUDENTS WHERE seat_id=%d", SEAT_ID));
				boolean hasSeat = false;
				while (rs0.next()) {
					hasSeat = true;
					break;
				}

				if (!hasSeat) {
					stmt.executeUpdate(String.format(
							"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d)",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), 0));

					// 每个桶后面都有spareBuckets个备用桶
					for (int j = 0; j < spareBuckets; j++) {
						stmt.executeUpdate(String.format(
								"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d);",
								SEAT_ID, "", "", 0));
					}
					return;
				}

				// 检查该座位及其备用桶是否已存在学生
				rs0 = stmt.executeQuery(
						String.format("SELECT * From STUDENTS WHERE seat_id=%d AND student_id=''", SEAT_ID));
				boolean hasCollision = true;
				int spareID = 0;

				boolean block = false;
				int emptyBuckets = 0;
				while (rs0.next()) {
					if (!block) {
						block = true;
						spareID = rs0.getInt("id");
					}
					emptyBuckets++;
				}
				hasCollision = (emptyBuckets != (spareBuckets + 1));

//				System.out.println(String.format("seat_id:%d, STUDENT_ID:%s, hasCollision:%b -> id:%s", SEAT_ID,
//						STUDENT_ID, hasCollision, spareID));

				// 一旦当前条目已存在数据，就往后寻找备用桶，若备用桶全满，则在原条目的collisions+1
				int collisions = 0;
				if (hasCollision) {
					// 表示备用桶全满
					if (emptyBuckets == 0) {
						rs0 = stmt.executeQuery(String.format("SELECT * From STUDENTS WHERE seat_id=%d", SEAT_ID));
						while (rs0.next()) {
							collisions = rs0.getInt("collisions") + 1;
							spareID = rs0.getInt("id");
							break;
						}
					}

					stmt.executeUpdate(String.format(
							"UPDATE STUDENTS SET seat_id=%d, student_id='%s', student_name='%s', collisions=%d WHERE id=%d",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions, spareID));
				} else {
					stmt.executeUpdate(String.format(
							"UPDATE STUDENTS SET seat_id=%d, student_id='%s', student_name='%s', collisions=%d WHERE seat_id=%d",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions, SEAT_ID));
				}

				rs0.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					e.printStackTrace();
			}
		}
	}
}
