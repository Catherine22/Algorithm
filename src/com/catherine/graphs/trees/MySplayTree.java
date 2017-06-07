package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

/**
 * 
 * 
 * 两种情况下特别适用伸展树：<br>
 * 1. 当被访问过的节点很可能再度被访问时 <br>
 * 2. 下个访问节点就在刚被访问过的节点附近 <br>
 * <br>
 * 定义： <br>
 * 节点一旦访问过就移到树根（变成根节点）
 * 
 * 
 * @author Catherine
 */
public class MySplayTree<E> extends MyBinarySearchTreeKernel<E> {
	
	public MySplayTree(int key, E root) {
		super(key, root);
	}

	/**
	 * 将节点通过反复单旋的方式逐步移动到根节点。
	 * 
	 * @param node
	 */
	public void splay(Node<E> node) {
		if (node == root)
			return;

		if (isLeftChild(node))
			zig(node.getParent());
		else
			zag(node.getParent());
		// 递归
		splay(node);
	}

	/**
	 * 参照 {@link #search(int)}，完成搜寻后就把该节点移到树根，大幅减少下次搜寻相同节点的时间。
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	@Override
	public Node<E> search(int key) {
		Node<E> result = super.search(key);
		splay(result);
		return result;
	}

}
