package com.catherine.trees;

import com.catherine.trees.nodes.Node;

public interface RedBlackBST<E> {
	/**
	 * 和BST常规的插入做法一样，插入时预设时红节点，如果父节点为红色，此时违反红黑树定义，成为“双红缺陷(double-red)”。
	 * 
	 * @param key
	 * @return
	 */
	public Node<E> search(int key);

	public Node<E> insert(int key, E data);

	public void remove(int key);

	/**
	 * 目标节点为红节点，目标节点的父节点也是红色，称双红缺陷(double-red)。<br>
	 * 考察目标节点的祖父节点和父节点的兄弟（叔父节点）。
	 * 
	 * @param node目标节点
	 */
	public void solveDoubleRed(Node<E> node);

	public void solveDoubleBlack(Node<E> node);

	/**
	 * 红黑树的高度是指黑节点的高度。
	 */
	public void updateHeight();

	/**
	 * 转成(2,4)树
	 * 
	 * @return
	 */
	public MyBTree convertTo24Tree();

}
