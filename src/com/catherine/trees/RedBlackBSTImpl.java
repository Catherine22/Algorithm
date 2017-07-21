package com.catherine.trees;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
import com.catherine.trees.nodes.RedBlackBSTNode;
import com.catherine.trees.nodes.RedBlackBSTNode.Color;

/**
 * 四个规则：<br>
 * 1. 树根必为黑色，除了全树只有一个节点的情况外。<br>
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
		Node<E> lc = node.getlChild();
		Node<E> rc = node.getrChild();
		if (lc == null && rc == null)
			// 表示该节点为叶节点
			return 0;

		if (lc != null)
			return (lc.isBlack()) ? lc.getHeight() + 1 : lc.getHeight();
		else
			return (rc.isBlack()) ? rc.getHeight() + 1 : rc.getHeight();
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
			if (grandP == null) {
				root.setColor(Color.BLACK);
				node.setColor(Color.RED);
				return;
			}

			Node<E> uncle = (parent == grandP.getlChild()) ? grandP.getrChild() : grandP.getlChild();

			// 情况1——挑出三个节点来看，无视叔父节点。B-tree中间节点变黑色，两边红色。中间节点往上提，另外两节点变成左右孩子。
			if (uncle == null || uncle.isBlack()) {
				boolean isLeftNode = (node == parent.getlChild());
				boolean isLeftParent = (parent == grandP.getlChild());
				Node<E> ancestor = grandP.getParent();
				if (isLeftNode == isLeftParent) {
					// 中间节点边黑色，两旁红色
					grandP.setColor(RedBlackBSTNode.Color.RED);
					parent.setColor(RedBlackBSTNode.Color.BLACK);
					node.setColor(RedBlackBSTNode.Color.RED);

					// 新父节点
					parent.setParent(ancestor);
					if (ancestor != null) {
						if (grandP == ancestor.getlChild())
							ancestor.setlChild(parent);
						else
							ancestor.setrChild(parent);
					} else {
						root = parent;
					}

					if (isLeftParent) {
						if (SHOW_LOG)
							System.out.println(String.format("n=%s, 双红缺陷1，三者一左下斜线", node.getKey()));

						// 左孩子不变
						// 新右孩子
						grandP.setlChild(parent.getrChild());
						if (grandP.getlChild() != null)
							grandP.getlChild().setParent(grandP);
						parent.setrChild(grandP);
						grandP.setParent(parent);
					} else {
						if (SHOW_LOG)
							System.out.println(String.format("n=%s, 双红缺陷1，三者一右下斜线", node.getKey()));

						// 右孩子不变
						// 新左孩子
						grandP.setrChild(parent.getlChild());
						if (grandP.getrChild() != null) {
							grandP.getrChild().setParent(parent);
						}
						parent.setlChild(grandP);
						grandP.setParent(parent);
					}
				} else {
					grandP.setColor(RedBlackBSTNode.Color.RED);
					node.setColor(RedBlackBSTNode.Color.BLACK);
					parent.setColor(RedBlackBSTNode.Color.RED);

					// 新父节点
					node.setParent(ancestor);
					if (ancestor != null) {
						if (grandP == ancestor.getlChild())
							ancestor.setlChild(node);
						else
							ancestor.setrChild(node);
					} else {
						root = node;
					}

					if (isLeftParent) {
						if (SHOW_LOG)
							System.out.println(String.format("n=%s, 双红缺陷1，三者<", node.getKey()));

						// 新右节点
						parent.setrChild(node.getlChild());
						if (parent.getrChild() != null) {
							parent.getrChild().setParent(parent);
						}
						node.setlChild(parent);
						parent.setParent(node);

						// 新左节点
						grandP.setlChild(node.getrChild());
						if (grandP.getlChild() != null) {
							grandP.getlChild().setParent(grandP);
						}
						node.setrChild(grandP);
						grandP.setParent(node);
					} else {
						if (SHOW_LOG)
							System.out.println(String.format("n=%s, 双红缺陷1，三者>", node.getKey()));

						// 新左节点
						grandP.setrChild(node.getlChild());
						if (grandP.getrChild() != null) {
							grandP.getrChild().setParent(grandP);
						}
						node.setlChild(grandP);
						grandP.setParent(node);

						// 新右节点
						parent.setlChild(node.getrChild());
						if (parent.getlChild() != null) {
							parent.getlChild().setParent(parent);
						}
						node.setrChild(parent);
						parent.setParent(node);
					}
				}

				// 更新高度
				node.setHeight(getHeight(node));
				parent.setHeight(getHeight(parent));
				grandP.setHeight(getHeight(grandP));
				hot = grandP;
				updateHeight(hot);
				return;
			}
			// 情况2——四节点合起来看就是B-tree的上溢，最上面的节点为红色，两旁黑色，最下面红色。
			else {
				if (SHOW_LOG) {
					boolean isLeftNode = (node == parent.getlChild());
					boolean isLeftParent = (parent == grandP.getlChild());
					if (isLeftNode == isLeftParent) {

						if (isLeftParent)
							System.out.println(String.format("n=%s, 双红缺陷2，叔父在右边，祖孙三代一左下斜线", node.getKey()));
						else
							System.out.println(String.format("n=%s, 双红缺陷2，叔父在左边，祖孙三代一右下斜线", node.getKey()));

					} else {

						if (isLeftParent)
							System.out.println(String.format("n=%s, 双红缺陷2，叔父在右边，祖孙三代<形", node.getKey()));
						else
							System.out.println(String.format("n=%s, 双红缺陷2，叔父在左边，祖孙三代>形", node.getKey()));

					}
				}
				grandP.setColor(Color.RED);
				parent.setColor(Color.BLACK);
				uncle.setColor(Color.BLACK);
				node.setColor(Color.RED);

				// 更新高度
				node.setHeight(getHeight(node));
				parent.setHeight(getHeight(parent));
				grandP.setHeight(getHeight(grandP));
				uncle.setHeight(getHeight(uncle));
				hot = grandP;
				updateHeight(hot);
				solveDoubleRed(grandP);
			}

			if (root == grandP) // 情况2换完grandP都是红色
				root.setColor(Color.BLACK);
		} else // 没有双红缺陷
			return;
	}

	@Override
	public void solveDoubleBlack(Node<E> node) {

	}

	/**
	 * 做完{@link #solveDoubleBlack(Node)}或{@link #solveDoubleRed(Node)}再来做，功能写在
	 * {@link #updateHeight(Node)}
	 */
	@Override
	protected void updateAboveHeight(Node<E> node) {
		// do nothing
	}

	@Override
	public void updateHeight(Node<E> node) {
		if (node.getParent() == null)
			return;
		int h1 = node.getHeight();
		int newH = (node.isBlack()) ? h1 + 1 : h1;
		node.getParent().setHeight(newH);
		updateAboveHeight(node.getParent());
	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}
}
