package com.catherine.linked_list;

import java.util.Stack;

/**
 * @author : Catherine
 * @created : 21/11/2020
 * <p>
 * Reverse Linked List
 * <p>
 * Reverse a singly linked list.
 * <p>
 * Example:
 * <p>
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 * <p>
 * Follow up:
 * <p>
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/219/classic-problems/1205/
 */
public class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode r = head;
        Stack<Integer> stack = new Stack();
        while (r != null) {
            stack.push(r.val);
            r = r.next;
        }

        r = head;
        while (r != null) {
            r.val = stack.pop();
            r = r.next;
        }

        return head;
    }
}
