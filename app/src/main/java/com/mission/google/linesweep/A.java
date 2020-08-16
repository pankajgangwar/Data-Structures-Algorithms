package com.mission.google.linesweep;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class A {
    /*
        https://leetcode.com/problems/remove-covered-intervals/
        Line-Sweeping
        Intervals
    */
    public int removeCoveredIntervalsPass(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
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
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
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
