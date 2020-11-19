package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 19/11/2020
 * <p>
 * Remove Nth Node From End of List
 * <p>
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * <p>
 * Follow up: Could you do this in one pass?
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * Example 2:
 * <p>
 * Input: head = [1], n = 1
 * Output: []
 * Example 3:
 * <p>
 * Input: head = [1,2], n = 1
 * Output: [1]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/214/two-pointer-technique/1296/
 */
public class ReverseDeletion {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = 0;

        ListNode ptr = head;
        while (ptr != null) {
            len++;
            ptr = ptr.next;
        }

        if (head == null || n > len) {
            return null;
        }

        int d = len - n; // distance to the node
        ptr = head;

        while (true) {
            if (d == 0) {
                head = head.next;
                return head;
            } else if (d == 1) {
                if (ptr.next != null && ptr.next.next != null) {
                    ptr.next = ptr.next.next;
                } else {
                    ptr.next = null;
                }
                return head;
            } else {
                d--;
                ptr = ptr.next;
            }
        }
    }
}
