package com.catherine.dictionary;

import com.catherine.dictionary.unit_test.RawDaoImpl;

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
		// 要做hash处理的是学生信息表的ID
		RawDaoImpl.getInstance().createRandomTable(20, 0.75f, 30, 36);
		System.out.println(RawDaoImpl.getInstance().getTableList());
		System.out.println(RawDaoImpl.getInstance().getStudent());
		if (SHOW_DEBUG_LOG)
			analyze();
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

	public void analyze() {
		System.out.println(String.format("original size:%d, new size:%d", 0, 0));
		System.out.println(String.format("collisions:%d%%", 100));
		System.out.println(String.format("space usage:%d%%, improve:%d%%", 0, 0));

	}
}
