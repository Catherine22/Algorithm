package com.catherine.binary_tree;

/**
 * @author : Catherine
 * @created : 11/01/2020
 * https://www.youtube.com/watch?v=PoBGyrIWisE&ab_channel=Jenny%27slecturesCS%2FITNET%26JRF
 * https://github.com/IPL/Leetcode-Java/blob/master/src/main/java/Construct%20Binary%20Tree%20from%20Preorder%20and%20Inorder%20Traversal.java
 *
 *
 * Given inorder and preorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 *
 * For example, given
 *
 * preorder = [3,9,20,15,7]
 * inorder = [9,3,15,20,7]
 * Return the following binary tree:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * https://leetcode.com/explore/learn/card/data-structure-tree/133/conclusion/943/
 */
public class BuildTreeFromInPre {
    // inorder: L C R
    // preorder: C L R
    public TreeNode buildTree(int[] inorder, int[] preorder) {
        int preStart = 0;
        int preEnd = preorder.length - 1;
        int inStart = 0;
        int inEnd = inorder.length - 1;

        return buildTree(preorder, preStart, preEnd,
                inorder, inStart, inEnd);

    }

    public TreeNode buildTree(int[] preorder, int preStart, int preEnd,
                              int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd)
            return null;

        int rootValue = preorder[preStart];
        TreeNode root = new TreeNode(rootValue);

        int k = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootValue) {
                k = i;
                break;
            }
        }

        // Because k is not the length, it it need to -(inStart+1) to get the length
        root.left = buildTree(preorder, preStart + 1, preStart + 1 - inStart + k - 1,
                inorder, inStart, k - 1);
        // postStart+k-inStart = postStart+k-(inStart+1) +1
        root.right = buildTree(preorder, preStart + 1 - inStart + k, preEnd,
                inorder, k + 1, inEnd);

        return root;
    }
}
