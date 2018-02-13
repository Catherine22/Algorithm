package com.catherine.trees.nodes;

/**
 * Binary tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class BinaryNode<E> implements Node<E> {
	/**
	 * 节点到叶子的最长长度（由下往上，从最下层孩子出发）
	 */
	private int height;

	/**
	 * 根到节点的最长长度（由上往下，从根出发）
	 */
	private int depth;
	private E data;
	private BinaryNode<E> parent;
	private BinaryNode<E> lChild;
	private BinaryNode<E> rChild;

	public BinaryNode(E data, BinaryNode<E> parent, BinaryNode<E> lChild, BinaryNode<E> rChild, int height, int depth) {
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
			return String.format("{\"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_data\": \"%s\"}", data,
					height, depth, parent.data);
		else
			return String.format("{\"data\": \"%s\", \"height\": %d, \"depth\": %d, \"parent_data\": \"%s\"}", data,
					height, depth, "null parent");
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
		this.parent = (BinaryNode<E>) parent;
	}

	@Override
	public Node<E> getlChild() {
		return lChild;
	}

	@Override
	public void setlChild(Node<E> lChild) {
		this.lChild = (BinaryNode<E>) lChild;
	}

	@Override
	public Node<E> getrChild() {
		return rChild;
	}

	@Override
	public void setrChild(Node<E> rChild) {
		this.rChild = (BinaryNode<E>) rChild;
	}

	@Override
	public String getInfo() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append((data == null) ? "null" : data.toString());
		sBuilder.append("(p:");
		sBuilder.append((parent == null) ? "null" : parent.getData());
		sBuilder.append(')');
		return sBuilder.toString();
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

}
