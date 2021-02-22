package com.pkumar7;

import com.pkumar7.datastructures.ListNode;
import com.pkumar7.unionfind.UnionFind;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    // https://leetcode.com/problems/remove-boxes/

    public static void main(String[] args) {
        Current current = new Current();
        int c_k = 3;
        //int[] res = current.mostCompetitive(c, c_k);
        //System.out.println("res = " + Arrays.toString(res));
        int[] arr = {1, -1, -2, 4, -7, 3};
        int[] nums = new int[]{6, 10, 6};
        //int res = current.longestPalindrome("cacb", "cbba");
    }

    public boolean check(int[] nums) {
        int n = nums.length;
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[(i + 1) % n]) {
                k++;
            }
            if (k > 1) return false;
        }
        return true;
    }

    /* 1726. Tuple with Same Product
     * https://leetcode.com/problems/tuple-with-same-product/
     * */
    public int tupleSameProduct(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                int prod = nums[i] * nums[j];
                res += 8 * map.getOrDefault(prod, 0);
                map.put(prod, map.getOrDefault(prod, 0) + 1);
            }
        }
        return res;
    }

    /* 881. Boats to Save People
     * https://leetcode.com/problems/boats-to-save-people/
     * */
    public int numRescueBoats(int[] arr, int limit) {
        int n = arr.length;
        Integer[] people = new Integer[n];
        for (int i = 0; i < n; i++) {
            people[i] = arr[i];
        }
        Arrays.sort(people, (a, b) -> b - a);
        int boats = 0;
        for (int i = 0, j = n - 1; i <= j; ) {
            if (people[i] + people[j] <= limit) {
                i += 1;
                j -= 1;
            } else if (people[i] + people[j] > limit) {
                i += 1;
            }
            boats += 1;
        }
        return boats;
    }

    /* 1722. Minimize Hamming Distance After Swap Operations
     * https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
     * */
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        List<Integer> t = new ArrayList<>();
        for (int i = 0; i < target.length; i++) {
            t.add(target[i]);
        }
        UnionFind unionfind = new UnionFind(n);
        for (int[] s : allowedSwaps) {
            unionfind.union(s[0], s[1]);
        }
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<Integer, HashMap<Integer, Integer>>();
        for (int i = 0; i < source.length; i++) {
            int a = source[i];
            int root = unionfind.find(i);
            map.putIfAbsent(root, new HashMap<Integer, Integer>());
            HashMap<Integer, Integer> store = map.get(root);
            store.put(a, store.getOrDefault(a, 0) + 1);
        }
        int res = 0;
        for (int i = 0; i < target.length; i++) {
            int a = target[i];
            int root = unionfind.find(i);
            HashMap<Integer, Integer> store = map.getOrDefault(root, new HashMap<Integer, Integer>());
            if (store.getOrDefault(a, 0) == 0) res++;
            else store.put(a, store.get(a) - 1);
        }
        return res;
    }

    /*
     * https://cses.fi/problemset/task/1687/
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
