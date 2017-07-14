package com.catherine.graphs.trees.nodes;

import java.util.ArrayList;
import java.util.List;

//B-tree
/**
 * 关键码向量(x)与孩子向量(o)的位置应对齐，孩子向量在关键码向量的左右两侧，比如：<br>
 * _x_x_x_<br>
 * o_o_o_o<br>
 * <br>
 * 所以初始时有一个关键码向量与两个孩子（左右孩子）向量。<br>
 * <br>
 * <br>
 * 一棵m阶的B-Tree每个节点（外部节点除外），都会有：<br>
 * 1. 一系列的key值，key(i)>key(i-1) <br>
 * 2. key(i)的左孩子节点child(i-1)内每个key值都小于key(i)，同时也都大于key(i-1)。<br>
 * 3. key的个数n必须满足 [m / 2]-1<= n <= m-1<br>
 * 4. 每个节点的孩子／分支个数必须满足 [m / 2]<= n <= m，所以也称为([m / 2], m)-tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class B_Node implements Cloneable {
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

	@SuppressWarnings("unchecked")
	@Override
	public B_Node clone() throws CloneNotSupportedException {
		B_Node tmp = (B_Node) super.clone();
		if (parent != null)
			tmp.parent = (B_Node) this.parent.clone();
		if (key != null)
			tmp.key = (List<Integer>) ((ArrayList<Integer>) this.key).clone();
		if (child != null)
			tmp.child = (List<B_Node>) ((ArrayList<B_Node>) this.child).clone();
		return tmp;
	}

	@Override
	public String toString() {
		String a = (parent != null && parent.getKey() != null) ? parent.getKey().toString() : "null";
		String b = key != null ? key.toString() : "null";
		String c = (child != null) ? child.toString() : "null";
		return String.format("[parent key:%s, key:%s, child key:%s]\n", a, b, c);
	}
}
