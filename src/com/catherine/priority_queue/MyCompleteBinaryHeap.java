package com.catherine.priority_queue;

import java.util.Iterator;

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
	public void heapify(T n) {
		priorityQueueImpl.heapify(n);
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

	public Iterator<T> iterator() {
		return priorityQueueImpl.iterator();
	}

	public void printTree() {
		priorityQueueImpl.printTree();
	}
}
