package com.hynial.preinter.dstructalgorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

public class SymmetricTree {
    /**
     * 给定一个二叉树, 检查它是否是镜像对称的
     * 例如以下是镜像对称的
     *      1
     *     / \
     *    2   2
     *   / \ / \
     *  3  4 4  3
     *
     * 下面这个则不是镜像对称的
     *      1
     *     / \
     *    2   2
     *     \   \
     *      3   3
     *
     * TreeNode类的定义:
     *
     * @param treeNode 一颗二叉树
     * @return boolean 是否是对称的
     */
    public static boolean isTreeSymmetric(TreeNode treeNode) {
        if(treeNode == null) return true;
        if(treeNode.left == null && treeNode.right == null) return true;
        if(treeNode.left != null && treeNode.right != null){
            return isSymmetric(treeNode.left, treeNode.right);
        }else{
            return false;
        }
    }

    public static boolean isSymmetric(TreeNode sym1, TreeNode sym2){
        if(sym1.val != sym2.val) return false;

        if(sym1.right == null && sym2.left == null){
            if(sym1.left == null && sym2.right == null){
                return true;
            }else if(sym1.left != null && sym2.right != null){
                return isSymmetric(sym1.left, sym2.right);
            }else{
                return false;
            }
        }else if(sym1.right != null && sym2.left != null){
            boolean first = isSymmetric(sym1.right, sym2.left);
            if(first){
                if(sym1.left == null && sym2.right == null){
                    return true;
                }else if(sym1.left != null && sym2.right != null){
                    return isSymmetric(sym1.left, sym2.right);
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // 以下给出TreeNode类, 请勿修改
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isSymmetric2(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();
            if (t1 == null && t2 == null) continue;
            if (t1 == null || t2 == null) return false;
            if (t1.val != t2.val) return false;
            q.add(t1.left);
            q.add(t2.right);
            q.add(t1.right);
            q.add(t2.left);
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(1);
        TreeNode second1 = new TreeNode(2);
        TreeNode second2 = new TreeNode(2);
        rootNode.left = second1;
        rootNode.right = second2;

        TreeNode third1 = new TreeNode(3);
        TreeNode third2 = new TreeNode(4);
        TreeNode third3 = new TreeNode(4);
        TreeNode third4 = new TreeNode(3);

        second1.left = third1;
        second1.right = third2;

        second2.left = third3;
        second2.right = third4;

        System.out.println(isTreeSymmetric(rootNode));

        TreeNode four1 = new TreeNode(5);
        TreeNode four2 = new TreeNode(5);
        third1.left = four1;
        third4.left = four2;
        System.out.println(isTreeSymmetric(rootNode));

        rootNode = new TreeNode(1);
        second1 = new TreeNode(2);
        second2 = new TreeNode(2);
        third1 = new TreeNode(3);
        third2 = new TreeNode(3);

        rootNode.left = second1;
        rootNode.right = second2;
        second1.right = third1;
        second2.right = third2;

        System.out.println(isTreeSymmetric(rootNode));

        System.out.println(new SymmetricTree().isSymmetric2(rootNode));
    }
}
