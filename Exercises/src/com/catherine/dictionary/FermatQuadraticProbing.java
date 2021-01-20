package com.catherine.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，有两种解决方式，线性试探和平方试探。<br>
 * <br>
 * 双向平方试探<br>
 * 双向平方试探的函数为：[hash(key) + 1^2] % M, [hash(key) - 1^2] % M, [hash(key) + 2^2] %
 * M, ... [hash(key) + k^2] % M, [hash(key) - k^2] % M<br>
 * 根据Fermat的两平方数和理论，M应为一素数且=4k+3最佳，比如3、7、11、19......，如此可保证前M项找到的数都不同。
 * 
 * @author Catherine
 * @see QuadraticProbing 平方试探
 */
public class FermatQuadraticProbing extends Probing {
	private int spareBuckets;
	private int mod;

	private static class InstanceHolder {
		private static FermatQuadraticProbing instance = new FermatQuadraticProbing();
	}

	public static FermatQuadraticProbing getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	/**
	 * 
	 * @param spareBuckets
	 *            备用桶的数量
	 * @param mod
	 *            应为一素数，根据Fermat的两平方数和理论，M=4k+3最佳，比如3、7、11、19......，如此可保证前M项找到的数都不同。
	 */
	protected FermatQuadraticProbing(int spareBuckets, int mod) {
		this.spareBuckets = spareBuckets;
		this.mod = mod;
		getInstance();
	}

	private FermatQuadraticProbing() {
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
				if (rs0.next())
					hasSeat = true;

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
				// 检查该座位是否已有学生
				int headID = 0;
				rs0 = stmt.executeQuery(String.format("SELECT * From STUDENTS WHERE seat_id=%d", SEAT_ID));
				if (rs0.next()) {
					headID = rs0.getInt("id");
				}

				// 还有没有空桶
				boolean hasRoom = false;
				// 空桶的id
				int spareID = 0;
				// 最多查找mod/2次，因为n^2%M的结果最多为M/2次
				int limitation = Math.round(mod * 1.0f / 2);
				// 当前指针
				int currentSeatID = SEAT_ID;
				int n = 1;
				// 向前还是向后找
				int direction = 1;

				// 检查该座位及其备用桶是否已存在学生
				while (limitation > 0) {
					rs0 = stmt.executeQuery(
							String.format("SELECT * From STUDENTS WHERE seat_id=%d AND student_id=''", currentSeatID));

					if (rs0.next()) {
						hasRoom = true;
						spareID = rs0.getInt("id");
						limitation = 0;
					}
					currentSeatID = (SEAT_ID + direction * n * n) % mod;
//					System.out.println(
//							String.format("(%d + %d) %% %d = %d", SEAT_ID, direction * n * n, mod, currentSeatID));
					if (direction == -1) {
						n++;
						limitation--;
						direction = 1;
					} else
						direction = -1;
				}

				// 表示该座位已经有学生
				boolean hasCollision = (headID != spareID);

				// System.out.println(String.format("seat_id:%d,STUDENT_ID:%s,hasCollision:%b,hasRoom:%b
				// -> id:%s",
				// SEAT_ID, STUDENT_ID, hasCollision, hasRoom, spareID));

				// 一旦当前条目已存在数据，就往后寻找备用桶，直到整条数组的备用桶全满，则在原条目的collisions+1
				int collisions = 0;
				if (hasCollision) {
					// 表示备用桶全满
					if (!hasRoom) {
						rs0 = stmt.executeQuery(String.format("SELECT * From STUDENTS WHERE id=%d", headID));
						if (rs0.next()) {
							collisions = rs0.getInt("collisions") + 1;
							spareID = rs0.getInt("id");
						}

						stmt.executeUpdate(String.format(
								"UPDATE STUDENTS SET seat_id=%d, student_id='%s', student_name='%s', collisions=%d WHERE id=%d",
								SEAT_ID, STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions, spareID));
					} else {
						stmt.executeUpdate(String.format(
								"UPDATE STUDENTS SET  student_id='%s', student_name='%s', collisions=%d WHERE id=%d",
								STUDENT_ID + "", getInstance().md5(STUDENT_ID + ""), collisions, spareID));
					}
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