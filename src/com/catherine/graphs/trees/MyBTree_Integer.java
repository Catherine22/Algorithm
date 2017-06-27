package com.catherine.graphs.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
 * 3. 所有的外部节点的深度相等。（在很多树中，外部节点等同于叶节点，在B-Tree中，外部节点代表叶节点不存在的子节点）。<br>
 * 4. 树高 h = 外部节点的深度。（一般的树高是最大的叶节点高度）<br>
 * <br>
 * 内部节点的分支数限制：<br>
 * 树根： 2 <= n+1<br>
 * 其余： [m/2] <= n+1<br>
 * 称作([[m/2], m)-树，比如(3, 5)-tree, (4, 8)-tree<br>
 * 
 * @author Catherine
 *
 */
public class MyBTree_Integer implements BTree {
	/**
	 * 关键码总数
	 */
	private int size;
	/**
	 * B-树的阶次m，至少为3，创建时指定，一般不能修改
	 */
	private int order;

	/**
	 * search()最后访问的非空(除非树空)的节点位置
	 */
	private B_Node hot;
	private B_Node root;
	private final static boolean SHOW_LOG = true;

	public MyBTree_Integer(int order) {
		this(order, 0);
	}

	public MyBTree_Integer(int order, int rootKey) {
		this.order = (order < 3) ? 3 : order;

		root = new B_Node();
		root.setParent(null);

		List<Integer> rootKeys = new ArrayList<>();
		rootKeys.add(rootKey); // 只有一个关键码
		root.setKey(rootKeys);

		B_Node lc = new B_Node();
		lc.setParent(root);
		B_Node rc = new B_Node();
		rc.setParent(root);

		List<B_Node> child = new ArrayList<>();
		child.add(0, lc);// 左孩子
		child.add(1, rc);// 右孩子
		root.setChild(child);
	}

	@Override
	public B_Node getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int size(B_Node node) {
		return 0;
	}

	@Override
	public int order() {
		return order;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getLargestHeight() {
		int n = (int) Math.ceil((size + 1) / 2);
		int p = (int) Math.ceil(order / 2);
		return 1 + (int) Math.floor(Math.log(n) / Math.log(p));
	}

	@Override
	public int getSmallestHeight() {
		return (int) Math.ceil(Math.log(size + 1) / Math.log(order));
	}

	@Override
	public B_Node search(int key) {
		checkElementIndex(key);
		return search(root, key);
	}

	private B_Node search(B_Node target, int key) {
		B_Node header = target;
		int rank = 0;
		while (rank < header.getKey().size()) {
			if (header.getKey().get(rank) < key)
				rank++;
			else if (header.getKey().get(rank) == key)
				return header;// 成功，返回关键码等于key的节点
			else
				break;
		}

		hot = header;
		if (rank > hot.getChild().size() || hot.getChild().get(rank) == null
				|| hot.getChild().get(rank).getKey() == null)
			return null;// 失败，最终抵达外部节点

		loadToRAM();
		return search(hot.getChild().get(rank), key);
	}

	/**
	 * 这边是返回在target超级节点中不大于key的关键码位置
	 * 
	 * @param target
	 * @param key
	 * @return
	 */
	private int searchPosi(B_Node target, int key) {
		B_Node header = target;
		int rank = 0;
		while (rank < header.getKey().size()) {
			if (header.getKey().get(rank) < key)
				rank++;
			else
				return rank - 1;
		}
		return rank - 1;
	}

	@Override
	public boolean insert(int key) {
		if (search(key) != null)
			return false;// 已经存在关键码，直接返回。

		int pos = searchPosi(hot, key);
		hot.getKey().add(pos + 1, key);
		hot.getChild().add(pos + 2, null);// 因为关键码向量(x)与孩子向量(o)的位置应对齐
		size++;
		solveOverflow(hot);// 若发生上溢，做分裂处理
		return true;
	}

	@Override
	public boolean remove(int key) {
		return false;
	}

	@Override
	public void release() {

	}

	@Override
	public void solveOverflow(B_Node node) {
		if (size <= (order - 1))
			return;

		if (SHOW_LOG)
			System.out.print("overflow" + node.getKey());
		// 假设上溢节点的关键码依次为 k0, k1...k(m-1)
		int median = (int) Math.floor(node.getKey().size() / 2);// 取中位数（无条件进位）为分界
		// k0~k(median-1), k(median), k(median+1)~k(m-1)
		// 将 k(median)上升一层，并将刚才左右两组关键码分裂成k(median)的左右孩子。
		int key = node.getKey().get(median);

		// 调整左孩子
		List<Integer> lKeys = new ArrayList<>();
		for (int i = 0; i < median; i++) {
			lKeys.add(node.getKey().get(i));
		}
		List<B_Node> lChildren = new ArrayList<>();
		for (int i = 0; i <= median; i++) {
			lChildren.add(node.getChild().get(i));
		}
		B_Node lChild = new B_Node();
		lChild.setKey(lKeys);
		lChild.setChild(lChildren);

		// 调整右孩子
		List<Integer> rKeys = new ArrayList<>();
		for (int i = median + 1; i < node.getKey().size(); i++) {
			rKeys.add(node.getKey().get(i));
		}
		List<B_Node> rChildren = new ArrayList<>();
		for (int i = median + 1; i < node.getChild().size(); i++) {
			rChildren.add(node.getChild().get(i));
		}
		B_Node rChild = new B_Node();
		rChild.setKey(rKeys);
		rChild.setChild(rChildren);

		// 移除节点
		node.getKey().remove(median);

		if (SHOW_LOG) {
			System.out.print(" split to " + lChild.getKey());
			System.out.println(" & " + rChild.getKey());
		}
		// 当上溢发生在根节点时，指定k(median)为新的根节点，并且具有两分支，k0~k(median-1)的左孩子和k(median+1)~k(m-1)的右孩子
		if (node.getParent() == null) {
			lChild.setParent(root);
			rChild.setParent(root);

			root.getKey().clear();
			root.getKey().add(key);

			root.getChild().clear();
			root.getChild().add(lChild);
			root.getChild().add(rChild);
		} else {
			lChild.setParent(node.getParent());
			rChild.setParent(node.getParent());

			// insert
			search(node.getParent(), key);
			int pos = searchPosi(hot, key);
			System.out.println("pos:" + pos);
			hot.getKey().add(pos + 1, key);
			hot.getChild().set(pos + 1, lChild);
			hot.getChild().set(pos + 2, rChild);
			solveOverflow(hot);// 若发生上溢，做分裂处理

		}

	}

	@Override
	public void solveUnderfolw(B_Node node) {

	}

	/**
	 * 检查位置是否合法并扔出Exception
	 * 
	 * @param index
	 *            指定索引
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	private void checkElementIndex(int index) {
		if (!isElementIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	/**
	 * 检查位置是否合法
	 * 
	 * @param index
	 *            指定索引
	 * @return 是否合法
	 */
	private boolean isElementIndex(int index) {
		return (index >= 0) || (index <= size);
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message. Of the many
	 * possible refactorings of the error handling code, this "outlining"
	 * performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	/**
	 * 模拟把数据载到内存里，每次跨级别存储，都需要花费大量时间。
	 */
	private void loadToRAM() {
		try {
			System.out.println("Loading to memory...");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int level = 0;
		B_Node header = root;
		Queue<B_Node> history = new LinkedList<>();
		if (header != null) {
			result.append(String.format("level %d:[", level++));
			if (header.getKey() != null) {
				if (header.getKey().size() == 0) {
					result.append("[]\n");
				} else {
					for (int k : header.getKey())
						result.append(String.format("%d, ", k));
					result.delete(result.length() - 2, result.length());
					result.append("]\n");
				}
			}
		}
		StringBuilder tmp = new StringBuilder();
		boolean keepTmp = false;
		if (header.getChild() != null) {
			history.addAll(header.getChild());
			tmp.append(String.format("level %d:", level++));
			while (history.size() > 0) {
				header = history.poll();
				if (header != null && header.getKey() != null) {
					keepTmp = true;
					tmp.append(String.format("[", level++));
					if (header.getKey().size() == 0) {
						tmp.append("[]\n");
					} else {
						for (int k : header.getKey())
							tmp.append(String.format("%d, ", k));
						tmp.delete(tmp.length() - 2, tmp.length());
						tmp.append("] ");
					}
				} else
					keepTmp = false;
			}
			tmp.delete(tmp.length() - 1, tmp.length());
			tmp.append("\n");
		}
		if (keepTmp)
			result.append(tmp);

		return result.toString();
	}
}
