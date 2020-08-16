package com.mission.google.arrays;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 15/August/2020
 */
class A {
    /*
    * 1551. Minimum Operations to Make Array Equal
    * https://leetcode.com/problems/minimum-operations-to-make-array-equal/
    * */
    public int minOperations(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (2 * i) + 1;
        }
        int res = 0;
        int i = 0, j = n -1;
        while (i < j){
            int tar = (arr[i] + arr[j]) / 2;
            int diff = tar - arr[i];
            res += diff;
            arr[i] += diff;
            arr[j] -= diff;
            i++;
            j--;
        }
        return res;
    }
    /*1550. Three Consecutive Odds
    * https://leetcode.com/problems/three-consecutive-odds/
    * */
    public boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 0; i < arr.length - 2; i++) {
            if(arr[i] % 2 == 1 && arr[i+1] % 2 == 1 && arr[i+2] % 2 == 1){
                return true;
            }
        }
        return false;
    }

    /* 769. Max Chunks To Make Sorted
    * https://leetcode.com/problems/max-chunks-to-make-sorted/
    * */
    public int maxChunksToSorted1(int[] arr) {
        int n = arr.length;

        int[] maxOfLeft = new int[n];
        int[] minOfRight = new int[n];

        maxOfLeft[0] = arr[0];
        for(int i = 1; i < n; i++){
            maxOfLeft[i] = Math.max(maxOfLeft[i-1], arr[i]);
        }

        minOfRight[n-1] = arr[n-1];
        for(int i = n - 2; i >= 0; i--){
            minOfRight[i] = Math.min(minOfRight[i+1], arr[i]);
        }

        int res = 1;
        for(int i = 0; i < n - 1; i++){
            if(maxOfLeft[i] <= minOfRight[i+1]) res++;
        }
        return res;
    }

    public int maxChunksToSorted(int[] arr){
        if(arr == null || arr.length == 0) return 0;
        int res = 0;
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            if(max == i) res++;
        }
        return res;
    }

    /*
    598. Range Addition II
    https://leetcode.com/problems/range-addition-ii/
    */
    public int maxCount(int m, int n, int[][] ops) {
        int min_m = m;
        int min_n = n;
        for(int[] x : ops){
            min_m = Math.min(min_m, x[0]);
            min_n = Math.min(min_n, x[1]);
        }
        return ( min_m * min_n);
    }

    /* 370. Range Addition
    * https://leetcode.com/problems/range-addition
    * */
    public int[] getModifiedArray(int n, int[][] updates) {
        // return bruteForce(n, updates);
        return optimal(n, updates);
    }

    public int[] optimal(int n, int[][] updates) {
        int[] res = new int[n+1];
        for(int[] x : updates){
            res[x[0]] += x[2];
            res[x[1] + 1] -= x[2]; //reset the addition when its carried over to last idx
        }
        for(int i = 1; i < n + 1; i++){
            res[i] += res[i - 1];
        }
        int[] ans = new int[n];
        for(int i = 0; i < n; i++){
            ans[i] = res[i];
        }
        return ans;
    }


    public int[] bruteForce(int n, int[][] updates) {
        int[] res = new int[n];
        for(int i = 0; i < updates.length; i++){
            int start = updates[i][0];
            int end = updates[i][1];
            int inc = updates[i][2];

            for(int j = start; j <= end; j++){
                res[j] += inc;
            }
        }
        return res;
    }
}
