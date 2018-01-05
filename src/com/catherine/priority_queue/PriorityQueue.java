package com.catherine.priority_queue;

/**
 * 优先级队列<br>
 * <br>
 * 可以用BBST来实现(AVL tree, Splay tree, Black red
 * tree...)，但是BBST功能过于强大，用BBST来实现显得有些小题大做了。在此由完全二叉堆(Complete binary
 * heap)来实现优先级队列。
 * 
 * @author Catherine
 *
 * @param <E>
 * @see MyCompleteBinaryHeap 完全二叉堆
 */
public abstract class PriorityQueue<E> {

	/**
	 * 插入词条（Entry）
	 * 
	 * @param e
	 */
	abstract void insert(E e);

	/**
	 * 
	 * @return 优先级最高的词条
	 */
	abstract E getMax();

	/**
	 * 删除优先级最高的词条
	 * 
	 * @return 新。优先级最高的词条
	 */
	abstract E delMax();
}
