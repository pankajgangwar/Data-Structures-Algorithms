package com.mission.google;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class Current
{
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

    public static void main(String[] args)    {
        Current current = new Current();
        //current.maxDistance(new int[]{1,2,3,4,7}, 3);
        //current.mostVisited(4, new int[]{1,3,1,2});
    }

    // https://leetcode.com/problems/binary-tree-pruning/
    // https://leetcode.com/problems/total-hamming-distance/

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
