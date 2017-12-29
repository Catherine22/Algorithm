package com.catherine.dictionary.functions;

import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.data.Student;

public abstract class HashingTemplate {

	public abstract List<Student> hash(List<Student> rawTableList);

	public abstract List<Student> getStudent();

	public abstract List<Student> getTableList();

	public void analyse(List<Student> rawTableList, List<Student> rawStudentList, List<Student> newTableList,
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

	/**
	 * 找到最小素数，若没有返回-1
	 * 
	 * @param from
	 *            （含）
	 * @param to
	 *            （不含）
	 * @return
	 */
	protected int getPrimeNumber(int from, int to) {
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

	/**
	 * 取中间三位数字，例如传入12345，则回传234，传入1356则回传135，不足三位则原数返还。
	 * 
	 * @param num
	 * @return
	 */
	protected int getMid3Num(int num) {
		if (num < 100 && num > -100)
			return num;
		int result = 0;
		Stack<Integer> digits = new Stack<>();

		// 先分解成各个位数
		if (num > 0) {
			while (num > 0) {
				digits.push(num % 10);
				num /= 10;
			}
		} else {
			while (num < 0) {
				digits.push(num % -10);
				num /= 10;
			}
		}

		// 取中间三位
		if (digits.size() % 2 != 0) {
			int mid = (digits.size() - 1) / 2;
			result = digits.elementAt(mid - 1) * (int) Math.pow(10, 0) + digits.elementAt(mid) * (int) Math.pow(10, 1)
					+ digits.elementAt(mid + 1) * (int) Math.pow(10, 2);
		} else {
			int mid = digits.size() / 2;
			result = digits.elementAt(mid - 1) * (int) Math.pow(10, 0) + digits.elementAt(mid) * (int) Math.pow(10, 1)
					+ digits.elementAt(mid + 1) * (int) Math.pow(10, 2);
		}
		return result;
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

	/**
	 * 反转奇数地址的数字顺序，比如传入stack{134,275,12,20}，返回stack{134,572,12,2}
	 * 
	 * @param numbers
	 * @return
	 */
	protected Stack<Integer> reverseOddAddressesNum(Stack<Integer> numbers) {
		Stack<Integer> reversedStack = new Stack<>();
		Stack<Integer> intContainer = new Stack<>();

		while (numbers.size() > 0) {
			if (numbers.size() % 2 != 0) {
				// 偶数位不变
				reversedStack.push(numbers.pop());
			} else {
				// 反转数字

				// 共有几个位数
				int digits = 0;
				int tmp = numbers.pop();

				// 先分解成各个位数
				if (tmp > 0) {
					while (tmp > 0) {
						digits++;
						intContainer.push(tmp % 10);
						tmp /= 10;
					}
				} else {
					while (tmp < 0) {
						digits++;
						intContainer.push(tmp % -10);
						tmp /= 10;
					}
				}

				// 重新构成
				tmp = 0;
				int header = 0;
				while (header < digits) {
					tmp += (intContainer.pop() * Math.pow(10, header));
					header++;
				}
				reversedStack.push(tmp);
			}
		}

		// 最后再反转stack
		numbers.clear();
		while (!reversedStack.isEmpty())
			numbers.push(reversedStack.pop());
		return numbers;
	}

	/**
	 * 反转奇数地址的二进制数字顺序，比如传入stack{134,275,12,20}，也就是{10000110, 100010011, 1100,
	 * 10100}，返回stack{134,401,12,5}
	 * 
	 * @param numbers
	 * @return
	 */
	protected Stack<Integer> reverseOddAddressesBinary(Stack<Integer> numbers) {
		Stack<Integer> reversedStack = new Stack<>();

		while (numbers.size() > 0) {
			if (numbers.size() % 2 != 0) {
				// 偶数位不变
				reversedStack.push(numbers.pop());
			} else {
				// 反转二进制
				StringBuilder binary = new StringBuilder(Integer.toBinaryString(numbers.pop()));
				reversedStack.push(Integer.parseInt(binary.reverse().toString(), 2));
			}
		}

		// 最后再反转stack
		numbers.clear();
		while (!reversedStack.isEmpty())
			numbers.push(reversedStack.pop());
		return numbers;
	}

}
