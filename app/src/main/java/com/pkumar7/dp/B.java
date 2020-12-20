package com.pkumar7.dp;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
class B {
    public static void main(String[] args) {
        B current = new B();
        current.constrainedSubsetSum(new int[] {10,-2,-10,-5,20}, 2);
    }

    /* https://leetcode.com/problems/constrained-subsequence-sum/
    * 1425. Constrained Subsequence Sum
    * */
    public int constrainedSubsetSum(int[] nums, int k) {
        Deque<Integer> deq = new ArrayDeque<>();
        int maxSum = nums[0];
        for(int i = 0; i < nums.length; ++i){
            nums[i] += !deq.isEmpty() ? nums[deq.peekFirst()] : 0;
            maxSum = Math.max(maxSum, nums[i]);
            while(!deq.isEmpty() && deq.peekFirst() < i - k + 1){
                deq.pollFirst();
            }
            while(!deq.isEmpty() && nums[deq.peekLast()] < nums[i]){
                deq.pollLast();
            }
            if(nums[i] > 0){
                deq.offer(i);
            }
        }
        return maxSum;
    }
}
