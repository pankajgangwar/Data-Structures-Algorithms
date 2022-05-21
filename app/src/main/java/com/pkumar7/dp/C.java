package com.pkumar7.dp;

import java.util.ArrayList;
import java.util.List;

public class C {

    public int numTilings(int n) {
        if(n <= 2) return n;
        int[] dp = new int[n + 1];
        int mod = (int)1e9 + 7;
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 5;
        for (int i = 4; i <= n ; i++) {
            dp[i] += 2*(dp[i-1]) + (dp[i-3]);
            dp[i] %= mod;
        }
        return dp[n];
    }

    /*
     * https://leetcode.com/problems/champagne-tower/
     * 799. Champagne Tower
     * */
    public double champagneTower(int poured_glass, int query_row, int query_glass) {
        double[][] tower = new double[query_row + 1][query_row + 1];
        tower[0][0] = (double)poured_glass;

        for (int i = 0; i <= query_row; i++) {
            double[] currentRow = tower[i];
            for (int j = 0; j < i + 1; j++) {
                double extraAmount = Math.max(0, (currentRow[j] - 1.0));
                currentRow[j] = Math.min(1, currentRow[j]);
                if(extraAmount > 0 && i+1 <= query_row ){
                    tower[i + 1][j] += extraAmount / 2;
                    tower[i + 1][j + 1] += extraAmount / 2;
                }
            }
        }
        return tower[query_row][query_glass];
    }

    /* 2266. Count Number of Texts
    * https://leetcode.com/problems/count-number-of-texts/
    * */
    public int countTexts(String s) {
        int mod = (int)1e9 + 7;
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] % mod;
            if(i > 1 && s.charAt(i - 2) == s.charAt(i - 1)){
                dp[i] = (dp[i] + dp[i - 2]) % mod;
                if(i > 2 && s.charAt(i - 3) == s.charAt(i - 1)){
                    dp[i] = (dp[i] + dp[i - 3]) % mod;
                    if(i > 3 && (s.charAt(i-1) == '7' || s.charAt(i-1) == '9') && s.charAt(i-1) == s.charAt(i-4)){
                        dp[i] = (dp[i] + dp[i - 4]) % mod;
                    }
                }
            }
        }
        return dp[n] % mod;
    }

    /*
    2267. Check if There Is a Valid Parentheses String Path
    * https://leetcode.com/problems/check-if-there-is-a-valid-parentheses-string-path/
    * */
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int max = m + n - 2;
        memo = new Boolean[m + 1][n + 1][max + 1];

        return helper(grid, 0, 0, 0);
    }

    Boolean[][][] memo;
    public boolean helper(char[][] grid, int curr_x, int curr_y, int open){
        int m = grid.length;
        int n = grid[0].length;
        int max = m + n - 2;

        if(curr_x >= m || curr_y >= n) return false;

        char ch = grid[curr_x][curr_y];
        if(ch == '(') open++;
        else open--;

        if(open < 0) return false;
        if(open > max) return false;

        if(curr_x == m - 1 && curr_y == n - 1){
            return (open == 0);
        }
        if(memo[curr_x][curr_y][open] != null) {
            return memo[curr_x][curr_y][open];
        }
        return memo [curr_x][curr_y][open] = helper(grid, curr_x, curr_y + 1,  open) || helper(grid, curr_x + 1, curr_y,  open);
    }

    /*
     * 2100. Find Good Days to Rob the Bank
     * https://leetcode.com/problems/find-good-days-to-rob-the-bank/
     * */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {

        List<Integer> res = new ArrayList<>();
        int n = security.length;

        if(time == 0){
            for (int i = 0; i < n; i++) {
                res.add(i);
            }
            return res;
        }

        int[] left = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = security[i - 1] >= security[i] ? left[i - 1] + 1 : 1;
        }
        int[] right = new int[n];
        right[n-1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = security[i] <= security[i + 1] ? right[i + 1] + 1 : 1;
        }
        for (int i = time; i < n - time; i++) {
            if(left[i - 1] >= time && right[i+1] >= time && security[i - 1] >= security[i] && security[i] <= security[i+1]){
                res.add(i);
            }
        }
        return res;
    }
}
