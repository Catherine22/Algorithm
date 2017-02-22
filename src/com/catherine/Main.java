package com.catherine;

import java.util.ArrayList;
import java.util.List;

import com.catherine.data_type.MyArrayList;
import com.catherine.data_type.Operator;
import com.catherine.data_type.Search;
import com.catherine.data_type.Sequence;
import com.catherine.sort.BubbleSort;
import com.catherine.sort.InsertionSort;
import com.catherine.sort.MergeSort;
import com.catherine.turing_machine.TuringMachine;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

public class Main {

	private static int[] input1 = new int[] { 3, 5, 7, 1, 4, 2, 10, 4, -10, 3, 5, 7, 1, 4, 2, 10, 4, -10, 3, 5, 7, 1, 4,
			2, 10, 4, -10, 3, 5, 7, 1, 4, 2, 10, 4, -10 };
	private static int[] input2 = new int[] { 38, 29, 28, 11, 4, 5, 2 };
	private static int[] input3 = new int[] { 5, 11, 13, 15, 28, 29, 38 };
	private static int[] input4 = null;
	private static int[] input5 = new int[] { 1 };
	private static int[] input6 = new int[] { 1, 4, 1, 1, 7, 3, 64, 5, 23, 12, 14, 10 };
	private static int[] input7 = new int[] { 23, 24, 25, 26, 29, 4, 2 };

	public static void main(String[] args) {
		// InsertionSort
		// InsertionSort is = new InsertionSort();
		// // printArray("InsertionSort", is.sort(input1, true));

		// MergeSort
		MergeSort ms = new MergeSort();
		 TrackLog tLog = new TrackLog("MergeSort"); //track
		 Analysis.startTracking(tLog); //track
		printArray("MergeSort", ms.sort(input3, false));
		 Analysis.endTracking(tLog); //track
		 Analysis.printTrack(tLog); //track

		// BubbleSort
		// BubbleSort bs = new BubbleSort();
		// TrackLog tLog1 = new TrackLog("BubbleSort"); //track
		// Analysis.startTracking(tLog1); //track
		// printArray("BubbleSort", bs.sort(input1, false));
		// Analysis.endTracking(tLog1); //track
		// Analysis.printTrack(tLog1); //track
		// printArray("BubbleSort2", bs.sort2(input3, false));
		// printArray("BubbleSort3", bs.sort3(input7, false));

		// Hailstone
		// Other other = new Other();
		// printList("Hailstone", other.getHailstone(42));

		// Increment on Turing Machine
		// TuringMachine tMachine = new TuringMachine();
		// printArray("TuringMachine", tMachine.increase(new int[] { 0, 0, 1, 1,
		// 1, 1 }));

		// Sequence sequence = new Sequence();
		// 2 ways to increase the capacity
		// sequence.increaseArray();
		// sequence.doubleArray();

		// System.out.println("find " + sequence.find(input1, -10));
		// printArray("insert", sequence.insert(input1, 5, 9));
		// printArray("remove", sequence.remove(input1, 1, 7));
		// printArray("shift", sequence.shift(input2, 2, 5, 7));
		// printArray("iterator", sequence.iterator(input1, new Operator() {
		//
		// @Override
		// public int doSomethine(int input) {
		// return ++input;
		// }
		// }));

		// printArray("removeDuplicates", sequence.removeDuplicates(input1));
		// printArray("removeDuplicatesAndSort1",sequence.removeDuplicatesAndSort1(input6));
		// printArray("removeDuplicatesAndSort2",sequence.removeDuplicatesAndSort2(input6));

		// Search search = new Search();
		// System.out.println("binSearch:" + search.binSearch(input6, 1, 0,
		// input6.length - 1));
		// System.out.println("binSearch2:" + search.binSearch2(input6, 1, 0,
		// input6.length - 1));
		// System.out.println("fibSearch:" + search.fibSearch(input6, 1, 0,
		// input6.length - 1));

		// ArrayList
		// List<String> a1 = new ArrayList<>();
		// List<String> a2 = new ArrayList<>();
		// a2 = a1;// 两者指向相同地址，所以其实是一样的
		// a1.add("hello");
		// a1.add("I'm a1!");
		// a2.add("I'm a2!");
		// System.out.println(a1);
		// System.out.println(a2);
		// MyArrayList<String> myA1 = new MyArrayList<>();
		// List<String> myA2 = new MyArrayList<>();
		// myA2 = (List<String>) myA1.clone(); // 用clone()就是返回一份拷贝，两者互不相干
		// myA1.add("hello");
		// myA1.add("I'm myA1!");
		// myA2.add("I'm myA2!");
		// System.out.println(myA1);
		// System.out.println(myA2);
	}

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
