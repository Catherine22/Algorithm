package com.catherine.sort;

import java.util.LinkedList;

import com.catherine.Main;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * average: n^2 <br>
 * 因为要找出最小或最大值，每次查找花费的时间是 (n - k) 这里的时间是固定的，所以没有最好或最坏的情形，一律 n^2。<br>
 * <br>
 * memory: 1<br>
 * <br>
 * 相较于Bubble sort，selection sort 减少了元素移动的次数（ 从 n 变成 1），在实际运行中大幅了减少运行时间。<br>
 * 挑出集合中最大（小）值，挪到最后面。<br>
 * <br>
 * selection sort适合用LinkedList实作，因为只要改引用而不是调整整个数组。<br>
 * 
 * @param <T>
 * @author Catherine
 *
 */
public class SelectionSort<T extends Comparable<? super T>> extends BaseSort<T> {

	public SelectionSort() {
		TAG = "SelectionSort";
	}

	@Override
	public T[] sort(T[] a, boolean isAscending) {
		if (a == null || a.length == 0)
			return a;
		TrackLog tLog = new TrackLog(TAG);
		Analysis.startTracking(tLog);

		T[] input = a.clone();
		LinkedList<T> outputs = new LinkedList<>();
		for (T v : input) {
			outputs.add(v);
		}

		if (SHOW_DEBUG_LOG)
			Main.printList("original list", outputs);

		int header = outputs.size() - 1;
		int tag = 0;
		T minOrMax = outputs.getFirst();

		while (header >= 0) {
			for (int i = 0; i <= header; i++) {
				if (isAscending) {
					if (minOrMax.compareTo(outputs.get(i)) < 0) {
						minOrMax = outputs.get(i);
						tag = i;
					}
				} else {
					if (minOrMax.compareTo(outputs.get(i)) > 0) {
						minOrMax = outputs.get(i);
						tag = i;
					}
				}
			}

			if (SHOW_DEBUG_LOG)
				System.out.println("tag:" + tag + "/header:" + header + "/minOrMax:" + minOrMax);

			outputs.remove(tag);
			outputs.add(header--, minOrMax);
			tag = 0;
			minOrMax = outputs.get(0);

			if (SHOW_DEBUG_LOG)
				Main.printList("sorted list", outputs);
		}

		for (int i = 0; i < input.length; i++)
			input[i] = outputs.pop();

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

}
