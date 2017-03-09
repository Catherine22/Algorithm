Algorithms
===================


## Sorting
- [Bubble Sort] (normal version, v2, v3)
- [Insertion Sort]
- [Merge Sort]
- Heap sort
- Quick sort
- [Selection sort]

## Simulate Turing Machine
  - [1 + a positive integer]

## [Sequence]
  - 2 ways to increase the capacity.

> **Note:**
> - increaseArray()
>  - However, in the worst case, It takes O(n) to increase the capacity of a vector by one.
>Space utilization is always 100%.
> - doubleArray()
>  - If you double the capacity each time, you might get lower space utilization (the lowest is 50%), but it only takes O(1).
> - Double the capacity is more efficient. In general, it's better.

  - Insert/remove/find a element
  - Shift continued-elements(It's also a high level method to remove continued-elements)
  - Update each element with an interface
  - Remove duplicates with/without sorting

  > **Note:**
  > - removeDuplicates()
  >  - O(n^2)
  >  - remove duplicates without sorting.
  > - removeDuplicatesAndSort1(), removeDuplicatesAndSort2()
  >  - O(n log n)
  >  - remove duplicates after sorting.
  >  - It's a little like using sort()+unique() in vector on C++.
  > - Using removeDuplicatesAndSort1() or removeDuplicatesAndSort2() if you don't need to return unsorted array.
  > - removeDuplicatesAndSort2() is more efficient and smarter then removeDuplicatesAndSort1() due to using replace method to remove duplicates.

  - [Sequence Searching] is used to **search a sorted array to find an element.** If an array contains an element, it'll return where the element is in the array and also return the latest position if there're duplicated elements.
  - And if the element wasn't found, this algorithm would return the index which refer to a value(array[index] < element < array[index+1]).
      - two ways to do binary searching (Usually, binSearch2() is more efficient than binSearch(), it's better)
      - Fibonacci Searching

## List
  - [MyArrayList]
    - Base on array
  - [MyLinkedList]
    - Base on Node, a user-defined Object including a predecessor, a value, and a successor.
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

## Tree
  - Performance

|  | vector | list | tree |
| -- | -- | -- | -- |
| search | O | X | O |
| insert / remove | X | O | O |

  - Imaginary Tree is sort of List<List>
  - [MyTree]


## Reference
  - [Algorithms, 4th Edition]

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
   [MyTree]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/MyTree.java>
   [NumberSystem]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/NumberSystem.java>
   [Others]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/utils/Others.java>
   [Algorithms, 4th Edition]:<http://algs4.cs.princeton.edu/home/>
