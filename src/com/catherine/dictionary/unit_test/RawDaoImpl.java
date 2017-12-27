package com.catherine.dictionary.unit_test;

import java.util.List;

public class RawDaoImpl extends MainTestDao implements UnitTestDao {
	private final String TABLE = "students_raw";

	private static class RawDaoImplHolder {
		private static RawDaoImpl instance = new RawDaoImpl();
	}

	public static RawDaoImpl getInstance() {
		return RawDaoImplHolder.instance;
	}

	private RawDaoImpl() {
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
