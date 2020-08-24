package com.mission.google.dp;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    public static void main(String[] args) {
        A curr = new A();
        curr.stoneGameV(new int[]{6,2,3,4,5,5});
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
