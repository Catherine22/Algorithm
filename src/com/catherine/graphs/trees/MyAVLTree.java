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
	@Override
	public boolean isBBST() {
		return true;
	}

	/**
	 * 所有祖先节点都會失衡<br>
	 */
	@Override
	public Node<E> insert(int key, E data) {
		return super.insert(key, data);
	}

	/**
	 * 
	 * 1. 插入节点的父节点往上推，祖孙三代都是同方向，只需旋转祖父节点可达到平衡（包含祖父以上节点）<br>
	 * 
	 * @param ins
	 */
	public void balance(final Node<E> node) {
		if (node.getParent() != null || node.getParent().getParent() != null
				|| node.getParent().getParent().getParent() != null) {
			int count = 3;// 祖孙三代
			int left = 0;
			int right = 0;

			while (count > 0) {
				count--;
				if (isLeftChild(node))
					left++;
				else
					right++;
			}
			// 情况1
			if (right == 3)
				zig(node.getParent().getParent().getParent());
			else if (left == 3)
				zag(node.getParent().getParent().getParent());
		}

	}

	/**
	 * 只有一个父节点会失衡
	 */
	@Override
	public void remove(int key) {

	}

}
