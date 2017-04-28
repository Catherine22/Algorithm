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
	 * 插入或移除节点造成AVL Tree失衡<br>
	 * 插入情形：<br>
	 * 1. 插入节点的父节点往上推，祖孙三代都是同方向，只需旋转祖父节点可达到平衡（包含祖父以上节点）<br>
	 * 2. A（祖父） - B（父，左子树） — C （右子树），插入节点位于C，此时须双旋，变成C（父） — A 和 B<br>
	 * 
	 * @param ins
	 */
	public void balance(final Node<E> node) {
		if (node.getParent() != null || node.getParent().getParent() != null
				|| node.getParent().getParent().getParent() != null) {
			int count = 3;// 祖孙三代
			int left = 0;
			int right = 0;

			Node<E> target = node;
			while (count > 0) {
				count--;
				if (isLeftChild(target))
					left++;
				else
					right++;
				target = target.getParent();
			}

			// 情况1，单旋
			if (right == 3)
				zag(node.getParent().getParent().getParent());
			else if (left == 3)
				zig(node.getParent().getParent().getParent());

			// 情况2，双旋
			else if (right == 2 && left == 1) {
				target = node.getParent();
				zag(target);
				zig(target);
			} else if (right == 1 && left == 2) {
				target = node.getParent();
				zig(target);
				zag(target);
			}
		}

	}

	/**
	 * 只有一个父节点会失衡
	 */
	@Override
	public void remove(int key) {

	}

}
