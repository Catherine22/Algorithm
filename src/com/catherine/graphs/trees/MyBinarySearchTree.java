package com.catherine.graphs.trees;

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
public class MyBinarySearchTree<E> extends BST_Template<E>{
	
	public MyBinarySearchTree(int key, E root) {
		super(key, root);
	}

	@Override
	public Node<E> search(int key) {
		hot = root;
		boolean stop = false;
		Node<E> node = null;

		while (!stop) {
			if (key > hot.key) {
				if (hot.rChild == null)
					stop = true;
				else
					hot = hot.rChild;
			} else if (key < hot.key) {
				if (hot.lChild == null)
					stop = true;
				else
					hot = hot.lChild;
			} else {
				node = hot;
				hot = node.parent;
				stop = true;
			}
		}
		return node;
	}

	@Override
	public void insert(int key, E data) {
		if (search(key) != null)
			throw new IllegalArgumentException("This node has already been added.");

		final Node<E> parent = hot;
		if (key > parent.key)
			insertRC(parent, key, data);
		else if (key < parent.key)
			insertLC(parent, key, data);
	}

	@Override
	public void remove(int key) {
		Node<E> node = search(key);
		if (node == null)
			throw new NullPointerException("Node not found.");

		// 情况2
		if (node.lChild != null && node.rChild != null) {
			Node<E> succ = succ(node);
			if (SHOW_LOG) {
				System.out.println("node:" + node.key);
				System.out.println("succ:" + succ.key);
			}
			swap(node, succ);
			node = succ;
		}

		Node<E> parent = node.parent;
		// 情况1
		if (node.lChild != null && node.rChild == null) {
			killParent(parent, node.lChild);
			parent.height = parent.lChild.height + 1;
		}
		// 情况1
		else if (node.lChild == null && node.rChild != null) {
			killParent(parent, node.rChild);
			parent.height = parent.rChild.height + 1;
		}
		// 情况1
		else {
			if (node != root && node == parent.lChild)
				parent.lChild = null;
			else if (node != root && node == parent.rChild)
				parent.rChild = null;
			parent.height = 0;
			node = null;
		}
		updateAboveHeight(parent);

		size--;
	}

	@Override
	public boolean isBBST() {
		return false;
	}

	@Override
	void balance() {
		
	}

	@Override
	void zig(Node<E> node) {
		
	}

	@Override
	void zag(Node<E> node) {
		
	}
}
