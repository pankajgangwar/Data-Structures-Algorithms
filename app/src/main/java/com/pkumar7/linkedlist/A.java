package com.pkumar7.linkedlist;

import com.pkumar7.datastructures.ListNode;

/**
 * Created by Pankaj Kumar on 14/November/2020
 */
class A {

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
