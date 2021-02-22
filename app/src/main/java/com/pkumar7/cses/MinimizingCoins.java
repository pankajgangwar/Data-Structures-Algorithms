//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1633
 * https://cses.fi/problemset/result/1675817/
 */
class MinimizingCoins {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int target = fs.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = fs.nextInt();
        }
        coins = Arrays.stream(coins).
                boxed().
                sorted((a, b) -> b.compareTo(a)). // sort descending
                mapToInt(i -> i).
                toArray();
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0) {
                    int sub_res = dp[i - coins[j]];
                    if (sub_res != Integer.MAX_VALUE && sub_res + 1 < dp[i]) {
                        dp[i] = sub_res + 1;
                    }
                }
            }
        }
        int res = dp[target] == Integer.MAX_VALUE ? -1 : dp[target];
        out.printf("%d", res);
        out.flush();
        out.close();
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
