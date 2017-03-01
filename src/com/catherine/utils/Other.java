package com.catherine.utils;

import java.util.ArrayList;
import java.util.List;

public class Other {

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
}
