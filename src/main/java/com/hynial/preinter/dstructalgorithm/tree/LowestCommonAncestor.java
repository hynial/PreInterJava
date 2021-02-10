package com.hynial.preinter.dstructalgorithm.tree;

public class LowestCommonAncestor {
    public static TreeNode findLowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return root;
        if (root == p || root == q) return root;

        TreeNode left = findLowestCommonAncestor(root.left, p, q);
        TreeNode right = findLowestCommonAncestor(root.right, p, q);

        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        return root;
    }

    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);

        t1.left = t2;
        t1.right = t4;
        t2.right = t3;
        t2.left = t5;
        t5.right = t6;

        TreeNode commonNode = findLowestCommonAncestor(t1, t3, t6);
        if (commonNode != null) {
            System.out.println(commonNode.val);
        }
    }
}