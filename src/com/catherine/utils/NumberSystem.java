package com.catherine.utils;

import java.util.Stack;

public class NumberSystem {

	public final static int DECIMAL = 10;
	public final static int HEX = 16;
	public final static int OCTAL = 8;
	public final static int BINARY = 2;

	// 预设输出是否为大写
	private final boolean isUpperCase = true;

	/**
	 * 利用stack实现进制转换，十进制转其他。<br>
	 * 效果等同于Integer.toString(number, base)<br>
	 * char A从65开始<br>
	 * char a从97开始<br>
	 * 
	 * @param number
	 * @param base
	 * @return
	 */
	public String convertDecimalToOthers(int number, int base) {
		String result = "";
		Stack<Integer> history = new Stack<>();

		// 超出字母范围，直接返回原来的数值
		if ((base - 10) > 26)
			return String.valueOf(number);

		// 大小写切换
		int cTemp = 55;
		if (!isUpperCase)
			cTemp = 87;

		int i = number % base;
		while (number >= base) {
			history.push(i);
			// System.out.println(i);
			number /= base;
			i = number % base;
		}
		history.push(number);

		while (!history.isEmpty()) {
			int n = history.pop();
			if (n > 9)
				result += (char) (n + cTemp);
			else
				result += String.valueOf(n);
		}
		return result;
	}
}
