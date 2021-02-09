package com.hynial.preinter.dstructalgorithm.tree;

public class TreeNode {
    int val;

    TreeNode left;
    TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val){
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
        if(left != null){
            //left.parent = this;
        }
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
        if(right != null){
            //right.parent = this;
        }
    }

    @Override
    public boolean equals(Object o){
        return this == o;
    }
}
