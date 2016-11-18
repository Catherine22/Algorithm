package com.catherine;

import com.catherine.sort.InsertionSort;
import com.catherine.sort.MergeSort;

public class Main {

	private static int[] input1 = new int[] { 3, 5, 7, 1, 4, 2, 10, 4, -10 };
	private static int[] input2 = new int[] { 38, 29, 28, 15, 13, 11, 5 };
	private static int[] input3 = new int[] { 5, 11, 13, 15, 28, 29, 38 };
	private static int[] input4 = null;
	private static int[] input5 = new int[] { 1 };

	public static void main(String[] args) {
		// InsertionSort is = new InsertionSort();
		// printArray(is.sort(input1, true));
		 MergeSort ms = new MergeSort();
		 printArray(ms.sort(input1, true));
	}
	
//	public static int[] generateRandomArray(int maxLength){
//		int[] result = new int[];
//		
//		return null;
//	}

	public static void printArray(int[] array) {
		System.out.println("--------------------------------------------------------");
		if (array == null)
			System.out.println("null");
		else {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i]);
				if (i != array.length - 1)
					System.out.print(",");
				else
					System.out.println();
			}
		}
		System.out.println("--------------------------------------------------------");
	}

}
