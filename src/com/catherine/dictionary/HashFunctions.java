package com.catherine.dictionary;

import java.util.List;
import java.util.Stack;

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
 * 散列函数可以有各式不同的设计，并非固定的模式，大原则是越随机、越没有规律越好。
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
	public HashFunctions(int capacity, float loadFactor, int from, int to, boolean isUnique) {
		rawDaoImpl = new HashingDaoImpl("students_raw");
		rawDaoImpl.createRandomTable(capacity, loadFactor, from, to, isUnique);
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
	 * 4. 平方取中<br>
	 * 取关键码平方值的中间三位数作为散列地址<br>
	 * <br>
	 * 之所以取中间三码是有用意的，在平方运算中，是由一系列的累加进位而成，从个位数开始向左计算，
	 * 每个位数都是由原数经求和而成，因此取经过最多原数位的中间三位作为散列地址能让原关键码对散列地址的影响较大。
	 */
	public void midSquare() {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_mid_square");
		for (Student student : rawTableList) {
			hashingDaoImpl.insert(getMid3Num(student.seat_id * student.seat_id), student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("Median square");
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 5. 基本折叠法<br>
	 * 将关键码分割成等宽的若干段，取其总和作为散列地址，比如123456切3段成为12，34，56，取总和102作为映射函数。<br>
	 * <br>
	 * 
	 * @param n
	 *            平均切成几段
	 */
	public void fold(int n) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_fold");
		for (Student student : rawTableList) {
			Stack<Integer> stack = separate(student.seat_id, n);
			int temp = 0;
			while (!stack.isEmpty())
				temp += stack.pop();
			hashingDaoImpl.insert(temp, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("Fold");
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 6. 旋转折叠法<br>
	 * 将关键码分割成等宽的若干段，每一段的读取方向不同（从左到右或右到左读数字），取其总和作为散列地址，比如123456切3段成为12，43，56，
	 * 取总和111作为映射函数。<br>
	 * <br>
	 * 
	 * @param n
	 *            平均切成几段
	 */
	public void rotateAndFold(int n) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_rotate_fold");
		for (Student student : rawTableList) {
			Stack<Integer> stack = reverseOddAddressesNum(separate(student.seat_id, n));
			int temp = 0;
			while (!stack.isEmpty())
				temp += stack.pop();
			hashingDaoImpl.insert(temp, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("Rotate + fold");
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 7. 基本折叠法 + XOR<br>
	 * 将关键码转成二进制再分割成等宽的若干段，取其总和作为散列地址，比如124会转为1111100，切3段成为1，111，100，取1 XOR 111
	 * XOR 100后作为映射函数。<br>
	 * <br>
	 * 
	 * @param n
	 *            平均切成几段
	 */
	public void XORFold(int n) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_xor_fold");
		for (Student student : rawTableList) {
			Stack<Integer> stack = separate(student.seat_id, n);
			int temp = 0;
			while (!stack.isEmpty()) {
				temp ^= stack.pop();
			}
			hashingDaoImpl.insert(temp, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("XOR fold");
			analyze(rawTableList, rawStudentList, newTableList, newStudentList);
		}
	}

	/**
	 * 8. 旋转折叠法 + XOR<br>
	 * 将关键码转成二进制再分割成等宽的若干段，每一段的读取方向不同（从左到右或右到左读数字），356会转为101100100，切3段成为101，100，
	 * 100，取101 XOR 1 XOR 100后作为映射函数。<br>
	 * <br>
	 * 
	 * @param n
	 *            平均切成几段
	 */
	public void rotateAndXORFold(int n) {
		List<Student> rawTableList = rawDaoImpl.getTableList();
		List<Student> rawStudentList = rawDaoImpl.getStudent();

		// 要做hash处理的是学生信息表的seat_id
		HashingDaoImpl hashingDaoImpl = new HashingDaoImpl("students_hashing_rotate_xor_fold");
		for (Student student : rawTableList) {
			Stack<Integer> stack = reverseOddAddressesBinary(separate(student.seat_id, n));
			int temp = 0;
			while (!stack.isEmpty())
				temp ^= stack.pop();
			hashingDaoImpl.insert(temp, student.student_id);
		}

		List<Student> newTableList = hashingDaoImpl.getTableList();
		List<Student> newStudentList = hashingDaoImpl.getStudent();

		if (SHOW_DEBUG_LOG) {
			System.out.println("***************analytics***************");
			System.out.println("Rotate + fold + XOR");
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

	/**
	 * 取中间三位数字，例如传入12345，则回传234，传入1356则回传135，不足三位则原数返还。
	 * 
	 * @param num
	 * @return
	 */
	private int getMid3Num(int num) {
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
	private Stack<Integer> separate(int num, int n) {
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
	private Stack<Integer> reverseOddAddressesNum(Stack<Integer> numbers) {
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
	private Stack<Integer> reverseOddAddressesBinary(Stack<Integer> numbers) {
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
