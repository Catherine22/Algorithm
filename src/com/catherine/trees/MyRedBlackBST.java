package com.catherine.trees;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import com.catherine.trees.MyBinaryTree.Order;
import com.catherine.trees.nodes.Node;

public class MyRedBlackBST<E extends Comparable<? super E>> implements BinaryTree<E>, RedBlackBST<E> {
	private RedBlackBSTImpl<E> rbTree;

	public MyRedBlackBST(E root) {
		rbTree = new RedBlackBSTImpl<E>(root);
	}

	@Override
	public Node<E> search(E data) {
		return rbTree.search(data);
	}

	@Override
	public Node<E> add(E data) {
		return rbTree.insertAndBalance(data);
	}

	@Override
	public void remove(E data) {
		rbTree.removeAndBalance(data);
	}

	@Override
	public Node<E> getRoot() {
		return rbTree.getRoot();
	}

	@Override
	public boolean isEmpty() {
		return rbTree.isEmpty();
	}

	@Override
	public int size() {
		return rbTree.size();
	}

	@Override
	public int size(Node<E> node) {
		return rbTree.size(node);
	}

	@Override
	public int getHeight() {
		return rbTree.getBHeight();
	}

	@Override
	public boolean isFull() {
		return rbTree.isFull();
	}

	@Override
	public void removeRCCompletely(Node<E> parent) {
		rbTree.removeRCCompletely(parent);
	}

	@Override
	public void removeLCCompletely(Node<E> parent) {
		rbTree.removeLCCompletely(parent);
	}

	@Override
	public void traversal(Order order) {
		rbTree.traversal(order);
	}

	@Override
	public void traverseLevel() {
		rbTree.traverseLevel();
	}

	@Override
	public void traversePre() {
		rbTree.traversePre();

	}

	@Override
	public void traverseIn() {
		rbTree.traverseIn();

	}

	@Override
	public void traversePost() {
		rbTree.traversePost();
	}

	@Override
	public void traversePre(Node<E> node) {
		rbTree.traversePre(node);
	}

	@Override
	public void traverseIn(Node<E> node) {
		rbTree.traverseIn(node);
	}

	@Override
	public void traversePost(Node<E> node) {
		rbTree.traversePost(node);
	}

	@Override
	public Node<E> succ(Node<E> node) {
		return rbTree.succ(node);
	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}

	@Override
	public void clear() {
		rbTree.clear();
	}

	@Override
	public void copyInto(Object[] anArray) {
		rbTree.copyInto(anArray);
	}

	@Override
	public Object clone() {
		return rbTree.clone();
	}

	@Override
	public Object[] toArray() {
		return rbTree.toArray();
	}

	@Override
	public E[] toArray(E[] e) {
		return rbTree.toArray(e);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return rbTree.subList(fromIndex, toIndex);
	}

	@Override
	public ListIterator<E> listIterator() {
		return rbTree.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return rbTree.listIterator(index);
	}

	@Override
	public Iterator<E> iterator() {
		return rbTree.iterator();
	}

	public Spliterator<E> spliterator() {
		return rbTree.spliterator();
	}
}
