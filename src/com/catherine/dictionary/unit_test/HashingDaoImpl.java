package com.catherine.dictionary.unit_test;

import java.util.List;

public class HashingDaoImpl extends MainTestDao implements UnitTestDao {
	private final String TABLE = "students_hashing";

	private static class RawDaoImplHolder {
		private static HashingDaoImpl instance = new HashingDaoImpl();
	}

	public static HashingDaoImpl getInstance() {
		return RawDaoImplHolder.instance;
	}

	private HashingDaoImpl() {
		super.initialize(TABLE);
	}

	public void createRandomTable(int capacity, float loadFactor, int from, int to) {
		super.getInstance().createRandomTable(TABLE, capacity, loadFactor, from, to);
	}

	public void insert(int SEAT_ID, String STUDENT_ID) {
		super.getInstance().insert(TABLE, SEAT_ID, STUDENT_ID);
	}

	public List<Student> getStudent() {
		return super.getInstance().getStudent(TABLE);
	}

	public List<Student> getTableList(){
		return super.getInstance().getTableList(TABLE);
	}
}
