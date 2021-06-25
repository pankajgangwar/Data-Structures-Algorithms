package com.pkumar7.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class LeetCodeProblems {

    public static void main(String args[]){

    }


    public static List<Integer> getFirstTwoElementWithoutPairs(List<Integer> list){
        Map<Integer,Integer> mMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            int ele = list.get(i);
            if(!mMap.containsKey(ele)){
                mMap.put(ele,1);
            }else{
                mMap.put(ele,mMap.get(ele)+1);
            }
        }
        List<Integer> result = new ArrayList<>();
        Set<Integer> keys = mMap.keySet();

        Iterator<Integer> it = keys.iterator();

        for(int i = 0; i < list.size(); i++) {
            int ele = list.get(i);
            if(mMap.get(ele) == 1){
                if(result.size() < 2)
                    result.add(ele);
            }
        }

        for (Integer ele : result) {
            System.out.print(ele +",");
        }
        return result;
    }

    public static String reverseWords(String statement){
        String[] words = statement.split(" ");
        String result="";
        for (int i = words.length - 1; i >= 0 ; i--) {
             result += words[i] + " ";
        }
        result = result.trim();
        System.out.println(result);
        return result;
    }

    private static void call_array(int i, int[] arr) {
        arr[i] = 6;
        i = 5;
    }

    /**
     * https://leetcode.com/problems/find-k-th-smallest-pair-distance/
     */
    public int smallestDistancePair(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);

        int low = nums[1] - nums[0];
        for (int i = 1; i < n -1; i++) {
            low = Math.min(low, nums[i+1] - nums[i]);
        }

        int high = nums[n-1] - nums[0];
        while(low < high){
            int mid = low + (high - low)/2;
            int pairs = countPairs(nums,mid);
            if(pairs < k){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    private int countPairs(int[] nums, int mid) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int j = i;
            while(j < n && nums[j] - nums[i] <= mid){
                j++;
            }
            res += j - i - 1;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/trapping-rain-water/
     *
     * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     * */
    public int trap(int[] height) {
        int total_water_trapped = 0;
        int n = height.length;
        int leftMax[] = new int[n];
        int rightMax[] = new int[n];

        leftMax[0] = height[0];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1],height[i]);
        }

        rightMax[n-1] = height[n-1];
        for (int i = n - 2; i > 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1],height[i]);
        }

        for (int i = 0; i < n; i++) {
                int min_water = Math.min(leftMax[i],rightMax[i]);

                if(min_water > height[i]){
                    int curr_water = min_water - height[i];
                    total_water_trapped = total_water_trapped + curr_water;
                }
        }
        return total_water_trapped;
    }

    /***
     * https://leetcode.com/problems/container-with-most-water/
     */
    public int maxArea(int[] height) {
        int max_area = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i+1; j < height.length; j++) {
                max_area = Math.max(max_area,
                        Math.min(height[i],height[j]) * (j-i));
            }
        }
        return max_area;
    }

    public int maxAreaLinearTime(int[] height){
        int l = 0, r = height.length - 1;
        int maxArea = 0;

        while(l < r){
            maxArea = Math.max(maxArea, Math.min(height[l],height[r]) *(r - l));
            if(height[l] < height[r]){
                l++;
            }else{
                r--;
            }
        }
        return maxArea;
    }
}


