package com.catherine.dictionary.functions;

import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 8. 旋转折叠法 + XOR<br>
 * 将关键码转成二进制再分割成等宽的若干段，每一段的读取方向不同（从左到右或右到左读数字），356会转为101100100，切3段成为101，100，
 * 100，取101 XOR 1 XOR 100后作为映射函数。<br>
 * <br>
 * 
 * @param n
 *            平均切成几段
 */
public class RotateAndXORFold extends HashingTemplate {
	private int n;
	private HashingHelper hashingHelper;

	public RotateAndXORFold(int n, CollisionMode collisionMode) {
		this.n = n;
		this.collisionMode = collisionMode;
		table = String.format("%s_students_hashing_rotate_xor_fold", CollisionMode.getName(collisionMode.getMode()));
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			Stack<Integer> stack = reverseOddAddressesBinary(separate(student.seat_id, n));
			int temp = 0;
			while (!stack.isEmpty())
				temp ^= stack.pop();
			hashingHelper.put(temp, student.student_id);
		}
		return hashingHelper.getTableList();
	}

	@Override
	public List<Student> getStudent() {
		return hashingHelper.getStudent();
	}

	@Override
	public List<Student> getTableList() {
		return hashingHelper.getTableList();
	}

	@Override
	public void analyse(List<Student> rawTableList, List<Student> rawStudentList, List<Student> newTableList,
			List<Student> newStudentList) {
		System.out.println("***************analytics***************");
		System.out.println(
				String.format("Rotate + fold + XOR\tmode: %s", CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
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
