package com.catherine.graphs.trees.nodes;

public class BSTNode<E> implements Node<E> {

	/**
	 * key-value, key不重复
	 */
	private int key;

	/**
	 * 节点到叶子的最长长度（由下往上，从最下层孩子出发）
	 */
	private int height;

	/**
	 * 根到节点的最长长度（由上往下，从根出发）
	 */
	private int depth;
	private E data;
	private BSTNode<E> parent;
	private BSTNode<E> lChild;
	private BSTNode<E> rChild;

	public BSTNode(int key, E data, BSTNode<E> parent, BSTNode<E> lChild, BSTNode<E> rChild, int height, int depth) {
		this.data = data;
		this.depth = depth;
		this.height = height;
		this.key = key;
		this.parent = parent;
		this.lChild = lChild;
		this.rChild = rChild;
	}

	public String getInfo() {
		return key + "(" + height + ") ";
	}

	@Override
	public String toString() {
		if (parent != null)
			return String.format(
					"{\"key\": \"%d\", \"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_key\": \"%d\"}", key,
					data, height, depth, parent.key);
		else
			return String.format(
					"{\"key\": \"%d\", \"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_key\": \"%s\"}", key,
					data, height, depth, "null parent");
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public E getData() {
		return data;
	}

	@Override
	public void setData(E data) {
		this.data = data;
	}

	@Override
	public Node<E> getParent() {
		return (BSTNode<E>) parent;
	}

	@Override
	public void setParent(Node<E> parent) {
		this.parent = (BSTNode<E>) parent;
	}

	@Override
	public Node<E> getlChild() {
		return (BSTNode<E>) lChild;
	}

	@Override
	public void setlChild(Node<E> lChild) {
		this.lChild = (BSTNode<E>) lChild;
	}

	@Override
	public Node<E> getrChild() {
		return (BSTNode<E>) rChild;
	}

	@Override
	public void setrChild(Node<E> rChild) {
		this.rChild = (BSTNode<E>) rChild;
	}
}
