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
public class MyBinarySearchTree<E> extends MyBinaryTree<E> {

	/**
	 * 二叉搜寻中，有找到，hot则指向该节点的父节点；没找到指向最后访问的节点。<br>
	 * 加入哨兵的概念后，hot永远代表目标节点的父节点。
	 */
	Node<E> hot;

	public MyBinarySearchTree(int key, E root) {
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

	/**
	 * 由于所有节点的垂直投影就是该树的中序遍历，搜寻时用{@link com.catherine.getData()_type.Search}
	 * 的binSearch概念。<br>
	 * 无论成功与否，都是指向命中节点。（没找到时，在{@link #hot}建立一个子节点作为哨兵，整棵树仍然是BST<br>
	 * <br>
	 * 每比较一次，节点的深度都会下降一层，也就是递归深度不超过树高。<br>
	 * 
	 * @param key
	 *            搜寻节点的key
	 * @return 命中节点或<code>null<code>
	 */
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

	/**
	 * 先做一次遍历，得到hot，用hot作为父节点插入。 暂不考虑重复数值情况。
	 * 
	 * @param key
	 *            插入节点的key
	 * @param data
	 *            插入节点的value
	 */
	public void insert(int key, E data) {
		if (search(key) != null)
			throw new IllegalArgumentException("This node has already been added.");

		final Node<E> parent = hot;
		if (key > parent.getKey())
			insertRC(parent, key, data);
		else if (key < parent.getKey())
			insertLC(parent, key, data);
	}

	/**
	 * 情况1:欲移除节点只有一个左孩子或右孩子，移除节点后孩子上位，取代原节点。<br>
	 * 情况2:欲移除节点有左右孩子。化繁为简，变成情况1再处理<br>
	 * 情况2先找出目标节点的后继节点{@link #succ(Node)}，两节点交换后就变成情况1（顶多只会有右孩子，因为后继已经是最左边的节点了），
	 * 比照情况1处理，这边还要再将欲移除节点的父节点设为hot，重新整理树高。
	 * 
	 * @param key
	 */
	public void remove(int key) {
		Node<E> node = search(key);
		if (node == null)
			throw new NullPointerException("Node not found.");

		// 情况2
		if (node.getlChild() != null && node.getrChild() != null) {
			Node<E> succ = succ(node);
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
			parent.setHeight(parent.getlChild().getHeight() + 1);
		}
		// 情况1
		else if (node.getlChild() == null && node.getrChild() != null) {
			killParent(parent, node.getrChild());
			parent.setHeight(parent.getrChild().getHeight() + 1);
		}
		// 情况1
		else {
			if (node != root && node == parent.getlChild())
				parent.setlChild(null);
			else if (node != root && node == parent.getrChild())
				parent.setrChild(null);
			parent.setHeight(0);
			node = null;
		}
		updateAboveHeight(parent);

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
		updateAboveHeight(grandParent);
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
		updateAboveHeight(child);
		return child;
	}

	/**
	 * 随机生成，取任意不重复的n个数产生的排列组合应为n!，生成的树平均高度为log n；<br>
	 * 但实际上这些树产生的树的组合只有卡塔兰数——catalan(n)个，生成的树平均高度为开根号n<br>
	 * 比如取123三个数，在213和231的组合时，产生的二叉搜寻树都是一样的。
	 */
	public static MyBinarySearchTree<Object> random(int size) {
		MyBinarySearchTree<Object> newBST;
		List<Integer> sequence = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sequence.add(i + 1);
		}
		// 产生乱数序列
		Collections.shuffle(sequence);
		System.out.print(sequence.get(0) + " ");
		newBST = new MyBinarySearchTree<>(sequence.get(0), null);
		for (int i = 1; i < size; i++) {
			System.out.print(sequence.get(i) + " ");
			newBST.insert(sequence.get(i), null);
		}
		System.out.print("\n");

		return newBST;
	}

	/**
	 * 随机生成产生的树的组合只有卡塔兰数个
	 * 
	 * @param size
	 * @return
	 */
	public int countRandomTrees(int size) {
		Others others = new Others();
		return others.getCatalan1(size);
	}

	/**
	 * 左右子树的高度越接近（越平衡），全树的高度也通常越低。<br>
	 * 由n个节点组成的二叉树，高度不低于base2的log n，此时成为理想平衡，出现在满二叉树。<br>
	 * 实际应用中理想平衡太严苛，会放松平衡的标准，只要能确保树的高度不超过base10的log n，就是适度平衡。<br>
	 * 此时的树称为BBST，平衡二叉搜索树。
	 * 
	 * @return
	 */
	boolean isBBST() {
		return false;
	}

	/**
	 * 两个限制：<br>
	 * 操作的计算时间O(1)<br>
	 * 操作次数不超过O(log n)
	 */
	void balance() {
	}

	/**
	 * 围绕node向右旋转
	 */
	void zig(Node<E> node) {
	}

	/**
	 * 围绕node向左旋转
	 */
	void zag(Node<E> node) {
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
