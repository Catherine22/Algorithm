package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 21/11/2020
 * <p>
 * Palindrome Linked List
 * <p>
 * Given a singly linked list, determine if it is a palindrome.
 * <p>
 * Example 1:
 * <p>
 * Input: 1->2
 * Output: false
 * Example 2:
 * <p>
 * Input: 1->2->2->1
 * Output: true
 * Follow up:
 * Could you do it in O(n) time and O(1) space?
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/219/classic-problems/1209/
 */
public class PalindromeList {
    public boolean isPalindrome(ListNode head) {
        int len = 0;
        boolean isOddLen = false;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            len++;
        }

        if (len % 2 == 1) {
            isOddLen = true;
        }

        len = len / 2;
        ptr = head;
        ListNode tail = null;
        while (len > 0) {
            if (tail == null) {
                tail = new ListNode(ptr.val);
            } else {
                tail = new ListNode(ptr.val, tail);
            }
            ptr = ptr.next;
            len--;
        }

        if (isOddLen && ptr != null) {
            ptr = ptr.next;
        }

        while (ptr != null) {
            if (tail.val != ptr.val) {
                return false;
            }
            ptr = ptr.next;
            tail = tail.next;
        }

        return true;
    }
}
