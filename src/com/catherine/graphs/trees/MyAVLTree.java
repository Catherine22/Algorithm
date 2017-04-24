package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

/**
 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyAVLTree<E> extends MyBinarySearchTree<E> {

	public MyAVLTree(int key, E root) {
		super(key, root);
	}

	/**
	 * AVL Tree一定是适度平衡
	 */
	public boolean isBBST() {
		return true;
	}

	@Override
	public Node<E> insert(int key, E data) {
		return null;
	}

	@Override
	public void remove(int key) {

	}
}
