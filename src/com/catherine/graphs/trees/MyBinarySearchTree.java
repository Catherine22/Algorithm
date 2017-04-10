package com.catherine.graphs.trees;

/**
 * 
 * 若任意节点的左子树不空，则左子树上所有结点的值均小于（等于）它的根结点的值<br>
 * 若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值<br>
 * 所有节点的垂直投影就是该树的中序遍历<br>
 * 
 * @author Catherine
 *
 */
public class MyBinarySearchTree<E> extends BST_Template<E> implements java.io.Serializable {
	private static final long serialVersionUID = -9214087702987337919L;

	public MyBinarySearchTree(E root) {
		super(root);
	}

	@Override
	public Node<E> search(E data) {
		return null;
	}
}
