package com.mission.google.dp;

import java.util.HashMap;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {
    /*1553. Minimum Number of Days to Eat N Oranges
    * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
    * */
    HashMap<Integer, Integer> dp = new HashMap<>();
    public int minDaysDp(int n) {
        if(n <= 1) return n;
        if(dp.containsKey(n)) return dp.get(n);
        int res = 1 + Math.min(n % 2 + minDaysDp(n / 2), n % 3 + minDaysDp(n / 3));
        dp.put(n, res);
        return res;
    }
}
