package com.catherine.string;

import com.catherine.Main;

/**
 * String, 串<br>
 * 这边重点在于如何实现高效的indexOf接口
 * 
 * @author Catherine
 *
 */
public class StringUtils {
	private final static boolean SHOW_LOG = true;

	/**
	 * 返回前缀，[0, k)
	 * 
	 * @param s
	 * @param k
	 * @return
	 */
	public static String prefix(String s, int k) {
		if (k < 1 || k > s.length())
			throw new IndexOutOfBoundsException("k must be bigger than 0 and be smaller than the length of s.");
		return s.substring(0, k);
	}

	/**
	 * 返回后缀，[n-k, n)
	 * 
	 * @param s
	 * @param k
	 * @return
	 */
	public static String suffix(String s, int k) {
		if (k < 1 || k > s.length())
			throw new IndexOutOfBoundsException("k must be bigger than 0 and be smaller than the length of s.");
		return s.substring(s.length() - k, s.length());
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
			throw new IndexOutOfBoundsException("s must not be longer than p.");

		char[] sa = s.toCharArray();
		char[] pa = p.toCharArray();

		int j = 0;// 主指针
		int t = 0; // 模式串指针
		while (j < sa.length) {
			if (sa[j++] == pa[t++]) {
				if (t >= pa.length)
					return (j - pa.length);
			} else {
				t = 0;
			}
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
			throw new IndexOutOfBoundsException("s must not be longer than p.");

		char[] sa = s.toCharArray();
		char[] pa = p.toCharArray();

		// 创建查询表
		KMPTable kmpTable = new KMPTable(p);

		int j = 0;// 主指针
		int t = 0; // 模式串指针
		while (j < sa.length) {

			// if (SHOW_LOG) {
			// if (t < 0)
			// System.out.print(j + ":" + sa[j] + ", p[-1]");
			// else
			// System.out.print(j + ":" + sa[j] + ", " + pa[t]);
			// }

			// i<0，也就是查询表为空集合时，返回-1，此时等同于成功比对。
			if ((t < 0) || sa[j] == pa[t]) {// 匹配
				// if (SHOW_LOG)
				// System.out.println(" 匹配");
				j++;
				t++;
				if (t >= pa.length)
					return (j - pa.length);
			} else { // 根据查询表找到下一个检查点
				// if (SHOW_LOG)
				// System.out.println(" 不匹配");
				t = kmpTable.next(t);
			}
		}
		return -1;
	}

	/**
	 * KMP算法核心——查询表
	 * 
	 * @author Catherine
	 *
	 */
	private static class KMPTable {
		private int[] nextTable;

		/**
		 * 建构查询表<br>
		 * 以本文串[...,c,h,i,n,c,h,i,x,...]和模式串[c,h,i,n,c,h,i,l,l,a]为例：<br>
		 * 查询表为[c(-1),h(0),i(0),n(0),c(0),h(1),i(2),l(3),l(0),a(0)]。<br>
		 * step1 发现l和x不同:<br>
		 * 本文串[...,c,h,i,n,c,h,i,x,...]<br>
		 * 模式串____[c,h,i,n,c,h,i,l,l,a]<br>
		 * <br>
		 * step2 右移至3:<br>
		 * 本文串[...,c,h,i,n,c,h,i,x,...]<br>
		 * 模式移____------->[c,h,i,n,c,h,i,l,l,a]<br>
		 * <br>
		 * 设刚才找到的相同子串长度为j，右移后的新位置为t（表示右移量为t-j），则<br>
		 * 位置：[0..,(j-t),........j............]<br>
		 * 本文串[...,c,h,i,n,c,h,i,x,...]<br>
		 * 模式串____[c,h,i,n,c,h,i,l,l,a]<br>
		 * 模式移____------->[c,h,i,n,c,h,i,l,l,a]<br>
		 * <br>
		 * <br>
		 * 任意t，next集合需 (0 <= t < j) 或 (模式串[0, t) == 模式串[(j - t), j]) <br>
		 * 为避免回朔，位移(t-j)应越小越好，t应越大越好。
		 * 
		 * @param p
		 *            子串
		 */
		public KMPTable(String subString) {
			char[] p = subString.toCharArray();
			nextTable = new int[p.length];
			nextTable[0] = -1;

			int j = 0;// 主指针
			int t = nextTable[0]; // 模式串指针(p[-1]为通配符，一旦next集合为空，返回-1，其背后意义代表加入秩(index)为-1的哨兵作为通配符，通配符的意思就是一定匹配。)
			int last = p.length - 1;
			while (j < last) {
				if ((t < 0) || p[j] == p[t]) // 匹配
					nextTable[++j] = ++t;
				else // 这边不需要设置终止条件，因为有哨兵
					t = nextTable[t];
			}
			if (SHOW_LOG)
				Main.printArray("KMP Table", nextTable);
		}

		/**
		 * 根据查询表返回下个查询位置。<br>
		 * 
		 * 
		 * @param p
		 *            当前指标在模式串的位置
		 * @return
		 */
		public int next(int p) {
			return nextTable[p];
		}

	}
}
