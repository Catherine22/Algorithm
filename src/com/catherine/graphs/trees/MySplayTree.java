package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

/**
 * 
 * 当被访问过的节点很可能再度被访问时，特别适用伸展树 <br>
 * 定义：节点一旦访问过就移到树根（变成根节点） <br>
 * 做坏情况：假设有个伸展树树根为1，子树节点为2～5，整棵树看起来是一撇，做了从5～1的访问后，伸展树又回到原始的状态（也就是这样的情况可能不断循环）。
 * 
 * 
 * @author Catherine
 */
public class MySplayTree<E> implements BinarySearchTree<E>, BinaryTree<E> {

	private BinarySearchTreeImpl<E> spTree;

	public MySplayTree(int key, E root) {
		spTree = new BinarySearchTreeImpl<E>(key, root);
	}

	/**
	 * 将节点通过反复单旋的方式逐步移动到根节点。
	 * 
	 * @param node
	 */
	public void splay(Node<E> node) {
		if (node == spTree.getRoot())
			return;

		if (spTree.isLeftChild(node))
			zig(node.getParent());
		else
			zag(node.getParent());
		// 递归
		splay(node);
	}

	/**
	 * 双层旋转，转到祖父节点。一次考察两节点，父节点和祖父节点。<br>
	 * 一共有四种情况：<br>
	 * 1. grandparent - parent(L) - target(L)：两次{@link #zig(Node)}<br>
	 * 2. grandparent - parent(R) - target(R)：两次{@link #zag(Node)}<br>
	 * 3. grandparent - parent(R) - target(L)：{@link #zig(Node)} +
	 * {@link #zag(Node)}<br>
	 * 4. grandparent - parent(L) - target(R)：{@link #zag(Node)}+
	 * {@link #zig(Node)}<br>
	 * 
	 * @param node
	 */
	public void splayEfficiently(Node<E> node) {
		Node<E> parent = node.getParent();
		if (parent == null)
			return;

		Node<E> grandparent = parent.getParent();
		if (grandparent == null)
			splay(node);
		else {
			if (spTree.isLeftChild(node)) {
				// L-L
				if (spTree.isLeftChild(parent)) {
					zig(grandparent);
					zig(parent);
				}
				// >
				else {
					zig(parent);
					zag(grandparent);
				}
			} else {
				// R-R
				if (spTree.isRightChild(parent)) {
					zag(grandparent);
					zag(parent);
				}
				// <
				else {
					zag(parent);
					zig(grandparent);
				}
			}
			splayEfficiently(node);
		}
	}

	/**
	 * 完成搜寻后就把该节点移到树根，大幅减少下次搜寻相同节点的时间。
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	@Override
	public Node<E> search(int key) {
		return search(key, true);
	}

	/**
	 * 同{@link #search(int)}
	 * 
	 * @param key
	 * @param efficient
	 *            用双层旋转或是每层旋转
	 * @return
	 */
	public Node<E> search(int key, boolean efficient) {
		Node<E> result = spTree.search(key);
		if (efficient)
			splayEfficiently(result);
		else
			splay(result);
		return result;
	}

	@Override
	public Node<E> getRoot() {
		return spTree.getRoot();
	}

	@Override
	public boolean isEmpty() {
		return spTree.isEmpty();
	}

	@Override
	public int size() {
		return spTree.size();
	}

	@Override
	public int size(Node<E> node) {
		return spTree.size(node);
	}

	@Override
	public int getHeight() {
		return spTree.getHeight();
	}

	@Override
	public boolean isFull() {
		return spTree.isFull();
	}

	@Override
	public void removeRCCompletely(Node<E> parent) {
		spTree.removeRCCompletely(parent);
	}

	@Override
	public void removeLCCompletely(Node<E> parent) {
		spTree.removeLCCompletely(parent);
	}

	@Override
	public void traverseLevel() {
		spTree.traverseLevel();
	}

	@Override
	public void traversePreNR1() {
		spTree.traversePreNR1();
	}

	@Override
	public void traversePreNR2() {
		spTree.traversePreNR2();

	}

	@Override
	public void traversePre() {
		spTree.traversePre();

	}

	@Override
	public void traverseInNR() {
		spTree.traverseInNR();

	}

	@Override
	public void traverseIn() {
		spTree.traverseIn();

	}

	@Override
	public Node<E> succ(Node<E> node) {
		return spTree.succ(node);
	}

	@Override
	public void traversePostNR1() {
		spTree.traversePostNR1();

	}

	@Override
	public void traversePostNR2() {
		spTree.traversePostNR2();

	}

	@Override
	public void traversePost() {
		spTree.traversePost();

	}

	@Override
	public Node<E> insert(int key, E data) {
		return spTree.insert(key, data);
	}

	@Override
	public void remove(int key) {
		spTree.remove(key);
	}

	@Override
	public int countRandomTrees(int size) {
		return spTree.countRandomTrees(size);
	}

	@Override
	public boolean isBBST() {
		return spTree.isBBST();
	}

	@Override
	public void zig(Node<E> node) {
		spTree.zig(node);

	}

	@Override
	public void zag(Node<E> node) {
		spTree.zag(node);

	}

	@Override
	public void left_rightRotate(Node<E> node) {
		spTree.left_rightRotate(node);

	}

	@Override
	public void right_leftRotate(Node<E> node) {
		spTree.right_leftRotate(node);

	}

	@Override
	public void isAVLTree(Callback callback) {
		spTree.isAVLTree(callback);

	}

}
