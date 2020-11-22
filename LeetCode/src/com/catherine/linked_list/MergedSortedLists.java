package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 22/11/2020
 * <p>
 * Merge Two Sorted Lists
 * <p>
 * Merge two sorted linked lists and return it as a new sorted list. The new list should be made by splicing together the nodes of the first two lists.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: l1 = [1,2,4], l2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 * Example 2:
 * <p>
 * Input: l1 = [], l2 = []
 * Output: []
 * Example 3:
 * <p>
 * Input: l1 = [], l2 = [0]
 * Output: [0]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both l1 and l2 are sorted in non-decreasing order.
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/213/conclusion/1227/
 */
public class MergedSortedLists {
    ListNode l3 = null;
    ListNode ptr3 = null;

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;

        while (true) {
            if (ptr1 != null && ptr2 != null) {
                if (ptr1.val < ptr2.val) {
                    safeAdd(ptr1.val);
                    ptr1 = ptr1.next;
                } else {
                    safeAdd(ptr2.val);
                    ptr2 = ptr2.next;
                }
            } else if (ptr1 != null) {
                safeAdd(ptr1.val);
                ptr1 = ptr1.next;
            } else if (ptr2 != null) {
                safeAdd(ptr2.val);
                ptr2 = ptr2.next;
            } else {
                break;
            }
        }

        return l3;
    }

    private void safeAdd(int val) {
        if (l3 == null) {
            l3 = new ListNode(val);
            ptr3 = l3;
        } else {
            ptr3.next = new ListNode(val);
            ptr3 = ptr3.next;
        }
    }
}
