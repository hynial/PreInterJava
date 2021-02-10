package com.hynial.preinter.dstructalgorithm.linkedlist;

public class RemoveNthFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;
        if (n <= 0) return head;

        int len = 0;
        ListNode currNode = head;
        while (currNode != null) {
            currNode = currNode.next;
            len++;
        }

        if (n > len) return null;
        if (n == len) {
            head = head.next;
            return head;
        }

        int i = 0;
        currNode = head;
        while (currNode != null) {

            if (i == len - n - 1) {
                currNode.next = currNode.next.next;
                break;
            }

            currNode = currNode.next;
            i++;
        }

        return head;
    }

    public ListNode removeNthFromEndMystery(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        // Move first to the end, maintaining the gap
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        GenLinkedList genLinkedList = new GenLinkedList();
        RemoveNthFromEnd removeNthFromEnd = new RemoveNthFromEnd();

        ListNode testNode = genLinkedList.genLinkedListFromIntArr(new int[]{2, 3, 3, 1});

        ListNode resultNode = removeNthFromEnd.removeNthFromEndMystery(testNode, 1);

        genLinkedList.printListNode(resultNode);
    }
}
