package com.catherine.dictionary.unit_test;

import java.util.List;

public interface UnitTestDao {
	public void createRandomTable(int capacity, float loadFactor, int from, int to);

	public void insert(int SEAT_ID, String STUDENT_ID);

	public List<Student> getStudent();

	public List<Student> getTableList();
}
