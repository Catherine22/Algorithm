package com.catherine.data_type;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
	 * 加入指定元素到链表第一位
	 * 
	 * @param e
	 *            指定元素
	 */
	@Override
	public void addFirst(E e) {
		linkFirst(e);
	}

	/**
	 * 
	 * 把原本第一位Node（null-item-next）挪到第二位Node（e-item-next），插入e作为第一位Node（null-e-item
	 * ）。 新的链表变成null-e-（旧的）first-...
	 * 
	 * @param e
	 */
	private void linkFirst(E e) {
		Node<E> second = first;
		Node<E> newFirst = new Node<>(null, e, second);
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
	 * @param e
	 *            指定元素
	 */
	@Override
	public void addLast(E e) {
		linkLast(e);
	}

	/**
	 * 
	 * 把原本最后Node（prev-item-null）挪到倒数第二位Node（prev-item-e），插入e作为最后Node（item-e-null
	 * ）。 新的链表变成...-（旧的）last-e-null
	 * 
	 * @param e
	 */
	private void linkLast(E e) {
		Node<E> second = last;
		Node<E> newlast = new Node<>(second, e, null);
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
	 * @param e
	 *            指定元素
	 * @return true
	 */
	public boolean add(E e) {
		linkLast(e);
		return true;
	}
	
    public void add(int index, E element) {

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
	public E removeFirst() {
		// TODO Auto-generated method stub
		return null;
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
