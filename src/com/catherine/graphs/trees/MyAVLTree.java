package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

/**
 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyAVLTree<E> extends MyBinarySearchTreeKernel<E> {

	public MyAVLTree(int key, E root) {
		super(key, root);
	}

	/**
	 * AVL Tree一定是适度平衡
	 */
	public boolean isBBST() {
		return true;
	}

	/**
	 * 所有祖先节点都會失衡
	 */
	@Override
	public Node<E> insert(int key, E data) {
		return null;
	}

	/**
	 * 只有一个父节点会失衡
	 */
	@Override
	public void remove(int key) {

	}
	/**
	 * 围绕node向右旋转，视情况双旋（为了平衡）
	 */
	@Override
	public void zig(Node<E> node) {
		super.zig(node);
	}
	/**
	 * 围绕node向左旋转，视情况双旋（为了平衡）
	 */
	@Override
	public void zag(Node<E> node) {
		super.zag(node);
	}
}
