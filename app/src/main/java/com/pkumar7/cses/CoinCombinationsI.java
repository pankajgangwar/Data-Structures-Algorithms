//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1635
 * https://leetcode.com/problems/combination-sum-iv/
 */
class CoinCombinationsI {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int target = fs.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = fs.nextInt();
        }
        int res = solve(target, coins);
        out.printf("%d", res);
        out.flush();
        out.close();
    }

    public static int solve(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        int mod = (int) 1e9 + 7;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < n; j++) {
                if (i - coins[j] >= 0) {
                    dp[i] = (dp[i] + dp[i - coins[j]]) % mod;
                }
            }
        }
        return dp[amount];
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        public String next() {
            while (!st.hasMoreElements())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}
