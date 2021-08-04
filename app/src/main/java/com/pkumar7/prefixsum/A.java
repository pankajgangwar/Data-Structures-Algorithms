package com.pkumar7.prefixsum;

import java.util.ArrayList;
import java.util.List;

public class A {

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
