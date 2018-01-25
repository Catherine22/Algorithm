package com.catherine.priority_queue;

import java.util.List;
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
 * @param <T>
 */
class PriorityQueueImpl<T extends Comparable<? super T>> extends Vector<T> implements PriorityQueue<T> {
	protected final boolean SHOW_DEBUG_LOG = false;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 880638399272054759L;

	@Override
	public int size() {
		return super.size();
	}

	@Override
	public void insert(T t) {
		add(t);
		percolateUp(t);
	}

	@Override
	public T getMax() {
		return get(0);
	}

	@Override
	public T delMax() {
		if (size() == 0)
			return null;
		if (size() == 1) {
			T tmp = get(0);
			remove(0);
			return tmp;
		}
		
		set(0, get(size() - 1));
		remove(size() - 1);
		percolateDown(get(size() - 1), get(0));
		return get(0);
	}

	/**
	 * （完全二叉树）找到父亲
	 * 
	 * @param e
	 * @return
	 */
	public T getParent(T e) {
		int i = getPos(e);

		if (i <= 0)
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

		i = 1 + (i << 1);// time 2, odd number
		if (i >= size() || i <= 0)
			return null;

		// System.out.println(e + "'s LChild is " + get(i));
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

		i = (1 + i) << 1;// time 2, even number
		if (i >= size() || i <= 0)
			return null;

		// System.out.println(e + "'s RChild is " + get(i));
		return get(i);
	}

	/**
	 * （完全二叉树）找到父亲
	 * 
	 * @param e
	 * @return
	 */
	public int getParentPos(T e) {
		int i = getPos(e);

		if (i <= 0)
			return -1;

		return (i - 1) >> 1;// divide by 2
	}

	/**
	 * （完全二叉树）找到左孩子
	 * 
	 * @param e
	 * @return
	 */
	private int getLChildPos(T t) {
		int i = getPos(t);
		if (i == -1)
			return -1;

		i = 1 + (i << 1);// time 2, odd number
		if (i >= size() || i <= 0)
			return -1;

		return i;
	}

	/**
	 * （完全二叉树）找到右孩子
	 * 
	 * @param e
	 * @return
	 */
	private int getRChildPos(T t) {
		int i = getPos(t);
		if (i == -1)
			return -1;

		i = (1 + i) << 1;// time 2, even number
		if (i >= size() || i <= 0)
			return -1;

		return i;
	}

	/**
	 * 返回较大或唯一的孩子
	 * 
	 * @param i
	 * @return
	 */
	private T getChild(T i) {
		T rc = getRChild(i);
		if (rc == null)
			return getLChild(i);
		else {
			T lc = getLChild(i);
			return ((lc != null) && (rc).compareTo(lc) > 0) ? rc : lc;
		}
	}

	/**
	 * 返回较大或唯一的孩子
	 * 
	 * @param i
	 * @return
	 */
	private int getChildPos(T i) {
		int rp = getRChildPos(i);
		int lp = getLChildPos(i);

		if (rp == -1 && lp == -1)
			return -1;
		else if (rp == -1)
			return lp;
		else if (lp == -1)
			return rp;
		else {
			return (get(lp).compareTo(get(rp)) > 0) ? lp : rp;
		}
	}

	/**
	 * 返回该节点在向量里的位置
	 * 
	 * @param e
	 * @return
	 */
	private int getPos(T t) {
		int pos = -1;
		int count = 0;
		while (count < size()) {
			if (get(count) == t) {
				pos = count;
				break;
			}
			count++;
		}
		return pos;
	}

	@Override
	public void percolateDown(T n, T i) {
		T c = getChild(i);

		if (c == null)
			return;

		int basePos = getPos(i);
		T base = i;
		int limit = getPos(n);
		int childPos = getPos(c);

		if (childPos > limit || childPos == -1)
			return;

		if (SHOW_DEBUG_LOG)
			System.out
					.println(String.format("percolateDown %s, %s", (i == null ? "null" : i), (c == null ? "null" : c)));

		boolean swap = false;
		// 指定节点和其孩子比较，一旦有交换就进行下一轮比较，让原孩子成为新指针，与其孩子比较
		while (childPos <= limit && (c).compareTo(base) > 0) {
			swap = true;
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("assign %s to %d", c.toString(),
			// basePos));
			set(basePos, c);

			// 找出孩子
			int rp, lp;
			rp = (1 + childPos) << 1;
			lp = 1 + (childPos << 1);

			if ((rp > limit || rp <= 0) && (lp > limit || lp <= 0)) {
				// 没孩子
				basePos = childPos;
				break;
			} else if (rp > limit || rp <= 0) {
				// 没右孩子
				childPos = lp;
			} else if (lp > limit || lp <= 0) {
				// 没左孩子
				childPos = rp;
			} else {
				// 挑大的孩子
				childPos = (get(rp).compareTo(get(lp)) > 0) ? rp : lp;
			}
			c = get(childPos);

			// 找出父亲
			basePos = (childPos - 1) >> 1;
			i = base;
			// if (SHOW_DEBUG_LOG)
			// System.out.println(
			// String.format("basePos:%d, target:%s, childPos:%d, child:%s",
			// basePos, i, childPos, c));
		}

		if (swap) {
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("fill %s in %d", base,
			// basePos));
			set(basePos, base);
		}
	}

	@Override
	public void percolateUp(T i) {
		int basePos = getPos(i);
		int parentPos = (basePos < 1) ? -1 : ((basePos - 1) >> 1);
		if (parentPos == -1)
			return;

		T base = i;
		T p = get(parentPos);

		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("percolateUp %s(%d), %s(%d)", (i == null ? "null" : i),
					(i == null ? -1 : basePos), (p == null ? "null" : p), (p == null ? -1 : parentPos)));

		boolean swap = false;
		while (parentPos != -1 && (base).compareTo(p) > 0) {
			swap = true;
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("set %s to %s", i.toString(),
			// p.toString()));
			set(basePos, p);
			basePos = parentPos;

			if (basePos < 1)
				break;

			parentPos = (basePos - 1) >> 1;
			p = get(parentPos);
		}

		if (swap) {
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("set %s to %s", get(basePos),
			// base));
			set(basePos, base);
		}
	}

	/**
	 * 上滤<br>
	 * 如果E与其父亲违反堆序性，则交换为止，交换后继续和新父亲（原祖父）做确认，直到 E与父亲符合堆序性或到达顶点。<br>
	 * 由于树高不超过log(n)，所以比较次数最多就是log(n)，最坏情况是每一次上滤都要交换（每次交换需要三次赋值），所以=3·log(n)。
	 * <br>
	 * 在此可以优化交换的逻辑，变成首先备份初始节点，接着往上比较，一旦父节点比较小就取代孩子，直到符合堆序性或到达顶点，
	 * 再将初始节点指定给当前位置。一共需要两次赋值，所以=log(n)+2。<br>
	 * 
	 * @see #percolateUp(Comparable)
	 * @param i
	 */
	@Deprecated
	public void percolateUpOriginal(T i) {
		T p = getParent(i);
		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("percolateUp %s, %s", (i == null ? "null" : i), (p == null ? "null" : p)));
		if (p != null && (i).compareTo(p) > 0) {
			swap(i, p);
			percolateUpOriginal(i);
		}
	}

	/**
	 * 对前n个词条的第i个节点进行下滤，n>i<br>
	 * 如果E与其较大的子节点违反堆序性，则交换为止，交换后继续和新较大的子节点（原孙子）做确认，直到E与孩子符合堆序性或到达底端。<br>
	 * 由于树高不超过log(n)，所以比较次数最多就是log(n)，最坏情况是每一次上滤都要交换（每次交换需要三次赋值），所以=3·log(n)。
	 * <br>
	 * 在此可以优化交换的逻辑，变成首先备份初始节点，接着往下比较，一旦子节点比较大就取代父亲，直到符合堆序性或到达底端，
	 * 再将初始节点指定给当前位置。一共需要两次赋值，所以=log(n)+2。<br>
	 * 
	 * @param n
	 * @param i
	 */
	@Deprecated
	public void percolateDownOriginal(T n, T i) {
		int cp = getChildPos(i);

		if (cp == -1)
			return;

		T c = get(cp);
		if (cp > getPos(n))
			return;

		if (SHOW_DEBUG_LOG)
			System.out
					.println(String.format("percolateDown %s, %s", (i == null ? "null" : i), (c == null ? "null" : c)));

		if ((c).compareTo(i) > 0) {
			swap(c, i);
			percolateDownOriginal(n, i);
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
	public void heapify(T[] array) {
		if (array == null || array.length == 0)
			return;

		// 方法1
		// for (T t : list) {
		// insert(t);
		// }

		// 方法2
		for (T t : array) {
			add(t);
		}

		// 找出最后一个元素的父亲
		int target = array.length / 2 - 1;
		int limit = array.length - 1;
		merge(target, limit);
	}

	@Override
	public void heapify(List<T> list) {
		if (list == null || list.size() == 0)
			return;

		// 方法1
		// for (T t : list) {
		// insert(t);
		// }

		// 方法2
		for (T t : list) {
			add(t);
		}

		// 找出最后一个元素的父亲
		int target = list.size() / 2 - 1;
		int limit = list.size() - 1;
		merge(target, limit);
	}

	private void merge(int target, int limit) {
		if (target < 0 || limit < target)
			return;

		T l = get(limit);
		// 目标节点
		// System.out.println("目标 " + get(target));
		percolateDown(l, get(target));
		// printTree();

		int parentPos = getParentPos(get(target));
		if (parentPos >= 0) {
			T p = get(parentPos);
			// 换兄弟节点
			int siblingPos = (getLChildPos(p) == target) ? getRChildPos(p) : getLChildPos(p);
			if (siblingPos > 0) {
				// System.out.println("兄弟 " + get(siblingPos));
				percolateDown(l, get(siblingPos));
				// printTree();
			}

			// 换父节点
			// System.out.println("父 " + get(parentPos));
			percolateDown(l, get(parentPos));
			// printTree();

			merge(parentPos, limit);
		}
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
