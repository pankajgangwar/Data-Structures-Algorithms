//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * Prob: https://cses.fi/problemset/task/1637
 * Sol: https://cses.fi/problemset/result/1685289/
 */
class RemovingDigits {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        long ct = System.currentTimeMillis();
        /*int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        int res = solveMemo(n);*/
        int res = solvedp(n);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", res);
        out.flush();
        out.close();
    }

    public static int solvedp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int number = 1; number <= n; number++) {
            String curr = Integer.valueOf(number).toString();
            dp[number] = Integer.MAX_VALUE;
            for (int j = curr.length() - 1; j >= 0; j--) {
                int digit = curr.charAt(j) - '0';
                if (digit == 0) continue;
                dp[number] = Math.min(dp[number], dp[number - digit] + 1);
            }
        }
        return dp[n];
    }

    public static int solveMemo(int n, int[] memo) { // n = 27
        if (n == 0) return 0;
        if (memo[n] != -1) {
            return memo[n];
        }
        String number = Integer.valueOf(n).toString();
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0';
            if (digit == 0) continue;
            int sub_res = solveMemo(n - digit, memo);
            if (sub_res != Integer.MAX_VALUE) {
                res = Math.min(res, sub_res + 1);
            }
        }
        return memo[n] = res;
    }

    public static int solvedfs(int n) { // n = 27
        if (n == 0) return 0;
        String number = String.valueOf(n);
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0';
            if (digit == 0) continue;
            int sub_res = solvedfs(n - digit);
            if (sub_res != Integer.MAX_VALUE) {
                res = Math.min(res, sub_res + 1);
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
