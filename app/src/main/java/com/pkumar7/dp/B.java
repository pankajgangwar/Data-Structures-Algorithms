package com.pkumar7.dp;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
class B {
    public static void main(String[] args) {
        B current = new B();
        current.findNumberOfLIS(new int[]{1,3,5,4,7});
    }

    /* 1745. Palindrome Partitioning IV
     * https://leetcode.com/problems/palindrome-partitioning-iv/
     * */
    public boolean checkPartitioning(String s) {
        boolean [][]isPal = new boolean[2001][2001];
        for (int i = s.length() - 1 ; i >= 0; i--) {
            for (int j = i; j < s.length() ; j++) {
                isPal[i][j] = s.charAt(i) == s.charAt(j) && (i + 1 >= j || isPal[i + 1][j - 1]);
            }
        }
        for (int i = 2; i < s.length(); i++) {
            if(isPal[i][s.length() -1]){
                for (int j = 1; j < i; j++) {
                    if(isPal[j][i-1] && isPal[0][j-1]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* 673. Number of Longest Increasing Subsequence
    * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
    * */
    public int findNumberOfLIS(int[] nums) {
        int len[] = new int[nums.length];
        int cnt[] = new int[nums.length];

        int max_len = 0, res=  0;
        for(int i = 0; i < nums.length; i++){
            len[i] = 1;
            cnt[i] = 1;
            for(int j = 0; j < i; j++){
                if(nums[j] < nums[i]){
                    if(len[i] == len[j] + 1) {
                        cnt[i] += cnt[j];
                    }else if(len[i] < len[j] + 1){
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }

            if(max_len == len[i]) {
                res += cnt[i];
            }else if(max_len < len[i]){
                max_len = len[i];
                res = cnt[i];
            }
        }
        return res;
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
