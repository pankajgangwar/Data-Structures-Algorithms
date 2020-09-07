package com.mission.google;

import java.util.LinkedList;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class Current {
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /*
       https://codeforces.com/blog/entry/20935
       https://www.spoj.com/problems/PT07X/
       https://leetcode.com/problems/sum-of-distances-in-tree/
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */
    /* Binary search problems*/
    // https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/
    // https://leetcode.com/problems/max-points-on-a-line
    public static void main(String[] args) {
        Current current = new Current();
        //current.numTriplets(a,b);
        //String s = "bbbaaa";
        //int[] cost = {4,9,3,8,8,9};
    }

    /*
    * 801. Minimum Swaps To Make Sequences Increasing
    * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
    * */
    public int minSwap(int[] a, int[] b) {
        return dfs(a, b, 0);
    }

    public int dfs(int[] a, int[] b, int cost){
        boolean isA = true;
        for(int i = 0; i < a.length - 1; i++){
            if(a[i] > a[i+1]) {
                isA = false;
                break;
            }
        }
        boolean isB = true;
        for(int i = 0; i < b.length - 1; i++){
            if(b[i] > b[i+1]) {
                isB = false;
                break;
            }
        }
        if(isA && isB){
            return cost;
        }
        return cost;
    }

}
