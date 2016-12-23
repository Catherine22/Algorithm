package com.catherine.sort;

import com.catherine.utils.Analysis;
import com.catherine.utils.TraceLog;

/**
 * best: n <br>
 * average: n^2 worst: n^2 <br>
 * <br>
 * memory: 1
 * 
 * @author Catherine
 *
 */
public class BubbleSort extends BaseSort {

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		TraceLog tLog = new TraceLog("BubbleSort");
		Analysis.startTracing(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;
		int temp;
		for (int i = input.length; i >= 0; i--) {
			for (int j = 0; j < i - 1; j++) {
				if (isAscending) {
					if (input[j] > input[j + 1]) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
					}
				} else {
					if (input[j] < input[j + 1]) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
					}
				}
			}
		}
		Analysis.endTracing(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrace(tLog);
		return input;
	}

	/**
	 * 改良版，排序时，若从第一个元素到正在排的元素前一位都已经排好时，可以直接终止排序。<br>
	 * 原本扫描路径呈现三角形，改良后若符合条件会变成平行四边形。
	 * 
	 * @param input
	 * @param isAscending
	 * @return
	 */
	public int[] sort2(int[] input, boolean isAscending) {
		TraceLog tLog = new TraceLog("BubbleSort2");
		Analysis.startTracing(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;
		int path = input.length - 1;

		int exchangeCount = 0;
		int temp;
		while ((path > 0)) {
			for (int j = 0; j < path; j++) {
				if (SHOW_DEBUG_LOG)
					System.out.print(j);
				if (isAscending) {
					if (input[j] > input[j + 1]) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						exchangeCount++;
					}
				} else {
					if (input[j] < input[j + 1]) {
						temp = input[j];
						input[j] = input[j + 1];
						input[j + 1] = temp;
						exchangeCount++;
					}
				}
			}
			if (SHOW_DEBUG_LOG)
				System.out.print("\t"+exchangeCount+"\n");
			if (exchangeCount == 0)
				break;

			path--;
			exchangeCount = 0;
		}
		Analysis.endTracing(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrace(tLog);
		return input;
	}

}
