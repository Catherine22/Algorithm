package com.catherine.data_type.trees;

import java.util.Stack;

/**
 * 
 * @author Catherine
 *
 *         每个节点都有0～2个子节点
 *
 * @param <E>
 */
public class MyBinarySearchTree<E> implements java.io.Serializable {

	private static final long serialVersionUID = 551109471535675044L;
	private final static boolean SHOW_LOG = true;
	transient int size = 0;
	private Node<E> root;

	public MyBinarySearchTree(E root) {
		setRoot(root);
	}

	/**
	 * 设置根节点
	 * 
	 * @param 数值
	 * @return 根节点
	 */
	public Node<E> setRoot(E data) {
		size++;
		Node<E> n;
		if (root == null)
			n = new Node<>(data, null, null, null, 0);
		else
			n = new Node<>(data, null, root.lChild, root.rChild, root.height);
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
		 * 节点到叶子的最长长度
		 */
		int height;
		E data;
		Node<E> parent;
		Node<E> lChild;
		Node<E> rChild;

		public Node(E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height) {
			this.data = data;
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
			final int h = cNode.height;
			child = new Node<>(data, parent, lChild, rChild, h + 1);
		} else
			child = new Node<>(data, parent, null, null, 0);

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
			final int h = cNode.height;
			child = new Node<>(data, parent, lChild, rChild, h + 1);
		} else
			child = new Node<>(data, parent, null, null, 0);

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
		parent.rChild = null;
		parent.height = 0;
		updateAboveHeight(parent);
	}

	/**
	 * 移除整个左子树
	 * 
	 * @param parent
	 *            父节点
	 */
	public void removeLCCompletely(Node<E> parent) {
		parent.lChild = null;
		parent.height = 0;
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
	 * 中序时的直接后继
	 * 
	 * @return
	 */
	public Node<E> succ() {
		return null;
	}

	/**
	 * 以阶层遍历
	 */
	public void traverseLevel() {

	}

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePreNR1() {
		if (root == null)
			throw new NullPointerException("null root!");

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
	}

	/**
	 * 使用迭代而非递归<br>
	 * 先序遍历（中-左-右）<br>
	 * 从根出发，先遍历所有左节点（斜线路径），再遍历隔壁排直到遍历全部节点。
	 */
	public void traversePreNR2() {
		if (root == null)
			throw new NullPointerException("null root!");
		System.out.println("non-recursively pre-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;

		while (node != null || bin.size() > 0) {
			// 遍历一排的所有左节点
			while (node != null) {
				System.out.print(node.data + " ");// 打印父节点
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
	}

	/**
	 * 递归<br>
	 * 先序遍历（中-左-右）
	 */
	public void traversePre() {
		if (root == null)
			throw new NullPointerException("null root!");

		System.out.println("recursively pre-order traverse:");
		traversePre(root);
		System.out.println("\n");
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
	 * 中序遍历（左-中-右）
	 */
	public void traverseInNR() {
		if (root == null)
			throw new NullPointerException("null root!");

		System.out.println("non-recursively in-order traverse:");

		System.out.println("\n");
	}

	/**
	 * 递归<br>
	 * 中序遍历（左-中-右）
	 */
	public void traverseIn() {
		if (root == null)
			throw new NullPointerException("null root!");

		System.out.println("recursively in-order traverse:");
		traverseIn(root);
		System.out.println("\n");
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
	 * 使用迭代而非递归<br>
	 * 后序遍历（左-右-中）
	 */
	public void traversePostNR() {
		if (root == null)
			throw new NullPointerException("null root!");

		System.out.println("non-recursively post-order traverse:");

		System.out.println("\n");
	}

	/**
	 * 递归<br>
	 * 后序遍历（左-右-中）
	 */
	public void traversePost() {
		if (root == null)
			throw new NullPointerException("null root!");

		System.out.println("post-order traverse:");
		traversePost(root);
		System.out.println("\n");
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
