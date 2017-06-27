package com.catherine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Stack;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.catherine.data_type.MyArrayList;
import com.catherine.data_type.MyLinkedList;
import com.catherine.data_type.Operator;
import com.catherine.data_type.Search;
import com.catherine.data_type.Sequence;
import com.catherine.graphs.DirectedGraph;
import com.catherine.graphs.DirectedGraph.Vertex;
import com.catherine.graphs.trees.Callback;
import com.catherine.graphs.trees.MyAVLTree;
import com.catherine.graphs.trees.MyBTree_Integer;
import com.catherine.graphs.trees.MyBinarySearchTree;
import com.catherine.graphs.trees.MyBinaryTree;
import com.catherine.graphs.trees.MySplayTree;
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
import com.catherine.utils.security.CipherKit;
import com.catherine.utils.security.DESCallback;
import com.catherine.utils.security.KeystoreManager;
import com.catherine.utils.security.MessageDigestKit;
import com.catherine.utils.security.RSACallback;

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
		// testInsertionSort();
		// testMergeSort();
		// testBubbleSort();
		// testSelectionSort();
		// testHailstone();
		// testTuringMachine();
		// testSequence();
		// testSearch();
		// testArrayList();
		// testLinkedList();
		// testStack();
		// testDirectedGraph();
		// testBinaryTree();
		// testBST();
		// testAVLTree();

		// testCryptography();

		// testSplayTree();
		testBTree();
	}

	public static void testBTree() {
		MyBTree_Integer myBTree = new MyBTree_Integer(3, 10);
		myBTree.insert(1);
		myBTree.insert(2);
		System.out.println(myBTree.toString());
	}

	public static void testSplayTree() {
		MySplayTree<String> mySplayTree1 = new MySplayTree<String>(641, null);
		mySplayTree1.insert(468, null);
		mySplayTree1.insert(777, null);
		mySplayTree1.insert(448, null);
		mySplayTree1.insert(507, null);
		mySplayTree1.insert(743, null);
		mySplayTree1.insert(21, null);
		mySplayTree1.insert(486, null);
		mySplayTree1.insert(527, null);
		mySplayTree1.insert(1, null);
		mySplayTree1.insert(285, null);
		mySplayTree1.insert(495, null);
		mySplayTree1.insert(69, null);
		mySplayTree1.insert(333, null);
		mySplayTree1.insert(266, null);
		mySplayTree1.insert(400, null);
		mySplayTree1.traverseLevel();
		mySplayTree1.search(333);
		mySplayTree1.traverseLevel();

		// the worst case
		MySplayTree<String> mySplayTree2 = new MySplayTree<String>(90, null);
		mySplayTree2.insert(80, null);
		mySplayTree2.insert(70, null);
		mySplayTree2.insert(60, null);
		mySplayTree2.insert(50, null);
		mySplayTree2.insert(40, null);
		mySplayTree2.insert(30, null);
		mySplayTree2.insert(20, null);
		mySplayTree2.insert(10, null);
		mySplayTree2.insert(0, null);

		// System.out.println("Searching with ordinary rotation in the worst
		// case");
		// for (int i = 0; i < 100; i+=10) {
		// System.out.println(String.format("rotate %d", i));
		// mySplayTree2.search(i, false);
		// mySplayTree2.traverseLevel();
		// }
		// System.out.println("");
		// System.out.println("Searching with efficient rotation in the worst
		// case");
		// for (int i = 0; i < 100; i+=10) {
		// System.out.println(String.format("rotate %d", i));
		// mySplayTree2.search(i);
		// mySplayTree2.traverseLevel();
		// }

		// insert
		mySplayTree2.insert(15, null);
		mySplayTree2.traverseLevel();

		// delete
		mySplayTree1.remove(468);
		mySplayTree1.traverseLevel();
	}

	private static boolean lock;
	private static PublicKey signatureKey;
	private static byte[] signature;

	private static void testCryptography() {
		try {
			final Analysis analysis = new Analysis();
			// KeystoreManager.printCertificatesInfo();
			// KeystoreManager.printKeyStoreInfo();

			TrackLog log1 = new TrackLog("General a single key");
			analysis.startTracking(log1);
			String secretKeyString = KeystoreManager.generateKeyString();
			analysis.endTracking(log1);
			analysis.printTrack(log1);

			TrackLog log2 = new TrackLog("Decrypt the key");
			analysis.startTracking(log2);
			KeystoreManager.converStringToKey(secretKeyString);
			analysis.endTracking(log2);
			analysis.printTrack(log2);

			final TrackLog log3 = new TrackLog("General a RSA keyPair");
			analysis.startTracking(log3);
			KeystoreManager.generateRSAKeyPair(new RSACallback() {

				@Override
				public void onResponse(String modulus, String exponent) {
					analysis.endTracking(log3);
					analysis.printTrack(log3);

					try {
						TrackLog log4 = new TrackLog("Decrypt the RSA keyPair");
						analysis.startTracking(log4);
						KeystoreManager.converStringToPublicKey(modulus, exponent);
						analysis.endTracking(log4);
						analysis.printTrack(log4);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (InvalidKeySpecException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onResponse(PrivateKey privateKey) {
				}
			});

			TrackLog log5 = new TrackLog("General a keypair from the keystore");
			analysis.startTracking(log5);
			KeystoreManager.getKeyPairFromKeystore();
			analysis.endTracking(log5);
			analysis.printTrack(log5);

			TrackLog log6 = new TrackLog("Encrypt a string from the keyPair");
			analysis.startTracking(log6);
			byte[] msg = CipherKit.encrypt("你好啊！");
			analysis.endTracking(log6);
			analysis.printTrack(log6);

			TrackLog log7 = new TrackLog("Decrypt a string from the keyPair");
			analysis.startTracking(log7);
			System.out.println(CipherKit.decrypt(msg));
			analysis.endTracking(log7);
			analysis.printTrack(log7);

			final TrackLog log8 = new TrackLog("Encrypt a string from the secretKey key");
			analysis.startTracking(log8);
			final Key sKey = KeystoreManager.generateKey();
			CipherKit.encryptDES(sKey, "Hi there!", new DESCallback() {

				@Override
				public void onResponse(byte[] iv, byte[] message) {
					analysis.endTracking(log8);
					analysis.printTrack(log8);

					try {
						TrackLog log9 = new TrackLog("Decrypt a string from the secretKey key");
						analysis.startTracking(log9);
						System.out.println(CipherKit.decryptDES(sKey, iv, message));
						analysis.endTracking(log9);
						analysis.printTrack(log9);
					} catch (InvalidKeyException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (NoSuchPaddingException e) {
						e.printStackTrace();
					} catch (IllegalBlockSizeException e) {
						e.printStackTrace();
					} catch (BadPaddingException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (InvalidAlgorithmParameterException e) {
						e.printStackTrace();
					}

				}
			});
			// verify files
			final TrackLog log10 = new TrackLog("Signing the file ");
			analysis.startTracking(log10);
			lock = true;
			KeystoreManager.generateRSAKeyPair(new RSACallback() {

				@Override
				public void onResponse(String modulus, String exponent) {
					try {
						signatureKey = (PublicKey) KeystoreManager.converStringToPublicKey(modulus, exponent);
						if (!lock) {
							TrackLog log11 = new TrackLog("verifing the file with signature ");
							analysis.startTracking(log11);
							boolean islegel = MessageDigestKit.verifySignature(signature, "assets/metals.jpg",
									signatureKey);
							System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
							System.out.println("Signature: Is this file legel? " + islegel);
							analysis.endTracking(log11);
							analysis.printTrack(log11);
						}
						lock = false;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (InvalidKeySpecException e) {
						e.printStackTrace();
					} catch (InvalidKeyException e) {
						e.printStackTrace();
					} catch (SignatureException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onResponse(PrivateKey privateKey) {
					try {
						signature = MessageDigestKit.signFiles("assets/metals.jpg", privateKey);
						analysis.endTracking(log10);
						analysis.printTrack(log10);

						if (!lock) {
							TrackLog log11 = new TrackLog("verifing the file with signature ");
							analysis.startTracking(log11);
							boolean islegel = MessageDigestKit.verifySignature(signature, "assets/metals.jpg",
									signatureKey);
							System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
							System.out.println("Signature: Is this file legel? " + islegel);
							analysis.endTracking(log11);
							analysis.printTrack(log11);
						}
						lock = false;
					} catch (InvalidKeyException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (SignatureException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});

		} catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException
				| UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException
				| IOException | InvalidKeySpecException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public static void testAVLTree() {
		final MyAVLTree<String> myAVLTree1 = new MyAVLTree<String>(20, null);
		myAVLTree1.insert(10, null);
		myAVLTree1.insert(30, null);
		myAVLTree1.insert(5, null);
		myAVLTree1.insert(25, null);
		myAVLTree1.insert(40, null);
		myAVLTree1.insert(35, null);
		myAVLTree1.insert(45, null);
		myAVLTree1.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				myAVLTree1.traverseLevel();
				System.out.println("Is that an AVL tree? " + result);
				myAVLTree1.insertAndBalance(34, null);
				myAVLTree1.isAVLTree(new Callback() {
					@Override
					public void onResponse(boolean result) {
						myAVLTree1.traverseLevel();
						System.out.println("Is that still an AVL tree? " + result);
					}
				});
			}
		});

		final MyAVLTree<String> myAVLTree2 = new MyAVLTree<String>(9, null);
		myAVLTree2.insert(1, null);
		myAVLTree2.insert(10, null);
		myAVLTree2.insert(5, null);
		myAVLTree2.insert(0, null);
		myAVLTree2.insert(11, null);
		myAVLTree2.insert(-1, null);
		myAVLTree2.insert(2, null);
		myAVLTree2.insert(6, null);
		myAVLTree2.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				myAVLTree2.traverseLevel();
				System.out.println("Is that an AVL tree? " + result);
				myAVLTree2.removeAndBalance(10);
				myAVLTree2.isAVLTree(new Callback() {
					@Override
					public void onResponse(boolean result) {
						myAVLTree2.traverseLevel();
						System.out.println("Is that still an AVL tree? " + result);
					}
				});
			}
		});

		final MyAVLTree<String> myAVLTree3 = new MyAVLTree<String>(44, null);
		myAVLTree3.insert(17, null);
		myAVLTree3.insert(78, null);
		myAVLTree3.insert(32, null);
		myAVLTree3.insert(50, null);
		myAVLTree3.insert(88, null);
		myAVLTree3.insert(48, null);
		myAVLTree3.insert(62, null);
		myAVLTree3.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				myAVLTree3.traverseLevel();
				System.out.println("Is that an AVL tree? " + result);
				myAVLTree3.removeAndBalance(32);
				myAVLTree3.isAVLTree(new Callback() {
					@Override
					public void onResponse(boolean result) {
						myAVLTree3.traverseLevel();
						System.out.println("Is that still an AVL tree? " + result);
					}
				});
			}
		});

		final MyAVLTree<String> myAVLTree4 = new MyAVLTree<String>(50, null);
		myAVLTree4.insert(25, null);
		myAVLTree4.insert(75, null);
		myAVLTree4.insert(10, null);
		myAVLTree4.insert(30, null);
		myAVLTree4.insert(60, null);
		myAVLTree4.insert(80, null);
		myAVLTree4.insert(5, null);
		myAVLTree4.insert(15, null);
		myAVLTree4.insert(27, null);
		myAVLTree4.insert(55, null);
		myAVLTree4.insert(1, null);
		myAVLTree4.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				myAVLTree4.traverseLevel();
				System.out.println("Is that an AVL tree? " + result);
				myAVLTree4.removeAndBalance(80);
				myAVLTree4.isAVLTree(new Callback() {
					@Override
					public void onResponse(boolean result) {
						myAVLTree4.traverseLevel();
						System.out.println("Is that still an AVL tree? " + result);
					}
				});
			}
		});

		final MyAVLTree<String> myAVLTree5 = new MyAVLTree<String>(50, null);
		myAVLTree5.insert(25, null);
		myAVLTree5.insert(75, null);
		myAVLTree5.insert(10, null);
		myAVLTree5.insert(30, null);
		myAVLTree5.insert(60, null);
		myAVLTree5.insert(5, null);
		myAVLTree5.insert(15, null);
		myAVLTree5.insert(27, null);
		myAVLTree5.insert(55, null);
		myAVLTree5.insert(1, null);
		myAVLTree5.isAVLTree(new Callback() {
			@Override
			public void onResponse(boolean result) {
				myAVLTree5.traverseLevel();
				System.out.println("Is that an AVL tree? " + result);
				myAVLTree5.insertAndBalance(80, null);
				myAVLTree5.isAVLTree(new Callback() {
					@Override
					public void onResponse(boolean result) {
						myAVLTree5.traverseLevel();
						System.out.println("Is that still an AVL tree? " + result);
					}
				});
			}
		});
	}

	public static void testBST() {
		MyBinarySearchTree<String> mBST = new MyBinarySearchTree<>(50, "R");
		mBST.insert(20, "二十");
		mBST.insert(80, "");
		mBST.insert(10, "");
		mBST.insert(30, "");
		mBST.insert(70, "");
		mBST.insert(90, "");
		mBST.insert(35, "");
		mBST.insert(25, "");
		mBST.insert(65, "");
		// mBST.removeRCCompletely(mBST.search(80));
		// mBST.removeLCCompletely(mBST.search(80));
		// mBST.remove(20);
		mBST.traverseIn();// 中序一定是小到大排
		mBST.traverseLevel();
		mBST.zig(mBST.search(80));
		mBST.traverseIn();// 中序一定是小到大排
		mBST.traverseLevel();
		// System.out.println(mBST.succ(mBST.search(20)));

		// System.out.println(mBST.search(25));
		// System.out.println(mBST.size());
		// MyBinarySearchTree<Object> randomBST = (MyBinarySearchTree<Object>)
		// MyBinarySearchTree.random(5);
		// randomBST.traverseLevel();
		// System.out.println("Is this BST full? " + randomBST.isFull());
	}

	public static void testBinaryTree() {
		MyBinaryTree<String> mBT = new MyBinaryTree<String>("R");
		Node<String> lc = mBT.insertLC(mBT.getRoot(), "A");
		mBT.insertRC(lc, "C");
		Node<String> rc = mBT.insertRC(mBT.getRoot(), "B");
		Node<String> rclc = mBT.insertLC(rc, "D");
		mBT.insertRC(rc, "E");
		mBT.setLC(rclc, "F");

		for (int i = 0; i < 500; i++) {
			Node<String> node = mBT.insertRC(rclc, "G");
			rclc = node;
		}

		System.out.println("H:" + mBT.getHeight());
		mBT.traversePre();
		mBT.traversePreNR1();
		mBT.traversePreNR2();
		mBT.traverseIn();
		mBT.traverseInNR();
		mBT.traversePost();
		mBT.traversePostNR1();
		mBT.traversePostNR2();
		mBT.traverseLevel();
	}

	public static void testDirectedGraph() {
		DirectedGraph<String> dGraph = new DirectedGraph<>();
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
		Vertex<String> vb = dGraph.addVertex("B");
		Vertex<String> vc = dGraph.addVertex("C");
		Vertex<String> vd = dGraph.addVertex("D");
		// Vertex<String> ve = dGraph.addVertex("E");
		Vertex<String> vf = dGraph.addVertex("F");
		Vertex<String> vg = dGraph.addVertex("G");
		Vertex<String> va = dGraph.addVertex("A");
		dGraph.addEdge(va, vb);
		dGraph.addEdge(va, vc);
		dGraph.addEdge(va, vd);
		dGraph.addEdge(vb, vc);
		dGraph.addEdge(vb, vd);
		dGraph.addEdge(vb, vf);
		dGraph.addEdge(vd, vf);
		dGraph.addEdge(vd, vg);
		dGraph.addEdge(vf, vg);

		dGraph.setVertex(0, "a");
		dGraph.setVertex(1, "b");
		dGraph.setVertex(2, "c");

		System.out.println("size:" + dGraph.size());
		System.out.println(dGraph.nextNbr(va, 2).toString());
		System.out.println(dGraph.getChild(vb, 3).toString());

		// bfs
		System.out.println(dGraph.toString());
		dGraph.printVertexes();
		System.out.println(dGraph.edgeCount());
		dGraph.bfs(vb);
		System.out.println(dGraph.toString());
		dGraph.printVertexes();
		System.out.println(dGraph.edgeCount());
		dGraph.deBfs(vb);
		System.out.println(dGraph.toString());
		dGraph.printVertexes();
		System.out.println(dGraph.edgeCount());

		// bfs连通图专用
		System.out.println("bfs vertex:" + dGraph.bfs().toString());

		// dfs
		System.out.println(dGraph.toString());
		dGraph.printVertexes();
		dGraph.dfs(va);
		System.out.println(dGraph.toString());
		dGraph.printVertexes();
	}

	public static void testStack() {
		NumberSystem ns = new NumberSystem();
		System.out.println(ns.convertDecimalToOthers(33646, 35));
		System.out.println(Integer.toString(33646, 35));
		Others other = new Others();
		boolean b = other.isBracketsCorrect(
				"(1/Math.sqrt(5)) * (Math.pow(((1 + Math.sqrt(5))/2), n) -Math.pow(((1 - Math.sqrt(5))/2), n))");
		System.out.println(b + "");
		SortableStackPermutation ssp = new SortableStackPermutation();
		Stack<Integer> oriS = new Stack<>();
		oriS.push(1);
		oriS.push(2);
		oriS.push(3);
		oriS.push(4);
		oriS.push(5);
		Stack<Integer> sortedS = new Stack<>();
		sortedS.push(1);
		sortedS.push(2);
		sortedS.push(5);
		sortedS.push(3);
		sortedS.push(4);
		boolean b1 = ssp.isSortableStack(oriS, sortedS);
		System.out.println(b1 + "");
	}

	public static void testLinkedList() {
		MyLinkedList<Integer> list = new MyLinkedList<>();
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(3);
		list.add(3);
		list.add(3);
		list.add(5);
		list.add(5);
		list.add(7);
		printList("MyLinkedList", list);
		list.removeDuplicates();
		printList("MyLinkedList", list);
	}

	public static void testArrayList() {
		List<String> a1 = new ArrayList<>();
		List<String> a2 = new ArrayList<>();
		a2 = a1;// 两者指向相同地址，所以其实是一样的
		a1.add("hello");
		a1.add("I'm a1!");
		a2.add("I'm a2!");
		System.out.println(a1);
		System.out.println(a2);
		MyArrayList<String> myA1 = new MyArrayList<>();
		List<String> myA2 = new MyArrayList<>();
		myA2 = (List<String>) myA1.clone(); // 用clone()就是返回一份拷贝，两者互不相干
		myA1.add("hello");
		myA1.add("I'm myA1!");
		myA2.add("I'm myA2!");
		System.out.println(myA1);
		System.out.println(myA2);
	}

	public static void testSearch() {
		Search search = new Search();
		System.out.println("binSearch:" + search.binSearch(input6, 1, 0, input6.length - 1));
		System.out.println("binSearch2:" + search.binSearch2(input6, 1, 0, input6.length - 1));
		System.out.println("fibSearch:" + search.fibSearch(input6, 1, 0, input6.length - 1));
	}

	public static void testSequence() {
		Sequence sequence = new Sequence();
		System.out.println("find " + sequence.find(input1, -10));
		printArray("insert", sequence.insert(input1, 5, 9));
		printArray("remove", sequence.remove(input1, 1, 7));
		printArray("shift", sequence.shift(input2, 2, 5, 7));
		printArray("iterator", sequence.iterator(input1, new Operator() {

			@Override
			public int doSomethine(int input) {
				return ++input;
			}
		}));

		printArray("removeDuplicates", sequence.removeDuplicates(input1));
		printArray("removeDuplicatesAndSort1", sequence.removeDuplicatesAndSort1(input6));
		printArray("removeDuplicatesAndSort2", sequence.removeDuplicatesAndSort2(input6));

	}

	public static void testTuringMachine() {
		// Increment on Turing Machine
		TuringMachine tMachine = new TuringMachine();
		printArray("TuringMachine", tMachine.increase(new int[] { 0, 0, 1, 1, 1, 1 }));

		Sequence sequence = new Sequence();
		// 2 ways to increase the capacity
		sequence.increaseArray();
		sequence.doubleArray();
	}

	public static void testHailstone() {
		Others other = new Others();
		printList("Hailstone", other.getHailstone(42));
	}

	public static void testSelectionSort() {
		SelectionSort ms = new SelectionSort();
		TrackLog tLog = new TrackLog("SelectionSort"); // track
		Analysis.startTracking(tLog); // track
		printArray("SelectionSort", ms.sort(input1, true));
		Analysis.endTracking(tLog); // track
		Analysis.printTrack(tLog); // track
	}

	public static void testBubbleSort() {
		BubbleSort bs = new BubbleSort();
		TrackLog tLog1 = new TrackLog("BubbleSort"); // track
		Analysis.startTracking(tLog1); // track
		printArray("BubbleSort", bs.sort(input1, false));
		Analysis.endTracking(tLog1); // track
		Analysis.printTrack(tLog1); // track
		printArray("BubbleSort2", bs.sort2(input3, false));
		printArray("BubbleSort3", bs.sort3(input7, false));
	}

	public static void testInsertionSort() {
		InsertionSort is = new InsertionSort();
		printArray("InsertionSort", is.sort(input1, true));
	}

	public static void testMergeSort() {
		MergeSort ms = new MergeSort();
		TrackLog tLog = new TrackLog("MergeSort"); // track
		Analysis.startTracking(tLog); // track
		printArray("MergeSort", ms.sort(input3, false));
		Analysis.endTracking(tLog); // track
		Analysis.printTrack(tLog); // track
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