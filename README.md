Algorithms
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
> - increaseArray() - In the worst case, It takes O(n) to increase the capacity of a vector by one, but it's space utilization is always 100%. <br>
> - doubleArray() - If you double the capacity each time, you might get lower space utilization (the lowest is 50%), but it only takes O(1). <br>
> If you need a large array, double the capacity of your array is more efficient. In general, it's better.

  - Insert/remove/find a element
  - Shift continued-elements (It's also a high level method to remove continued-elements)
  - Update each element with an interface
  - Remove duplicates with/without sorting

  > **Tips**
  > - removeDuplicates() - No need to sort your array before you remove duplicated elements. O(n^2) <br>
  > - removeDuplicatesAndSort1(), removeDuplicatesAndSort2() - It only takes O(n log n), but you've got to sort the whole sequence before removing duplicates. It's a little like using sort()+unique() with vector in C++. <br>
  > - Using removeDuplicatesAndSort1() or removeDuplicatesAndSort2() if you don't need to return unsorted array. <br>
  > - removeDuplicatesAndSort2() is more efficient and smarter then removeDuplicatesAndSort1() due to replacing elements instead of removing duplicates.

  - [Sequence Searching] is used to **search a sorted array to find an element.** If the array contains the element, it'll return where the element is in the array or the latest position while there're duplicated elements.
  - As I was saying, if the element've never been found, this algorithm would return the index referring to a value(array[index] < element < array[index+1]).
      - two ways to do binary searching (Usually, binSearch2() which is more efficient than binSearch() is better)
      - Fibonacci Searching

## List
  - [MyArrayList]
    - Base on array
  - [MyLinkedList]
    - Base on the node, a user-defined Object including a predecessor, a value, and a successor.
    - Remove duplicates

## Stack
>  You would push the top object with push(), and use peek() to get the top object without removing it. <br>
>  Add an object on the top with pop(). <br>
>  So, it takes O(1) to add and remove an object. <br>
>  But if you'd like to put or delete an element at the bottom, it takes O(n) so that the stack could move every object following the first (bottom) object.

  - [NumberSystem] convertDecimalToOthers()
  - [Others] isBracketsCorrect()
  - [Stack-sortable permutation]

## Queue
  -

## Graph

Traversal - to simplify Graph and make it looks like a sequence. It's a powerful way to transfer a unsequenced type to a sequence.


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

  > It extends [MyBinaryTree]. The only one difference between the node ([BNode]) of MyBinaryTree and the node ([BSTNode]) of  MyBinarySearchTree is key, which means you sort the whole tree by keys and you are able to assign null or duplicated values. It's a key-value pair. Or you can just remove value if you don't need it.<br>
  > [MyBinaryTree] implements BinaryTree the interface <br>
  > [MyBinarySearchTreeKernel] implements BinarySearchTree the interface and extends [MyBinaryTree]<br>
  > If you want to operate BST, you have to create [MyBinarySearchTree] instead of [MyBinarySearchTreeKernel], however. Because I want to hide some public methods which are unsupported for BST are built by [MyBinaryTree] <br>

### AVL Tree
  -
  - [MyAVLTree]

### Undirected Graph
  - [UndirectedGraph]

### Directed Graph
|    | BFS | DFS |
| ---- | ---- | ---- |
| next node | neighbor | child |
| speed |  | faster |
| memory requirement |  | less |
| data type | Queue | Stack |

  - Breadth-first search (BFS) - Choose a vertex (root) being the beginning of a graph and start to visit each vertex. It's just like level order traversal of the Tree.
  - Depth-first search (DFS) - Choose a vertex (root) being the beginning of a graph and start to visit each vertex.
  - Connected Graph - A graph is connected when there is a path between every pair of vertices. If you wanna find out a path which is able to traversal the whole graph, run bfs from every vertex and make sure the status of each vertex is VISITED. See more in ``` public Vertex<E> bfs(){...} ```
  - [DirectedGraph]

  > There are two arrays, I stored all vertexes in vertexes[] and another array adjMatrix[][] which is used to copy down edges is a two-dimensional Edge<E> array. For example, if adjMatrix[i][j] is not empty, it means there's an edge from i to j.<br>
  > An Edge<E> is a user-defined class, it contains data, weight and status.

## [Others]
  - Hailstone
  - Fibonacci
  - Catalan


## Reference
  - [Algorithms, 4th Edition]
  - [数据结构学习网]

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
   [MyBinaryTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/MyBinaryTree.java>
   [MyBinarySearchTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/MyBinarySearchTree.java>
   [MyBinarySearchTreeKernel]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/MyBinarySearchTreeKernel.java>
   [MyAVLTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/MyAVLTree.java>
   [BSTNode]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/nodes/BSTNode.java>
   [BNode]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/trees/nodes/BNode.java>
   [NumberSystem]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/NumberSystem.java>
   [Others]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/Others.java>
   [DirectedGraph]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/DirectedGraph.java>
   [UndirectedGraph]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/graphs/UndirectedGraph.java>
   [tree_pic]:<https://github.com/Catherine22/Algorithms/blob/master/res/tree.png>
   [Algorithms, 4th Edition]:<http://algs4.cs.princeton.edu/home/>
   [数据结构学习网]:<http://www.xiaohuanglv.com/list/course-list_all.html>


  [1]: https://github.com/Catherine22/Algorithms/blob/master/res/tree.png
