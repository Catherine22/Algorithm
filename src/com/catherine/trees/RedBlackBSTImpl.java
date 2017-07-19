package com.catherine.trees;

import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;

/**
 * 四个规则：<br>
 * 1. 树根必为黑色<br>
 * 2. 外部节点均为黑色<br>
 * 3. 其余节点若为红色，则只能有黑孩子（也就是不可能出现同时为红的父子两代，也就是出现一个红节点，该节点的父子比为黑色。）<br>
 * 4. 外部节点到根，途中黑节点数目相等<br>
 * <br>
 * 把红孩子向上提升至父亲旁边，再把他们看作一个超级节点，红黑树就像是四阶的B树或者又称为(2, 4)树，每个节点都有个对应(2, 4)树的key。<br>
 * 
 * @author Catherine
 *
 */
public class RedBlackBSTImpl<E> extends BinarySearchTreeImpl<E> implements RedBlackBST<E> {
	private final static boolean SHOW_LOG = true;
	protected NodeAdapter<E> adapter;

	public RedBlackBSTImpl(int key, E root) {
		super();
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.RB);
		setRoot(key, root);
	}

	@Override
	public Node<E> search(int key) {
		return super.search(key);
	}

	@Override
	public Node<E> insert(int key, E data) {
		return null;
	}

	@Override
	public void remove(int key) {

	}

	@Override
	public void solveDoubleRed(Node<E> node) {

	}

	@Override
	public void solveDoubleBlack(Node<E> node) {

	}

	@Override
	public void updateHeight() {

	}
}
