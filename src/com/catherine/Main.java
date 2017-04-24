package com.catherine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.catherine.data_type.MyArrayList;
import com.catherine.data_type.MyLinkedList;
import com.catherine.data_type.Operator;
import com.catherine.data_type.Search;
import com.catherine.data_type.Sequence;
import com.catherine.graphs.DirectedGraph;
import com.catherine.graphs.DirectedGraph.Vertex;
import com.catherine.graphs.trees.Callback;
import com.catherine.graphs.trees.MyAVLTree;
import com.catherine.graphs.trees.MyBinarySearchTree;
import com.catherine.graphs.trees.MyBinaryTree;
import com.catherine.graphs.trees.nodes.Node;
import com.catherine.sort.BubbleSort;
import com.catherine.sort.InsertionSort;
import com.catherine.sort.MergeSort;
import com.catherine.sort.SelectionSort;
import com.catherine.sort.SortableStackPermutation;
import com.catherine.turing_machine.TuringMachine;
import com.catherine.utils.Analysis;
import com.catherine.utils.NumberSystem;
import com.catherine.utils.Others;
import com.catherine.utils.TrackLog;

public class Main {

	private static int[] input1 = new int[] { 3, 5, 7, 1, 4, 2, 10, 4, -10, 3, 5, 7, 1, 4, 2, 10, 4, -10, 3, 5, 7, 1, 4,
			2, 10, 4, -10, 3, 5, 7, 1, 4, 2, 10, 4, -10 };
	private static int[] input2 = new int[] { 38, 29, 28, 11, 4, 5, 2 };
	private static int[] input3 = new int[] { 5, 11, 13, 15, 28, 29, 38 };
	private static int[] input4 = null;
	private static int[] input5 = new int[] { 1 };
	private static int[] input6 = new int[] { 1, 4, 1, 1, 7, 3, 64, 5, 23, 12, 14, 10 };
	private static int[] input7 = new int[] { 23, 24, 25, 26, 29, 4, 2 };

	public static void main(String[] args) {
		// InsertionSort--------------------------------------------------------
		// InsertionSort is = new InsertionSort();
		// // printArray("InsertionSort", is.sort(input1, true));

		// MergeSort--------------------------------------------------------
		// MergeSort ms = new MergeSort();
		// TrackLog tLog = new TrackLog("MergeSort"); //track
		// Analysis.startTracking(tLog); //track
		// printArray("MergeSort", ms.sort(input3, false));
		// Analysis.endTracking(tLog); //track
		// Analysis.printTrack(tLog); //track

		// BubbleSort--------------------------------------------------------
		// BubbleSort bs = new BubbleSort();
		// TrackLog tLog1 = new TrackLog("BubbleSort"); //track
		// Analysis.startTracking(tLog1); //track
		// printArray("BubbleSort", bs.sort(input1, false));
		// Analysis.endTracking(tLog1); //track
		// Analysis.printTrack(tLog1); //track
		// printArray("BubbleSort2", bs.sort2(input3, false));
		// printArray("BubbleSort3", bs.sort3(input7, false));

		// SelectionSort--------------------------------------------------------
		// SelectionSort ms = new SelectionSort();
		// TrackLog tLog = new TrackLog("SelectionSort"); //track
		// Analysis.startTracking(tLog); //track
		// printArray("SelectionSort", ms.sort(input1, true));
		// Analysis.endTracking(tLog); //track
		// Analysis.printTrack(tLog); //track

		// Hailstone--------------------------------------------------------
		// Others other = new Others();
		// printList("Hailstone", other.getHailstone(42));

		// Increment on Turing
		// Machine--------------------------------------------------------
		// TuringMachine tMachine = new TuringMachine();
		// printArray("TuringMachine", tMachine.increase(new int[] { 0, 0, 1, 1,
		// 1, 1 }));

		// Sequence sequence = new Sequence();
		// 2 ways to increase the capacity
		// sequence.increaseArray();
		// sequence.doubleArray();

		// Sequence--------------------------------------------------------
		// System.out.println("find " + sequence.find(input1, -10));
		// printArray("insert", sequence.insert(input1, 5, 9));
		// printArray("remove", sequence.remove(input1, 1, 7));
		// printArray("shift", sequence.shift(input2, 2, 5, 7));
		// printArray("iterator", sequence.iterator(input1, new Operator() {
		//
		// @Override
		// public int doSomethine(int input) {
		// return ++input;
		// }
		// }));

		// printArray("removeDuplicates", sequence.removeDuplicates(input1));
		// printArray("removeDuplicatesAndSort1",sequence.removeDuplicatesAndSort1(input6));
		// printArray("removeDuplicatesAndSort2",sequence.removeDuplicatesAndSort2(input6));

		// Search--------------------------------------------------------
		// Search search = new Search();
		// System.out.println("binSearch:" + search.binSearch(input6, 1, 0,
		// input6.length - 1));
		// System.out.println("binSearch2:" + search.binSearch2(input6, 1, 0,
		// input6.length - 1));
		// System.out.println("fibSearch:" + search.fibSearch(input6, 1, 0,
		// input6.length - 1));

		// ArrayList--------------------------------------------------------
		// List<String> a1 = new ArrayList<>();
		// List<String> a2 = new ArrayList<>();
		// a2 = a1;// 两者指向相同地址，所以其实是一样的
		// a1.add("hello");
		// a1.add("I'm a1!");
		// a2.add("I'm a2!");
		// System.out.println(a1);
		// System.out.println(a2);
		// MyArrayList<String> myA1 = new MyArrayList<>();
		// List<String> myA2 = new MyArrayList<>();
		// myA2 = (List<String>) myA1.clone(); // 用clone()就是返回一份拷贝，两者互不相干
		// myA1.add("hello");
		// myA1.add("I'm myA1!");
		// myA2.add("I'm myA2!");
		// System.out.println(myA1);
		// System.out.println(myA2);

		// LinkedList--------------------------------------------------------
		// MyLinkedList<Integer> list = new MyLinkedList<>();
		// list.add(1);
		// list.add(1);
		// list.add(1);
		// list.add(1);
		// list.add(3);
		// list.add(3);
		// list.add(3);
		// list.add(5);
		// list.add(5);
		// list.add(7);
		// printList("MyLinkedList", list);
		// list.removeDuplicates();
		// printList("MyLinkedList", list);

		// Stack--------------------------------------------------------
		// NumberSystem ns = new NumberSystem();
		// System.out.println(ns.convertDecimalToOthers(33646, 35));
		// System.out.println(Integer.toString(33646, 35));
		// Others other = new Others();
		// boolean b = other.isBracketsCorrect(
		// "(1/Math.sqrt(5)) * (Math.pow(((1 + Math.sqrt(5))/2), n) -
		// Math.pow(((1 - Math.sqrt(5))/2), n))");
		// System.out.println(b + "");
		// SortableStackPermutation ssp = new SortableStackPermutation();
		// Stack<Integer> oriS = new Stack<>();
		// oriS.push(1);
		// oriS.push(2);
		// oriS.push(3);
		// oriS.push(4);
		// oriS.push(5);
		// Stack<Integer> sortedS = new Stack<>();
		// sortedS.push(1);
		// sortedS.push(2);
		// sortedS.push(5);
		// sortedS.push(3);
		// sortedS.push(4);
		// boolean b1 = ssp.isSortableStack(oriS,sortedS);
		// System.out.println(b1 + "");

		// Binary Tree--------------------------------------------------------
		// MyBinaryTree<String> mBT = new MyBinaryTree<String>("R");
		// Node<String> lc = mBT.insertLC(mBT.getRoot(), "A");
		// mBT.insertRC(lc, "C");
		// Node<String> rc = mBT.insertRC(mBT.getRoot(), "B");
		// Node<String> rclc = mBT.insertLC(rc, "D");
		// mBT.insertRC(rc, "E");
		// mBT.setLC(rclc, "F");
		//
		// for (int i = 0; i < 500; i++) {
		// Node<String> node = mBT.insertRC(rclc, "G");
		// rclc = node;
		// }
		//
		// System.out.println("H:" + mBT.getHeight());
		// mBT.traversePre();
		// mBT.traversePreNR1();
		// mBT.traversePreNR2();
		// mBT.traverseIn();
		// mBT.traverseInNR();
		// mBT.traversePost();
		// mBT.traversePostNR1();
		// mBT.traversePostNR2();
		// mBT.traverseLevel();

		// BST--------------------------------------------------------
		// MyBinarySearchTree<String> mBST = new MyBinarySearchTree<>(50, "R");
		// mBST.insert(20, "二十");
		// mBST.insert(80, "");
		// mBST.insert(10, "");
		// mBST.insert(30, "");
		// mBST.insert(70, "");
		// mBST.insert(90, "");
		// mBST.insert(35, "");
		// mBST.insert(25, "");
		// mBST.insert(65, "");
		// // mBST.removeRCCompletely(mBST.search(80));
		// mBST.removeLCCompletely(mBST.search(80));
		// mBST.remove(20);
		// mBST.traverseIn();// 中序一定是小到大排
		// mBST.traverseLevel();
		// // System.out.println(mBST.succ(mBST.search(20)));
		// System.out.println(mBST.search(25));
		// System.out.println(mBST.size());
		// MyBinarySearchTree<Object> randomBST = (MyBinarySearchTree<Object>)
		// MyBinarySearchTree.random(5);
		// randomBST.traverseLevel();
		// System.out.println("Is this BST full? " + randomBST.isFull());
		// AVL Tree-----------------------------------------------------
		MyAVLTree<String> myAVLTree = new MyAVLTree<String>(3, null);
		myAVLTree.insert(2, null);
		myAVLTree.insert(11, null);
		myAVLTree.insert(1, null);
		myAVLTree.insert(6, null);
		myAVLTree.insert(18, null);
		myAVLTree.insert(9, null);
		myAVLTree.insert(10, null);
		myAVLTree.traverseIn();
		myAVLTree.traverseLevel();
		myAVLTree.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				System.out.println("Is this an AVL TREE? " + result);
			}
		});

		// Graph--------------------------------------------------------
		// DirectedGraph<String> dGraph = new DirectedGraph<>();
		// Vertex<String> va = dGraph.addVertex("A");
		// Vertex<String> vc = dGraph.addVertex("C");
		// Vertex<String> vb = dGraph.addVertex(1, "B");
		// Vertex<String> vd = dGraph.addVertex("D");
		// Vertex<String> ve = dGraph.addVertex("E");
		//
		// dGraph.addEdge(va, vb);
		// dGraph.addEdge(vb, vc);
		// dGraph.addEdge(vb, ve);
		// dGraph.addEdge(va, vc);
		// dGraph.addEdge(vc, vd);
		// dGraph.addEdge(vd, ve);
		// dGraph.addEdge(vc, ve);
		// dGraph.removeEdge(vc, va);
		// dGraph.removeEdge(vc, ve);
		// Vertex<String> vb = dGraph.addVertex("B");
		// Vertex<String> vc = dGraph.addVertex("C");
		// Vertex<String> vd = dGraph.addVertex("D");
		// // Vertex<String> ve = dGraph.addVertex("E");
		// Vertex<String> vf = dGraph.addVertex("F");
		// Vertex<String> vg = dGraph.addVertex("G");
		// Vertex<String> va = dGraph.addVertex("A");
		// dGraph.addEdge(va, vb);
		// dGraph.addEdge(va, vc);
		// dGraph.addEdge(va, vd);
		// dGraph.addEdge(vb, vc);
		// dGraph.addEdge(vb, vd);
		// dGraph.addEdge(vb, vf);
		// dGraph.addEdge(vd, vf);
		// dGraph.addEdge(vd, vg);
		// dGraph.addEdge(vf, vg);

		// dGraph.setVertex(0, "a");
		// dGraph.setVertex(1, "b");
		// dGraph.setVertex(2, "c");

		// System.out.println("size:" + dGraph.size());
		// System.out.println(dGraph.nextNbr(va, 2).toString());
		// System.out.println(dGraph.getChild(vb, 3).toString());

		// bfs
		// System.out.println(dGraph.toString());
		// dGraph.printVertexes();
		// System.out.println(dGraph.edgeCount());
		// dGraph.bfs(vb);
		// System.out.println(dGraph.toString());
		// dGraph.printVertexes();
		// System.out.println(dGraph.edgeCount());
		// dGraph.deBfs(vb);
		// System.out.println(dGraph.toString());
		// dGraph.printVertexes();
		// System.out.println(dGraph.edgeCount());
		//
		// bfs连通图专用
		// System.out.println("bfs vertex:" + dGraph.bfs().toString());

		// dfs
		// System.out.println(dGraph.toString());
		// dGraph.printVertexes();
		// dGraph.dfs(va);
		// System.out.println(dGraph.toString());
		// dGraph.printVertexes();
	}

	public static void printArray(String title, int[] array) {
		System.out.println("--------------------------------------------------------");
		if (array == null)
			System.out.println("null");
		else {
			System.out.print(title + " -> [");
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i]);
				if (i != array.length - 1)
					System.out.print(",");
				else
					System.out.println("]");
			}
		}
		System.out.println("--------------------------------------------------------");
	}

	public static void printList(String title, List<Integer> list) {
		System.out.println("--------------------------------------------------------");
		if (list == null)
			System.out.println("null");
		else {
			System.out.print(title + " -> [");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i));
				if (i != list.size() - 1)
					System.out.print(",");
				else
					System.out.println("]");
			}
		}
		System.out.println("--------------------------------------------------------");
	}

}