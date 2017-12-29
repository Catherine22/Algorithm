package com.catherine.dictionary;

import java.util.List;

import com.catherine.dictionary.data.Student;
import com.catherine.dictionary.functions.CollisionMode;

public class HashingHelper {
	private String TABLE;
	private int collisionMode;
	private HashingDaoTemplate hashingDao;

	private HashingDaoTemplate getHashingDao(int collisionMode) {
		if (hashingDao != null)
			return hashingDao;
		else {
			switch (collisionMode) {
			case CollisionMode.DO_NOTHING:
				hashingDao = new HashingDao();
				return hashingDao;
			case CollisionMode.PROBING_SEQUENCE:
				hashingDao = new ProbingSequenceDao();
				return hashingDao;
			default:
				throw new NullPointerException("No such a mode");
			}
		}
	}

	/**
	 * 
	 * @param TABLE
	 * @param collisionMode
	 *            决定该如何处理冲突问题
	 * 
	 * @see CollisionMode#DO_NOTHING DO_NOTHING 不处理冲突
	 * @see CollisionMode#PROBING_SEQUENCE PROBING_SEQUENCE 线性试探
	 */
	public HashingHelper(String TABLE, int collisionMode) {
		this.collisionMode = collisionMode;
		this.TABLE = TABLE;

		switch (collisionMode) {
		case CollisionMode.DO_NOTHING:
			HashingDao.initialize(TABLE);
			break;
		case CollisionMode.PROBING_SEQUENCE:
			ProbingSequenceDao.initialize(TABLE);
			break;
		default:
			throw new NullPointerException("No such a mode");
		}
	}

	public void createRandomTable(int capacity, float loadFactor, int from, int to, boolean isUnique) {
		getHashingDao(collisionMode).createRandomTable(TABLE, capacity, loadFactor, from, to, isUnique);
	}

	public List<Student> getStudent() {
		return getHashingDao(collisionMode).getStudent(TABLE);
	}

	public List<Student> getTableList() {
		return getHashingDao(collisionMode).getTableList(TABLE);
	}

	/**
	 * 
	 * @param SEAT_ID
	 *            key
	 * @param STUDENT_ID
	 *            value
	 */
	public void put(int SEAT_ID, String STUDENT_ID) {
		getHashingDao(collisionMode).insert(TABLE, SEAT_ID, STUDENT_ID);
	}

	/**
	 * 
	 * @param SEAT_ID
	 *            key
	 * @return
	 */
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
	public void remove(int SEAT_ID) {

	}
}
