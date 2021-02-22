//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1633
 */
class DiceCombinations {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int target = fs.nextInt();
        int[] dice = {1, 2, 3, 4, 5, 6};
        int[] dp = new int[target + 1];
        dp[0] = 1;
        int mod = (int) 1e9 + 7;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < 6; j++) {
                if (i - dice[j] >= 0) {
                    dp[i] += dp[i - dice[j]];
                    dp[i] = dp[i] % mod;
                }
            }
        }
        out.printf("%d", dp[target]);
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
