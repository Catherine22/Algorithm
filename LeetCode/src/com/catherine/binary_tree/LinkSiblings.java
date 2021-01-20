package com.catherine.binary_tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author : Catherine
 * @created : 19/01/2021
 * <p>
 * Populating Next Right Pointers in Each Node
 * <p>
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
 * <p>
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
 * <p>
 * Initially, all next pointers are set to NULL.
 * <p>
 * <p>
 * <p>
 * Follow up:
 * <p>
 * You may only use constant extra space.
 * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 * <p>
 * Follow up:
 * <p>
 * You may only use constant extra space.
 * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * <p>
 * Input: root = [1,2,3,4,5,6,7]
 * Output: [1,#,2,3,#,4,5,6,7,#]
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the given tree is less than 4096.
 * -1000 <= node.val <= 1000
 * <p>
 * https://leetcode.com/explore/learn/card/data-structure-tree/133/conclusion/994/
 */

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};

public class LinkSiblings {
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        int level = 0;  // debug
        List<Integer> collection = new LinkedList<>(); // debug

        Queue<Node> parents = new LinkedList<>();
        Queue<Node> siblings = new LinkedList<>();
        Node node = root;
        node.next = null;
        parents.offer(node);
        boolean isRight = false;

        Node res = node;
        Node resRoot = res;

        while (node != null || !parents.isEmpty()) {
            System.out.print("level " + level++ + ",\t"); // debug logger
            while (!parents.isEmpty()) {
                node = parents.poll();
                res = node;
                if (node != root) {
                    isRight = !isRight;
                }

                if (node != null) {
                    collection.add(res.val);
                    System.out.print(res.val + " "); // debug logger

                    if (node.left != null) {
                        siblings.offer(res.left);
                    } else {
                        siblings.offer(null);
                    }

                    if (node.right != null) {
                        siblings.offer(res.right);
                    } else {
                        siblings.offer(null);
                    }
                }
            }

            int countdown = siblings.size();
            for (Node n : siblings) {
                parents.offer(n);

                if (n == null) {
                    countdown--;
                }
            }

            System.out.printf("node: %d, siblings: %s \n", node.val, getQueueStr(siblings)); // debug logger

            // link nodes in the level
            if (siblings.size() > 0) {
                Node n = siblings.poll();

                while (!siblings.isEmpty() && n != null) {
                    n.next = siblings.poll();
                    n = n.next;
                }
            }

            siblings.clear();
            node = null;
            System.out.print("\n"); // debug logger

            if (countdown == 0) {
                break;
            }
        }

        return resRoot;

    }

    // debug logger
    private String getQueueStr(Queue<Node> list) {
        StringBuilder res = new StringBuilder("[");
        if (!list.isEmpty()) {
            for (Node n : list) {
                if (n == null) {
                    res.append("#");
                } else {
                    res.append(n.val);
                }

                res.append(", ");
            }
            res = new StringBuilder(res.substring(0, res.length() - 2));
        }
        return res + "]";
    }
}
