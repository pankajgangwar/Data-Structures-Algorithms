package com.pkumar7.dp;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    public static void main(String[] args) {
        A curr = new A();
        curr.stoneGameV(new int[]{6,2,3,4,5,5});
    }

    /* 871. Minimum Number of Refueling Stops
     * https://leetcode.com/problems/minimum-number-of-refueling-stops/
     * */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        //return usingPriorityQueue(target, startFuel, stations);
        return usingDp(target, startFuel, stations);
    }

    public int usingDp(int target, int startFuel, int[][] stations){
        int n = stations.length;
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0 && dp[j] >= stations[i][0]; j--) {
                dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
            }
        }
        for (int i = 0; i <= n; i++) {
            if(dp[i] >= target) return i;
        }
        return -1;
    }

    public int usingPriorityQueue(int target, int startFuel, int[][] stations){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> -a + b);
        long curr = startFuel;
        int i = 0;
        int n = stations.length;
        int res = 0;
        while (true){
            while (i < n && stations[i][0] <= curr){
                maxHeap.offer(stations[i][1]);
                i++;
            }
            if(curr >= target) return res;
            if(maxHeap.isEmpty()) return -1;
            curr += maxHeap.poll();
            res++;
        }
    }

    /* 1594. Maximum Non Negative Product in a Matrix
     * https://leetcode.com/problems/maximum-non-negative-product-in-a-matrix/
     * */
    public int maxProductPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[][] max = new long[m][n];
        long[][] min = new long[m][n];
        max[0][0] = min[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {// first col
            max[i][0] = min[i][0] = max[i-1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {// first row
            max[0][i] = min[0][i] = max[0][i-1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(grid[i][j] < 0){
                    max[i][j] = Math.min(min[i][j-1], min[i-1][j]) * grid[i][j];
                    min[i][j] = Math.max(max[i][j-1], max[i-1][j]) * grid[i][j];
                }else{
                    min[i][j] = Math.min(min[i][j-1], min[i-1][j]) * grid[i][j];
                    max[i][j] = Math.max(max[i][j-1], max[i-1][j]) * grid[i][j];
                }
            }
        }
        int mod = (int)1e9 + 7;
        long ans = max[m - 1][n - 1] % mod;
        return ans < 0 ? -1 : (int)ans;
    }

    public int maxProductPath1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        dfs(grid, 0, 0, grid[0][0] );
        return maxprod < 0 ? -1 : (int)(maxprod % mod );
    }
    long maxprod = Long.MIN_VALUE;
    int mod = (int)1e9 + 7;
    public void dfs(int[][] grid, int curr_x, int curr_y, long prod){
        int m = grid.length;
        int n = grid[0].length;
        if(curr_x == m - 1 && curr_y == n - 1 ){
            maxprod = Math.max(maxprod, prod);
            return;
        }
        int[][] dirs = new int[][]{{1,0},{0,1}};
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + curr_x;
            int next_y = dirs[i][1] + curr_y;
            if(isValid(grid, next_x, next_y)){
                if(grid[next_x][next_y] == 0){
                    maxprod = Math.max(maxprod, 0);
                    continue;
                }
                dfs(grid, next_x, next_y, prod * (long)grid[next_x][next_y] );
            }
        }
    }

    public boolean isValid(int[][] grid, int next_x, int next_y ){
        int m = grid.length;
        int n = grid[0].length;
        if(next_x >= m || next_x < 0 || next_y >= n || next_y < 0 ){
            return false;
        }
        return true;
    }



    /* 1027. Longest Arithmetic Subsequence
     * https://leetcode.com/problems/longest-arithmetic-subsequence/
     * */
    public int longestArithSeqLength(int[] arr) {
        int n = arr.length;
        int res = 2;
        HashMap<Integer, Integer>[] dp = new HashMap[n];
        dp[0] = new HashMap<>();
        for (int j = 1; j < n; j++) {
            dp[j] = new HashMap<>();
            for (int i = 0; i < j; i++) {
                int diff = arr[j] - arr[i];
                dp[j].put(diff, dp[i].getOrDefault(diff, 1) + 1);
                res = Math.max(res, dp[j].get(diff));
            }
        }
        return res;
    }


    /*
    * 1563. Stone Game V
    * https://leetcode.com/problems/stone-game-v/
    * */
    int[] prefixsum;
    int[][] memo;
    public int stoneGameV(int[] stones) {
        int n = stones.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        prefixsum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixsum[i + 1] = stones[i] + prefixsum[i];
        }
        int res = dfs(stones, 0, n - 1);
        System.out.println("res = " + res);
        return res;
    }

    public int dfs(int[] arr, int i, int j) {
        if(i == j) return 0;
        if(memo[i][j] != -1) return memo[i][j];
        memo[i][j] = 0;
        for (int p = i + 1; p <= j; p++){
            int l = prefixsum[p] - prefixsum[i];
            int r = prefixsum[j + 1] - prefixsum[p];
            if(l < r){
                memo[i][j] = Math.max(memo[i][j], l + dfs(arr, i, p - 1));
            }else if(l > r){
                memo[i][j] = Math.max(memo[i][j], r + dfs(arr, p, j));
            }else{
                memo[i][j] = Math.max(memo[i][j],
                        l + Math.max(dfs(arr, i, p - 1), dfs(arr,p, j)));
            }
        }
        return memo[i][j];
    }

    /* 1473. Paint House III
    * https://leetcode.com/problems/paint-house-iii/
    * */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m][n][target + 1];
        for (int i = 0; i < m; i++ ) {
            for(int j = 0; j < n; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        int res = dfs(houses, cost, m, n, target, 0, 0, -1, dp);
        return res == max ? -1 : res;
    }

    int max = (int)1e9;
    public int dfs(int[] h, int[][] c, int m, int n, int target, int housePainted, int currTarget, int neighColor, int[][][] dp) {
        if(housePainted == m) {
            if(target == currTarget) return 0;
            return max;
        }
        if(currTarget == target + 1) return max;
        if(neighColor != -1){
            if(dp[housePainted][neighColor][currTarget] != -1) return dp[housePainted][neighColor][currTarget];
        }
        int res = max;
        for (int currColor = 0; currColor < n; currColor++) {
            boolean needPaint = true;
            if (h[housePainted] != 0) { // If house is already painted
                if(h[housePainted] != currColor + 1){ // If color is not same as neighbor, skip it
                    continue;
                }else{
                    needPaint = false;
                }
            }
            if (currColor != neighColor) {
                res = Math.min(res, dfs(h, c, m, n, target, housePainted + 1, currTarget + 1, currColor, dp) + ((!needPaint) ? 0 : c[housePainted][currColor]));
            }else {
                res = Math.min(res, dfs(h, c, m, n, target, housePainted + 1, currTarget, currColor, dp) + ((!needPaint) ? 0 : c[housePainted][currColor]));
            }
        }
        if(neighColor != -1) {
            dp[housePainted][neighColor][currTarget] = res;
        }
        return res;
    }

    /*1553. Minimum Number of Days to Eat N Oranges
    * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
    * */
    HashMap<Integer, Integer> dp = new HashMap<>();
    public int minDaysDp(int n) {
        if(n <= 1) return n;
        if(dp.containsKey(n)) return dp.get(n);
        int res = 1 + Math.min(n % 2 + minDaysDp(n / 2), n % 3 + minDaysDp(n / 3));
        dp.put(n, res);
        return res;
    }
}
