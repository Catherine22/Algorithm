package com.catherine.sort;

import java.util.Collections;

import com.catherine.priority_queue.MyCompleteBinaryHeap;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * 用priority queue結合selection sort實現。
 * 
 * @author Catherine
 *
 * @param <T>
 * @see SelectionSort SelectionSort
 * @see com.catherine.priority_queue.PriorityQueueImpl PriorityQueueImpl
 */
public class HeapSort<T extends Comparable<? super T>> extends BaseSort<T> {

	public HeapSort() {
		TAG = "HeapSort";
	}

	@Override
	public T[] sort(T[] a, boolean isAscending) {
		if (a == null || a.length == 0)
			return a;
		TrackLog tLog = new TrackLog(TAG);
		Analysis.startTracking(tLog);

		T[] input = a.clone();
		MyCompleteBinaryHeap<T> pq = new MyCompleteBinaryHeap<>();
		pq.heapify(input);

		input[0] = pq.getMax();

		for (int i = 1; i < input.length; i++)
			input[i] = pq.delMax();

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

}
