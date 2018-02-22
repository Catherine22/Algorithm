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
	 *            母串，或称本文串
	 * @param p
	 *            子串，或称模式串
	 * @exception IndexOutOfBoundsException
	 * @return position
	 */
	public static int indexOf(String s, String p) {
		return indexOfKMP(s, p);
	}

	/**
	 * BF算法，即暴风(Brute Force)算法，一种蛮力算法，一个一个比较。<br>
	 * 每次都是先比对子串的第一个，如果相同就比对子串的第二个......以此类推。
	 * 
	 * @param s
	 *            母串，或称本文串
	 * @param p
	 *            子串，或称模式串
	 * @deprecated
	 * @exception IndexOutOfBoundsException
	 * @return position
	 */
	public static int indexOfBF(String s, String p) {
		if (p.length() == 0)
			return 0;
		if (p.length() > s.length())
			throw new IndexOutOfBoundsException("p must be smaller than s.");

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

	/**
	 * KMP算法<br>
	 * 在蛮力算法中，每次都是先比对子串的第一个，如果相同就比对子串的第二个......以此类推。<br>
	 * 最花时间的就是第一层迭代的比较，如果不用一个一个比就能改善效能。也就是KMP的目的。
	 * 假如s有100个，p有7个，当s[16]==p[0]，进入下一层迭代，比到s[20]的时候不符合，但前面四个(s[16]~s[19])都符合，
	 * 第五个也就是p[4]不同，在这种情况下， 不必非得从s[17]和p[0]继续比，可能是s[17] ～
	 * s[20]开始，KMP关键在于下个位置的预测。<br>
	 * <br>
	 * 预设时与其说是观察前面相同的值，不如说是看子串的内容。KMP把子串的内容归纳整理成一张查询表。
	 * 
	 * @param s
	 *            母串，或称本文串
	 * @param p
	 *            子串，或称模式串
	 * @exception IndexOutOfBoundsException
	 * @return position
	 */
	public static int indexOfKMP(String s, String p) {
		if (p.length() == 0)
			return 0;
		if (p.length() > s.length())
			throw new IndexOutOfBoundsException("p must be smaller than s.");

		char[] sa = s.toCharArray();
		char[] pa = p.toCharArray();
		int countDown = pa.length;
		int last = sa.length - pa.length + 1;

		int i = 0;
		while (i < last) {
			for (int j = 0; j < pa.length; j++) {
				if (sa[i + j] == pa[j]) {
					// System.out.println((i + j) + ":" + sa[i + j]);
					countDown--;
				} else
					break;
			}
			if (countDown == 0) {
				// 释放查询表
				KMPTableCache = null;
				return i;
			}
			countDown = pa.length;
			i = next(p, i);
		}
		// 释放查询表
		KMPTableCache = null;
		return -1;
	}

	/**
	 * KMP算法核心——查询表
	 */
	private static String KMPTableCache;

	/**
	 * KMP算法核心——查询表
	 * 
	 * @param p
	 *            子串（不变）
	 * @param j
	 *            当前进度
	 * @return
	 */
	private static int next(String p, int j) {
		if (p != KMPTableCache) {

		}
		return j + 1;
	}
}
