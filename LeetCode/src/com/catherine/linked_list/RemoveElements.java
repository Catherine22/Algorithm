package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 21/11/2020
 * <p>
 * Remove Linked List Elements
 * <p>
 * Remove all elements from a linked list of integers that have value val.
 * <p>
 * Example:
 * <p>
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/219/classic-problems/1207/
 */
public class RemoveElements {
    public ListNode removeElements(ListNode head, int val) {
        ListNode ptr = head;
        ListNode res = null;
        ListNode resPtr = null;

        while (ptr != null) {
            if (ptr.val != val) {
                if (resPtr == null) {
                    resPtr = new ListNode(ptr.val);
                    res = resPtr;
                } else {
                    resPtr.next = new ListNode(ptr.val);
                    resPtr = resPtr.next;
                }
            }
            ptr = ptr.next;
        }

        return res;
    }
}
