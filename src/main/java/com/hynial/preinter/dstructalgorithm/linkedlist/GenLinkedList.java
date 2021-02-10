package com.hynial.preinter.dstructalgorithm.linkedlist;

public class GenLinkedList {
    public ListNode genLinkedListFromIntArr(int[] intArr) {
        if (intArr == null || intArr.length == 0) return null;

        ListNode listNode = new ListNode(0);
        ListNode tmpNode = listNode;
        for (int i = 0; i < intArr.length; i++) {
            tmpNode.next = new ListNode(intArr[i]);
            tmpNode = tmpNode.next;

        }

        return listNode.next;
    }

    public void printListNode(ListNode listNode) {
        if (listNode == null) {
            System.out.println("NULL");
            return;
        }
        ListNode currNode = listNode;
        while (currNode != null) {
            if (currNode.next != null) {
                System.out.print(currNode.val + ">>");
            } else {
                System.out.println(currNode.val + "");
            }
            currNode = currNode.next;
        }

    }
}
