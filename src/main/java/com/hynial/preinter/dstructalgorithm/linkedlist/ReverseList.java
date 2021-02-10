package com.hynial.preinter.dstructalgorithm.linkedlist;

public class ReverseList {
    public static void main(String[] args) {
        int[] listArr = new int[]{1, 2, 4, 3, 6, 5, 7, 3, 8, 9};

        ListNode listNode = new ListNode(0);
        ListNode currNode = listNode;
        currNode.val = 99;
        for (int i = 0; i < listArr.length; i++) {
            currNode.next = new ListNode(listArr[i]);
            currNode = currNode.next;
        }

        //listNode = listNode.next;
        currNode = listNode;
        while (currNode != null) {
            System.out.print(currNode.val + ">>");
            currNode = currNode.next;
        }
        System.out.println();

        ListNode cNode = listNode, preNode = null, tmpNode;

        while (cNode != null) {
            tmpNode = cNode.next;
            cNode.next = preNode;
            preNode = cNode;
            cNode = tmpNode;
        }

        while (preNode != null) {
            System.out.print(preNode.val + ">>");
            preNode = preNode.next;
        }
    }
}
