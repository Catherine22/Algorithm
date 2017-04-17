package com.catherine.graphs.trees;

public abstract class AbstractNode<E> {

	/**
	 * 节点到叶子的最长长度（由下往上，从最下层孩子出发）
	 */
	public int height;

	/**
	 * 根到节点的最长长度（由上往下，从根出发）
	 */
	public int depth;
	public E data;

	public AbstractNode(E data, int height, int depth) {
		this.data = data;
		this.depth = depth;
		this.height = height;
	}

	public abstract String toString();
}