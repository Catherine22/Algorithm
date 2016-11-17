package com.catherine.sort;

import com.catherine.Main;

public class MergeSort extends BaseSort {

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		// int halfLength = input.length / 2;

		int[] A = new int[] { 6, 2, 3};// 1, 4, 6, 2, 5, 7, 1, 4
		int[] result = new int[A.length];
		float layer = Math.round((float) A.length / 2.0f);
		if (layer == 1.0f) {// 代表长度=2，只有两个值

			result = swap2num(A, 0, 1, isAscending);
		}

		while (layer != 1.0f) {// 代表长度>2，对半分
			System.out.println("对半分");

			int halfLength = Math.round((float) A.length / (float) 2);// 2
			int resultPoint = 0;// 结果列表目前进度

			int leftPoint = 0;// 左半边目前进度
			int rightPoint = leftPoint + halfLength;// 右半边目前进度
			int rightStartPoint = 0;// 右半边起始进度
			int length = result.length;

			for (int i = 0; i < length; i += halfLength) {
				// 将array分一半，两边各自处理
				rightStartPoint = halfLength;// 1
				// 左右两边各挑出一数比较
				while (leftPoint < rightStartPoint && rightPoint < length) {
					System.out.println("[" + leftPoint + "," + rightPoint + "]");
					if (A[rightPoint] < A[leftPoint]) {
						System.out.println("右" + A[rightPoint]);
						result[resultPoint] = A[rightPoint];
						resultPoint++;
						rightPoint++;
					} else {
						System.out.println("左" + A[leftPoint]);
						result[resultPoint] = A[leftPoint];
						resultPoint++;
						leftPoint++;
					}
				}
				// 加入所有右边剩下的数
				while (rightPoint < length) {
					System.out.println("剩右" + A[rightPoint]);
					result[resultPoint] = A[rightPoint];
					rightPoint++;
					resultPoint++;
				}
				// 加入所有左边剩下的数
				while (leftPoint < rightStartPoint) {
					System.out.println("剩左" + A[leftPoint]);
					result[resultPoint] = A[leftPoint];
					leftPoint++;
					resultPoint++;
				}
			}
			Main.printArray(result);
			layer = Math.round(layer / 2.0f);
		}
		return result;
	}

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

}
