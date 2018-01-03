package com.catherine.dictionary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.catherine.dictionary.data.Student;

public abstract class HashingDaoTemplate {
	protected final static boolean SHOW_DEBUG_LOG = true;
	protected static HashingDaoTemplate INSTANCE;

	/**
	 * 生成随机不重复表，数据库中含有学生信息表，包含五个栏位：索引、座位编号、学号、姓名、碰撞次数。每个学号都有对应的姓名，但是不是每个座位都有学生。
	 * 碰撞次数代表进行hash时该栏位发生多少次碰撞，同一个座位被重复塞入学生就+1，初始值为0。
	 * 
	 * @param capacity
	 *            容量，代表List的长度
	 * @param loadFactor
	 *            代表List内有百分之多少的栏位要被赋值，比如设置0.75，容量为100，代表生成75个随机数，留下25个空栏位。
	 * @param from
	 *            随机数数值范围（含）
	 * @param to
	 *            随机数数值范围（不含）
	 * @param isUnique
	 *            链值是否不重复（在此是指学号要不要重复）
	 */
	protected abstract void createRandomTable(String TABLE, int capacity, float loadFactor, int from, int to,
			boolean isUnique);

	/**
	 * 插入新的栏位（学生）
	 * 
	 * @param TABLE
	 * @param SEAT_ID
	 * @param STUDENT_ID
	 */
	protected abstract void insert(String TABLE, int SEAT_ID, String STUDENT_ID);

	

	/**
	 * 建立数据库，为了简化逻辑，seat_id为座位id，student_id作为学生ID（唯一识别码），学生名就用随机数(student_id)
	 * 的MD5。<br>
	 * 每个id都有对应的seat_id，但是不一定每个座位都有学生。<br>
	 * 每个student_id都对应一个student_name。<br>
	 * collisions代表进行hash时该栏位发生多少次碰撞，同一个座位被重复塞入学生就+1，初始值为0。<br>
	 * 
	 */
	protected void initialize(String TABLE) {
		synchronized (INSTANCE) {
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

	/**
	 * 
	 * 生成随机链表
	 * 
	 * @param capacity
	 *            容量，代表List的长度
	 * @param loadFactor
	 *            代表List内有百分之多少的栏位要被赋值，比如设置0.75，容量为100，代表生成75个随机数，留下25个空栏位。
	 * @param from
	 *            随机数数值范围（含）
	 * @param to
	 *            随机数数值范围（不含）
	 * @param isUnique
	 *            链值是否不重复
	 * @return
	 */
	protected List<Integer> getRandomIntList(int capacity, float loadFactor, int from, int to, boolean isUnique) {
		if (capacity < 0)
			throw new IllegalArgumentException("capacity<0");
		if (from >= to)
			throw new IllegalArgumentException("from >= to");
		if (loadFactor > 100 || loadFactor < 0)
			throw new IllegalArgumentException("loadFactor>100 || loadFactor<0");

		int entitySize = (int) (capacity * loadFactor);
		int emptySpace = capacity - entitySize;

		if (isUnique && (to - from) < entitySize) {
			throw new IllegalArgumentException(
					"Your range of keys should be larger so that every element in this list would be unique.");
		}

		if (SHOW_DEBUG_LOG)
			System.out.print("rawArray from " + from + " to " + to);

		List<Integer> rawList = new LinkedList<>();
		int rawSize = to - from;
		for (int i = 0; i < rawSize; i++) {
			rawList.add(from++);
		}

		List<Integer> newList = new ArrayList<>();
		int header = 0;
		while (newList.size() < entitySize) {
			header = newList.size();
			Collections.shuffle(rawList);
			newList.addAll(header, rawList);
		}
		newList = newList.subList(0, entitySize);

		for (int i = 0; i < emptySpace; i++) {
			newList.add(null);
		}
		rawList.clear();
		Collections.shuffle(newList);

		if (SHOW_DEBUG_LOG)
			System.out.println(", size:" + newList.size());
		return newList;
	}

	/**
	 * 取得全部学生列表
	 * 
	 * @return
	 */
	protected List<Student> getStudent(String TABLE) {
		synchronized (INSTANCE) {
			List<Student> students = new ArrayList<>();
			try {
				Class.forName("org.sqlite.JDBC");
				Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
				Statement stmt = c.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS WHERE student_id != ''");
				while (rs.next()) {
					students.add(new Student(rs.getInt("id"), rs.getInt("seat_id"), rs.getString("student_id"),
							rs.getString("student_name"), rs.getInt("collisions")));
				}
				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			return students;
		}
	}

	/**
	 * 取得全部列表（包含没有学生的栏位）
	 * 
	 * @return
	 */
	protected List<Student> getTableList(String TABLE) {
		synchronized (INSTANCE) {
			List<Student> students = new ArrayList<>();
			try {
				Class.forName("org.sqlite.JDBC");
				Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
				Statement stmt = c.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS");
				while (rs.next()) {
					students.add(new Student(rs.getInt("id"), rs.getInt("seat_id"), rs.getString("student_id"),
							rs.getString("student_name"), rs.getInt("collisions")));
				}
				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				if (SHOW_DEBUG_LOG)
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			return students;
		}
	}

	protected String md5(String rawData) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			byte[] bytes = messageDigest.digest(rawData.getBytes());

			// 将byte数组转换成十六进制的字符串
			StringBuffer sb = new StringBuffer();
			// 把每一个byte做一个与运算 0xff
			for (byte b : bytes) {
				// 与运算
				int num = b & 0xff;// 加盐
				String str = Integer.toHexString(num);
				if (str.length() == 1) {
					// 长度为1时前面补0
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
