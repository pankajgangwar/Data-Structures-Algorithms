//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1746/
//Sol: https://cses.fi/problemset/result/1695467
public class ArrayDescription {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        int[] array = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            array[i] = fs.nextInt();
        }
        //long ct = System.currentTimeMillis();
        int ans = solve(array, m, n);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    private static int solve(int[] array, int m, int n) {
        int mod = (int) 1e9 + 7;
        int[][] dp = new int[n + 2][m + 2];
        for (int i = 1; i <= n; i++) {
            for (int x = 1; x <= m; x++) {
                if (i == 1) {
                    if (array[i] == 0 || array[i] == x) {
                        dp[i][x] = 1;
                    } else {
                        dp[i][x] = 0;
                    }
                } else {
                    if (array[i] == 0 || array[i] == x) {
                        dp[i][x] = ((dp[i - 1][x - 1] + dp[i - 1][x]) % mod + (dp[i - 1][x + 1])) % mod;
                    } else {
                        dp[i][x] = 0;
                    }
                }
            }
        }
        int res = 0;
        for (int x = 1; x <= m; x++) {
            res = (res + dp[n][x]) % mod;
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