package com.catherine.graphs.trees.nodes;

import java.util.Arrays;

//B-tree
public class B_Node<E> {
	private B_Node<E> parent;
	/**
	 * 关键码向量
	 */
	private E[] key;
	/**
	 * 孩子向量（其长度总比key夗一）
	 */
	private B_Node<E>[] child;

	public B_Node<E> getParent() {
		return parent;
	}

	public void setParent(B_Node<E> parent) {
		this.parent = parent;
	}

	public E[] getKey() {
		return key;
	}

	public void setKey(E[] key) {
		this.key = key;
	}

	public B_Node<E>[] getChild() {
		return child;
	}

	public void setChild(B_Node<E>[] child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "B_Node [parent=" + parent + ", key=" + Arrays.toString(key) + ", child=" + Arrays.toString(child) + "]";
	}
}
