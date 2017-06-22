package com.catherine.graphs.trees;

import java.util.ArrayList;
import java.util.List;

import com.catherine.graphs.trees.nodes.B_Node;

/**
 * “B-Tree”——平衡的多路搜索树。<br>
 * 
 * 640K ought to be enough for anybody. —— B. Gates, 1981.<br>
 * 
 * 知识点1：<br>
 * 以内存和磁盘为例，两者访问速度为ms（10的-3秒） VS ns（10的-9秒），差至少10的-1，也就是一秒至于一天。<br>
 * 所以才会宁可访问内存10次、100次也要避免访问一次外存。 <br>
 * 两个相邻存储级别之间的数据传输，统称I/O操作。所以应该尽可能地减少I/O操作。 <br>
 * <br>
 * 知识点2：<br>
 * 从磁盘中读写1B与读写1KB几乎一样快。 <br>
 * 所以要嘛一次也不访问，要不就一次访问大量（批量式缓冲区：以page或block为单位，使用缓冲区）。 <br>
 * <br>
 * 经适当合并，得超级节点。<br>
 * 每2代合并：4路<br>
 * 每3代合并：8路<br>
 * 每n代合并：2的m次方路（n条分支），有n-1个关键码（每个超级节点有几个小节点）<br>
 * <br>
 * 利用外存批量访问的支持，加入B-tree的特性，每下降一层，都以超级节点为单位，读入一组关键码，具体一组大小视磁盘的数据块大小。<br>
 * 以AVL tree为例，每次查找要log(2, 10的-9) = 30次，每次只读一个关键码。
 * 以256个分支的B-tree为例，每次查找要log(256, 10的-9) = 4~5次。<br>
 * <br>
 * <br>
 * B-Tree定义：<br>
 * 1. n路平衡搜索树(n >= 2)<br>
 * 2. 所有的叶节点的深度相等。<br>
 * 3. 所有的外部节点的深度相等。（在很多树中，外部节点等同于叶节点，在B-Tree中，外部节点代表叶节点不存在的子节点）。 4. 树高 h =
 * 外部节点的深度。（一般的树高是最大的叶节点高度）<br>
 * <br>
 * 内部节点的分支数限制：<br>
 * 树根： 2 <= n+1<br>
 * 其余： [m/2] <= n+1<br>
 * 称作([[m/2], m)-树，比如(3, 5)-tree, (4, 8)-tree<br>
 * 
 * @author Catherine
 *
 */
public class MyBTree<E> implements BTree<E> {
	/**
	 * 关键码总数
	 */
	private int size;
	/**
	 * 前次
	 */
	private int order;

	/**
	 * 最后访问的非空节点。
	 */
	private B_Node<E> hot;
	private B_Node<E> root;

	public MyBTree() {
		this(null);
	}

	public MyBTree(E rootData) {
		this(rootData, null, null);
	}

	public MyBTree(E rootData, E lcData, E rcData) {
		root = new B_Node<E>();
		root.setParent(null);

		List<Integer> rootKeys = new ArrayList<>();
		rootKeys.add(0); // 只有一个关键码
		root.setKey(rootKeys);

		List<E> rootValues = new ArrayList<>();
		rootValues.add(rootData); // 只有一个关键码
		root.setData(rootValues);

		List<B_Node<E>> child = new ArrayList<>();// 两个孩子
		List<E> lcDatas = new ArrayList<>();
		lcDatas.add(lcData);
		List<E> rcDatas = new ArrayList<>();
		rcDatas.add(rcData);
		List<Integer> lcKeys = new ArrayList<>();
		lcKeys.add(1);
		List<Integer> rcKeys = new ArrayList<>();
		rcKeys.add(2);

		B_Node<E> lc = new B_Node<>();
		lc.setParent(root);
		lc.setKey(lcKeys);
		lc.setData(lcDatas);
		B_Node<E> rc = new B_Node<>();
		rc.setParent(root);
		rc.setKey(rcKeys);
		rc.setData(rcDatas);

		child.add(0, lc);// 左孩子
		child.add(1, rc);// 右孩子
		root.setChild(child);
	}

	@Override
	public B_Node<E> getRoot() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int size(B_Node<E> node) {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public B_Node<E> search(E e) {
		return null;
	}

	@Override
	public boolean insert(E e) {
		return false;
	}

	@Override
	public boolean remove(E e) {
		return false;
	}

	@Override
	public void solveOverflow(B_Node<E> node) {

	}

	@Override
	public void solveUnderfolw(B_Node<E> node) {

	}
}
