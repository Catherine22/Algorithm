package com.catherine.data_type;

import java.util.Arrays;

import com.catherine.Main;

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
		long start = System.currentTimeMillis();
		if (array.length == 0 || toPos <= fromPos)
			throw new ArrayIndexOutOfBoundsException();
		// 此方法只能用于有序数组
		Arrays.sort(array);

		if (SHOW_DEBUG_LOG)
			Main.printArray("sorted:", array);

		int midPos = 0;
		int count = 0;
		boolean stop = false;
		boolean stopCheckingDup = false;
		while (!stop && !stopCheckingDup) {
			midPos = (fromPos + toPos) / 2;
			if (SHOW_DEBUG_LOG)
				System.out.println("mid[" + midPos + "]:" + array[midPos]);

			if (toPos - fromPos == 1) {// 剩两个数做比较
				if (element < array[toPos] && array[midPos] < element) {
					// 表示数值介于array[midPos]～array[toPos]之间，也就是两个都不是
					count += 2;
				} else if (element < array[toPos] && element < array[midPos]) {
					// 表示数值<array[midPos]
					count++;
					midPos--;
				} else if (element >= array[toPos]) {
					// 表示数值>=array[toPos]
					count += 2;
					midPos++;
				}
				stop = true;
			} else if (fromPos < toPos) {
				if (element < array[midPos]) {
					count++;
					toPos = midPos;
				} else if (element > array[midPos]) {
					count += 2;
					fromPos = midPos;
				} else {
					stop = true;
				}
			} else {
				stop = true;
			}

			// 检查是否有重复
			while (stop && !stopCheckingDup) {
				if (SHOW_DEBUG_LOG)
					System.out.printf("检查array[%d,%d]是否重复\n", midPos, midPos + 1);
				// count++;
				if (midPos < array.length - 1 && array[midPos] == array[midPos + 1])
					midPos++;
				else
					stopCheckingDup = true;
			}
		}
		if (SHOW_DEBUG_LOG)
			System.out.printf("比较了%d次\n", count);
		long end = System.currentTimeMillis();
		if (SHOW_DEBUG_LOG)
			System.out.println("binSearch() took " + (end - start) + " ms");
		return midPos;
	}

	/**
	 * 比起binSearch(), binSearch2()只分成两部分查找 <br>
	 * 在最好及最坏情况下都逊于binSearch()，但整体来说可减少比较次数。 <br>
	 * 
	 * 将array对切，分成左、右兩部分，每次比较查找元素是位于那个区间 <br>
	 * 若查找元素&lt;中间元素，将toPos改成原中间元素，查找范围变成fromPos～toPos（原middlePos），产生新的middlePos
	 * <br>
	 * 若查找元素&gt;=中间元素，将fromPos改成原中间元素，查找范围变成fromPos（原middlePos）～toPos，
	 * 产生新的middlePos <br>
	 * <br>
	 * <br>
	 * 平均查找时间约O(1.5logn)。 <br>
	 * 延伸出一个不公平的情形，比较时先比较左边再比较右边，所以如果查找元素&lt;中间元素只需比较1次，如果查找元素&gt;中间元素，必须比较两次。
	 * <br>
	 * 使用Fibonacci Searching提高效能
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
	public int binSearch2(int[] array, int element, int fromPos, int toPos) {
		long start = System.currentTimeMillis();
		if (array.length == 0 || toPos <= fromPos)
			throw new ArrayIndexOutOfBoundsException();
		// 此方法只能用于有序数组
		Arrays.sort(array);

		if (SHOW_DEBUG_LOG)
			Main.printArray("sorted:", array);

		int midPos = 0;
		int count = 0;
		boolean stop = false;
		boolean stopCheckingDup = false;
		while (!stop && !stopCheckingDup) {
			midPos = (fromPos + toPos) / 2;
			if (SHOW_DEBUG_LOG)
				System.out.println("mid[" + midPos + "]:" + array[midPos]);
			if (toPos - fromPos == 1) {// 剩两个数做比较
				if (element < array[toPos] && array[midPos] < element) {
					// 表示数值介于array[midPos]～array[toPos]之间，也就是两个都不是
					count += 2;
				} else if (element < array[toPos] && element < array[midPos]) {
					// 表示数值<array[midPos]
					count++;
					midPos--;
				} else if (element >= array[toPos]) {
					// 表示数值>=array[toPos]
					count += 2;
					midPos++;
				}
				stop = true;
			} else if (fromPos < toPos) {
				if (element < array[midPos]) {
					count++;
					toPos = midPos - 1;
				} else {
					count += 2;
					fromPos = midPos;
				}
			} else {
				stop = true;
			}

			// 检查是否有重复
			while (stop && !stopCheckingDup) {
				if (SHOW_DEBUG_LOG)
					System.out.printf("检查array[%d,%d]是否重复\n", midPos, midPos + 1);
				// count++;
				if (midPos < array.length - 1 && array[midPos] == array[midPos + 1])
					midPos++;
				else
					stopCheckingDup = true;
			}
		}

		if (SHOW_DEBUG_LOG)
			System.out.printf("比较了%d次\n", count);
		long end = System.currentTimeMillis();
		if (SHOW_DEBUG_LOG)
			System.out.println("binSearch2() took " + (end - start) + " ms");
		return midPos;
	}

	/**
	 * 
	 * 延续binary searching的逻辑，解决左右两边比较成本不同的问题（左+1、右+2），将左边的元素增加，进而达到补偿。 <br>
	 * 
	 * Fibonacci searching在常系数的意义上优于Binary searching<br>
	 * If fib(k)-1 is equal to the size of array, split array at fib(k-1)-1.<br>
	 * <br>
	 * 左fib(k-1)-1个，右fib(k-2)-1个，中间一个
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
		long start = System.currentTimeMillis();
		int fibIndex = isFibNumMinusOne(array.length);
		if (fibIndex == -1) {// array个数不是fib(k)-1，直接用一般二元搜寻
			System.out.println("array个数不是fib(k)-1，直接用一般二元搜寻");
			return binSearch(array, element, fromPos, toPos);
		} else {
			if (array.length == 0 || toPos <= fromPos)
				throw new ArrayIndexOutOfBoundsException();
			// 此方法只能用于有序数组
			Arrays.sort(array);

			if (SHOW_DEBUG_LOG)
				Main.printArray("sorted:", array);

			int midPos = 0;
			int count = 0;
			boolean stop = false;
			boolean stopCheckingDup = false;

			midPos = getFibNum(fibIndex - 1) - 1;
			while (!stop && fromPos < toPos && count < 8) {
				if (SHOW_DEBUG_LOG)
					System.out.println("mid[" + midPos + "]:" + array[midPos]);
				if (element > array[midPos]) {

				} else if (element < array[midPos]) {

				} else {
					stop = true;
				}
				
				
				// 检查是否有重复
				while (stop && !stopCheckingDup) {
					if (SHOW_DEBUG_LOG)
						System.out.printf("检查array[%d,%d]是否重复\n", midPos, midPos + 1);
					// count++;
					if (midPos < array.length - 1 && array[midPos] == array[midPos + 1])
						midPos++;
					else
						stopCheckingDup = true;
				}
			}

			if (SHOW_DEBUG_LOG)
				System.out.printf("比较了%d次\n", count);
			long end = System.currentTimeMillis();
			if (SHOW_DEBUG_LOG)
				System.out.println("fibSearch() took " + (end - start) + " ms");
			return midPos;
		}
	}

	/**
	 * 
	 * Recognizing Fibonacci numbers (1, 1, 2, 3, 5, 8, 13...) <br>
	 * <br>
	 * Return the position of input num in Fibonacci Sequence. <br>
	 * F(n) = (1/Math.sqrt(5)) * (Math.pow(((1 + Math.sqrt(5))/2), n) -
	 * Math.pow(((1 - Math.sqrt(5))/2), n)) <br>
	 * <br>
	 * <br>
	 * To arise whether a positive integer x is a Fibonacci number. <br>
	 * <br>
	 * <br>
	 * 
	 * @param num
	 *            a positive number
	 * @return fibIndex (-1 as not found and 1 as the first num)
	 */
	public int isFibNum(int num) {
		// 在Fibonacci里的顺序，从1开始
		int fibIndex = 1;
		// 在Fibonacci里第fibIndex个值是什么
		int fibValue = 1;

		while (fibValue < num) {
			fibIndex++;
			fibValue = (int) ((1 / Math.sqrt(5))
					* (Math.pow(((1 + Math.sqrt(5)) / 2), fibIndex) - Math.pow(((1 - Math.sqrt(5)) / 2), fibIndex)));
		}
		if (fibValue == num)
			return fibIndex;
		else
			return -1;
	}

	/**
	 * Get fib(index)
	 * 
	 * @param index
	 *            position
	 * @return Fibonacci number
	 */
	private int getFibNum(int index) {
		return (int) ((1 / Math.sqrt(5))
				* (Math.pow(((1 + Math.sqrt(5)) / 2), index) - Math.pow(((1 - Math.sqrt(5)) / 2), index)));
	}

	/**
	 * Check if num is equal to fib(k)-1
	 * 
	 * @param num
	 *            a positive number
	 * @return fibIndex (-1 as not found)
	 */
	private int isFibNumMinusOne(int num) {
		int fibIndex = 1;
		int fibValue = 1;

		while (fibValue < num) {
			fibIndex++;
			fibValue = (int) ((1 / Math.sqrt(5))
					* (Math.pow(((1 + Math.sqrt(5)) / 2), fibIndex) - Math.pow(((1 - Math.sqrt(5)) / 2), fibIndex)));
		}
		if (fibValue - 1 == num)
			return fibIndex;
		else
			return -1;
	}
}
