package com.catherine.trees.nodes;

/**
 * Red-black BST
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class RedBlackBSTNode<E> implements Node<E> {
	public enum Color {
		RED, BLACK
	}

	/**
	 * key-value, key不重复
	 */
	private int key;

	/**
	 * 到最末端黑节点的长度
	 */
	private int height;

	/**
	 * 节点到根的长度
	 */
	private int depth;
	private E data;
	private RedBlackBSTNode<E> parent;
	private RedBlackBSTNode<E> lChild;
	private RedBlackBSTNode<E> rChild;
	private boolean isBlack;// false: red

	public RedBlackBSTNode(int key, E data, RedBlackBSTNode<E> parent, RedBlackBSTNode<E> lChild,
			RedBlackBSTNode<E> rChild, int height, int depth) {
		// 新增节点预设红色
		this(key, data, parent, lChild, rChild, height, depth, false);
	}

	public RedBlackBSTNode(int key, E data, RedBlackBSTNode<E> parent, RedBlackBSTNode<E> lChild,
			RedBlackBSTNode<E> rChild, int height, int depth, boolean isBlack) {
		this.data = data;
		this.depth = depth;
		this.height = height;
		this.key = key;
		this.parent = parent;
		this.lChild = lChild;
		this.rChild = rChild;
		this.isBlack = isBlack;
	}

	@Override
	public String toString() {
		String pkey = (getParent() != null) ? getParent().getKey() + "" : "null parent";
		String lkey = (getlChild() != null) ? getlChild().getKey() + "" : "null lChild";
		String rkey = (getrChild() != null) ? getrChild().getKey() + "" : "null rChild";
		String color = isBlack ? "B" : "R";
		return String.format(
				"{\"key\": \"%d\", \"data\": \"%s\", \"color\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_key\": \"%s\", \"lChild_key\": \"%s\", \"rChild_key\": \"%s\"}",
				getKey(), getData(), color, getHeight(), getDepth(), pkey, lkey, rkey);

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
		return (RedBlackBSTNode<E>) parent;
	}

	@Override
	public void setParent(Node<E> parent) {
		this.parent = (RedBlackBSTNode<E>) parent;
	}

	@Override
	public Node<E> getlChild() {
		return (RedBlackBSTNode<E>) lChild;
	}

	@Override
	public void setlChild(Node<E> lChild) {
		this.lChild = (RedBlackBSTNode<E>) lChild;
	}

	@Override
	public Node<E> getrChild() {
		return (RedBlackBSTNode<E>) rChild;
	}

	@Override
	public void setrChild(Node<E> rChild) {
		this.rChild = (RedBlackBSTNode<E>) rChild;
	}

	@Override
	public String getInfo() {
		String color = isBlack ? "B" : "R";
		return String.format("%d(%d, %s) ", key, height, color);
	}

	@Override
	public boolean isBlack() {
		return isBlack;
	}

	@Override
	public boolean isRed() {
		return !isBlack;
	}

	@Override
	public void setColor(Color color) {
		isBlack = (color == Color.BLACK);
	}

}