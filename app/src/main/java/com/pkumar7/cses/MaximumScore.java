package com.pkumar7.cses;//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1746/
//Sol: https://cses.fi/problemset/result/1695467
public class MaximumScore {
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
        //int ans = maximumScore(array, m, n);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        //out.printf("%d", ans);
        out.flush();
        out.close();
    }

    public static int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length;
        int i = 0, j = n - 1;
        dp = new int[n + 1][n + 1];
        for (int k = 0; k <= n; k++) {
            Arrays.fill(dp[k], -1);
        }
        return dfs(nums, i, j, 0, multipliers);
    }

    static int[][] dp;

    public static int dfs(int[] nums, int i, int j, int currIdx, int[] multiplier) {
        if (i > j) {
            return 0;
        }
        if (currIdx == multiplier.length - 1) {
            int left = Math.max(nums[i] * multiplier[currIdx], nums[j] * multiplier[currIdx]);
            return left;
        }
        if (dp[i][j] != -1) return dp[i][j];
        int start = (nums[i] * multiplier[currIdx]) + dfs(nums, i + 1, j, currIdx + 1, multiplier);// difference if i is chosen
        int end = (nums[j] * multiplier[currIdx]) + dfs(nums, i, j - 1, currIdx + 1, multiplier);// difference if j is chosen
        dp[i][j] = Math.max(start, end);
        return dp[i][j];
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