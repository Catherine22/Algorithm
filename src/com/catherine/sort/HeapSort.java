package com.catherine.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.catherine.Main;
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

	@Override
	public T[] sort(T[] input, boolean isAscending) {
		TrackLog tLog = new TrackLog("SelectionSort");
		Analysis.startTracking(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		MyCompleteBinaryHeap<T> pq = new MyCompleteBinaryHeap<>();
		pq.heapify(input);

		input[0] = pq.getMax();
		for (int i = 1; i < input.length; i++)
			input[i] = pq.delMax();

		if (isAscending) {
			T tmp;
			int m = ((int) Math.floor(input.length / 2)) - 1;
			for (int i = 0; i < m; i++) {
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
