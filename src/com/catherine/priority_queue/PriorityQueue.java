package com.catherine.priority_queue;

/**
 * 优先级队列<br>
 * <br>
 * 可以用BBST来实现(AVL tree, Splay tree, Red black
 * tree...)，但是BBST功能过于强大，用BBST来实现显得有些小题大做了。在此由完全二叉堆(Complete binary
 * heap)来实现优先级队列。 <br>
 * <br>
 * 堆序性： H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * @author Catherine
 *
 * @param <E>
 * @see MyCompleteBinaryHeap 完全二叉堆
 */
public interface PriorityQueue<E> {

	/**
	 * 插入词条（Entry）并上滤。
	 * 
	 * @param e
	 */
	public void insert(E e);

	/**
	 * 返回优先级最高的词条（也就是堆顶）
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 * 
	 * @return 优先级最高的词条
	 */
	public E getMax();

	/**
	 * 删除优先级最高的词条（也就是堆顶），让最后一个节点取代根节点并下滤。
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 * 
	 * @return 新。优先级最高的词条
	 */
	public E delMax();

	/**
	 * 对前n个词条的第i个节点进行下滤，n>i<br>
	 * 如果E与其较大的子节点违反堆序性，则交换为止，交换后继续和新较大的子节点（原孙子）做确认，直到E与孩子符合堆序性或到达底端。<br>
	 * 由于树高不超过log(n)，所以比较次数最多就是log(n)，最坏情况是每一次上滤都要交换（每次交换需要三次赋值），所以=3·log(n)。
	 * <br>
	 * 在此可以优化交换的逻辑，变成首先备份初始节点，接着往下比较，一旦子节点比较大就取代父亲，直到符合堆序性或到达底端，
	 * 再将初始节点指定给当前位置。一共需要两次赋值，所以=log(n)+2。<br>
	 * 
	 * @param n
	 * @param i
	 */
	void percolateDown(E n, E i);

	/**
	 * 上滤<br>
	 * 如果E与其父亲违反堆序性，则交换为止，交换后继续和新父亲（原祖父）做确认，直到E与父亲符合堆序性或到达顶点。<br>
	 * 由于树高不超过log(n)，所以比较次数最多就是log(n)，最坏情况是每一次上滤都要交换（每次交换需要三次赋值），所以=3·log(n)。
	 * <br>
	 * 在此可以优化交换的逻辑，变成首先备份初始节点，接着往上比较，一旦父节点比较小就取代孩子，直到符合堆序性或到达顶点，
	 * 再将初始节点指定给当前位置。一共需要两次赋值，所以=log(n)+2。<br>
	 * 
	 * @param i
	 */
	void percolateUp(E i);

	void heapify(E n);

}
