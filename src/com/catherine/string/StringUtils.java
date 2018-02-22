package com.catherine.string;

/**
 * String, 串<br>
 * 这边重点在于如何实现高效的indexOf接口
 * 
 * @author Catherine
 *
 */
public class StringUtils {

	/**
	 * 返回前缀，[0, k)
	 * 
	 * @param s
	 * @param k
	 * @return
	 */
	public static String prefix(String s, int k) {
		return null;
	}

	/**
	 * 返回后缀，[n-k, n)
	 * 
	 * @param s
	 * @param k
	 * @return
	 */
	public static String suffix(String s, int k) {
		return null;
	}

	/**
	 * 返回[i, k)
	 * 
	 * @param s
	 * @param i
	 * @param k
	 * @return
	 */
	public static String subStr(String s, int i, int k) {
		return suffix(prefix(s, k), i);
	}

	/**
	 * 串s是否存在某个子串p，返回位置。
	 * 
	 * @param s
	 *            串
	 * @param p
	 *            子串，或称模式串
	 * @return
	 */
	public static int indexOf(String s, String p) {
		return indexOf0(s, p);
	}

	/**
	 * 暴力解，一个一个比较。
	 * 
	 * @param s
	 * @param p
	 * @deprecated
	 * @return
	 */
	public static int indexOf0(String s, String p) {
		if (s.length() == 0)
			return 0;

		char[] sa = s.toCharArray();
		char[] pa = p.toCharArray();
		int countDown = pa.length;
		int last = sa.length - pa.length + 1;
		for (int i = 0; i < last; i++) {
			for (int j = 0; j < pa.length; j++) {
				if (sa[i + j] == pa[j]) {
					// System.out.println((i + j) + ":" + sa[i + j]);
					countDown--;
				} else
					break;
			}
			if (countDown == 0)
				return i;
			countDown = pa.length;
		}
		return -1;
	}
}
