package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 26/11/2020
 */
class Node {
    int val;
    Node prev;
    Node next;
    Node child;
    Node random;

    Node() {

    }

    Node(int val) {
        this.val = val;
    }
}
