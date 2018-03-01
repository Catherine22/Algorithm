package com.catherine.string;

import com.catherine.Main;

/**
 * String, 串<br>
 * 这边重点在于如何实现高效的indexOf接口。<br>
 * 这边探讨效能要分成成功和失败分别讨论。<br>
 * <br>
 * 假设寻找失败：比起KMP，蛮力算法最好的情况下只需要O(n)，一旦字符集的规模越来愈高，这样的情况会越来越高。<br>
 * 也就是KMP适用字符集规模小的情况，因此常见的KMP大抵是用二进制示范。
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
		return indexOfBF(s, p);
	}

	/**
	 * BF算法，即暴风(Brute Force)算法，一种蛮力算法，一个一个比较。<br>
	 * 每次都是先比对子串的第一个，如果相同就比对子串的第二个......以此类推。
	 * 
	 * @param s
	 *            母串，或称本文串
	 * @param p
	 *            子串，或称模式串
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
		int progress = 0;// j + t
		while (j < sa.length) {

			// if (SHOW_LOG)
			// System.out.print(j + ":" + sa[j] + ", p[" + t + "]");

			progress = j + t;
			if ((progress < sa.length) && (sa[progress] == pa[t++])) {
				// if (SHOW_LOG)
				// System.out.println(" 匹配");
				if (t >= pa.length)
					return j;
			} else {
				// if (SHOW_LOG)
				// System.out.println(" 不匹配");
				j++;
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
	 * 在比对过程中，蛮力算法是一旦母串任意位置的字符和子串第一个相同，就继续向后比对，直到完全一样即返回或是出现异数，母串位置向后推进。<br>
	 * 大多时候其实都是比对失败的，尤其字符集规模（好比常见中文字有5000个）越大成功率越小，因此Boyer-
	 * Moore算法的核心就是加速比对失败的速度。<br>
	 * 
	 * @param s
	 *            母串，或称本文串
	 * @param p
	 *            子串，或称模式串
	 * @exception IndexOutOfBoundsException
	 * @return position
	 */
	public static int indexOfBM(String s, String p) {
		if (p.length() == 0)
			return 0;
		if (p.length() > s.length())
			throw new IndexOutOfBoundsException("s must not be longer than p.");

		// 创建查询表
		BMTable bmTable = new BMTable(s, p);
		int i = 0;// index
		int d = 0;// shift
		while (i < s.length()) {
			d = bmTable.next(i);
			switch (d) {
			case -1:
				i += p.length();
				break;
			case 0:
				return i;
			default:
				i += d;
			}
		}
		return -1;
	}

	/**
	 * KMP算法，最坏情况下不超过O(n)，n为本文串长度。
	 * 
	 * @author Catherine
	 *
	 */
	private static class KMPTable {
		/**
		 * KMP算法核心——查询表
		 */
		private int[] nextTable;
		/**
		 * 模式串
		 */
		private char[] p;

		public KMPTable(String subString) {
			advancedBuild(subString);
		}

		/**
		 * 建构查询表<br>
		 * <br>
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
		 * @param subString
		 *            子串
		 */
		@Deprecated
		private void build(String subString) {
			p = subString.toCharArray();
			nextTable = new int[p.length];
			nextTable[0] = -1;

			int j = 0;// 主指针(0 ~ subString.len-1)
			int t = nextTable[0]; // 模式串指针(p[-1]为通配符，一旦nextTable集合为空，返回-1，其背后意义代表加入秩(index)为-1的哨兵作为通配符，通配符的意思就是一定匹配，当p[-1]匹配，下一个就是p[0]，就好像p向右移动一步。)
			while (j < (p.length - 1)) {
				if (t < 0 || p[j] == p[t]) // 匹配
					nextTable[++j] = ++t;
				else // 这边不需要设置终止条件，因为有哨兵
					t = nextTable[t];
			}
			if (SHOW_LOG)
				Main.printArray("KMP Table", nextTable);
		}

		/**
		 * 有时候通过{@link #next(int)}取得的秩对应的值接连都相同，而且这个值是不匹配的，<br>
		 * 也就是意味着除了第一次比对之外，直到取得不同的值之前，其它次比对都是浪费时间。<br>
		 * 因此能确保返回的秩对应的值和上次不同，就能改善KMP算法。<br>
		 * <br>
		 * 基于{@link #build(String)}微调，建构新的查询表。<br>
		 * 
		 * @param subString
		 * @return
		 */
		private void advancedBuild(String subString) {
			p = subString.toCharArray();
			nextTable = new int[p.length];
			nextTable[0] = -1;

			int j = 0;// 主指针(0 ~ subString.len-1)
			int t = nextTable[0]; // 模式串指针(p[-1]为通配符，一旦nextTable集合为空，返回-1，其背后意义代表加入秩(index)为-1的哨兵作为通配符，通配符的意思就是一定匹配，当p[-1]匹配，下一个就是p[0]，就好像p向右移动一步。)
			while (j < (p.length - 1)) {
				// System.out.println("j:" + j + "\tt:" + t);
				if (t < 0 || p[j] == p[t]) { // 匹配
					++j;
					++t;
					// 如果查询表上次位置的值和本次一样，就继续往后找。
					nextTable[j] = (p[j] == p[t]) ? nextTable[t] : t;
				} else // 这边不需要设置终止条件，因为有哨兵
					t = nextTable[t];
			}
			if (SHOW_LOG)
				Main.printArray("KMP Table", nextTable);
		}

		/**
		 * 根据查询表返回下个查询位置。<br>
		 * 
		 * 
		 * @param t
		 *            当前指标在模式串的位置
		 * @return
		 */
		public int next(int t) {
			return nextTable[t];
		}
	}

	/**
	 * BM算法表
	 * 
	 * @author Catherine
	 *
	 */
	private static class BMTable {
		/**
		 * Boyer-Moore算法核心——BC(Bad Character)表
		 */
		private int[] bcTable;
		private int[] gsTable;

		/**
		 * 模式串
		 */
		private char[] p;
		/**
		 * 本文串
		 */
		private char[] main;

		public BMTable(String mainString, String subString) {
			main = mainString.toCharArray();
			p = subString.toCharArray();
			build();
		}

		/**
		 * 
		 * 假设位移時，本文串为“道可道非常道名可名非常名”，模式串为“非常名”，比对时由模式串右到左，流程如下：<br>
		 * 1.‘道’和‘名’比<br>
		 * 道可道非常道名可名非常名<br>
		 * 非常名<br>
		 * <br>
		 * 2.发现本文串里没有‘道’，位移到从‘道’下一个开始。<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。非常名<br>
		 * <br>
		 * 3.‘道’和‘名’比<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。非常名<br>
		 * <br>
		 * 4.发现本文串里没有‘道’，位移到从‘道’下一个开始。<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。。。。非常名<br>
		 * <br>
		 * 5.‘名’和‘名’比，命中，‘可’和‘常’比，失败<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。。。。非常名<br>
		 * <br>
		 * 6.发现本文串里没有‘可’，位移到从‘可’下一个开始。<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。。。。。。非常名<br>
		 * <br>
		 * 7.‘常’和‘名’比<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。。。。。。非常名<br>
		 * <br>
		 * 8.发现本文串里有‘常’，位移至本文串“常”与模式串该‘常’相对。<br>
		 * 道可道非常道名可名非常名<br>
		 * 。。。。。。。。。非常名<br>
		 * 9. 命中<br>
		 * <br>
		 * <br>
		 * 假设上个在本文串的位置是i，找到相同值在本文串的位置为j（步骤8的‘常’），<br>
		 * 本文串位移量(shift) = j - 本文串p[0]～p[‘常’]的长度。<br>
		 * bc['x']（bc表中‘常’的值）存放的就是本文串p[0]～p[‘x’]的长度 = j - shift。
		 */
		private void build() {
			// 长度和字母表一样
			bcTable = new int[main.length];
			for (int i = 0; i < bcTable.length; i++) {
				// 同KMP，引入哨兵的概念（没有候选的情况，用-1作为循环终止条件，结构上就是模式串位移p.length）
				bcTable[i] = -1;
			}

			int i = 0;
			int shift = -1;
			while (i < bcTable.length) {
				// if (SHOW_LOG)
				// System.out.println("\n" + i + ":" + "\t");
				shift = -1;
				// bc['x']可能有多个候选，为避免回溯，尽可能让位移少一点，也就是bc['x']尽可能大一点。
				for (int j = p.length - 1; j >= 0 && (i + j) < bcTable.length; j--) {
					// if (SHOW_LOG)
					// System.out.print("main[" + (i + j) + "]:" + main[j + i] +
					// " vs " + "p[" + j + "]:" + p[j] + "\t");
					if (main[i + j] == p[j]) {
						shift = j;
						bcTable[i] = shift;
					} else {
						if (lastIndexOf(main[i + j], p) >= 0) {
							shift = 1;
							bcTable[i] = shift;
						}
						break;
					}
				}
				// if (SHOW_LOG)
				// System.out.println("shift:" + shift);
				i += (shift > 0) ? shift : p.length;
			}

			if (SHOW_LOG)
				Main.printArray("BC Table", bcTable);
		}

		private int lastIndexOf(char v, char[] c) {
			for (int i = c.length - 1; i >= 0; i--) {
				if (c[i] == v)
					return i;
			}
			return -1;
		}

		/**
		 * 根据查询表返回下个查询位置。<br>
		 * 
		 * 
		 * @param t
		 *            当前指标在模式串的位置
		 * @return
		 */
		public int next(int t) {
			return bcTable[t];
		}
	}
}
