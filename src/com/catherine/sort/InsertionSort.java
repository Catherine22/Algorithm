package com.catherine.sort;

public class InsertionSort extends BaseSort {

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		int temp;
		for (int i = 0; i < input.length - 1; i++) {
			for (int j = i; j > 0; j--) {
				if (!isAscending) {
					if (input[j] > input[j - 1]) {
						temp = input[j];
						input[j] = input[j - 1];
						input[j - 1] = temp;
//						System.out.println(input[j - 1] + " switch " + input[j]);
					}
				} else {
					if (input[j] < input[j - 1]) {
						temp = input[j];
						input[j] = input[j - 1];
						input[j - 1] = temp;
//						System.out.println(input[j - 1] + " switch " + input[j]);
					}
				}
			}
		}

		return input;
	}

}
