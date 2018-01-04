package com.catherine.dictionary.functions;

import com.catherine.dictionary.LinearProbing;
import com.catherine.dictionary.QuadraticProbing;

public class CollisionMode {

	public final static int DO_NOTHING = 0;

	/**
	 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，每个散列地址（之后简称为桶）
	 * 可存放一个LinkedList， 让重复的关键码存在同一个桶中。<br>
	 * bucket(key1-key2-key3)<br>
	 * |<br>
	 * bucket(key4-key5)<br>
	 * |<br>
	 * bucket(key6)<br>
	 * 
	 * 
	 * 如此会有一个重大缺陷，CPU缓存依赖连续内存地址，一旦已链构成桶，整个散列地址并非连续地址，直接影响效能。<br>
	 * 因而对散列进一步改进，桶不以LinkedList的形式存在，而是加上固定数量的备用桶，让整个散列列表为桶串联起来的一长串连续地址。<br>
	 * <br>
	 * <br>
	 * 假设hash(key)=key%7，目前有0～6的地址，M=7<br>
	 * key=10、13、11、3、8、5、14<br>
	 * <br>
	 * 10:放3<br>
	 * 13:放6<br>
	 * 11:放4<br>
	 * 3:3冲突，改成(3+1)，放4，继续冲突，改(3+2)，放5<br>
	 * 8:放1<br>
	 * 5:5冲突，改成(5+1)，放6冲突，改成(5+2)，7超过地址长度，回到0，放0<br>
	 * 14:0冲突，改成(0+1)，放1，继续冲突，改(0+2)，放2<br>
	 * 整个表为[5, 8, 14, 10, 11, 3, 13]<br>
	 * 
	 * @author Catherine
	 * @see LinearProbing 线性试探
	 *
	 */
	public final static int LINEAR_PROBING = 1;
	/**
	 * 设计散列函数时，同一个关键码(key)映射到同一个散列地址(value)是无可避免的，为了解决这个问题，有两种解决方式，线性试探和平方试探。
	 * 平方试探是以平方数为单位，寻找下一个桶单元。<br>
	 * 平方试探的函数为：[hash(key) + n^2] % M<br>
	 * <br>
	 * <br>
	 * 假设hash(key)=key%7，目前有0～6的地址，M=7<br>
	 * key=10、13、11、3、8、5、14<br>
	 * <br>
	 * 10:放3<br>
	 * 13:放6<br>
	 * 11:放4<br>
	 * 3:3冲突，改成(3+1^2)%7，放4，继续冲突，改(3+2^2)%7，放0<br>
	 * 8:放1<br>
	 * 5:放5<br>
	 * 14:0冲突，改成(0+1^2)%7，放1，继续冲突，改(0+2^2)%7，放4，继续冲突，改(0+3^2)%7，放2<br>
	 * 整个表为[3, 8, 14, 10, 11, 5, 13]<br>
	 * 
	 * @author Catherine
	 * @see QuadraticProbing 平方试探
	 */
	public final static int QUADRATIC_PROBING = 2;

	public static String getName(int mode) {
		switch (mode) {
		case DO_NOTHING:
			return "DO_NOTHING";
		case LINEAR_PROBING:
			return "LINEAR_PROBING";
		case QUADRATIC_PROBING:
			return "QUADRATIC_PROBING";
		default:
			throw new NullPointerException("No such a mode");
		}
	}

	private int spareBuckets;
	private int mode;

	public CollisionMode() {

	}

	public CollisionMode(Builder builder) {
		mode = builder.mode;
		spareBuckets = builder.spareBuckets;
	}

	public static class Builder {
		private int mode;
		private int spareBuckets;

		public Builder() {
			mode = DO_NOTHING;
		}

		public Builder spareBuckets(int spareBuckets) {
			this.spareBuckets = spareBuckets;
			return this;
		}

		public Builder mode(int mode) {
			this.mode = mode;
			return this;
		}

		public CollisionMode build() {
			return new CollisionMode(this);
		}
	}

	public int getSpareBuckets() {
		return spareBuckets;
	}

	public int getMode() {
		return mode;
	}
}
