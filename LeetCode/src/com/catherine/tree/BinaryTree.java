package com.catherine.tree;

/**
 * @author : Catherine
 * @created : 23/06/2021
 */
public class BinaryTree {


    //    Node tree1 = new Node(8);
//    tree1.left = new Node(4, new Node(2), new Node(6));
//    tree1.right = new Node(10, null, new Node(20));
//    System.out.println(isBinarySearchTree(tree1)); // true
//
//    Node tree2 = new Node(8);
//    tree2.left = new Node(4, new Node(2), new Node(12));
//    tree2.right = new Node(10, null, new Node(20));
//    System.out.println(bt.isBinarySearchTree(tree2)); // false
    public boolean isBinarySearchTree(Node root) {
        Node node = root;
        if (node == null) {
            return true;
        }
        if (node.left != null && !isLeftTree(node, node.left)) {
            return false;
        }
        if (node.right != null && !isRightTree(node, node.right)) {
            return false;
        }
        return true;
    }

    //    Node tree1 = new Node(10);
//    tree1.left = new Node(5, new Node(3), new Node(7));
//    tree1.right = new Node(20, null, new Node(30));
//    System.out.println(isCompleteBinaryTree(tree1)); // false
//
//    Node tree2 = new Node(10);
//    tree2.left = new Node(5, new Node(3), new Node(7));
//    tree2.right = new Node(20, new Node(15), null);
//    System.out.println(isCompleteBinaryTree(tree2)); // true
    public boolean isCompleteBinaryTree(Node root) {
        Node node = root;
        if (node == null) {
            return true;
        }

        if (node.left == null && node.right != null) {
            return false;
        }

        int leftChildren = getChildrenNum(node.left);
        int rightChildren = getChildrenNum(node.right);

        if (rightChildren > leftChildren) {
            return false;
        }

        if (node.left != null && !isCompleteBinaryTree(node.left)) {
            return false;
        }

        if (node.right != null && !isCompleteBinaryTree(node.right)) {
            return false;
        }

        return true;
    }

    private int getChildrenNum(Node root) {
        Node node = root;
        int count = 0;
        if (node == null) {
            return count;
        }

        if (node.left != null) {
            count++;
            count += getChildrenNum(node.left);
        }

        if (node.right != null) {
            count++;
            count += getChildrenNum(node.right);
        }

        return count;
    }

    //    Node tree1 = new Node(10);
//    tree1.left = new Node(5, null, new Node(12));
//    tree1.right = new Node(20, new Node(3, new Node(9), new Node(18)), new Node(7));
//    System.out.println(isFull(tree1)); // false
//
//    Node tree2 = new Node(10);
//    tree2.left = new Node(5, null, null);
//    tree2.right = new Node(20, new Node(3, new Node(9), new Node(18)), new Node(7));
//    System.out.println(isFull(tree2)); // true
    public boolean isFull(Node root) {
        Node node = root;
        if (node == null) {
            return true;
        }

        int children = 0;

        if (node.left != null) {
            children++;
        }

        if (node.right != null) {
            children++;
        }

        if (children % 2 == 1) {
            return false;
        }

        if (node.left != null) {
            return isFull(node.left);
        }

        if (node.right != null) {
            return isFull(node.right);
        }
        return true;
    }

    public boolean isPerfectBinaryTree(Node root) {
        Node node = root;
        return isFull(node) && isCompleteBinaryTree(node);
    }

    private boolean isLeftTree(Node tree, Node subtree) {
        if (subtree.value > tree.value) {
            return false;
        }
        if (subtree.left != null && !isLeftTree(subtree, subtree.left)) {
            return false;
        }
        if (subtree.right != null && !isLeftTree(tree, subtree.right)) {
            return false;
        }
        return true;
    }

    private boolean isRightTree(Node tree, Node subtree) {
        if (subtree.value <= tree.value) {
            return false;
        }
        if (subtree.left != null && !isRightTree(subtree, subtree.left)) {
            return false;
        }
        if (subtree.right != null && !isRightTree(tree, subtree.right)) {
            return false;
        }
        return true;
    }
}
