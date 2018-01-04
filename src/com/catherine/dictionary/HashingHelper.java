package com.catherine.dictionary;

import java.util.List;

import com.catherine.dictionary.data.Student;
import com.catherine.dictionary.functions.CollisionMode;

public class HashingHelper {
	private String TABLE;
	private CollisionMode collisionMode;
	private Probing hashingDao;

	private Probing getHashingDao(CollisionMode collisionMode) {
		if (hashingDao != null)
			return hashingDao;
		else {
			switch (collisionMode.getMode()) {
			case CollisionMode.DO_NOTHING:
				hashingDao = new SimpleProbing();
				return hashingDao;
			case CollisionMode.LINEAR_PROBING:
				hashingDao = new LinearProbing(collisionMode.getSpareBuckets());
				return hashingDao;
			case CollisionMode.QUADRATIC_PROBING:
				hashingDao = new QuadraticProbing();
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
	 * @see CollisionMode#DO_NOTHING 不处理冲突
	 * @see CollisionMode#LINEAR_PROBING 线性试探
	 * @see CollisionMode#QUADRATIC_PROBING 平方试探
	 */
	public HashingHelper(String TABLE, CollisionMode collisionMode) {
		this.collisionMode = collisionMode;
		this.TABLE = TABLE;
		getHashingDao(collisionMode).initialize(TABLE);
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
	 * 插入新的栏位（学生）
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
	 */
	public void remove(int SEAT_ID) {

	}
}
