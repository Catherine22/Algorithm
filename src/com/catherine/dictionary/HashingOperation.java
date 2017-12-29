package com.catherine.dictionary;

public interface HashingOperation {

	public void put(int key, String value);

	public int get(int key);

	public void remove(int key);
}
