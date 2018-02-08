package com.catherine.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;

import com.catherine.data_type.MyLinkedList;
import com.catherine.trees.nodes.Node;
import com.catherine.trees.nodes.NodeAdapter;
import com.catherine.trees.nodes.Nodes;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * 
 * @author Catherine
 *
 *         每个节点都有0～2个子节点
 *
 * @param <E>
 */
public class MyBinaryTree<E extends Comparable<? super E>> implements BinaryTree<E> {
	protected final static boolean SHOW_LOG = false;
	protected NodeAdapter<E> adapter;
	protected transient int size = 0;
	protected Node<E> root;

	protected MyBinaryTree() {
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.STANDARD);
	}

	public MyBinaryTree(E root) {
		adapter = new NodeAdapter<>();
		adapter.setType(Nodes.STANDARD);
		setRoot(root);
	}

	/**
	 * 设置根节点
	 * 
	 * @param 数值
	 * @return 根节点
	 */
	public Node<E> setRoot(E data) {
		Node<E> n;
		if (root == null) {
			size++;
			n = adapter.buildNode(data, null, null, null, 0, 0);
		} else
			n = adapter.buildNode(data, null, root.getlChild(), root.getrChild(), root.getHeight(), root.getDepth());
		root = n;
		return root;
	}

	@Override
	public Node<E> getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int size(Node<E> node) {
		int s = 1;// 加入自身
		if (node.getlChild() != null)
			s += size(node.getlChild());

		if (node.getrChild() != null)
			s += size(node.getrChild());
		return s;
	}

	/**
	 * 每加入或移除一子节点，父节点及其父节点等高度都会变动。 <br>
	 * 二叉树须检查是否已被兄弟节点修改过。 <br>
	 * 节点高度定义：<br>
	 * 1. 只有单一节点：0<br>
	 * 2. 无节点，也就是空树：-1<br>
	 * 3. 其他：取左右子树中高度大着+1<br>
	 * 
	 * 
	 * @return 高度
	 */
	protected void updateAboveHeight(Node<E> node) {
		if (node == null || node.getParent() == null)
			return;

		int h1 = node.getHeight();
		int h2 = -1;
		// 该节点是左节点且兄弟节点存在
		if (node.getParent().getlChild() == node && node.getParent().getrChild() != null) {
			h2 = node.getParent().getrChild().getHeight();
		}
		// 该节点是右节点且兄弟节点存在
		else if (node.getParent().getrChild() == node && node.getParent().getlChild() != null) {
			h2 = node.getParent().getlChild().getHeight();
		}
		int newH = (h1 > h2) ? h1 + 1 : h2 + 1;
		// 更新父节点高度而不是自己，自己在呼叫方法时就必须是正确的值了
		node.getParent().setHeight(newH);
		updateAboveHeight(node.getParent());
	}

	@Override
	public int getHeight() {
		if (root == null)
			return -1;

		if (root.getlChild() == null && root.getrChild() == null)
			return 0;

		return getHeight(root);
	}

	/**
	 * 使用递归计算高度
	 * 
	 * @param node
	 * @return
	 */
	protected int getHeight(Node<E> node) {
		int l = 0;
		int r = 0;

		if (node == null)
			return -1;// 若为叶子，上一次判断时会直达else判断式，因此l和r都多加一次，在此处扣除

		if (node.getlChild() != null && node.getrChild() == null) {
			l += getHeight(node.getlChild());
			l++;
		} else if (node.getlChild() == null && node.getrChild() != null) {
			r += getHeight(node.getrChild());
			r++;
		} else {
			l += getHeight(node.getlChild());// 若为叶子得-1
			r += getHeight(node.getrChild());// 若为叶子得-1
			l++;
			r++;
		}
		if (SHOW_LOG)
			System.out.println(node.getInfo() + "\tl:" + l + "\tr:" + r + "\th:" + node.getHeight());
		return (l > r) ? l : r;
	}

	@Override
	public boolean isFull() {
		if (root == null)
			throw new NullPointerException("null root!");

		Queue<Node<E>> parent = new LinkedList<>();
		Queue<Node<E>> siblings = new LinkedList<>();
		Node<E> node = root;
		parent.offer(node);
		// int level = 0;

		while (node != null || !parent.isEmpty()) {
			// System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				// System.out.print(node.getInfo());

				if (node.getlChild() != null)
					siblings.offer(node.getlChild());

				if (node.getrChild() != null)
					siblings.offer(node.getrChild());

				if (node.getlChild() != null && node.getrChild() == null)
					return false;

				if (node.getlChild() == null && node.getrChild() != null)
					return false;
			}

			for (Node<E> n : siblings)
				parent.offer(n);

			siblings.clear();
			node = null;

			// System.out.print("\n");
		}
		return true;
	};

	/**
	 * 插入子节点于左边，若已有节点则成为该节点的父节点
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 新节点
	 */
	public Node<E> insertLC(Node<E> parent, E data) {
		Node<E> child;
		if (parent.getlChild() != null) {
			final Node<E> cNode = parent.getlChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(data, parent, null, null, 0, parent.getDepth() + 1);

		if (SHOW_LOG)
			System.out.println("insertLC:" + child.toString());
		size++;
		parent.setlChild(child);
		child.setHeight(getHeight(child));
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
	public Node<E> insertRC(Node<E> parent, E data) {
		Node<E> child;
		if (parent.getrChild() != null) {
			final Node<E> cNode = parent.getrChild();
			final Node<E> lChild = cNode.getlChild();
			final Node<E> rChild = cNode.getrChild();
			child = adapter.buildNode(data, parent, lChild, rChild, cNode.getHeight(), cNode.getDepth());
		} else
			child = adapter.buildNode(data, parent, null, null, 0, parent.getDepth() + 1);

		if (SHOW_LOG)
			System.out.println("insertRC:" + child.toString());
		size++;
		parent.setrChild(child);
		child.setHeight(getHeight(child));
		updateAboveHeight(child);
		return child;
	}

	@Override
	public void removeRCCompletely(Node<E> parent) {
		if (parent == null) // throw new NullPointerException("Parent must not
							// be null");
			return;
		if (parent.getrChild() != null) {
			size -= size(parent.getrChild());
			parent.setrChild(null);
			int height = (parent.getlChild() != null) ? parent.getlChild().getHeight() + 1 : 0;
			parent.setHeight(height);
			updateAboveHeight(parent);
		}
	}

	@Override
	public void removeLCCompletely(Node<E> parent) {
		if (parent == null)// throw new NullPointerException("Parent must not be
							// null");
			return;
		if (parent.getlChild() != null) {
			size -= size(parent.getlChild());
			parent.setlChild(null);
			int height = (parent.getrChild() != null) ? parent.getrChild().getHeight() + 1 : 0;
			parent.setHeight(height);
			updateAboveHeight(parent);
		}
	}

	/**
	 * 修改左子树的值，如果没有就创建一个
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 原数值
	 */
	public E setLC(Node<E> parent, E data) {
		if (parent.getlChild() == null) {
			insertLC(parent, data);
			return null;
		}

		final E o = parent.getlChild().getData();
		parent.getlChild().setData(data);
		return o;
	}

	/**
	 * 修改右子树的值，如果没有就创建一个
	 * 
	 * @param parent
	 *            父节点
	 * @param data
	 *            数值
	 * @return 原数值
	 */
	public E setRC(Node<E> parent, E data) {
		if (parent.getrChild() == null) {
			insertRC(parent, data);
			return null;
		}

		final E o = parent.getrChild().getData();
		parent.getrChild().setData(data);
		return o;
	}

	@Override
	public void traverseLevel() {
		if (root == null)
			throw new NullPointerException("null root!");

		Queue<Node<E>> parent = new LinkedList<>();
		Queue<Node<E>> siblings = new LinkedList<>();
		Node<E> node = root;
		parent.offer(node);
		int level = 0;
		boolean isRight = false;
		while (node != null || !parent.isEmpty()) {
			System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				node = parent.poll();
				if (node == root) {
					isRight = false;
				} else {
					isRight = !isRight;
				}

				if (node != null) {
					System.out.print(node.getInfo());

					if (node.getlChild() != null)
						siblings.offer(node.getlChild());
					else
						siblings.offer(null);

					if (node.getrChild() != null)
						siblings.offer(node.getrChild());
					else
						siblings.offer(null);
				}

			}

			int countdown = siblings.size();
			for (Node<E> n : siblings) {
				parent.offer(n);

				if (n == null)
					countdown--;
			}

			siblings.clear();
			node = null;
			System.out.print("\n");

			if (countdown == 0)
				break;
		}
	}

	@Override
	public void traversePreNR1() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePreNR1");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively pre-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		bin.push(root);
		while (!bin.isEmpty()) {
			Node<E> node = bin.pop();
			System.out.print(node.getInfo());

			if (node.getrChild() != null)
				bin.push(node.getrChild());

			if (node.getlChild() != null)
				bin.push(node.getlChild());
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	@Override
	public void traversePreNR2() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePreNR2");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively pre-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;

		while (node != null || !bin.isEmpty()) {
			// 遍历一排的所有左节点
			while (node != null) {
				System.out.print(node.getInfo());
				bin.push(node);// 弹出打印过的没用节点
				node = node.getlChild();
			}

			// 遍历过左节点后前往最近的右节点，之后再遍历该右节点的整排左节点
			if (bin.size() > 0) {
				node = bin.pop();
				node = node.getrChild();
			}
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	@Override
	public void traversePre() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePre");
		Analysis.startTracking(tLog);

		System.out.println("recursively pre-order traverse:");
		traversePre(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点先序遍历（中-左-右）
	 */
	public void traversePre(Node<E> node) {
		System.out.print(node.getInfo());
		if (node.getlChild() != null)
			traversePre(node.getlChild());
		if (node.getrChild() != null)
			traversePre(node.getrChild());
	}

	@Override
	public void traverseInNR() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traverseInNR");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively in-order traverse:");

		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;

		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.getlChild();
			}
			if (!bin.isEmpty()) {
				node = bin.pop();
				System.out.print(node.getInfo());
				node = node.getrChild();
			}
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	@Override
	public void traverseIn() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traverseIn");
		Analysis.startTracking(tLog);

		System.out.println("recursively in-order traverse:");
		traverseIn(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点中序遍历（左-中-右）
	 */
	public void traverseIn(Node<E> node) {
		if (node.getlChild() != null)
			traverseIn(node.getlChild());
		System.out.print(node.getInfo());
		if (node.getrChild() != null)
			traverseIn(node.getrChild());
	}

	/**
	 * {@link #succ(Node)}专用，记录上次中序访问节点
	 */
	private Node<E> preTmp;
	/**
	 * {@link #succ(Node)}专用，记录直接后继
	 */
	private Node<E> succ;
	/**
	 * {@link #succ(Node)}专用，停止递归
	 */
	private boolean stopRecursion = false;

	/**
	 * 返回当前节点在中序意义下的直接后继。
	 * 
	 * @param node
	 *            当前节点
	 * @return 后继节点
	 */
	@Override
	public Node<E> succ(Node<E> node) {
		succ = null;
		preTmp = null;
		stopRecursion = false;
		succ(node, root);
		return succ;
	}

	/**
	 * 
	 * @param node
	 *            指定节点（固定）
	 * @param tmp
	 *            每次递归的节点
	 */
	private void succ(Node<E> node, Node<E> tmp) {
		if (!stopRecursion) {
			if (tmp.getlChild() != null)
				succ(node, tmp.getlChild());
			// 目的是要找出直接后继，一旦上一个节点为指定节点，表示这次的节点就是要找的直接后继
			if (node == preTmp) {
				succ = tmp;
				stopRecursion = true;
			}
			preTmp = tmp;
			if (tmp.getrChild() != null)
				succ(node, tmp.getrChild());
		}
	}

	@Override
	public void traversePostNR1() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePostNR1");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively post-order traverse:");
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;
		Node<E> lastLC = null;// 如果该节点有右子树，遍历其右子树之前先暂存该节点。

		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.getlChild();
			}

			node = bin.peek();

			// 当前节点的右孩子如果为空或者已经被访问，则访问当前节点
			if (node.getrChild() == null || node.getrChild() == lastLC) {
				System.out.print(node.getInfo());
				lastLC = node;// 一旦访问过就要记录，下一轮就会判断到node.getrChild() == lastLC
				bin.pop();// 打印过就从栈里弹出
				node = null;// 其实node应为栈中最后一个节点，下一轮会指定bin.peek()
			} else
				node = node.getrChild();
		}
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	@Override
	public void traversePostNR2() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePostNR2");
		Analysis.startTracking(tLog);

		System.out.println("non-recursively post-order traverse:");
		Stack<Node<E>> lBin = new Stack<>();
		Stack<Node<E>> rBin = new Stack<>();
		Node<E> node = root;
		lBin.push(node);

		while (!lBin.isEmpty()) {
			node = lBin.pop();
			rBin.push(node);

			if (node.getlChild() != null)
				lBin.push(node.getlChild());

			if (node.getrChild() != null)
				lBin.push(node.getrChild());
		}

		while (!rBin.isEmpty()) {
			System.out.print(rBin.peek().getInfo());
			rBin.pop();
		}

		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	@Override
	public void traversePost() {
		if (root == null)
			throw new NullPointerException("null root!");

		TrackLog tLog = new TrackLog("traversePost");
		Analysis.startTracking(tLog);

		System.out.println("post-order traverse:");
		traversePost(root);
		System.out.println("\n");

		Analysis.endTracking(tLog);
		Analysis.printTrack(tLog);
	}

	/**
	 * 递归<br>
	 * 从任一节点后序遍历（左-右-中）
	 */
	private void traversePost(Node<E> node) {
		if (node.getlChild() != null)
			traversePost(node.getlChild());
		if (node.getrChild() != null)
			traversePost(node.getrChild());
		System.out.print(node.getInfo());
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	public synchronized void copyInto(Object[] anArray) {
		Stack<Node<E>> bin = new Stack<>();
		Node<E> node = root;
		anArray = new Object[size];
		int h = 0;

		while (node != null || bin.size() > 0) {
			while (node != null) {
				bin.push(node);
				node = node.getlChild();
			}
			if (!bin.isEmpty()) {
				node = bin.pop();
				anArray[h] = node.getData();
				node = node.getrChild();
			}
		}
	}

	enum Tag {
		LEVEL, ROOT, LEFT, RIGHT
	}

	public Object clone() {
		// clone用
		Queue<Tag> flags = new LinkedList<>();
		Queue<Node<E>> history = new LinkedList<>();
		// 走访用
		int level = 0;
		Queue<Node<E>> parent = new LinkedList<>();
		Queue<Node<E>> siblings = new LinkedList<>();
		// 以阶层重新填充clone，首先阶层走访，记录各节点的值与位置
		if (root != null) {
			Node<E> node = root;
			parent.offer(node);
			boolean isRight = false;
			while (node != null || !parent.isEmpty()) {
				// System.out.print("level " + level++ + ",\t");
				System.out.print("level " + level++ + ",\n");
				flags.offer(Tag.LEVEL);
				while (!parent.isEmpty()) {
					node = parent.poll();
					if (node == null) {
						if (isRight)
							flags.offer(Tag.RIGHT);
						else
							flags.offer(Tag.LEFT);
						history.offer(null);
						isRight = !isRight;
					} else if (node == root) {
						flags.offer(Tag.ROOT);
						history.offer(root);
						System.out.println("build root " + node.getData());
						isRight = false;
					} else {
						if (isRight) {
							flags.offer(Tag.RIGHT);
							history.offer(node);
							// clone.insertRC(node.getParent(),
							// node.getData()).getParent();
							System.out
									.println("insert R child " + node.getData() + " to " + node.getParent().getData());
						} else {
							flags.offer(Tag.LEFT);
							history.offer(node);
							// clone.insertLC(node.getParent(),
							// node.getData()).getParent();
							System.out
									.println("insert L child " + node.getData() + " to " + node.getParent().getData());
						}
						isRight = !isRight;
					}

					if (node != null) {
						// System.out.print(node.getInfo());

						if (node.getlChild() != null)
							siblings.offer(node.getlChild());
						else
							siblings.offer(null);

						if (node.getrChild() != null)
							siblings.offer(node.getrChild());
						else
							siblings.offer(null);
					}

				}

				int countdown = siblings.size();
				for (Node<E> n : siblings) {
					parent.offer(n);

					if (n == null)
						countdown--;
				}

				siblings.clear();
				node = null;

				if (countdown == 0)
					break;
			}
		}

		System.out.println("\nclone!!");
		// System.out.println(history.toString());
		// System.out.println(flags.toString());

		level = 0;
		Node<E> head = null;
		MyBinaryTree<E> clone = null;
		parent.clear();
		siblings.clear();
		while (history.size() > 0) {
			System.out.print("level " + level++ + ",\n");
			while (flags.peek() != Tag.LEVEL) {
				if (flags.peek() == Tag.ROOT) {
					clone = new MyBinaryTree<>(history.poll().getData());
					head = clone.root;
				} else if (flags.peek() == Tag.LEFT) {
					if (history.peek() == null) {
						System.out.println("null");
						history.poll();
					} else {
						System.out.println("add L:" + siblings.peek().getData());
						siblings.offer(clone.insertLC(head, history.poll().getData()));
					}
				} else if (flags.peek() == Tag.RIGHT) {
					if (history.peek() == null) {
						System.out.println("null");
						history.poll();
					} else {
						System.out.println("add R:" + siblings.peek().getData());
						siblings.offer(clone.insertRC(head, history.poll().getData()));
					}
					head = parent.poll();
				}
				flags.poll();
			}

			int countdown = siblings.size();
			for (Node<E> n : siblings) {
				parent.offer(n);

				if (n == null)
					countdown--;
			}

			siblings.clear();
			head = parent.poll();
			if (countdown == 0)
				break;

			flags.poll();
		}
		// clone.traverseLevel();
		return clone;
	}

}
