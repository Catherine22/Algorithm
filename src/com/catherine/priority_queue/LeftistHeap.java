package com.catherine.priority_queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.catherine.data_type.Search;
import com.catherine.trees.nodes.Node;

/**
 * 左式堆<br>
 * 用来高效合并两个已排序的堆。<br>
 * <br>
 * 特性：<br>
 * 1. 保证堆序性<br>
 * 2. 拓扑结构上不会是完全二叉树<br>
 * 3. 引入外部节点与npl(Null point length，即任意节点到外部节点到最短距离)<br>
 * 4. 任意节点x的左孩子npl>=右孩子npl则成为左倾堆或左式堆<br>
 * 
 * @author Catherine
 *
 * @param <T>
 */
public class LeftistHeap<T extends Comparable<? super T>> extends PriorityQueueBinTreeImpl<T> {
	protected final boolean SHOW_DEBUG_LOG = false;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 5439346124731561315L;

	/**
	 * 任意节点到外部节点到最短距离，同时也代表以t为根节点的最大满子树高度。<br>
	 * 1. 外部节点npl必为0<br>
	 * 2. 返回左右孩子中npl较小值+1
	 * 
	 * @param t
	 * @return
	 */
	private int getNPL(Node<T> node) {
		if (node == null)
			return 0;

		// 本来完整逻辑应为：
		// int ln = getNPL(node.getlChild());
		// int rn = getNPL(node.getrChild());
		// if (ln > rn)
		// return rn + 1;
		// else
		// return ln + 1;

		// 但左式堆的定义让左孩子npl必>=右孩子npl，因此只需考虑右孩子
		return getNPL(node.getrChild()) + 1;

	}

	/**
	 * 右侧链，从节点t出发，一直延右侧前进。<br>
	 * 右侧链的长度=npl(t)<br>
	 * <br>
	 * 
	 * 一个右侧链长为d的左式堆，<br>
	 * 最少有2的d次方-1个内部节点<br>
	 * 最少有2的d+1次方-1个节点<br>
	 * <br>
	 * 可以类推，n个节点的左式堆 d<=[底数为2的log(n+1)]-1=O(log n) <br>
	 * 所以左式堆的目标就是要将操作控制在右侧链，达到O(log n)
	 * 
	 * @param t
	 */
	public Collection<Node<T>> getRChain(Node<T> n) {
		if (n == null)
			throw new NullPointerException("n must not be null.");
		Collection<Node<T>> collection = new LinkedList<>();
		collection.add(n);
		Node<T> next = n.getrChild();
		while (next != null) {
			collection.add(next);
			next = next.getrChild();
		}
		return collection;
	}

	public Collection<Node<T>> getRChain() {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		return getRChain(root);
	}

	/**
	 * 合并(若合并两个堆，传入两堆的根节点)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Node<T> merge(Node<T> a, Node<T> b) {
		// System.out.println(String.format("merge a(%s) and b(%s)", a == null ?
		// "null" : a.getData(),
		// b == null ? "null" : b.getData()));
		if (a == null)
			return b;
		if (b == null)
			return a;

		if (a.getData().compareTo(b.getData()) < 0) {
			// 交换引用
			Node<T> tmp = a;
			a = b;
			b = tmp;
		}

		// 将b合并到a的右子堆
		a.setrChild(merge(a.getrChild(), b));
		a.getrChild().setParent(a);

		// 确保右子堆的NPL较小
		if (a.getlChild() == null || getNPL(a.getlChild()) < getNPL(a.getrChild())) {
			// 交换引用
			Node<T> tmp = a.getlChild();
			a.setlChild(a.getrChild());
			a.setrChild(tmp);
		}

		return a;
	}

	/**
	 * 左式堆的插入就是合并。 {@link #merge(Node, Node)}
	 */
	public void insert(T a) {
		if (root == null) {
			System.out.println("setRoot:" + a);
			setRoot(a);
			return;
		}

		PriorityQueueBinTreeImpl<T> t = new PriorityQueueBinTreeImpl<>(a);
		Node<T> n = merge(root, t.getRoot());
		if (n == t.getRoot()) {
			updateRefer(t);
		}
	}

	/**
	 * 删除该节点，合并其左右孩子。 {@link #merge(Node, Node)}
	 * 
	 * @param a
	 */
	public void remove(T t) {
		remove(find(t));
	}

	/**
	 * 删除该节点，合并其左右孩子。 {@link #merge(Node, Node)}
	 * 
	 * @param a
	 */
	public void remove(Node<T> node) {
		Node<T> lc = node.getlChild();
		Node<T> rc = node.getrChild();
		Node<T> p = node.getParent();

		if (lc != null)
			lc.setParent(null);
		if (rc != null)
			rc.setParent(null);
		
		if (lc == null && rc == null) {
			if (p == null) {
				root = null;
				size = 0;
			} else {
				if (node == p.getlChild())
					p.setlChild(null);
				else
					p.setrChild(null);
			}
			return;
		}

		Node<T> n = merge(lc, rc);

		if (p == null)
			updateRefer(n);
		else {
			n.setParent(p);
			if (node == p.getlChild())
				p.setlChild(n);
			else
				p.setrChild(n);
		}
	}

	private void updateRefer(Node<T> newRoot) {
		List<T> tmp = new LinkedList<>();
		traverseIn(tmp, newRoot);
		root = newRoot;
		root.setParent(null);
		size = tmp.size();
		tmp.clear();
		tmp = null;
	}

	private void updateRefer(PriorityQueueBinTreeImpl<T> refer) {
		root = refer.getRoot();
		root.setParent(null);
		size = refer.size();
	}

	public void printTree() {
		printTree(root);
	}

	public void printTree(Node<T> a) {
		Queue<Node<T>> parent = new LinkedList<>();
		Queue<Node<T>> siblings = new LinkedList<>();
		Node<T> node = a;
		parent.offer(node);
		int level = 0;
		boolean isRight = false;
		while (node != null || !parent.isEmpty()) {
			System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				if (node == root) {
					isRight = false;
				} else {
					isRight = !isRight;
				}

				if (node != null) {
					// System.out.print(String.format("%snpl:%d\t",
					// node.getInfo(), getNPL(node)));
					System.out.print(node.getInfo() + "\t");
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
			System.out.print("\n");

			if (countdown == 0)
				break;
		}
	}
}
