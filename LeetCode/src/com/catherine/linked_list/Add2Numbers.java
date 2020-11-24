package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 24/11/2020
 * <p>
 * Add Two Numbers
 * <p>
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * <p>
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * Example 2:
 * <p>
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * Example 3:
 * <p>
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/213/conclusion/1228/
 */
public class Add2Numbers {
    // must deal with overflow
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sum = null;
        ListNode head = null;
        boolean carried = false;

        while (true) {
            if (c1 != null) {
                if (sum == null) {
                    sum = new ListNode();
                    head = sum;
                }
                sum.val += c1.val;
                if (sum.val >= 10) {
                    carried = true;
                    sum.val -= 10;
                }
                c1 = c1.next;
            }

            if (c2 != null) {
                if (sum == null) {
                    sum = new ListNode();
                    head = sum;
                }
                sum.val += c2.val;
                if (sum.val >= 10) {
                    carried = true;
                    sum.val -= 10;
                }
                c2 = c2.next;
            }

            if (carried) {
                sum.next = new ListNode(1);
                carried = false;
            }

            if (c1 == null && c2 == null) {
                return head;
            } else {
                if (sum.next == null) {
                    sum.next = new ListNode(0);
                }
                sum = sum.next;
            }
        }
    }
}
