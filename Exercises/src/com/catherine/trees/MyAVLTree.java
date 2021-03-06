package com.catherine.trees;

import com.catherine.trees.nodes.Node;

/**
 * BST中每个节点的平衡因子都是1、0或-1则为AVL Tree
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyAVLTree<E extends Comparable<? super E>> extends BinarySearchTreeImpl<E> {
	protected final static boolean SHOW_LOG = false;

	public MyAVLTree(E root) {
		super(root);
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
	public Node<E> add(E data) {
		return super.add(data);
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
	public void insertAndBalance(E data) {
		final Node<E> newNode = super.add(data);
		Node<E> ancestor = newNode.getParent();

		Node<E> target = ancestor;
		Node<E> lastTarget = newNode;
		Node<E> child = null;

		int count = 1;
		while (ancestor != null) {
			if (SHOW_LOG)
				System.out.println(String.format("round %d, ancestor:%d", count++, ancestor.getData()));

			if (isBalanced(ancestor)) {
				target = ancestor;
				child = lastTarget;
				ancestor = ancestor.getParent();
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
					System.out.println(String.format("%s -> %s(%s) -> %s(%s)", ancestor.getData(), target.getData(), r2,
							child.getData(), r3));
				}

				child = null;
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
	}

	/**
	 * 只有一个父节点会失衡
	 */
	@Override
	public void remove(E data) {
		super.remove(data);
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
	 * <br>
	 * <br>
	 * p.s. 取子节点和孙子节点则是依照balance factory，如果左子树较高，balance factory > 1，那就取左边的节点，
	 * <br>
	 * 反之balance factory <
	 * -1，那就取右边的节点（为什么不是介于1和-1之间是因为如果是1、0或-1表示平衡，就不会被当成目标节点）。
	 * 
	 * @param key
	 */
	public void removeAndBalance(E data) {
		Node<E> tmp = search(data);
		super.remove(tmp);

		Node<E> ancestor = hot;
		Node<E> target = null;
		Node<E> child = null;

		int count = 1;
		while (ancestor != null) {
			if (SHOW_LOG)
				System.out.println(String.format("round %d, ancestor:%d", count++, ancestor.getData()));

			if (isBalanced(ancestor)) {
				ancestor = ancestor.getParent();
			} else {
				// 节点取高度较高的那边
				target = (getBalanceFactor(ancestor) < -1) ? ancestor.getrChild() : ancestor.getlChild();
				if (target != null) {
					// 子节点取高度较高的
					child = (getBalanceFactor(target) < -1) ? target.getrChild() : target.getlChild();
				}

				// 没失衡的祖先直接返回（表示移除节点后仍保持平衡）
				if (ancestor == null || target == null || child == null)
					break;

				boolean isLeftChild = isLeftChild(target);
				boolean isLeftGrandchild = isLeftChild(child);

				if (SHOW_LOG) {
					String r2 = (isLeftChild) ? "L" : "R";
					String r3 = (isLeftGrandchild) ? "L" : "R";
					System.out.println(String.format("%s -> %s(%s) -> %s(%s)", ancestor.getData(), target.getData(), r2,
							child.getData(), r3));
				}

				tmp = null;
				child = null;
				target = null;

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
	}

}
