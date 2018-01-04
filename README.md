trees/Algorithms
===================


## Sort
- [Bubble Sort]
- [Insertion Sort]
- [Merge Sort]
- Heap sort
- Quick sort
- [Selection sort]

## Simulating Turing Machine
  - [1 + a positive integer]

## [Sequence]
  - 2 ways to increase the capacity.

> **Tips**
> - increaseArray() - In the worst case, it spends O(n) on increasing the capacity of a vector by one. But its space utilization is always 100%. <br>
> - doubleArray() - If you double the capacity each time, you might get lower space utilization (the lowest is 50%), but it will just spend O(1). <br>
> If you need a large array, double the capacity of your array is more efficient. In general, it's better.

  - Insertion/removing/searching
  - Shift continued-elements (It's also a high level method to remove continued-elements)
  - Update each element with an interface
  - Remove duplicates with/without sorting

  > **Tips**
  > - removeDuplicates() - It's not necessary to sort your array before you remove duplicated elements. O(n^2) <br>
  > - removeDuplicatesAndSort1(), removeDuplicatesAndSort2() - It just spend O(n log n), but you've got to sort the whole sequence before removing duplicates. It's a little like using sort()+unique() with vector in C++. <br>
  > - Using removeDuplicatesAndSort1() or removeDuplicatesAndSort2() when you don't want to return a unsorted array. <br>
  > - removeDuplicatesAndSort2() is more efficient and smarter then removeDuplicatesAndSort1() which replaces elements instead of removing duplicates.

  - [Sequence Searching] is used to **search a sorted array and find an element.** If an array contains the element, it'll return where the element is in that array or the latest position while there're duplicated elements.
  - As I was saying, if the element hasn't been found, this algorithm would return an index referring to the value(array[index] < element < array[index+1]).
      - Two ways to binary search (Normally, binSearch2() is more efficient than binSearch())
      - Fibonacci Searching

## List
  - [MyArrayList]
    - Base on Array
  - [MyLinkedList]
    - Base on Node, a user-defined Object includes a predecessor, a value, and a successor.
    - Remove duplicates

## Stack
>  push() is used to return the top object and remove it. peek() is to get the top object without removing it. <br>
>  pop() is to add an object on the top . <br>
>  It spends O(1) to add and remove an object. <br>
>  But if you'd like to put or delete an element at the bottom, it'd spend O(n) because the stack must move every object below the first (bottom) object.

  - [NumberSystem] convertDecimalToOthers()
  - [Others] isBracketsCorrect()
  - [Stack-sortable permutation]

## Queue
  -

## Graph

Traversal - to simplify Graph and make it looks like a sequence. It's a powerful way to transfer a unsequenced data structure to a sequence.

### Undirected Graph
  - [UndirectedGraph]

### Directed Graph
|    | BFS | DFS |
| ---- | ---- | ---- |
| next node | neighbor | child |
| speed |  | faster |
| memory requirement |  | less |
| data type | Queue | Stack |

  - Breadth-first search (BFS) - Choose a vertex (root) as the beginning of a graph and visit every vertex. This is just like level order traversal of the Tree.
  - Depth-first search (DFS) - Choose a vertex (root) as the beginning of a graph and visit every vertex.
  - Connected Graph - While there is at least one path between every pair of vertices, the graph will be connect. If you wanna find out a path which is able to traversal the whole graph, try to run BFS from every vertex and make sure the status of each vertex is VISITED. See more in ``` public Vertex<E> bfs(){...} ```
  - [DirectedGraph]

  > There are two arrays, I stores all vertexes in vertexes[] and another array adjMatrix[][], a two-dimensional Edge<E> array, which is used to copy down edges. For example, if adjMatrix[i][j] is not empty, that means there's an edge from i to j.<br>
  > Edge<E> is a user-defined class. It contains data, weight and status.

## Tree

### Binary Tree

![tree][1]
  - It's a special case of Graph.
  - Performance

|    | vector | list | tree |
| ---- | ---- | ---- | ---- |
| search | O | X | O |
| insert / remove | X | O | O |


  - Imaginary Tree is sort of List<List>
  - Tree traversal (Pre-order, in-order and post-order)
  - [MyBinaryTree]

  > - Insert / remove nodes or subtrees <br>
  > - Calculate the height of subtrees <br>
  > - Depth-first search (DFS) in recursion and non-recursion ways<br>
  > - Find out the successor of any node by in-order traversal.<br>
  > **Speed: traversePreNR2() > traversePreNR1() > traversePre() <br>
  > It's incredible that running a nested loop (traversePreNR2) is faster than recursion and both takes O(n)**


### Binary Search Tree (BST)

  - A BST projection is kind of like a sorted sequence, you can easily get a sorted data structure by in-order traversal.
  - In this case, I assumed that there are no duplicated keys in the BST. Every operation based on this scenario.
  - [MyBinarySearchTree]

  > It extends [MyBinaryTree]. The only one difference between the node ([BNode]) of MyBinaryTree and the node ([BSTNode]) of  MyBinarySearchTree is key, which means you sort the whole tree by keys and you are able to assign null or duplicated values. It's a key-value pair. Or you can just remove value if you don't need it.<br><br>
  > - [MyBinaryTree] implements BinaryTree the interface <br>
  > - [BinarySearchTreeImpl] implements BinarySearchTree the interface and extends [MyBinaryTree]<br>
  > If you want to operate BST, you have to create [MyBinarySearchTree] instead of [BinarySearchTreeImpl], however. Because I want to hide some public methods which are not supported for BST are built by [MyBinaryTree] <br>

### AVL Tree

  - An AVL tree is a balanced binary search tree. For every node, the heights of left and right children of every node differs by at most 土 1 (|hl - hr| <= 1)

  - **Single rotations**

  ![rotation][2]

  ```java
  zig(node);
  ```

  and

  ```java
  zag(node);
  ```

  - **Double rotations**

  ![double rotation][3]

  ```java
  //parent-node-child looks like "<"
  left_rightRotate(node);
  ```

  and

  ```java
  //parent-node-child looks like ">"
  right_leftRotate(node)
  ```

  - keep balance while inserting and removing

  ![tree][4]
  ![tree][5]
  ![tree][6]
  ![tree][7]

  - [MyAVLTree]

## Classy tree

### Splay tree

- Splay tree practices pretty well when the recently accessed node may be accessed again.
- Splay tree moves the selected node to the root after searching, inserting and removing.
- Splaying the node every two levels (parent and grandparent) makes splaying more efficient then splaying the node every level.
- [MySplayTree]

>If you want to find out a node in a linked list, it would traverse in order from the beginning. It means that your target  which is in the front of the list would be accessed faster, but if you want to read the last node, however. You have to wait O(n). So what if we move the recently accessed node to the head of the list, we can access the node very quickly. That's what Splay tree does.

### B-Tree

- Every node contains more than two keys and branches, it seems like a binary tree merges some of its nodes into a super node and that's why B-tree looks flatter and wider.
- There are 2 tips about memory you must know -- First of all, it spends a great deal of time to retrieve data from external memory (I/O). Then, the average time of reading a byte or a blocks of data are almost the same. To pick up speed of accessing data, you would like to take a bunch of data a time (just like you can obtain a lot of data by accessing a node) or you'd rather retrieve data from main memory (RAM) 100 times than external memory (disk) once (B-tree always searches data from the first level). And that's what B-tree is designed to optimize filesystem.

![B-tree][8]

- Insertion/removing/searching
- Underflow and overflow issues
- [MyBTree]


## Dictionary

### Hashing
- There are four types of structures - Call by rank(Vector), call by position(List), call by key(BST) and call by value(Hashing).
- Let's say there are 100M telephones and 25K telephone numbers and you're going to design a data structure to save  and search them.  If you loop the data to find out a number, it'll spend only O(1). But there are 100M telephones. That means your accurate performance is 25K/100M = 0.025%. It's terrible. The point is there are too many redundant space(telephones). To optimize the space usage is what bucket array or hash table does.

> There are many ways to hash. Here in [HashFunctions], I made some hashing functions:
> 1. Hashing by remainder
> 2. Hashing by MAD
> 3. Hashing by selecting digits
> 4. Hashing by median of square numbers
> 5. Hashing by folding
> 6. Hashing by rotation + folding
> 7. Hashing by folding + XOR
> 8. Hashing by rotation + folding + XOR

**Collisions**

value = hash(key). As you hash keys, you almot can't avoid to get the same value by different keys. That's what we call "collision".

Open addressing (or closed hashing)
> Basically, it's support to be mutiple slots (linked list chaining).
> You put duplicates into the same value list. Using LinkedList to minimize space. Now you could imagine this hash table looks like a tree. But it is hard to cache because CPU caches sequence addresses. And you probably won't visit the value in order of writing.
> So we create a chain and you put every bucket (which contains value comes from different keys) in sequence. Those buckets have fixed capacity. It means values sometimes can't be put into the bucket where they are support to be. Instead, they are assigned to the other buckets. This chain is called probing sequence/chain is a list.

Two ways to probe:
- [LinearProbing] : hash(key) + 1, hash(key) + 2, ... hash(key) + k
- [QuadraticProbing] : [hash(key) + 1^2] % M, [hash(key) + 2^2] % M, ... [hash(key) + k^2] % M
- [FermatQuadraticProbing] : [hash(key) + 1^2] % M, [hash(key) - 1^2] % M, [hash(key) + 2^2] % M, ... [hash(key) + k^2] % M, [hash(key) - k^2] % M

![quadratic probing][13]

In Quadratic Probing, when M is a prime number and M = 4x+3 you would get the most uniformly distributed hash table.

![Fermat's theorem on sums of two squares][14]

## [Others]
  - Hailstone
  - Fibonacci
  - Catalan

### Cryptography

|  API | description |
| ---- | ---- |
| [KeystoreManager] | 1. Generate a secret key or a keyPair. <br> 2. Put or get the secret key/keyPair from a keyStore. |
| [MessageDigestKit] | Verify files with its message digest or signature. |
| [CipherKit] | Encrypt or decrypt messages by the secret key/keyPair. |
| [CertificatesManager] | 1. Analyse and retrieve data such like certificate extensions, the signature and so on from a certificate. <br> 2. Get data by OID. <br> 3. Validate a certificate. |
| [JwsHelper] | 1. Split JWS into 3 parts and decode them. <br> 2. Take Android SafetyNet attestation JWS for example, it validates its certificates and JWS signature. <br> 3. Here is [SecuritySample] an Android sample integrated attestation APIs and JWS validation. | 

 >Follow these steps:
>1. Extract the SSL certificate chain from the JWS message.
>2. Validate the SSL certificate chain and use SSL hostname matching to verify that the leaf certificate was issued to the hostname attest.android.com.
>3. Use the certificate to verify the signature of the JWS message.<br>


## Reference
  - [Algorithms, 4th Edition]
  - 邓俊辉《数据结构 C++语言版》第三版
  - [深入理解Android之Java Security]
  - [Server Authentication During SSL Handshake]
  - [Verifying a Certificate Chain]
  - [JSON Web Signature (JWS) draft-jones-json-web-signature-01]

## License

  ```
  Copyright 2017 Catherine Chen (https://github.com/Catherine22)

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
  ```

   [Merge Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/MergeSort.java>
   [Insertion Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/InsertionSort.java>
   [Bubble Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/BubbleSort.java>
   [Selection sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/SelectionSort.java>
   [Stack-sortable permutation]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/SortableStackPermutation.java>
   [Sequence]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/Sequence.java>
   [Sequence Searching]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/Search.java>
   [1 + a positive integer]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/turing_machine/TuringMachine.java>
   [MyArrayList]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/MyArrayList.java>
   [MyLinkedList]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/MyLinkedList.java>
   [MyBinaryTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MyBinaryTree.java>
   [MyBinarySearchTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MyBinarySearchTree.java>
   [BinarySearchTreeImpl]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/BinarySearchTreeImpl.java>
   [MyAVLTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MyAVLTree.java>
   [MySplayTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MySplayTree.java>
   [BSTNode]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/nodes/BSTNode.java>
   [BNode]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/nodes/BNode.java>
   [NumberSystem]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/NumberSystem.java>
   [Others]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/Others.java>
   [DirectedGraph]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/DirectedGraph.java>
   [UndirectedGraph]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/UndirectedGraph.java>
   [KeystoreManager]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/security/KeystoreManager.java>
   [MessageDigestKit]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/security/MessageDigestKit.java>
   [CipherKit]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/security/CipherKit.java>
   [CertificatesManager]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/security/CertificatesManager.java>
   [JwsHelper]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/security/JwsHelper.java>
   [MyBTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MyBTree.java>
   [RedBlackBSTImpl]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/RedBlackBSTImpl.java>
   [MyRedBlackBST]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/trees/MyRedBlackBST.java>
   [HashFunctions]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/dictionary/functions/>
   [LinearProbing]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/dictionary/LinearProbing.java>
   [QuadraticProbing]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/dictionary/QuadraticProbing.java>
   [FermatQuadraticProbing]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/dictionary/FermatQuadraticProbing.java>

   [Algorithms, 4th Edition]:<http://algs4.cs.princeton.edu/home/>
   [深入理解Android之Java Security]:<http://blog.csdn.net/innost/article/details/44081147>
   [Server Authentication During SSL Handshake]:<https://docs.oracle.com/cd/E19693-01/819-0997/aakhc/index.html>
   [Verifying a Certificate Chain]:<https://docs.oracle.com/cd/E19316-01/820-2765/gdzea/index.html>
   [JSON Web Signature (JWS) draft-jones-json-web-signature-01]:<http://self-issued.info/docs/draft-jones-json-web-signature-01.html>
   [SecuritySample]:<https://github.com/Catherine22/SecuritySample>
   [Double red video]:<https://www.youtube.com/watch?v=m9tse9Gr2pE>

  [1]: https://github.com/Catherine22/Algorithms/blob/master/res/tree.png
  [2]: https://github.com/Catherine22/Algorithms/blob/master/res/tree_rotation.png
  [3]: https://github.com/Catherine22/Algorithms/blob/master/res/tree_double_rotation.png
  [4]: https://github.com/Catherine22/Algorithms/blob/master/res/i_s.png
  [5]: https://github.com/Catherine22/Algorithms/blob/master/res/i_d.png
  [6]: https://github.com/Catherine22/Algorithms/blob/master/res/r_s.png
  [7]: https://github.com/Catherine22/Algorithms/blob/master/res/r_d.png
  [8]: https://github.com/Catherine22/Algorithms/blob/master/res/b-tree.png
  [9]: https://github.com/Catherine22/Algorithms/blob/master/res/B_R_BST.png
  [10]: https://github.com/Catherine22/Algorithms/blob/master/res/rr1_1.png.png
  [11]: https://github.com/Catherine22/Algorithms/blob/master/res/rr1_2.png.png
  [12]: https://github.com/Catherine22/Algorithms/blob/master/res/rr2.png
  [13]: https://github.com/Catherine22/Algorithms/blob/master/res/quadratic_probing.png
  [14]: https://github.com/Catherine22/Algorithms/blob/master/res/Fermat_s_theorem.png
