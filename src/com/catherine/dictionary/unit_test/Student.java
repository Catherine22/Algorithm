package com.catherine.dictionary.unit_test;

public class Student {
	public Student() {

	}

	public Student(int id, int seat_id, String student_id, String student_name, int collisions) {
		this.id = id;
		this.seat_id = seat_id;
		this.student_id = student_id;
		this.student_name = student_name;
		this.collisions = collisions;
	}

	public int id, seat_id, collisions;
	public String student_id, student_name;

	@Override
	public String toString() {
		return "Student [id=" + id + ", student_id=" + student_id + ", collisions=" + collisions + "]";
	}

}
