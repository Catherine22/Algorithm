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
	 * <br>
	 * <br>
	 * 平均查找时间约O(1.5logn)。 <br>
	 * 延伸出一个不公平的情形，比较时先比较左边再比较右边，所以如果查找元素&lt;中间元素只需比较1次，如果查找元素&gt;中间元素，必须比较两次。
	 * <br>
	 * 使用fibonacci Searching提高效能
	 * 
	 * @param array
	 *            selected-array
	 * @param element
	 *            search the integer in selected-array
	 * @param fromPos
	 *            search range from fromPos to toPos
	 * @param toPos
	 *            search range from fromPos to toPos
	 * @return position
	 */
	public int binSearch(int[] array, int element, int fromPos, int toPos) {
		if (array.length == 0 || toPos <= fromPos)
			throw new ArrayIndexOutOfBoundsException();
		// 此方法只能用于有序数组
		Arrays.sort(array);

		int midPos = 0;
		int count = 0;
		boolean stop = false;
		while (!stop && fromPos < toPos) {
			midPos = (fromPos + toPos) / 2;
			if (SHOW_DEBUG_LOG)
				System.out.println("mid=" + midPos);
			if (midPos == fromPos) {// 剩两个数做比较
				if (element < array[toPos] && array[midPos] < element) {
					count += 2;
					// 表示数值介于array[midPos]～array[toPos]之间
				} else if (element < array[toPos] && element < array[midPos]) {
					count++;
					midPos--;// 表示数值<array[midPos]
				} else if (element == array[toPos]) {
					count += 2;
					midPos++;// 表示数值==array[toPos]
				} else if (array[toPos] < element) {
					count += 2;
					midPos++;// 表示数值>array[toPos]
				}
				stop = true;
			} else {
				if (element < array[midPos]) {
					count++;
					toPos = midPos;
				} else if (array[midPos] < element) {
					count += 2;
					fromPos = midPos;
				} else {
					// 检查是否有重复
					while (!stop) {
						if (SHOW_DEBUG_LOG)
							System.out.printf("检查array[%d,%d]是否重复\n", midPos, midPos + 1);
						// count++;
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

	/**
	 * 
	 * 延续binary searching的逻辑，解决左右两边比较成本不同的问题（左+1、右+2），将左边的元素增加，进而达到补偿。 <br>
	 * 
	 * Fibonacci searching在常系数的意义上优于Binary searching
	 * 
	 * @param array
	 *            selected-array
	 * @param element
	 *            search the integer in selected-array
	 * @param fromPos
	 *            search range from fromPos to toPos
	 * @param toPos
	 *            search range from fromPos to toPos
	 * @return position
	 */
	public int fibSearch(int[] array, int element, int fromPos, int toPos) {
		includedInFib(1);
		includedInFib(3);
		includedInFib(5);
		includedInFib(8);
		includedInFib(7);
		includedInFib(4);
		return 0;
	}

	/**
	 * Return the position of input num in Fibonacci Sequence. <br>
	 * F(n) = (1/Math.sqrt(5)) * (Math.pow(((1 + Math.sqrt(5)) /(1/2)), n) -
	 * Math.pow(((1 - Math.sqrt(5)) /(1/2)), n)) <br>
	 * <br>
	 * <br>
	 * To arise whether a positive integer x is a Fibonacci number. <br>
	 * This is true if and only if one or both of 5*Math.pow(x,2)+4 or
	 * 5*Math.pow(x,2)-4 is a perfect square.
	 * 
	 * @param num
	 *            a positive number
	 * @return fibIndex (-1 as not found)
	 */
	private int includedInFib(int num) {
		int fibIndex = -1;
		int temp = 1;
		// 1, 1, 2, 3, 5, 8, 13...
		int calValue = (int) (5 * Math.pow(num, 2));
		while (Math.pow(temp, 2) <= (calValue - 4)) {
			// 先检查是不是Fibonacci数列
			if ((calValue + 4 == Math.pow(temp, 2)) || (calValue - 4 == Math.pow(temp, 2))) {
				// num是Fibonacci数列，找出位置

			}
			temp++;
		}
		return fibIndex;
	}
}
