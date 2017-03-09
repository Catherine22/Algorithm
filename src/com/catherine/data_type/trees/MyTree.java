package com.catherine.data_type.trees;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 定义tree的节点时包含了三个元素（索引、值、父节点的索引）。<br>
 * 利用ArrayList的索引作为有序树的索引，再加入LinkedList的Node当作树的每个节点。<br>
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyTree<E> extends AbstractTree<E> implements Cloneable, Serializable {

	private static final long serialVersionUID = 5469151515081509859L;
	private transient Node<E>[] elementData;

	private int size = 0;
	private int modCount = 0;

	/**
	 * Default capacity = 10
	 */
	public MyTree() {
		this(10);
	}

	public MyTree(E root) {
		this(10);
		setRoot(root);
	}

	public MyTree(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException(
					"Capacity should be filled in more than 0! Illegal capacity: " + capacity);
		else
			elementData = new Node[capacity];
	}

	public MyTree(int capacity, E root) {
		if (capacity <= 0)
			throw new IllegalArgumentException(
					"Capacity should be filled in more than 0! Illegal capacity: " + capacity);
		else {
			elementData = new Node[capacity];
			setRoot(root);
		}
	}

	private static class Node<E> {
		E item;
		Node<E> parent;
		Node<E>[] children;

		Node(Node<E> parent, E item, Node<E>[] children) {
			this.item = item;
			this.parent = parent;
			this.children = children;
		}
	}

	/**
	 * 将此Tree实例的长度改成当前列表长度
	 */
	public void trimToSize() {
		modCount++; // 此Tree全部被修改次数
		int oldCapacity = elementData.length;
		if (size < oldCapacity)
			elementData = Arrays.copyOf(elementData, size);
	}

	/**
	 * 确保此Tree的最小容量能容纳下参数minCapacity指定的容量，<br>
	 * 当minCapacity大于原容量时须扩容，首先增加原本的一半，<br>
	 * 万一增加后还是小于minCapacity就直接用minCapacity当新容量。<br>
	 * <br>
	 * 之所以扩一半是因为加0.5倍式扩容虽然牺牲内存空间，空间利用率(已使用/全部空间)至少大于75%，但在运行速度上远超越递增式扩容。<br>
	 * 
	 * @param minCapacity
	 *            指定的最小所需容量
	 */
	public void ensureCapacity(int minCapacity) {
		modCount++;
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	/**
	 * 逻辑同{@link #ensureCapacity}，给子树专用。
	 * 
	 * @param subtree
	 *            指定的树
	 * @param minCapacity
	 *            指定的最小所需容量
	 */
	private void ensureSubtreeCapacity(Node<E>[] subtree, int minCapacity) {
		modCount++;
		if (minCapacity - subtree.length > 0)
			growSubtree(subtree, minCapacity);
	}

	/**
	 * 有些虚拟机会保留head words在array中，所以须取最大整数扣除标头，否则造成OutOfMemoryError
	 */
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	/**
	 * 扩容（不超过MAX_ARRAY_SIZE）<br>
	 * 用>>代替／2<br>
	 * 用<<代替*2<br>
	 * 
	 * @param minCapacity
	 *            最小所需容量
	 */
	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + oldCapacity >> 1;
		if (newCapacity - minCapacity < 0)// 扩容后还是不够
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)// 扩容后超越整数上限，设置长度为上限
			newCapacity = hugeCapacity(minCapacity);
		elementData = Arrays.copyOf(elementData, minCapacity);
	}

	/**
	 * 逻辑同{@link #grow}，给子树专用。
	 * 
	 * @param subtree
	 *            指定的树
	 * @param minCapacity
	 *            指定的最小所需容量
	 */
	private void growSubtree(Node<E>[] subtree, int minCapacity) {
		int oldCapacity = subtree.length;
		int newCapacity = oldCapacity + oldCapacity >> 1;
		if (newCapacity - minCapacity < 0)// 扩容后还是不够
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)// 扩容后超越整数上限，设置长度为上限
			newCapacity = hugeCapacity(minCapacity);
		subtree = Arrays.copyOf(subtree, minCapacity);
	}

	/**
	 * 
	 * @param minCapacity
	 *            最小所需容量
	 * @return Integer.MAX_VALUE or MAX_ARRAY_SIZE which shows that
	 *         MAX_ARRAY_SIZE does not yet have a real use.
	 */
	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0)// overflow
			throw new OutOfMemoryError("Error minCapacity: " + minCapacity);
		// 表示还没出现保留head words在array中的虚拟机，才会返回Integer.MAX_VALUE
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	/** 返回此列表中的元素的个数 */
	public int size() {
		return size;
	}

	/** 如果此列表中没有元素，则返回 true */
	public boolean isEmpty() {
		return size == 0;
	}

	/** 如果此列表中包含指定的元素，则返回 true。 */
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	/**
	 * 返回指定对象在Tree中第一个位置， 找不到指定对象返回-1。<br>
	 * 传入null会返回Tree中第一个null的位置。<br>
	 * 
	 * @param o
	 *            指定对象
	 * @return 位置或-1
	 */
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i].item == null)
					return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (o.equals(elementData[i].item))
					return i;
			}
		}
		return -1;
	}

	/**
	 * 返回指定对象在Tree中最后一个位置， 找不到指定对象返回-1。<br>
	 * 传入null会返回Tree中最后一个null的位置。<br>
	 * 
	 * @param o
	 *            指定对象
	 * @return 位置或-1
	 */
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i].item == null)
					return i;
			}
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (o.equals(elementData[i].item))
					return i;
			}
		}
		return -1;
	}

	/**
	 * 拷贝此数组并回传副本
	 * 
	 * @return 副本
	 */
	public Object clone() {
		return null;
	}

	/**
	 * 回传一个array，包含此Tree的元素并且正确的被排列。
	 * 
	 * @return A new array (In other words, this method must allocate a new
	 *         array)
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = elementData[i].item;
		}
		return array;

		// return Arrays.copyOf(elementData, size);
	}

	/**
	 * 将当前ArrayList转换成与传入的T类型相同的数组<br>
	 * 当传入的a的length小于ArrayList的size的时候、方法内部会生成一个新的T[ ]返回<br>
	 * 如果传入的T[]的length大于ArrayList的size、则T[]从下标size开始到最后的元素都自动用null填充。
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
			return (T[]) Arrays.copyOf(elementData, a.length, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	/**
	 * 获取Tree中索引为index位置的元素
	 * 
	 * @param index
	 *            索引
	 * @return 元素
	 * @throws IndexOutOfBoundsException
	 */
	public E get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		return elementData[index].item;
	}

	/**
	 * 加入指定元素到树最后（成为上一个节点的兄弟）<br>
	 * 若树为空则成为根节点，若树仅有根节点则抛出异常（根节点没有兄弟）
	 * 
	 * @param element
	 *            指定元素
	 */
	public void addSib(E element) {
		if (size < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(size));
		else if (size == 1)
			throw new IllegalArgumentException("You can't insert siblings at root.");
		linkLastSib(element);
	}

	/**
	 * 插入指定元素到指定位置（成为原节点的兄弟）<br>
	 * 输入0则抛出异常（根节点没有兄弟）
	 * 
	 * @param element
	 *            指定元素
	 * @throws IndexOutOfBoundsException
	 */
	public void addSib(int index, E element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		else if (index == 1)
			throw new IllegalArgumentException("You can't insert siblings at root.");
		else {
			ensureCapacity(size + 1);// Increments modCount
			System.arraycopy(elementData, index, elementData, index + 1, size - index);
			elementData[index].item = element;
			elementData[index].children = null;
			size++;
		}
	}

	/**
	 * 设置根节点
	 * 
	 * @param element
	 */
	public void setRoot(E element) {
		if (size == 0)
			addLeaf(element);
		else
			elementData[0].item = element;
	}

	/**
	 * 加入指定元素成為成为最后节点的子节点，若树为空则创建root。
	 * 
	 * @param element
	 *            指定元素
	 */
	public void addLeaf(E element) {
		if (size < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(size));
		else
			linkLastLeaf(element);
	}

	/**
	 * 加入指定元素成為成为指定节点的子节点
	 * 
	 * @param index
	 *            父节点索引
	 * @param element
	 *            指定元素
	 * @throws IndexOutOfBoundsException
	 */
	public void addLeaf(int index, E element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		else {

		}
	}

	/**
	 * 
	 * 原本最后Node (data; parent(x); children(Node<E>[] args...)) 后加入新Node(data;
	 * parent(x); children(null))。
	 * 
	 * @param element
	 */
	private void linkLastSib(E element) {
		Node<E> newlast = new Node<>(null, element, null);// while size==0
		if (size > 0) {
			Node<E> preLast = elementData[size - 1];
			newlast = new Node<>(preLast.parent, element, null);
		}
		ensureCapacity(size + 1);// Increments modCount
		elementData[size++] = newlast;
	}

	private void linkLastLeaf(E element) {
		ensureCapacity(size + 1);// Increments modCount
		if (size == 0) {
			Node<E> root = new Node<>(null, element, null);
			elementData[size++] = root;
		} else {
			Node<E> parentNode = elementData[size - 1];
			Node<E>[] cNode = parentNode.children;

			if (cNode == null)
				cNode = new Node[1];
			else
				ensureSubtreeCapacity(cNode, (cNode.length + 1));// Increments
																	// modCount
			Node<E> leaf = new Node<>(parentNode, element, null);
			cNode[cNode.length - 1] = leaf;
			elementData[size++] = leaf;
		}
	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E replace(int index, E value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int search(E value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRoot(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeaf(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E getParent(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E[] getChildren(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel(int key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toString() {
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append("index\titem\tparent\tchildren\n");
		for (int i = 0; i < size; i++) {
			Node<E> node = elementData[i];

			sbBuilder.append(i + ":\t" + node.item);
			if (node.parent == null)
				sbBuilder.append("\tnull");
			else
				sbBuilder.append("\t" + node.parent.item);
			if (node.children != null) {
				sbBuilder.append("\t[");
				for (Node<E> n : node.children)
					sbBuilder.append(n.item + ", ");
				sbBuilder.append("]");
			}
			sbBuilder.append("\n");
		}
		return sbBuilder.toString();
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
	 * Constructs an IndexOutOfBoundsException detail message. Of the many
	 * possible refactorings of the error handling code, this "outlining"
	 * performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}
}
