package com.catherine.data_type;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> extends AbstractSequentialList<E>
		implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

	transient int size = 0;
	/**
	 * 链表的第一个元素<br>
	 * 两原则：<br>
	 * 如果为null最后一个元素也是null，这是个null的链表<br>
	 * 前一位一定为null<br>
	 */
	transient Node<E> first;
	/**
	 * 链表的最后一个元素<br>
	 * 两原则：<br>
	 * 如果为null第一个元素也是null，这是个null的链表<br>
	 * 后一位一定为null<br>
	 */
	transient Node<E> last;

	public MyLinkedList() {
	}

	public MyLinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	/**
	 * 每一个Node都包含:<br>
	 * 1. item, 自己的元素值<br>
	 * 2. prev, 前一位的元素值<br>
	 * 3. next, 后一位的元素值<br>
	 * 
	 * @author Catherine
	 *
	 * @param <E>
	 */
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * 根据索引获取节点。<br>
	 * <br>
	 * 每个Node的prev／next指向上／下一个Node，因而串联起来成为链表，<br>
	 * 所以搜寻时必须由first或last开始一个接着一个的寻找。
	 * 
	 * @param index
	 *            索引
	 * @return 指定位置的Node<E>
	 */
	private Node<E> node(int index) {
		// 如果指定位置在链表前半段，从first开始找比较快。
		if (index < (size >> 1)) {
			Node<E> x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x;
		}

		// 如果指定位置在链表后半段，从last开始找比较快。
		else {
			Node<E> x = last;
			for (int i = size - 1; i > index; i--)
				x = x.prev;
			return x;
		}
	}

	/**
	 * 加入指定元素到链表第一位
	 * 
	 * @param element
	 *            指定元素
	 */
	@Override
	public void addFirst(E element) {
		linkFirst(element);
	}

	/**
	 * 
	 * 把原本第一位Node（null-item-next）挪到第二位Node（e-item-next），插入e作为第一位Node（null-e-item
	 * ）。 新的链表变成null-e-（旧的）first-...
	 * 
	 * @param element
	 */
	private void linkFirst(E element) {
		Node<E> second = first;
		Node<E> newFirst = new Node<>(null, element, second);
		first = newFirst;

		if (second == null)
			last = newFirst;
		else
			second.prev = newFirst;

		size++;
		modCount++;
	}

	/**
	 * 加入指定元素到链表最后
	 * 
	 * @param element
	 *            指定元素
	 */
	@Override
	public void addLast(E element) {
		linkLast(element);
	}

	/**
	 * 
	 * 把原本最后Node（prev-item-null）挪到倒数第二位Node（prev-item-e），插入e作为最后Node（item-e-null
	 * ）。 新的链表变成...-（旧的）last-e-null
	 * 
	 * @param element
	 */
	private void linkLast(E element) {
		Node<E> second = last;
		Node<E> newlast = new Node<>(second, element, null);
		last = newlast;

		if (second == null)
			first = newlast;
		else
			second.next = newlast;

		size++;
		modCount++;
	}

	/**
	 * 加入指定元素到链表最后，逻辑同addLast(E)
	 * 
	 * @param element
	 *            指定元素
	 * @return true
	 */
	public boolean add(E element) {
		linkLast(element);
		return true;
	}

	/**
	 * 加入指定元素到指定位置
	 * 
	 * @param index
	 *            指定索引
	 * @param element
	 *            指定元素
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	public void add(int index, E element) {
		checkPositionIndex(index);

		if (index == size)
			linkLast(element);
		else if (index == 0)
			linkFirst(element);
		else
			linkBefore(element, node(index));
	}

	/**
	 * 原本是predNode(prev - itemP - succNode) - succNode(succNode - itemS - next)
	 * <br>
	 * <br>
	 * 變成predNode(prev - itemP - newNode) - newNode(predNode - element -
	 * succNode) - succNode(newNode - item - next)
	 * 
	 * @param element
	 *            指定元素
	 * @param succ
	 *            successor继任者，原本该位置的Node往后移一位，把自己的位置留给successor
	 */
	private void linkBefore(E element, Node<E> succ) {
		// 重新链接newNode
		final Node<E> pred = succ.prev;
		final Node<E> newNode = new Node<>(pred, element, succ);
		// 重新链接predNode
		pred.next = newNode;
		// 重新链接succNode
		succ.prev = newNode;

		size++;
		modCount++;
	}

	/**
	 * 移除头节点并返回头节点
	 *
	 * @return 头节点
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E removeFirst() {
		Node<E> fNode = first;
		if (fNode == null)
			throw new NoSuchElementException();
		else
			return unlinkFirst(fNode);
	}

	/**
	 * 原本是first(null - itemP - succNode) - succNode(succNode - itemS - next)
	 * -... <br>
	 * <br>
	 * 變成first(null - itemS - next) -...
	 * 
	 * @param fNode
	 * @return
	 */
	private E unlinkFirst(Node<E> fNode) {
		final E element = fNode.item;
		final Node<E> newFirst = fNode.next;

		first = newFirst;

		// 整个链表本来就只有一个元素的情况
		if (newFirst == null)
			last = null; // 变成空链表
		else
			newFirst.prev = null;

		//让GC回收被移除的node
		fNode.item = null;
		
		size--;
		modCount++;
		return element;
	}

	/**
	 * 检查位置是否合法并扔出Exception
	 * 
	 * @param index
	 *            指定索引
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	private void checkPositionIndex(int index) {
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	/**
	 * 检查位置是否合法
	 * 
	 * @param index
	 *            指定索引
	 * @return 是否合法
	 */
	private boolean isPositionIndex(int index) {
		return (index >= 0) || (index <= size);
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message. Of the many
	 * possible refactorings of the error handling code, this "outlining"
	 * performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	@Override
	public boolean offerFirst(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offerLast(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(E e) {
		// TODO Auto-generated method stub

	}

	@Override
	public E pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
