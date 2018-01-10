package com.catherine.sort;

/**
 * best: n <br>
 * average: n^2 <br>
 * worst: n^2 <br>
 * <br>
 * memory: 1（就地算法 in-place）
 * 
 * @param <T>
 * @author Catherine
 *
 */
public class InsertionSort<T extends Comparable<? super T>> extends BaseSort<T> {

	@Override
	public T[] sort(T[] input, boolean isAscending) {
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		T temp;
		for (int i = 0; i < input.length; i++) {
			for (int j = i; j > 0; j--) {
				if (!isAscending) {
					if (input[j].compareTo(input[j - 1]) > 0) {
						temp = input[j];
						input[j] = input[j - 1];
						input[j - 1] = temp;
						if (SHOW_DEBUG_LOG)
							System.out.println(input[j - 1] + " switch " + input[j]);
					}
				} else {
					if (input[j].compareTo(input[j - 1]) < 0) {
						temp = input[j];
						input[j] = input[j - 1];
						input[j - 1] = temp;
						if (SHOW_DEBUG_LOG)
							System.out.println(input[j - 1] + " switch " + input[j]);
					}
				}
			}
		}

		return input;
	}

}
