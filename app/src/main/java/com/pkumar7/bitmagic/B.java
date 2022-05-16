package com.pkumar7.bitmagic;

public class B {
    /* 2275. Largest Combination With Bitwise AND Greater Than Zero
     * https://leetcode.com/problems/largest-combination-with-bitwise-and-greater-than-zero/
     * */
    public int largestCombination(int[] candidates) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int curr = 0;
            for(int c : candidates){
                if((c >> i & 1) == 1){
                    curr += 1;
                }
            }
            ans = Math.max(ans, curr);
        }
        return ans;
    }
}
