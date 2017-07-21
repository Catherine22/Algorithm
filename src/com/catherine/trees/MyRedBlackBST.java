package com.catherine.trees;

import com.catherine.trees.nodes.Node;

public class MyRedBlackBST<E> implements BinaryTree<E>, RedBlackBST<E> {
	private RedBlackBSTImpl<E> rbTree;

	public MyRedBlackBST(int key, E root) {
		rbTree = new RedBlackBSTImpl<E>(key, root);
	}

	@Override
	public Node<E> search(int key) {
		return rbTree.search(key);
	}

	@Override
	public Node<E> insert(int key, E data) {
		return rbTree.insert(key, data);
	}

	@Override
	public void remove(int key) {
		rbTree.remove(key);
	}

	@Override
	public void solveDoubleRed(Node<E> node) {
		rbTree.solveDoubleRed(node);
	}

	@Override
	public void solveDoubleBlack(Node<E> node) {
		rbTree.solveDoubleBlack(node);
	}

	@Override
	public void updateHeight(Node<E> node) {
		rbTree.updateHeight(node);
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
		return rbTree.getHeight();
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
	public void traverseLevel() {
		rbTree.traverseLevel();
	}

	@Override
	public void traversePreNR1() {
		rbTree.traversePreNR1();

	}

	@Override
	public void traversePreNR2() {
		rbTree.traversePreNR2();

	}

	@Override
	public void traversePre() {
		rbTree.traversePre();

	}

	@Override
	public void traverseInNR() {
		rbTree.traverseInNR();
	}

	@Override
	public void traverseIn() {
		rbTree.traverseIn();

	}

	@Override
	public Node<E> succ(Node<E> node) {
		return rbTree.succ(node);
	}

	@Override
	public void traversePostNR1() {
		rbTree.traversePostNR1();
	}

	@Override
	public void traversePostNR2() {
		rbTree.traversePostNR2();
	}

	@Override
	public void traversePost() {
		rbTree.traversePost();
	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}

}
