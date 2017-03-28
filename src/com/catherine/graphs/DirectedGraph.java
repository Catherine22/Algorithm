package com.catherine.graphs;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;

import com.catherine.data_type.trees.MyBinaryTree.Node;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

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
	private Vertex<E>[] vertexes;
	/**
	 * 邻接关系矩阵，只要不是null代表有边
	 */
	private Edge<E>[][] adjMatrix;
	private int size;
	private int edgeCount;

	public DirectedGraph() {
		this(10);
	}

	public DirectedGraph(int vertexNum) {
		super();
		if (vertexNum <= 0)
			throw new IllegalArgumentException("Vertex number should be more than 0! Illegal capacity: " + vertexNum);
		else {
			vertexes = new Vertex[vertexNum];
			adjMatrix = new Edge[vertexNum][vertexNum];
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

		public String toString() {
			String statusString = "UNDISCOVERED";
			if (status == Status.DISCOVERED)
				statusString = "DISCOVERED";
			else if (status == Status.VISITED)
				statusString = "VISITED";

			if (data == null)
				return String.format(
						"{\"data\": \"%s\", \"indegree\": %d, \"outdegree\": %d, \"status\": \"%s\", \"dTime\": %d, \"fTime\": %d, \"priority\": %d}",
						"Null data", indegree, outdegree, statusString, dTime, fTime, priority);

			return String.format(
					"{\"data\": \"%s\", \"indegree\": %d, \"outdegree\": %d, \"status\": \"%s\", \"dTime\": %d, \"fTime\": %d, \"priority\": %d}",
					data, indegree, outdegree, statusString, dTime, fTime, priority);
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

		public String toString() {
			String statusString = "UNDETERMINED";
			if (status == Status.TREE)
				statusString = "TREE";
			else if (status == Status.CROSS)
				statusString = "CROSS";
			else if (status == Status.FORWARD)
				statusString = "FORWARD";
			else if (status == Status.BACKWARD)
				statusString = "BACKWARD";

			if (data == null)
				return String.format("{\"data\": \"%s\", \"weight\": %d, \"status\": \"%s\"}", "Null data", weight,
						statusString);

			return String.format("{\"data\": \"%s\", \"weight\": %d, \"status\": \"%s\"}", data, weight, statusString);
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
		vertexes[size++] = v;
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
		ensureCapacity(size + 1);// Increments modCount
		System.arraycopy(vertexes, index, vertexes, index + 1, size - index);
		Vertex<E> v = new Vertex<>(data);
		vertexes[index] = v;
		size++;
		return v;
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
			vertexes[index] = v;
		} else
			vertexes[index].data = data;

		return vertexes[index];
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
		addEdge(begin, target, null);
	}

	/**
	 * 邻接两顶点
	 * 
	 * @param begin
	 *            起始顶点
	 * @param target
	 *            结束顶点
	 * @param data
	 *            边的数据
	 */
	public void addEdge(Vertex<E> begin, Vertex<E> target, E data) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");
		if (isVertexNull(target))
			throw new NullPointerException("null target vertex!");
		if (begin == target)
			throw new IllegalArgumentException("You can't make a vertex point to itself.");

		// 如果已经建立链接则忽略
		if (adjMatrix[indexOf(begin)][indexOf(target)] == null) {
			Edge<E> edge = new Edge<>(data);
			adjMatrix[indexOf(begin)][indexOf(target)] = edge;
			edgeCount++;

			begin.outdegree++;
			target.indegree++;
		}
	}
	
	/**
	 * 移除边
	 * @param begin
	 *            起始顶点
	 * @param target
	 *            结束顶点
	 */
	public void removeEdge(Vertex<E> begin, Vertex<E> target){
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");
		if (isVertexNull(target))
			throw new NullPointerException("null target vertex!");
		if (begin == target)
			throw new IllegalArgumentException("You can't make a vertex point to itself.");
		
		// 如果已经建立链接则忽略
		if (adjMatrix[indexOf(begin)][indexOf(target)] != null) {
			adjMatrix[indexOf(begin)][indexOf(target)] = null;
			edgeCount--;

			begin.outdegree--;
			target.indegree--;
		}
		
	}

	/**
	 * 检查两顶点间是否有边存在
	 * 
	 * @param begin
	 *            起始顶点
	 * @param target
	 *            结束顶点
	 * @return 是／否
	 */
	public boolean existEage(Vertex<E> begin, Vertex<E> target) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");
		if (isVertexNull(target))
			throw new NullPointerException("null target vertex!");
		if (begin == target)
			throw new IllegalArgumentException("You can't make a vertex point to itself.");

		return adjMatrix[indexOf(begin)][indexOf(target)] != null;
	}

	/**
	 * 检查两顶点间是否有边存在
	 * 
	 * @param begin
	 *            起始顶点
	 * @param target
	 *            结束顶点
	 * @return 边／null
	 */
	public Edge<E> getEage(Vertex<E> begin, Vertex<E> target) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");
		if (isVertexNull(target))
			throw new NullPointerException("null target vertex!");
		if (begin == target)
			throw new IllegalArgumentException("You can't make a vertex point to itself.");

		return adjMatrix[indexOf(begin)][indexOf(target)];
	}

	/**
	 * 取得第n个邻边
	 * 
	 * @param v
	 *            被检查的节点
	 * @param number
	 *            第n个
	 * @return 邻边或是null表示没有邻边
	 */
	public Vertex<E> nextNbr(Vertex<E> v, int number) {
		// if (isVertexNull(v))
		// throw new NullPointerException("null begin vertex!");
		//
		// if (number < 1 || number > size - 1)
		// throw new IndexOutOfBoundsException("number should be less then
		// size-1 and more then 0.");

		for (int i = 0; i < size; i++) {
			if (adjMatrix[indexOf(v)][i] != null) {
				number--;
				if (number == 0)
					return vertexes[i];
			}
		}
		return null;
	}

	/**
	 * 取得邻边
	 * 
	 * @param v
	 *            被检查的节点
	 * @return 邻边或是null表示没有邻边
	 */
	public Vertex<E> firstNbr(Vertex<E> v) {
		return nextNbr(v, 1);
	}

	/**
	 * 广度优先搜索，做法类似二叉树的阶层走访<br>
	 * 当一顶点所有对外链接的顶点（邻居）都找出来后，状态变成VISITED<br>
	 * 访问过的边要变成TREE的状态。
	 * 
	 * @param begin
	 */
	public void bfs(Vertex<E> begin) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");

		System.out.println("started at " + unixToDataString(getUnixtime()));

		Queue<Vertex<E>> parent = new LinkedList<>();
		Queue<Vertex<E>> siblings = new LinkedList<>();
		Vertex<E> vertex = begin;

		int level = 0;
		parent.offer(vertex);
		while (vertex != null || !parent.isEmpty()) {
			System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				vertex = parent.poll();
				if (vertex.status != Vertex.Status.VISITED) {
					vertex.dTime = getUnixtime();
					vertex.status = Vertex.Status.DISCOVERED;

					System.out.print(vertex.data + " ");

					if (vertex.outdegree > 0) {
						int sibHeader = 1;
						Vertex<E> sibV = nextNbr(vertex, sibHeader++);
						while (sibV != null) {
							siblings.offer(sibV);
							sibV = nextNbr(vertex, sibHeader++);
						}
					}
					vertex.status = Vertex.Status.VISITED;
					vertex.fTime = getUnixtime();
				}
			}

			for (Vertex<E> v : siblings)
				parent.offer(v);
			siblings.clear();
			vertex = null;
			System.out.print("\n");
		}

		System.out.println("finished at " + unixToDataString(getUnixtime()));
	}

	/**
	 * 清除bfs过后的数值<br>
	 * 其实就是再做一次bfs，但是把status回归UNDISCOVERED，dTime和fTime回复-1（预设值）<br>
	 * 访问过的边要变成UNDETERMINED的状态。
	 * 
	 * 
	 * @param begin
	 */
	public void deBfs(Vertex<E> begin) {
		if (isVertexNull(begin))
			throw new NullPointerException("null begin vertex!");

		// System.out.println("started at " + unixToDataString(getUnixtime()));

		Queue<Vertex<E>> parent = new LinkedList<>();
		Queue<Vertex<E>> siblings = new LinkedList<>();
		Vertex<E> vertex = begin;

		// int level = 0;
		parent.offer(vertex);
		while (vertex != null || !parent.isEmpty()) {
			// System.out.print("level " + level++ + ",\t");

			while (!parent.isEmpty()) {
				vertex = parent.poll();
				if (vertex.status != Vertex.Status.UNDISCOVERED) {
					vertex.dTime = -1;
					vertex.status = Vertex.Status.DISCOVERED;
					System.out.print(vertex.data + " ");
					if (vertex.outdegree > 0) {
						int sibHeader = 1;
						Vertex<E> sibV = nextNbr(vertex, sibHeader++);
						while (sibV != null) {
							siblings.offer(sibV);
							sibV = nextNbr(vertex, sibHeader++);
						}
					}
					vertex.status = Vertex.Status.UNDISCOVERED;
					vertex.fTime = -1;
				}
			}

			for (Vertex<E> v : siblings)
				parent.offer(v);
			siblings.clear();
			vertex = null;
			// System.out.print("\n");
		}

		// System.out.println("finished at " + unixToDataString(getUnixtime()));
	}

	private int getUnixtime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}

	private String unixToDataString(int unixtime) {
		Date date = new Date(unixtime * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		return sdf.format(date);
	}

	public int indexOf(Vertex<E> v) {
		for (int i = 0; i < size; i++) {
			if (vertexes[i] == v)
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
		if (minCapacity - vertexes.length > 0)
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
		int oldCapacity = vertexes.length;
		int newCapacity = oldCapacity + oldCapacity >> 1;
		if (newCapacity - minCapacity < 0)// 扩容后还是不够
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)// 扩容后超越整数上限，设置长度为上限
			newCapacity = hugeCapacity(minCapacity);
		vertexes = Arrays.copyOf(vertexes, newCapacity);
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

	/** 返回此列表中的边的个数 */
	public int edgeCount() {
		return edgeCount;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("Edges\t");
		for (int i = 0; i < size; i++) {
			sBuilder.append(vertexes[i].data + "\t");
		}
		sBuilder.append("\n");
		for (int i = 0; i < size; i++) {
			sBuilder.append(vertexes[i].data + "\t");
			for (int j = 0; j < size; j++) {
				if (adjMatrix[i][j] == null)
					sBuilder.append("X" + "\t");
				else
					sBuilder.append("O"/* adjMatrix[i][j].data */ + "\t");
			}
			sBuilder.append("\n");
		}
		return sBuilder.toString();
	}
}
