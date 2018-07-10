package com.catherine.sort;

import com.catherine.Main;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * best: n log n //Uniform distribution（均勻分佈）<br>
 * average: n log n //Let's say we have a uniform distributed array<br>
 * worst: n^2 //The pivot we found is always the maximum or minimum element of
 * the array.<br>
 * <br>
 * 
 * Find out a pivot, for each element on pivot's right must be larger then the
 * right ones. Therefore, the point is how quick we can find out the pivot, and
 * then using binary recursion.<br>
 * 
 * Every element is a pivot in a sorted array. What Quick sort means is the
 * procedure of convert every element into a pivot. <br>
 * <br>
 * Optimization:<br>
 * 1. Set pivots from random elements.<br>
 * 2. [UNIX] Pick up 3 elements and select the middle element as the pivot.<br>
 * 
 * 
 * @author Catherine
 *
 * @param <T>
 *            extends Comparable
 */
public class QuickSort<T extends Comparable<? super T>> extends BaseSort<T> {

	public QuickSort() {
		TAG = "QuickSort";
	}

	@Override
	public T[] sort(T[] a, boolean isAscending) {
		if (a == null || a.length == 0)
			return a;
		TrackLog tLog = new TrackLog(TAG);
		Analysis.startTracking(tLog);

		T[] input = a.clone();
		merge(input, 0, input.length - 1, isAscending);

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

	private void merge(T[] input, int l, int r, boolean isAscending) {
		if (l < r && l < input.length && r >= 0) {
			// System.out.println("from " + l + " to " + r);
			int head = partition(input, l, r, isAscending);
			// System.out.println("head " + head);
			merge(input, head + 1, r, isAscending);
			merge(input, l, head - 1, isAscending);
		}
	}

	/**
	 * Turn head into the pivot
	 * 
	 * @param input
	 * @param head
	 * @param target
	 * @param isAscending
	 * @return the position of pivot
	 */
	private int partition(T[] input, int head, int target, boolean isAscending) {
		T temp = null;
		int tempInt = 0;
		int c = 0;

		// Main.printArray("QS " + c++ + "->" + input[head] + " vs "
		// + input[target], input);
		if (isAscending) {
			while (head != target) {
				if (input[head].compareTo(input[target]) <= 0) {

					if (head > target) {
						tempInt = head;
						head = target;
						target = tempInt;

						temp = input[head];
						input[head] = input[target];
						input[target] = temp;
					}

					// Main.printArray("QS " + c++ + "->" + input[head] + " vs "
					// + input[target], input);
					target--;
				} else {
					if (head < target) {
						tempInt = head;
						head = target;
						target = tempInt;

						temp = input[head];
						input[head] = input[target];
						input[target] = temp;
					}

					// Main.printArray("QS " + c++ + "->" + input[head] + " vs "
					// + input[target], input);
					target++;

				}

			}
		} else {
			while (head != target) {
				if (input[head].compareTo(input[target]) >= 0) {

					if (head > target) {
						tempInt = head;
						head = target;
						target = tempInt;

						temp = input[head];
						input[head] = input[target];
						input[target] = temp;
					}

					// Main.printArray("QS " + c++ + "->" + input[head] + " vs "
					// + input[target], input);
					target--;
				} else {
					if (head < target) {
						tempInt = head;
						head = target;
						target = tempInt;

						temp = input[head];
						input[head] = input[target];
						input[target] = temp;
					}

					// Main.printArray("QS " + c++ + "->" + input[head] + " vs "
					// + input[target], input);
					target++;

				}

			}
		}
		return head;
	}
}
