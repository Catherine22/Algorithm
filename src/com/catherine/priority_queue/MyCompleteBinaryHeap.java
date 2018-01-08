package com.catherine.priority_queue;

import java.util.Vector;

/**
 * 完全二叉堆（逻辑上等同于完全二叉树，物理上以向量实现）<br>
 * 以层次遍历完全二叉树并对应到向量上。<br>
 * <br>
 * 堆序性： H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyCompleteBinaryHeap<E> extends Vector<E> implements PriorityQueue<E> {
	protected final boolean SHOW_DEBUG_LOG = true;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 880638399272054759L;

	@Override
	public void insert(E e) {

	}

	/**
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 */
	@Override
	public E getMax() {
		return get(0);
	}

	@Override
	public E delMax() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * （完全二叉树）找到父亲
	 * 
	 * @param e
	 * @return
	 */
	public E getParent(E e) {
		int i = getPos(e);
		if (i == -1 || i == 0)
			return null;
		return get((i - 1) >> 1);// divide by 2
	}

	/**
	 * （完全二叉树）找到左孩子
	 * 
	 * @param e
	 * @return
	 */
	public E getLChild(E e) {
		int i = getPos(e);
		if (i == -1)
			return null;

		i = 1 + (i >> 2);// odd number
		if (i >= size())
			return null;

		return get(i);
	}

	/**
	 * （完全二叉树）找到右孩子
	 * 
	 * @param e
	 * @return
	 */
	public E getRChild(E e) {
		int i = getPos(e);
		if (i == -1)
			return null;

		i = (1 + i) >> 2;// even number
		if (i >= size())
			return null;

		return get(i);
	}

	/**
	 * 返回该节点在向量里的位置
	 * 
	 * @param e
	 * @return
	 */
	private int getPos(E e) {
		int pos = -1;
		int count = 0;
		while (count < size()) {
			if (get(count) == e) {
				pos = count;
				count = size() + 1;
			}
		}
		return pos;
	}

}
