package com.catherine.graphs.trees;

import java.util.LinkedList;
import java.util.List;

import com.catherine.graphs.trees.nodes.Node;

/**
 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyAVLTree<E> extends MyBinarySearchTreeKernel<E> {
	protected final static boolean SHOW_LOG = true;

	public MyAVLTree(int key, E root) {
		super(key, root);
	}

	/**
	 * AVL Tree一定是适度平衡
	 */
	@Override
	public boolean isBBST() {
		return true;
	}

	/**
	 * 所有祖先节点都會失衡<br>
	 */
	@Override
	public Node<E> insert(int key, E data) {
		return super.insert(key, data);
	}

	/**
	 * 插入或移除节点造成AVL Tree失衡<br>
	 * 插入情形（一共两种）：<br>
	 * 1. 插入节点的父节点往上推，祖孙三代都是同方向，只需旋转祖父节点可达到平衡（包含祖父以上节点）<br>
	 * 2. A（祖父） - B（父，左子树） — C （右子树），插入节点位于C，此时须双旋，变成C（父） — A 和 B<br>
	 * Or A（祖父） - B（父，右子树） — C （左子树），插入节点位于C，此时须双旋，变成C（父） — A 和 B<br>
	 * 
	 * @param ins
	 */
	@SuppressWarnings("unused")
	public void insertAndBalance(int key, E data) {
		final Node<E> newNode = insert(key, data);
		Node<E> ancestor = newNode.getParent();

		Node<E> child = ancestor;
		Node<E> tmp = newNode;
		Node<E> grandchild = null;

		// 找出第一个失衡的祖先
		while (ancestor != null) {
			if (isBalanced(ancestor)) {
				child = ancestor;
				grandchild = tmp;
				ancestor = ancestor.getParent();
				tmp = child;
			} else
				break;
		}

		if (ancestor == null || child == null || grandchild == null)
			return;

		boolean isLeftChild = isLeftChild(child);
		boolean isLeftGrandchild = isLeftChild(grandchild);

		if (SHOW_LOG) {
			String r2 = (isLeftChild) ? "L" : "R";
			String r3 = (isLeftGrandchild) ? "L" : "R";
			System.out.println(String.format("%s -> %s(%s) -> %s(%s)", ancestor.getKey(), child.getKey(), r2,
					grandchild.getKey(), r3));
		}

		tmp = null;
		child = null;
		grandchild = null;

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

	/**
	 * 只有一个父节点会失衡
	 */
	@Override
	public void remove(int key) {
		super.remove(key);
	}

	/**
	 * 
	 * 插入或移除节点造成AVL Tree失衡<br>
	 * 移除情形：<br>
	 * 1. 可能会失衡的节点，hot（{@link #remove(int)}
	 * 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为一直线同方向，并且和移除节点反向，
	 * 此时根据该祖先的子节点的高度不同旋转次数也会不同，复衡最多O(log n)次。 <br>
	 * 2. 可能会失衡的节点，hot（{@link #remove(int)} 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为>或<的方向，
	 * 此时做一次双旋让孙子节点成为新的父节点，复衡最多O(log n)次。 <br>
	 * 其它. 旋转后高度不变，就不会发生新的失衡。<br>
	 * 
	 * @param key
	 */
	public void removeAndBalance(int key) {
		Node<E> tmp = search(key);
		super.remove(tmp);

		Node<E> ancestor = hot;
		Node<E> child = null;
		Node<E> grandchild = null;

		while (ancestor != null) {
			if (isBalanced(ancestor)) {
				ancestor = ancestor.getParent();
			} else {
				if (isLeftChild(tmp)) {
					child = ancestor.getrChild();
					if (child != null) {
						// 孙子节点取高度较高的，情况2取得的孙子节点的高度一定高于兄弟
						if (getBalanceFactor(child) < -1) {
							grandchild = child.getlChild();
						} else
							grandchild = child.getrChild();
					}
				} else {
					child = ancestor.getlChild();
					if (child != null) {
						// 孙子节点取高度较高的，情况2取得的孙子节点的高度一定高于兄弟
						if (getBalanceFactor(child) < -1) {
							grandchild = child.getrChild();
						} else
							grandchild = child.getlChild();
					}
				}
				break;
			}
		}

		// 没失衡的祖先直接返回
		if (ancestor == null || child == null || grandchild == null)
			return;

		boolean isLeftChild = isLeftChild(child);
		boolean isLeftGrandchild = isLeftChild(grandchild);

		if (SHOW_LOG) {
			String r2 = (isLeftChild) ? "L" : "R";
			String r3 = (isLeftGrandchild) ? "L" : "R";
			System.out.println(String.format("%s -> %s(%s) -> %s(%s)", ancestor.getKey(), child.getKey(), r2,
					grandchild.getKey(), r3));
		}

		tmp = null;
		child = null;
		grandchild = null;
		
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

	/**
	 * 移除并利用3+4重构达成平衡
	 * 
	 * @param key
	 */
	public void removeAndConnect34(int key) {
		Node<E> delNode = search(key);
		super.remove(delNode);
		if (hot == null) {// 表示移除根节点并且成为空树了
			System.out.println("变成空树");
			return;
		}
		if (SHOW_LOG)
			System.out.println("hot:" + hot.getInfo());

		connect34(hot);
	}

	/**
	 * 
	 * 3+4重构<br>
	 * 做完插入或移除操作后，失衡的AVL tree通过此方法恢复平衡<br>
	 * 找出第一个失衡的祖先节点hot及其父親、祖父一共三个节点，再取出此三个节点的子树一共四个，<br>
	 * 分别中序排序三个节点和四个子树后合并，成为平衡的AVL tree。
	 * 
	 * @param node
	 */
	public void connect34(Node<E> node) {
		if (isBalanced(node))
			return;

		Node<E> p = node.getParent();
		if (p == null)
			return;
		Node<E> g = p.getParent();
		if (g == null)
			return;
		final Node<E> ancestor = g.getParent();
		List<Node<E>> subtrees = new LinkedList<>();

		subtrees.add(node.getlChild());
		subtrees.add(node.getrChild());
		Node<E> tmp = p;
		Node<E> mark = node;
		while (tmp != ancestor) {
			if (p.getlChild() != mark)
				subtrees.add(p.getlChild());
			else
				subtrees.add(p.getrChild());

			mark = tmp;
			tmp = tmp.getParent();
		}
		if (SHOW_LOG) {
			System.out.print("{");
			for (Node<E> n : subtrees) {
				System.out.print(n.getInfo());
			}
			System.out.println("{");
		}
	}
}
