package com.catherine.data_type;

import com.catherine.Main;

/**
 * 看的数组A[n]直觉连续的内存位置<br>
 * 物理地址 = A[0]的位置 + i*s，s为一元素占用的空间量 所以又称作Linear array。
 * 
 * 向量是将数组做抽象和泛化，按线性次序封装而成。<br>
 * 例如定义的ADT接口有size(), get(r), insert(r,e)...
 * 
 * 
 * 
 * Abstract Data Type(ADT) is a data type, where only behavior is defined but
 * not implementation.T.
 * 
 * Opposite of ADT is Concrete Data Type (CDT), where it contains an
 * implementation of ADT.
 * 
 * Examples: Array, List, Map, Queue, Set, Stack, Table, Tree, and Vector are
 * ADTs. Each of these ADTs have many implementations i.e. CDT. Container is a
 * high-level ADT of above all ADTs.
 * 
 * Real life example: Book is Abstract (Telephone Book is an implementation)
 * 
 * @author Catherine
 *
 */
public class Sequence {
	protected final boolean SHOW_DEBUG_LOG = true;
	private int[] array = new int[10];
	private int[] temp;

	/**
	 * 在最坏情况下，递增式扩容须耗时O(n)，分摊每次扩容成本O(1)<br>
	 * 优点是空间利用率(已使用／全部空间)=100%
	 */
	public void increaseArray() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			if (i >= array.length) {
				temp = array;
				array = new int[temp.length + 1];

				for (int j = 0; j < temp.length; j++)
					array[j] = temp[j];
			}
		}
		long end = System.currentTimeMillis();
		if (SHOW_DEBUG_LOG)
			System.out.println("increaseArray() took " + (end - start) + " ms");
	}

	/**
	 * 在空间不足时，使用加倍空间扩展优于逐渐空间扩展increaseArray()，<br>
	 * 加倍式扩容虽然牺牲内存空间，但在运行速度上远超越递增式扩容。<br>
	 * 递增式扩容须耗时O(1)，空间利用率(已使用／全部空间)>50%<br>
	 * 结论：加倍式扩容优于递增式扩容
	 * 
	 */
	public void doubleArray() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			if (i >= array.length) {
				temp = array;
				array = new int[temp.length * 2];

				for (int j = 0; j < temp.length; j++)
					array[j] = temp[j];
			}
		}
		long end = System.currentTimeMillis();
		if (SHOW_DEBUG_LOG)
			System.out.println("doubleArray() took " + (end - start) + " ms");
	}

	/**
	 * 在array的position位置插入value，position后面的元素全右移一位，必要时扩容。
	 * 
	 * @param array
	 * @param position
	 * @param value
	 * @return
	 */
	public int[] insert(int[] array, int position, int value) {
		int[] result = doubleArray(array);
		Main.printArray("test", result);
		// position后面的元素全右移一位，从最后一位开始检查
		for (int i = array.length - 1; i >= position; i--) {
			result[i + 1] = result[i];
			if (position == i)
				result[i] = value;
		}
		return result;
	}

	/**
	 * 移除array中第fromPos到第toPos位置的所有元素，toPos后的元素自动向左移动，视情况加倍式缩小array
	 * 
	 * @param array
	 * @param fromPos
	 * @param toPos
	 * @return
	 */
	public int[] remove(int[] array, int fromPos, int toPos) {
		int pointer = fromPos;
		for (int i = toPos + 1; i < array.length; i++) {
			array[pointer++] = array[i];
		}

		int movedNum = toPos - fromPos + 1;
		for (int i = 0; i < movedNum; i++) {
			array[array.length - 1 - i] = 0;
		}

		if (movedNum >= (float) array.length / 2)
			array = shrink(array);
		return array;

	}

	/**
	 * 移除一个，可以直接当成一种移除区间
	 * @param array
	 * @param pos
	 * @return
	 */
	public int[] remove(int[] array, int pos) {
		array = remove(array, pos, pos);
		return array;

	}

	/**
	 * 逆向查找，检查数组中是否包含value，没有则返回非法位置-1 <br>
	 * 复杂度O(1)~O(n)
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public int find(int[] array, int value) {
		int pointer = array.length;
		// 停止条件 1. 扫描范围超出合法位置（由后往前检查） 2. 找到value
		while ((0 < pointer--) && (array[pointer] != value))
			;
		if (SHOW_DEBUG_LOG)
			System.out.println(pointer);
		return pointer;
	}

	/**
	 * 加倍式扩容
	 * 
	 * @param array
	 * @return
	 */
	private int[] doubleArray(int[] array) {
		temp = array;
		array = new int[temp.length * 2];

		for (int j = 0; j < temp.length; j++)
			array[j] = temp[j];
		return array;
	}

	/**
	 * 加倍式缩容 把0当成空值，重复缩容
	 * 
	 * @param array
	 * @return
	 */
	private int[] shrink(int[] array) {
		int nullNum = 0;
		// 用Math.ceil无条件进位，用（float）保证运算结果不会被去小数点
		temp = new int[(int) Math.ceil((float) array.length / 2)];
		for (int j = 0; j < temp.length; j++) {
			temp[j] = array[j];

			// 把0当成空值，重复缩容
			if (temp[j] == 0) {
				nullNum++;
			}
		}
		// 把0当成空值，重复缩容
		if (nullNum >= (temp.length / 2))
			return shrink(temp);
		return temp;
	}
}
