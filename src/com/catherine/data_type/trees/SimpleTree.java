package com.catherine.data_type.trees;

import java.io.Serializable;

/**
 * 定义tree的节点时包含了三个元素（索引、值、父节点的索引）。<br>
 * @author Catherine
 *
 * @param <E>
 */
public class SimpleTree<E> extends AbstractTree<E> implements Cloneable, Serializable {

	private static final long serialVersionUID = 5469151515081509859L;
	private int size = 0;
	private int modCount = 0;
	transient Node<E> root;
	transient Node<E> last;
	
	public SimpleTree() {

	}
	

	private static class Node<E> {
		E item;
		Node<E> parent;
		Node<E>[] children;

		Node(Node<E> parent,E item, Node<E>[] children) {
			this.item = item;
			this.parent = parent;
			this.children = children;
		}
	}
	
	/**
	 * 加入指定元素到树最后（成为上一个节点的兄弟）
	 * 
	 * @param element
	 *            指定元素
	 */
	public void addSib(E element) {
		linkLastSib(element);
	}
    
    public boolean add(E e) {
    	linkLastSib(e);
        return true;
    }


	@Override
	public void add(int index, E value) {
        checkPositionIndex(index);
		
        

		size++;
		modCount++;
	}
    
    
    /**
	 * 
	 * 原本最后Node (data; parent(x); children(Node<E>[] args...)) 后加入新Node(data; parent(x); children(null))。
	 * 
	 * @param element
	 */
	private void linkLastSib(E element) {
		Node<E> preLast = last;
		Node<E> newlast = new Node<>(preLast.parent, element, null);
		last = newlast;

		if (preLast == null)
			root = newlast;

		size++;
		modCount++;
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
	
	public String toString(){
		StringBuilder sbBuilder = new StringBuilder();
		Node<E>[] temp = root.children;
		int index=0;
		if(temp.length!=0){
		for(Node<E> n:temp){
			sbBuilder.append(index+"\t"+(String)root.item+"children["+(++index)+", ");
		}
		sbBuilder.append("]\n");
		
		for()
		}
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
