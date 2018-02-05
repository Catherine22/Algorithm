package com.catherine.trees.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * B-tree<br>
 * <br>
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
public class BNode<E> implements Cloneable {
	private BNode<E> parent;
	/**
	 * 关键码向量
	 */
	private List<E> key;
	/**
	 * 孩子向量（其总长度比关键码向量多一）
	 */
	private List<BNode<E>> child;

	public BNode<E> getParent() {
		return parent;
	}

	public void setParent(BNode<E> parent) {
		this.parent = parent;
	}

	public List<E> getKey() {
		return key;
	}

	public void setKey(List<E> key) {
		this.key = key;
	}

	public List<BNode<E>> getChild() {
		return child;
	}

	public void setChild(List<BNode<E>> child) {
		this.child = child;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<E> clone() throws CloneNotSupportedException {
		BNode<E> tmp = (BNode<E>) super.clone();
		if (parent != null)
			tmp.parent = (BNode<E>) this.parent.clone();
		if (key != null)
			tmp.key = (List<E>) ((ArrayList<E>) this.key).clone();
		if (child != null)
			tmp.child = (List<BNode<E>>) ((ArrayList<BNode<E>>) this.child).clone();
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
