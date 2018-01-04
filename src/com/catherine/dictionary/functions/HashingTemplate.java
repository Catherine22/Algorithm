package com.catherine.dictionary.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.data.Student;

public abstract class HashingTemplate {
	protected String table;
	protected CollisionMode collisionMode;

	public abstract List<Student> hash(List<Student> rawStudentList);

	public abstract List<Student> getStudent();

	public abstract List<Student> getTableList();

	/**
	 * 查找（指定座位的学生），假如一个座位对应多个学生，则返回首先找到的值。
	 * 
	 * @param SEAT_ID
	 * @return students
	 */
	public Student get(int SEAT_ID) {
		List<Student> students = getAll(SEAT_ID);
		return students.get(0);
	}

	/**
	 * 查找（指定座位的学生），假如一个座位对应多个学生，则全部返回。
	 * 
	 * @param SEAT_ID
	 * @return students
	 */
	protected List<Student> getAll(int SEAT_ID) {
		List<Student> students = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", table));
			Statement stmt = c.createStatement();

			// 检查该座位是否已存在学生
			ResultSet rs0 = stmt.executeQuery(String.format("SELECT * FROM STUDENTS WHERE seat_id=%d", SEAT_ID));
			while (rs0.next()) {
				// TODO bugs
				System.out.println("add:" + new Student(rs0.getInt("id"), rs0.getInt("seat_id"),
						rs0.getString("student_id"), rs0.getString("student_name"), rs0.getInt("collisions")));
				students.add(new Student(rs0.getInt("id"), rs0.getInt("seat_id"), rs0.getString("student_id"),
						rs0.getString("student_name"), rs0.getInt("collisions")));
			}
			rs0.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}

	public abstract void analyse(List<Student> rawTableList, List<Student> rawStudentList, List<Student> newTableList,
			List<Student> newStudentList);

	public void analyse(String TABLE, List<Student> rawTableList, List<Student> rawStudentList,
			List<Student> newTableList, List<Student> newStudentList) {
		System.out.println(String.format("original size:%d, new size:%d (%.2f%% ↓)", rawTableList.size(),
				newTableList.size(), (rawTableList.size() - newTableList.size()) * 100.0f / rawTableList.size()));

		int lostKeys = rawStudentList.size() - newStudentList.size();

		int collisions = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", TABLE));
			Statement stmt = c.createStatement();

			ResultSet rs0 = stmt.executeQuery("SELECT SUM(collisions) FROM STUDENTS");
			while (rs0.next()) {
				collisions = rs0.getInt(1);
			}
			rs0.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(String.format("lost keys:%d (%.2f%% ↑), collisions:%d (%.2f%% ↑)", lostKeys,
				lostKeys * 100.0f / rawStudentList.size(), collisions, collisions * 100.0f / rawStudentList.size()));

		float su0 = rawStudentList.size() * 100.0f / rawTableList.size();
		float su1 = newStudentList.size() * 100.0f / newTableList.size();

		float diff = 0;
		String symbol = "";
		if (su1 > su0) {
			symbol = "↑";
			diff = su1 - su0;
		} else {
			symbol = "↓";
			diff = su0 - su1;
		}
		System.out.println(String.format("space usage:%.2f%% (%.2f%% %s)", su1, diff, symbol));
	}

	/**
	 * 将num分割成n段，比如传入num = 23456，n=3，回传stack{56,34,2}，<br>
	 * 若num长度小于n，直接返回stack{num}<br>
	 * 
	 * @param num
	 * @param n
	 * @return
	 */
	protected Stack<Integer> separate(int num, int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n <= 0");

		Stack<Integer> digits = new Stack<>();
		int numDigits = 0;

		// 先计算num一共有几个位数
		int temp = num;
		if (temp > 0) {
			while (temp > 0) {
				numDigits++;
				temp /= 10;
			}
		} else {
			while (temp < 0) {
				numDigits++;
				temp /= 10;
			}
		}

		// num长度小于n，无法分割，则视为切成一份
		if (n > numDigits)
			n = 1;

		int k = (int) Math.pow(10, (int) Math.round(numDigits * 1.0f / n));
		if (num > 0) {
			while (num > 0) {
				digits.push(num % k);
				num /= k;
			}
		} else {
			while (num < 0) {
				digits.push(num % -k);
				num /= k;
			}
		}
		return digits;
	}

}
