package com.mission.google.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pankaj Kumar on 13/August/2020
 */
class A {
    public static void main(String[] args) {

    }

    /*
    * 846. Hand of Straights
    * https://leetcode.com/problems/hand-of-straights/
    * Similar : https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
    * */
    public boolean isNStraightHand(int[] hand, int W) {
        if(hand == null || hand.length == 0 || (hand.length % W != 0) ){
            return false;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < hand.length; i++){
            map.put(hand[i], map.getOrDefault(hand[i], 0) + 1);
        }
        Arrays.sort(hand);

        for(int i = 0; i < hand.length; i++){
            if(map.get(hand[i]) > 0){
                for(int j = 0; j < W; j++){
                    int next = hand[i] + j;
                    if(!map.containsKey(next)){
                        return false;
                    }
                    if(map.get(next) < 0){
                        return false;
                    }

                    map.put(next, map.getOrDefault(next, 0) - 1);
                }
            }
        }
        return true;
    }
    /*
    * 1296. Divide Array in Sets of K Consecutive Numbers
    * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
    * */
    public boolean isPossibleDivide(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0)
            return false;

        if(nums.length % k != 0){
            return false;
        }
        Arrays.sort(nums);

        Map<Integer, Integer> freq = new HashMap<>();

        for(int ele : nums){
            freq.put(ele, freq.getOrDefault(ele, 0) + 1 );
        }

        for(int i = 0; i < nums.length; i++){
            if(freq.get(nums[i]) > 0){
                for(int j = 0; j < k; j++){
                    int next = nums[i] + j;
                    if(!freq.containsKey(next)){
                        return false;
                    }
                    if(freq.get(next) <= 0){
                        return false;
                    }
                    freq.put(next, freq.get(next) -1);
                }
            }
        }
        return true;
    }
}
