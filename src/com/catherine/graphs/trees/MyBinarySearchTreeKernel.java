package com.catherine.graphs.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.catherine.graphs.trees.nodes.Node;
import com.catherine.graphs.trees.nodes.NodeAdapter;
import com.catherine.graphs.trees.nodes.Nodes;
import com.catherine.utils.Others;

/**
 * 
 * 若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值<br>
 * 若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值<br>
 * 所有节点的垂直投影就是该树的中序遍历<br>
 * 这边用key作为值比大小，value可以重复，key不行。
 * 
 * @author Catherine
 *
 */
class MyBinarySearchTreeKernel<E> extends MyBinaryTree<E> implements BinarySearchTree<E> {

	/**
	 * 二叉搜寻中，有找到，hot则指向该节点的父节点；没找到指向最后访问的节点。<br>
	 * 加入哨兵的概念后，hot永远代表目标节点的父节点。
	 */
	protected Node<E> hot;

	public MyBinarySearchTreeKernel(int key, E root) {
		super();
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.BST);
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

	@Override
	public Node<E> search(int key) {
		hot = root;
		boolean stop = false;
		Node<E> node = null;

		while (!stop) {
			if (key > hot.getKey()) {
				if (hot.getrChild() == null)
					stop = true;
				else
					hot = hot.getrChild();
			} else if (key < hot.getKey()) {
				if (hot.getlChild() == null)
					stop = true;
				else
					hot = hot.getlChild();
			} else {
				node = hot;
				hot = node.getParent();
				stop = true;
			}
		}
		return node;
	}

	@Override
	public Node<E> insert(int key, E data) {
		if (search(key) != null)
			throw new UnsupportedOperationException("This node has already been added.");

		final Node<E> parent = hot;
		if (key > parent.getKey())
			return insertRC(parent, key, data);
		else if (key < parent.getKey())
			return insertLC(parent, key, data);
		else
			return null;// 暂不考虑重复数值情况。
	}

	@Override
	public void remove(int key) {
		remove(search(key));
	}

	protected void remove(Node<E> node) {
		if (node == null)
			throw new NullPointerException("Node not found.");
		if (node.getParent() == null) {
			// 移除根节点
			size = 0;
			root = null;
			hot = null;
			return;
		}
		hot = node.getParent();
		// 情况2
		if (node.getlChild() != null && node.getrChild() != null) {
			Node<E> succ = succ(node);
			hot = succ.getParent();
			if (SHOW_LOG) {
				System.out.println("node:" + node.getKey());
				System.out.println("succ:" + succ.getKey());
			}
			swap(node, succ);
			node = succ;
		}

		Node<E> parent = node.getParent();
		// 情况1
		if (node.getlChild() != null && node.getrChild() == null) {
			killParent(parent, node.getlChild());
		}
		// 情况1
		else if (node.getlChild() == null && node.getrChild() != null) {
			killParent(parent, node.getrChild());
		}
		// 情况1
		else {
			if (node != root && node == parent.getlChild())
				parent.setlChild(null);
			else if (node != root && node == parent.getrChild())
				parent.setrChild(null);
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
	private void swap(Node<E> node1, Node<E> node2) {
		if (SHOW_LOG) {
			System.out.println("node1:" + node1.toString());
			System.out.println("node2:" + node2.toString());
		}
		int tmpKey = node1.getKey();
		int tmpHeight = node1.getHeight();
		int tmpDepth = node1.getDepth();
		E tmpData = node1.getData();

		node1.setKey(node2.getKey());
		node1.setHeight(node2.getHeight());
		node1.setDepth(node2.getDepth());
		node1.setData(node2.getData());
		node2.setKey(tmpKey);
		node2.setHeight(tmpHeight);
		node2.setDepth(tmpDepth);
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
	private Node<E> insertLC(Node<E> parent, int key, E data) {
		Node<E> child;
		if (parent.getlChild() != null) {
			final Node<E> cNode = parent.getlChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(key, data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(key, data, parent, null, null, 0, parent.getDepth() + 1);

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
	private Node<E> insertRC(Node<E> parent, int key, E data) {
		Node<E> child;
		if (parent.getrChild() != null) {
			final Node<E> cNode = parent.getrChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(key, data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(key, data, parent, null, null, 0, parent.getDepth() + 1);

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
	public static BinarySearchTree<Object> random(int size) {
		BinarySearchTree<Object> newBST = null;
		List<Integer> sequence = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sequence.add(i + 1);
		}
		// 产生乱数序列
		Collections.shuffle(sequence);
		System.out.print(sequence.get(0) + " ");
		newBST = new MyBinarySearchTreeKernel<Object>(sequence.get(0), null);
		for (int i = 1; i < size; i++) {
			System.out.print(sequence.get(i) + " ");
			newBST.insert(sequence.get(i), null);
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
	public void balance() {
	}

	@Override
	public void zig(Node<E> node) {
		if (node == root || node.getParent() == root) {
			if (root.getlChild() != null)
				node = root.getlChild();
			else
				throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		}
		Node<E> rc = node.getParent();
		hot = rc;
		if (rc.getParent() == null) {
			root = node;
			node.setParent(null);
		} else if (isLeftChild(rc)) {
			rc.getParent().setlChild(node);
			node.setParent(rc.getParent());
		} else {
			rc.getParent().setrChild(node);
			node.setParent(rc.getParent());
		}
		rc.setlChild(node.getrChild());
		if (rc.getlChild() != null)
			rc.getlChild().setParent(rc);
		node.setrChild(rc);
		rc.setParent(node);

		hot.setHeight(getHeight(hot));
		updateAboveHeight(hot);
	}

	@Override
	public void zag(Node<E> node) {
		if (node == root || node.getParent() == root) {
			if (root.getrChild() != null)
				node = root.getrChild();
			else
				throw new UnsupportedOperationException(String.format("This node(%s) cannot rotate", node.getInfo()));
		}
		Node<E> lc = node.getParent();
		hot = lc;
		if (lc.getParent() == null) {
			root = node;
			node.setParent(null);
		} else if (isLeftChild(lc)) {
			lc.getParent().setlChild(node);
			node.setParent(lc.getParent());
		} else {
			lc.getParent().setrChild(node);
			node.setParent(lc.getParent());
		}
		lc.setrChild(node.getlChild());
		if (lc.getrChild() != null)
			lc.getrChild().setParent(lc);
		node.setlChild(lc);
		lc.setParent(node);

		hot.setHeight(getHeight(hot));
		updateAboveHeight(hot);
	}

	/**
	 * 传入一节点，检查是否为父节点的左孩子
	 * 
	 * @param child
	 * @return
	 */
	protected boolean isLeftChild(Node<E> child) {
		return child.getParent().getlChild() == child;
	}

	/**
	 * 传入一节点，检查是否为父节点的右孩子
	 * 
	 * @param child
	 * @return
	 */
	protected boolean isRightChild(Node<E> child) {
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
		return lHeight - rHeight;
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public Node<E> setRoot(E data) {
		throw new UnsupportedOperationException("setRoot(E data)");
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public Node<E> insertLC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("insertLC(Node<E> parent, E data)");
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public Node<E> insertRC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("insertRC(Node<E> parent, E data)");
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public E setLC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("setLC(Node<E> parent, E data)");
	}

	/**
	 * Binary Tree only
	 */
	@Override
	public E setRC(Node<E> parent, E data) {
		throw new UnsupportedOperationException("setRC(Node<E> parent, E data)");
	}

}
