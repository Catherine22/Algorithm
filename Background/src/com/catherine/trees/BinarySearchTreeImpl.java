package com.catherine.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
import com.catherine.utils.Analysis;
import com.catherine.utils.Others;

/**
 * 
 * 若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值<br>
 * 若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值<br>
 * 所有节点的垂直投影就是该树的中序遍历<br>
 * 这边用key可以重复。
 * 
 * @author Catherine
 *
 */
public class BinarySearchTreeImpl<E extends Comparable<? super E>> extends MyBinaryTree<E>
		implements BinarySearchTree<E> {
	private final static boolean SHOW_LOG = false;

	/**
	 * 二叉搜寻中，有找到，hot则指向该节点的父节点；没找到指向最后访问的节点。<br>
	 * 加入哨兵的概念后，hot永远代表目标节点的父节点。
	 */
	protected Node<E> hot;
	// test
	private final int ANALYSIS_ID = 23104987;

	protected BinarySearchTreeImpl() {
		super();
	}

	public BinarySearchTreeImpl(E root) {
		super();
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.BST);
		setRoot(root);
	}

	/**
	 * 设置根节点
	 * 
	 * @param 数值
	 * @return 根节点
	 */
	public Node<E> setRoot(E data) {
		Node<E> n;
		if (root == null) {
			size++;
			n = adapter.buildNode(data, null, null, null, 0, 0);
		} else
			n = adapter.buildNode(data, null, root.getlChild(), root.getrChild(), root.getHeight(), root.getDepth());
		root = n;
		hot = root;
		return root;
	}

	@Override
	public Node<E> search(E data) {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		hot = root;
		boolean stop = false;
		Node<E> node = null;

		while (!stop) {
			if (SHOW_LOG)
				Analysis.count(ANALYSIS_ID);

			if (data.compareTo(hot.getData()) < 0) {
				if (hot.getlChild() == null)
					stop = true;
				else
					hot = hot.getlChild();
			} else if (data.compareTo(hot.getData()) > 0) {
				if (hot.getrChild() == null)
					stop = true;
				else
					hot = hot.getrChild();
			} else {
				node = hot;
				hot = node.getParent();
				stop = true;
			}
		}

		if (SHOW_LOG)
			Analysis.stopCounting(ANALYSIS_ID);

		return node;
	}

	@Override
	public Node<E> searchLast(E data) {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		hot = root;
		boolean stop = false;
		Node<E> node = null;

		while (!stop) {
			if (SHOW_LOG)
				Analysis.count(ANALYSIS_ID);

			if (data.compareTo(hot.getData()) < 0) {
				if (hot.getlChild() == null)
					stop = true;
				else
					hot = hot.getlChild();
			} else if (data.compareTo(hot.getData()) > 0) {
				if (hot.getrChild() == null)
					stop = true;
				else
					hot = hot.getrChild();
			} else {
				// 由于允许data重复，一旦出现两个相同的data，应返回最后一个
				if (hot.getrChild() == null || data.compareTo(hot.getrChild().getData()) != 0) {
					node = hot;
					hot = node.getParent();
					stop = true;
				} else
					hot = hot.getrChild();
			}
		}

		if (SHOW_LOG)
			Analysis.stopCounting(ANALYSIS_ID);

		return node;
	}

	@Override
	public synchronized Node<E> add(E data) {
		if (data == null)
			throw new NullPointerException("Data must not be null.");
		if (root == null)
			setRoot(data);

		searchLast(data);
		// 当插入节点的值与根节点相同时，此时hot为null
		Node<E> parent = (hot == null) ? root : hot;

		if (data.compareTo(parent.getData()) >= 0) {
			return insertRC(parent, data);
		} else {
			return insertLC(parent, data);
		}
	}

	@Override
	public void remove(E data) {
		remove(search(data));
	}

	protected void remove(Node<E> node) {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		if (node == null)
			throw new NullPointerException("Node must not be null.");
		hot = node.getParent();
		// 情况2
		if (node.getlChild() != null && node.getrChild() != null) {
			Node<E> succ = succ(node);
			hot = succ.getParent();
			if (SHOW_LOG) {
				System.out.println("node:" + node.getData());
				System.out.println("succ:" + succ.getData());
			}
			swap(node, succ);
			node = succ;
		}

		// Node<E> parent = node.getParent();
		// 情况1-移除节点为根节点
		if (hot == null) {
			root = node;
			root.setHeight(root.getHeight());
			updateAboveHeight(root);
			size--;
			return;
		}

		// 情况1-移除节点仅一孩子，且为左孩子
		else if (node.getlChild() != null && node.getrChild() == null) {
			killParent(hot, node.getlChild());
		}
		// 情况1-移除节点仅一孩子，且为右孩子
		else if (node.getlChild() == null && node.getrChild() != null) {
			killParent(hot, node.getrChild());
		}
		// 情况1-移除节点没孩子
		else {
			if (node != root && node == hot.getlChild())
				hot.setlChild(null);
			else if (node != root && node == hot.getrChild())
				hot.setrChild(null);
			node = null;
		}

		hot.setHeight(getHeight(hot));
		updateAboveHeight(hot);
		size--;
	}

	/**
	 * 内部方法{@link #remove(int)}专用<br>
	 * grandParent - parent - grandchild，移除parent<br>
	 * size和height在{@link #remove(int)}处理
	 * 
	 * @param grandParent
	 * @param grandchild
	 */
	private void killParent(Node<E> grandParent, Node<E> grandchild) {
		Node<E> parent = grandchild.getParent();
		if (grandParent.getrChild() == parent) {
			grandParent.setrChild(grandchild);
			grandchild.setParent(grandParent);
			parent = null;
		} else if (grandParent.getlChild() == parent) {
			grandParent.setlChild(grandchild);
			grandchild.setParent(grandParent);
			parent = null;
		}
		if (SHOW_LOG) {
			System.out.println("kp grandchild:" + grandchild.toString());
			System.out.println("kp grandparent:" + grandParent.toString());
		}
	}

	/**
	 * 内部方法{@link #remove(int)}专用<br>
	 * 交换两节点的值（仍是同样的引用）
	 * 
	 * @param node1
	 * @param node2
	 */
	protected void swap(Node<E> node1, Node<E> node2) {
		if (SHOW_LOG) {
			System.out.println("node1:" + node1.toString());
			System.out.println("node2:" + node2.toString());
		}
		E tmpData = node1.getData();
		node1.setData(node2.getData());
		node2.setData(tmpData);

		if (SHOW_LOG) {
			System.out.println("new node1:" + node1.toString());
			System.out.println("new node2:" + node2.toString());
		}
	}

	/**
	 * 插入子节点于左边，若已有节点则成为该节点的父节点
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertLC(Node<E> parent, E data) {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		Node<E> child;
		if (parent.getlChild() != null) {
			final Node<E> cNode = parent.getlChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(data, parent, null, null, 0, parent.getDepth() + 1);

		if (SHOW_LOG)
			System.out.println("insertLC:" + child.toString());
		size++;
		parent.setlChild(child);
		child.setHeight(getHeight(child));
		updateAboveHeight(child);
		return child;
	}

	/**
	 * 插入子节点于右边，若已有节点则成为该节点的父节点
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertRC(Node<E> parent, E data) {
		if (root == null)
			throw new NullPointerException("Root must not be null.");
		Node<E> child;
		if (parent.getrChild() != null) {
			final Node<E> cNode = parent.getrChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(data, parent, null, null, 0, parent.getDepth() + 1);

		if (SHOW_LOG)
			System.out.println("insertRC:" + child.toString());
		size++;
		parent.setrChild(child);
		child.setHeight(getHeight(child));
		updateAboveHeight(child);
		return child;
	}

	/**
	 * 不公平的随机生成二叉搜寻树<br>
	 * 取任意不重复的n个数产生的排列组合应为n!，生成的树平均高度为log n；<br>
	 * 但实际上这些树产生的树的组合只有卡塔兰数——catalan(n)个，生成的树平均高度为开根号n<br>
	 * 比如取123三个数，在213和231的组合时，产生的二叉搜寻树都是一样的。
	 */
	public static BinarySearchTree<Integer> random(int size) {
		BinarySearchTree<Integer> newBST = null;
		List<Integer> sequence = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sequence.add(i + 1);
		}
		// 产生乱数序列
		Collections.shuffle(sequence);
		System.out.print(sequence.get(0) + " ");
		newBST = new BinarySearchTreeImpl<Integer>(sequence.get(0));
		for (int i = 1; i < size; i++) {
			System.out.print(sequence.get(i) + " ");
			newBST.add(sequence.get(i));
		}
		System.out.print("\n");
		return newBST;
	}

	@Override
	public int countRandomTrees(int size) {
		Others others = new Others();
		return others.getCatalan1(size);
	}

	@Override
	public boolean isBBST() {
		return getHeight() <= Math.log10(size);
	}

	@Override
	public void zig(Node<E> node) {
		Node<E> p = node.getlChild();
		if (p == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		Node<E> gp = node.getParent();
		hot = node;

		Node<E> subtree = p.getrChild();

		if (gp != null) {
			if (isLeftChild(node))
				gp.setlChild(p);
			else
				gp.setrChild(p);
		} else
			root = p;
		p.setParent(gp);

		node.setlChild(subtree);
		if (subtree != null)
			subtree.setParent(node);

		p.setrChild(node);
		node.setParent(p);

		if (hot != null) {
			hot.setHeight(getHeight(hot));
			updateAboveHeight(hot);
		}
	}

	@Override
	public void zag(Node<E> node) {
		Node<E> p = node.getrChild();
		if (p == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		Node<E> gp = node.getParent();
		hot = node;

		Node<E> subtree = p.getlChild();

		if (gp != null) {
			if (isLeftChild(node))
				gp.setlChild(p);
			else
				gp.setrChild(p);
		} else
			root = p;
		p.setParent(gp);

		node.setrChild(subtree);
		if (subtree != null)
			subtree.setParent(node);

		p.setlChild(node);
		node.setParent(p);

		if (hot != null) {
			hot.setHeight(getHeight(hot));
			updateAboveHeight(hot);
		}
	}

	@Override
	public void left_rightRotate(Node<E> node) {
		Node<E> leftSubtree = node.getlChild();
		if (leftSubtree == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		Node<E> p = leftSubtree.getrChild();
		if (p == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));

		hot = p;
		Node<E> gp = node.getParent();

		if (gp != null) {
			if (isLeftChild(node))
				gp.setlChild(p);
			else
				gp.setrChild(p);
		} else
			root = p;
		p.setParent(gp);

		Node<E> leftSubtreeTmp = p.getrChild();
		node.setlChild(leftSubtreeTmp);
		if (leftSubtreeTmp != null)
			leftSubtreeTmp.setParent(node);

		Node<E> rightSubtreeTmp = p.getlChild();
		leftSubtree.setrChild(rightSubtreeTmp);
		if (rightSubtreeTmp != null)
			rightSubtreeTmp.setParent(leftSubtree);

		p.setrChild(node);
		node.setParent(p);

		p.setlChild(leftSubtree);
		leftSubtree.setParent(p);

		p.getlChild().setHeight(getHeight(p.getlChild()));
		p.getrChild().setHeight(getHeight(p.getrChild()));
		updateAboveHeight(p.getlChild());
	}

	@Override
	public void right_leftRotate(Node<E> node) {
		Node<E> rightSubtree = node.getrChild();
		if (rightSubtree == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		Node<E> p = rightSubtree.getlChild();
		if (p == null)
			throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));

		hot = p;
		Node<E> gp = node.getParent();

		if (gp != null) {
			if (isLeftChild(node))
				gp.setlChild(p);
			else
				gp.setrChild(p);
		} else
			root = p;
		p.setParent(gp);

		Node<E> rightSubtreeTmp = p.getlChild();
		node.setrChild(rightSubtreeTmp);
		if (rightSubtreeTmp != null)
			rightSubtreeTmp.setParent(node);

		Node<E> leftSubtreeTmp = p.getrChild();
		rightSubtree.setlChild(leftSubtreeTmp);
		if (leftSubtreeTmp != null)
			leftSubtreeTmp.setParent(rightSubtree);

		p.setlChild(node);
		node.setParent(p);

		p.setrChild(rightSubtree);
		rightSubtree.setParent(p);

		p.getlChild().setHeight(getHeight(p.getlChild()));
		p.getrChild().setHeight(getHeight(p.getrChild()));
		updateAboveHeight(p.getlChild());
	}

	/**
	 * 传入一节点，检查是否为父节点的左孩子
	 * 
	 * @param child
	 * @return
	 */
	protected boolean isLeftChild(Node<E> child) {
		if (child.getParent() == null)
			return false;
		return child.getParent().getlChild() == child;
	}

	/**
	 * 传入一节点，检查是否为父节点的右孩子
	 * 
	 * @param child
	 * @return
	 */
	protected boolean isRightChild(Node<E> child) {
		if (child.getParent() == null)
			return false;
		return child.getParent().getrChild() == child;
	}

	private class CheckAVLPointer {
		/**
		 * (AVL Tree)一旦一个节点平衡因子不符合，直接停止递归
		 */
		public boolean stopCheckingAVL;
		/**
		 * (AVL Tree)符合的节点数=全部节点数
		 */
		public int counter;
	}

	@Override
	public void isAVLTree(final Callback callback) {
		final CheckAVLPointer checkAVLPointer = new CheckAVLPointer();
		checkAVLPointer.counter = size;
		isAVLTree(root, checkAVLPointer, new Callback() {
			@Override
			public void onResponse(boolean result) {
				checkAVLPointer.counter--;
				if (!result) {
					checkAVLPointer.stopCheckingAVL = true;
					callback.onResponse(false);
				}

				if (checkAVLPointer.counter == 0 && !checkAVLPointer.stopCheckingAVL)
					callback.onResponse(true);

			}
		});
	}

	/**
	 * 递归<br>
	 * 从任一节点中序遍历（左-中-右）
	 */
	private void isAVLTree(Node<E> node, CheckAVLPointer checkAVLPointer, Callback callback) {
		if (!checkAVLPointer.stopCheckingAVL) {
			if (node.getlChild() != null)
				isAVLTree(node.getlChild(), checkAVLPointer, callback);
			if (Math.abs(getBalanceFactor(node)) > 1) {
				// System.out.println(node.getInfo());
				callback.onResponse(false);
			} else {
				// System.out.println(node.getInfo());
				callback.onResponse(true);
			}
			if (node.getrChild() != null)
				isAVLTree(node.getrChild(), checkAVLPointer, callback);
		}
	}

	/**
	 * 取得各节点的平衡因子（左子树高度-右子树高度） <br>
	 * 子树节点高度定义（同节点高度定义）：<br>
	 * 1. 子树为单一节点：0<br>
	 * 2. 无子树：-1<br>
	 * 3. 其他：取子树高度<br>
	 * 
	 * @return
	 */
	protected int getBalanceFactor(Node<E> node) {
		int lHeight = (node.getlChild() == null) ? -1 : node.getlChild().getHeight();
		int rHeight = (node.getrChild() == null) ? -1 : node.getrChild().getHeight();
		// System.out.println(String.format("L:%d, R:%s", lHeight,
		// node.getrChild().toString()));
		return lHeight - rHeight;
	}

	/**
	 * 当左子树-右子树的高度<=1时即为平衡节点
	 * 
	 * @param node
	 * @return
	 */
	protected boolean isBalanced(Node<E> node) {
		return Math.abs(getBalanceFactor(node)) <= 1;
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public final E setLC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("setLC(Node<E> parent, E data)");
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public final E setRC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("setRC(Node<E> parent, E data)");
	}
}
