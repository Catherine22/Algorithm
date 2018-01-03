package com.catherine.dictionary.functions;

import java.util.List;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 1. 除余法<br>
 * hash(key) = key % m<br>
 * <br>
 * 为了要让整个散列表分布的更均匀，尽可能的不要冲突，计算M时应选择gcd(key, m) = 1， 因为key会不断的变动，所以m应该找一个素数。 <br>
 * 缺点：<br>
 * 不动点——hash(0) = 0<br>
 * 零阶均匀——相邻关键码的散列地址也会相邻<br>
 * 
 * @param m
 *            应为一素数
 */
public class Remainder extends HashingTemplate {
	private int m;
	private HashingHelper hashingHelper;

	public Remainder(int m, CollisionMode collisionMode) {
		this.m = m;
		this.collisionMode = collisionMode;
		table = String.format("students_hashing_%d", m);
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawStudentList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawStudentList) {
			hashingHelper.put(student.seat_id % m, student.student_id);
		}
		return hashingHelper.getStudent();
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
		System.out.println(String.format("mod %d\tmode: %s", m, CollisionMode.getName(collisionMode.getMode())));
		analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
	}
}
