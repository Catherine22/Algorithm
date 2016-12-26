package com.catherine.sort;

import java.util.ArrayList;
import java.util.List;

import com.catherine.Main;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * best: n log n <br>
 * average: n log n <br>
 * worst: n log n <br>
 * <br>
 * memory: worst case is n
 * 
 * @author Catherine
 *
 */
public class MergeSort extends BaseSort {
	private static final String TAG = "MergeSort";

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		int halfLen = (int) Math.round((float) input.length / 2.0f);
		int[] leftArray = new int[halfLen];
		int[] righttArray = new int[input.length - halfLen];

		for (int i = 0; i < leftArray.length; i++) {
			leftArray[i] = input[i];
		}
		for (int i = 0; i < righttArray.length; i++) {
			righttArray[i] = input[i + leftArray.length];
		}

		leftArray = sortOneSide(isAscending, leftArray);
		righttArray = sortOneSide(isAscending, righttArray);

		for (int i = 0; i < input.length; i++) {
			if (i < leftArray.length)
				input[i] = leftArray[i];
			else
				input[i] = righttArray[i - leftArray.length];
		}

		if (SHOW_DEBUG_LOG)
			Main.printArray(TAG, input);

		return sort2Parts(input, isAscending);
	}

	/**
	 * 对半分，找出两两一组再排序。 例如一个集合有8个元素， 4个，4个，再分成2，2，2，2 将每2个比较，再将排序过的2合并，变成4，4，
	 * 再排序4，4，最后合并
	 */
	private int[] sortOneSide(boolean isAscending, int[] array) {
		int setNum = 2;
		while (setNum <= array.length) {
			List<Integer> tempList = new ArrayList<Integer>();
			int[] tempArray = new int[setNum];
			for (int i = 0; i < array.length; i += setNum) {
				// 集合里有基数个元素，会在最后一次循环是多出一个，须另外处理
				if ((i + (setNum / 2)) < array.length) {
					for (int j = 0; j < tempArray.length; j++) {
						tempArray[j] = array[i + j];
					}
					tempArray = sort2Parts(tempArray, isAscending);
					for (int j = 0; j < tempArray.length; j++) {
						tempList.add(tempArray[j]);
					}
				} else {
					// 处理剩一个数的情形（最后三个数排序）
					int[] biggerArray = new int[3];
					biggerArray[0] = tempList.get(tempList.size() - 2);
					biggerArray[1] = tempList.get(tempList.size() - 1);
					biggerArray[2] = array[i];

					biggerArray = sort2Parts(biggerArray, isAscending);

					tempList.set(tempList.size() - 2, biggerArray[0]);
					tempList.set(tempList.size() - 1, biggerArray[1]);
					tempList.add(biggerArray[2]);
				}
			}

			for (int j = 0; j < tempList.size(); j++) {
				array[j] = tempList.get(j);
			}
			setNum *= 2;
		}
		return array;
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
	private int[] swap2num(int[] array, int num1position, int num2position, boolean isAscending) {
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
		if (array.length == 2)
			return swap2num(array, 0, 1, isAscending);

		if (SHOW_DEBUG_LOG) {
			System.out.print("sort");
			Main.printArray(TAG, array);
		}

		// 左右两边各挑出一数比较
		while (leftPointer < rightStartPointer && rightPointer < length) {
			if (SHOW_DEBUG_LOG)
				System.out.println("比较[" + leftPointer + "],[" + rightPointer + "]");
			if (isAscending) {
				if (array[rightPointer] < array[leftPointer]) {
					if (SHOW_DEBUG_LOG)
						System.out.println("拿出右[" + rightPointer + "]" + array[rightPointer]);
					result[resultPointer++] = array[rightPointer++];
				} else {
					if (SHOW_DEBUG_LOG)
						System.out.println("拿出左[" + leftPointer + "]" + array[leftPointer]);
					result[resultPointer++] = array[leftPointer++];
				}
			} else {
				if (array[rightPointer] > array[leftPointer]) {
					if (SHOW_DEBUG_LOG)
						System.out.println("拿出右[" + rightPointer + "]" + array[rightPointer]);
					result[resultPointer++] = array[rightPointer++];
				} else {
					if (SHOW_DEBUG_LOG)
						System.out.println("拿出左[" + leftPointer + "]" + array[leftPointer]);
					result[resultPointer++] = array[leftPointer++];
				}
			}
		}
		// 加入所有右边剩下的数
		while (rightPointer < length) {
			if (SHOW_DEBUG_LOG)
				System.out.println("剩右[" + rightPointer + "]" + array[rightPointer]);
			result[resultPointer++] = array[rightPointer++];
		}
		// 加入所有左边剩下的数
		while (leftPointer < halfLength) {
			if (SHOW_DEBUG_LOG)
				System.out.println("剩左[" + leftPointer + "]" + array[leftPointer]);
			result[resultPointer++] = array[leftPointer++];
		}
		if (SHOW_DEBUG_LOG) {
			System.out.print("sorting accomplished");
			Main.printArray(TAG, result);
		}
		return result;
	}

}
