package com.catherine.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.catherine.dictionary.data.Student;

/**
 * 不同的关键码(key)经散列函数hash(key)计算后的散列地址(value)可能会重复，在此忽略冲突情况，直接覆盖，累计到collisions栏位记录
 * 
 * @author Catherine
 *
 */
class HashingDao extends HashingDaoTemplate {

	private static class InstanceHolder {
		private static HashingDao instance = new HashingDao();
	}

	public static HashingDao getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	protected HashingDao() {
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
								i, data + "", getInstance().md5(data + ""), 0));
					} else {
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
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions, SEAT_ID));

				} else {
					stmt.executeUpdate(String.format(
							"INSERT INTO STUDENTS (seat_id, student_id, student_name, collisions) VALUES (%d, '%s', '%s', %d)",
							SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions));
				}
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
}
