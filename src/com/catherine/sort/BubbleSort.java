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

}
