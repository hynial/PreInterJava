package com.hynial.preinter.dstructalgorithm.linkedlist;

public class MergeTwoLists {
    public ListNode mergeTwoListsMystery(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode currNode = l1, cNode = l2, preNode = null, insertNode;
        while (currNode != null) {
            while (cNode != null) {
                if (currNode.val < cNode.val) {
                    insertNode = new ListNode(currNode.val);
                    if (preNode == null) {
                        preNode = insertNode;
                        l2 = preNode;
                        preNode.next = cNode;
                        cNode = preNode;
                    } else {
                        preNode.next = insertNode;
                        insertNode.next = cNode;
                        cNode = insertNode;
                    }
                    break;
                } else {
                    preNode = cNode;

                    if (cNode.next == null) {
                        insertNode = new ListNode(currNode.val);
                        cNode.next = insertNode;
                        break;
                    }

                    cNode = cNode.next;
                }
            }

            currNode = currNode.next;
        }

        return l2;
    }

    public ListNode mergeTwoListsOrdinary(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head, cNode1 = l1, cNode2 = l2, combineCurrNode;

        if (cNode1.val < cNode2.val) {
            head = cNode1;
            cNode1 = cNode1.next;
        } else {
            head = cNode2;
            cNode2 = cNode2.next;
        }

        combineCurrNode = head;

        while (cNode1 != null && cNode2 != null) {
            if (cNode1.val < cNode2.val) {
                combineCurrNode.next = cNode1;
                cNode1 = cNode1.next;
            } else {
                combineCurrNode.next = cNode2;
                cNode2 = cNode2.next;
            }

            combineCurrNode = combineCurrNode.next;
        }

        if (cNode1 == null) {
            combineCurrNode.next = cNode2;
        }

        if (cNode2 == null) {
            combineCurrNode.next = cNode1;
        }

        return head;
    }

    public ListNode mergeTwoListsStrange(ListNode l1, ListNode l2) {
        ListNode head1 = l1;
        ListNode pre1 = null;
        if (l1 == null)
            return l2;
        else if (l2 == null)
            return l1;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val && l1.next != null) {
                pre1 = l1;
                l1 = l1.next;
            } else if (pre1 != null && l1.val > l2.val) {
                pre1.next = l2;
                ListNode ne2 = l2.next;
                l2.next = l1;
                pre1 = l2;
                l2 = ne2;
            } else if (pre1 == null && l1.val > l2.val) {
                ListNode ne2 = l2.next;
                l2.next = l1;
                head1 = l2;
                pre1 = l2;
                l2 = ne2;
            } else {
                l1.next = l2;
                break;
            }
        }
        return head1;
    }

    public static void main(String[] args) {
        GenLinkedList genLinkedList = new GenLinkedList();
        ListNode sort1 = genLinkedList.genLinkedListFromIntArr(new int[]{0, 3, 66});
        ListNode sort2 = genLinkedList.genLinkedListFromIntArr(new int[]{1, 4, 6, 25, 55, 77});

        MergeTwoLists mergeTwoLists = new MergeTwoLists();
        ListNode mergedList = mergeTwoLists.mergeTwoLists(sort1, sort2);
        genLinkedList.printListNode(mergedList);
        genLinkedList.printListNode(mergeTwoLists.mergeTwoListsMystery(sort1, sort2));
        genLinkedList.printListNode(mergeTwoLists.mergeTwoListsOrdinary(sort1, sort2));
        genLinkedList.printListNode(mergeTwoLists.mergeTwoListsStrange(sort1, sort2));
    }
}
