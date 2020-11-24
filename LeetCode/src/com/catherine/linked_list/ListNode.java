package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 18/11/2020
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int x) {
        val = x;
        next = null;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
