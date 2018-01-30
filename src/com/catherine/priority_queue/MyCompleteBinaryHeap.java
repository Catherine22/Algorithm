package com.catherine.priority_queue;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

/**
 * 完全二叉堆（逻辑上等同于完全二叉树，物理上以向量实现）<br>
 * 以层次遍历完全二叉树并对应到向量上。<br>
 * <br>
 * 堆序性：只要i>0，H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * @author Catherine
 *
 * @param <T>
 */
public class MyCompleteBinaryHeap<T extends Comparable<? super T>> implements PriorityQueue<T> {
	private PriorityQueueImpl<T> priorityQueueImpl;

	public MyCompleteBinaryHeap() {
		priorityQueueImpl = new PriorityQueueImpl<>();
	}

	public PriorityQueue<T> getPriorityQueue() {
		return priorityQueueImpl;
	}

	@Override
	public void insert(T e) {
		priorityQueueImpl.add(e);
		percolateUp(e);
	}

	@Override
	public T getMax() {
		return priorityQueueImpl.get(0);
	}

	@Override
	public T delMax() {
		return priorityQueueImpl.delMax();
	}

	@Override
	public void percolateDown(T n, T i) {
		priorityQueueImpl.percolateDown(n, i);
	}

	@Override
	public void percolateUp(T i) {
		priorityQueueImpl.percolateUp(i);
	}

	@Override
	@Deprecated
	public void completedlyHeapify(T[] array) {
		priorityQueueImpl.heapify(array);
	}

	@Override
	@Deprecated
	public void completedlyHeapify(List<T> list) {
		priorityQueueImpl.heapify(list);
	}

	@Override
	public void heapify(T[] array) {
		priorityQueueImpl.heapify(array);
	}

	@Override
	public void heapify(List<T> list) {
		priorityQueueImpl.heapify(list);
	}

	@Override
	public void merge(PriorityQueue<T> heap) {
		priorityQueueImpl.merge(heap);
	}

	public T getParent(T e) {
		return priorityQueueImpl.getParent(e);
	}

	public T getLChild(T e) {
		return priorityQueueImpl.getLChild(e);
	}

	public T getRChild(T e) {
		return priorityQueueImpl.getRChild(e);
	}

	public void printTree() {
		priorityQueueImpl.printTree();
	}

	public int size() {
		return priorityQueueImpl.size();
	}

	public synchronized void copyInto(Object[] anArray) {
		priorityQueueImpl.copyInto(anArray);
	}

	public synchronized void trimToSize() {
		priorityQueueImpl.trimToSize();
	}

	public synchronized boolean isEmpty() {
		return priorityQueueImpl.isEmpty();
	}

	public int indexOf(Object o) {
		return priorityQueueImpl.indexOf(o);
	}

	public synchronized int indexOf(Object o, int index) {
		return priorityQueueImpl.indexOf(o, index);
	}

	public synchronized int lastIndexOf(Object o) {
		return priorityQueueImpl.lastIndexOf(o);
	}

	public synchronized int lastIndexOf(Object o, int index) {
		return priorityQueueImpl.lastIndexOf(o, index);
	}

	public synchronized T elementAt(int index) {
		return priorityQueueImpl.elementAt(index);
	}

	public synchronized T firstElement() {
		return priorityQueueImpl.firstElement();
	}

	public synchronized T lastElement() {
		return priorityQueueImpl.lastElement();
	}

	public synchronized Object clone() {
		return priorityQueueImpl.clone();
	}

	public synchronized Object[] toArray() {
		return priorityQueueImpl.toArray();
	}

	public synchronized <T> T[] toArray(T[] a) {
		return priorityQueueImpl.toArray(a);
	}

	public synchronized T get(int index) {
		return priorityQueueImpl.get(index);
	}

	public void clear() {
		priorityQueueImpl.clear();
	}

	public synchronized String toString() {
		return priorityQueueImpl.toString();
	}

	public synchronized List<T> subList(int fromIndex, int toIndex) {
		return priorityQueueImpl.subList(fromIndex, toIndex);
	}

	public synchronized ListIterator<T> listIterator(int index) {
		return priorityQueueImpl.listIterator(index);
	}

	public synchronized ListIterator<T> listIterator() {
		return priorityQueueImpl.listIterator();
	}

	public synchronized Iterator<T> iterator() {
		return priorityQueueImpl.iterator();
	}

	public Spliterator<T> spliterator() {
		return priorityQueueImpl.spliterator();
	}
}
