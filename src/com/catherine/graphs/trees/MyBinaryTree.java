package com.catherine.graphs.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * 
 * @author Catherine
 *
 *         每个节点都有0～2个子节点
 *
 * @param <E>
 */
public class MyBinaryTree<E> implements java.io.Serializable {

	private static final long serialVersionUID = 551109471535675044L;
	private final static boolean SHOW_LOG = true;
	transient int size = 0;
	private Node<E> root;

	public MyBinaryTree(E root) {
		setRoot(root);
	}

	/**
	 * 设置根节点
	 * 
	 * @param 数值
	 * @return 根节点
	 */
	public Node<E> setRoot(E data) {
		Node<E> n;
		if (root == null) {
			size++;
			n = new Node<>(data, null, null, null, 0, 0);
		} else
			n = new Node<>(data, null, root.lChild, root.rChild, root.height, root.depth);
		root = n;
		return root;
	}

	/**
	 * 返回根节点
	 * 
	 * @return 根节点
	 */
	public Node<E> getRoot() {
		return root;
	}

	public static class Node<E> {

		/**
		 * 节点到叶子的最长长度（由下往上，从最下层孩子出发）
		 */
		int height;

		/**
		 * 根到节点的最长长度（由上往下，从根出发）
		 */
		int depth;
		E data;
		Node<E> parent;
		Node<E> lChild;
		Node<E> rChild;

		public Node(E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height, int depth) {
			this.data = data;
			this.depth = depth;
			this.height = height;
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
		}
	}

	/**
	 * 是否为空树（没有节点）
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * 子树规模
	 * 
	 * @return 子节点数
	 */
	public int size() {
		return size;
	}

	/**
	 * 当前节点的子树规模
	 * 
	 * @param 指定节点
	 * @return 子节点数
	 */
	public int size(Node<E> node) {
		int s = 1;// 加入自身
		if (node.lChild != null)
			s += size(node.lChild);

		if (node.rChild != null)
			s += size(node.rChild);
		return s;
	}

	/**
	 * 交换两节点的值（仍是同样的引用）
	 * 
	 * @param node1
	 * @param node2
	 */
	protected void swap(Node<E> node1, Node<E> node2) {
		if (SHOW_LOG) {
			System.out.println("node1:" + node1.toString());
			System.out.println("node2:" + node2.toString());
		}
		int tmpHeight = node1.height;
		int tmpDepth = node1.depth;
		E tmpData = node1.data;

		node1.height = node2.height;
		node1.depth = node2.depth;
		node1.data = node2.data;
		node2.height = tmpHeight;
		node2.depth = tmpDepth;
		node2.data = tmpData;

		if (SHOW_LOG) {
			System.out.println("new node1:" + node1.toString());
			System.out.println("new node2:" + node2.toString());
		}
	}

	/**
	 * 每加入或移除一子节点，父节点及其父节点等高度都会变动。 <br>
	 * 二叉树须检查是否已被兄弟节点修改过。 <br>
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：-1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * <br>
	 * 只有两种可能，parent的高度大于等于2，表示为移除节点<br>
	 * parent的高度和自身一样，表示为新增节点<br>
	 * 
	 * <br>
	 * parent的高度=自身+1为正常情况。
	 * 
	 * @return 高度
	 */
	private void updateAboveHeight(Node<E> node) {
		if (node.parent == null)
			return;

		if (node.height == node.parent.height) {
			node.parent.height++;
			updateAboveHeight(node.parent);
		} else if (node.parent.height - node.height >= 2) {
			node.parent.height = node.height + 1;
			updateAboveHeight(node.parent);
		} else
			return;
	}

	/**
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：-1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * 
	 * @return 高度
	 */
	public int getHeight() {
		if (root == null)
			return -1;

		if (root.lChild == null && root.rChild == null)
			return 0;

		return getHighestChild(root);
	}

	/**
	 * 使用递归计算高度
	 * 
	 * @param node
	 * @return
	 */
	private int getHighestChild(Node<E> node) {
		int l = 0;
		int r = 0;

		if (node == null)
			return -1;// 若为叶子，上一次判断时会直达else判断式，因此l和r都多加一次，在此处扣除

		if (node.lChild != null && node.rChild == null) {
			l += getHighestChild(node.lChild);
			l++;
		} else if (node.lChild == null && node.rChild != null) {
			r += getHighestChild(node.rChild);
			r++;
		} else {
			l += getHighestChild(node.lChild);// 若为叶子得-1
			r += getHighestChild(node.rChild);// 若为叶子得-1
			l++;
			r++;
		}

		if (SHOW_LOG)
			System.out.println(node.data + "\tl:" + l + "\tr:" + r + "\th:" + node.height);
		return (l > r) ? l : r;
	}

	/**
	 * 满二叉树（full binary tree）、真二叉树（proper binary tree）又称为严格二叉树（strictly binary
	 * tree），每个节点都只有0或2个节点。
	 * 
	 * @return
	 */
	public boolean isFull() {
		return false;
	};

	/**
	 * 插入子节点于左边，若已有节点则成为该节点的父节点
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertLC(Node<E> parent, E data) {
		Node<E> child;
		if (parent.lChild != null) {
			final Node<E> cNode = parent.lChild;
			final Node<E> lChild = cNode.lChild;
			final Node<E> rChild = cNode.rChild;
			child = new Node<>(data, parent, lChild, rChild, cNode.height, cNode.depth);
		} else
			child = new Node<>(data, parent, null, null, 0, parent.depth + 1);

		if (SHOW_LOG)
			System.out.println("insertLC:" + child.toString());
		size++;
		parent.lChild = child;
		updateAboveHeight(child);
		return child;
	}

	/**
	 * 插入子节点于右边，若已有节点则成为该节点的父节点
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertRC(Node<E> parent, E data) {
		Node<E> child;
		if (parent.rChild != null) {
			final Node<E> cNode = parent.rChild;
			final Node<E> lChild = cNode.lChild;
			final Node<E> rChild = cNode.rChild;
			child = new Node<>(data, parent, lChild, rChild, cNode.height, cNode.depth);
		} else
			child = new Node<>(data, parent, null, null, 0, parent.depth + 1);

		if (SHOW_LOG)
			System.out.println("insertRC:" + child.toString());
		size++;
		parent.rChild = child;
		updateAboveHeight(child);
		return child;
	}

	/**
	 * 移除整个右子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeRCCompletely(Node<E> parent) {
		if (parent.rChild != null)
			size -= size(parent.rChild);
		parent.rChild = null;
		parent.height = (parent.lChild != null) ? parent.lChild.height + 1 : 0;
		updateAboveHeight(parent);
	}

	/**
	 * 移除整个左子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeLCCompletely(Node<E> parent) {
		if (parent.lChild != null)
			size -= size(parent.lChild);
		parent.lChild = null;
		parent.height = (parent.rChild != null) ? parent.rChild.height + 1 : 0;
		updateAboveHeight(parent);
	}

	/**
	 * 修改左子树的值，如果没有就创建一个
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 原数值
	 */
	public E setLC(Node<E> parent, E data) {
		if (parent.lChild == null) {
			insertLC(parent, data);
			return null;
		}

		final E o = parent.lChild.data;
		parent.lChild.data = data;
		return o;
	}

	/**
	 * 修改右子树的值，如果没有就创建一个
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 原数值
	 */
	public E setRC(Node<E> parent, E data) {
		if (parent.rChild == null) {
			insertRC(parent, data);
			return null;
		}

		final E o = parent.rChild.data;
		parent.rChild.data = data;
		return o;
	}

	/**
	 * 以阶层遍历
	 */
	public void traverseLevel() {
		if (root == null)
			throw new NullPointerException("null root!");

		Queue<Node<E>> parent = new LinkedList<>();
		Queue<Node<E>> siblings = new LinkedList<>();
		Node<E> node = root;
		parent.offer(node);
		int level = 0;

		while (node != null || !parent.isEmpty()) {
			System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				if (node.data == null)
					System.out.print("null ");
				else
					System.out.print(node.data + " ");

				if (node.lChild != null)
					siblings.offer(node.lChild);

				if (node.rChild != null)
					siblings.offer(node.rChild);
			}

			for (Node<E> n : siblings)
				parent.offer(n);

			siblings.clear();
			node = null;

			System.out.print("\n");
		}
	}

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePreNR1() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePreNR1");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively pre-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		bin.push(root);
		while (!bin.isEmpty()) {
			Node<E> node = bin.pop();
			if (node.data == null)
				System.out.print("null ");
			else
				System.out.print(node.data + " ");

			if (node.rChild != null)
				bin.push(node.rChild);

			if (node.lChild != null)
				bin.push(node.lChild);
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）<br>
	 * 从根出发，先遍历所有左节点（斜线路径），再遍历隔壁排直到遍历全部节点。<br>
	 * <br>
	 * 乍一看嵌套两个循环应该是O(n^2)，但是实际上每个节点都只有被push操作一次，也就是其实运行时间还是O(n)，就系数来看，其实还比递归快。
	 */
	public void traversePreNR2() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePreNR2");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively pre-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;

		while (node != null || !bin.isEmpty()) {
			// 遍历一排的所有左节点
			while (node != null) {
				if (node.data == null)
					System.out.print("null ");
				else
					System.out.print(node.data + " ");
				bin.push(node);// 弹出打印过的没用节点
				node = node.lChild;
			}

			// 遍历过左节点后前往最近的右节点，之后再遍历该右节点的整排左节点
			if (bin.size() > 0) {
				node = bin.pop();
				node = node.rChild;
			}
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePre() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePre");
		Analysis.startTracking(tLog);

		System.out.println("recursively pre-order traverse:");
		traversePre(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点先序遍历（中-左-右）
	 */
	public void traversePre(Node<E> node) {
		if (node.data == null)
			System.out.print("null ");
		else
			System.out.print(node.data + " ");
		if (node.lChild != null)
			traversePre(node.lChild);
		if (node.rChild != null)
			traversePre(node.rChild);
	}

	/**
	 * 使用迭代而非递归<br>
	 * 中序遍历（左-中-右）<br>
	 * 每个左侧节点就是一条链，由最左下的节点开始遍历右子树。 <br>
	 * <br>
	 * 乍一看嵌套两个循环应该是O(n^2)，但是实际上每个节点都只有被push操作一次，也就是其实运行时间还是O(n)，就系数来看，其实还比递归快。
	 * 
	 */
	public void traverseInNR() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traverseInNR");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively in-order traverse:");

		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;

		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.lChild;
			}
			if (!bin.isEmpty()) {
				node = bin.pop();
				if (node.data == null)
					System.out.print("null ");
				else
					System.out.print(node.data + " ");
				node = node.rChild;
			}
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 中序遍历（左-中-右）
	 */
	public void traverseIn() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traverseIn");
		Analysis.startTracking(tLog);

		System.out.println("recursively in-order traverse:");
		traverseIn(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点中序遍历（左-中-右）
	 */
	public void traverseIn(Node<E> node) {
		if (node.lChild != null)
			traverseIn(node.lChild);
		if (node.data == null)
			System.out.print("null ");
		else
			System.out.print(node.data + " ");
		if (node.rChild != null)
			traverseIn(node.rChild);
	}

	/**
	 * {@link #succ(Node)}专用，记录上次中序访问节点
	 */
	private Node<E> preTmp;
	/**
	 * {@link #succ(Node)}专用，记录直接后继
	 */
	private Node<E> succ;
	/**
	 * {@link #succ(Node)}专用，停止递归
	 */
	private boolean stopRecursion = false;

	/**
	 * 返回当前节点在中序意义下的直接后继。
	 * 
	 * @param node
	 *            当前节点
	 * @return 后继节点
	 */
	public Node<E> succ(Node<E> node) {
		succ = null;
		preTmp = null;
		stopRecursion = false;
		succ(node, root);
		return succ;
	}

	/**
	 * 
	 * @param node
	 *            指定节点（固定）
	 * @param tmp
	 *            每次递归的节点
	 */
	private void succ(Node<E> node, Node<E> tmp) {
		if (!stopRecursion) {
			if (tmp.lChild != null)
				succ(node, tmp.lChild);
			if (SHOW_LOG) {
				if (preTmp == null)
					System.out.print(tmp.data + "(NULL) ");
				else
					System.out.print(tmp.data + "(" + preTmp.data + ") ");
			}
			// 目的是要找出直接后继，一旦上一个节点为指定节点，表示这次的节点就是要找的直接后继
			if (node == preTmp) {
				succ = tmp;
				stopRecursion = true;
			}
			preTmp = tmp;
			if (tmp.rChild != null)
				succ(node, tmp.rChild);
		}
	}

	/**
	 * 使用迭代而非递归<br>
	 * 后序遍历（左-右-中）<br>
	 * 先找到最左下的节点，检查是否有右子树，如果有也要用前面的方法继续找直到没有右子树为止。
	 */
	public void traversePostNR1() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePostNR1");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively post-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;
		Node<E> lastLC = null;// 如果该节点有右子树，遍历其右子树之前先暂存该节点。

		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.lChild;
			}

			node = bin.peek();

			// 当前节点的右孩子如果为空或者已经被访问，则访问当前节点
			if (node.rChild == null || node.rChild == lastLC) {
				if (node.data == null)
					System.out.print("null ");
				else
					System.out.print(node.data + " ");
				lastLC = node;// 一旦访问过就要记录，下一轮就会判断到node.rChild == lastLC
				bin.pop();// 打印过就从栈里弹出
				node = null;// 其实node应为栈中最后一个节点，下一轮会指定bin.peek()
			} else
				node = node.rChild;
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 使用迭代而非递归<br>
	 * 后序遍历（左-右-中）<br>
	 * 双栈法
	 */
	public void traversePostNR2() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePostNR2");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively post-order traverse:");
		Stack<Node<E>> lBin = new Stack<>();
		Stack<Node<E>> rBin = new Stack<>();
		Node<E> node = root;
		lBin.push(node);

		while (!lBin.isEmpty()) {
			node = lBin.pop();
			rBin.push(node);

			if (node.lChild != null)
				lBin.push(node.lChild);

			if (node.rChild != null)
				lBin.push(node.rChild);
		}

		while (!rBin.isEmpty()) {
			if (rBin.peek().data == null)
				System.out.print("null ");
			else
				System.out.print(rBin.peek().data + " ");
			rBin.pop();
		}

		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 后序遍历（左-右-中）
	 */
	public void traversePost() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePost");
		Analysis.startTracking(tLog);

		System.out.println("post-order traverse:");
		traversePost(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点后序遍历（左-右-中）
	 */
	public void traversePost(Node<E> node) {
		if (node.lChild != null)
			traversePost(node.lChild);
		if (node.rChild != null)
			traversePost(node.rChild);
		if (node.data == null)
			System.out.print("null ");
		else
			System.out.print(node.data + " ");
	}
}
