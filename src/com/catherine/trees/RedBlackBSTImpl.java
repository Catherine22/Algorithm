package com.catherine.trees;

import java.util.ArrayList;
import java.util.List;

import com.catherine.trees.nodes.BNode;
import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
import com.catherine.trees.nodes.RedBlackBSTNode;
import com.catherine.trees.nodes.RedBlackBSTNode.Color;

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
		root.setColor(RedBlackBSTNode.Color.BLACK);
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
	 * 目标节点到任意叶子的黑节点高度。<br>
	 * 由于高度都一样，就采用任意路径。
	 * 
	 * @param node
	 * @return
	 */
	@Override
	protected int getHeight(Node<E> node) {
		Node<E> child = (node.getlChild() == null) ? node.getrChild() : node.getlChild();
		if (child == null) {
			// 表示该节点为叶节点
			return 0;
		}
		return (child.isBlack()) ? child.getHeight() + 1 : child.getHeight();
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
		if (node == null || node.getParent() == null)
			return;

		if (node.isRed() && node.getParent().isRed()) {
			Node<E> parent = node.getParent();
			Node<E> grandP = parent.getParent();

			// 只有两节点的情况。
			if (grandP == null)
				return;

			Node<E> uncle = (parent == grandP.getlChild()) ? grandP.getrChild() : grandP.getlChild();

			// 情况1——挑出三个节点来看，无视叔父节点。B-tree中间节点变黑色，两边红色
			if (uncle == null || uncle.isBlack()) {
				boolean isLeftNode = (node == parent.getlChild());
				boolean isLeftParent = (parent == grandP.getlChild());
				if (isLeftNode == isLeftParent) {
					if (SHOW_LOG)
						System.out.println("双红缺陷1，三者一直线");
					grandP.setColor(RedBlackBSTNode.Color.RED);
					parent.setColor(RedBlackBSTNode.Color.BLACK);
				} else {
					if (SHOW_LOG)
						System.out.println("双红缺陷1，三者>或<");
					grandP.setColor(RedBlackBSTNode.Color.RED);
					node.setColor(RedBlackBSTNode.Color.BLACK);
				}
				return;
			}
			// 情况2——四节点合起来看就是B-tree的上溢，最上面的节点为红色，两旁黑色，最下面红色。
			else {
				if (SHOW_LOG)
					System.out.print("双红缺陷2，");

				// 排列此四个节点的顺序，不论阶层。
				Node<E> n0 = null;
				Node<E> n1 = null;
				Node<E> n2 = null;
				Node<E> n3 = null;

				if (uncle == grandP.getlChild()) {
					n0 = uncle;
					n1 = grandP;

					if (node == parent.getlChild()) {
						if (SHOW_LOG)
							System.out.println("uncle-grandparent-node-parent");
						n2 = node;
						n3 = parent;
					} else {
						if (SHOW_LOG)
							System.out.println("uucle-grandparent-parent-node");
						n3 = node;
						n2 = parent;
					}

					solveOverflow(n0, n1, n2, n3);
				} else {
					n3 = uncle;
					n2 = grandP;

					if (node == parent.getlChild()) {
						if (SHOW_LOG)
							System.out.println("node-parent-grandparent-uncle");
						n0 = node;
						n1 = parent;
					} else {
						if (SHOW_LOG)
							System.out.println("parent-node-grandparent-uncle");
						n0 = parent;
						n1 = node;
					}

					// 此时的结构就像已经做过上溢处理，中位数n2已经是最高节点。
				}

				n2.setColor(Color.RED);
				n1.setColor(Color.BLACK);
				n3.setColor(Color.BLACK);
				n0.setColor(Color.RED);
				solveDoubleRed(n2);
			}
		} else // 没有双红缺陷
			return;
	}

	/**
	 * 不论阶层，依左到右顺序带入欲修正节点。
	 * 
	 * @param n0
	 * @param n1
	 * @param n2
	 * @param n3
	 */
	private void solveOverflow(Node<E> n0, Node<E> n1, Node<E> n2, Node<E> n3) {
		if (SHOW_LOG)
			System.out.println(String.format("overflow, divided [%s, %s, %s, %s]", n0.getKey(), n1.getKey(),
					n2.getKey(), n3.getKey()));

		// 上溢节点依次为 n0, n1, n2, n3
		// 取中位数n2（无条件进位）为分界
		// n0~n1, n2, n3
		// 将 n2上升一层，并将刚才左右两组关键码分裂成n2的左右孩子。

		// 处理中位数节点及其父节点
		Node<E> parent = n1.getParent();
		if (parent != null) {
			if (parent.getlChild() == n1)
				parent.setlChild(n2);
			else
				parent.setrChild(n2);
		} else {
			root = n2;
		}
		n2.setParent(parent);

		// 处理中位数节点左孩子
		n1.setrChild(n2.getlChild());
		if (n1.getrChild() != null)
			n1.getrChild().setParent(n1);

		n2.setlChild(n1);
		n1.setParent(n2);

		// 处理中位数节点右孩子
		if (n3.getlChild() == n2) {
			n3.setlChild(n2.getrChild());
			if (n3.getlChild() != null)
				n3.getlChild().setParent(n3);
		}
		n2.setrChild(n3);
		n3.setParent(n2);
	}

	@Override
	public void solveDoubleBlack(Node<E> node) {

	}

	/**
	 * 从最后面到叶节点到自身的黑节点树，每条路径都会是同样长度。<br>
	 * 计算节点高度时，不考虑自身节点。
	 */
	@Override
	protected void updateAboveHeight(Node<E> node) {
		if (node.getParent() == null)
			return;
		int h1 = node.getHeight();
		int newH = (node.isBlack()) ? h1 + 1 : h1;
		node.getParent().setHeight(newH);
		updateAboveHeight(node.getParent());
	}

	@Override
	public void updateHeight() {

	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}
}
