package com.catherine.bin_converter;

/**
 * @author : Catherine
 * @created : 04/11/2020
 * <p>
 * Definition for singly-linked list.
 * @see Bin2Dec
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}