package com.catherine.dictionary;

import java.util.List;

import com.catherine.dictionary.unit_test.HashingDaoImpl;
import com.catherine.dictionary.unit_test.Student;

/**
 * 在此探讨的散列都是通过散列算法的设计，将元素从大的集合映射到小的集合的情况。<br>
 * 反之，从小的集合到大的集合的情况，则是另一种用途——密码学。<br>
 * <br>
 * <br>
 * 设计散列（hash）函数时应注意下列原则：<br>
 * <br>
 * 1. 确定（determinism）：同一个关键码总是映射到同一个地址。<br>
 * 2. 快速（efficiency）：预期O（1）。<br>
 * 3. 射满（surjection）：尽可能射满整个映射空间。<br>
 * 4. 均匀（uniformity）：关键码映射到散列表各位置的概率应尽量接近。<br>
 * <br>
 * 散列函数可以有各式不同的设计，并非固定的模式。
 * 
 * @author Catherine
 *
 */
public class HashFunctions {
	private final boolean SHOW_DEBUG_LOG = true;
	private HashingDaoImpl rawDaoImpl;

	/**
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
	public HashFunctions(int capacity, float loadFactor, int from, int to) {
		rawDaoImpl = new HashingDaoImpl("students_raw");
		rawDaoImpl.createRandomTable(capacity, loadFactor, from, to);
	}

	/**
	 * 1. 除余法<br>
	 * hash(key) = key % m<br>
	 * <br>
	 * 为了要让整个散列表分布的更均匀，尽可能的不要冲突，计算M时应选择gcd(key, m) = 1， 因为key会不断的变动，所以m应该找一个素数。
	 * <br>
	 * 缺点：<br>
	 * 不动点——hash(0) = 0<br>
	 * 零阶均匀——相邻关键码的散列地址也会相邻<br>
	 * 
	 * @param m
	 *            应为一素数
	 */
	public void remainder(int m) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_" + m);
		for (Student student : rawTableList) {
			hashingDaoImpl.insert(student.seat_id % m, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("mod " + m);
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 2. MAD<br>
	 * hash(key) = (key * step + offset) % m<br>
	 * <br>
	 * 改良除余法，经过MAD处理后不再出现不动点与零阶均匀（MAD是一阶均匀）<br>
	 * 视情况而定，不是非得要用高阶均匀。
	 * 
	 * @param step
	 * @param offset
	 * @param m
	 *            应为一素数
	 */
	public void mad(int step, int offset, int m) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl(String.format("students_hashing_%d_%d_%d", step, offset, m));
		for (Student student : rawTableList) {
			hashingDaoImpl.insert((student.seat_id * step + offset) % m, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println(String.format("(%d * key + %d) %% %d", step, offset, m));
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 3. 数字分析法<br>
	 * 必须先定义怎么样的分析法，比如这边采用的是取关键码的奇数位作为散列地址。
	 */
	public void selectingDigits() {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_odd");
		for (Student student : rawTableList) {
			if (student.seat_id % 2 == 1)
				hashingDaoImpl.insert(student.seat_id, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("odd number");
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
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

		int lostKeys = rawStudentList.size() - newStudentList.size();

		int collisions = 0;
		for (Student student : newStudentList) {
			if (student.collisions > 0)
				collisions++;
		}
		System.out.println(String.format("lost keys:%d (%.2f%%), collisions:%d (%.2f%%)", lostKeys,
				lostKeys * 100.0f / rawStudentList.size(), collisions, collisions * 100.0f / newStudentList.size()));

		float su0 = rawStudentList.size() * 100.0f / rawTableList.size();
		float su1 = newStudentList.size() * 100.0f / newTableList.size();
		System.out.println(String.format("space usage:%.2f%%, improved:%.2f%%", su1, su1 - su0));

	}
}
