package com.catherine;

import com.catherine.sort.InsertionSort;

public class Main {

	private static int[] input1 = new int[] { 3, 5, 1, 7, 2, 4, 10, 4, -10 };
	private static int[] input2 = new int[] { 1, 3, 6, 2, 7, 11, 38 };
	private static int[] input3 = null;
	private static int[] input4 = new int[] { 1 };

	public static void main(String[] args) {

		InsertionSort is = new InsertionSort();
		printArray(is.sort(input1, true));
	}

	private static void printArray(int[] array) {
		if (array == null)
			System.out.println("null");
		else {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i]);
				if (i != array.length - 1)
					System.out.print(",");
			}
			System.out.println();
		}
	}

}
