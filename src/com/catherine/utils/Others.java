package com.catherine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Others {

	/**
	 * n&lt;=1 print 1 <br>
	 * n%2==0 print n/2 <br>
	 * n%2==1 print 3n + 1 <br>
	 * <br>
	 * For instance, starting with n = 12, one gets the sequence 12, 6, 3, 10,
	 * 5, 16, 8, 4, 2, 1.
	 * 
	 * @param num
	 *            number
	 * 
	 * @return Hailstone array
	 */
	public List<Integer> getHailstone(int num) {
		List<Integer> result = new ArrayList<>();
		int calculate = num;

		if (calculate <= 1)
			result.add(1);

		while (calculate > 1) {
			calculate = (calculate % 2 == 1) ? (3 * calculate + 1) : (calculate / 2);
			result.add(calculate);
		}
		return result;
	}

	/**
	 * Recursion
	 * 
	 * @param num
	 *            number
	 * @return fibonacci array[num]
	 */
	public int fibonacci(int num) {
		if (num == 1 || num == 2) {
			return 1;
		} else {
			return fibonacci(num - 1) + fibonacci(num - 2);
		}
	}

	/**
	 * Input an expression included curly braces, parentheses or square
	 * brackets, and return if it's a legal sentences.<br>
	 * 
	 * @param expression
	 *            e.g. (1/Math.sqrt(5)) * (Math.pow(((1 + Math.sqrt(5))/2), n) -
	 *            Math.pow(((1 - Math.sqrt(5))/2), n))
	 * @return
	 */
	public boolean isBracketsCorrect(String expression) {
		Stack<String> leftBrackets = new Stack<>();

		try {
			for (int i = 0; i < expression.length(); i++) {
				String c = String.valueOf(expression.charAt(i));
				if (c.equals("("))
					leftBrackets.push("C");
				else if (c.equals("["))
					leftBrackets.push("[");
				else if (c.equals("{"))
					leftBrackets.push("{");
				else if (c.equals(")"))
					leftBrackets.pop();
				else if (c.equals("]"))
					leftBrackets.pop();
				else if (c.equals("}"))
					leftBrackets.pop();
			}
			return (leftBrackets.isEmpty());
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * h(0)=1<br>
	 * h(1)=1<br>
	 * n>=2,<br>
	 * h(n) = h(0)*h(n-1)+h(1)*h(n-2) + ... + h(n-1)*h(0)<br>
	 * = h(n-1)*(4*n-2)/(n+1); <br>
	 * 解法1——公式解h(n) = h(n-1)*(4*n-2)/(n+1); <br>
	 * 
	 * @param index
	 * @return 卡特兰数
	 */
	public int getCatalan1(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n<0");
		if (n <= 1)
			return 1;

		int catalan = getCatalanKernel(n);
		return catalan;
	}

	/**
	 * 递归推导公式
	 * 
	 * @param n
	 * @return
	 */
	private int getCatalanKernel(int n) {
		if (n <= 1)
			return 1;
		return getCatalanKernel(n - 1) * (4 * n - 2) / (n + 1);
	}

	/**
	 * 解法2，建立卡特兰数列表
	 */
	private List<Integer> catalans;

	/**
	 * h(0)=1<br>
	 * h(1)=1<br>
	 * n>=2,<br>
	 * h(n) = h(0)*h(n-1)+h(1)*h(n-2) + ... + h(n-1)*h(0)<br>
	 * = h(n-1)*(4*n-2)/(n+1); <br>
	 * 解法2——h(n) = h(0)*h(n-1)+h(1)*h(n-2) + ... + h(n-1)*h(0)<br>
	 * 
	 * @param index
	 * @return 卡特兰数
	 */
	public int getCatalan2(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n<0");
		catalans = new ArrayList<>();
		catalans.add(1);// n=0
		catalans.add(1);// n=1
		calculateCatalan(n);
		// 释放内存
		int ans = catalans.get(n);
		catalans.clear();
		return ans;
	}

	/**
	 * 递归原公式
	 * 
	 * @param n
	 * @return
	 */
	private int calculateCatalan(int n) {
		if (n <= 1)
			return 1;
		int catalan = 0;
		int head = n;
		if (catalans.size() - 1 < n) {
			while (head > 0) {
				catalan += calculateCatalan(head - 1) * calculateCatalan(catalans.size() - head);
				head--;
			}
			catalans.add(catalan);
		}
		return catalans.get(n);
	}

}
