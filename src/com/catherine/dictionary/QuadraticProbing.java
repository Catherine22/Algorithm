package com.catherine.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，有两种解决方式，线性试探和平方试探。
 * 
 * 平方试探是以平方数为单位，寻找下一个桶单元。<br>
 * 平方试探的函数为：[hash(key) + n^2] % M<br>
 * 定理：当M为素数时，x^2%M的结果恰好有M/2种，恰由前M/2项取遍。<br>
 * <br>
 * <br>
 * 假设hash(key)=key%7，目前有0～6的地址，M=7<br>
 * key=10、13、11、3、8、5、14<br>
 * <br>
 * 10:放3<br>
 * 13:放6<br>
 * 11:放4<br>
 * 3:3冲突，改成(3+1^2)%7，放4，继续冲突，改(3+2^2)%7，放0<br>
 * 8:放1<br>
 * 5:放5<br>
 * 14:0冲突，改成(0+1^2)%7，放1，继续冲突，改(0+2^2)%7，放4，继续冲突，改(0+3^2)%7，放2<br>
 * 整个表为[3, 8, 14, 10, 11, 5, 13]<br>
 * 
 * @author Catherine
 * @see LinearProbing 线性试探
 */
public class QuadraticProbing extends Probing {
	private int spareBuckets;
	private int mod;

	private static class InstanceHolder {
		private static QuadraticProbing instance = new QuadraticProbing();
	}

	public static QuadraticProbing getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	/**
	 * 
	 * @param spareBuckets
	 *            备用桶的数量
	 * @param mod
	 *            应为一素数，且mod=4k+3最佳，比如3、7、11、19......
	 */
	protected QuadraticProbing(int spareBuckets, int mod) {
		this.spareBuckets = spareBuckets;
		this.mod = mod;
		getInstance();
	}

	private QuadraticProbing() {
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

				// 检查该座位及其备用桶是否已存在学生
				while (limitation > 0) {
					rs0 = stmt.executeQuery(
							String.format("SELECT * From STUDENTS WHERE seat_id=%d AND student_id=''", currentSeatID));

					if (rs0.next()) {
						hasRoom = true;
						spareID = rs0.getInt("id");
						limitation = 0;
					}
					currentSeatID = (SEAT_ID + n * n) % mod;
//					System.out.println(
//							String.format("(%d + %d) %% %d = %d", SEAT_ID, n * n, mod, currentSeatID));
					n++;
					limitation--;
				}

				// 表示该座位已经有学生
				boolean hasCollision = (headID != spareID);

//				System.out.println(String.format("seat_id:%d,STUDENT_ID:%s,hasCollision:%b,hasRoom:%b -> id:%s",
//						SEAT_ID, STUDENT_ID, hasCollision, hasRoom, spareID));

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