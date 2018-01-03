package com.catherine.dictionary.functions;

import java.util.List;
import java.util.Stack;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 5. 基本折叠法<br>
 * 将关键码分割成等宽的若干段，取其总和作为散列地址，比如123456切3段成为12，34，56，取总和102作为映射函数。<br>
 * <br>
 * 
 * @param n
 *            平均切成几段
 */
public class Fold extends HashingTemplate {
	private int n;
	private String table;
	private CollisionMode collisionMode;
	private HashingHelper hashingHelper;

	public Fold(int n, CollisionMode collisionMode) {
		this.n = n;
		this.collisionMode = collisionMode;
		table = "students_hashing_fold";
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			Stack<Integer> stack = separate(student.seat_id, n);
			int temp = 0;
			while (!stack.isEmpty())
				temp += stack.pop();
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
		System.out.println(String.format("Fold\tmode: %s", CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
	}
}
