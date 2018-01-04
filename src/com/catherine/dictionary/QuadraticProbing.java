package com.catherine.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，有两种解决方式，线性试探和平方试探。
 * 平方试探是以平方数为单位，寻找下一个桶单元。<br>
 * 平方试探的函数为：[hash(key) + n^2] % M<br>
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

	private static class InstanceHolder {
		private static QuadraticProbing instance = new QuadraticProbing();
	}

	public static QuadraticProbing getInstance() {
		INSTANCE = InstanceHolder.instance;
		return InstanceHolder.instance;
	}

	protected QuadraticProbing() {
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