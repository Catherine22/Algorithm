package com.catherine.priority_queue;

import java.util.Vector;

/**
 * 完全二叉堆（逻辑上等同于完全二叉树，物理上以向量实现）<br>
 * 以层次遍历完全二叉树并对应到向量上。<br>
 * <br>
 * 堆序性：只要i>0，H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyCompleteBinaryHeap<T extends Comparable<? super T>> extends Vector<T> implements PriorityQueue<T> {
	protected final boolean SHOW_DEBUG_LOG = true;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 880638399272054759L;

	@Override
	public void insert(T e) {
		add(e);
		percolateUp(e);
	}

	/**
	 * 
	 * 堆序性： H[i] <= H[parent_of_i] <br>
	 * (表示最大H[i]必为根节点，也就是H[0])
	 */
	@Override
	public T getMax() {
		return get(0);
	}

	@Override
	public T delMax() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * （完全二叉树）找到父亲
	 * 
	 * @param e
	 * @return
	 */
	public T getParent(T e) {
		int i = getPos(e);
		if (i == -1 || i == 0)
			return null;
		// System.out.println(e + "'s parent is " + get((i - 1) >> 1));
		return get((i - 1) >> 1);// divide by 2
	}

	/**
	 * （完全二叉树）找到左孩子
	 * 
	 * @param e
	 * @return
	 */
	public T getLChild(T e) {
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
	public T getRChild(T e) {
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
	private int getPos(T e) {
		int pos = -1;
		int count = 0;
		while (count < size()) {
			if (get(count) == e) {
				pos = count;
				count = size() + 1;
			}
			count++;
		}
		return pos;
	}

	@Override
	public void percolateDown(T n, T i) {
		// TODO Auto-generated method stub
	}

	@Override
	public void percolateUp(T i) {
		T p = getParent(i);
		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("percolateUp %s, %s", (i == null ? "null" : i), (p == null ? "null" : p)));
		if (p != null && (i).compareTo(p) > 0) {
			swap(i, p);
			percolateUp(i);
		}
	}

	private void swap(T i, T p) {
		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("swap %s, %s", i.toString(), p.toString()));
		int p1 = getPos(i);
		T tmp = i;
		set(p1, p);
		set((p1 - 1) >> 1, tmp);
	}

	@Override
	public void heapify(T n) {
		// TODO Auto-generated method stub

	}

	public void printTree() {
		if (size() == 1) {
			System.out.println("level1: " + get(0));
			return;
		}

		int level = 1; // 计算树高一共有几层
		int len = 1; // 每一层有多少元素
		int header = 0; // 目前读到哪里
		StringBuilder sBuilder = new StringBuilder();
		while (header < size()) {
			System.out.print(String.format("level%d: ", level));
			for (int i = 0; (i < len) && (header < size()); i++) {
				sBuilder.append(get(header));
				sBuilder.append(", ");
				header++;
			}
			System.out.println(sBuilder.substring(0, sBuilder.length() - 2));
			sBuilder.delete(0, sBuilder.length());
			len = (int) Math.pow(2, level);
			level++;
		}

	}

}
