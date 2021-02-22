//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1638/
//Sol: https://cses.fi/problemset/result/1688402/
public class GridPaths {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < grid.length; i++) {
            String row = fs.next();
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = row.charAt(j) == '.' ? 1 : -1;
            }
        }
        int ans = solve(grid);
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    private static int solve(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n + 1][n + 1];
        dp[0][1] = 1;
        int mod = (int) 1e9 + 7;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (grid[i - 1][j - 1] == 1) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                    dp[i][j] %= mod;
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return dp[n][n];
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