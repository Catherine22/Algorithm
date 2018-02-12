package com.catherine.trees;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import com.catherine.trees.MyBinaryTree.Order;
import com.catherine.trees.nodes.Node;

/**
 * 
 * 当被访问过的节点很可能再度被访问时，特别适用伸展树 <br>
 * 定义：节点一旦访问过就移到树根（变成根节点） <br>
 * 不需要记录和维护节点高度。出于效率的考虑，实际应用中可视情况，省略这类更新。<br>
 * 最坏情况：假设有个伸展树树根为1，子树节点为2～5，整棵树看起来是一撇，做了从5～1的访问后，伸展树又回到原始的状态（也就是这样的情况可能不断循环）。
 * 
 * 
 * @author Catherine
 */
public class MySplayTree<E extends Comparable<? super E>> implements BinarySearchTree<E>, BinaryTree<E> {

	private BinarySearchTreeImpl<E> spTree;

	public MySplayTree(E root) {
		spTree = new BinarySearchTreeImpl<E>(root);
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
	 * 双层旋转，转到祖父节点。一次考察两节点，父节点和祖父节点。用此方法避免“最坏情况”，在分摊的意义下提高整体效率。<br>
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
	 * 完成搜寻后就把该节点移到树根，大幅减少下次搜寻相同节点的时间。 与BST不同的是，无论找到与否，根节点都会变成最后被访问的节点。
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	@Override
	public Node<E> search(E data) {
		return search(data, true);
	}

	@Override
	public Node<E> searchLast(E data) {
		return searchLast(data, true);
	}

	/**
	 * 同{@link #search(data)}
	 * 
	 * @param key
	 * @param efficient
	 *            用双层旋转或是每层旋转
	 * @return
	 */
	public Node<E> search(E data, boolean efficient) {
		Node<E> result = spTree.search(data);
		if (efficient) {
			if (result == null)
				splayEfficiently(spTree.hot);// 找不到就让最后访问的节点变成根节点
			else
				splayEfficiently(result);
		} else {
			if (result == null)
				splay(spTree.hot);// 找不到就让最后访问的节点变成根节点
			else
				splay(result);
		}
		return result;
	}

	/**
	 * 同{@link #searchLast(data)}
	 * 
	 * @param key
	 * @param efficient
	 *            用双层旋转或是每层旋转
	 * @return
	 */
	public Node<E> searchLast(E data, boolean efficient) {
		return null;
	}

	/**
	 * 理论上是做一次BST的插入，再将新插入节点旋转至根节点。<br>
	 * 在此有个便捷做法，先运行BST的插入操作，再用SplayTree（{@link #search(int)}）的搜寻操作 （因为
	 * {@link #search(int)}已经集成{@link #splayEfficiently(Node)}）。
	 */
	@Override
	public Node<E> add(E data) {
		return search(spTree.add(data).getData());
	}

	/**
	 * 理论上是做一次BST的删除，再将删除节点的父节点旋转至根节点。<br>
	 * 新运行BST的删除操作，再用SplayTree（{@link #search(int)}）的搜寻操作 （因为
	 * {@link #search(int)}已经集成{@link #splayEfficiently(Node)}）。
	 */
	@Override
	public void remove(E data) {
		spTree.remove(data);
		search(data);
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

	public void traversal(Order order) {
		spTree.traversal(order);
	}

	@Override
	public void traverseLevel() {
		spTree.traverseLevel();
	}

	@Override
	public void traversePre() {
		spTree.traversePre();

	}

	@Override
	public void traverseIn() {
		spTree.traverseIn();

	}

	@Override
	public void traversePost() {
		spTree.traversePost();

	}

	@Override
	public void traversePre(Node<E> node) {
		spTree.traversePre(node);
	}

	@Override
	public void traverseIn(Node<E> node) {
		spTree.traverseIn(node);
	}

	@Override
	public void traversePost(Node<E> node) {
		spTree.traversePost(node);
	}

	@Override
	public Node<E> succ(Node<E> node) {
		return spTree.succ(node);
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

	@Override
	public void clear() {
		spTree.clear();
	}

	@Override
	public void copyInto(Object[] anArray) {
		spTree.copyInto(anArray);
	}

	@Override
	public Object clone() {
		return spTree.clone();
	}

	@Override
	public Object[] toArray() {
		return spTree.toArray();
	}

	@Override
	public E[] toArray(E[] e) {
		return spTree.toArray(e);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return spTree.subList(fromIndex, toIndex);
	}

	@Override
	public ListIterator<E> listIterator() {
		return spTree.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return spTree.listIterator(index);
	}

	@Override
	public Iterator<E> iterator() {
		return spTree.iterator();
	}

	public Spliterator<E> spliterator() {
		return spTree.spliterator();
	}

	@Override
	public Object[] toArray(Order order) {
		return spTree.toArray(order);
	}

	@Override
	public E[] toArray(E[] a, Order order) {
		return spTree.toArray(a, order);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex, Order order) {
		return spTree.subList(fromIndex, toIndex, order);
	}

	@Override
	public ListIterator<E> listIterator(Order order) {
		return spTree.listIterator(order);
	}

	@Override
	public ListIterator<E> listIterator(int index, Order order) {
		return spTree.listIterator(index, order);
	}

	@Override
	public Iterator<E> iterator(Order order) {
		return spTree.iterator(order);
	}

	@Override
	public Spliterator<E> spliterator(Order order) {
		return spTree.spliterator(order);
	}
}
