package com.catherine.data_type.trees;

/**
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyBinarySearchTree<E> implements java.io.Serializable {

	private static final long serialVersionUID = 551109471535675044L;
	private final static boolean SHOW_LOG = false;
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
		root.data = data;
		root.parent = null;
		return root;
	}

	public static class Node<E> {

		E data;
		int height;
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
	 * 每加入一子节点，父节点及其父节点等高度都会变动。 <br>
	 * <br>
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * 
	 * @return 高度
	 */
	private void updateAboveHeight(Node<E> node) {

	}

	/**
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：1<br>
	 * 3. 其他：取左右子树中高度大着+1（含自身）<br>
	 * 
	 * @return 高度
	 */
	public int getHeight() {
		if (root == null)
			return 1;

		if (root.lChild == null && root.rChild == null)
			return 0;

		return getHighestChild(root);
	}

	private int getHighestChild(Node<E> node) {
		int l = 0;
		int r = 0;

		if (node.lChild != null && node.rChild == null)
			l += getHighestChild(node.lChild);
		else if (node.lChild == null && node.rChild != null)
			r += getHighestChild(node.rChild);
		else {
			l += getHighestChild(node.lChild);
			r += getHighestChild(node.rChild);
		}

		System.out.println("l:" + l + "\tr:" + r);
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
	 * 插入子节点于左边
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertLC(Node<E> parent, E data) {
		Node<E> child = new Node<>(data, parent, null, null, parent.height + 1);
		parent.lChild = child;
		return child;
	}

	/**
	 * 插入子节点于右边
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertRC(Node<E> parent, E data) {
		Node<E> child = new Node<>(data, parent, null, null, parent.height + 1);
		parent.rChild = child;
		return child;
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
	 * 先序遍历
	 */
	public void traversePre() {

	}

	/**
	 * 中序遍历
	 */
	public void traverseIn() {

	}

	/**
	 * 后序遍历
	 */
	public void traversePast() {

	}

	public String toString() {
		if (root == null)
			return "null tree";
	}
}
