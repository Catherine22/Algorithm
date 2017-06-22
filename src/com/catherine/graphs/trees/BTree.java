package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.B_Node;

public interface BTree<E> {
	/**
	 * 返回根节点
	 * 
	 * @return 根节点
	 */
	public B_Node<E> getRoot();

	/**
	 * 是否为空树（没有节点）
	 * 
	 * @return boolean
	 */
	public boolean isEmpty();

	/**
	 * 子树规模
	 * 
	 * @return 子节点数
	 */
	public int size();

	/**
	 * 当前节点的子树规模
	 * 
	 * @param 指定节点
	 * @return 子节点数
	 */
	public int size(B_Node<E> node);

	/**
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：-1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * 
	 * @return 高度
	 */
	public int getHeight();

	/**
	 * 失败查找比终止于外部节点。<br>
	 * 
	 * @param e
	 * @return
	 */
	public B_Node<E> search(E e);

	public boolean insert(E e);

	public boolean remove(E e);

	/**
	 * 因插入而上溢后的分裂处理
	 * 
	 * @param node
	 */
	public void solveOverflow(B_Node<E> node);

	/**
	 * 因删除而下溢后的合并处理
	 * 
	 * @param node
	 */
	public void solveUnderfolw(B_Node<E> node);
}