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
	 * 一定要从B-tree的角度来看方便理解。<br>
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
	 * 情况1：黑叔父节点。中间节点往上搬，其它放两侧，中间节点黑色，其它红色。不必改变拓扑结构，不必检查原祖父的祖先，耗时O(1)。<br>
	 * 1-1 目标-父-祖父成一直线，父节点为中间节点。<br>
	 * 1-2 目标-父-祖父成"<"或">"型，目标节点为中间节点。<br>
	 * <br>
	 * 情况2：红叔父节点，要检查原祖父的祖先，至多耗时O(n)。<br>
	 * 四节点合起来看就是B-tree的上溢，最上面的节点为红色，两旁黑色，最下层红色。 <br>
	 * 因为最上面的节点为红色，做完后情况2节点可能会再度发生双红缺陷，情况2须检查祖先。
	 * 
	 * @param node目标节点
	 */
	public void solveDoubleRed(Node<E> node);

	public void solveDoubleBlack(Node<E> node);

	/**
	 * 红黑树的高度是指黑节点的高度。 <br>
	 * 从最后面到叶节点到自身的黑节点树，每条路径都会是同样长度。<br>
	 * 计算节点高度时，不考虑自身节点。
	 */
	public void updateHeight(Node<E> node);

	/**
	 * 转成(2,4)树
	 * 
	 * @return
	 */
	public MyBTree<E> convertTo24Tree();

}
