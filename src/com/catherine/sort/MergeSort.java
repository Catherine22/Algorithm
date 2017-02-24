package com.catherine.sort;

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
	int[] tmp;

	@Override
	public int[] sort(int[] a, boolean isAscending) {
		TrackLog tLog = new TrackLog("MergeSort");
		Analysis.startTracking(tLog);

		tmp = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			tmp[i] = a[i];
		}
		mergeSort(a, 0, a.length - 1, isAscending);

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return tmp;
	}

	/**
	 * 使用递归不断重复进行（排左边+排右边+整合）
	 * 
	 * @param a
	 *            欲排序数组
	 * @param left
	 *            起始位置
	 * @param right
	 *            结束位置
	 * @param isAscending
	 *            是否递增
	 */
	private void mergeSort(int[] a, int left, int right, boolean isAscending) {
		if (right > left) {
			int center = (left + right) / 2;
			// 排左边
			if (SHOW_DEBUG_LOG)
				System.out.println("排左边:" + left + "/" + center);
			mergeSort(a, left, center, isAscending);
			// 排右边
			if (SHOW_DEBUG_LOG)
				System.out.println("排右边:" + (center + 1) + "/" + right);
			mergeSort(a, center + 1, right, isAscending);
			// 整合
			if (SHOW_DEBUG_LOG)
				System.out.println("排整合:" + left + "/" + center + "/" + right);
			merge(a, left, center, right, isAscending);
		}
	}

	private void merge(int[] a, int lStart, int center, int rEnd, boolean isAscending) {
		int rStart = center + 1;// 右边起始位置
		int lEnd = center;// 左边结束位置
		int header = lStart;// 填入回传数组的指针

		if (SHOW_DEBUG_LOG)
			System.out.println("l from " + lStart + " to " + lEnd + ", r from " + rStart + " to " + rEnd);

		if (SHOW_DEBUG_LOG)
			Main.printArray("STEP0", tmp);

		while (lStart <= lEnd && rStart <= rEnd) {
			if (isAscending) {
				if (a[rStart] <= a[lStart])
					tmp[header++] = a[rStart++];
				else
					tmp[header++] = a[lStart++];

				if (SHOW_DEBUG_LOG)
					Main.printArray("STEP1", tmp);
			} else {
				if (a[rStart] >= a[lStart])
					tmp[header++] = a[rStart++];
				else
					tmp[header++] = a[lStart++];

				if (SHOW_DEBUG_LOG)
					Main.printArray("STEP1", tmp);
			}
		}

		while (rStart <= rEnd) // 把剩下的都填入
			tmp[header++] = a[rStart++];

		while (lStart <= lEnd) // 把剩下的都填入
			tmp[header++] = a[lStart++];

		if (SHOW_DEBUG_LOG)
			Main.printArray("STEP2", tmp);

		for (int i = 0; i < a.length; i++) {
			a[i] = tmp[i];
		}

		if (SHOW_DEBUG_LOG)
			Main.printArray("STEP3", tmp);
	}

}
