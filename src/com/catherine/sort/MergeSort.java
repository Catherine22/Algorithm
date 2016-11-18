package com.catherine.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

import com.catherine.Main;

public class MergeSort extends BaseSort {

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		int[] A = new int[] { 4, 6, 5, 2, 7, 3, 6, 3, 4, 6, 5, 2, 7, 3, 6, 3 };
		int[] result = new int[A.length];

		int halfLen = (int) Math.floor((float) A.length / 2.0f);
		int[] leftArray = new int[halfLen];
		int[] righttArray = new int[A.length - halfLen];

		for (int i = 0; i < leftArray.length; i++) {
			leftArray[i] = A[i];
		}
		for (int i = 0; i < righttArray.length; i++) {
			righttArray[i] = A[i + leftArray.length];
		}

		int setNum = 2;

		Main.printArray(leftArray);
		/**
		 * 对半分，找出两两一组再排序。 例如一个集合有8个元素， 4个，4个，再分成2，2，2，2 将每2个比较，再将排序过的2合并，变成4，4，
		 * 再排序4，4，最后合并
		 */
		while (setNum <= leftArray.length) {

			List<Integer> tempList = new ArrayList<Integer>();
			int[] tempArray = new int[setNum];
			for (int i = 0; i < leftArray.length; i += setNum) {
				if ((i + (setNum / 2)) < leftArray.length) {
					System.out.println(leftArray[i] + " vs "
							+ leftArray[i + (setNum / 2)]);
					for (int j = 0; j < tempArray.length; j++) {
						tempArray[j] = leftArray[i + j];
					}
					tempArray = sort2Parts(tempArray, isAscending);
					for (int j = 0; j < tempArray.length; j++) {
						tempList.add(tempArray[j]);
					}
				} else {
					tempList.add(leftArray[i]);
				}
			}

			for (int j = 0; j < tempList.size(); j++) {
				leftArray[j] = tempList.get(j);
			}
			Main.printArray(leftArray);
			setNum *= 2;
		}

		// Main.printArray(leftArray);
		// Main.printArray(righttArray);
		//
		// for (int i = 0; i < leftArray.length; i++) {
		// A[i] = leftArray[i];
		// }
		// for (int i = 0; i < righttArray.length; i++) {
		// A[i + leftArray.length] = righttArray[i];
		// }

		result = sort2Parts(A, isAscending);
		return result;
	}

	/**
	 * 比较两数
	 * 
	 * @param array
	 *            欲排列的array
	 * @param num1position
	 *            array[num1position]
	 * @param num2position
	 *            array[num2position]
	 * @param isAscending
	 *            是否由小到大
	 * @return 排序结果
	 */
	private int[] swap2num(int[] array, int num1position, int num2position,
			boolean isAscending) {
		int temp;
		if ((array[num1position] < array[num2position] && !isAscending)
				|| (array[num1position] > array[num2position] && isAscending)) {
			temp = array[num1position];
			array[num1position] = array[num2position];
			array[num2position] = temp;
		}
		return array;

	}

	/**
	 * 分成左右两边排序，先把全部都两两比较排序（模拟最后一层），再分一半两边各取一个数做比较 所以最多只能比较四个数
	 * 
	 * @param array
	 *            欲排列的array
	 * @param isAscending
	 *            是否由小到大
	 * @return 排序结果
	 */
	private int[] sort2Parts(int[] array, boolean isAscending) {
		/**
		 * 回传结果
		 */
		int[] result = new int[array.length];
		/**
		 * 欲排列的array一半長度
		 */
		int halfLength = Math.round((float) array.length / (float) 2);
		/**
		 * 结果列表目前进度
		 */
		int resultPointer = 0;
		/**
		 * 左半边目前进度
		 */
		int leftPointer = 0;
		/**
		 * 右半边目前进度
		 */
		int rightPointer = leftPointer + halfLength;
		/**
		 * 右半边起始进度
		 */
		int rightStartPointer = halfLength;
		/**
		 * 欲排列的array長度
		 */
		int length = array.length;

		// 两两比较排序（模拟最后一层）
		for (int i = 0; i < array.length; i += 2) {
			if ((i + 1) < array.length)
				array = swap2num(array, i, i + 1, isAscending);
		}

		if (array.length == 2)
			return array;

		System.out.print("sort");
		Main.printArray(array);

		// 左右两边各挑出一数比较
		while (resultPointer < rightStartPointer && rightPointer < length) {
			System.out
					.println("比较[" + leftPointer + "],[" + rightPointer + "]");
			if (isAscending) {
				if (array[rightPointer] < array[leftPointer]) {
					System.out.println("拿出右[" + rightPointer + "]"
							+ array[rightPointer]);
					result[resultPointer] = array[rightPointer];
					resultPointer++;
					rightPointer++;
				} else {
					System.out.println("拿出左[" + leftPointer + "]"
							+ array[leftPointer]);
					result[resultPointer] = array[leftPointer];
					resultPointer++;
					leftPointer++;
				}
			} else {
				if (array[rightPointer] > array[leftPointer]) {
					System.out.println("拿出右[" + rightPointer + "]"
							+ array[rightPointer]);
					result[resultPointer] = array[rightPointer];
					resultPointer++;
					rightPointer++;
				} else {
					System.out.println("拿出左[" + leftPointer + "]"
							+ array[leftPointer]);
					result[resultPointer] = array[leftPointer];
					resultPointer++;
					leftPointer++;
				}
			}
		}
		// 加入所有右边剩下的数
		while (rightPointer < length) {
			System.out
					.println("剩右[" + rightPointer + "]" + array[rightPointer]);
			result[resultPointer] = array[rightPointer];
			rightPointer++;
			resultPointer++;
		}
		// 加入所有左边剩下的数
		while (leftPointer < halfLength) {
			System.out.println("剩左[" + leftPointer + "]" + array[leftPointer]);
			result[resultPointer] = array[leftPointer];
			leftPointer++;
			resultPointer++;
		}

		System.out.print("finished sorting");
		Main.printArray(result);
		return result;
	}

}
