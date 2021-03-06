package com.catherine.trees;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import com.catherine.trees.MyBinaryTree.Order;
import com.catherine.trees.nodes.Node;

public interface BinaryTree<E> {

	/**
	 * 返回根节点
	 * 
	 * @return 根节点
	 */
	public Node<E> getRoot();

	/**
	 * 是否为空树（没有节点）
	 * 
	 * @return boolean
	 */
	public boolean isEmpty();

	/**
	 * 子树规模
	 * 
	 * @return 子节点数
	 */
	public int size();

	/**
	 * 当前节点的子树规模
	 * 
	 * @param 指定节点
	 * @return 子节点数
	 */
	public int size(Node<E> node);

	/**
	 * 移除全部节点
	 */
	public void clear();

	/**
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：-1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * 
	 * @return 高度
	 */
	public int getHeight();

	/**
	 * 满二叉树（full binary tree）、真二叉树（proper binary tree）又称为严格二叉树（strictly binary
	 * tree），每个节点都只有0或2个节点。
	 * 
	 * 用{@link #traverseLevel()}
	 * 
	 * @return
	 */
	public boolean isFull();

	/**
	 * 移除整个右子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeRCCompletely(Node<E> parent);

	/**
	 * 移除整个左子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeLCCompletely(Node<E> parent);

	/**
	 * 遍历
	 * 
	 * @param order
	 *            阶层、先序、中序、后序
	 */
	public Collection<E> traversal(Order order);

	/**
	 * 以阶层遍历
	 */
	public Collection<E> traverseLevel();

	/**
	 * 先序遍历（中-左-右）
	 */
	public Collection<E> traversePre();

	/**
	 * 中序遍历（左-中-右）
	 */
	public Collection<E> traverseIn();

	/**
	 * 后序遍历（左-右-中）<br>
	 */
	public Collection<E> traversePost();

	/**
	 * 递归<br>
	 * 从任意节点开始先序遍历（中-左-右）
	 */
	public void traversePre(Node<E> node);

	/**
	 * 递归<br>
	 * 从任意节点开始中序遍历（左-中-右）
	 */
	public void traverseIn(Node<E> node);

	/**
	 * 递归<br>
	 * 从任意节点开始后序遍历（左-右-中）<br>
	 */
	public void traversePost(Node<E> node);

	/**
	 * 返回当前节点在中序意义下的直接后继。
	 * 
	 * @param node
	 *            当前节点
	 * @return 后继节点
	 */
	public Node<E> succ(Node<E> node);

	/**
	 * 这边以中序遍历的方式转成数组<br>
	 * 
	 * @param anArray
	 */
	public void copyInto(Object[] anArray);

	/**
	 * 拷贝此树并回传副本，两次阶层走访填充clone
	 * 
	 * @return 副本
	 */
	public Object clone();

	/**
	 * 这边以中序遍历的方式转成数组<br>
	 * 
	 * @return
	 */
	public Object[] toArray();

	/**
	 * 自定义走访方式转成数组
	 * 
	 * @param order
	 * @return
	 */
	public Object[] toArray(Order order);

	/**
	 * 这边以中序遍历的方式转成数组<br>
	 * 
	 * @return
	 */
	public E[] toArray(E[] a);

	/**
	 * 自定义走访方式转成数组数组<br>
	 * 
	 * @param a
	 * @param order
	 * @return
	 */
	public E[] toArray(E[] a, Order order);

	/**
	 * 这边以中序遍历的方式转成list<br>
	 * 
	 * @return
	 */
	public List<E> subList(int fromIndex, int toIndex);

	/**
	 * 这边以自定义遍历的方式转成list<br>
	 * 
	 * @return
	 */
	public List<E> subList(int fromIndex, int toIndex, Order order);

	// TODO try to listIterator binary tree with binary tree iterator
	public ListIterator<E> listIterator();

	// TODO try to listIterator binary tree with binary tree iterator
	public ListIterator<E> listIterator(int index);

	// TODO try to iterator binary tree with binary tree iterator
	public Iterator<E> iterator();

	// TODO try to spliterator binary tree with binary tree iterator
	public Spliterator<E> spliterator();

	// TODO try to listIterator binary tree with binary tree iterator
	public ListIterator<E> listIterator(Order order);

	// TODO try to listIterator binary tree with binary tree iterator
	public ListIterator<E> listIterator(int index, Order order);

	// TODO try to iterator binary tree with binary tree iterator
	public Iterator<E> iterator(Order order);

	// TODO try to spliterator binary tree with binary tree iterator
	public Spliterator<E> spliterator(Order order);
}
