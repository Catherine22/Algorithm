package com.catherine.trees;

import com.catherine.trees.nodes.Node;

public interface RedBlackBST<E extends Comparable<? super E>> {
	/**
	 * 和BST常规的插入做法一样，插入时预设时红节点，如果父节点为红色，此时违反红黑树定义，成为“双红缺陷(double-red)”。
	 * 
	 * @param key
	 * @return
	 */
	public Node<E> search(E data);

	public Node<E> insert(E data);

	public void remove(E data);

	/**
	 * 转成(2,4)树
	 * 
	 * @return
	 */
	public MyBTree<E> convertTo24Tree();

}
