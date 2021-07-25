package com.pkumar7.math;

public class B {

    /*
     * 1925. Count Square Sum Triples
     * https://leetcode.com/problems/count-square-sum-triples/
     * */
    public int countTriples(int n) {
        int res = 0;
        for (int a = 1; a * a < n * n; a++) {
            for (int b = 1; b * b < n * n; b++) {
                for (int c = 1; c <= n; c++) {
                    if (a == b || b == c) {
                        continue;
                    }
                    if (a * a + b * b == c * c) {
                        res += 1;
                    }
                }
            }
        }
        return res;
    }
}
