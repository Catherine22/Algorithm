package com.catherine.priority_queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import com.catherine.trees.MyBinaryTree;
import com.catherine.trees.nodes.Node;

/**
 * 完全二叉堆（逻辑上等同于完全二叉树，物理上以向量实现）<br>
 * 以层次遍历完全二叉树并对应到向量上。<br>
 * <br>
 * 堆序性：只要i>0，H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * 此处为了实现左式堆以二叉树实现，一般来说用Vector实现即可。
 * 
 * @author Catherine
 * @see PriorityQueueVectorImpl
 * @param <T>
 */
public class PriorityQueueBinTreeImpl<T extends Comparable<? super T>> extends MyBinaryTree<T>
		implements PriorityQueueBinTree<T> {
	protected final boolean SHOW_DEBUG_LOG = false;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 880638399272054759L;

	public PriorityQueueBinTreeImpl(T root) {
		super(root);
	}

	private Node<T> add(Node<T> parent, T t) {
		if (parent == null && root != null)
			return null;
		else if (parent == null && root == null) {
			setRoot(t);
			return root;
		} else {
			if (parent.getlChild() == null)
				return insertLC(parent, t);

			if (parent.getrChild() == null)
				return insertRC(parent, t);

			return insertLC(parent, t);
		}
	}

	private void remove(Node<T> node) {
		if (node == null || root == null)
			return;
		size--;
		if (node == root) {
			root = null;
			return;
		}

		if (node.getlChild() == null && node.getrChild() == null) {
			if (node.getParent().getlChild() == node)
				node.getParent().setlChild(null);
			else
				node.getParent().setrChild(null);
		} else if (node.getlChild() != null && node.getrChild() == null) {
			if (node.getParent().getlChild() == node) {
				node.getlChild().setParent(node.getParent());
				node.getParent().setlChild(node.getlChild());
			} else {
				node.getlChild().setParent(node.getParent());
				node.getParent().setrChild(node.getlChild());
			}
		} else if (node.getlChild() == null && node.getrChild() != null) {
			if (node.getParent().getlChild() == node) {
				node.getrChild().setParent(node.getParent());
				node.getParent().setlChild(node.getrChild());
			} else {
				node.getrChild().setParent(node.getParent());
				node.getParent().setrChild(node.getrChild());
			}
		} else {
			Node<T> cNode = (node.getlChild().getData().compareTo(node.getrChild().getData()) > 0) ? node.getlChild()
					: node.getrChild();
			if (node.getParent().getlChild() == node) {
				cNode.setParent(node.getParent());
				node.getParent().setlChild(cNode);
			} else {
				cNode.setParent(node.getParent());
				node.getParent().setrChild(cNode);
			}
		}
		updateAboveHeight(node.getParent());
		node = null;
	}

	@Override
	public void insert(T t) {
		percolateUp(add(findLastNode(), t));
	}

	@Override
	public T getMax() {
		return root.getData();
	}

	@Override
	public synchronized T delMax() {
		if (size == 0)
			return null;
		if (size == 1) {
			T tmp = root.getData();
			remove(root);
			return tmp;
		}

		Node<T> bottom = findbottom();
		root.setData(bottom.getData());
		remove(bottom);
		bottom = findbottom();

		percolateDown(root, bottom);
		return root.getData();
	}

	/**
	 * n2节点是否在年n1节点右边
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	private boolean isRighterThan(Node<T> n1, Node<T> n2) {
		if (n1 == null || n2 == null)
			return false;

		// 用中序遍历（左-中-右）
		Stack<Node<T>> bin = new Stack<>();
		Node<T> node = root;
		int n1Pos = 0;
		int n2Pos = 0;
		int head = 0;
		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.getlChild();
			}
			if (!bin.isEmpty()) {
				node = bin.pop();
				if (node == n1)
					n1Pos = head;
				if (node == n2)
					n2Pos = head;
				node = node.getrChild();
			}
			head++;
		}

		return (n2Pos > n1Pos);
	}

	/**
	 * 返回最下层最右边的节点
	 * 
	 * @return
	 */
	private Node<T> findbottom() {
		if (root == null)
			return null;
		Stack<Node<T>> stack = traverse();
		return stack.pop();
	}

	/**
	 * 返回最上层且最右边没有缺少孩子的节点，比如：<br>
	 * ___1 <br>
	 * __2 3 <br>
	 * 4 5 6 <br>
	 * 返回3（因为3少了个右孩子）<br>
	 * ___1 <br>
	 * __2 3 <br>
	 * 返回2
	 * 
	 * 
	 * 
	 * @return
	 */
	private Node<T> findLastNode() {
		if (root == null)
			return null;

		// 阶层走访
		Queue<Node<T>> parent = new LinkedList<>();
		Queue<Node<T>> siblings = new LinkedList<>();
		Node<T> node = root;
		parent.offer(node);
		int level = 0;
		boolean isRight = false;
		while (node != null || !parent.isEmpty()) {
			// System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				if (node == root) {
					isRight = false;
				} else {
					isRight = !isRight;
				}

				if (node != null) {

					// System.out.print(node.getInfo());
					if (node.getlChild() == null || node.getrChild() == null) {
						// System.out.print(" return\n");
						return node;
					}

					if (node.getlChild() != null)
						siblings.offer(node.getlChild());
					else
						siblings.offer(null);

					if (node.getrChild() != null)
						siblings.offer(node.getrChild());
					else
						siblings.offer(null);
				}

			}

			int countdown = siblings.size();
			for (Node<T> n : siblings) {
				parent.offer(n);

				if (n == null)
					countdown--;
			}

			siblings.clear();
			node = null;
			// System.out.print("\n");

			if (countdown == 0)
				break;
		}
		return null;
	}

	@Override
	public synchronized void percolateDown(Node<T> n, Node<T> i) {
		if (n == null || getChild(n) == null || n == i)
			return;

		Stack<Node<T>> track = traverse();
		int limit = track.indexOf(i);
		if (limit < 0)
			throw new NullPointerException("i is not found.");

		T base = n.getData();
		Node<T> head = getChild(n);
		while (head != null && track.indexOf(head) <= limit) {
			if (head.getData().compareTo(base) > 0) {
				head.getParent().setData(head.getData());
				if (getChild(head) != null) {
					head = getChild(head);
				} else
					break;
			} else {
				head = head.getParent();
				break;
			}
		}

		head.setData(base);
	}

	// 找出较大或唯一的孩子
	private Node<T> getChild(Node<T> n) {
		if (n.getlChild() == null) {
			if (n.getrChild() == null)
				return null;
			else
				return n.getrChild();
		} else {
			if (n.getrChild() == null)
				return n.getlChild();
			else
				return (n.getlChild().getData().compareTo(n.getrChild().getData()) > 0) ? n.getlChild() : n.getrChild();
		}
	}

	@Override
	public synchronized void percolateUp(Node<T> i) {
		System.out.println("percolateUp " + "node:" + i);
		Node<T> basePos = i;
		Node<T> parentPos = (basePos == null) ? null : basePos.getParent();
		if (parentPos == null)
			return;

		T base = i.getData();
		Node<T> p = parentPos;

		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("percolateUp %s(%d), %s(%d)", (i == null ? "null" : i),
					(i == null ? -1 : basePos), (p == null ? "null" : p), (p == null ? -1 : parentPos)));

		boolean swap = false;
		while (parentPos != null && (base).compareTo(p.getData()) > 0) {
			swap = true;
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("set %s to %s", i.toString(),
			// p.toString()));
			basePos.setData(p.getData());
			basePos = parentPos;

			if (basePos == null)
				break;

			parentPos = basePos.getParent();
			p = parentPos;
		}

		if (swap) {
			// if (SHOW_DEBUG_LOG)
			// System.out.println(String.format("set %s to %s", get(basePos),
			// base));
			basePos.setData(base);
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
	public synchronized void percolateUpOriginal(Node<T> i) {
		Node<T> p = i.getParent();
		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("percolateUp %s, %s", (i == null ? "null" : i), (p == null ? "null" : p)));
		if (p != null && (i.getData()).compareTo(p.getData()) > 0) {
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
	public synchronized void percolateDownOriginal(Node<T> n, Node<T> i) {
		Node<T> cp = getChild(i);

		if (cp == null)
			return;

		Node<T> c = cp;
		if (isRighterThan(cp, n))
			return;

		if (SHOW_DEBUG_LOG)
			System.out
					.println(String.format("percolateDown %s, %s", (i == null ? "null" : i), (c == null ? "null" : c)));

		if ((c.getData()).compareTo(i.getData()) > 0) {
			swap(c, i);
			percolateDownOriginal(n, i);
		}
	}

	@Override
	public synchronized T get(int index) {
		Stack<Node<T>> bin = new Stack<>();
		Node<T> node = root;
		T res = null;
		int countdown = index;

		while ((countdown > 0) && (node != null || bin.size() > 0)) {
			while (node != null) {
				bin.push(node);
				node = node.getlChild();
			}
			if (!bin.isEmpty()) {
				node = bin.pop();
				res = node.getData();
				node = node.getrChild();
				countdown--;
			}
		}
		return res;
	}

	protected synchronized void swap(Node<T> i, Node<T> p) {
		if (SHOW_DEBUG_LOG)
			System.out.println(String.format("swap %s, %s", i.toString(), p.toString()));
		T tmp = i.getData();
		i.setData(p.getData());
		p.setData(tmp);
	}

	@Override
	@Deprecated
	public void completedlyHeapify(T[] array) {
		for (T t : array) {
			insert(t);
		}
	}

	@Override
	@Deprecated
	public void completedlyHeapify(List<T> list) {
		for (T t : list) {
			insert(t);
		}
	}

	@Override
	public void heapify(T[] array) {
		heapify(array, array.length - 1, array.length - 1);
	}

	@Override
	public void heapify(List<T> list) {
		heapify(list, list.size() - 1, list.size() - 1);
	}

	public synchronized void merge(PriorityQueueBinTree<T> heap) {
		if (heap == null || heap.size() == 0)
			return;
		PriorityQueueBinTreeImpl<T> tmp = new PriorityQueueBinTreeImpl<>(heap.getMax());

		// 方法1-挨个合并
		// if (size() > tmp.size()) {
		// insert(tmp.getMax());
		// while (tmp.size() > 1)
		// insert(tmp.delMax());
		// } else {
		// if (size() > 0)
		// tmp.insert(getMax());
		// while (size() > 1)
		// tmp.insert(delMax());
		// clear();
		// for (int i = 0; i < tmp.size(); i++) {
		// add(tmp.get(i));
		// }
		// }

		// 方法2-佛洛依德算法
		if (size() > tmp.size()) {
			Stack<Node<T>> bin = new Stack<>();
			Node<T> node = tmp.root;

			while (node != null || bin.size() > 0) {
				while (node != null) {
					bin.push(node);
					node = node.getlChild();
				}
				if (!bin.isEmpty()) {
					node = bin.pop();
					add(findLastNode(), node.getData());
					node = node.getrChild();
				}
			}

			Node<T> target = findLastNode();
			Node<T> n = target;
			int countdown = size - 1;
			while (target != null) {
				merge(target, n);

				countdown--;
				Node<T> t = root;
				Node<T> h = root;
				if (size == 0)
					target = null;

				while ((countdown > 0) && (t.getlChild() != null || t.getrChild() != null)) {
					if (t.getlChild() == null)
						h = t.getrChild();

					if (t.getrChild() == null)
						h = t.getlChild();

					t = h;
				}

				target = h;
			}
		} else {
			Stack<Node<T>> bin = new Stack<>();
			Node<T> node = root;

			while (node != null || bin.size() > 0) {
				while (node != null) {
					bin.push(node);
					node = node.getlChild();
				}
				if (!bin.isEmpty()) {
					node = bin.pop();
					tmp.add(findLastNode(), node.getData());
					node = node.getrChild();
				}
			}

			Node<T> n = findLastNode();
			Stack<Node<T>> traverseIns = new Stack<>();
			bin = new Stack<>();
			node = root;

			while (node != null || bin.size() > 0) {
				while (node != null) {
					bin.push(node);
					node = node.getlChild();
				}
				if (!bin.isEmpty()) {
					node = bin.pop();
					traverseIns.add(node);
					node = node.getrChild();
				}

			}

			while (!traverseIns.isEmpty())
				merge(traverseIns.pop(), n);

			root = tmp.getRoot();
		}
		size += heap.size();
	}

	/**
	 * 用{@link #heapify(List)}对整个集合做处理。
	 * 
	 * @param array
	 *            可排序的集合
	 * @param target
	 *            从第target开始开始下滤
	 * @param n
	 *            最后滤到第n个词条
	 */
	private void heapify(T[] array, int target, int n) {
		if (array == null || array.length == 0)
			return;

		remove(root);

		// 方法1
		// for (T t : array) {
		// insert(t);
		// }

		// 方法2
		// 用stack记录阶层走访，再一一合并
		for (T t : array) {
			insert(t);
		}

		Stack<Node<T>> stack = traverse();
		Node<T> nNode = stack.get(n);
		while (!stack.isEmpty()) {
			merge(stack.pop(), nNode);
		}

	}

	/**
	 * 用{@link #heapify(Object[])}对整个集合做处理。
	 * 
	 * @param list
	 *            可排序的集合
	 * @param target
	 *            从第target开始开始下滤
	 * @param n
	 *            最后滤到第n个词条
	 */
	private void heapify(List<T> list, int target, int n) {
		if (list == null || list.size() == 0)
			return;

		remove(root);

		// 方法1
		// for (T t : list) {
		// insert(t);
		// }

		// 方法2
		// 用stack记录阶层走访，再一一合并
		for (T t : list) {
			insert(t);
		}

		Stack<Node<T>> stack = traverse();
		Node<T> nNode = stack.get(n);
		while (!stack.isEmpty()) {
			merge(stack.pop(), nNode);
		}
	}

	/**
	 * @param target
	 *            从哪里开始滤
	 * @param n
	 *            最后滤到哪里
	 */
	private synchronized void merge(Node<T> target, Node<T> n) {
		if (target == null || isRighterThan(n, target))
			return;

		Node<T> l = n;
		// 目标节点
		// System.out.println("目标 " + get(target));
		percolateDown(l, target);
		// printTree();

		Node<T> parentPos = target.getParent();
		if (parentPos != null) {
			Node<T> p = parentPos;
			// 换兄弟节点
			Node<T> siblingPos = (p.getlChild() == target) ? p.getrChild() : p.getlChild();
			if (siblingPos != null) {
				// System.out.println("兄弟 " + get(siblingPos));
				percolateDown(l, siblingPos);
				// printTree();
			}

			// 换父节点
			// System.out.println("父 " + get(parentPos));
			percolateDown(l, parentPos);

			merge(parentPos, n);
		}
	}

	// 阶层走访
	private Stack<Node<T>> traverse() {
		Stack<Node<T>> stack = new Stack<>();
		Queue<Node<T>> parent = new LinkedList<>();
		Queue<Node<T>> siblings = new LinkedList<>();
		Node<T> node = root;
		parent.offer(node);
		int level = 0;
		boolean isRight = false;
		while (node != null || !parent.isEmpty()) {
			// System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				if (node == root) {
					isRight = false;
				} else {
					isRight = !isRight;
				}

				if (node != null) {
					stack.push(node);
					// System.out.print(node.getInfo() + " ");

					if (node.getlChild() != null)
						siblings.offer(node.getlChild());
					else
						siblings.offer(null);

					if (node.getrChild() != null)
						siblings.offer(node.getrChild());
					else
						siblings.offer(null);
				}

			}

			int countdown = siblings.size();
			for (Node<T> n : siblings) {
				parent.offer(n);

				if (n == null)
					countdown--;
			}

			siblings.clear();
			node = null;
			// System.out.print("\n");

			if (countdown == 0)
				break;
		}
		return stack;
	}

	@Override
	public PriorityQueueBinTreeImpl<T> clone() {
		// clone用
		Queue<Tag> flags = new LinkedList<>();
		Queue<Node<T>> history = new LinkedList<>();
		// 打印Log用
		int level = 0;
		Queue<Node<T>> parent = new LinkedList<>();
		Queue<Node<T>> siblings = new LinkedList<>();
		// 以阶层重新填充clone，首先阶层走访，记录各节点的值与位置
		if (root != null) {
			Node<T> node = root;
			parent.offer(node);
			boolean isRight = false;
			while (node != null || !parent.isEmpty()) {
				// System.out.print("\nlevel " + level++ + ",\t");
				flags.offer(Tag.LEVEL);
				while (!parent.isEmpty()) {
					node = parent.poll();
					if (node == null) {
						if (isRight)
							flags.offer(Tag.RIGHT);
						else
							flags.offer(Tag.LEFT);
						history.offer(null);
						isRight = !isRight;
					} else if (node == root) {
						flags.offer(Tag.ROOT);
						history.offer(root);
						// System.out.println("build root " + node.getData());
						isRight = false;
					} else {
						if (isRight) {
							flags.offer(Tag.RIGHT);
							history.offer(node);
							// System.out.println("insert R child " +
							// node.getData() + " to " +
							// node.getParent().getData());
						} else {
							flags.offer(Tag.LEFT);
							history.offer(node);
							// System.out.println("insert L child " +
							// node.getData() + " to " +
							// node.getParent().getData());
						}
						isRight = !isRight;
					}

					if (node != null) {
						// System.out.print(node.getInfo());

						if (node.getlChild() != null)
							siblings.offer(node.getlChild());
						else
							siblings.offer(null);

						if (node.getrChild() != null)
							siblings.offer(node.getrChild());
						else
							siblings.offer(null);
					}

				}

				int countdown = siblings.size();
				for (Node<T> n : siblings) {
					parent.offer(n);

					if (n == null)
						countdown--;
				}

				siblings.clear();
				node = null;

				if (countdown == 0)
					break;

			}
		}

		// 把刚才收集的节点导入clone里

		level = 0;
		Node<T> head = null;
		// 简单说，parent存储当前要处理的节点（第n层），siblings存下次要处理的节点（第n+1层）
		parent.clear();
		siblings.clear();

		// 先处理根节点
		// flags.peek() == Tag.LEVEL
		// System.out.print("level " + level++ + ",\t");
		flags.poll();
		// flags.peek() == Tag.ROOT
		PriorityQueueBinTreeImpl<T> clone = new PriorityQueueBinTreeImpl<>(history.poll().getData());
		parent.add(clone.root);
		siblings.add(clone.root);
		head = parent.poll();
		// System.out.print("add Root:" + head.getData());
		flags.poll();

		while (true) {
			if (flags.peek() == Tag.LEFT) {
				if (history.peek() == null) {
					// System.out.print("null" + ",\t");
					history.poll();
				} else {
					// System.out.print("add L:" + history.peek().getData() +
					// ",\t");
					siblings.offer(clone.insertLC(head, history.poll().getData()));
				}
			} else if (flags.peek() == Tag.RIGHT) {
				if (history.peek() == null) {
					// System.out.print("null" + ",\t");
					history.poll();
				} else {
					// System.out.print("add R:" + history.peek().getData() +
					// ",\t");
					siblings.offer(clone.insertRC(head, history.poll().getData()));
				}
				head = parent.poll();
			} else {// level
				// System.out.print("\nlevel " + level++ + ",\t");
				int countdown = siblings.size();
				for (Node<T> n : siblings) {
					parent.offer(n);

					if (n == null)
						countdown--;
				}

				siblings.clear();
				head = parent.poll();
				if (countdown == 0)
					break;
			}
			flags.poll();
		}

		flags.clear();
		history.clear();
		parent.clear();
		siblings.clear();
		return clone;
	}

	public T[] toArray(T[] a) {
		copyInto(a);
		return a;
	}

	@Override
	public void copyInto(Object[] a) {
		if (a == null || a.length < size)
			throw new IndexOutOfBoundsException("Array size must be the same as size or larger as well.");

		PriorityQueueBinTreeImpl<T> clone = clone();
		a[0] = clone.getMax();
		for (int i = 1; i < clone.size - 1; i++) {
			a[i] = clone.delMax();
		}
	}
}
