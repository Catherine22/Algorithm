package com.catherine.dictionary.functions;

import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 4. 平方取中<br>
 * 取关键码平方值的中间三位数作为散列地址<br>
 * <br>
 * 之所以取中间三码是有用意的，在平方运算中，是由一系列的累加进位而成，从个位数开始向左计算，
 * 每个位数都是由原数经求和而成，因此取经过最多原数位的中间三位作为散列地址能让原关键码对散列地址的影响较大。
 */
public class MidSquare extends HashingTemplate {
	private HashingHelper hashingHelper;

	public MidSquare(CollisionMode collisionMode) {
		this.collisionMode = collisionMode;
		table = String.format("%s_students_hashing_mid_square", CollisionMode.getName(collisionMode.getMode()));
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			hashingHelper.put(getMid3Num(student.seat_id * student.seat_id), student.student_id);
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
		System.out.println(String.format("Median square\tmode: %s", CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
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
}
