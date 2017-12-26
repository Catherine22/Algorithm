package com.catherine.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestUtils {
	private static final boolean SHOW_DEBUG_LOG = true;
	private final String DB_NAME = "md5_numbers";

	/**
	 * 建立数据库，为了简化逻辑，SERIAL_NUM作为学号（唯一识别码），学生名就用随机数(SERIAL_NUM)的MD5
	 * 
	 * @param capacity
	 *            容量，代表一张表内有多少栏位
	 * @param loadFactor
	 *            代表一张表内有百分之多少的栏位要被赋值，比如设置0.75，容量为100，代表生成75个学生，留下25个空栏位。
	 * @param from
	 *            学号范围（SERIAL_NUM）（含）
	 * @param to
	 *            学号范围（SERIAL_NUM）（不含）
	 */
	private TestUtils(int capacity, float loadFactor, int from, int to) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", DB_NAME));

			if (SHOW_DEBUG_LOG)
				System.out.println("Opened database successfully");

			stmt = c.createStatement();

			String droping = "DROP TABLE IF EXISTS STUDENTS";
			stmt.executeUpdate(droping);

			String creation = "CREATE TABLE STUDENTS (ID INT PRIMARY KEY  NOT NULL, SERIAL_NUM  TEXT  NOT NULL, NAME  TEXT  NOT NULL)";
			stmt.executeUpdate(creation);
			stmt.close();
			c.close();
		} catch (Exception e) {
			if (SHOW_DEBUG_LOG)
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		if (SHOW_DEBUG_LOG)
			System.out.println("Table created successfully");
	}

	/**
	 * 
	 * 生成随机不重复数组
	 * 
	 * @param size
	 *            数组长度
	 * @param from
	 *            数值范围（含）
	 * @param to
	 *            数值范围（不含）
	 * @return
	 */
	public static int[] getRandomIntArray(int size, int from, int to) {

		return null;
	}

	/**
	 * 生成随机不重复链表
	 * 
	 * @param capacity
	 *            容量，代表List的长度
	 * @param loadFactor
	 *            代表List内有百分之多少的栏位要被赋值，比如设置0.75，容量为100，代表生成75个随机数，留下25个空栏位。
	 * @param from
	 *            随机数数值范围（含）
	 * @param to
	 *            随机数数值范围（不含）
	 */
	public static List<Integer> getRandomIntList(int capacity, float loadFactor, int from, int to) {
		if (capacity < 0)
			throw new IllegalArgumentException("capacity<0");
		if (from >= to)
			throw new IllegalArgumentException("from >= to");
		if (loadFactor > 100 || loadFactor < 0)
			throw new IllegalArgumentException("loadFactor>100 || loadFactor<0");

		List<Integer> rawList = new LinkedList<>();
		int size = to - from;

		for (int i = 0; i < size; i++) {
			rawList.add(from++);
		}

		if (SHOW_DEBUG_LOG)
			System.out.println("rawArray from " + rawList.get(0) + "~" + rawList.get(rawList.size() - 1));

		int emptySpace = (int) (capacity * (1 - loadFactor));
		for (int i = 0; i < emptySpace; i++) {
			rawList.add(null);
		}

		Collections.shuffle(rawList);

		if (SHOW_DEBUG_LOG)
			System.out.println(rawList);
		return rawList;
	}

	/**
	 * 生成随机不重复表
	 * 
	 * @param capacity
	 *            容量，代表List的长度
	 * @param loadFactor
	 *            代表List内有百分之多少的栏位要被赋值，比如设置0.75，容量为100，代表生成75个随机数，留下25个空栏位。
	 * @param from
	 *            随机数数值范围（含）
	 * @param to
	 *            随机数数值范围（不含）
	 */
	public static void createRandomTable(int capacity, float loadFactor, int from, int to) {
		TestUtils testUtils = new TestUtils(capacity, loadFactor, from, to);
		List<Integer> randomList = getRandomIntList(capacity, loadFactor, from, to);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", testUtils.DB_NAME));

			stmt = c.createStatement();

			StringBuilder insertion = new StringBuilder();
			for (int i = 0; i < randomList.size(); i++) {
				Integer data = randomList.get(i);
				if (data != null)
					insertion.append(String.format("INSERT INTO STUDENTS (ID,SERIAL_NUM,NAME) VALUES (%d, '%s', '%s');",
							i, data + "", testUtils.md5(data + "") + ""));
				else
					insertion.append(String.format("INSERT INTO STUDENTS (ID,SERIAL_NUM,NAME) VALUES (%d, '%s', '%s');",
							i, "", ""));
			}
			stmt.executeUpdate(insertion.toString());

			stmt.close();
			c.close();
		} catch (Exception e) {
			if (SHOW_DEBUG_LOG)
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private String md5(String rawData) {
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
