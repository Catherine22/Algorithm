package com.catherine.priority_queue;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import com.catherine.trees.MyBinaryTree.Order;
import com.catherine.trees.nodes.Node;

/**
 * 完全二叉堆（逻辑上等同于完全二叉树，物理上以向量实现）<br>
 * 以层次遍历完全二叉树并对应到向量上。<br>
 * <br>
 * 堆序性：只要i>0，H[i] <= H[parent_of_i] <br>
 * (表示最大H[i]必为根节点，也就是H[0])
 * 
 * @author Catherine
 *
 * @param <T>
 */
public class MyCompleteBinaryHeap<T extends Comparable<? super T>>
		implements PriorityQueueVector<T>, PriorityQueueBinTree<T> {
	private PriorityQueueVectorImpl<T> priorityQueueVectorImpl;
	private PriorityQueueBinTreeImpl<T> priorityQueueBinTreeImpl;
	private Structure structure;

	public enum Structure {
		VECTOR, BINARY_TREE
	}

	public MyCompleteBinaryHeap() {
		this(Structure.VECTOR, null);
	}

	public MyCompleteBinaryHeap(T root) {
		this(Structure.BINARY_TREE, root);
	}

	public MyCompleteBinaryHeap(Structure structure, T root) {
		this.structure = structure;
		if (structure == Structure.VECTOR)
			priorityQueueVectorImpl = new PriorityQueueVectorImpl<>();
		else
			priorityQueueBinTreeImpl = new PriorityQueueBinTreeImpl<>(root);
	}

	public PriorityQueueVector<T> getPriorityQueueVector() {
		return priorityQueueVectorImpl;
	}

	public PriorityQueueBinTree<T> getPriorityQueueBinTree() {
		return priorityQueueBinTreeImpl;
	}

	@Override
	public void insert(T e) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.insert(e);
		} else {
			priorityQueueBinTreeImpl.insert(e);
		}
	}

	@Override
	public T getMax() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.getMax();
		} else {
			return priorityQueueBinTreeImpl.getMax();
		}
	}

	@Override
	public T delMax() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.delMax();
		} else {
			return priorityQueueBinTreeImpl.delMax();
		}
	}

	@Override
	public void percolateDown(T n, T i) throws IllegalAccessException {
		if (structure != Structure.VECTOR)
			throw new IllegalAccessException("VECTOR Structure only");
		priorityQueueVectorImpl.percolateDown(n, i);
	}

	@Override
	public void percolateUp(T i) throws IllegalAccessException {
		if (structure != Structure.VECTOR)
			throw new IllegalAccessException("VECTOR Structure only");
		priorityQueueVectorImpl.percolateUp(i);
	}

	@Override
	@Deprecated
	public void completedlyHeapify(T[] array) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.heapify(array);
		} else {
			priorityQueueBinTreeImpl.heapify(array);
		}
	}

	@Override
	@Deprecated
	public void completedlyHeapify(List<T> list) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.heapify(list);
		} else {
			priorityQueueBinTreeImpl.heapify(list);
		}
	}

	@Override
	public void heapify(T[] array) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.heapify(array);
		} else {
			priorityQueueBinTreeImpl.heapify(array);
		}
	}

	@Override
	public void heapify(List<T> list) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.heapify(list);
		} else {
			priorityQueueBinTreeImpl.heapify(list);
		}
	}

	@Override
	public void merge(PriorityQueueVector<T> heap) {
		priorityQueueVectorImpl.merge(heap);
	}

	public void printTree() {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.printTree();
		} else {
			priorityQueueBinTreeImpl.printTree();
		}
	}

	public int size() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.size();
		} else {
			return priorityQueueBinTreeImpl.size();
		}
	}

	public void copyInto(Object[] anArray) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.copyInto(anArray);
		} else {
			priorityQueueBinTreeImpl.copyInto(anArray);
		}
	}

	public synchronized void trimToSize() throws IllegalAccessException {
		if (structure != Structure.VECTOR)
			throw new IllegalAccessException("VECTOR Structure only");
		priorityQueueVectorImpl.trimToSize();

	}

	public boolean isEmpty() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.isEmpty();
		} else {
			return priorityQueueBinTreeImpl.isEmpty();
		}
	}

	public int indexOf(Object o) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.indexOf(o);
		} else {
			return indexOf(o, priorityQueueBinTreeImpl.size());
		}
	}

	public int indexOf(Object o, int index) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.indexOf(o, index);
		} else {
			Object[] anArray = new Object[priorityQueueBinTreeImpl.size()];
			priorityQueueBinTreeImpl.copyInto(anArray);
			while (index >= 0) {
				if (anArray[index] == o)
					break;
				else
					index--;
			}
			return index;
		}
	}

	public int lastIndexOf(Object o) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastIndexOf(o);
		} else {
			return lastIndexOf(o, 0);
		}
	}

	public int lastIndexOf(Object o, int index) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastIndexOf(o, index);
		} else {
			Object[] anArray = new Object[priorityQueueBinTreeImpl.size()];
			priorityQueueBinTreeImpl.copyInto(anArray);
			int head = priorityQueueBinTreeImpl.size();
			while (head >= index) {
				if (anArray[head] == o)
					break;
				else
					index--;
			}
			return (head < index) ? -1 : head;
		}
	}

	public Object elementAt(int index) {
		if (index < 0 || index >= size())
			throw new ArrayIndexOutOfBoundsException("Index must be from 0 to size-1");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.elementAt(index);
		} else {
			Object[] anArray = new Object[priorityQueueBinTreeImpl.size()];
			priorityQueueBinTreeImpl.copyInto(anArray);
			return anArray[index];
		}
	}

	public synchronized T firstElement() {
		return getMax();
	}

	public synchronized T lastElement() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastElement();
		} else {
			List<T> list = priorityQueueBinTreeImpl.subList(0, priorityQueueBinTreeImpl.size());
			for (int i = 0; i < list.size() - 1; i++) {
				if (list.get(i).compareTo(list.get(i + 1)) > 0)
					list.set(0, list.get(i + 1));
			}
			return list.get(0);
		}
	}

	public synchronized Object clone() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.clone();
		} else {
			return priorityQueueBinTreeImpl.clone();
		}
	}

	public Object[] toArray() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toArray();
		} else {
			return priorityQueueBinTreeImpl.toArray();
		}
	}

	public T[] toArray(T[] a) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toArray(a);
		} else {
			return priorityQueueBinTreeImpl.toArray(a);
		}
	}

	public T get(int index) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.get(index);
		} else {
			return priorityQueueBinTreeImpl.get(index);
		}
	}

	public void clear() {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.clear();
		} else {
			priorityQueueBinTreeImpl.clear();
		}
	}

	public String toString() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toString();
		} else {
			return priorityQueueBinTreeImpl.toString();
		}
	}

	public List<T> subList(int fromIndex, int toIndex) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.subList(fromIndex, toIndex);
		} else {
			return priorityQueueBinTreeImpl.subList(fromIndex, toIndex);
		}
	}

	public ListIterator<T> listIterator(int index) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.listIterator(index);
		} else {
			return priorityQueueBinTreeImpl.listIterator(index);
		}
	}

	public ListIterator<T> listIterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.listIterator();
		} else {
			return priorityQueueBinTreeImpl.listIterator();
		}
	}

	public Iterator<T> iterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.iterator();
		} else {
			return priorityQueueBinTreeImpl.iterator();
		}
	}

	public Spliterator<T> spliterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.spliterator();
		} else {
			return priorityQueueBinTreeImpl.spliterator();
		}
	}

	@Override
	public void percolateDown(Node<T> n, Node<T> i) throws IllegalAccessException {
		if (structure != Structure.BINARY_TREE)
			throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.percolateDown(n, i);
	}

	@Override
	public void percolateUp(Node<T> i) throws IllegalAccessException {
		if (structure != Structure.BINARY_TREE)
			throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.percolateUp(i);
	}

	@Override
	public void merge(PriorityQueueBinTree<T> heap) throws IllegalAccessException {
		if (structure != Structure.BINARY_TREE)
			throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.merge(heap);
	}
}
