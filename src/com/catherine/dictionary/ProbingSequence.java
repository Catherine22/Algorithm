package com.catherine.dictionary;

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
 * 
 * bucket(key1)<br>
 * |<br>
 * spareBucket(key2)<br>
 * |<br>
 * spareBucket(key3)<br>
 * |<br>
 * bucket(key4)<br>
 * |<br>
 * spareBucket(key5)<br>
 * |<br>
 * spareBucket()<br>
 * |<br>
 * bucket(key6)<br>
 * |<br>
 * spareBucket()<br>
 * |<br>
 * spareBucket()<br>
 * 
 * @author Catherine
 *
 */
public class ProbingSequence implements HashingOperation{

	/**
	 * 备用桶数量，根据冲突严重程度增加
	 * 
	 * @param spareBucket
	 */
	public ProbingSequence(int spareBucket) {
	}

	@Override
	public void put(int key, String value) {

	}

	@Override
	public int get(int key) {
		int value = 0;

		return value;
	}

	@Override
	public void remove(int key) {

	}

}
