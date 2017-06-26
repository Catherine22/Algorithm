package com.catherine.graphs.trees.nodes;

import java.util.List;

//B-tree
/**
 * 关键码向量(x)与孩子向量(o)的位置应对齐，孩子向量在关键码向量的左右两侧，比如：<br>
 * _x_x_x_<br>
 * o_o_o_o<br>
 * <br>
 * 所以初始时有一个关键码向量与两个孩子（左右孩子）向量。
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class B_Node<E> {
	private B_Node<E> parent;
	/**
	 * 关键码向量位置（不重复）
	 */
	private List<Integer> key;
	/**
	 * 关键码向量值（这边是我自定义）
	 */
	private List<E> data;
	/**
	 * 孩子向量（其总长度比关键码向量多一）
	 */
	private List<B_Node<E>> child;

	public B_Node<E> getParent() {
		return parent;
	}

	public void setParent(B_Node<E> parent) {
		this.parent = parent;
	}

	public List<Integer> getKey() {
		return key;
	}

	public void setKey(List<Integer> key) {
		this.key = key;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public List<B_Node<E>> getChild() {
		return child;
	}

	public void setChild(List<B_Node<E>> child) {
		this.child = child;
	}

}
