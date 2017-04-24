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
	/**
	 * 一旦一个节点平衡因子不符合，直接停止递归
	 */
	private boolean stopCheckAVL;
	/**
	 * 符合的节点数=全部节点数
	 */
	private int counter;

	public MyAVLTree(int key, E root) {
		super(key, root);
	}

	public interface Callback {
		void onResponse(boolean result);
	}

	/**
	 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
	 * 
	 * @param callback
	 */
	public void isAVLTree(final Callback callback) {
		counter = size;
		isAVLTree(root, new Callback() {
			@Override
			public void onResponse(boolean result) {
				counter--;
				if (!result) {
					stopCheckAVL = true;
					callback.onResponse(false);
				}

				if (counter == 0 && !stopCheckAVL)
					callback.onResponse(true);
			}
		});
	}

	/**
	 * 递归<br>
	 * 从任一节点中序遍历（左-中-右）
	 */
	private void isAVLTree(Node<E> node, Callback callback) {
		if (!stopCheckAVL) {
			if (node.getlChild() != null)
				isAVLTree(node.getlChild(), callback);
			if (Math.abs(getBalanceFactor(node)) > 1) {
				// System.out.println(node.getInfo());
				callback.onResponse(false);
			} else {
				// System.out.println(node.getInfo());
				callback.onResponse(true);
			}
			if (node.getrChild() != null)
				isAVLTree(node.getrChild(), callback);
		}
	}

	/**
	 * 取得各节点的平衡因子（左子树高度-右子树高度）
	 * 
	 * @param node
	 * @return
	 */
	private int getBalanceFactor(Node<E> node) {
		int lHeight = (node.getlChild() == null) ? -1 : node.getlChild().getHeight();
		int rHeight = (node.getrChild() == null) ? -1 : node.getrChild().getHeight();
		return lHeight - rHeight;
	}
}
