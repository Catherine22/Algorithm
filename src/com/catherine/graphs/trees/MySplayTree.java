package com.catherine.graphs.trees;

import com.catherine.graphs.trees.nodes.Node;

/**
 * 
 * 当被访问过的节点很可能再度被访问时，特别适用伸展树 <br>
 * 定义： <br>
 * 节点一旦访问过就移到树根（变成根节点）
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
	protected void splay(Node<E> node) {
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
	 * 参照 {@link #search(int)}，完成搜寻后就把该节点移到树根，大幅减少下次搜寻相同节点的时间。
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	@Override
	public Node<E> search(int key) {
		Node<E> result = spTree.search(key);
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
