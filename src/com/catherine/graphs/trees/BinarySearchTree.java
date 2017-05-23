package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

public interface BinarySearchTree<E> {

	/**
	 * 由于所有节点的垂直投影就是该树的中序遍历，搜寻时用{@link com.catherine.getData()_type.Search}
	 * 的binSearch概念。<br>
	 * 无论成功与否，都是指向命中节点。（没找到时，在{@link #hot}建立一个子节点作为哨兵，整棵树仍然是BST<br>
	 * <br>
	 * 每比较一次，节点的深度都会下降一层，也就是递归深度不超过树高。<br>
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	public Node<E> search(int key);

	/**
	 * 先做一次遍历，得到hot，用hot作为父节点插入。 暂不考虑重复数值情况。
	 * 
	 * @param key
	 *            插入节点的key
	 * @param data
	 *            插入节点的value
	 */
	public Node<E> insert(int key, E data);

	/**
	 * 情况1:欲移除节点只有一个左孩子或右孩子，移除节点后孩子上位，取代原节点。<br>
	 * 情况2:欲移除节点有左右孩子。化繁为简，变成情况1再处理<br>
	 * 情况2先找出目标节点的后继节点{@link #succ(Node)}，两节点交换后就变成情况1（顶多只会有右孩子，因为后继已经是最左边的节点了），
	 * 比照情况1处理，这边还要再将欲移除节点的父节点设为hot，重新整理树高。
	 * 
	 * @param key
	 */
	public void remove(int key);

	/**
	 * 随机生成产生的树的组合只有卡塔兰数个
	 * 
	 * @param size
	 * @return
	 */
	public int countRandomTrees(int size);

	/**
	 * 左右子树的高度越接近（越平衡），全树的高度也通常越低。<br>
	 * 由n个节点组成的二叉树，高度不低于base2的log n，恰为log n时为理想平衡，出现在满二叉树。<br>
	 * 实际应用中理想平衡太严苛，会放松平衡的标准，只要能确保树的高度不超过base10的log n，就是适度平衡。<br>
	 * 此时的树称为BBST，平衡二叉搜索树。
	 * 
	 * @return
	 */
	public boolean isBBST();

	/**
	 * node和node的左孩子向右旋转，没有左孩子则报错
	 */
	public void zig(Node<E> node);

	/**
	 * node和node的右孩子向左旋转，没有右孩子则报错
	 */
	public void zag(Node<E> node);

	/**
	 * node-lChild-lrChild 看起来像"<"
	 */
	public void left_rightRotate(Node<E> node);

	/**
	 * node-rChild-rlChild 看起来像">"
	 */
	public void right_leftRotate(Node<E> node);

	/**
	 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
	 * 
	 * @param callback
	 */
	public void isAVLTree(final Callback callback);
}
