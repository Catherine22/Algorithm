package com.catherine.data_type.trees;

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
		root.height = 1;
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
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
			this.height = height;
		}
	}

	/**
	 * 是否为空树（没有节点）
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
	 * @param index
	 *            父节点索引
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertLC(int index, E data) {
		return null;
	}

	/**
	 * 插入子节点于右边
	 * 
	 * @param index
	 *            父节点索引
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertRC(int index, E data) {
		return null;
	}

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
	 * 遍历
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
}
