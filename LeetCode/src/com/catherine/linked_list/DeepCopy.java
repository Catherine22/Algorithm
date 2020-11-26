package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 26/11/2020
 * <p>
 * Copy List with Random Pointer
 * <p>
 * A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
 * <p>
 * Return a deep copy of the list.
 * <p>
 * The Linked List is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
 * <p>
 * val: an integer representing Node.val
 * random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null if it does not point to any node.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Example 2:
 * <p>
 * <p>
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * Example 3:
 * <p>
 * <p>
 * <p>
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * Example 4:
 * <p>
 * Input: head = []
 * Output: []
 * Explanation: Given linked list is empty (null pointer), so return null.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * -10000 <= Node.val <= 10000
 * Node.random is null or pointing to a node in the linked list.
 * The number of nodes will not exceed 1000.
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/213/conclusion/1229/
 */
public class DeepCopy {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Node ptr = head;
        Node copy = null;
        Node copyPtr = null;
        while (ptr != null) {
            if (copy == null) {
                copy = new Node(ptr.val);
                copyPtr = copy;
            } else {
                copyPtr.next = new Node(ptr.val);
                copyPtr = copyPtr.next;
            }
            ptr = ptr.next;
        }

        ptr = head;
        copyPtr = copy;
        Node headScan = head;
        Node copyScan = copy;
        while (ptr != null) {
            if (ptr.random == null) {
                copyPtr.random = null;
            } else {
                headScan = head;
                copyScan = copy;
                while (true) {
                    if (ptr.random == headScan) {
                        copyPtr.random = copyScan;
                        break;
                    }

                    headScan = headScan.next;
                    copyScan = copyScan.next;
                }
            }
            ptr = ptr.next;
            copyPtr = copyPtr.next;
        }

        return copy;
    }
}
