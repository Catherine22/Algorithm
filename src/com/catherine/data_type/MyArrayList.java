package com.catherine.data_type;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<E> extends AbstractList<E> implements List<E> {

	private Object[] elementData;
	private int size;

	public MyArrayList(int capacity) {
		super();
		if (capacity < 0)
			throw new IllegalArgumentException(
					"Capacity should be filled in more than 0! Illegal capacity: " + capacity);
		else
			elementData = new Object[capacity];
	}

	/**
	 * Default capacity = 10
	 */
	public MyArrayList() {
		this(10);
	}

	/**
	 * 将此ArrayList实例的长度改成当前列表长度
	 */
	public void trimToSize() {
		modCount++; // 此ArrayList全部被修改次数
		int oldCapacity = elementData.length;
		if (size < oldCapacity)
			elementData = Arrays.copyOf(elementData, size);
	}

	/**
	 * 确保此ArrayList的最小容量能容纳下参数minCapacity指定的容量，<br>
	 * 当minCapacity大于原容量时须扩容，首先增加原本的一半，<br>
	 * 万一增加后还是小于minCapacity就直接那minCapacity当新容量。<br>
	 * <br>
	 * 之所以阔一半是因为加0.5倍式扩容虽然牺牲内存空间，空间利用率(已使用/全部空间)至少大于75%，但在运行速度上远超越递增式扩容。<br>
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
	 * 
	 * @param minCapacity 最小所需容量
	 * @return Integer.MAX_VALUE or MAX_ARRAY_SIZE which shows that
	 *         MAX_ARRAY_SIZE does not yet have a real use.
	 */
	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0)// overflow
			throw new OutOfMemoryError("Error minCapacity: " + minCapacity);
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	/** 返回此列表中的元素的个数 */
	@Override
	public int size() {
		return size;
	}

	/** 如果此列表中没有元素，则返回 true */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/** 如果此列表中包含指定的元素，则返回 true。 */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub

	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
