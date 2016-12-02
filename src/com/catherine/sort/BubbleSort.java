package com.catherine.sort;

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

		return input;
	}

}
