package com.catherine.graphs.trees;

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
	 * 
	 * @param ins
	 */
	public void insertAndBalance(int key, E data) {
		final Node<E> node = super.insert(key, data);
		if (node.getParent() != null || node.getParent().getParent() != null
				|| node.getParent().getParent().getParent() != null) {
			int count = 3;// 祖孙三代
			int left = 0;
			int right = 0;

			Node<E> target = node;
			while (count > 0) {
				count--;
				if (isLeftChild(target))
					left++;
				else
					right++;
				target = target.getParent();
			}

			// 情况1，单旋
			if (right == 3)
				zag(node.getParent().getParent().getParent());
			else if (left == 3)
				zig(node.getParent().getParent().getParent());

			// 情况2，双旋
			else if (right == 2 && left == 1) {
				target = node.getParent();
				zag(target);
				zig(target);
			} else if (right == 1 && left == 2) {
				target = node.getParent();
				zig(target);
				zag(target);
			}
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
		while (ancestor != null && Math.abs(getBalanceFactor(ancestor)) <= 1) {
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
	 * 3+4重构<br>
	 * 做完插入或移除操作后，失衡的AVL tree通过此方法恢复平衡<br>
	 * 找出第一个失衡的祖先节点hot及其祖孙一共三个节点，再取出此三个节点的子树一共四个，<br>
	 * 分别中序排序三个节点和四个子树后合并，成为平衡的AVL tree。
	 */
	public void balance() {

	}
}
