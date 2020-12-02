package com.catherine.binary_tree;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : Catherine
 * @created : 28/11/2020
 * <p>
 * Binary Tree Postorder Traversal
 * <p>
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: root = [1,null,2,3]
 * Output: [3,2,1]
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
 * Output: [2,1]
 * Example 5:
 * <p>
 * <p>
 * Input: root = [1,null,2]
 * Output: [2,1]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of the nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 * <p>
 * <p>
 * Follow up:
 * <p>
 * Recursive solution is trivial, could you do it iteratively?
 * <p>
 * https://leetcode.com/explore/learn/card/data-structure-tree/134/traverse-a-tree/930/
 */
public class PostorderTraversal {
    private final List<Integer> res = new LinkedList<>();

    public List<Integer> postorderTraversal(TreeNode root) {
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
