package com.catherine.trees;

import com.catherine.trees.nodes.BNode;

public interface BTree<E> {
	/**
	 * 一个节点最多有m-1个key值
	 * 
	 * @return
	 */
	public boolean isFull();

	/**
	 * 返回根节点
	 * 
	 * @return 根节点
	 */
	public BNode<E> getRoot();

	/**
	 * 是否为空树（没有节点）
	 * 
	 * @return boolean
	 */
	public boolean isEmpty();

	/**
	 * 子树规模
	 * 
	 * @return 子节点数
	 */
	public int size();

	/**
	 * 阶次
	 * 
	 * @return
	 */
	public int order();

	/**
	 * 节点高度h定义： 树根第0层<br>
	 * 树根的子节点第1层<br>
	 * ...<br>
	 * 叶节点第h-1层<br>
	 * 外部节点第h层<br>
	 * 
	 * @return 高度
	 */
	public int getHeight();

	/**
	 * 取最大高度（表示每个超级节点应尽可能瘦）
	 * 
	 * @return
	 */
	public int getLargestHeight();

	/**
	 * 取最小高度（表示每个超级节点应尽可能胖），但分枝数不可超过order
	 * 
	 * @return
	 */
	public int getSmallestHeight();

	/**
	 * 一层一层往下找，直觉会想到用二分查找算法加速，但以整个内外存来看，这样的加速未必有效，甚至可能会花上更多时间。<br>
	 * 失败查找会终止于外部节点。<br>
	 * 由于每一层都只会查找一组key列表，所以会影响速度的是树的高度。<br>
	 * 时间花在：<br>
	 * 1. 载入下一层次节点（IO操作），这边最耗时。<br>
	 * 2. 每一层的顺序查找。 <br>
	 * 
	 * @param key
	 * @return
	 */
	public BNode<E> search(E key);

	/**
	 * 须考虑插入key后导致的上溢情形。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean insert(E key);

	/**
	 * 删除时希望目标节点是叶节点，如果不是叶节点就和后继（一定会是叶节点）交换位置后再移除。<br>
	 * 删除须考虑下溢。
	 * 
	 * @param key
	 * @return
	 */
	public boolean remove(E key);

	/**
	 * 上溢：关键码总数超过阶次m-1，即违反B-tree定义<br>
	 * 因插入而上溢后的分裂处理，分裂处理后，可能导致父节点也发生上溢，以此类推最多到根节点。<br>
	 * 如果分裂发生在根节点，就让中位数节点成为新的根节点，整个B-tree唯一可允许的例外就是根节点可以只有两个分支（就是此时），<br>
	 * 整个B-tree唯一增加高度的情况就是分裂发生在根节点。
	 * 
	 * @param node
	 */
	public void solveOverflow(BNode<E> node);

	/**
	 * 因删除而下溢后的合并处理<br>
	 * 下溢:关键码总数少于[m / 2]-1，即违反B-tree定义<br>
	 * 第一种做法：旋转（首先找出左右的兄弟，如果兄弟节点有多余的key值，找出最大或最小的key顶替父节点的值，然后再把父节点的key值挪给下溢节点。）
	 * <br>
	 * 第二种做法：合并。假如兄弟节点没有多余key值适用，此时兄弟节点的key长度为最小长度[m / 2]-1，目标节点key长度 < [m /
	 * 2]-1，加上父节点的key值(1)合并（key值长度<=m-1）<br>
	 * 
	 * @param node
	 */
	public void solveUnderflow(BNode<E> node);

	public void printInfo();
}
