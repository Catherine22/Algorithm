package com.catherine.dictionary.test;

import java.util.List;

import com.catherine.dictionary.HashingOperation;

public class HashingHelper extends HashingDao implements HashingOperation {
	private String TABLE;

	public HashingHelper(String TABLE) {
		this.TABLE = TABLE;
		super.initialize(TABLE);
	}

	public void createRandomTable(int capacity, float loadFactor, int from, int to, boolean isUnique) {
		super.getInstance().createRandomTable(TABLE, capacity, loadFactor, from, to, isUnique);
	}

	public List<Student> getStudent() {
		return super.getInstance().getStudent(TABLE);
	}

	public List<Student> getTableList() {
		return super.getInstance().getTableList(TABLE);
	}

	/**
	 * 
	 * @param SEAT_ID
	 *            key
	 * @param STUDENT_ID
	 *            value
	 */
	@Override
	public void put(int SEAT_ID, String STUDENT_ID) {
		super.getInstance().insert(TABLE, SEAT_ID, STUDENT_ID);
	}

	/**
	 * 
	 * @param SEAT_ID
	 *            key
	 * @return
	 */
	@Override
	public int get(int SEAT_ID) {
		int value = 0;

		return value;
	}

	/**
	 * 
	 * @param SEAT_ID
	 *            key
	 * @return
	 */
	@Override
	public void remove(int SEAT_ID) {

	}
}
