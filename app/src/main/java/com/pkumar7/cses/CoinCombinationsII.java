//package com.pkumar7.cses;//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1636
 */
class CoinCombinationsII {
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
        int[][] dp = new int[n + 1][amount + 1];
        int mod = (int) 1e9 + 7;
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                dp[i][j] %= mod;
            }
        }
        return dp[n][amount];
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
