package com.catherine.priority_queue;

/**
 * 优先级队列<br>
 * <br>
 * 可以用BBST来实现(AVL tree, Splay tree, Red black
 * tree...)，但是BBST功能过于强大，用BBST来实现显得有些小题大做了。在此由完全二叉堆(Complete binary
 * heap)来实现优先级队列。
 * 
 * @author Catherine
 *
 * @param <E>
 * @see MyCompleteBinaryHeap 完全二叉堆
 */
public interface PriorityQueue<E> {

	/**
	 * 插入词条（Entry）
	 * 
	 * @param e
	 */
	public void insert(E e);

	/**
	 * 
	 * @return 优先级最高的词条
	 */
	public E getMax();

	/**
	 * 删除优先级最高的词条
	 * 
	 * @return 新。优先级最高的词条
	 */
	public E delMax();

	void percolateDown(E n, E i);

	/**
	 * 上滤<br>
	 * 如果E与其父亲违反堆序性，则交换为止，交换后继续和新父亲（原祖父）做确认，直到 E与父亲符合堆序性或到达顶点。
	 * 
	 * @param i
	 */
	void percolateUp(E i);

	void heapify(E n);

}
