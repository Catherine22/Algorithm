package com.catherine.dictionary.functions;

import java.util.List;

import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;

/**
 * 2. modulo<br>
 * hash(key) = (key * step + offset) % m<br>
 * <br>
 * 改良除余法，经过modulo处理后不再出现不动点与零阶均匀（modulo是一阶均匀）<br>
 * 视情况而定，不是非得要用高阶均匀。
 * 
 * @param step
 * @param offset
 * @param m
 *            应为一素数
 */
public class Mod extends HashingTemplate {
	private int m;
	private int step;
	private int offset;
	private String table;
	private CollisionMode collisionMode;
	private HashingHelper hashingHelper;

	public Mod(int step, int offset, int m, CollisionMode collisionMode) {
		this.m = m;
		this.step = step;
		this.offset = offset;
		this.collisionMode = collisionMode;
		table = String.format("students_hashing_%d_%d_%d", step, offset, m);
		hashingHelper = new HashingHelper(table, collisionMode);
	}

	@Override
	public List<Student> hash(List<Student> rawTableList) {
		// 要做hash处理的是学生信息表的seat_id
		for (Student student : rawTableList) {
			hashingHelper.put((student.seat_id * step + offset) % m, student.student_id);
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
		System.out.println(String.format("(%d * key + %d) %% %d\tmode: %s", step, offset, m,
				CollisionMode.getName(collisionMode.getMode())));
		super.analyse(table, rawTableList, rawStudentList, newTableList, newStudentList);
	}
}
