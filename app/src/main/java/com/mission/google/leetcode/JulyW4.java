package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Created by Pankaj Kumar on 26/July/2020
 */
class JulyW4 {

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
    /* https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/ */
    /* https://leetcode.com/problems/k-th-smallest-prime-fraction/ */
    /* https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/ */


    /* https://leetcode.com/problems/divide-chocolate/ */
    /* https://leetcode.com/problems/integer-replacement/ */
    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */

    public static void main(String[] args) {
        JulyW4 w4 = new JulyW4();
        int res = w4.minFlips("101");
        System.out.println("res = " + res);
    }

    /*
    1104. Path In Zigzag Labelled Binary Tree
    https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
    */
    public List<Integer> pathInZigZagTree(int label) {

        return new ArrayList<>();
    }

    /*
    https://leetcode.com/problems/design-a-leaderboard/
    */

    /*
    LC : 969. Pancake Sorting
    https://leetcode.com/problems/pancake-sorting/
    */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> res = new ArrayList<>();
        int largest = A.length;
        int n = A.length;
        for (int i = 0; i < n; i++) {
            int idx = findLargest(A, largest);
            reverse(A, 0, idx);
            reverse(A, 0, largest - 1 );
            res.add(idx + 1);
            res.add(largest--);
        }
        return res;
    }

    public void reverse(int[] arr, int low, int high){
        while (low < high){
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            low++;
            high--;
        }
    }

    public int findLargest(int [] arr, int target){
        int n = arr.length;
        for(int i = 0; i < n; i++){
            if(arr[i] == target) return i;
        }
        return -1;
    }

    /* 
    LC : 926. Flip String to Monotone Increasing
    https://leetcode.com/problems/flip-string-to-monotone-increasing
    */
    public int minFlipsMonoIncr(String S) {
        char[] arr = S.toCharArray();
        int flipCount = 0;
        int oneCount = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == '0') {
                if(oneCount == 0) continue;
                flipCount++;
            }else {
                oneCount++;   
            }
            if(flipCount > oneCount) {
                flipCount = oneCount;
            }
        }
        return flipCount;
    }

    public int countOdds(int low, int high) {
        int ret = 0;
        ret += (high - low) / 2 ;
        if(low % 2 == 1 || high % 2 ==1 ) ret++;
        return ret;
    }

    /*
     LC : 1524. Number of Sub-arrays With Odd Sum
     https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum
     */
    public int numOfSubarrays(int[] arr) {
        int ans = 0;
        int mod = (int)1e9 + 7;
        int odd = 0, even = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] % 2 == 1){
                int temp = odd;
                odd = even + 1;
                even = temp;
            }else{
                even++;
            }
            ans = (ans + odd)  % mod;
        }
        return ans;
    }

    /*
    * LC : 1530. Number of Good Leaf Nodes Pairs
    * https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
    * */
    int res = 0;
    public int countPairs(TreeNode root, int distance) {
        helper(root, distance);
        return res;
    }

    public List<Integer> helper(TreeNode root, int distance) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        if(root.left == null && root.right == null) {
            list.add(1);
            return list;
        }
        List<Integer> left = helper(root.left, distance);
        List<Integer> right = helper(root.right, distance);
        for (int x : left) {
            for (int y :  right) {
                if(x + y <= distance ){
                    res++;
                }
            }
        }
        for(int x : left){
            if(x + 1 > distance)continue;
            list.add(x + 1);
        }
        for(int y : right){
            if(y + 1 > distance)continue;
            list.add(y + 1);
        }
        return list;
    }

    /*
    1529. Bulb Switcher IV
    https://leetcode.com/problems/bulb-switcher-iv/
    */
    public int minFlips(String target) {
        int flips = 0;
        for (int i = 0; i < target.length(); i++){
            if(i > 0 && target.charAt(i) != target.charAt(i-1)){
                flips++;
            }
            if(i == 0 && target.charAt(i) == '1'){
                flips++;
            }
        }
        return flips;
    }

    /*
    LC : 1528
    https://leetcode.com/problems/shuffle-string/ */
    public String restoreString(String s, int[] indices) {
        char[] arr = s.toCharArray();
        char[] copy = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            copy[indices[i]] = arr[i];
        }
        return new String(copy);
    }

}
