package com.catherine;

import java.util.List;

import com.catherine.sort.InsertionSort;
import com.catherine.sort.MergeSort;

public class Main {

	private static int[] input1 = new int[] { 3, 5, 7, 1, 4, 2, 10, 4, -10 };
	private static int[] input2 = new int[] { 38, 29, 28, 15, 13, 11, 5 };
	private static int[] input3 = new int[] { 5, 11, 13, 15, 28, 29, 38 };
	private static int[] input4 = null;
	private static int[] input5 = new int[] { 1 };

	public static void main(String[] args) {
		// InsertionSort
		// InsertionSort is = new InsertionSort();
		// // printArray("InsertionSort", is.sort(input1, true));

		// MergeSort
		// MergeSort ms = new MergeSort();
		// printArray("MergeSort", ms.sort(input1, false));

		Other other = new Other();
		printList("Hailstone", other.getHailstone(42));

	}

	// public static int[] generateRandomArray(int maxLength){
	// int[] result = new int[];
	//
	// return null;
	// }

	public static void printArray(String title, int[] array) {
		System.out.println("--------------------------------------------------------");
		if (array == null)
			System.out.println("null");
		else {
			System.out.print(title + " -> [");
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i]);
				if (i != array.length - 1)
					System.out.print(",");
				else
					System.out.println("]");
			}
		}
		System.out.println("--------------------------------------------------------");
	}
	
	public static void printList(String title, List<Integer> list) {
		System.out.println("--------------------------------------------------------");
		if (list == null)
			System.out.println("null");
		else {
			System.out.print(title + " -> [");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i));
				if (i != list.size() - 1)
					System.out.print(",");
				else
					System.out.println("]");
			}
		}
		System.out.println("--------------------------------------------------------");
	}

}
