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
		Node<E> node = search(key);
		if (node == null)
			return;
		if (node.getParent() == null) {
			remove(node);
			return;
		}

		boolean rightNodeRemoved = (node.getParent().getrChild() == node);
		remove(node);
		solveDoubleBlack(hot, rightNodeRemoved);
	}

	/**
	 * 一定要从B-tree的角度来看方便理解。<br>
	 * 目标节点为红节点，目标节点的父节点也是红色，称双红缺陷(double-red)。<br>
	 * 考察目标节点的祖父节点和父节点的兄弟（叔父节点）。<br>
	 * <br>
	 * 目标节点-父节点-祖父节点-叔父节点四点上升到同一排，想象成四个key，为(2,4)树的一个节点。<br>
	 * 有四种组合：<br>
	 * 目标-父-祖父-叔父<br>
	 * 父-目标-祖父-叔父<br>
	 * 叔父-祖父-目标-父<br>
	 * 叔父-祖父-父-目标<br>
	 * <br>
	 * 情况1：黑叔父节点。中间节点往上搬，其它放两侧，中间节点黑色，其它红色。不必改变拓扑结构，不必检查原祖父的祖先，耗时O(1)。<br>
	 * 1-1 目标-父-祖父成一直线，父节点为中间节点。<br>
	 * 1-2 目标-父-祖父成"<"或">"型，目标节点为中间节点。<br>
	 * <br>
	 * 情况2：红叔父节点，要检查原祖父的祖先，至多耗时O(n)。<br>
	 * 四节点合起来看就是B-tree的上溢，最上面的节点为红色，两旁黑色，最下层红色。 <br>
	 * 因为最上面的节点为红色，做完后情况2节点可能会再度发生双红缺陷，情况2须检查祖先。
	 * 
	 * @param node目标节点
	 */
	private void solveDoubleRed(Node<E> node) {
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

	/**
	 * 双黑缺陷<br>
	 * 就像在b-tree中发生下溢。<br>
	 * 情况1:移除节点node后，其父节点连接黑孩子（node节点的后继和兄弟节点），后继节点只能有一个孩子，左右都行，兄弟节点必须是黑色，
	 * 兄弟节点的左孩子节点为红色，耗时O(1)。<br>
	 * 做一次右旋，parent变成后继节点的父节点，兄弟节点成为新parent，parent的左孩子为原本兄弟节点的左孩子（红），让新父节点成为红色，
	 * 两孩子为黑色。<br>
	 * <br>
	 * 情况2:parent为红色，两孩子（兄弟节点和后继节点）都是黑色，耗时O(1).此前处理只需将parent转黑色、兄弟节点转红色。<br>
	 * <br>
	 * 情况3:
	 * 
	 * @param parent
	 *            经过移除操作后的hot节点
	 * @param rightNodeRemoved
	 *            被移除的节点是否为hot节点的右孩子
	 */
	private void solveDoubleBlack(Node<E> parent, boolean rightNodeRemoved) {
		if (parent == null)
			return;
		System.out.println("solveDoubleBlack parent:" + parent.getKey());

		if (rightNodeRemoved) {
			Node<E> lc = parent.getlChild();
			if (lc == null) {
				// 表示原树只有root和root的右孩子两节点。
				root.setColor(Color.RED);
				return;
			}
			Node<E> succ = parent.getrChild();
			if (succ == null) {
				System.out.println("没有后继节点");
				// test
				return;
			}
			// Node<E> redNode;
			Node<E> childOfSucc = succ.getlChild();
			if (childOfSucc == null) {
				childOfSucc = succ.getrChild();
			} else {
				if (succ.getrChild() != null) {
					// 非情况1
					System.out.println("后继节点有两个孩子");
					// test
					return;
				}
			}
			if (parent.isRed() && lc.isBlack() && succ.isBlack()) {
				// 情况2
				parent.setColor(Color.BLACK);
				lc.setColor(Color.RED);
				succ.setColor(Color.BLACK);
				return;
			}
			if (lc.getlChild() != null && lc.getlChild().isRed()) {
				// 情况1
				System.out.println(String.format("parent=%s, 双黑缺陷1，左孩子的左孩子为红节点。", parent.getKey()));
				// redNode = lc.getlChild();

				// 旋转
				Node<E> grandp = parent.getParent();
				lc.setParent(grandp);
				if (grandp != null) {
					if (parent == grandp.getlChild())
						grandp.setlChild(lc);
					else
						grandp.setrChild(lc);
				} else
					root = lc;

				parent.setrChild(childOfSucc);
				if (parent.getrChild() != null)
					parent.getrChild().setParent(parent);
				parent.setlChild(lc.getrChild());
				if (parent.getlChild() != null) {
					parent.getlChild().setParent(parent);
				}

				lc.setrChild(parent);
				parent.setParent(lc);

				lc.setColor(Color.RED);
				parent.setColor(Color.BLACK);
				childOfSucc.setColor(Color.BLACK);
				return;
			}

		} else {
			Node<E> rc = parent.getlChild();
			if (rc == null) {
				// 表示原树只有root和root的左孩子两节点。
				root.setColor(Color.RED);
				return;
			}

			Node<E> succ = parent.getrChild();
			if (succ == null) {
				System.out.println("没有后继节点");
				// test
				return;
			}
			// Node<E> redNode;
			Node<E> childOfSucc = succ.getlChild();
			if (childOfSucc == null) {
				childOfSucc = succ.getrChild();
			} else {
				if (succ.getrChild() != null) {
					// 非情况1
					System.out.println("后继节点有两个孩子");
					// test
					return;
				}
			}
			if (parent.isRed() && rc.isBlack() && succ.isBlack()) {
				// 情况2
				parent.setColor(Color.BLACK);
				rc.setColor(Color.RED);
				succ.setColor(Color.BLACK);
				return;
			}
			if (rc.getrChild() != null && rc.getrChild().isRed()) {
				// 情况1
				System.out.println(String.format("parent=%s, 双黑缺陷1，右孩子的右孩子为红节点。", parent.getKey()));
				// redNode = rc.getlChild();

				// 旋转
				Node<E> grandp = parent.getParent();
				rc.setParent(grandp);
				if (grandp != null) {
					if (parent == grandp.getlChild())
						grandp.setlChild(rc);
					else
						grandp.setrChild(rc);
				} else
					root = rc;

				parent.setlChild(childOfSucc);
				if (parent.getlChild() != null)
					parent.getlChild().setParent(parent);
				parent.setrChild(rc.getrChild());
				if (parent.getrChild() != null) {
					parent.getrChild().setParent(parent);
				}

				rc.setlChild(parent);
				parent.setParent(rc);

				rc.setColor(Color.RED);
				parent.setColor(Color.BLACK);
				childOfSucc.setColor(Color.BLACK);
				return;
			}
		}

	}

	/**
	 * 让{@link #solveDoubleBlack(Node)}或{@link #solveDoubleRed(Node)}来做，功能写在
	 * {@link #updateHeight(Node)}，提升效率。
	 */
	@Override
	protected void updateAboveHeight(Node<E> node) {
		// do nothing
	}

	/**
	 * 红黑树的高度是指黑节点的高度。 <br>
	 * 从最后面到叶节点到自身的黑节点树，每条路径都会是同样长度。<br>
	 * 计算节点高度时，不考虑自身节点。
	 */
	private void updateHeight(Node<E> node) {
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
