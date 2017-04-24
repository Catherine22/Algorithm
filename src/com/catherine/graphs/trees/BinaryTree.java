package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

public interface BinaryTree<E> {

	/**
	 * 返回根节点
	 * 
	 * @return 根节点
	 */
	public Node<E> getRoot();

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
	public int size(Node<E> node);

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
	 * 满二叉树（full binary tree）、真二叉树（proper binary tree）又称为严格二叉树（strictly binary
	 * tree），每个节点都只有0或2个节点。
	 * 
	 * 用{@link #traverseLevel()}
	 * 
	 * @return
	 */
	public boolean isFull();

	/**
	 * 移除整个右子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeRCCompletely(Node<E> parent);

	/**
	 * 移除整个左子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeLCCompletely(Node<E> parent);

	/**
	 * 以阶层遍历
	 */
	public void traverseLevel();

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePreNR1();

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）<br>
	 * 从根出发，先遍历所有左节点（斜线路径），再遍历隔壁排直到遍历全部节点。<br>
	 * <br>
	 * 乍一看嵌套两个循环应该是O(n^2)，但是实际上每个节点都只有被push操作一次，也就是其实运行时间还是O(n)，就系数来看，其实还比递归快。
	 */
	public void traversePreNR2();

	/**
	 * 递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePre();

	/**
	 * 使用迭代而非递归<br>
	 * 中序遍历（左-中-右）<br>
	 * 每个左侧节点就是一条链，由最左下的节点开始遍历右子树。 <br>
	 * <br>
	 * 乍一看嵌套两个循环应该是O(n^2)，但是实际上每个节点都只有被push操作一次，也就是其实运行时间还是O(n)，就系数来看，其实还比递归快。
	 * 
	 */
	public void traverseInNR();

	/**
	 * 递归<br>
	 * 中序遍历（左-中-右）
	 */
	public void traverseIn();

	/**
	 * 返回当前节点在中序意义下的直接后继。
	 * 
	 * @param node
	 *            当前节点
	 * @return 后继节点
	 */
	public Node<E> succ(Node<E> node);

	/**
	 * 使用迭代而非递归<br>
	 * 后序遍历（左-右-中）<br>
	 * 先找到最左下的节点，检查是否有右子树，如果有也要用前面的方法继续找直到没有右子树为止。
	 */
	public void traversePostNR1();

	/**
	 * 使用迭代而非递归<br>
	 * 后序遍历（左-右-中）<br>
	 * 双栈法
	 */
	public void traversePostNR2();

	/**
	 * 递归<br>
	 * 后序遍历（左-右-中）
	 */
	public void traversePost();
}
