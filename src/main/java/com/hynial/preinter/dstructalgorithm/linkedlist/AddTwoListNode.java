package com.hynial.preinter.dstructalgorithm.linkedlist;

public class AddTwoListNode {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int d = 1;
        long l1Val = 0;
        while (l1 != null) {
            l1Val += l1.val * d;
            d *= 10;
            l1 = l1.next;
        }
        d = 1;
        long l2Val = 0;
        while (l2 != null) {
            l2Val += l2.val * d;
            d *= 10;
            l2 = l2.next;
        }

        long result = l1Val + l2Val;
        if (result == 0) {
            return new ListNode(0);
        }
        long gen = 10;
        ListNode rNode = null;
        int i = 0;
        while (result / (gen / 10) != 0) {
            long left = result % gen;
            left = left / (gen / 10);
            ListNode tmp = new ListNode((int) left);
            if (i == 0) {
                rNode = tmp;
            } else {
                ListNode node = rNode;
                while (node.next != null) {
                    node = node.next;
                }
                node.next = tmp;
            }
            gen *= 10;
            i++;
        }

        return rNode;
    }


    public ListNode intToList(long a) {
        long gen = 10;
        int m = 0;
        ListNode result = null;
        if (a == 0) {
            return new ListNode(0);
        }
        while (a / (gen / 10) != 0) {
            long left = a % gen;
            left = left / (gen / 10);

            ListNode tmp = new ListNode((int) left);
            if (m == 0) {
                result = tmp;
            } else {
                ListNode node = result;
                while (node.next != null) {
                    node = node.next;
                }
                node.next = tmp;
            }
            m++;
            gen *= 10;
        }
        return result;
    }

    public void print(ListNode node) {
        while (node != null) {
            System.out.print(node.val + "==>");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {

        AddTwoListNode myTest = new AddTwoListNode();
        ListNode l1 = new ListNode(9);

        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);

        ListNode l3 = myTest.intToList(9);
        ListNode l4 = myTest.intToList(9999999991l);

        myTest.print(l3);
        myTest.print(l4);

        ListNode l5 = myTest.addTwoNumbers(l3, l4);

        while (l5 != null) {
            System.out.print(l5.val + "--->");
            l5 = l5.next;
        }
        System.out.println();
    }
}
