package com.catherine.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import com.catherine.trees.MyBinaryTree.Order;
import com.catherine.trees.nodes.Node;

/**
 * {@link com.catherine.trees.BinarySearchTreeImpl}继承
 * {@link com.catherine.trees.MyBinaryTree}<br>
 * 有一些从MyBinaryTree公开的方法不适用二叉搜索树（
 * 在BinarySearchTreeImpl内搜寻UnsupportedOperationException），所以另外定义一个客户端接口
 * 
 * @author Catherine
 *
 */
public class MyBinarySearchTree<E extends Comparable<? super E>> implements BinaryTree<E>, BinarySearchTree<E> {

	private BinarySearchTreeImpl<E> bst;

	public MyBinarySearchTree(E root) {
		bst = new BinarySearchTreeImpl<>(root);
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
	public void traversal(Order order) {
		bst.traversal(order);
	}

	@Override
	public void traverseLevel() {
		bst.traverseLevel();
	}

	@Override
	public void traversePre() {
		bst.traversePre();
	}

	@Override
	public void traverseIn() {
		bst.traverseIn();
	}

	@Override
	public void traversePost() {
		bst.traversePost();
	}

	@Override
	public void traversePre(Node<E> node) {
		bst.traversePre(node);
	}

	@Override
	public void traverseIn(Node<E> node) {
		bst.traverseIn(node);
	}

	@Override
	public void traversePost(Node<E> node) {
		bst.traversePost(node);
	}

	@Override
	public Node<E> succ(Node<E> node) {
		return bst.succ(node);
	}

	@Override
	public Node<E> search(E data) {
		return bst.search(data);
	}

	@Override
	public Node<E> searchLast(E data) {
		return bst.searchLast(data);
	}

	@Override
	public Node<E> add(E data) {
		return bst.add(data);
	}

	@Override
	public void remove(E data) {
		bst.remove(data);
	}

	/**
	 * 不公平的随机生成二叉搜寻树<br>
	 * 取任意不重复的n个数产生的排列组合应为n!，生成的树平均高度为log n；<br>
	 * 但实际上这些树产生的树的组合只有卡塔兰数——catalan(n)个，生成的树平均高度为开根号n<br>
	 * 比如取123三个数，在213和231的组合时，产生的二叉搜寻树都是一样的。
	 */
	public static BinarySearchTree<Integer> random(int size, int from, int to) {
		if (size <= 0)
			throw new IllegalArgumentException("Size must be more than 0");
		if (to <= from)
			throw new IllegalArgumentException("Error range");

		BinarySearchTree<Integer> newBST = null;
		List<Integer> sequence = new ArrayList<>();
		for (int i = from; i < to; i++) {
			sequence.add(i);
		}
		// 产生乱数序列
		Collections.shuffle(sequence);
		System.out.print("Random BST -> " + sequence.get(0) + " ");
		newBST = new MyBinarySearchTree<Integer>(sequence.get(0));
		for (int i = 1; i < size; i++) {
			System.out.print(sequence.get(i) + " ");
			newBST.add(sequence.get(i));
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
	public void left_rightRotate(Node<E> node) {
		bst.left_rightRotate(node);
	}

	@Override
	public void right_leftRotate(Node<E> node) {
		bst.right_leftRotate(node);
	}

	@Override
	public void clear() {
		bst.clear();
	}

	@Override
	public void copyInto(Object[] anArray) {
		bst.copyInto(anArray);
	}

	@Override
	public Object clone() {
		return bst.clone();
	}

	@Override
	public Object[] toArray() {
		return bst.toArray();
	}

	@Override
	public E[] toArray(E[] e) {
		return bst.toArray(e);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return bst.subList(fromIndex, toIndex);
	}

	@Override
	public ListIterator<E> listIterator() {
		return bst.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return bst.listIterator(index);
	}

	@Override
	public Iterator<E> iterator() {
		return bst.iterator();
	}

	public Spliterator<E> spliterator() {
		return bst.spliterator();
	}

	@Override
	public Object[] toArray(Order order) {
		return bst.toArray(order);
	}

	@Override
	public E[] toArray(E[] a, Order order) {
		return bst.toArray(a, order);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex, Order order) {
		return bst.subList(fromIndex, toIndex, order);
	}

	@Override
	public ListIterator<E> listIterator(Order order) {
		return bst.listIterator(order);
	}

	@Override
	public ListIterator<E> listIterator(int index, Order order) {
		return bst.listIterator(index, order);
	}

	@Override
	public Iterator<E> iterator(Order order) {
		return bst.iterator(order);
	}

	@Override
	public Spliterator<E> spliterator(Order order) {
		return bst.spliterator(order);
	}
}
