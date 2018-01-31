package com.catherine.priority_queue;

import java.util.List;

import com.catherine.trees.MyBinaryTree;
import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;

/**
 * 左式堆<br>
 * 用来高效合并两个已排序的堆。<br>
 * <br>
 * 特性：<br>
 * 1. 保证堆序性<br>
 * 2. 拓扑结构上不会是完全二叉树<br>
 * 3. 引入外部节点与npl(Null point length，即任意节点到外部节点到最短距离)<br>
 * 4. 任意节点x的左孩子npl>=右孩子npl则成为左倾堆或左式堆<br>
 * 
 * @author Catherine
 *
 * @param <T>
 */
public class LeftistHeap<T extends Comparable<? super T>> {
	protected final boolean SHOW_DEBUG_LOG = false;
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 5439346124731561315L;

	/**
	 * 任意节点到外部节点到最短距离，同时也代表以t为根节点的最大满子树高度。<br>
	 * 1. 外部节点npl必为0<br>
	 * 2. 返回左右孩子中npl较小值+1
	 * 
	 * @param t
	 * @return
	 */
	public int getNPL(T t) {
		return 0;
	}

	private int getNPL(Node<T> node) {
		if (node == null)
			return 0;

		// 本来完整逻辑应为：
		// Node<T> lc = getLChild(node);
		// Node<T> rc = getRChild(node);
		// if (getNPL(lc) > getNPL(rc))
		// return getNPL(rc) + 1;
		// else
		// return getNPL(lc) + 1;

		// 但左式堆的定义让左孩子npl必>=右孩子npl，因此只需考虑右孩子
		return getNPL(getRChild(node)) + 1;

	}

	private Node<T> getLChild(Node<T> node) {
		if (node == null)
			return null;
		return node.getlChild();
	}

	private Node<T> getRChild(Node<T> node) {
		if (node == null)
			return null;
		return node.getrChild();
	}

	/**
	 * 右侧链，从节点t出发，一直延右侧前进。<br>
	 * 右侧链的长度=npl(t)
	 * 
	 * @param t
	 */
	public void getRChain(T t) {

	}
}
