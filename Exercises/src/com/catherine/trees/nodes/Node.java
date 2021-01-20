package com.catherine.trees.nodes;

import com.catherine.trees.nodes.RedBlackBSTNode.Color;

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

	public String getInfo();

	public boolean isBlack();

	public boolean isRed();

	public void setColor(Color color);
}
