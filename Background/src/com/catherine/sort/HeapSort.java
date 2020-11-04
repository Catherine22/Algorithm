package com.catherine.sort;

import com.catherine.Main;
import com.catherine.priority_queue.MyCompleteBinaryHeap;
import com.catherine.priority_queue.PriorityQueueVectorImpl;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * 用priority queue結合selection sort實現。
 * 
 * @author Catherine
 *
 * @param <T>
 * @see SelectionSort SelectionSort
 * @see com.catherine.priority_queue.PriorityQueueVectorImpl PriorityQueueVectorImpl
 */
public class HeapSort<T extends Comparable<? super T>> extends BaseSort<T> {

	public HeapSort() {
		TAG = "HeapSort";
	}

	/**
	 * 利用完全二叉堆的特性，根节点恆为最大值，将数组倒入完全二叉堆后不断的取出并移除根节点。
	 */
	@Override
	public T[] sort(T[] a, boolean isAscending) {
		if (a == null || a.length == 0)
			return a;
		TAG = "HeapSort1";
		TrackLog tLog = new TrackLog(TAG);
		Analysis.startTracking(tLog);

		T[] input = a.clone();

		MyCompleteBinaryHeap<T> pq = new MyCompleteBinaryHeap<>();
		pq.heapify(input);

		//TODO
//		Main.printArray("HEAPIFY", input);

		// 首先根节点已经坐落[0]，直接从[1]开始
		input[0] = pq.getMax();
		//TODO
//		System.out.print("HEAP: {"+input[0]);

		for (int i = 1; i < input.length; i++) {
			input[i] = pq.delMax();
			//TODO
//			System.out.print(" "+input[i]);
		}
		//TODO
//		System.out.println(" }");

		if (isAscending) {
			T tmp;
			int m = ((int) Math.floor(input.length / 2)) - 1;
			for (int i = 0; i <= m; i++) {
				tmp = input[i];
				input[i] = input[input.length - i - 1];
				input[input.length - i - 1] = tmp;
			}
		}

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

	/**
	 * 完全二叉堆+就地算法<br>
	 * 假如由小到大排序，将数组倒入完全二叉堆后，首先挑出根节点，和最后一个元素交换，然后在对刚交换的元素做上滤，再挑出根节点......以此循环。
	 * 
	 * @param a
	 * @param isAscending
	 * @return
	 */
	public T[] sort2(T[] a, boolean isAscending) {
		if (a == null || a.length == 0)
			return a;
		TAG = "HeapSort2";
		TrackLog tLog = new TrackLog(TAG);
		Analysis.startTracking(tLog);

		T[] input = a.clone();

		PriorityQueueVectorImpl<T> pq = new PriorityQueueVectorImpl<>();
		pq.heapify(input);
		pq.toArray(input);

		for (int i = 0; i < input.length - 1; i++) {
			swapAndPercolateDown(pq, (input.length - 1 - i));
		}

		pq.toArray(input);

		if (!isAscending) {
			T tmp;
			int m = ((int) Math.floor(input.length / 2)) - 1;
			for (int i = 0; i <= m; i++) {
				tmp = input[i];
				input[i] = input[input.length - i - 1];
				input[input.length - i - 1] = tmp;
			}
		}

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

	/**
	 * 将最大值（根节点）移到最后面，并自定下滤范围至第n个元素（完整下滤n应为pq.size() - 1），返回指定区间的最大值（根节点）
	 * 
	 * @param pq
	 *            完全二叉堆
	 * @param n
	 *            滤范围至第n个元素
	 * @return 指定区间的最大值（根节点）
	 */
	private T swapAndPercolateDown(PriorityQueueVectorImpl<T> pq, int n) {
		if (pq.size() == 0)
			return null;
		if (pq.size() == 1) {
			T tmp = pq.get(0);
			return tmp;
		}

		if (n > 0) {
			T tmp = pq.get(0);
			pq.set(0, pq.get(n));
			pq.set(n, tmp);
			pq.percolateDown(pq.get(n - 1), pq.get(0));
		}
		return pq.get(0);
	}
}
