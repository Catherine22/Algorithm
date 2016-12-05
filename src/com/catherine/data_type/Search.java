package com.catherine.data_type;

import java.util.Arrays;

/**
 * 这边的search应含多功能，而非仅仅寻找的功能而已。 <br>
 * 例如在插入新元素时，通过search的调用，找到一个适合插入的位置（这样能让插入新元素后仍保持排序）。 <br>
 * <br>
 * <br>
 * 假如查找失败，在array中找到一个值&lt;=查找元素，并返回该元素的位置。 <br>
 * E.g. 查找元素&lt;array中最小值，返回fromPos-1；查找元素&gt;array中最小值，返回toPos-1； <br>
 * 若查找成功，返回该区段最后一个值。 <br>
 * <br>
 * <br>
 * 之后应用insert时，就能插入返回值+1的位置。
 * 
 * @author Catherine
 *
 */
public class Search {
	protected final boolean SHOW_DEBUG_LOG = true;

	/**
	 * 将array对切，分成左、中、右三部分，每次比较查找元素是位于那个区间 <br>
	 * 若查找元素&lt;中间元素，将toPos改成原中间元素，查找范围变成fromPos～toPos（原middlePos），产生新的middlePos
	 * <br>
	 * 若查找元素&gt;中间元素，将fromPos改成原中间元素，查找范围变成fromPos（原middlePos）～toPos，
	 * 产生新的middlePos <br>
	 * 若查找元素=中间元素，代表找到了。 <br>
	 * 
	 * @param array
	 *            selected-array
	 * @param element
	 *            search the integer in selected-array
	 * @param fromPos
	 *            search range from fromPos to toPos
	 * @param toPos
	 *            search range from fromPos to toPos
	 * @return position 0, 1, 1, 1, 3, 3, 7, 7, 64
	 */
	public int binSearch(int[] array, int element, int fromPos, int toPos) {
		if (array.length == 0 || toPos <= fromPos)
			throw new ArrayIndexOutOfBoundsException();
		//此方法只能用于有序数组
		Arrays.sort(array);

		int midPos = 0;
		int count = 0;
		boolean stop = false;
		while (!stop && fromPos < toPos) {
			midPos = (fromPos + toPos) / 2;
			if (SHOW_DEBUG_LOG)
				System.out.println("mid=" + midPos);
			if (midPos == fromPos) {// 剩两个数做比较
				count++;
				if (element < array[toPos] && array[midPos] < element) {
					// 表示数值介于array[midPos]～array[toPos]之间
				} else if (element < array[toPos] && element < array[midPos]) {
					midPos--;// 表示数值<array[midPos]
				} else if (element == array[toPos]) {
					midPos++;// 表示数值==array[toPos]
				} else if (array[toPos] < element) {
					midPos++;// 表示数值>array[toPos]
				}
				stop = true;
			} else {
				count++;
				if (element < array[midPos])
					toPos = midPos;
				else if (array[midPos] < element)
					fromPos = midPos;
				else {
					count--;
					// 检查是否有重复
					while (!stop) {
						if (SHOW_DEBUG_LOG)
							System.out.printf("检查array[%d,%d]是否重复\n", midPos, midPos + 1);
						count++;
						if (array[midPos] != array[midPos + 1]) {
							stop = true;
						} else
							midPos++;
					}
				}
			}
		}
		if (SHOW_DEBUG_LOG)
			System.out.printf("比较了%d次\n", count);
		return midPos;
	}

	public int fibSearch(int[] array, int element, int fromPos, int toPos) {

		return 0;
	}
}
