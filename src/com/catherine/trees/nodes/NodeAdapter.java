package com.catherine.trees.nodes;

import com.catherine.trees.nodes.RedBlackBSTNode.Color;

/**
 * 利用装饰者模式切换Node
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class NodeAdapter<E> implements Node<E>, Cloneable {
	private Nodes type;
	private Node<E> aNode;

	public void setType(Nodes type) {
		this.type = type;
	}

	public Node<E> buildNode(E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height, int depth) {
		if (type == Nodes.STANDARD)
			aNode = new BinaryNode<E>(data, (BinaryNode<E>) parent, (BinaryNode<E>) lChild, (BinaryNode<E>) rChild,
					height, depth);
		else if (type == Nodes.BST)
			aNode = new BSTNode<E>(data, (BSTNode<E>) parent, (BSTNode<E>) lChild, (BSTNode<E>) rChild, height, depth);
		else if (type == Nodes.RED_BLACK)
			aNode = new RedBlackBSTNode<E>(data, (RedBlackBSTNode<E>) parent, (RedBlackBSTNode<E>) lChild,
					(RedBlackBSTNode<E>) rChild, height, depth);

		return aNode;
	}

	public Nodes getType() {
		return type;
	}

	@Override
	public boolean isBlack() {
		if (type == Nodes.RED_BLACK) {
			RedBlackBSTNode<E> node = (RedBlackBSTNode<E>) aNode;
			return node.isBlack();
		}
		return false;
	}

	@Override
	public boolean isRed() {
		if (type == Nodes.RED_BLACK) {
			RedBlackBSTNode<E> node = (RedBlackBSTNode<E>) aNode;
			return node.isRed();
		}
		return false;
	}

	@Override
	public void setColor(Color color) {
		if (type == Nodes.RED_BLACK) {
			RedBlackBSTNode<E> node = (RedBlackBSTNode<E>) aNode;
			node.setColor(color);
		}
	}

	@Override
	public int getHeight() {
		return aNode.getHeight();
	}

	@Override
	public void setHeight(int height) {
		aNode.setHeight(height);
	}

	@Override
	public int getDepth() {
		return aNode.getDepth();
	}

	@Override
	public void setDepth(int depth) {
		aNode.setDepth(depth);
	}

	@Override
	public E getData() {
		return aNode.getData();
	}

	@Override
	public void setData(E data) {
		aNode.setData(data);
	}

	@Override
	public Node<E> getParent() {
		return aNode.getParent();
	}

	@Override
	public void setParent(Node<E> parent) {
		aNode.setParent(parent);
	}

	@Override
	public Node<E> getlChild() {
		return aNode.getlChild();
	}

	@Override
	public void setlChild(Node<E> lChild) {
		aNode.setlChild(lChild);
	}

	@Override
	public Node<E> getrChild() {
		return aNode.getrChild();
	}

	@Override
	public void setrChild(Node<E> rChild) {
		aNode.setrChild(rChild);
	}

	@Override
	public String toString() {
		return aNode.toString();
	}

	@Override
	public String getInfo() {
		return aNode.getInfo();
	}

	/**
	 * 拷贝此节点并回传副本
	 * 
	 * @return 副本
	 */
	public Node<E> clone() {
		return aNode.clone();
	}

}
