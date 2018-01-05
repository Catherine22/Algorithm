package com.catherine.trees;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
import com.catherine.trees.nodes.RedBlackBSTNode;
import com.catherine.trees.nodes.RedBlackBSTNode.Color;

/**
 * 四个规则：<br>
 * 1. 全树节点要不是红色就是黑色，每次操作结束后，根节点应为黑色。<br>
 * 2. 外部节点均为黑色<br>
 * 3. 任意红节点的孩子都是黑节点，不会出现父子连续的两个红节点。<br>
 * 4. 外部节点到任意节点，途中黑节点数目相等<br>
 * <br>
 * 红黑树是BST（二元搜索树），每个节点最多两个子节点<br>
 * 外部节点：又称哨兵节点(sentinel nodes)，必为null，为末端叶节点的节点，也就是假想节点，辅助用。 <br>
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
		adapter.setType(Nodes.RED_BLACK);
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
		root.setColor(Color.BLACK);
		hot = root;
		return root;
	}

	/**
	 * 只返回黑节点的高度。<br>
	 * 根到任意黑叶子的高度都一样。
	 * 
	 * @return
	 */
	@Override
	public int getHeight() {
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
		if (node == null)
			return -1;

		Node<E> lc = node.getlChild();
		Node<E> rc = node.getrChild();
		if (lc == null && rc == null)
			// 表示该节点为根节点
			return 0;

		if (lc != null)
			return (lc.isBlack()) ? lc.getHeight() + 1 : lc.getHeight();
		else
			return (rc.isBlack()) ? rc.getHeight() + 1 : rc.getHeight();
	}

	/**
	 * 红黑树的搜寻花费O(log n) 时间
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Node<E> search(int key) {
		return super.search(key);
	}

	@Override
	public Node<E> insert(int key, E data) {
		return null;
	}

	@Override
	public void remove(int key) {
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

	}

	/**
	 * 双黑缺陷<br>
	 * 就像在b-tree中发生下溢。<br>
	 * 情况1:移除节点node后，其parent节点连接黑孩子（node节点的后继和兄弟节点），后继节点只能有一个孩子，左右都行，兄弟节点必须是黑色，
	 * 兄弟节点的左孩子节点为红色，耗时O(1)。<br>
	 * 做一次右旋，parent变成后继节点的父节点，兄弟节点成为新parent，parent的左孩子为原本兄弟节点的左孩子（红），让新父节点成为红色，
	 * 两孩子为黑色。<br>
	 * <br>
	 * 情况2:parent为红色，两孩子（兄弟节点和后继节点）都是黑色，耗时O(1)。此前处理只需将parent转黑色、兄弟节点转红色。<br>
	 * <br>
	 * 情况3:同情况1，但是涉及的四个节点（parent, succ, sibling, child of
	 * sibling）都是黑色。解决：兄弟节点转红色，其它都黑色，双黑缺陷向上传播直到树根，必须递归判断，耗时O(n)。<br>
	 * <br>
	 * 情况4:当兄弟节点为红，其它节点为黑色时，做一次旋转，让兄弟节点变成新parent，原parent变成孩子，此时还是double
	 * black，再做一次判断，此时必不会有情况3和情况4，也就是情况4总共需要做两次double black处理，耗时O(1)。
	 * 
	 * @param parent
	 *            经过移除操作后的hot节点
	 * @param rightNodeRemoved
	 *            被移除的节点是否为hot节点的右孩子
	 */
	private void solveDoubleBlack(Node<E> parent, boolean rightNodeRemoved) {

	}

	/**
	 * 让{@link #solveDoubleBlack(Node)}或{@link #solveDoubleRed(Node)}来做，功能写在
	 * {@link #updateHeight(Node)}，提升效率。
	 */
	@Override
	protected void updateAboveHeight(Node<E> node) {
		// do nothing
	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}
}
