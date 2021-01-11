package com.catherine.binary_tree;

/**
 * @author : Catherine
 * @created : 24/12/2020
 * https://www.youtube.com/watch?v=s5XRtcud35E&ab_channel=Jenny%27slecturesCS%2FITNET%26JRF
 * https://github.com/awangdev/LintCode/blob/master/Java/Construct%20Binary%20Tree%20from%20Inorder%20and%20Postorder%20Traversal.java
 *
 *
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 *
 * For example, given
 *
 * inorder = [9,3,15,20,7]
 * postorder = [9,15,7,20,3]
 * Return the following binary tree:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * https://leetcode.com/explore/learn/card/data-structure-tree/133/conclusion/942/
 */
public class BuildTreeFromInPost {
    // inorder: L C R
    // postorder: L R C
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length != postorder.length) {
            return null;
        }
        return build(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode build(int[] inorder, int inStart, int inEnd,
                           int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[postEnd]);
        int center = findCenter(inorder, inStart, inEnd, postorder[postEnd]);
        root.left = build(inorder, inStart, center - 1,
                postorder, postStart, postStart + (center - inStart) - 1);
        root.right = build(inorder, center + 1, inEnd,
                postorder, postStart + (center - inStart), postEnd - 1);
        return root;
    }

    private int findCenter(int[] arr, int start, int end, int key) {
        for (int i = start; i <= end; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
}
