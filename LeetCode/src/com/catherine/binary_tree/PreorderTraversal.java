package com.catherine.binary_tree;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : Catherine
 * @created : 28/11/2020
 * <p>
 * Binary Tree Preorder Traversal
 * <p>
 * Given the root of a binary tree, return the preorder traversal of its nodes' values.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: root = [1,null,2,3]
 * Output: [1,2,3]
 * Example 2:
 * <p>
 * Input: root = []
 * Output: []
 * Example 3:
 * <p>
 * Input: root = [1]
 * Output: [1]
 * Example 4:
 * <p>
 * <p>
 * Input: root = [1,2]
 * Output: [1,2]
 * Example 5:
 * <p>
 * <p>
 * Input: root = [1,null,2]
 * Output: [1,2]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 * <p>
 * <p>
 * Follow up:
 * <p>
 * Recursive solution is trivial, could you do it iteratively?
 * <p>
 * https://leetcode.com/explore/learn/card/data-structure-tree/134/traverse-a-tree/928/
 */
public class PreorderTraversal {
    private final List<Integer> res = new LinkedList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return res;
        }

        traverse(root);
        return res;
    }


    private void traverse(TreeNode node) {
        res.add(node.val);
        if (node.left != null) {
            traverse(node.left);
        }

        if (node.right != null) {
            traverse(node.right);
        }
    }
}
