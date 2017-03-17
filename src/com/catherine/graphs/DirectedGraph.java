package com.catherine.graphs;

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
public class DirectedGraph {

	public static class Vertex<E> {
		E data;
		int indegree;
		int outdegree;

		// 遍历算法
		Status status;// 预设是UNDISCOVERED
		int dTime;// 顶点被发现的时间
		int fTime;// 顶点被访问完的时间
		int parent;// 记录顶点的父节点
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
			parent = -1;
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

		public Edge(E data, int weight) {
			this.data = data;
			this.weight = weight;
		}
	}
}
