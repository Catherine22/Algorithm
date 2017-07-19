package com.catherine.trees;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;

/**
 * 四个规则：<br>
 * 1. 树根必为黑色<br>
 * 2. 外部节点均为黑色<br>
 * 3. 其余节点若为红色，则只能有黑孩子（也就是不可能出现同时为红的父子两代，也就是出现一个红节点，该节点的父子比为黑色。）<br>
 * 4. 外部节点到根，途中黑节点数目相等<br>
 * <br>
 * 把红孩子向上提升至父亲旁边，再把他们看作一个超级节点，红黑树就像是四阶的B树或者又称为(2, 4)树，每个节点都有个对应(2, 4)树的key。<br>
 * 
 * @author Catherine
 *
 */
public class RedBlackBSTImpl<E> extends BinarySearchTreeImpl<E> implements RedBlackBST<E> {
	private final static boolean SHOW_LOG = true;

	public RedBlackBSTImpl(int key, E root) {
		super();
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.RB);
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
			n = adapter.buildNode(key, data, null, null, null, 0, 0);
		} else
			n = adapter.buildNode(key, data, null, root.getlChild(), root.getrChild(), root.getHeight(),
					root.getDepth());
		root = n;
		// 根节点比为黑色
		root.setColor(false);
		hot = root;
		return root;
	}

	/**
	 * 只返回黑节点的高度。<br>
	 * 根到任意叶子的黑节点高度都一样。
	 * 
	 * @return
	 */
	@Override
	public int getHeight() {
		if (root == null)
			return -1;

		// 检查任意一个孩子就行了，因为若只有一个孩子必为红孩子，不影响结果。
		if (root.getlChild() == null)
			return 1;

		return getHeight(root);
	}

	/**
	 * 到任意叶子的黑节点高度都一样。
	 * 
	 * @param node
	 * @return
	 */
	protected int getHeight(Node<E> node) {
		if (node == null)
			return -1;

		int height = 0;
		boolean stop = false;
		Node<E> header = root;
		// 走访任意路径
		while (!stop) {
			if (header != null) {
				if (header.isBlack())
					height++;
				// 随意一个孩子都行
				header = header.getlChild();
			} else
				stop = true;
		}
		return height;
	}

	@Override
	public Node<E> insert(int key, E data) {
		Node<E> node = super.insert(key, data);
		solveDoubleRed(node);
		return node;
	}

	@Override
	public void remove(int key) {

	}

	@Override
	public void solveDoubleRed(Node<E> node) {

	}

	@Override
	public void solveDoubleBlack(Node<E> node) {

	}

	@Override
	protected void updateAboveHeight(Node<E> node) {

	}

	@Override
	public void updateHeight() {

	}

	@Override
	public MyBTree convertTo24Tree() {
		return null;
	}
}
