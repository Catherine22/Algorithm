package com.catherine.trees.nodes;

/**
 * Red-black BST
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class RedBlackBSTNode<E> extends BSTNode<E> {

	private boolean isBlack;// false: red

	public RedBlackBSTNode(int key, E data, BSTNode<E> parent, BSTNode<E> lChild, BSTNode<E> rChild, int height,
			int depth) {
		super(key, data, parent, lChild, rChild, height, depth);
	}

	public boolean isBlack() {
		return isBlack;
	}

	public boolean isRed() {
		return !isBlack;
	}

	public void setColor(boolean isRed) {
		isBlack = !isRed;
	}

}
