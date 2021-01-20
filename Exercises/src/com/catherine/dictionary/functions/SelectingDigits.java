package com.catherine.dictionary.functions;

import java.util.List;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 3. 数字分析法<br>
 * 必须先定义怎么样的分析法，比如这边采用的是取关键码的奇数位作为散列地址。
 */
public class SelectingDigits extends HashingTemplate {
	private HashingHelper hashingHelper;

	public SelectingDigits(CollisionMode collisionMode) {
		this.collisionMode = collisionMode;
		table = String.format("%s_students_hashing_odd", CollisionMode.getName(collisionMode.getMode()));
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			if (student.seat_id % 2 == 1)
				hashingHelper.put(student.seat_id, student.student_id);
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
		System.out.println(String.format("odd number\tmode: %s", CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
	}
}
