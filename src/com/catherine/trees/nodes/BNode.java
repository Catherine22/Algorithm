package com.catherine.trees.nodes;

/**
 * Binary tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class BNode<E> implements Node<E> {
	/**
	 * 节点到叶子的最长长度（由下往上，从最下层孩子出发）
	 */
	private int height;

	/**
	 * 根到节点的最长长度（由上往下，从根出发）
	 */
	private int depth;
	private E data;
	private BNode<E> parent;
	private BNode<E> lChild;
	private BNode<E> rChild;

	public BNode(E data, BNode<E> parent, BNode<E> lChild, BNode<E> rChild, int height, int depth) {
		this.data = data;
		this.depth = depth;
		this.height = height;
		this.parent = parent;
		this.lChild = lChild;
		this.rChild = rChild;
	}

	@Override
	public String toString() {
		if (parent != null)
			return String.format(
					"{\"data\": \"%s\", \"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_data\": \"%s\"}",
					data, data, height, depth, parent.data);
		else
			return String.format(
					"{\"data\": \"%s\", \"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_data\": \"%s\"}",
					data, data, height, depth, "null parent");
	}

	@Override
	public int getKey() {
		// Do nothing
		return -1;
	}

	@Override
	public void setKey(int key) {
		// Do nothing
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
		return (BNode<E>) parent;
	}

	@Override
	public void setParent(Node<E> parent) {
		this.parent = (BNode<E>) parent;
	}

	@Override
	public Node<E> getlChild() {
		return (BNode<E>) lChild;
	}

	@Override
	public void setlChild(Node<E> lChild) {
		this.lChild = (BNode<E>) lChild;
	}

	@Override
	public Node<E> getrChild() {
		return (BNode<E>) rChild;
	}

	@Override
	public void setrChild(Node<E> rChild) {
		this.rChild = (BNode<E>) rChild;
	}

	@Override
	public String getInfo() {
		if (data != null)
			return data + " ";
		else
			return "null ";

	}
}
