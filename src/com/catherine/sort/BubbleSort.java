package com.catherine.sort;

import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * best: n <br>
 * worst: n^2 <br>
 * average: n^2 <br>
 * stability: stable <br>
 * <br>
 * memory: 1
 * 
 * @param <T>
 * @author Catherine
 *
 */
public class BubbleSort<T extends Comparable<? super T>> extends BaseSort<T> {

	@Override
	public T[] sort(T[] input, boolean isAscending) {
		TrackLog tLog = new TrackLog("BubbleSort");
		Analysis.startTracking(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;
		T temp;
		for (int i = input.length; i >= 0; i--) {
			for (int j = 0; j < i - 1; j++) {
				if (isAscending) {
					if (input[j].compareTo(input[j + 1]) > 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
					}
				} else {
					if (input[j].compareTo(input[j + 1]) < 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
					}
				}
			}
		}
		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

	/**
	 * 改良版，排序时，若从第一个元素到正在排的元素前一位都已经排好时，可以直接终止排序。<br>
	 * E.g. [1, 2, 3, 4], 25, 14, 26<br>
	 * 原本扫描路径呈现三角形，改良后若符合条件会变成平行四边形。
	 * 
	 * @param input
	 * @param isAscending
	 * @return
	 */
	public T[] sort2(T[] input, boolean isAscending) {
		TrackLog tLog = new TrackLog("BubbleSort2");
		Analysis.startTracking(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;
		int path = input.length - 1;

		int exchangeCount = 0;
		T temp;
		while ((path > 0)) {
			for (int j = 0; j < path; j++) {
				if (SHOW_DEBUG_LOG)
					System.out.print(j);
				if (isAscending) {
					if (input[j].compareTo(input[j + 1]) > 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						exchangeCount++;
					}
				} else {
					if (input[j].compareTo(input[j + 1]) < 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						exchangeCount++;
					}
				}
			}
			if (SHOW_DEBUG_LOG)
				System.out.print("\n");
			if (exchangeCount == 0)
				break;

			path--;
			exchangeCount = 0;
		}
		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

	/**
	 * 改良版，排序时，若从某一点到最后一位都已经排好时，表示该部分可掠过不检查，直接把排序范围改成某一点到前一位，大幅减少搜寻范围。<br>
	 * E.g. 19, 3, 14, [11, 12, 13, 14]<br>
	 * 原本扫描路径呈现三角形，改良后若符合条件会变成更小的三角形。
	 * 
	 * @param input
	 * @param isAscending
	 * @return
	 */
	public T[] sort3(T[] input, boolean isAscending) {
		TrackLog tLog = new TrackLog("BubbleSort3");
		Analysis.startTracking(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		int path = input.length - 1;
		int preExchangePos = path;
		T temp;
		for (int i = path; i > 0; i--) {
			for (int j = 0; j < path - 1; j++) {
				if (SHOW_DEBUG_LOG)
					System.out.print(j);
				if (isAscending) {
					if (input[j].compareTo(input[j + 1]) > 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						preExchangePos = j;
					}
				} else {
					if (input[j].compareTo(input[j + 1]) < 0) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						preExchangePos = j;
					}
				}
			}
			// 提前终止
			if (i == 1)
				break;

			path = preExchangePos + 1;
			if (SHOW_DEBUG_LOG)
				System.out.print("\t" + preExchangePos + "\t" + path + "\n");
		}

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

}
