package com.pkumar7.dp;

import java.util.ArrayList;
import java.util.List;

public class C {

    /*
     * 2100. Find Good Days to Rob the Bank
     * https://leetcode.com/problems/find-good-days-to-rob-the-bank/
     * */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {

        List<Integer> res = new ArrayList<>();
        int n = security.length;

        if(time == 0){
            for (int i = 0; i < n; i++) {
                res.add(i);
            }
            return res;
        }

        int[] left = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = security[i - 1] >= security[i] ? left[i - 1] + 1 : 1;
        }
        int[] right = new int[n];
        right[n-1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = security[i] <= security[i + 1] ? right[i + 1] + 1 : 1;
        }
        for (int i = time; i < n - time; i++) {
            if(left[i - 1] >= time && right[i+1] >= time && security[i - 1] >= security[i] && security[i] <= security[i+1]){
                res.add(i);
            }
        }
        return res;
    }
}
