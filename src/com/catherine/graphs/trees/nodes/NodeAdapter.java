package com.catherine.graphs.trees.nodes;

public class NodeAdapter<E> implements Node<E> {
	private Nodes type;
	private Node<E> aNode;

	public NodeAdapter(Nodes type) {
		this.type = type;
	}

	public void buildNode(E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height, int depth) {
		if (type == Nodes.STANDARD)
			aNode = new BNode<E>(data, (BNode<E>) parent, (BNode<E>) lChild, (BNode<E>) rChild, height, depth);
	}

	public void buildNode(int key, E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height, int depth) {
		if (type == Nodes.STANDARD)
			aNode = new BNode<E>(data, (BNode<E>) parent, (BNode<E>) lChild, (BNode<E>) rChild, height, depth);
		else if (type == Nodes.BST)
			aNode = new BSTNode<E>(key, data, (BSTNode<E>) parent, (BSTNode<E>) lChild, (BSTNode<E>) rChild, height,
					depth);
	}

	public Nodes getType() {
		return type;
	}

	public int getKey() {
		if (type == Nodes.BST) {
			BSTNode<E> node = (BSTNode<E>) aNode;
			return node.getKey();
		} else
			return -1;

	}

	public void setKey(int key) {
		if (type == Nodes.BST) {
			BSTNode<E> node = (BSTNode<E>) aNode;
			node.setKey(key);
			;
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

}
