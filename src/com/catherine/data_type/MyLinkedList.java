package com.catherine.data_type;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 自带Queue操作。<br>
 * LinkedList在查找和移除指定元素时是由头节点一个接着一个往下做的。<br>
 * LinkedList在加入或移除节点时只更动前后节点的引用而已。<br>
 * LinkedList比ArrayList占用更多内存（一个node除了item外还有前后引用）。<br>
 * 所以如果不会让LinkedList进行遍历的操作，它将优于ArrayList。
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyLinkedList<E> extends AbstractSequentialList<E>
		implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

	private final static boolean SHOW_LOG = false;
	private static final long serialVersionUID = 876323262645176354L;
	transient int size = 0;
	/**
	 * 链表的第一个元素<br>
	 * 两原则：<br>
	 * 如果为null最后一个元素也是null，这是个null的链表<br>
	 * 前一位一定为null<br>
	 */
	transient Node<E> first;
	/**
	 * 链表的最后一个元素<br>
	 * 两原则：<br>
	 * 如果为null第一个元素也是null，这是个null的链表<br>
	 * 后一位一定为null<br>
	 */
	transient Node<E> last;

	public MyLinkedList() {
		super();
	}

	public MyLinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	/**
	 * 每一个Node都包含:<br>
	 * 1. item, 自己的元素值<br>
	 * 2. prev, 前一位的元素值<br>
	 * 3. next, 后一位的元素值<br>
	 * 
	 * @author Catherine
	 *
	 * @param <E>
	 */
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * 根据索引获取节点。<br>
	 * <br>
	 * 每个Node的prev／next指向上／下一个Node，因而串联起来成为链表，<br>
	 * 所以搜寻时必须由first或last开始一个接着一个的寻找。
	 * 
	 * @param index
	 *            索引
	 * @return 指定位置的Node<E>
	 */
	private Node<E> node(int index) {
		// 如果指定位置在链表前半段，从first开始找比较快。
		if (index < (size >> 1)) {
			Node<E> x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x;
		}

		// 如果指定位置在链表后半段，从last开始找比较快。
		else {
			Node<E> x = last;
			for (int i = size - 1; i > index; i--)
				x = x.prev;
			return x;
		}
	}

	/**
	 * 加入指定元素到链表第一位
	 * 
	 * @param element
	 *            指定元素
	 */
	@Override
	public void addFirst(E element) {
		linkFirst(element);
	}

	/**
	 * 
	 * 把原本第一位Node（null-item-next）挪到第二位Node（e-item-next），插入e作为第一位Node（null-e-item
	 * ）。 新的链表变成null-e-（旧的）first-...
	 * 
	 * @param element
	 */
	private void linkFirst(E element) {
		Node<E> second = first;
		Node<E> newFirst = new Node<>(null, element, second);
		first = newFirst;

		if (second == null)
			last = newFirst;
		else
			second.prev = newFirst;

		size++;
		modCount++;
	}

	/**
	 * 加入指定元素到链表最后
	 * 
	 * @param element
	 *            指定元素
	 */
	@Override
	public void addLast(E element) {
		linkLast(element);
	}

	/**
	 * 
	 * 把原本最后Node（prev-item-null）挪到倒数第二位Node（prev-item-e），插入e作为最后Node（item-e-null
	 * ）。 新的链表变成...-（旧的）last-e-null
	 * 
	 * @param element
	 */
	private void linkLast(E element) {
		Node<E> second = last;
		Node<E> newlast = new Node<>(second, element, null);
		last = newlast;

		if (second == null)
			first = newlast;
		else
			second.next = newlast;

		size++;
		modCount++;
	}

	/**
	 * 加入指定元素到链表最后，逻辑同{@link #addLast}
	 * 
	 * @param element
	 *            指定元素
	 * @return true
	 */
	public boolean add(E element) {
		linkLast(element);
		return true;
	}

	/**
	 * 加入指定元素到指定位置
	 * 
	 * @param index
	 *            指定索引
	 * @param element
	 *            指定元素
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	public void add(int index, E element) {
		checkPositionIndex(index);

		if (index == size)
			linkLast(element);
		else if (index == 0)
			linkFirst(element);
		else
			linkBefore(element, node(index));
	}

	/**
	 * 原本是predNode(prev - itemP - succNode) - succNode(succNode - itemS - next)
	 * <br>
	 * <br>
	 * 變成predNode(prev - itemP - newNode) - newNode(predNode - element -
	 * succNode) - succNode(newNode - item - next)
	 * 
	 * @param element
	 *            指定元素
	 * @param succ
	 *            successor继任者，原本该位置的Node往后移一位，把自己的位置留给successor
	 */
	private void linkBefore(E element, Node<E> succ) {
		// 重新链接newNode
		final Node<E> pred = succ.prev;
		final Node<E> newNode = new Node<>(pred, element, succ);
		// 重新链接predNode
		pred.next = newNode;
		// 重新链接succNode
		succ.prev = newNode;

		size++;
		modCount++;
	}

	/**
	 * 移除头节点并返回头节点值
	 *
	 * @return 头节点
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E removeFirst() {
		Node<E> fNode = first;
		if (fNode == null)
			throw new NoSuchElementException();
		else
			return unlinkFirst(fNode);
	}

	/**
	 * 移除末节点并返回末节点值
	 * 
	 * @return 末节点
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E removeLast() {
		Node<E> lNode = last;
		if (lNode == null)
			throw new NoSuchElementException();
		else
			return unlinkLast(lNode);
	}

	/**
	 * 原本是first(null - itemP - succNode) - succNode(first - itemS - next) -...
	 * <br>
	 * <br>
	 * 變成first(null - itemS - next) -...
	 * 
	 * @param 头节点
	 * @return 头节点值
	 */
	private E unlinkFirst(Node<E> fNode) {
		final E element = fNode.item;
		final Node<E> succNode = fNode.next;

		first = succNode;

		// 整个链表本来就只有一个元素的情况
		if (succNode == null)
			last = null; // 变成空链表
		else
			succNode.prev = null;

		// 让GC回收被移除的node
		fNode.item = null;
		fNode.next = null;

		size--;
		modCount++;
		return element;
	}

	/**
	 * 原本是...predNode(pred - itemP - last) - last(predNode - itemL - null) <br>
	 * <br>
	 * <br>
	 * 變成...last(pred - itemP - null)
	 * 
	 * @param 末节点
	 * @return 末节点值
	 */
	private E unlinkLast(Node<E> lNode) {
		final E element = lNode.item;
		final Node<E> predNode = lNode.prev;

		last = predNode;

		// 整个链表本来就只有一个元素的情况
		if (predNode == null)
			first = null; // 变成空链表
		else
			last.next = null;

		// 让GC回收被移除的node
		lNode.item = null;
		lNode.prev = null;

		size--;
		modCount++;
		return element;
	}

	/**
	 * 返回第一位元素值
	 * 
	 * @return 第一位元素值
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E getFirst() {
		final Node<E> fNode = first;
		if (fNode == null)
			throw new NoSuchElementException();
		else
			return fNode.item;
	}

	/**
	 * 返回最后一位元素值
	 * 
	 * @return 最后一位元素值
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E getLast() {
		final Node<E> lNode = last;
		if (lNode == null)
			throw new NoSuchElementException();
		else
			return lNode.item;
	}

	/**
	 * 
	 * 返回指定位置元素值
	 * 
	 * @param index
	 *            索引
	 * @return 元素值
	 * @throws IndexOutOfBoundsException
	 */
	public E get(int index) {
		checkElementIndex(index);
		return node(index).item;
	}

	/**
	 * 取代指定位置元素值，返回原本的元素值。
	 * 
	 * @param index
	 *            索引
	 * @param 新的元素值
	 * @return 原本的元素值
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	public E set(int index, E element) {
		checkPositionIndex(index);
		Node<E> node = node(index);
		final E oriE = node.item;
		node.item = element;

		modCount++; // 原LinkedList没有
		return oriE;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	/**
	 * 当链表包含指定元素时返回{@code true}
	 * 
	 * @param 指定元素
	 * @return 当链表包含指定元素时返回{@code true}
	 */
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	/**
	 * 返回指定元素索引值，而且是最先找到（索引值最小）的。<br>
	 * 指定节点的元素值为null不报错。<br>
	 * <br>
	 * <br>
	 * String s = null; <br>
	 * <br>
	 * '==': print(s==null) is true<br>
	 * <br>
	 * 'Object.equal(null)': print(s.equal(null)) is NullPointerException<br>
	 * 因为呼叫.equal()的Object为null，不能呼叫Object的equal()。
	 * 
	 * @param 指定元素
	 * @return 返回指定元素索引值，如果链表里找不到就返回{@code -1}
	 */
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> nodeX = first; nodeX != null; nodeX = nodeX.next) {
				index++;
				if (nodeX.item == null)
					return index;
			}
		} else {
			for (Node<E> nodeX = first; nodeX != null; nodeX = nodeX.next) {
				index++;
				if (o.equals(nodeX.item))
					return index;
			}
		}
		return -1;
	};

	/**
	 * 返回指定元素索引值，而且是最后找到（索引值最大）的。<br>
	 * 指定节点的元素值为null不报错。<br>
	 * <br>
	 * <br>
	 * String s = null; <br>
	 * <br>
	 * '==': print(s==null) is true<br>
	 * <br>
	 * 'Object.equal(null)': print(s.equal(null)) is NullPointerException<br>
	 * 因为呼叫.equal()的Object为null，不能呼叫Object的equal()。
	 * 
	 * @param 指定元素
	 * @return 返回指定元素索引值，如果链表里找不到就返回{@code -1}
	 */
	public int lastIndexOf(Object o) {
		int index = size;
		if (o == null) {
			for (Node<E> nodeX = last; nodeX != null; nodeX = nodeX.prev) {
				index--;
				if (nodeX.item == null)
					return index;
			}
		} else {
			for (Node<E> nodeX = last; nodeX != null; nodeX = nodeX.prev) {
				index--;
				if (o.equals(nodeX.item))
					return index;
			}
		}
		return -1;
	};

	/**
	 * 返回链表头元素。
	 * 
	 * @return 头元素
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E element() {
		return getFirst();
	}

	/**
	 * 返回链表头元素，如果没有(表示此链表为空链表)不报错，返回null。
	 * 
	 * @return 头元素或null
	 */
	@Override
	public E peek() {
		final Node<E> fNode = first;
		return (fNode == null) ? null : fNode.item;
	}

	/**
	 * 加入指定元素到链表第一位，等同{@link #addFirst}。
	 * 
	 * @param element
	 *            指定元素
	 */
	@Override
	public void push(E e) {
		addFirst(e);
	}

	/**
	 * 移除头节点并返回头节点值，等同{@link #removeFirst}。
	 *
	 * @return 头节点
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	@Override
	public E pop() {
		return removeFirst();
	}

	/**
	 * 返回链表头元素，如果没有(表示此链表为空链表)不报错，返回null。<br>
	 * 等同{@link #peek}。
	 * 
	 * @return 头元素或null
	 */
	@Override
	public E peekFirst() {
		return peek();
	}

	/**
	 * 返回链表末元素，如果没有(表示此链表为空链表)不报错，返回null。<br>
	 * 
	 * @return 末元素或null
	 */
	@Override
	public E peekLast() {
		final Node<E> lNode = last;
		return (lNode == null) ? null : lNode.item;
	}

	/**
	 * 返回并移除链表头元素，如果没有(表示此链表为空链表)不报错，返回null。<br>
	 * 
	 * @return 头元素或null
	 */
	@Override
	public E poll() {
		final Node<E> fNode = first;
		return (fNode == null) ? null : unlinkFirst(fNode);
	}

	/**
	 * 返回并移除链表头元素，如果没有(表示此链表为空链表)不报错，返回null。<br>
	 * 等同{@link #poll}。
	 * 
	 * @return 头元素或null
	 */
	@Override
	public E pollFirst() {
		return poll();
	}

	/**
	 * 返回并移除链表末元素，如果没有(表示此链表为空链表)不报错，返回null。<br>
	 * 
	 * @return 末元素或null
	 */
	@Override
	public E pollLast() {
		final Node<E> lNode = last;
		return (lNode == null) ? null : unlinkLast(lNode);
	}

	/**
	 * 加入指定元素到链表最后，逻辑同{@link #add(E)}。<br>
	 * 
	 * @param element
	 *            指定元素
	 * @return true
	 */
	@Override
	public boolean offer(E e) {
		return add(e);
	}

	/**
	 * 加入指定元素到链表第一位，逻辑同{@link #addFirst(E)}。<br>
	 * 
	 * @param element
	 *            指定元素
	 * @return true
	 */
	@Override
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	/**
	 * 加入指定元素到链表最后，逻辑同{@link #addLast(E)}、{@link #offer(E)}。<br>
	 * 
	 * @param element
	 *            指定元素
	 * @return true
	 */
	@Override
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	/**
	 * 移除头节点并返回头节点值，等同{@link #removeFirst}。
	 * 
	 * @return 头元素或null
	 */
	@Override
	public E remove() {
		return removeFirst();
	}

	/**
	 * 移除指定节点。
	 * 
	 * @param 索引
	 * @return 指定元素或null
	 */
	@Override
	public E remove(int index) {
		checkElementIndex(index);
		return unlink(node(index));
	}

	/**
	 * 移除链表中含指定元素的节点，如元素有重复只会移除最先找到（索引最小）的节点。<br>
	 * 如果指定节点的元素值为null，那就返回null不报错。<br>
	 * <br>
	 * <br>
	 * String s = null; <br>
	 * <br>
	 * '==': print(s==null) is true<br>
	 * <br>
	 * 'Object.equal(null)': print(s.equal(null)) is NullPointerException<br>
	 * 因为呼叫.equal()的Object为null，不能呼叫Object的equal()。
	 * 
	 * @param 索引
	 * @return false表示找不到
	 */
	public boolean remove(Object o, boolean increasing) {
		if (o == null) {
			for (Node<E> nodeX = first; nodeX != null; nodeX = nodeX.next) {
				if (nodeX.item == null) {
					unlink(nodeX);
					return true;
				}
			}
		} else {
			for (Node<E> nodeX = first; nodeX != null; nodeX = nodeX.next) {
				if (o.equals(nodeX.item)) {
					unlink(nodeX);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 移除指定节点。<br>
	 * <br>
	 * <br>
	 * 原本是... - predNode(pred - itemP - chosenNode) - chosenNode(predNode -
	 * itemN - succNode) - succNode(chosenNode - itemS - next) -... <br>
	 * <br>
	 * 變成... - predNode(pred - itemP - succNode) - succNode(predNode - itemS -
	 * next) -... <br>
	 * 
	 * @param 索引
	 * @return 指定元素或null
	 */
	private E unlink(Node<E> chosenNode) {
		final E element = chosenNode.item;
		final Node<E> predNode = chosenNode.prev;
		final Node<E> succNode = chosenNode.next;

		if (predNode == null)
			first = succNode;
		else {
			chosenNode.prev = null;// GC回收
			predNode.next = succNode;
		}

		if (succNode == null)
			last = predNode;
		else {
			chosenNode.next = null;// GC回收
			succNode.prev = predNode;
		}

		chosenNode.item = null;// GC回收

		size--;
		modCount++;
		return element;
	}

	/**
	 * 
	 * 和{@link #remove(Object)}一摸一样。<br>
	 * 移除链表中含指定元素的节点，如元素有重复只会移除最先找到（索引最小）的节点。<br>
	 * 如果指定节点的元素值为null，那就返回null不报错。<br>
	 * <br>
	 * <br>
	 * String s = null; <br>
	 * <br>
	 * '==': print(s==null) is true<br>
	 * <br>
	 * 'Object.equal(null)': print(s.equal(null)) is NullPointerException<br>
	 * 因为呼叫.equal()的Object为null，不能呼叫Object的equal()。
	 * 
	 * @param 索引
	 * @return false表示找不到
	 */
	@Override
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	/**
	 * 移除链表中含指定元素的节点，如元素有重复只会移除最后面（索引最大）的节点。<br>
	 * 如果指定节点的元素值为null，那就返回null不报错。<br>
	 * <br>
	 * <br>
	 * String s = null; <br>
	 * <br>
	 * '==': print(s==null) is true<br>
	 * <br>
	 * 'Object.equal(null)': print(s.equal(null)) is NullPointerException<br>
	 * 因为呼叫.equal()的Object为null，不能呼叫Object的equal()。
	 * 
	 * @param 索引
	 * @return false表示找不到
	 */
	@Override
	public boolean removeLastOccurrence(Object o) {
		if (o == null) {
			for (Node<E> nodeX = last; nodeX != null; nodeX = nodeX.prev) {
				if (nodeX.item == null) {
					unlink(nodeX);
					return true;
				}
			}
		} else {
			for (Node<E> nodeX = last; nodeX != null; nodeX = nodeX.prev) {
				if (o.equals(nodeX.item)) {
					unlink(nodeX);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 移除链表所有元素，让GC处理。
	 */
	public void clear() {
		for (Node<E> node = first; node != null;) {
			Node<E> next = node.next;
			node.prev = null;
			node.item = null;
			node.next = null;

			node = next;
		}

		first = last = null;
		size = 0;
		modCount++;
	}

	// -------------------自定义方法-------------------
	/**
	 * 排序后移除链表所有重复元素。 <br>
	 * <br>
	 * 1(a)-1(b)-1(c)-2-2-4 <br>
	 * 1(a)-1(c)-2-2-4 <br>
	 * 1(a)-2-2-4 <br>
	 * 1(a)-2-4
	 */
	public void removeDuplicates() {
		if (first == null || first == last)
			return;
		// sort()

		compareAndMerge(first, first.next);

		int count = size;
		for (Node<E> node = first; node != null; node = node.next)
			if (--count == 0)
				last = node;
	}

	/**
	 * 传入两节点，若相同值则链接，不同则找下个节点，利用递归合并。
	 * 
	 * @param fNode
	 *            较前面的节点
	 * @param sNode
	 *            下一个节点或之后的其他节点
	 */

	private void compareAndMerge(Node<E> fNode, Node<E> sNode) {
		final Node<E> node1 = fNode;
		final Node<E> node2 = sNode;

		if (fNode == null)
			return;
		if (SHOW_LOG) {
			System.out.println("size:" + size);
			if (sNode != null)
				System.out.println(fNode.item + " compare to " + sNode.item);

			for (Node<E> node = first; node != null; node = node.next)
				System.out.print(node.item + " ");
			System.out.print("\n");
		}

		if (sNode != null) {
			if (node1.item == node2.item) {
				size--;
				compareAndMerge(node1, node2.next);
			} else {
				if (fNode.next == sNode)
					compareAndMerge(node2, node2.next);
				else
					link(node1, node2);

			}
		}
	}

	/**
	 * 链接传入的两节点。
	 * 
	 * @param fNode
	 *            前节点
	 * @param sNode
	 *            后节点
	 */
	private void link(Node<E> fNode, Node<E> sNode) {
		if (fNode.next == sNode)
			return;

		if (sNode != null) // null表示
			sNode.prev = fNode;

		fNode.next = sNode;
		compareAndMerge(sNode, sNode.next);
	}
	// -------------------自定义方法-------------------

	/**
	 * 回传一个array，包含此链表的元素并且正确的被排列。
	 * 
	 * @return A new array (In other words, this method must allocate a new
	 *         array)
	 */
	public Object[] toArray() {
		int index = 0;
		Object[] array = new Object[size];
		for (Node<E> node = first; node != null; node = node.next)
			array[index++] = node.item;
		return array;
	}

	/**
	 * 将当前链表转换成与传入的T类型相同的数组<br>
	 * 当传入的a的length小于链表的size的时候、方法内部会生成一个新的T[ ]返回<br>
	 * 如果传入的T[]的length大于链表的size、则T[]从下标size开始到最后的元素都自动用null填充。
	 * 
	 * @param a
	 *            T类型array
	 * @return 全新的array
	 * @throws ArrayStoreException
	 *             if the runtime type of the specified array is not a supertype
	 *             of the runtime type of every element in this list
	 * @throws NullPointerException
	 *             if the specified array is null
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

		int index = 0;
		Object[] array = a;
		for (Node<E> node = first; node != null; node = node.next)
			array[index++] = node.item;

		if (a.length > size)
			a[size] = null;
		return a;
	}

	/**
	 * 拷贝此链表并回传副本
	 * 
	 * @return 副本
	 */
	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			MyLinkedList<E> clone = (MyLinkedList<E>) super.clone();

			// 先让clone回到处女的状态
			clone.modCount = 0;
			clone.size = 0;
			clone.first = clone.last = null;

			// 重新填充clone
			for (Node<E> node = first; node != null; node = node.next)
				clone.add(node.item);

			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/**
	 * 储存MyLinkedList的实例到输出流（也就是持久化），要保证储存过程中数据没被修改。<br>
	 * 其实就是保存size和每个元素。
	 * 
	 * @param s
	 *            输出流
	 * @throws java.io.IOException
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		// 开始前先记录目前修改次数
		final int expectedModCount = modCount;

		s.defaultWriteObject();
		s.writeInt(size);
		for (Node<E> node = first; node != null; node = node.next)
			s.writeObject(node.item);

		if (expectedModCount != modCount) {
			// 表示此数组刚才有做其他操作，已更动
			throw new ConcurrentModificationException();
		}
	}

	@Override
	public Iterator<E> descendingIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	/**
	 * 检查位置是否合法并扔出Exception
	 * 
	 * @param index
	 *            指定索引
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	private void checkPositionIndex(int index) {
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
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
	private boolean isPositionIndex(int index) {
		return (index >= 0) || (index <= size);
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

}
