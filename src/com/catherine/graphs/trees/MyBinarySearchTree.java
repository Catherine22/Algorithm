package com.catherine.graphs.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.catherine.graphs.trees.nodes.Node;

/**
 * {@link com.catherine.graphs.trees.MyBinarySearchTreeKernel}继承
 * {@link com.catherine.graphs.trees.MyBinaryTree}<br>
 * 有一些从MyBinaryTree公开的方法不适用二叉搜索树（
 * 在MyBinarySearchTreeKernel内搜寻UnsupportedOperationException），所以另外定义一个客户端接口
 * 
 * @author Catherine
 *
 */
public class MyBinarySearchTree<E> implements BinaryTree<E>, BinarySearchTree<E> {

	private MyBinarySearchTreeKernel<E> bst;

	public MyBinarySearchTree(int key, E root) {
		bst = new MyBinarySearchTreeKernel<E>(key, root);
	}

	@Override
	public Node<E> getRoot() {
		return bst.getRoot();
	}

	@Override
	public boolean isEmpty() {
		return bst.isEmpty();
	}

	@Override
	public int size() {
		return bst.size;
	}

	@Override
	public int size(Node<E> node) {
		return bst.size(node);
	}

	@Override
	public int getHeight() {
		return bst.getHeight();
	}

	@Override
	public boolean isFull() {
		return bst.isFull();
	}

	@Override
	public void removeRCCompletely(Node<E> parent) {
		bst.removeRCCompletely(parent);
	}

	@Override
	public void removeLCCompletely(Node<E> parent) {
		bst.removeLCCompletely(parent);
	}

	@Override
	public void traverseLevel() {
		bst.traverseLevel();
	}

	@Override
	public void traversePreNR1() {
		bst.traversePreNR1();
	}

	@Override
	public void traversePreNR2() {
		bst.traversePreNR2();
	}

	@Override
	public void traversePre() {
		bst.traversePre();
	}

	@Override
	public void traverseInNR() {
		bst.traverseInNR();
	}

	@Override
	public void traverseIn() {
		bst.traverseIn();
	}

	@Override
	public Node<E> succ(Node<E> node) {
		return bst.succ(node);
	}

	@Override
	public void traversePostNR1() {
		bst.traversePostNR1();
	}

	@Override
	public void traversePostNR2() {
		bst.traversePostNR2();
	}

	@Override
	public void traversePost() {
		bst.traversePost();
	}

	@Override
	public Node<E> search(int key) {
		return bst.search(key);
	}

	@Override
	public Node<E> insert(int key, E data) {
		return bst.insert(key, data);
	}

	@Override
	public void remove(int key) {
		bst.remove(key);
	}

	/**
	 * 不公平的随机生成二叉搜寻树<br>
	 * 取任意不重复的n个数产生的排列组合应为n!，生成的树平均高度为log n；<br>
	 * 但实际上这些树产生的树的组合只有卡塔兰数——catalan(n)个，生成的树平均高度为开根号n<br>
	 * 比如取123三个数，在213和231的组合时，产生的二叉搜寻树都是一样的。
	 */
	public static BinarySearchTree<Object> random(int size) {
		BinarySearchTree<Object> newBST = null;
		List<Integer> sequence = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sequence.add(i + 1);
		}
		// 产生乱数序列
		Collections.shuffle(sequence);
		System.out.print(sequence.get(0) + " ");
		newBST = new MyBinarySearchTree<Object>(sequence.get(0), null);
		for (int i = 1; i < size; i++) {
			System.out.print(sequence.get(i) + " ");
			newBST.insert(sequence.get(i), null);
		}
		System.out.print("\n");

		return newBST;
	}

	@Override
	public int countRandomTrees(int size) {
		return bst.countRandomTrees(size);
	}

	@Override
	public boolean isBBST() {
		return bst.isBBST();
	}

	@Override
	public void balance() {
		bst.balance();
	}

	@Override
	public void zig(Node<E> node) {
		bst.zig(node);
	}

	@Override
	public void zag(Node<E> node) {
		bst.zag(node);
	}

	@Override
	public void isAVLTree(Callback callback) {
		bst.isAVLTree(callback);
	}
	@Override
	public void left_rightRotate(Node<E> node){
		bst.left_rightRotate(node);
	}
	
	@Override
	public void right_leftRotate(Node<E> node){
		bst.right_leftRotate(node);
	}
}
