package com.catherine.linked_list;

/**
 * @author : Catherine
 * @created : 17/11/2020
 * <p>
 * Design Linked List
 * <p>
 * Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
 * A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node.
 * If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.
 * <p>
 * Implement the MyLinkedList class:
 * <p>
 * MyLinkedList() Initializes the MyLinkedList object.
 * int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.
 * void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
 * void addAtTail(int val) Append a node of value val as the last element of the linked list.
 * void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. If index equals the length of the linked list, the node will be appended to the end of the linked list. If index is greater than the length, the node will not be inserted.
 * void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input
 * ["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
 * [[], [1], [3], [1, 2], [1], [1], [1]]
 * Output
 * [null, null, null, null, 2, null, 3]
 * <p>
 * Explanation
 * MyLinkedList myLinkedList = new MyLinkedList();
 * myLinkedList.addAtHead(1);
 * myLinkedList.addAtTail(3);
 * myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
 * myLinkedList.get(1);              // return 2
 * myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
 * myLinkedList.get(1);              // return 3
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 0 <= index, val <= 1000
 * Please do not use the built-in LinkedList library.
 * At most 2000 calls will be made to get, addAtHead, addAtTail,  addAtIndex and deleteAtIndex.
 * <p>
 * https://leetcode.com/explore/learn/card/linked-list/209/singly-linked-list/1290/
 */
public class MyLinkedList {
    class Node {
        int val = -1;
        Node prev = null;
        Node next = null;

        Node() {

        }

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public Object clone() {
            try {
                return (Node) super.clone();
            } catch (CloneNotSupportedException e) {
                return new Node(this.val, this.prev, this.next);
            }
        }
    }

    int len = 0;
    Node head = null;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
        // Do nothing
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index >= len) {
            return -1;
        }

        int countdown = index;
        Node ptr = this.head;
        while (countdown > 0) {
            ptr = ptr.next;
            countdown--;
        }
        return ptr.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        if (this.head == null) {
            this.head = new Node(val);
        } else {
            Node newNode = new Node(val);
            newNode.next = (Node) this.head.clone();
            newNode.next.prev = newNode;
            this.head = newNode;
        }
        len++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        if (this.head == null) {
            this.head = new Node(val);
        } else {
            Node ptr = this.head;
            while (ptr.next != null) {
                ptr = ptr.next;
            }
            Node newNode = new Node(val);
            ptr.next = newNode;
            newNode.prev = ptr;
        }
        len++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > len) {
            return;
        }

        if (len == 0) {
            this.head = new Node(val);
            len++;
            return;
        }

        if (index == 0) {
            Node newNode = new Node(val);
            newNode.next = (Node) this.head.clone();
            this.head = newNode;
            len++;
            return;
        }

        int count = 0;
        Node ptr = this.head;
        Node newNode = new Node(val);
        while (count < index - 1) {
            ptr = ptr.next;
            count++;
        }

        if (ptr.next != null) {
            newNode.next = (Node) ptr.next.clone();
        }

        ptr.next = newNode;
        newNode.prev = ptr;
        len++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index >= len) {
            return;
        }

        if (index == 0) {
            if (this.head.next != null) {
                this.head = (Node) this.head.next.clone();
                this.head.prev = null;
            } else {
                this.head = null;
            }
            len--;
            return;
        }

        int count = 0;
        Node ptr = this.head;
        while (count < index - 1) {
            ptr = ptr.next;
            count++;
        }

        if (index == len - 1) {
            ptr.next = null;
        } else {
            ptr.next = ptr.next.next;
            ptr.next.prev = ptr;
        }
        len--;
    }
}
