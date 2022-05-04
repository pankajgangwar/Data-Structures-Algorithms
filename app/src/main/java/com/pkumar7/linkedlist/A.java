package com.pkumar7.linkedlist;

import com.pkumar7.datastructures.LinkedList;
import com.pkumar7.datastructures.ListNode;

import java.util.HashSet;

/**
 * Created by Pankaj Kumar on 14/November/2020
 */
class A {
    /*
    * https://leetcode.com/problems/reverse-nodes-in-even-length-groups/
    * 2074. Reverse Nodes in Even Length Groups
    * */
    public ListNode reverseEvenLengthGroups(ListNode head) {
        int currLen = 1;
        ListNode temp = head;
        ListNode prev = head;
        int remainingNodes = findLength(head);
        while (temp != null){
            if(currLen % 2 == 0 && remainingNodes >= currLen){
                prev.next = reverseNodes(currLen, temp);
                temp = prev.next;
                int advance = currLen;
                while (advance-- > 0 && temp != null){
                    prev = temp;
                    temp = temp.next;
                    remainingNodes -= 1;
                }
            }else{
                if(currLen > remainingNodes && remainingNodes % 2 == 0){
                    prev.next = reverseNodes(currLen, temp);
                    temp = prev.next;
                }
                int advance = currLen;
                while (advance-- > 0 && temp != null){
                    prev = temp;
                    temp = temp.next;
                    remainingNodes -= 1;
                }
            }
            currLen += 1;
        }
        return head;
    }

    private int findLength(ListNode temp){
        int count = 0;
        while (temp != null){
            temp = temp.next;
            count += 1;
        }
        return count;
    }

    private ListNode reverseNodes(int len, ListNode head) {
        ListNode fastPtr = head;
        int i = 0;
        for(; i < len && fastPtr != null; i++){
            fastPtr = fastPtr.next;
        }
        while (i-- > 0) {
            ListNode next = head.next;
            head.next = fastPtr;
            fastPtr = head;
            head = next;
        }
        head = fastPtr;
        return head;
    }

    ListNode arrayToLinkedList(int arr[]) {
        int n = arr.length;
        ListNode root = null;
        for (int i = 0; i < n; i++){
            root = insert(root, arr[i]);
        }
        return root;
    }

    ListNode insert(ListNode root, int item) {
        ListNode temp = new ListNode(item);
        ListNode ptr;
        temp.next = null;

        if (root == null)
            root = temp;
        else {
            ptr = root;
            while (ptr.next != null)
                ptr = ptr.next;
            ptr.next = temp;
        }
        return root;
    }

    /* 92. Reverse Linked List II
    * https://leetcode.com/problems/reverse-linked-list-ii/
    * */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left == right) return head;
        ListNode lNode = null, rNode = null, prevLNode = null;
        ListNode temp = head;
        int pos = 0;
        while (temp != null){
            ++pos;
            if(pos == left){
                lNode = temp;
            }else if(pos == right){
                rNode = temp.next;
                temp.next = null;
            }
            if(lNode == null){
                prevLNode = temp;
            }
            temp = temp.next;
        }
        ListNode newHead = null;
        ListNode lNodeTemp = lNode;
        while (lNode != null){
            ListNode next = lNode.next;
            lNode.next = newHead;
            newHead = lNode;
            lNode = next;
        }
        if(lNodeTemp == null) return head;
        lNodeTemp.next = rNode;
        if(prevLNode != null){
            prevLNode.next = newHead;
        }else{
            return newHead;
        }
        return head;
    }

    /*
    * 83. Remove Duplicates from Sorted List
    * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
    * */
    public ListNode deleteDuplicatesI(ListNode head) {
        if(head == null) return head;
        while(head.next != null && head.val == head.next.val){
            head = head.next;
        }
        head.next = deleteDuplicatesI(head.next);
        return head;
    }

    /* 82. Remove Duplicates from Sorted List II
    https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
    */
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        if(head.next != null && head.val == head.next.val){
            while(head.next != null && head.val == head.next.val){
                head = head.next;
            }
            return deleteDuplicates(head.next);
        }else{
            head.next = deleteDuplicates(head.next);
        }
        return head;
    }

    /* 234. Palindrome Linked List
    * https://leetcode.com/problems/palindrome-linked-list/
    * */
    public boolean isPalindrome(ListNode head) {
        ListNode fastPtr = head;
        ListNode slowPtr = head;
        while (fastPtr != null && fastPtr.next != null) {
            fastPtr = fastPtr.next.next;
            slowPtr = slowPtr.next;
        }
        ListNode newHead = null;
        while(slowPtr != null){
            ListNode next = slowPtr.next;
            slowPtr.next = newHead;
            newHead = slowPtr;
            slowPtr = next;
        }
        while (head != null && newHead != null){
            if(head.val != newHead.val){
                return false;
            }
            head = head.next;
            newHead = newHead.next;
        }
        return true;
    }

    /* 1669. Merge In Between Linked Lists
     * https://leetcode.com/problems/merge-in-between-linked-lists/
     * */
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode prev_a = null, next_b = null;
        boolean a_found = false;
        ListNode temp = list1;
        while (temp != null){
            if(!a_found && a-- == 0){
                a_found = true;
            }
            if(b-- == 0){
                next_b = temp.next;
                break;
            }
            if(!a_found){
                prev_a = temp;
            }
            temp = temp.next;
        }
        if(prev_a == null){
            list1 = list2;
        }else{
            prev_a.next = list2;
        }
        while (list2.next != null){
            list2 = list2.next;
        }
        list2.next = next_b;
        return list1;
    }


    /* 708. Insert into a Sorted Circular Linked List
    * https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/
    * */
    public Node insert(Node head, int insertVal) {
        if(head == null){
            Node x = new Node(insertVal);
            x.next = x;
            return x;
        }
        Node temp = head;
        Node next = head.next;
        boolean inserted = false;
        while (true){
            if((temp.val > next.val && insertVal > temp.val ) ||
                    (temp.val > next.val && insertVal < next.val ) ||
                    (temp.val <= insertVal && insertVal <= next.val)){
                temp.next = new Node(insertVal, next);
                inserted = true;
                break;
            }
            temp = temp.next;
            next = next.next;
            if(temp == head) break;
        }
        if(!inserted){
            temp.next = new Node(insertVal, next);
        }
        return head;
    }

    class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    };

    /* 369. Plus One Linked List
     * https://leetcode.com/problems/plus-one-linked-list/
     * */
    public ListNode plusOne(ListNode head) {
        ListNode curr = dfs(head);
        if(carry == 1){
            ListNode newHead = new ListNode(carry);
            newHead.next = curr;
            return newHead;
        }
        return curr;
    }

    int carry = 0;
    public ListNode dfs(ListNode head) {
        if(head == null){
            return head;
        }
        head.next = dfs(head.next);
        if(head.next == null || carry == 1 ){
            head.val += 1;
        }
        carry = head.val / 10;
        head.val %= 10;
        return head;
    }
}
