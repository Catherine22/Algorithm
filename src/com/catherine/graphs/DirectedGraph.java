package com.catherine.graphs;

import java.util.Arrays;

/**
 * 图包含两要素：顶点+边<br>
 * Adjacency, 邻接关系：两顶点间的关系<br>
 * Incidence, 关联关系：顶点与边的关系<br>
 * <br>
 * 目前不讨论一顶点与自身产生邻接关系（自己连到自己）
 * 
 * @author Catherine
 *
 */
public class DirectedGraph<E> {
	/**
	 * 顶点集合
	 */
	private Vertex<E>[] vertexs;
	/**
	 * 邻接关系矩阵
	 */
	private boolean[][] adjMatrix;
	private int size;

	public DirectedGraph() {
		this(10);
	}

	public DirectedGraph(int vertexNum) {
		super();
		if (vertexNum <= 0)
			throw new IllegalArgumentException("Vertex number should be more than 0! Illegal capacity: " + vertexNum);
		else {
			vertexs = new Vertex[vertexNum];
			adjMatrix = new boolean[vertexNum][vertexNum];
		}
	}

	public static class Vertex<E> {
		E data;
		int indegree;
		int outdegree;

		// 遍历算法
		Status status;// 预设是UNDISCOVERED
		int dTime;// 顶点被发现的时间
		int fTime;// 顶点被访问完的时间
		Vertex<E> parent;// 记录顶点的父节点
		int priority;// 顶点的优先级

		public enum Status {
			UNDISCOVERED, DISCOVERED, VISITED
		}

		public Vertex(E data) {
			this.data = data;
			indegree = 0;
			outdegree = 0;

			status = Status.UNDISCOVERED;
			dTime = -1;
			fTime = -1;
			parent = null;
			priority = Integer.MAX_VALUE;
		}
	}

	public static class Edge<E> {
		E data;
		int weight;// 权重
		Status status;// 预设是UNDETERMINED

		public enum Status {
			UNDETERMINED, TREE, CROSS, FORWARD, BACKWARD
		}

		public Edge(E data) {
			this.data = data;
			this.weight = Integer.MAX_VALUE;
			this.status = Status.UNDETERMINED;
		}

		public Edge(E data, int weight) {
			this.data = data;
			this.weight = weight;
			this.status = Status.UNDETERMINED;
		}
	}

	/**
	 * 建立新顶点
	 * 
	 * @param data
	 *            数值
	 * @return 顶点
	 */
	public Vertex<E> addVertex(E data) {
		Vertex<E> v = new Vertex<>(data);
		ensureCapacity(size + 1);// Increments modCount
		vertexs[size++] = v;
		return v;
	}

	/**
	 * 建立新顶点
	 * 
	 * @param int
	 *            索引
	 * @param data
	 *            数值
	 * @return 顶点
	 */
	public Vertex<E> addVertex(int index, E data) {
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));


		return null;
	}

	/**
	 * 修改顶点值
	 * 
	 * @param int
	 *            索引
	 * @param data
	 *            数值
	 * @return 顶点
	 */
	public Vertex<E> setVertex(int index, E data) {
		if ((index < 0) || (index >= size))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

		if (index == size) {
			Vertex<E> v = new Vertex<>(data);
			ensureCapacity(++size);// Increments modCount
			vertexs[index] = v;
		} else
			vertexs[index].data = data;

		return vertexs[index];
	}

	/**
	 * 邻接两顶点
	 * 
	 * @param begin
	 *            起始顶点
	 * @param target
	 *            结束顶点
	 */
	public void addEdge(Vertex<E> begin, Vertex<E> target) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");
		if (isVertexNull(target))
			throw new NullPointerException("null target vertex!");

		// 如果已经建立链接则忽略
		if (adjMatrix[indexOf(begin)][indexOf(target)] != true) {
			adjMatrix[indexOf(begin)][indexOf(target)] = true;

			begin.outdegree++;
			target.indegree++;
		}
	}

	public int indexOf(Vertex<E> v) {
		for (int i = 0; i < size; i++) {
			if (vertexs[i] == v)
				return i;
		}
		return -1;
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
		if (minCapacity - vertexs.length > 0)
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
		int oldCapacity = vertexs.length;
		int newCapacity = oldCapacity + oldCapacity >> 1;
		if (newCapacity - minCapacity < 0)// 扩容后还是不够
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)// 扩容后超越整数上限，设置长度为上限
			newCapacity = hugeCapacity(minCapacity);
		vertexs = Arrays.copyOf(vertexs, newCapacity);
		adjMatrix = Arrays.copyOf(adjMatrix, newCapacity);
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

	private boolean isVertexNull(Vertex<E> v) {
		return (v == null);
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

	/** 返回此列表中的元素的个数 */
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public Vertex nextNbr() {
		return null;
	}

	public Vertex getFirstNbr() {
		return nextNbr();
	}

	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("*\t");
		for (int i = 0; i < size; i++) {
			sBuilder.append(vertexs[i].data + "\t");
		}
		sBuilder.append("\n");
		for (int i = 0; i < size; i++) {
			sBuilder.append(vertexs[i].data + "\t");
			for (int j = 0; j < size; j++) {
				sBuilder.append(adjMatrix[i][j] + "\t");
			}
			sBuilder.append("\n");
		}
		return sBuilder.toString();
	}
}
