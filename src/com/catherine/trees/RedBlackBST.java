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
	 * 考察目标节点的祖父节点和父节点的兄弟（叔父节点）。<br>
	 * <br>
	 * 目标节点-父节点-祖父节点-叔父节点四点上升到同一排，想象成四个key，为(2,4)树的一个节点。<br>
	 * 有四种组合：<br>
	 * 目标-父-祖父-叔父<br>
	 * 父-目标-祖父-叔父<br>
	 * 叔父-祖父-目标-父<br>
	 * 叔父-祖父-父-目标<br>
	 * <br>
	 * 情况1：红-红-黑-黑，不必改变拓扑结构，不必检查祖先，改成黑-红-黑-黑，耗时O(1)<br>
	 * 情况2：红-红-黑-红<br>
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
	public MyBTree<E> convertTo24Tree();

}
