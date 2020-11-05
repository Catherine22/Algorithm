package com.catherine.bin_converter;

/**
 * @author : Catherine
 * @created : 04/11/2020
 * <p>
 * Convert Binary Number in a Linked List to Integer
 * <p>
 * Given head which is a reference node to a singly-linked list.
 * The value of each node in the linked list is either 0 or 1.
 * The linked list holds the binary representation of a number.
 * <p>
 * Return the decimal value of the number in the linked list.
 * <p>
 * E.g.
 * Input: head = [1,0,1]
 * Output: 5
 * Explanation: (101) in base 2 = (5) in base 10
 * <p>
 * Constraints:
 * <p>
 * The Linked List is not empty.
 * Number of nodes will not exceed 30.
 * Each node's value is either 0 or 1.
 * <p>
 * https://leetcode.com/explore/challenge/card/november-leetcoding-challenge/564/week-1-november-1st-november-7th/3516/
 */
public class Bin2Dec {
    public int getDecimalValue(ListNode head) {
        if (head == null) {
            return 0;
        }

        int len = 1;
        ListNode tmp = head.next;
        while (tmp != null) {
            len++;
            tmp = tmp.next;
        }

        if (len > 30) {
            return 0;
        }

        tmp = head;
        int pos = len - 1;
        int val = 0;
        while (pos >= 0) {
            if (tmp.val != 0 && tmp.val != 1) {
                return 0;
            }
            val += tmp.val * ((int) Math.pow(2, pos--));
            tmp = tmp.next;
        }

        return val;
    }
}
