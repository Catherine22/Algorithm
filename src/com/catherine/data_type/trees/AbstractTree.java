package com.catherine.data_type.trees;

public abstract class AbstractTree<E> {
	protected final boolean SHOW_DEBUG_LOG = false;

	public abstract E remove(int key);
	public abstract E replace(int key, E value);
	public abstract int search(E value);
	public abstract boolean isRoot(int key);
	public abstract boolean isLeaf(int key);
	public abstract E getParent(int key);
	public abstract E[] getChildren(int key);
	public abstract int getLevel(int key);
}
