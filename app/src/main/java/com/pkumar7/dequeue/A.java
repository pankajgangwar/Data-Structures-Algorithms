package com.pkumar7.dequeue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
class A {

    public static void main(String[] args) {

    }

    /* 1696. Jump Game VI
    * Similar
    * https://leetcode.com/problems/jump-game-vi/
    * https://leetcode.com/problems/sliding-window-maximum/
    * https://leetcode.com/problems/constrained-subsequence-sum/
    * */
    public int maxResult(int[] nums, int k) {
        Deque<Integer> deq = new ArrayDeque<>();
        for(int i = 0; i < nums.length; ++i){
            nums[i] += !deq.isEmpty() ? nums[deq.peekFirst()] : 0;// Update curr index with max + curr
            while(!deq.isEmpty() && deq.peekFirst() < i - k + 1 ){ // remove the indices out of window
                deq.pollFirst();
            }
            while(!deq.isEmpty() && nums[deq.peekLast()] < nums[i]){
                // remove min elements from dequeue as they are of no use
                deq.pollLast();
            }
            deq.offer(i);
        }
        return nums[nums.length - 1];
    }
}
