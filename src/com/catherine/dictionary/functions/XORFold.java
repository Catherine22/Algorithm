package com.catherine.dictionary.functions;

import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 7. 基本折叠法 + XOR<br>
 * 将关键码转成二进制再分割成等宽的若干段，取其总和作为散列地址，比如124会转为1111100，切3段成为1，111，100，取1 XOR 111 XOR
 * 100后作为映射函数。<br>
 * <br>
 * 
 * @param n
 *            平均切成几段
 */
public class XORFold extends HashingTemplate {
	private int n;
	private HashingHelper hashingHelper;

	public XORFold(int n, CollisionMode collisionMode) {
		this.n = n;
		this.collisionMode = collisionMode;
		table = String.format("%s_students_hashing_xor_fold", CollisionMode.getName(collisionMode.getMode()));
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			Stack<Integer> stack = separate(student.seat_id, n);
			int temp = 0;
			while (!stack.isEmpty()) {
				temp ^= stack.pop();
			}
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
		System.out.println(String.format("XOR fold\tmode: %s", CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
	}
}
