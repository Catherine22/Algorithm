Algorithms
===================


## Sorting
- [Bubble Sort]    
- [Insertion Sort]
- [Merge Sort]
- Quick sort
- Selection sort

## Simulate Turing Machine
  - [1 + a positive integer]

## [Sequence]
  - 2 ways to increase the capacity.

> **Note:**
> - increaseArray()
>  - However, in the worst cast, It takes O(n) to increase the capacity of a vector by one.
>Space utilization is always 100%.
> - doubleArray()
>  - If you double the capacity each time, you might get lower space utilization (the lowest is 50%), but it only takes O(1).
> - Double the capacity is more efficient. In general, it's better.

  - Insert/remove/find a element
  - Shift continued-elements(It's also a high level method to remove continued-elements)
  - Update each element by Interface
  - Remove duplicates with/without sorting

  > **Note:**
  > - removeDuplicates()
  >  - O(n^2)
  >  - remove duplicates without sorting.
  > - removeDuplicatesAndSort1(), removeDuplicatesAndSort2()
  >  - O(n log n)
  >  - remove duplicates after sorting.
  >  - It's just like using sort()+unique() in vector by C++.
  > - Using removeDuplicatesAndSort1() or removeDuplicatesAndSort2() if you don't need to return unsorted array.
  > - removeDuplicatesAndSort2() is more efficient and smarter then removeDuplicatesAndSort1() due to using replace method to remove duplicates.

## Reference
  - [Algorithms, 4th Edition]


   [Merge Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/MergeSort.java>
   [Insertion Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/InsertionSort.java>
   [Bubble Sort]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/sort/BubbleSort.java>
   [Sequence]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/data_type/Sequence.java>
   [1 + a positive integer]:<https://github.com/Catherine22/Algorithms/blob/master/src/com/catherine/turing_machine/TuringMachine.java>
   [Algorithms, 4th Edition]:<http://algs4.cs.princeton.edu/home/>
