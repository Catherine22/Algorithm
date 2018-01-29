package com.catherine.priority_queue;

/**
 * 左式堆<br>
 * 用来高效合并两个已排序的堆。<br>
 * <br>
 * 特性：<br>
 * 1. 保证堆序性<br>
 * 2. 拓扑结构上不会是完全二叉树<br>
 * 3. 引入外部节点与npl(Null point length，即任意节点到外部节点到最短距离)<br>
 * 
 * @author Catherine
 *
 * @param <T>
 */
public class LeftistHeap<T extends Comparable<? super T>> extends PriorityQueueImpl<T> {
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 5439346124731561315L;

}
