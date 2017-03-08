package com.catherine.data_type.trees;

import java.util.AbstractList;

public abstract class AbstractTree<E> {
	protected final boolean SHOW_DEBUG_LOG = false;

	public abstract void add(int index, E value);
	public abstract E remove(int index);
	public abstract E replace(int index, E value);
	public abstract int search(E value);
}
