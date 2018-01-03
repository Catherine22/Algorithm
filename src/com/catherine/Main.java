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
import java.security.cert.X509Certificate;
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
import com.catherine.dictionary.HashingHelper;
import com.catherine.dictionary.data.Student;
import com.catherine.dictionary.functions.CollisionMode;
import com.catherine.dictionary.functions.Fold;
import com.catherine.dictionary.functions.HashingTemplate;
import com.catherine.dictionary.functions.Mod;
import com.catherine.dictionary.functions.MidSquare;
import com.catherine.dictionary.functions.Remainder;
import com.catherine.dictionary.functions.RotateAndFold;
import com.catherine.dictionary.functions.RotateAndXORFold;
import com.catherine.dictionary.functions.SelectingDigits;
import com.catherine.dictionary.functions.XORFold;
import com.catherine.graphs.DirectedGraph;
import com.catherine.graphs.DirectedGraph.Vertex;
import com.catherine.sort.BubbleSort;
import com.catherine.sort.InsertionSort;
import com.catherine.sort.MergeSort;
import com.catherine.sort.SelectionSort;
import com.catherine.sort.SortableStackPermutation;
import com.catherine.trees.Callback;
import com.catherine.trees.MyAVLTree;
import com.catherine.trees.MyBTree;
import com.catherine.trees.MyBinarySearchTree;
import com.catherine.trees.MyBinaryTree;
import com.catherine.trees.MySplayTree;
import com.catherine.trees.nodes.Node;
import com.catherine.turing_machine.TuringMachine;
import com.catherine.utils.Analysis;
import com.catherine.utils.NumberSystem;
import com.catherine.utils.Others;
import com.catherine.utils.TrackLog;
import com.catherine.security.Algorithm;
import com.catherine.security.CertificatesManager;
import com.catherine.security.CipherKit;
import com.catherine.security.DESCallback;
import com.catherine.security.JwsHelper;
import com.catherine.security.KeySet;
import com.catherine.security.KeystoreManager;
import com.catherine.security.MessageDigestKit;
import com.catherine.security.RSACallback;
import com.catherine.security.jws_object.AttestationResult;

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
		// testSplayTree();
		// testBTree();
		testHash();

		// testCryptography();
		// testJWS();
	}

	public static void testHash() {
		CollisionMode doNothing = new CollisionMode.Builder().build();
		CollisionMode probingSequence = new CollisionMode.Builder().mode(CollisionMode.PROBING_SEQUENCE).spareBuckets(3)
				.build();

		HashingHelper hashingHelper = new HashingHelper("students_raw", doNothing);
		hashingHelper.createRandomTable(100, 0.75f, 30, 336, true);
		List<Student> rawTableList = hashingHelper.getTableList();
		List<Student> rawStudentList = hashingHelper.getStudent();

		// remainder
		HashingTemplate remainder = new Remainder(17, doNothing);
//		remainder.hash(rawTableList);
//		remainder.analyse(rawTableList, rawStudentList, remainder.getTableList(), remainder.getStudent());

		remainder = new Remainder(17, probingSequence);
		remainder.hash(rawTableList);
		remainder.analyse(rawTableList, rawStudentList, remainder.getTableList(), remainder.getStudent());
		//
		// remainder = new Remainder(36);
		// remainder.hash(rawTableList);
		// remainder.analyse(rawTableList, rawStudentList,
		// remainder.getTableList(), remainder.getStudent());
		//
		// remainder = new Remainder(37);
		// remainder.hash(rawTableList);
		// remainder.analyse(rawTableList, rawStudentList,
		// remainder.getTableList(), remainder.getStudent());
		//
		// remainder = new Remainder(97);
		// remainder.hash(rawTableList);
		// remainder.analyse(rawTableList, rawStudentList,
		// remainder.getTableList(), remainder.getStudent());
		//
		// // modulo
		// HashingTemplate mod = new Mod(2, 14, 17);
		// mod.hash(rawTableList);
		// mod.analyse(rawTableList, rawStudentList, mod.getTableList(),
		// mod.getStudent());
		//
		// mod = new Mod(2, 14, 36);
		// mod.hash(rawTableList);
		// mod.analyse(rawTableList, rawStudentList, mod.getTableList(),
		// mod.getStudent());
		//
		// mod = new Mod(2, 14, 37);
		// mod.hash(rawTableList);
		// mod.analyse(rawTableList, rawStudentList, mod.getTableList(),
		// mod.getStudent());
		//
		// mod = new Mod(2, 14, 97);
		// mod.hash(rawTableList);
		// mod.analyse(rawTableList, rawStudentList, mod.getTableList(),
		// mod.getStudent());
		//
		// HashingTemplate sd = new SelectingDigits();
		// sd.hash(rawTableList);
		// sd.analyse(rawTableList, rawStudentList, sd.getTableList(),
		// sd.getStudent());
		//
		// MidSquare ms = new MidSquare();
		// ms.hash(rawTableList);
		// ms.analyse(rawTableList, rawStudentList, ms.getTableList(),
		// ms.getStudent());
		//
		// Fold fold = new Fold(2);
		// fold.hash(rawTableList);
		// fold.analyse(rawTableList, rawStudentList, fold.getTableList(),
		// fold.getStudent());
		//
		// RotateAndFold raf = new RotateAndFold(2);
		// raf.hash(rawTableList);
		// raf.analyse(rawTableList, rawStudentList, raf.getTableList(),
		// raf.getStudent());
		//
		// XORFold xorf = new XORFold(2);
		// xorf.hash(rawTableList);
		// xorf.analyse(rawTableList, rawStudentList, xorf.getTableList(),
		// xorf.getStudent());
		//
		// RotateAndXORFold raxorf = new RotateAndXORFold(2);
		// raxorf.hash(rawTableList);
		// raxorf.analyse(rawTableList, rawStudentList, raxorf.getTableList(),
		// raxorf.getStudent());
	}

	public static void testBTree() {
		MyBTree<String> myBTree = new MyBTree<>(5, 528);
		// myBTree.printInfo();
		myBTree.insert(192, null);
		myBTree.insert(268, null);
		myBTree.insert(703, null);
		myBTree.insert(850, null);

		myBTree.insert(57, null);
		myBTree.insert(152, null);
		myBTree.insert(249, null);
		myBTree.insert(266, null);
		myBTree.insert(315, null);
		myBTree.insert(423, null);
		myBTree.insert(468, null);
		myBTree.insert(484, null);
		myBTree.insert(619, null);
		myBTree.insert(644, null);
		myBTree.insert(758, null);
		myBTree.insert(771, null);
		myBTree.insert(865, null);
		myBTree.insert(882, null);
		myBTree.insert(936, null);
		myBTree.insert(984, null);
		// System.out.println(myBTree.toString());
		myBTree.remove(249);
		System.out.println(myBTree.toString());

		myBTree.remove(619);
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

	private static String attestationJws = "eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlFaURDQ0EzQ2dBd0lCQWdJSU5CY1JCYkpDSWxBd0RRWUpLb1pJaHZjTkFRRUxCUUF3U1RFTE1Ba0dBMVVFQmhNQ1ZWTXhFekFSQmdOVkJBb1RDa2R2YjJkc1pTQkpibU14SlRBakJnTlZCQU1USEVkdmIyZHNaU0JKYm5SbGNtNWxkQ0JCZFhSb2IzSnBkSGtnUnpJd0hoY05NVGN3TlRFM01UQTBNRE00V2hjTk1UY3hNakkzTURBd01EQXdXakJzTVFzd0NRWURWUVFHRXdKVlV6RVRNQkVHQTFVRUNBd0tRMkZzYVdadmNtNXBZVEVXTUJRR0ExVUVCd3dOVFc5MWJuUmhhVzRnVm1sbGR6RVRNQkVHQTFVRUNnd0tSMjl2WjJ4bElFbHVZekViTUJrR0ExVUVBd3dTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUF2L1dPZFpURlJlN29FUFhuUkNWOVNlbWxrNGpnckZSd0tFRng4d2FRTUMrU3NzSld5dUhpSHNNdEY2MDdOcFR1MCttbFBKOEM5TkhhbmtkUElzS3RLNmJ0emMreDBlc2c1VS9JUkQ0K2JRNVpSSDBrT1BxMmZpd1g1WmJnZDUrOFIzOWIyYkxmV0dDMmJkV1lxbHBvTUs1bXhFWW1BVVdIb0J4M2JHUldCR05BMi8vNHpaS0xqc0lFSFdFYksxOE5Kb0w3VlJOaExENlkvcmVXVElDdUNHdzVraERvbEJwYUw0R0xCakZvSEVBelNoZjFlUzhuYUFFcGpzQ3Z5ZWFCVzM0ZGJhUFJadFRIVWFBRDRtYXZCZ2hta0Z2eVRFL293SUJFa0pVakliRzFpUDlndnVzaEJhTmxjOHIway9tR1k1am1uNTZhVWRVZ1JnWjFwditqRFFJREFRQUJvNElCVHpDQ0FVc3dIUVlEVlIwbEJCWXdGQVlJS3dZQkJRVUhBd0VHQ0NzR0FRVUZCd01DTUIwR0ExVWRFUVFXTUJTQ0VtRjBkR1Z6ZEM1aGJtUnliMmxrTG1OdmJUQm9CZ2dyQmdFRkJRY0JBUVJjTUZvd0t3WUlLd1lCQlFVSE1BS0dIMmgwZEhBNkx5OXdhMmt1WjI5dloyeGxMbU52YlM5SFNVRkhNaTVqY25Rd0t3WUlLd1lCQlFVSE1BR0dIMmgwZEhBNkx5OWpiR2xsYm5Sek1TNW5iMjluYkdVdVkyOXRMMjlqYzNBd0hRWURWUjBPQkJZRUZCSitWS3BMSlNaK3VxOTEraW9ReHRTNzlMVzdNQXdHQTFVZEV3RUIvd1FDTUFBd0h3WURWUjBqQkJnd0ZvQVVTdDBHRmh1ODltaTFkdldCdHJ0aUdycGFnUzh3SVFZRFZSMGdCQm93R0RBTUJnb3JCZ0VFQWRaNUFnVUJNQWdHQm1lQkRBRUNBakF3QmdOVkhSOEVLVEFuTUNXZ0k2QWhoaDlvZEhSd09pOHZjR3RwTG1kdmIyZHNaUzVqYjIwdlIwbEJSekl1WTNKc01BMEdDU3FHU0liM0RRRUJDd1VBQTRJQkFRQ1hoMjdtNG11ZEs3NEwxM3FSWml5K0Jrc2ZudmJzL0dEaW9ra0FwdDVveUEvMk10TTFFMGxHY2tEL2NjVWpuNUF1RWx5N3FkdGJnM2NOTk9BbGlWemxnVWFHWTZXNldUWnhrNGl2UVcwbmpwOUFrWkU3Y2N1VDJVU000MEp0dS9WQWxGYUZQV1N3RXBoa3J6VUNjQ2M3cjFTSGpya1FxekRGUnc5dmV0V1VZMWFJZmw3VklGcG1RdmZNMnV3TFlneXRsblBMbHNsMVdrMFBVbWNsc2lEMUg3MmtLelZLeE5ySkFuWk1CeC83SXBKY0Q3alhNTGhneGluK3FVUFBtNzF1M2pxS2VSZ0tVK3FlTjcvSnJOb1dlSXIrR1krbTE0VFljSzhhWkNad3p4VkczeXdVeUI3U1ZzR0tRTEtGRk1USDF1T3lSL1lnSjN2cmgyT1QvMXdiIiwiTUlJRDhEQ0NBdGlnQXdJQkFnSURBanFTTUEwR0NTcUdTSWIzRFFFQkN3VUFNRUl4Q3pBSkJnTlZCQVlUQWxWVE1SWXdGQVlEVlFRS0V3MUhaVzlVY25WemRDQkpibU11TVJzd0dRWURWUVFERXhKSFpXOVVjblZ6ZENCSGJHOWlZV3dnUTBFd0hoY05NVFV3TkRBeE1EQXdNREF3V2hjTk1UY3hNak14TWpNMU9UVTVXakJKTVFzd0NRWURWUVFHRXdKVlV6RVRNQkVHQTFVRUNoTUtSMjl2WjJ4bElFbHVZekVsTUNNR0ExVUVBeE1jUjI5dloyeGxJRWx1ZEdWeWJtVjBJRUYxZEdodmNtbDBlU0JITWpDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBSndxQkhkYzJGQ1JPZ2FqZ3VEWVVFaThpVC94R1hBYWlFWis0SS9GOFluT0llNWEvbUVOdHpKRWlhQjBDMU5QVmFUT2dtS1Y3dXRaWDhiaEJZQVN4RjZVUDd4YlNEajBVL2NrNXZ1UjZSWEV6L1JURGZSSy9KOVUzbjIrb0d0dmg4RFFVQjhvTUFOQTJnaHpVV3gvL3pvOHB6Y0dqcjFMRVFUcmZTVGU1dm44TVhIN2xOVmc4eTVLcjBMU3krckVhaHF5ekZQZEZVdUxIOGdaWVIvTm5hZytZeXVFTldsbGhNZ1p4VVlpK0ZPVnZ1T0FTaERHS3V5Nmx5QVJ4em1aRUFTZzhHRjZsU1dNVGxKMTRyYnRDTW9VL000aWFyTk96MFlEbDVjRGZzQ3gzbnV2UlRQUHVqNXh0OTcwSlNYQ0RUV0puWjM3RGhGNWlSNDN4YStPY21rQ0F3RUFBYU9CNXpDQjVEQWZCZ05WSFNNRUdEQVdnQlRBZXBob2pZbjdxd1ZrREJGOXFuMWx1TXJNVGpBZEJnTlZIUTRFRmdRVVN0MEdGaHU4OW1pMWR2V0J0cnRpR3JwYWdTOHdEZ1lEVlIwUEFRSC9CQVFEQWdFR01DNEdDQ3NHQVFVRkJ3RUJCQ0l3SURBZUJnZ3JCZ0VGQlFjd0FZWVNhSFIwY0RvdkwyY3VjM2x0WTJRdVkyOXRNQklHQTFVZEV3RUIvd1FJTUFZQkFmOENBUUF3TlFZRFZSMGZCQzR3TERBcW9DaWdKb1lrYUhSMGNEb3ZMMmN1YzNsdFkySXVZMjl0TDJOeWJITXZaM1JuYkc5aVlXd3VZM0pzTUJjR0ExVWRJQVFRTUE0d0RBWUtLd1lCQkFIV2VRSUZBVEFOQmdrcWhraUc5dzBCQVFzRkFBT0NBUUVBQ0U0RXA0Qi9FQlpEWGdLdDEwS0E5TENPMHE2ejZ4RjlrSVFZZmVlUUZmdEpmNmlaQlpHN2VzbldQRGNZQ1pxMng1SWdCelV6Q2VRb1kzSU50T0F5bkllWXhCdDJpV2ZCVUZpd0U2b1RHaHN5cGI3cUVaVk1TR05KNlpsZElEZk0vaXBwVVJhVlM2bmVTWUxBRUhEMExQUHN2Q1FrMEU2c3BkbGVIbTJTd2Flc1NEV0IrZVhrbkdWcHpZZWtRVkEvTGxlbGtWRVNXQTZNQ2FHc2VxUVNwU2Z6bWhDWGZWVURCdmRtV0Y5ZlpPR3JYVzJsT1VoMW1Fd3BXanFOMHl2S25GVUV2L1RtRk5XQXJDYnRGNG1tazJ4Y3BNeTQ4R2FPWk9OOW11SUFzMG5INUFxcTNWdUR4M0NRUms2KzBOdFpsbXd1OVJZMjNuSE1BY0lTd1NIR0ZnPT0iXX0.eyJub25jZSI6InptNUl6SEhzOWtZR050L2RtVndnRlhVZVRjWWVNcS9SbHk5Tlk4TlQ1VUk9IiwidGltZXN0YW1wTXMiOjE0OTk0MTg5NzcwNzUsImFwa1BhY2thZ2VOYW1lIjoiY29tLmNhdGhlcmluZS5zZWN1cml0eXNhbXBsZSIsImFwa0RpZ2VzdFNoYTI1NiI6IlhpblVZLzJ3T1V5WUdlUHZIeXRwVmNQdnB3L3MraVY5RmI3SDJoL3dIWk09IiwiY3RzUHJvZmlsZU1hdGNoIjp0cnVlLCJleHRlbnNpb24iOiJDZUFtK0NHVUF5T0EiLCJhcGtDZXJ0aWZpY2F0ZURpZ2VzdFNoYTI1NiI6WyI5bUxGUzNlSFdPQmNIbEE0TW1PRG1mR3Z6Z2tiZzJZU1Eyei93dzlsQ2Z3PSJdLCJiYXNpY0ludGVncml0eSI6dHJ1ZX0.hXu9WfIR4qZ8yIcLeTfN6OIdsvyUmus2Ym_g3wloJtgTs3-aZpHlndPNtNGxDcrbnzJYs9LXAK4JT5aSuAycMDoFBEoI_iLKRfMiLWN5O6nRDCIHunbKfEdviTK9CkLNb_Yfcg3o-uugmGV-iEmQ_FXtDk0in7oKmdm6yujGbdm-BR69UWhtsX5b_-G4mgXxfW5b8cSp7Y-q-e2r1LtyXJ5Bl76eOk3ncBIOdg6M07y49sofxXRO3VF8QPYSdiaC8zMAWowpatpyW0yBsjXfLFKm8-xOV8vhlf0RCrtH83HYoT1NsU365frAW1y9N5L7NmUVXQuBEpmoRje3E8FCuA.eyJub25jZSI6ImFuWU1GVndXYy9Vcms4U280R252SFVPNXgxeHo1Z3QyaThkRnJzUmI5WEU9IiwidGltZXN0YW1wTXMiOjE0OTkwNjU4NjkyMTMsImFwa1BhY2thZ2VOYW1lIjoiY29tLmNhdGhlcmluZS5zZWN1cml0eXNhbXBsZSIsImFwa0RpZ2VzdFNoYTI1NiI6InFOQTZ0UWo1MnZ5cGlTV0NVTTJaVkVyekpkeVRSMlovTUVjRFhGYU1iVlU9IiwiY3RzUHJvZmlsZU1hdGNoIjp0cnVlLCJleHRlbnNpb24iOiJDYlNsSFZOd21KZ20iLCJhcGtDZXJ0aWZpY2F0ZURpZ2VzdFNoYTI1NiI6WyI5bUxGUzNlSFdPQmNIbEE0TW1PRG1mR3Z6Z2tiZzJZU1Eyei93dzlsQ2Z3PSJdLCJiYXNpY0ludGVncml0eSI6dHJ1ZX0.rrRFPRWVLIk6DYo81UysVcWp40ql8wIhmmIehRDZA9QDknlE5lya4wMDFvvMmpMuN7uS5bKBJdz9kSjdxpmDruoLZNHRDqyXQBX_N3OdTzv4Hta3fCBEVzroz_L3qR_je2IM5KIdQHiSo2ssZOTahLdij3eTCzJx5bJXIcxfleQ2AoXh1ONDyE7qIQFSQhQ_QMYPCMUkzTi1vJaiV5EPCQVrnGFk1XClBtZnVPSudM-61PmcLnQr7OvJYeSvxwclSr7BEhHrhZXxg-Vp5cCAGINAoeop7zfrXdU0SK-9P891JyoGE2XZB3yEcR0l_j8zJpPWfjTGBpKDqIQwa-y32g";

	/**
	 * 
	 */
	private static void testJWS() {
		try {
			JwsHelper jwsHelper = new JwsHelper(attestationJws);
			System.out.println("alg:" + jwsHelper.getAlg());
			AttestationResult result = new AttestationResult(jwsHelper.getDecodedPayload());
			System.out.println(result);

			List<X509Certificate> certs = jwsHelper.getX5CCertificates();

			X509Certificate rootCert = CertificatesManager.downloadCaIssuersCert(KeySet.GIAG2_URL);

			// Just verify one of the certificates which is belonged to
			// "attest.android.com" in this case.
			boolean isJwsHeaderLegal = false;
			for (X509Certificate cert : certs) {
				boolean isValid = CertificatesManager.validate(cert, rootCert);
				CertificatesManager.printCertificatesInfo(cert);
				if (isValid == true)
					isJwsHeaderLegal = true;
			}

			// Verify the signature of JWS
			boolean isJwsSignatureLegal = jwsHelper.verifySignature(Algorithm.ALG_SHA256_WITH_RSA);
			if (isJwsHeaderLegal && isJwsSignatureLegal)
				System.out.println("Android attestion JWS 通過驗證！");
			else
				System.out.println("Android attestion JWS 驗證失败！");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testCryptography() {
		try {
			KeystoreManager.printKeyStoreInfo(KeySet.KEYSTORE_PATH, KeySet.KEYSTORE_TYPE, KeySet.KEYSTORE_PW);

			TrackLog log1 = new TrackLog("General a single key");
			Analysis.startTracking(log1);
			String secretKeyString = KeystoreManager.generateKeyString();
			Analysis.endTracking(log1);
			Analysis.printTrack(log1);

			TrackLog log2 = new TrackLog("Decrypt the key");
			Analysis.startTracking(log2);
			KeystoreManager.converStringToKey(secretKeyString);
			Analysis.endTracking(log2);
			Analysis.printTrack(log2);

			final TrackLog log3 = new TrackLog("General a RSA keyPair");
			Analysis.startTracking(log3);
			KeystoreManager.generateRSAKeyPair(new RSACallback() {

				@Override
				public void onResponse(String modulus, String exponent) {
					Analysis.endTracking(log3);
					Analysis.printTrack(log3);

					try {
						TrackLog log4 = new TrackLog("Decrypt the RSA keyPair");
						Analysis.startTracking(log4);
						KeystoreManager.converStringToPublicKey(modulus, exponent);
						Analysis.endTracking(log4);
						Analysis.printTrack(log4);
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

				@Override
				public void onResponse(PrivateKey privateKey, PublicKey publicKey) {

				}
			});

			TrackLog log5 = new TrackLog("General a keypair from the keystore");
			Analysis.startTracking(log5);
			KeystoreManager.getKeyPairFromKeystore();
			Analysis.endTracking(log5);
			Analysis.printTrack(log5);

			TrackLog log6 = new TrackLog("Encrypt a string from the keyPair");
			Analysis.startTracking(log6);
			byte[] msg = CipherKit.encrypt("你好啊！");
			Analysis.endTracking(log6);
			Analysis.printTrack(log6);

			TrackLog log7 = new TrackLog("Decrypt a string from the keyPair");
			Analysis.startTracking(log7);
			System.out.println(CipherKit.decrypt(msg));
			Analysis.endTracking(log7);
			Analysis.printTrack(log7);

			final TrackLog log8 = new TrackLog("Encrypt a string from the secretKey key");
			Analysis.startTracking(log8);
			final Key sKey = KeystoreManager.generateKey();
			CipherKit.encryptDES(sKey, "Hi there!", new DESCallback() {

				@Override
				public void onResponse(byte[] iv, byte[] message) {
					Analysis.endTracking(log8);
					Analysis.printTrack(log8);

					try {
						TrackLog log9 = new TrackLog("Decrypt a string from the secretKey key");
						Analysis.startTracking(log9);
						System.out.println(CipherKit.decryptDES(sKey, iv, message));
						Analysis.endTracking(log9);
						Analysis.printTrack(log9);
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
			Analysis.startTracking(log10);
			KeystoreManager.generateRSAKeyPair(new RSACallback() {

				@Override
				public void onResponse(String modulus, String exponent) {
				}

				@Override
				public void onResponse(PrivateKey privateKey) {
				}

				@Override
				public void onResponse(PrivateKey privateKey, PublicKey publicKey) {

					try {
						byte[] signature = MessageDigestKit.signFiles("assets/metals.jpg", Algorithm.ALG_MD5_WITH_RSA,
								privateKey);
						Analysis.endTracking(log10);
						Analysis.printTrack(log10);

						TrackLog log11 = new TrackLog("verifing the file with signature ");
						Analysis.startTracking(log11);
						boolean islegel = MessageDigestKit.verifyFileSignature(signature, Algorithm.ALG_MD5_WITH_RSA,
								"assets/metals.jpg", publicKey);

						System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
						System.out.println("Signature: Is this file legel? " + islegel);
						Analysis.endTracking(log11);
						Analysis.printTrack(log11);

						signature = MessageDigestKit.sign(attestationJws.getBytes(), Algorithm.ALG_SHA256_WITH_RSA,
								privateKey);
						islegel = MessageDigestKit.verifySignature(signature, Algorithm.ALG_SHA256_WITH_RSA, publicKey,
								attestationJws.getBytes());

						System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));
						System.out.println("Signature: Is this file legel? " + islegel);
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SignatureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException
				| UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException
				| IOException | InvalidKeySpecException |

				ClassNotFoundException e1) {
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