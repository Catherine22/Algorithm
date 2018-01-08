package com.catherine.trees;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
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
	public int getBHeight() {
		return getBHeight(root);
	}

	/**
	 * 目标节点到任意叶子的黑节点高度。<br>
	 * 由于高度都一样，就采用任意路径。
	 * 
	 * @param node
	 * @return
	 */
	protected int getBHeight(Node<E> node) {
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
	 * 红黑树的搜寻花费O(log n)时间
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Node<E> search(int key) {
		return super.search(key);
	}

	/**
	 * 平衡红黑树节点的逻辑和AVL树一样，但加入重新着色的逻辑。颜色变化是根据叔叔节点判断 <br>
	 * 插入节点预设红色。 <br>
	 * 流程： <br>
	 * 1. 父节点改黑色，叔叔节点改黑色，祖父节点红色 <br>
	 * 2. 旋转 <br>
	 * 3. 将指针移到父节点，回到第一步重新开始，不断往上直到检查到根节点为止。
	 * 
	 * 插入旋转情形（一共两种）：<br>
	 * 1. 插入节点的父节点往上推，祖孙三代都是同方向，只需旋转祖父节点可达到平衡（包含祖父以上节点）<br>
	 * 2. A（祖父） - B（父，左子树） — C （右子树），插入节点位于C，此时须双旋，变成C（父） — A 和 B<br>
	 * Or A（祖父） - B（父，右子树） — C （左子树），插入节点位于C，此时须双旋，变成C（父） — A 和 B<br>
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public Node<E> insertAndBalance(int key, E data) {
		final Node<E> newNode = super.insert(key, data);
		Node<E> ancestor = newNode.getParent();

		Node<E> target = ancestor;
		Node<E> lastTarget = newNode;
		Node<E> child = null;
		Node<E> uncle = findUncle(newNode);

		int count = 1;
		while (ancestor != null) {
			if (SHOW_LOG)
				System.out.println(String.format("key: %d, round %d, ancestor:%d", key, count++, ancestor.getKey()));

			// 更新颜色
			if (ancestor.getParent() != null && uncle != null) {
				uncle.setColor(Color.BLACK);
				ancestor.setColor(Color.BLACK);
				ancestor.getParent().setColor(Color.RED);
			}

			if (isBalanced(ancestor)) {
				target = ancestor;
				child = lastTarget;
				ancestor = ancestor.getParent();
				uncle = findUncle(target);
				lastTarget = target;
			} else {
				// 没失衡的祖先直接返回（表示修改节点后仍保持平衡）
				if (ancestor == null || target == null || child == null)
					break;

				boolean isLeftChild = isLeftChild(target);
				boolean isLeftGrandchild = isLeftChild(child);

				if (SHOW_LOG) {
					String r2 = (isLeftChild) ? "L" : "R";
					String r3 = (isLeftGrandchild) ? "L" : "R";
					System.out.println(String.format("%s -> %s(%s) -> %s(%s)", ancestor.getKey(), target.getKey(), r2,
							child.getKey(), r3));
				}

				child = null;
				uncle = null;
				target = null;
				lastTarget = null;

				if (isLeftChild && isLeftGrandchild) {
					if (SHOW_LOG)
						System.out.println("符合情况1，三节点相连为一左斜线");
					// 符合情况1，三节点相连为一斜线
					zig(ancestor);
				} else if (!isLeftChild && !isLeftGrandchild) {
					if (SHOW_LOG)
						System.out.println("符合情况1，三节点相连为一右斜线");
					// 符合情况1，三节点相连为一斜线
					zag(ancestor);
				} else if (isLeftChild && !isLeftGrandchild) {
					if (SHOW_LOG)
						System.out.println("符合情况2，<形");
					// 符合情况2，"<"形
					left_rightRotate(ancestor);
				} else { // 也就是 else if (!isLeftChild && isLeftGrandchild)
					if (SHOW_LOG)
						System.out.println("符合情况2，>形");
					// 符合情况2，">"形
					right_leftRotate(ancestor);
				}
			}
		}

		root.setColor(Color.BLACK);
		return newNode;
	}

	/**
	 * 平衡红黑树节点的逻辑和AVL树一样，但加入重新着色的逻辑。<br>
	 * 1. 移除节点为红色，让移除节点的孩子取代移除节点，并改红色。<br>
	 * 双黑缺陷：<br>
	 * 
	 * 移除情形：<br>
	 * 1. 可能会失衡的节点，hot（{@link #remove(int)}
	 * 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为一直线同方向，并且和移除节点反向，
	 * 此时根据该祖先的子节点的高度不同旋转次数也会不同，复衡最多O(log n)次。 <br>
	 * 2. 可能会失衡的节点，hot（{@link #remove(int)} 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为>或<的方向，
	 * 此时做一次双旋让孙子节点成为新的父节点，复衡最多O(log n)次。 <br>
	 * 其它. 旋转后高度不变，就不会发生新的失衡。<br>
	 * <br>
	 * <br>
	 * p.s. 取子节点和孙子节点则是依照balance factory，如果左子树较高，balance factory > 1，那就取左边的节点，
	 * <br>
	 * 反之balance factory <
	 * -1，那就取右边的节点（为什么不是介于1和-1之间是因为如果是1、0或-1表示平衡，就不会被当成目标节点）。
	 * 
	 * @param key
	 */
	public void removeAndBalance(int key) {
	}

	/**
	 * 返回兄弟节点
	 * 
	 * @param node
	 * @return
	 */
	private Node<E> findSibling(Node<E> node) {
		if (node == null || node.getParent() == null)
			return null;
		if (isLeftChild(node))
			return node.getParent().getrChild();
		if (isRightChild(node))
			return node.getParent().getlChild();
		return null;
	}

	/**
	 * 返回叔叔节点
	 * 
	 * @param node
	 * @return
	 */
	private Node<E> findUncle(Node<E> node) {
		if (node == null || node.getParent() == null || node.getParent().getParent() == null)
			return null;
		if (isLeftChild(node.getParent()))
			return node.getParent().getParent().getrChild();
		if (isRightChild(node.getParent()))
			return node.getParent().getParent().getlChild();
		return null;
	}

	@Override
	public MyBTree<E> convertTo24Tree() {
		return null;
	}
}
