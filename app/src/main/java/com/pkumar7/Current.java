package com.pkumar7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

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

    //https://leetcode.com/problems/odd-even-jump/

    public static void main(String[] args) {
        Current current = new Current();
        int c_k = 3;
        //int[] res = current.mostCompetitive(c, c_k);
        //System.out.println("res = " + Arrays.toString(res));
        int[] arr = {1,-1,-2,4,-7,3};
        //int res = current.maxResult(arr, 2);
        //System.out.println(res);
        //int res = current.sortedSum(Arrays.asList(4,3,2,1));
        int[][] grid = new int[][]{
                {1,1,1,-1,-1},
                {1,1,1,-1,-1},
                {-1,-1,-1,1,1},
                {1,1,1,1,-1},
                {-1,-1,-1,-1,-1}
        };
    }

    /*
    * https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
    * */
    public static List<Long> getMaxArea(int w, int h, List<Boolean> isVertical, List<Integer> distance) {
        List<Long> res = new ArrayList<Long>();
        List<Integer> hList = new ArrayList<>();
        hList.add(0);
        hList.add(h);
        Collections.sort(hList);

        List<Integer> vList = new ArrayList<>();
        vList.add(0);
        vList.add(w);
        Collections.sort(vList);
        for (int i = 0; i < isVertical.size(); i++) {
            if(isVertical.get(i)){
                vList.add(distance.get(i));
                Collections.sort(vList);
            }else{
                hList.add(distance.get(i));
                Collections.sort(hList);
            }
            long maxArea = maxArea(hList, vList);
            res.add(maxArea);
        }
        return res;
    }

    public static int maxArea(List<Integer> hList, List<Integer> vList) {
        long H = 0;
        for (int i = 1; i < hList.size() ; i++) {
            H = Math.max(H, (long)hList.get(i) - hList.get(i-1));
        }
        long W = 0;
        for (int i = 1; i < vList.size() ; i++) {
            W = Math.max(W, (long)vList.get(i) - vList.get(i-1));
        }
        return (int) (H * W % (int)(1e9+7));
    }

    /* HackerRank problems*/
    public static long taskOfPairing(List<Long> freq) {
        long pairs = 0L;
        LinkedList<Long> freqs = new LinkedList<>(freq);

        Long prevWeight = 0L;
        while(!freqs.isEmpty()){
            Long dumbbells = freqs.pollFirst() + prevWeight;
            if(dumbbells % 2 == 0){
                pairs += dumbbells / 2;
                prevWeight = 0L;
            }else if(dumbbells > 2){
                prevWeight = 1L;
                pairs += dumbbells / 2;
            }else{
                prevWeight = dumbbells;
            }
        }
        BigInteger big = BigInteger.valueOf(pairs);
        return big.longValue();
    }

    /* 1642. Furthest Building You Can Reach
    * https://leetcode.com/problems/furthest-building-you-can-reach/
    * */


    /* 1505. Minimum Possible Integer After at Most K Adjacent Swaps On Digits
    * https://leetcode.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/
    * */
    public String minInteger(String num, int k) {
        return null;
    }

    /* 447. Number of Boomerangs
     * https://leetcode.com/problems/number-of-boomerangs/
     * */
    public int numberOfBoomerangs(int[][] points) {
        int n = points.length;
        if(n == 1) return 0;
        return n;
    }


    /*
     * https://leetcode.com/problems/count-subtrees-with-max-distance-between-cities/
     * */
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        int[] res = new int[n];
        return res;
    }

    /* 1000. Minimum Cost to Merge Stones
     * https://leetcode.com/problems/minimum-cost-to-merge-stones/
     * */
    public int mergeStones(int[] stones, int K) {
        return 0;
    }


}
