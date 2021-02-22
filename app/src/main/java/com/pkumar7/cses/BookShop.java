//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1158/
//Sol: https://cses.fi/problemset/result/1691182
public class BookShop {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int totalPrice = fs.nextInt();
        int[] price = new int[n];
        for (int i = 0; i < price.length; i++) {
            price[i] = fs.nextInt();
        }
        int[] pages = new int[n];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = fs.nextInt();
        }
        //long ct = System.currentTimeMillis();
        /*int[][]memo = new int[n + 1][totalPrice + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i],-1);
        }
        int ans = dfsMemo(price, pages, 0, totalPrice, memo);*/
        int ans = solve(price, pages, totalPrice);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    private static int solve(int[] prices, int[] pages, int totalPrice) {
        int n = prices.length;
        int[][] dp = new int[n + 1][totalPrice + 1];
        for (int i = 0; i <= totalPrice; i++) {
            dp[0][i] = 0;//We can't get any pages without books
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= totalPrice; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - prices[i - 1] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - prices[i - 1]] + pages[i - 1]);
                }
            }
        }
        return dp[n][totalPrice];
    }

    private static int dfsMemo(int[] prices, int[] pages, int idx, int totalPrice, int[][] memo) {
        if (totalPrice == 0 || idx == pages.length) return 0;
        if (memo[idx][totalPrice] != -1) return memo[idx][totalPrice];
        int res = Integer.MIN_VALUE;
        for (int i = idx; i < prices.length; i++) {
            // We have two choices, either buy this book or skip this book
            if (totalPrice - prices[i] >= 0) {
                //buying this book
                res = Math.max(res,
                        pages[i] + dfsMemo(prices, pages, i + 1, totalPrice - prices[i], memo));
            } else {
                //skipping this book
                res = Math.max(res, dfsMemo(prices, pages, i + 1, totalPrice, memo));
            }
        }
        return memo[idx][totalPrice] = res;
    }

    private static int dfs(int[] prices, int[] pages, int idx, int totalPrice) {
        if (totalPrice == 0 || idx == pages.length) return 0;
        int res = Integer.MIN_VALUE;
        for (int i = idx; i < prices.length; i++) {
            if (prices[i] <= totalPrice) {
                res = Math.max(res,
                        pages[i] + dfs(prices, pages, i + 1, totalPrice - prices[i]));
            } else {
                res = Math.max(res, dfs(prices, pages, i + 1, totalPrice));
            }
        }
        return res;
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