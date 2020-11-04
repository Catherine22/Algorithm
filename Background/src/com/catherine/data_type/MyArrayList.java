package com.catherine.data_type;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

//隐藏，不可见
//import java.util.ArrayList.Itr;
//import java.util.ArrayList.ListItr;
//import java.util.ArrayList.SubList;
/**
 * 因为有索引的概念，查找比linkedList快非常多，不用遍历。<br>
 * 当数据多、须大量随机查找时用ArrayList。<br>
 * 
 * @author Catherine
 *
 * @param <E>
 */
public class MyArrayList<E> extends AbstractList<E> implements List<E>, Cloneable, Serializable {

	// 序列版本号
	private static final long serialVersionUID = -973019176608242355L;
	// transient 短暂的，宣告后如有做序列化的动作时，加入此关键字就不会被序列化。
	private transient Object[] elementData;
	private int size;

	public MyArrayList(int capacity) {
		super();
		if (capacity <= 0)
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
		elementData = Arrays.copyOf(elementData, newCapacity);
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
	 * 返回指定对象在ArrayList中第一个位置， 找不到指定对象返回-1。<br>
	 * 传入null会返回ArrayList中第一个null的位置。<br>
	 * 
	 * @param o
	 *            指定对象
	 * @return 位置或-1
	 */
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null)
					return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (o.equals(elementData[i]))
					return i;
			}
		}
		return -1;
	}

	/**
	 * 返回指定对象在ArrayList中最后一个位置， 找不到指定对象返回-1。<br>
	 * 传入null会返回ArrayList中最后一个null的位置。<br>
	 * 
	 * @param o
	 *            指定对象
	 * @return 位置或-1
	 */
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i] == null)
					return i;
			}
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (o.equals(elementData[i]))
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
		try {
			MyArrayList<?> clone = (MyArrayList<?>) super.clone();
			clone.elementData = Arrays.copyOf(elementData, size);
			clone.modCount = 0;
			return clone;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}

	}

	/**
	 * 回传一个array，包含此ArrayList的元素并且正确的被排列。
	 * 
	 * @return A new array (In other words, this method must allocate a new
	 *         array)
	 */
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
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
	 * 获取ArrayList中索引为index位置的元素
	 * 
	 * @param index
	 *            索引
	 * @return 元素
	 */
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		return (E) elementData[index];
	}

	/**
	 * 设置ArrayList中索引为index位置的元素并返回原本的值
	 * 
	 * @param index
	 *            索引
	 * @param element
	 *            新的元素
	 * @return 原本的元素
	 */
	@SuppressWarnings("unchecked")
	public E set(int index, E element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

		E oldElement = (E) elementData[index];
		elementData[index] = element;
		return oldElement;
	}

	/**
	 * 添加指定元素至ArrayList最后
	 * 
	 * @param e
	 *            指定元素
	 * @return always true
	 */
	public boolean add(E element) {
		ensureCapacity(size + 1);// Increments modCount
		elementData[size++] = element;
		return true;
	}

	/**
	 * 添加指定元素至ArrayList特定位置
	 * 
	 * @param index
	 *            索引
	 * @param element
	 *            指定元素
	 */
	public void add(int index, E element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		ensureCapacity(size + 1);// Increments modCount
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	/**
	 * 有点像add，移除index的元素后，将index后的元素往前移一位，把最后一位设置null，让GC回收
	 * 
	 * @param index
	 *            移除index的元素
	 * @return 被移除的元素
	 */
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		modCount++;

		E oldElement = (E) elementData[index];

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;// clear to let GC do its work
		return oldElement;
	}

	/**
	 * 删除指定索引处的元素，不检查index，不返回被删除的元素。
	 * 
	 * @param index
	 *            索引
	 */
	private void fastRemove(int index) {
		modCount++;

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;// clear to let GC do its work
	}

	/**
	 * 删除指定元素
	 * 
	 * @param o
	 *            指定元素
	 * @return 删除结果
	 */
	public boolean remove(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					fastRemove(i);
					return true;
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (o.equals(elementData[i])) {
					fastRemove(i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 清空ArrayList
	 */
	public void clear() {
		modCount++;
		// clear to let GC do its work
		for (int i = 0; i < size; i++) {
			elementData[i] = null;
		}
		size = 0;
	}

	/**
	 * 加入指定元素到ArrayList（从ArrayList最后面开始加）
	 * 
	 * @param c
	 *            集合
	 * @return 结果
	 */
	public boolean addAll(Collection<? extends E> c) {
		Object[] objects = c.toArray();
		int collectionSize = objects.length;
		ensureCapacity(size + collectionSize); // Increments modCount
		System.arraycopy(objects, 0, elementData, size, collectionSize);
		size += collectionSize;
		return collectionSize != 0;
	}

	/**
	 * 加入指定元素到ArrayList（从index开始加）
	 * 
	 * @param index
	 *            索引
	 * @param c
	 *            集合
	 * @return 结果
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

		Object[] objects = c.toArray();
		int collectionSize = objects.length;
		ensureCapacity(size + collectionSize); // Increments modCount

		// 先让ArrayList扩容
		int increasedSize = size - index;
		if (increasedSize > 0)
			System.arraycopy(elementData, index, elementData, index + collectionSize, increasedSize);

		System.arraycopy(objects, 0, elementData, index, collectionSize);
		size += collectionSize;
		return collectionSize != 0;
	}

	/**
	 * 移除列表中索引在 fromIndex（包括）和 toIndex（不包括）之间的所有元素。 <br>
	 * <br>
	 * 1、将Object[] 从toIdnex开始之后的元素（包括toIndex处的元素）移到Object[]下标从fromIndex开始之后的位置
	 * <br>
	 * 2、若有Object[]尾部要有剩余的位置则用null填充 <br>
	 * 
	 * @param fromIndex
	 *            起始位置（包括）
	 * @param toIndex
	 *            结束位置（不包括）
	 */
	protected void removeRange(int fromIndex, int toIndex) {
		if (fromIndex >= size || fromIndex < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(fromIndex));
		if (toIndex >= size || toIndex < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(toIndex));
		if (toIndex < fromIndex)
			throw new IndexOutOfBoundsException(String.format("结束位置 %s 应大于起始位置 %s ", toIndex, fromIndex));

		modCount++;
		int decreasedSize = toIndex - fromIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, decreasedSize);

		// clear to let GC do its work
		int newSize = size - decreasedSize;
		for (int i = newSize; i < size; i++)
			elementData[i] = null;

		size = newSize;
	}

	/**
	 * 移除ArrayList中所有c包含的元素
	 * 
	 * @param c
	 *            指定集合
	 * @return 结果
	 * @throws ClassCastException
	 *             if the class of an element of this list is incompatible with
	 *             the specified collection (
	 *             <a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if this list contains a null element and the specified
	 *             collection does not permit null elements (
	 *             <a href="Collection.html#optional-restrictions">optional</a>
	 *             ), or if the specified collection is null
	 */
	public boolean removeAll(Collection<?> c) {
		return batchRemove(c, false);
	}

	/**
	 * 保留ArrayList中所有c包含的元素，其他移除。
	 * 
	 * @param c
	 *            指定集合
	 * @return 结果
	 * @throws ClassCastException
	 *             if the class of an element of this list is incompatible with
	 *             the specified collection (
	 *             <a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if this list contains a null element and the specified
	 *             collection does not permit null elements (
	 *             <a href="Collection.html#optional-restrictions">optional</a>
	 *             ), or if the specified collection is null
	 */
	public boolean retainAll(Collection<?> c) {
		return batchRemove(c, true);
	}

	/**
	 * 批量移除元素（指定集合或指定集合除外）
	 * 
	 * @param c
	 *            指定集合
	 * @param complement
	 *            including or excluding
	 * @return ArrayList的长度有没有变化
	 */
	private boolean batchRemove(Collection<?> c, boolean complement) {
		Object[] elementData = this.elementData;
		boolean modified = false;
		int newP = 0;
		int oriP = 0;
		try {
			for (; oriP < size; oriP++) {
				if (c.contains(elementData[oriP]) == complement) // 留下要的
					elementData[newP++] = elementData[oriP];
			}
		} finally {
			// Collection.contains() 可能抛出异常

			if (oriP != size) {
				// 表示有异常造成检查中断，把发生异常处后面的元素全往前搬
				System.arraycopy(elementData, oriP, elementData, newP, size - oriP);
				newP += (size - oriP);
			}

			// 如果总长度有变化（表示有移除元素），照惯例，把新长度到原长度之间的元素都设为null，让GC回收。
			if (newP != size) {
				for (int i = newP; i < size; i++)
					elementData[i] = null;

				modCount += size - newP;
				size = newP;
				modified = true;
			}
		}

		return modified;

	}

	/**
	 * 储存MyArrayList的实例到输出流（也就是持久化），要保证储存过程中数据没被修改。<br>
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
		for (int i = 0; i < size; i++) {
			s.writeObject(elementData[i]);
		}

		if (expectedModCount != modCount) {
			// 表示此数组刚才有做其他操作，已更动
			throw new ConcurrentModificationException();
		}
	}

	/**
	 * 还原被持久化的MyArrayList（要先做过writeObject()）。
	 * 
	 * @param s
	 *            输入流
	 * @throws java.io.IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();

		int len = s.readInt();
		Object[] a = elementData = new Object[len];

		for (int i = 0; i < size; i++) {
			a[i] = s.readObject();
		}
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message. Of the many
	 * possible refactorings of the error handling code, this "outlining"
	 * performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	// @Override
	// public Iterator<E> iterator() {
	// return new Itr();
	// }
	//
	// @Override
	// public ListIterator<E> listIterator() {
	// return new ListItr(0);
	// }
	//
	// @Override
	// public ListIterator<E> listIterator(int index) {
	// if (index >= size || index < 0)
	// throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	// return new ListItr(index);
	// }
	//
	// @Override
	// public List<E> subList(int fromIndex, int toIndex) {
	// if (fromIndex < 0)
	// throw new IndexOutOfBoundsException(outOfBoundsMsg(fromIndex));
	// if (toIndex > size)
	// throw new IndexOutOfBoundsException(outOfBoundsMsg(toIndex));
	// return new SubList(this, 0, fromIndex, toIndex);
	// }

	// 内部类
	// private class Itr implements Iterator<E> {
	//
	// @Override
	// public boolean hasNext() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public E next() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// }
	//
	// private class ListItr implements ListIterator<E> {
	//
	// ListItr(int index) {
	// super();
	// }
	//
	// @Override
	// public boolean hasNext() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public E next() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean hasPrevious() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public E previous() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public int nextIndex() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public int previousIndex() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public void remove() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void set(E e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void add(E e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// }
	//
	// private class SubList extends AbstractList<E> implements RandomAccess {
	//
	// SubList(AbstractList<E> parent, int offset, int fromIndex, int toIndex) {
	// }
	//
	// @Override
	// public E get(int index) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public int size() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// }

}
