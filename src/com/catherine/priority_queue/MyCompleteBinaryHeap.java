package com.catherine.priority_queue;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Stack;

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
			priorityQueueVectorImpl.add(e);
			percolateUp(e);
		} else {
			priorityQueueBinTreeImpl.add(e);
			percolateUp(e);
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
	public void percolateDown(T n, T i) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		priorityQueueVectorImpl.percolateDown(n, i);
	}

	@Override
	public void percolateUp(T i) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
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

	public T getParent(T e) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.getParent(e);
		} else {
			return priorityQueueBinTreeImpl.search(e).getParent().getData();
		}
	}

	public T getLChild(T e) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.getLChild(e);
		} else {
			return priorityQueueBinTreeImpl.search(e).getlChild().getData();
		}
	}

	public T getRChild(T e) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.getRChild(e);
		} else {
			return priorityQueueBinTreeImpl.search(e).getrChild().getData();
		}
	}

	public void printTree() {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.printTree();
		} else {
			priorityQueueBinTreeImpl.traverseLevel();
		}
	}

	public int size() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.size();
		} else {
			return priorityQueueBinTreeImpl.size();
		}
	}

	public synchronized void copyInto(Object[] anArray) {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.copyInto(anArray);
		} else {
			// TODO
		}
	}

	public synchronized void trimToSize() {
		if (structure == Structure.VECTOR) {
			priorityQueueVectorImpl.trimToSize();
		} else {
			// TODO
		}
	}

	public synchronized boolean isEmpty() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.isEmpty();
		} else {
			return priorityQueueBinTreeImpl.isEmpty();
		}
	}

	public int indexOf(Object o) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.indexOf(o);
		} else {
			return -1;
		}
	}

	public synchronized int indexOf(Object o, int index) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.indexOf(o, index);
		} else {
			return -1;
		}
	}

	public synchronized int lastIndexOf(Object o) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastIndexOf(o);
		} else {
			return -1;
		}
	}

	public synchronized int lastIndexOf(Object o, int index) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastIndexOf(o, index);
		} else {
			return -1;
		}
	}

	public synchronized T elementAt(int index) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("VECTOR Structure only");
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.elementAt(index);
		} else {
			return null;
		}
	}

	public synchronized T firstElement() {
		return getMax();
	}

	public synchronized T lastElement() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.lastElement();
		} else {
			// TODO
			return null;
		}
	}

	public synchronized Object clone() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.clone();
		} else {
			// TODO
			return null;
		}
	}

	public synchronized Object[] toArray() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toArray();
		} else {
			// TODO
			return null;
		}
	}

	public synchronized T[] toArray(T[] a) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toArray(a);
		} else {
			// TODO
			return null;
		}
	}

	public synchronized T get(int index) {
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

	public synchronized String toString() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.toString();
		} else {
			return priorityQueueBinTreeImpl.toString();
		}
	}

	public synchronized List<T> subList(int fromIndex, int toIndex) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.subList(fromIndex, toIndex);
		} else {
			// TODO
			return null;
			// return priorityQueueBinTreeImpl.subList(fromIndex, toIndex);
		}
	}

	public synchronized ListIterator<T> listIterator(int index) {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.listIterator(index);
		} else {
			// TODO
			return null;
			// return priorityQueueBinTreeImpl.listIterator(index);
		}
	}

	public synchronized ListIterator<T> listIterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.listIterator();
		} else {
			// TODO
			return null;
			// return priorityQueueBinTreeImpl.listIterator();
		}
	}

	public synchronized Iterator<T> iterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.iterator();
		} else {
			// TODO
			return null;
			// return priorityQueueBinTreeImpl.iterator();
		}
	}

	public Spliterator<T> spliterator() {
		if (structure == Structure.VECTOR) {
			return priorityQueueVectorImpl.spliterator();
		} else {
			// TODO
			return null;
			// return priorityQueueBinTreeImpl.spliterator();
		}
	}

	@Override
	public void percolateDown(Node<T> n, Node<T> i) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.percolateDown(n, i);
	}

	@Override
	public void percolateUp(Node<T> i) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.percolateUp(i);
	}

	@Override
	public void merge(PriorityQueueBinTree<T> heap) {
		// if (structure != Structure.VECTOR)
		// throw new IllegalAccessException("BINARY_TREE Structure only");
		priorityQueueBinTreeImpl.merge(heap);
	}
}
