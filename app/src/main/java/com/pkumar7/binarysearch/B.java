package com.pkumar7.binarysearch;

import java.util.ArrayList;
import java.util.List;

public class B {

    /*
     * https://leetcode.com/problems/find-the-longest-valid-obstacle-course-at-each-position/
     * https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf
     * */
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        return lisPatienceSort(obstacles);
    }

    public int[] lisPatienceSort(int[] nums) {
        List<Integer> piles = new ArrayList<>(nums.length);
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int pile = binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
            res[i] = pile + 1;
        }
        return res;
    }

    public int binarySearch(List<Integer> piles, int num){
        int low = 0, high = piles.size() - 1;
        while(low <= high){
            int mid = (high + low) / 2;
            int mid_ele = piles.get(mid);
            if(mid_ele <= num){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return -(low + 1);
    }
}
