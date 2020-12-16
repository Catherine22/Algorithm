package com.catherine.binary_tree;

/**
 * @author : Catherine
 * @created : 16/12/2020
 *
 * Symmetric Tree
 *
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 *
 * But the following [1,2,2,null,3,null,3] is not:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 *
 * Follow up: Solve it both recursively and iteratively.
 * https://leetcode.com/explore/learn/card/data-structure-tree/17/solve-problems-recursively/536/
 */
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        } else if (n1 == null || n2 == null) {
            return false;
        } else {
            if (n1.val != n2.val) {
                return false;
            }
        }

        if (!isSymmetric(n1.left, n2.right)) {
            return false;
        }
//        if (!isSymmetric(n1.right, n2.left)) {
//            return false;
//        }
//        return true;
        return isSymmetric(n1.right, n2.left);
    }
}
