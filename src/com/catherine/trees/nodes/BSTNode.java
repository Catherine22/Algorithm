package com.catherine.trees.nodes;

/**
 * Binary search tree(BST, Splay tree and AVL tree)
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class BSTNode<E> implements Node<E>, Cloneable {

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

	public BSTNode(E data, BSTNode<E> parent, BSTNode<E> lChild, BSTNode<E> rChild, int height, int depth) {
		this.data = data;
		this.depth = depth;
		this.height = height;
		this.parent = parent;
		this.lChild = lChild;
		this.rChild = rChild;
	}

	@Override
	public String toString() {
		String pkey = (parent != null) ? parent.data + "" : "null parent";
		String lkey = (lChild != null) ? lChild.data + "" : "null lChild";
		String rkey = (rChild != null) ? rChild.data + "" : "null rChild";

		return String.format(
				"{\"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_key\": \"%s\", \"lChild_key\": \"%s\", \"rChild_key\": \"%s\"}",
				data, height, depth, pkey, lkey, rkey);

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
		return parent;
	}

	@Override
	public void setParent(Node<E> parent) {
		this.parent = (BSTNode<E>) parent;
	}

	@Override
	public Node<E> getlChild() {
		return lChild;
	}

	@Override
	public void setlChild(Node<E> lChild) {
		this.lChild = (BSTNode<E>) lChild;
	}

	@Override
	public Node<E> getrChild() {
		return rChild;
	}

	@Override
	public void setrChild(Node<E> rChild) {
		this.rChild = (BSTNode<E>) rChild;
	}

	@Override
	public String getInfo() {
		// data + "(" + height + ") "
		return String.format("%s(%d)", (data == null) ? "null" : data.toString(), height);
	}

	@Override
	public boolean isBlack() {
		// do nothing
		return false;
	}

	@Override
	public boolean isRed() {
		// do nothing
		return false;
	}

	@Override
	public void setColor(RedBlackBSTNode.Color color) {
		// do nothing

	}

	/**
	 * 拷贝此节点并回传副本
	 * 
	 * @return 副本
	 */
	public Node<E> clone() {
		Node<E> clone = new BSTNode<E>(data, (parent == null) ? null : (BSTNode<E>) parent.clone(),
				(lChild == null) ? null : (BSTNode<E>) lChild.clone(),
				(rChild == null) ? null : (BSTNode<E>) rChild.clone(), height, depth);
		return clone;
	}
}
