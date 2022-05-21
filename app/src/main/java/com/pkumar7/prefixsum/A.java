package com.pkumar7.prefixsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A {

    /*
     * https://leetcode.com/problems/number-of-ways-to-select-buildings/
     * 2222. Number of Ways to Select Buildings
     * */
    public long numberOfWays(String s) {
        int n = s.length();
        long[] prefix = new long[n];
        int d = s.charAt(0) - '0';
        prefix[0] = d;
        for (int i = 1; i < n; i++) {
            int digit = s.charAt(i) - '0';
            prefix[i] = digit + prefix[i - 1];
        }
        long ways = 0;
        for (int i = 1; i < n; i++) {
            int digit = s.charAt(i) - '0';
            long onesOnRight = prefix[n - 1] - prefix[i];
            if(digit == 0){
                ways += prefix[i - 1] * onesOnRight;
            }else{
                long leftzero = i - prefix[i - 1];
                long rightZero = (n - i - 1) - onesOnRight;
                ways += (leftzero * rightZero);
            }
        }
        return ways;
    }

    /*
     * https://leetcode.com/problems/grid-game/
     * 2017. Grid Game
     * */
    public long gridGame(int[][] grid) {
        long top = Arrays.stream(grid[0]).asLongStream().sum();
        long bottom = 0;
        long res = Long.MAX_VALUE;
        for (int i = 0; i < grid[0].length; i++) {
            top -= grid[0][i];
            res = Math.min(res, Math.max(top, bottom));
            bottom += grid[1][i];
        }
        return res;
    }


    /* 1177. Can Make Palindrome from Substring
     * https://leetcode.com/problems/can-make-palindrome-from-substring/
     * */
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        int[][] count = new int[n + 1][26];

        for (int i = 0; i < n; i++) {
            count[i + 1] = count[i].clone();
            int idx = s.charAt(i) - 'a';
            count[i + 1][idx]++;
        }
        List<Boolean> res = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int sum = 0;
            for (int j = 0; j < 26; j++) {
                sum += (count[q[1] + 1][j] - count[q[0]][j]) % 2;
            }
            res.add(sum / 2 <= q[2]);
        }
        return res;
    }
}
