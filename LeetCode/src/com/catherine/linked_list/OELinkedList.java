package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 21/11/2020
 * <p>
 * Odd Even Linked List
 * <p>
 * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.
 * <p>
 * You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
 * <p>
 * Example 1:
 * <p>
 * Input: 1->2->3->4->5->NULL
 * Output: 1->3->5->2->4->NULL
 * Example 2:
 * <p>
 * Input: 2->1->3->5->6->4->7->NULL
 * Output: 2->3->6->7->1->5->4->NULL
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The relative order inside both the even and odd groups should remain as it was in the input.
 * The first node is considered odd, the second node even and so on ...
 * The length of the linked list is between [0, 10^4].
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/219/classic-problems/1208/
 */
public class OELinkedList {
    public ListNode oddEvenList(ListNode head) {
        ListNode ptr = head;
        ListNode sortedHead = null;
        ListNode sortedPtr = null;
        int pos = 1;

        while (ptr != null) {
            if (pos % 2 == 1) {
                if (sortedHead == null) {
                    sortedHead = new ListNode(ptr.val);
                    sortedPtr = sortedHead;
                } else {
                    sortedPtr.next = new ListNode(ptr.val);
                    sortedPtr = sortedPtr.next;
                }
            }
            pos++;
            ptr = ptr.next;
        }

        ptr = head;
        pos = 1;

        while (ptr != null) {
            if (pos % 2 == 0) {
                if (sortedHead == null) {
                    sortedHead = new ListNode(ptr.val);
                    sortedPtr = sortedHead;
                } else {
                    sortedPtr.next = new ListNode(ptr.val);
                    sortedPtr = sortedPtr.next;
                }
            }
            pos++;
            ptr = ptr.next;
        }


        return sortedHead;
    }
}
