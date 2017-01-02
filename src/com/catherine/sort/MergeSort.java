package com.catherine.sort;


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
public class MergeSort extends BaseSort{
	private static final String TAG = "MergeSort";
	@Override
	public int[] sort(int[] a, boolean isAscending) {
		int[] tmp = new int[a.length];
		mergeSort(a, tmp, 0, a.length - 1, isAscending);
		return a;
	}

	private void mergeSort(int[] a, int[] tmp, int left, int right,
			boolean isAscending) {
		if (left < right) {
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center, isAscending);
			mergeSort(a, tmp, center + 1, right, isAscending);
			merge(a, tmp, left, center + 1, right, isAscending);
		}
	}

	private void merge(int[] a, int[] tmp, int left, int right, int rightEnd,
			boolean isAscending) {
		int leftEnd = right - 1;
		int k = left;
		int num = rightEnd - left + 1;

		while (left <= leftEnd && right <= rightEnd)
			if (isAscending) {
				if (a[left] <= a[right])
					tmp[k++] = a[left++];
				else
					tmp[k++] = a[right++];
			} else {
				if (a[left] >= a[right])
					tmp[k++] = a[left++];
				else
					tmp[k++] = a[right++];
			}

		while (left <= leftEnd)
			// Copy rest of first half
			tmp[k++] = a[left++];

		while (right <= rightEnd)
			// Copy rest of right half
			tmp[k++] = a[right++];

		// Copy tmp back
		for (int i = 0; i < num; i++, rightEnd--)
			a[rightEnd] = tmp[rightEnd];
	}
}
