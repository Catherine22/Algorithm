package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 26/11/2020
 * <p>
 * Rotate List
 * <p>
 * Given the head of a linked list, rotate the list to the right by k places.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [4,5,1,2,3]
 * Example 2:
 * <p>
 * <p>
 * Input: head = [0,1,2], k = 4
 * Output: [2,0,1]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is in the range [0, 500].
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 109
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/213/conclusion/1295/
 */
public class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k < 1) {
            return head;
        }

        ListNode headPtr = head;
        int len = 0;
        while (headPtr != null) {
            headPtr = headPtr.next;
            len++;
        }

        headPtr = head;
        int moves = k % len;
        int truncates = len - moves;
        ListNode secondList = null;
        ListNode secondPtr = null;

        while (true) {
            if (secondList == null) {
                secondList = new ListNode(headPtr.val);
                secondPtr = secondList;
            } else {
                secondPtr.next = new ListNode(headPtr.val);
                secondPtr = secondPtr.next;
            }
            truncates--;
            if (truncates == 0) {
                break;
            }
            headPtr = headPtr.next;
        }

        ListNode fullList = null;
        ListNode fullPtr = null;
        while (moves > 0) {
            headPtr = headPtr.next;
            if (fullList == null) {
                fullList = new ListNode(headPtr.val);
                fullPtr = fullList;
            } else {
                fullPtr.next = new ListNode(headPtr.val);
                fullPtr = fullPtr.next;
            }
            moves--;
        }

        if (fullList != null) {
            fullPtr.next = secondList;
        } else {
            fullList = secondList;
        }

        return fullList;
    }
}
