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
	 * 阶次
	 * 
	 * @return
	 */
	public int order();

	/**
	 * 节点高度h定义： 树根第0层<br>
	 * 树根的子节点第1层<br>
	 * ...<br>
	 * 叶节点第h-1层<br>
	 * 外部节点第h层<br>
	 * 
	 * @return 高度
	 */
	public int getHeight();

	/**
	 * 取最大高度（表示每个超级节点应尽可能瘦）
	 * 
	 * @return
	 */
	public int getLargestHeight(int level);

	/**
	 * 一层一层往下找，直觉会想到用binary search加速，但以整个内外存来看，这样的加速未必有效，甚至可能会花上更多时间。<br>
	 * 失败查找会终止于外部节点。<br>
	 * 
	 * @param key
	 * @return
	 */
	public B_Node<E> search(int key);

	public boolean insert(int key, E e);

	public boolean remove(E e);

	public boolean remove(int key);

	/**
	 * 释放所有节点
	 */
	public void release();

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
