package com.catherine.data_type.trees;

public class BinarySearchTree<E> {

	public BinarySearchTree() {

	}

	public static class Node<E> {

		E data;
		int height;
		Node<E> parent;
		Node<E> lChild;
		Node<E> rChild;

		public Node(E data, Node<E> parent, Node<E> lChild, Node<E> rChild, int height) {
			this.data = data;
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
			this.height = height;
		}
	}
	
	
	/**
	 * 子树规模
	 * @return 子节点数
	 */
	public int size(){
		return 0;
	}

	/**
	 * 满二叉树（full binary tree）、真二叉树（proper binary tree）又称为严格二叉树（strictly binary
	 * tree），每个节点都只有0或2个节点。
	 * 
	 * @return
	 */
	public boolean isFull() {
		return false;
	};
	
	/**
	 * 插入子节点于左边
	 * @return
	 */
	public Node<E> insertLC(){
		
		return null;
	}
	
	/**
	 * 插入子节点于右边
	 * @return
	 */
	public Node<E> insertRC(){
		
		return null;
	}
	
	/**
	 * 中序时的直接后继
	 * @return
	 */
	public Node<E> succ(){
		return null;
	}
	
	/**
	 * 遍历
	 */
	public void traverseLevel(){
		
	}
	/**
	 * 先序遍历
	 */
	public void traversePre(){
		
	}
	/**
	 * 中序遍历
	 */
	public void traverseIn(){
		
	}
	/**
	 * 后序遍历
	 */
	public void traversePast(){
		
	}
}
