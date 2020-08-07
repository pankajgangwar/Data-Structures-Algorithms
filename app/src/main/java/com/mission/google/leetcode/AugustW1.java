package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Pankaj Kumar on 3/Aug/2020
 */
class AugustW1 {

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


    /* 
    421. Maximum XOR of Two Numbers in an Array
    https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */
    public int findMaximumXOR(int[] nums) {
        return findMaximumXORUsingTrie(nums);
        //return findMaximumXORUsingSets(nums);
    }
    
    class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        public TrieNode() { }
    }

    public int findMaximumXORUsingTrie(int[] nums){
        int max = nums[0];
        for(int n : nums) max = Math.max(max, n);

        int l = Integer.toBinaryString(max).length();
        int len = nums.length, bitMask = 1 << l;

        String[] strNums = new String[len];

        for(int i = 0; i < len; ++i){
            strNums[i] = Integer.toBinaryString(bitMask | nums[i]).substring(1);
        }

        int max_xor = 0;
        TrieNode root = new TrieNode();
        for(String n : strNums) {
            TrieNode curr = root, xorNode = root;
            int curr_xor = 0;
            for(Character bit : n.toCharArray()) {
                if(curr.children.containsKey(bit)) {
                    curr = curr.children.get(bit);
                }else{
                    TrieNode node = new TrieNode();
                    curr.children.put(bit, node);
                    curr = node;
                }

                Character bitFlip = bit == '0' ? '1' : '0';

                if(xorNode.children.containsKey(bitFlip)) {
                    curr_xor = (curr_xor << 1) | 1;
                    xorNode = xorNode.children.get(bitFlip);
                }else {
                    curr_xor = curr_xor << 1;
                    xorNode = xorNode.children.get(bit);
                }
                max_xor = Math.max(max_xor, curr_xor);
            }
        }
        return max_xor;
    }
    
    public int findMaximumXORUsingSets(int[] nums) {
        int max_xor = 0, curr_xor = 0;
        HashSet<Integer> prefixes = new HashSet<>();
        int max = 0;
        for(int n : nums) max = Math.max(max, n);

        int l = Integer.toBinaryString(max).length();
    
        for(int i = l - 1; i >=0 ; --i){
            max_xor = max_xor << 1; // make space of right most bit

            curr_xor = max_xor | 1; // add 1 to right most bit

            prefixes.clear();
            for(int n : nums){
                prefixes.add(n >> i); // add ith bit to prefixes
            }

            for(int n : prefixes) {
                // p1^p2 = curr
                // curr ^ p2 = p1
                if(prefixes.contains(curr_xor ^ n)){ 
                    max_xor = curr_xor;
                    break;
                }
            }
        }
        return max_xor;
    }

    /*
    939. Minimum Area Rectangle
    https://leetcode.com/problems/minimum-area-rectangle/ */
    public int minAreaRect(int[][] points) {
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for(int i = 0; i < points.length; i++) {
            if(!map.containsKey(points[0])) {
                map.putIfAbsent(points[i][0], new HashSet<Integer>());
            }
            map.get(points[i][0]).add(points[i][1]);
        }

        int min = Integer.MAX_VALUE;
        for(int[] p1 : points) {
            for(int[] p2 : points) {
                if(p1[0] == p2[0] || p1[1] == p2[1]) continue;
                if(map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1]) ){
                    min = Math.min(min, Math.abs(p1[0] - p2[0]) *  Math.abs(p1[1] - p2[1]) );
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /* 
    907. Sum of Subarray Minimums
    https://leetcode.com/problems/sum-of-subarray-minimums/ 
    Revisit
    */
    public int sumSubarrayMins(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        seen = new HashSet<>();
        //return helper(arr, start, end);
        return usingMonotoneStack(arr);
    }

    public int usingMonotoneStack(int[] arr) {
        Stack<int[]> stk_p = new Stack<>();
        Stack<int[]> stk_n = new Stack<>();
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];

        for(int i = 0; i < n; i++) {
            left[i] = i + 1;
            right[i] = n - i;
        }

        for(int i = 0; i < n; i++) {
            while(!stk_p.isEmpty() && stk_p.peek()[0] > arr[i]) {
                stk_p.pop();
            }
            left[i] = stk_p.isEmpty() ? (i + 1) : (i - stk_p.peek()[1]);
            stk_p.push(new int[]{arr[i], i});
        }

        for(int i = 0; i < n; i++) {
            while(!stk_n.isEmpty() && stk_n.peek()[0] > arr[i]) {
                int[] top = stk_n.pop();
                right[top[1]] = i - top[1];
            }
            stk_n.push(new int[]{arr[i], i});
        }

        int ans = 0;
        int mod = (int)1e9 + 7;
        for(int i = 0; i < n; i++){
            ans = (ans + arr[i] * left[i] * right[i]) % mod;
        }
        return ans;
    }
    
    HashSet<String> seen;
    public int helper(int[] arr, int start, int end) { // TLE
        int mod = (int)1e9 + 7;
        String s = start + "," + end;
        if(!seen.add(s)) {
            return 0;
        }
        int n = arr.length;
        if(start > end) return 0;
        if(start == end ) return arr[start];
        
        int min = Integer.MAX_VALUE;
        
        for(int i = start; i <= end; i++) {
            if(min > arr[i]) {
                min = arr[i];
            }
        }
        int l = helper(arr, start, end - 1) % mod;
        int r = helper(arr, start + 1, end) % mod;
        int res = (l + r + min) % mod;
        return res;
    }

    /* 
    LC : 260. Single Number III
    https://leetcode.com/problems/single-number-iii/ */
    public int[] singleNumber(int[] nums) {
        int diff = 0;
        for(int n : nums){
            diff = diff ^ n; 
        }

        diff = diff & -diff; // Get the LSB, power of 2
        int[] res = new int[]{ 0, 0 };

        for(int n : nums){
            int idx = diff & n;
            res[idx] = res[idx] ^ n;
        }
        return res;
    }

}