package com.pkumar7.linesweep;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class A {

    /*1589. Maximum Sum Obtained of Any Permutation
     * https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/
     * */
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int n = nums.length;
        int[] freq = new int[n];
        for (int i = 0; i < requests.length; i++) {
            int from = requests[i][0];
            int to = requests[i][1];
            freq[from] += 1;
            if(to + 1 < n){
                freq[to + 1] -= 1;
            }
        }
        for (int i = 1; i < n; i++) {
            freq[i] += freq[i - 1];
        }
        Arrays.sort(freq);
        Arrays.sort(nums);
        long mod = (long)1e9+7;
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += ((long)nums[i] * freq[i]) % mod;
        }
        return (int)(res % mod);
    }

    /* 1288. Remove Covered Intervals
        https://leetcode.com/problems/remove-covered-intervals/
    */
    public int removeCoveredIntervalsPass(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] == b[0] ? -a[1] + b[1] : a[0] - b[0]);
        int left = -1, right = -1;
        int uncovered = 0;
        for(int[] interval : intervals){
            if(interval[0] > left && interval[1] > right) {
                uncovered++;
                left = interval[0];
            }
            right = Math.max(right, interval[1]);
        }
        return uncovered;
    }

    public int removeCoveredIntervals(int[][] intervals){
        Arrays.sort(intervals, (a,b) -> a[0] == b[0] ? -a[1] + b[1] : a[0] - b[0]);
        int n = intervals.length;
        int[] curr = intervals[0];
        int removals = 0;
        for(int[] interval : intervals){
            if(curr[0] <= interval[0] && curr[1] >= interval[1]){
                removals++;
            }else{
                curr = interval;
            }
        }
        return n - removals + 1;//First interval is also removed
    }
}
