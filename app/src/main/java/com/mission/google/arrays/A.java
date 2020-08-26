package com.mission.google.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pankaj Kumar on 15/August/2020
 */
class A {

    /* 73. Set Matrix Zeroes
    *  https://leetcode.com/problems/set-matrix-zeroes/
    */
    public void setZeroes(int[][] matrix) {
        boolean fc = false, fr = false;
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            if(matrix[i][0] == 0) fc = true;
        }
        for (int i = 0; i < n; i++) {
            if(matrix[0][i] == 0) fr = true;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(matrix[i][j] == 0){
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }
        if(fr){
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
        if(fc){
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /* 1560. Most Visited Sector in a Circular Track
    * https://leetcode.com/problems/most-visited-sector-in-a-circular-track/
    * */
    public List<Integer> mostVisited(int n, int[] rounds) {
        List<Integer> res = new ArrayList<>();
        int start = rounds[0];
        int end = rounds[rounds.length - 1];
        for( int i = start ;;) {
            res.add(i);
            if(i == end) break;
            i++;
            if(i == n + 1) i = 1;
        }
        Collections.sort(res);
        return  res;
    }

    /* 1561. Maximum Number of Coins You Can Get
    * https://leetcode.com/problems/maximum-number-of-coins-you-can-get/
    * */
    public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int i = 0, k = piles.length -1, j = k - 1;
        int max = 0;
        while (i < j){
            max += piles[j];
            i++;
            j -= 2;
            k -= 2;
        }
        return max;
    }

    /* 56. Merge Intervals
    * https://leetcode.com/problems/merge-intervals/
    * */
    public int[][] merge(int[][] intervals) {
        if(intervals.length <= 1){
            return intervals;
        }
        Arrays.sort(intervals, (i1,i2) -> Integer.compare(i1[0],i2[0]));
        List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);

        for (int[] interval: intervals) {
            if (interval[0] <= newInterval[1]) {
                newInterval[1] = Math.max(newInterval[1], interval[1]);// updated max end of interval
            } else {
                newInterval = interval;
                result.add(newInterval);//update end of this interval later
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    /* 1497. Check If Array Pairs Are Divisible by k
    * https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/
    */
    public boolean canArrange(int[] arr, int k) {
        int[] freq = new int[k];
        for(int x : arr){
            int idx = (((x % k) + k) % k );
            freq[idx]++;
        }
        for(int i = 1; i <= k / 2; i++){
            if(freq[i] != freq[k - i]) return false;
        }
        return freq[0] % 2 == 0;
    }

    /* 1299. Replace Elements with Greatest Element on Right Side
    * https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
    * */
    public int[] replaceElements(int[] arr) {
        int n = arr.length;
        int max = arr[n-1];
        for(int i = n - 2; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = max;
            max = Math.max(max, temp);
        }
        arr[n-1] = -1;
        return arr;
    }
    
    public int[] replaceElements1(int[] arr) {
        int n = arr.length;
        int max = Integer.MIN_VALUE;
        for(int i = n - 1; i >= 0; i--){
            max = Math.max(max, arr[i]);
            arr[i] = max;
        }
        int[] res = new int[n];
        for(int i = 0; i < n - 1; i++){
            res[i] = arr[i+1];
        }
        res[n-1] = -1;
        return res;
    }

    /* 941. Valid Mountain Array
    * https://leetcode.com/problems/valid-mountain-array/
    * */
    public boolean validMountainArray(int[] arr) {
        boolean incr = false;
        boolean decr = false;
        for(int i = 0; i < arr.length -1 ; i++){
            if(arr[i] < arr[i + 1] && !decr){
                incr = true;
            }else if(arr[i] == arr[i+1]){
                return false;
            }else if(incr && arr[i] > arr[i+1]) {
                decr = true;
            }else{
                return false;
            }
        }
        return incr && decr;
    }

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
