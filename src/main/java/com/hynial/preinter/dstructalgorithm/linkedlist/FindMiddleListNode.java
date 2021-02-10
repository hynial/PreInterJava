package com.hynial.preinter.dstructalgorithm.linkedlist;

public class FindMiddleListNode {
    public ListNode middleNode(ListNode head) {
        if(head == null) return null;
        if(head.next == null) return head;
        ListNode currNode = head;
        int len = 0 ;
        while (currNode != null){
            currNode = currNode.next;
            len++;
        }

        int middleIdx = len / 2;
        int i = 0;
        while(head != null && i < middleIdx){
            head = head.next;
            i++;
        }

        return head;
    }

    public ListNode middleNodeMystery(ListNode head){
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        GenLinkedList genLinkedList = new GenLinkedList();
        ListNode testList = genLinkedList.genLinkedListFromIntArr(new int[]{1,2,4,5,44,4,3,6,3});

        FindMiddleListNode middleNode = new FindMiddleListNode();
        genLinkedList.printListNode(middleNode.middleNode(testList));
        genLinkedList.printListNode(middleNode.middleNodeMystery(testList));
    }
}
