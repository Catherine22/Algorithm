package com.catherine.linked_list;

import java.util.Stack;

/**
 * @author : Catherine
 * @created : 26/11/2020
 * <p>
 * Flatten a Multilevel Doubly Linked List
 * <p>
 * You are given a doubly linked list which in addition to the next and previous pointers, it could have a child pointer, which may or may not point to a separate doubly linked list. These child lists may have one or more children of their own, and so on, to produce a multilevel data structure, as shown in the example below.
 * <p>
 * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the first level of the list.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * Output: [1,2,3,7,8,11,12,9,10,4,5,6]
 * Explanation:
 * <p>
 * The multilevel linked list in the input is as follows:
 * <p>
 * <p>
 * <p>
 * After flattening the multilevel linked list it becomes:
 * <p>
 * <p>
 * Example 2:
 * <p>
 * Input: head = [1,2,null,3]
 * Output: [1,3,2]
 * Explanation:
 * <p>
 * The input multilevel linked list is as follows:
 * <p>
 * 1---2---NULL
 * |
 * 3---NULL
 * Example 3:
 * <p>
 * Input: head = []
 * Output: []
 * <p>
 * <p>
 * How multilevel linked list is represented in test case:
 * <p>
 * We use the multilevel linked list from Example 1 above:
 * <p>
 * 1---2---3---4---5---6--NULL
 * |   |   |
 * NULL NULL 7---8---9---10--NULL
 * |
 * 11--12--NULL
 * The serialization of each level is as follows:
 * <p>
 * [1,2,3,4,5,6,null]
 * [7,8,9,10,null]
 * [11,12,null]
 * To serialize all levels together we will add nulls in each level to signify no node connects to the upper node of the previous level. The serialization becomes:
 * <p>
 * [1,2,3,4,5,6,null]
 * [null,null,7,8,9,10,null]
 * [null,11,12,null]
 * Merging the serialization of each level and removing trailing nulls we obtain:
 * <p>
 * [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * Number of Nodes will not exceed 1000.
 * 1 <= Node.val <= 10^5
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/213/conclusion/1225/
 */
public class FlattenMultiLevelList {
    public Node flatten(Node head) {
        if (head == null) {
            return null;
        }

        Node ptr = head;
        boolean skip;
        Stack<Node> backlog = new Stack<>();

        Node flattenHead = null;
        Node flattenPtr = null;
        Node tmp;

        while (true) {
            skip = false;

            if (ptr.child != null) {
                if (flattenHead == null) {
                    flattenHead = new Node(ptr.val);
                    flattenPtr = flattenHead;
                } else {
                    tmp = new Node(ptr.val);
                    tmp.prev = flattenPtr;
                    flattenPtr.next = tmp;
                    flattenPtr = flattenPtr.next;
                }

                if (ptr.next != null) {
                    backlog.add(ptr.next);
                }

                ptr = ptr.child;
                skip = true;
            }

            if (!skip) {
                if (ptr.next != null) {
                    if (flattenHead == null) {
                        flattenHead = new Node(ptr.val);
                        flattenPtr = flattenHead;
                    } else {
                        tmp = new Node(ptr.val);
                        tmp.prev = flattenPtr;
                        flattenPtr.next = tmp;
                        flattenPtr = flattenPtr.next;
                    }
                    ptr = ptr.next;
                } else {
                    if (flattenHead == null) {
                        flattenHead = new Node(ptr.val);
                        flattenPtr = flattenHead;
                    } else {
                        tmp = new Node(ptr.val);
                        tmp.prev = flattenPtr;
                        flattenPtr.next = tmp;
                        flattenPtr = flattenPtr.next;
                    }

                    if (backlog.size() > 0) {
                        ptr = backlog.pop();
                    } else {
                        return flattenHead;
                    }
                }
            }
        }
    }
}
