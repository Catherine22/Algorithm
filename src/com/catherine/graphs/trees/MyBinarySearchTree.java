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
public class MyBinarySearchTree<E> extends BST_Template<E> implements java.io.Serializable {
	private static final long serialVersionUID = -9214087702987337919L;

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
		Node<E> node = null;
		if (key > parent.key)
			node = insertRC(parent, key, data);
		else if (key < parent.key)
			node = insertLC(parent, key, data);
		if (SHOW_LOG)
			System.out.println(node.toString());
	}

	@Override
	public void remove(int key) {
		Node<E> node = search(key);
		if (node == null)
			throw new NullPointerException("Node not found.");

		if (node.lChild == null && node.rChild == null){
			node = null;
		}

		updateAboveHeight(hot);
	}
}
