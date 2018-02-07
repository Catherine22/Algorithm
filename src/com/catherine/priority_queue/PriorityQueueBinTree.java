package com.catherine.priority_queue;

import java.util.List;

import com.catherine.trees.nodes.Node;

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
 * @param <T>
 * @see MyCompleteBinaryHeap 完全二叉堆
 */
public interface PriorityQueueBinTree<T> {

	/**
	 * 插入词条（Entry）并上滤。
	 * 
	 * @param e
	 */
	public void insert(T e);

	/**
	 * 返回优先级最高的词条（也就是堆顶）
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 * 
	 * @return 优先级最高的词条
	 */
	public T getMax();

	/**
	 * 删除优先级最高的词条（也就是堆顶），让最后一个节点取代根节点并下滤。
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 * 
	 * @return 新。优先级最高的词条
	 */
	public T delMax();

	/**
	 * 从第i个节点进行下滤直到第n个词条，n>i<br>
	 * 如果E与其较大的子节点违反堆序性，则交换为止，交换后继续和新较大的子节点（原孙子）做确认，直到E与孩子符合堆序性或到达底端。<br>
	 * 由于树高不超过log(n)，所以比较次数最多就是log(n)，最坏情况是每一次上滤都要交换（每次交换需要三次赋值），所以=3·log(n)。
	 * <br>
	 * 在此可以优化交换的逻辑，变成首先备份初始节点，接着往下比较，一旦子节点比较大就取代父亲，直到符合堆序性或到达底端，
	 * 再将初始节点指定给当前位置。一共需要两次赋值，所以=log(n)+2。<br>
	 * 
	 * @param n
	 * @param i
	 */
	void percolateDown(Node<T> n, Node<T> i);

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
	void percolateUp(Node<T> i);

	/**
	 * 传入一段可排序的集合，使之成为优先级队列。<br>
	 * 两种实现方式，这里采第二种：<br>
	 * 1. 将集合内的元素挨个加入，每次加入做上滤处理。（平淡无奇的土办法，花費O(n(log
	 * n))效率差，花费的时间足以做全排序，而在此只需达到偏序）<br>
	 * 2. 佛洛依德算法，由上而下的下濾——先把元素搬进来，然后从最后一个元素的父亲开始下滤，再兄弟下滤，
	 * 再两者的父亲下滤，类似合并堆的概念，O(n)。<br>
	 * 
	 * @param list
	 *            可排序的集合
	 */
	public void heapify(List<T> list);

	/**
	 * 传入一段可排序的集合，使之成为优先级队列。<br>
	 * 两种实现方式，这里采第二种：<br>
	 * 1. 将集合内的元素挨个加入，每次加入做上滤处理。（平淡无奇的土办法，花費O(n(log
	 * n))效率差，花费的时间足以做全排序，而在此只需达到偏序）<br>
	 * 2. 佛洛依德算法，由上而下的下濾——先把元素搬进来，然后从最后一个元素的父亲开始下滤，再兄弟下滤，
	 * 再两者的父亲下滤，类似合并堆的概念，O(n)。<br>
	 * 
	 * @param array
	 *            可排序的集合
	 */
	public void heapify(T[] array);

	/**
	 * 一般来说不必用到这个，直接用{@link #heapify(Object[])}做偏序处理即可。<br>
	 * 传入一段可排序的集合，使之成为优先级队列，并全排序。<br>
	 * 将集合内的元素挨个加入，每次加入做上滤处理。 <br>
	 * 
	 * @param n
	 */
	@Deprecated
	public void completedlyHeapify(T[] array);

	/**
	 * 一般来说不必用到这个，直接用{@link #heapify(List)}做偏序处理即可。 <br>
	 * 传入一段可排序的集合，使之成为优先级队列，并全排序。 <br>
	 * 将集合内的元素挨个加入，每次加入做上滤处理。 <br>
	 * 
	 * @param n
	 */
	@Deprecated
	public void completedlyHeapify(List<T> list);

	/**
	 * 合并两个堆，有三种实现方式，此处用第二种实现。 <br>
	 * 堆A与堆B，且 |A| = m >= n = |B| <br>
	 * 1. 挨个添加 = A.insert(B.delMax()) = O(m * log(m+n)) <br>
	 * 2. 佛洛依德算法 = O(m+n) <br>
	 * 3. 左式堆 = O(log(n))<br>
	 * 
	 * @see com.catherine.priority_queue.LeftistHeap 左式堆
	 * @param heap
	 */
	public void merge(PriorityQueueBinTree<T> heap);

	int size();

	T get(int position);

}
