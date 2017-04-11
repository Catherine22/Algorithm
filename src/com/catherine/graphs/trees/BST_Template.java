package com.catherine.graphs.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.catherine.graphs.trees.BST_Template.Node;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * 大致和{@link com.catherine.graphs.trees.MyBinaryTree}差不多，但Node多了key值，为了BST排序用。
 * 
 * @author Catherine
 *
 * @param <E>
 */
abstract class BST_Template<E> {
	final static boolean SHOW_LOG = false;
	transient int size = 0;
	Node<E> root;
	/**
	 * 二叉搜寻中，有找到，hot则指向该节点的父节点；没找到指向最后访问的节点。<br>
	 * 加入哨兵的概念后，hot永远代表目标搜节点的父节点。
	 */
	Node<E> hot;

	BST_Template(int key, E root) {
		setRoot(key, root);
	}

	/**
	 * 设置根节点
	 * 
	 * @param 数值
	 * @return 根节点
	 */
	public Node<E> setRoot(int key, E data) {
		Node<E> n;
		if (root == null) {
			size++;
			n = new Node<>(key, data, null, null, null, 0);
		} else
			n = new Node<>(key, data, null, root.lChild, root.rChild, root.height);
		root = n;
		hot = root;
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
		 * 节点到叶子的最长长度
		 */
		int height;

		/**
		 * key-value, key不重复
		 */
		int key;
		E data;
		Node<E> parent;
		Node<E> lChild;
		Node<E> rChild;

		public Node(int key, E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height) {
			this.key = key;
			this.data = data;
			this.height = height;
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
		}

		public String toString() {
			if (parent != null)
				return String.format("{\"key\": \"%d\", \"data\": \"%s\", \"height\": %d, \"parent_key\": \"%d\"}", key,
						data, height, parent.key);
			else
				return String.format("{\"key\": \"%d\", \"data\": \"%s\", \"height\": %d, \"parent_key\": \"%s\"}", key,
						data, height, "null parent");
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
	 * 内部方法{@link #remove(int)}专用<br>
	 * grandParent - parent - grandchild，移除parent<br>
	 * size和height在{@link #remove(int)}处理
	 * 
	 * @param grandParent
	 * @param grandchild
	 */
	protected void killParent(Node<E> grandParent, Node<E> grandchild) {
		Node<E> parent = grandchild.parent;
		if (grandParent.rChild == parent) {
			grandParent.rChild = grandchild;
			grandchild.parent = grandParent;
			parent = null;
		} else if (grandParent.lChild == parent) {
			grandParent.lChild = grandchild;
			grandchild.parent = grandParent;
			parent = null;
		}
	}

	/**
	 * 内部方法{@link #remove(int)}专用<br>
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
		int tmpKey = node1.key;
		int tmpHeigh = node1.height;
		E tmpData = node1.data;

		node1.key = node2.key;
		node1.height = node2.height;
		node1.data = node2.data;
		node2.key = tmpKey;
		node2.height = tmpHeigh;
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
	protected void updateAboveHeight(Node<E> node) {
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
	Node<E> insertLC(Node<E> parent, int key, E data) {
		Node<E> child;
		if (parent.lChild != null) {
			final Node<E> cNode = parent.lChild;
			final Node<E> lChild = cNode.lChild;
			final Node<E> rChild = cNode.rChild;
			final int h = cNode.height;
			child = new Node<>(key, data, parent, lChild, rChild, h + 1);
		} else
			child = new Node<>(key, data, parent, null, null, parent.height + 1);

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
	Node<E> insertRC(Node<E> parent, int key, E data) {
		Node<E> child;
		if (parent.rChild != null) {
			final Node<E> cNode = parent.rChild;
			final Node<E> lChild = cNode.lChild;
			final Node<E> rChild = cNode.rChild;
			final int h = cNode.height;
			child = new Node<>(key, data, parent, lChild, rChild, h + 1);
		} else
			child = new Node<>(key, data, parent, null, null, parent.height + 1);

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
				System.out.print(node.key + " ");

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
			System.out.print(node.key + " ");

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
				System.out.print(node.key + " ");
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
		System.out.print(node.key + " ");
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
				System.out.print(node.key + " ");
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
		System.out.print(node.key + " ");
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
					System.out.print(tmp.key + "(NULL) ");
				else
					System.out.print(tmp.key + "(" + preTmp.key + ") ");
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
				System.out.print(node.key + " ");
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
			System.out.print(rBin.peek().key + " ");
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
		System.out.print(node.key + " ");
	}

	/**
	 * 由于所有节点的垂直投影就是该树的中序遍历，搜寻时用{@link com.catherine.data_type.Search}
	 * 的binSearch概念。<br>
	 * 无论成功与否，都是指向命中节点。（没找到时，在{@link #hot}建立一个子节点作为哨兵，整棵树仍然是BST<br>
	 * <br>
	 * 每比较一次，节点的深度都会下降一层，也就是递归深度不超过树高。<br>
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
	public abstract Node<E> search(int key);

	/**
	 * 先做一次遍历，得到hot，用hot作为父节点插入。 暂不考虑重复数值情况。
	 * 
	 * @param key
	 *            插入节点的key
	 * @param data
	 *            插入节点的value
	 */
	public abstract void insert(int key, E data);

	/**
	 * 情况1:欲移除节点只有一个左孩子或右孩子，移除节点后孩子上位，取代原节点。<br>
	 * 情况2:欲移除节点有左右孩子。化繁为简，变成情况1再处理<br>
	 * 情况2先找出目标节点的后继节点{@link #succ(Node)}，两节点交换后就变成情况1（顶多只会有右孩子，因为后继已经是最左边的节点了），
	 * 比照情况1处理，这边还要再将欲移除节点的父节点设为hot，重新整理树高。
	 * 
	 * @param key
	 */
	public abstract void remove(int key);
}
