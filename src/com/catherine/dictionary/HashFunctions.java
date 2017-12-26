package com.catherine.dictionary;

/**
 * 设计散列（hash）函数时应注意下列原则：<br>
 * <br>
 * 1. 确定（determinism）：同一个关键码总是映射到同一个地址。<br>
 * 2. 快速（efficiency）：预期O（1）。<br>
 * 3. 射满（surjection）：尽可能射满整个映射空间。<br>
 * 4. 均匀（uniformity）：关键码映射到散列表各位置的概率应尽量接近。<br>
 * 
 * @author Catherine
 *
 */
public class HashFunctions {
	private final boolean SHOW_DEBUG_LOG = true;

	/**
	 * 除余法<br>
	 * hash(key) = key % M
	 * 
	 * @param key
	 */
	public void remainder(String key) {

		if (SHOW_DEBUG_LOG)
			analyze();
	}

	public void analyze() {

	}
}
