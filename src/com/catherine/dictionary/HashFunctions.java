package com.catherine.dictionary;

import java.util.List;

import com.catherine.dictionary.unit_test.HashingDaoImpl;
import com.catherine.dictionary.unit_test.RawDaoImpl;
import com.catherine.dictionary.unit_test.Student;

/**
 * 设计散列（hash）函数时应注意下列原则：<br>
 * <br>
 * 1. 确定（determinism）：同一个关键码总是映射到同一个地址。<br>
 * 2. 快速（efficiency）：预期O（1）。<br>
 * 3. 射满（surjection）：尽可能射满整个映射空间。<br>
 * 4. 均匀（uniformity）：关键码映射到散列表各位置的概率应尽量接近。<br>
 * 
 * @author Catherine
 *
 */
public class HashFunctions {
	private final boolean SHOW_DEBUG_LOG = true;

	/**
	 * 除余法<br>
	 * hash(key) = key % m<br>
	 * <br>
	 * 为了要让整个散列表分布的更均匀，尽可能的不要冲突，计算M时应选择gcd(key, m) = 1， 因为key会不断的变动，所以m应该找一个素数。
	 * 
	 * @param key
	 */
	public void remainder(int m) {
		RawDaoImpl.getInstance().createRandomTable(20, 0.75f, 30, 36);
		List<Student> rawTableList = RawDaoImpl.getInstance().getTableList();
		List<Student> rawStudentList = RawDaoImpl.getInstance().getStudent();

		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawStudentList) {
			HashingDaoImpl.getInstance().insert(student.seat_id % m, student.student_id);
		}

		List<Student> newTableList = HashingDaoImpl.getInstance().getTableList();
		List<Student> newStudentList = HashingDaoImpl.getInstance().getStudent();

		if (SHOW_DEBUG_LOG)
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
	}

	/**
	 * 找到最小素数，若没有返回-1
	 * 
	 * @param from
	 *            （含）
	 * @param to
	 *            （不含）
	 * @return
	 */
	public int getPrimeNumber(int from, int to) {
		if (to <= from)
			throw new IllegalArgumentException("to <= from");
		if (to < 2)
			return -1;
		if (from == 2)
			return 2;

		int num = (from < 2) ? 2 : from;
		int half;
		boolean stop = false;
		boolean found = false;

		while (!stop && num < to) {
			found = true;
			half = num / 2 + 1;
			for (int i = 2; i < half; i++) {
				if (num % i == 0) {
					found = false;
					break;
				}
			}
			if (found)
				break;
			num++;
		}

		return (found) ? num : -1;
	}

	public void analyze(List<Student> rawTableList, List<Student> rawStudentList, List<Student> newTableList,
			List<Student> newStudentList) {
		System.out.println(String.format("original size:%d, new size:%d", rawTableList.size(), newTableList.size()));
		
		int collisions = 0;
		for (Student student : newStudentList) {
			if (student.collisions > 0)
				collisions++;
		}
		System.out.println(String.format("lost keys:%d, collisions:%.2f%%", collisions,
				collisions * 1.0f / newStudentList.size()));

		float su0 = rawStudentList.size() * 1.0f / rawTableList.size();
		float su1 = newStudentList.size() * 1.0f / newTableList.size();
		System.out.println(String.format("space usage:%.2f%%, improve:%.2f%%", su1, su1 - su0));

	}
}
