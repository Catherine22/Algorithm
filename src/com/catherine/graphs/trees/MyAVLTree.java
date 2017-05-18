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
	 * 插入情形：<br>
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
		boolean isLeftNode = isLeftChild(ancestor);
		boolean isLeftChild = isLeftChild(child);
		boolean isLeftGrandchild = isLeftChild(grandchild);

		if (SHOW_LOG) {
			String r1 = (isLeftNode) ? "L" : "R";
			String r2 = (isLeftChild) ? "L" : "R";
			String r3 = (isLeftGrandchild) ? "L" : "R";
			System.out.println(String.format("%s(%s) -> %s(%s) -> %s(%s)", ancestor.getKey(), r1, child.getKey(), r2,
					grandchild.getKey(), r3));
		}

		tmp = null;
		child = null;
		grandchild = null;

		// 没失衡的祖先直接返回
		if (ancestor == null) {
			if (SHOW_LOG)
				System.out.println("没失衡的祖先直接返回");
			return;
		}
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
		} else if (!isLeftChild && isLeftGrandchild) {
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
	 * 1. 可能会失衡的节点，hot（{@link #remove(int)} 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为一直线同方向，
	 * 此时根据该祖先的子节点的高度不同旋转次数也会不同，复衡最多O(log n)次。 <br>
	 * 2. 可能会失衡的节点，hot（{@link #remove(int)} 操作后重新指定）或其祖先，该祖先的子节点以及孙子节点为>或<的方向，
	 * 此时做一次双旋让孙子节点成为新的父节点，复衡最多O(log n)次。 <br>
	 * 其它. 旋转后高度不变，就不会发生新的失衡。<br>
	 * 
	 * @param key
	 */
	public void removeAndBalance(int key) {
		Node<E> delNode = search(key);
		boolean skip = false;// 假如已经处理过后面可省略不再检查
		boolean isDelNodeLeftChild = isLeftChild(delNode);// 不用考虑移除的是根节点的情况，因为移除后会直接返回，该变数也没用了

		super.remove(delNode);
		if (hot == null) {// 表示移除根节点并且成为空树了
			System.out.println("变成空树");
			return;
		}
		if (SHOW_LOG)
			System.out.println("hot:" + hot.getInfo());

		// 找到可能会失衡的祖先
		Node<E> ancestor = hot;// 祖先从hot开始，hot的定义详见super.remove()
		while (ancestor != null && isBalanced(ancestor)) {
			ancestor = ancestor.getParent();
		}
		// 移除节点后仍平衡直接结束
		if (ancestor == null)
			return;

		if (SHOW_LOG)
			System.out.println("lean, ancestor:" + ancestor.getInfo());

		Node<E> tmp = ancestor;

		// 情况1
		if (!skip) {
			int count = 2;// 祖孙三代
			int right = 0;
			int left = 0;

			if (isRightChild(tmp)) {// 本身是右子树，只需要检查自己的右孩子及右孙子
				while (count > 0) {
					count--;
					if (tmp.getrChild() != null)
						right++;
					else
						count = -1;
					tmp = tmp.getrChild();
				}
				if (SHOW_LOG)
					System.out.println(String.format("left:%d, right:%d", left, right));

				// 情况1，左单旋该祖先的子节点
				if (right == 2) {
					Node<E> target = ancestor.getrChild();
					zag(target);
					int bf = 0;
					// 检查新祖先的高度变化，检查到根节点为止
					while (target != null) {
						bf = getBalanceFactor(target);
						if (SHOW_LOG) {
							System.out.println("target:" + target.getInfo());
							System.out.println(String.format("bf:%d", bf));
						}
						// 失衡
						if (Math.abs(bf) > 1)
							zig(target);
						target = target.getParent();
					}
					skip = true;
				}
			}
			if (isLeftChild(tmp)) {// 本身是左子树，只需要检查自己的左孩子及左孙子
				while (count > 0) {
					count--;
					if (tmp.getlChild() != null)
						left++;
					else
						count = -1;
					tmp = tmp.getlChild();
				}
				if (SHOW_LOG)
					System.out.println(String.format("left:%d, right:%d", left, right));

				// 情况1，右单旋该祖先的子节点
				if (left == 2) {
					Node<E> target = ancestor.getlChild();
					zig(target);
					int bf = 0;
					// 检查新祖先的高度变化，检查到根节点为止
					while (target != null) {
						bf = getBalanceFactor(target);
						if (SHOW_LOG) {
							System.out.println("target:" + target.getInfo());
							System.out.println(String.format("bf:%d", bf));
						}
						traverseLevel();
						// 失衡
						if (Math.abs(bf) > 1)
							zag(target);
						target = target.getParent();
					}
					skip = true;
				}
			}
		}

		if (!skip) {
			if (SHOW_LOG)
				System.out.print("情况2, ");
			if (isRightChild(tmp)) {// 本身是右子树，只需要检查自己的左孩子及右孙子
				if (SHOW_LOG)
					System.out.println("hot本身是右子树");
				if (tmp.getlChild() != null && tmp.getlChild().getrChild() != null) {
					if (SHOW_LOG)
						System.out.println(">形");

					// 情况2，zig()zag()
					Node<E> newLChild = ancestor;
					Node<E> newParent = ancestor.getrChild().getlChild();
					zig(newParent);
					zag(newLChild);
					int bf = 0;
					// 检查新祖先的高度变化，检查到根节点为止
					while (newParent != null) {
						bf = getBalanceFactor(newParent);
						if (SHOW_LOG) {
							System.out.println("target:" + newParent.getInfo());
							System.out.println(String.format("bf:%d", bf));
						}
						// 失衡
						if (Math.abs(bf) > 1) {
							System.out.println("失衡");
						}
						newParent = newParent.getParent();
					}
					skip = true;

				}
			} else if (isLeftChild(tmp)) {// 本身是左子树，只需要检查自己的右孩子及左孙子
				if (SHOW_LOG)
					System.out.println("hot本身是左子树");
				if (tmp.getrChild() != null && tmp.getrChild().getlChild() != null) {
					if (SHOW_LOG)
						System.out.println("<形");

					// 情况2，zag()zig()
					Node<E> newRChild = ancestor;
					Node<E> newParent = ancestor.getlChild().getrChild();
					zag(newParent);
					zig(newRChild);
					int bf = 0;
					// 检查新祖先的高度变化，检查到根节点为止
					while (newParent != null) {
						bf = getBalanceFactor(newParent);
						if (SHOW_LOG) {
							System.out.println("target:" + newParent.getInfo());
							System.out.println(String.format("bf:%d", bf));
						}
						// 失衡
						if (Math.abs(bf) > 1) {
							System.out.println("失衡");
						}
						newParent = newParent.getParent();
					}
					skip = true;
				}
			} else {// 本身是根节点
				if (SHOW_LOG)
					System.out.print("hot是根节点, ");
				if (isDelNodeLeftChild) {
					if (SHOW_LOG)
						System.out.println(">形");

					// 情况2，zig()zag()
					Node<E> newLChild = ancestor;
					Node<E> newParent = ancestor.getrChild().getlChild();
					zig(newParent);
					zag(newLChild);
				} else {
					if (SHOW_LOG)
						System.out.println("<形");

					// 情况2，zag()zig()
					Node<E> newRChild = ancestor;
					Node<E> newParent = ancestor.getlChild().getrChild();
					zag(newParent);
					zig(newRChild);
				}
				skip = true;
			}
		}

		// 其它
		if (!skip) {
			int bf = getBalanceFactor(ancestor);
			if (bf > 1)
				zig(ancestor);
			else if (bf < -1)
				zag(ancestor);
		}

		// release
		tmp = null;
		delNode = null;
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
