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
public class B_Node {
	private B_Node parent;
	/**
	 * 关键码向量位置（不重复）
	 */
	private List<Integer> key;
	/**
	 * 孩子向量（其总长度比关键码向量多一）
	 */
	private List<B_Node> child;

	public B_Node getParent() {
		return parent;
	}

	public void setParent(B_Node parent) {
		this.parent = parent;
	}

	public List<Integer> getKey() {
		return key;
	}

	public void setKey(List<Integer> key) {
		this.key = key;
	}

	public List<B_Node> getChild() {
		return child;
	}

	public void setChild(List<B_Node> child) {
		this.child = child;
	}

}
