package com.pkumar7.twopointers;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 21/August/2020
 */
class A {

    /*
    828. Count Unique Characters of All Substrings of a Given String
    https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/
    */
    public int uniqueLetterString(String s) {
        int n = s.length();
        int[][] index = new int[26][2];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(index[i], -1);
        }
        int res = 0, mod = (int)1e9+7;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'A';
            res = (res + (i - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
            index[c][0] = index[c][1];
            index[c][1] = i;
        }
        for (int i = 0; i < 26; i++) {
            res = (res + (n - index[i][1]) * (index[i][1] - index[i][0]) % mod ) % mod;
        }
        return res;
    }

    /* 977. Squares of a Sorted Array
    * https://leetcode.com/problems/squares-of-a-sorted-array/
    */
    public int[] sortedSquares(int[] A) {
        int n = A.length;
        int i = 0, j = n - 1;
        int[] result = new int[A.length];
        for (int p = n - 1; p >= 0; --p) {
            if(Math.abs(A[i]) > Math.abs(A[j])) {
                result[p] = A[i] * A[i];
                i++;
            }else{
                result[p] = A[j] * A[j];
                j--;
            }
        }
        return result;
    }
}
