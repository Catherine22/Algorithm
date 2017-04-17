package com.catherine.graphs.trees.nodes;

public interface Node<E> {

	public int getHeight();

	public void setHeight(int height);

	public int getDepth();

	public void setDepth(int depth);

	public E getData();

	public void setData(E data);

	public Node<E> getParent();

	public void setParent(Node<E> parent);

	public Node<E> getlChild();

	public void setlChild(Node<E> lChild);

	public Node<E> getrChild();

	public void setrChild(Node<E> rChild);
}
