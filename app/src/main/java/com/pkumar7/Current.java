package com.pkumar7;

import com.pkumar7.datastructures.FenwickTree.RangeQueryPointUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
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
    /* Binary search problems*/
    // https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/
    // https://leetcode.com/problems/max-points-on-a-line

    public static void main(String[] args) {
        Current current = new Current();
    }

    final int INT_SIZE = 32;
    class TrieNode {
        int value;  // Only used in leaf nodes
        TrieNode[] arr = new TrieNode[2];

        public TrieNode() {
            value = 0;
            arr[0] = null;
            arr[1] = null;
        }
    }
    TrieNode root;
    void insert(int pre_xor) {
        TrieNode temp = root;
        for (int i = INT_SIZE - 1; i >= 0; i--) {
            int val = (pre_xor & (1 << i)) >= 1 ? 1 : 0;
            if (temp.arr[val] == null)
                temp.arr[val] = new TrieNode();

            temp = temp.arr[val];
        }
        temp.value = pre_xor;
    }

     int query(int pre_xor) {
        TrieNode temp = root;
        for (int i = INT_SIZE - 1; i >= 0; i--) {
            int val = (pre_xor & (1 << i)) >= 1 ? 1 : 0;
            if (temp.arr[1 - val] != null){
                temp = temp.arr[1 - val];
            }
            else if (temp.arr[val] != null) {
                temp = temp.arr[val];
            }
        }
        return pre_xor ^ (temp.value);
    }

    int maxSubarrayXOR(int arr[], int n, String s, int k) {
        root = new TrieNode();
        insert(0);
        int result = Integer.MIN_VALUE;
        int pre_xor = 0;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            pre_xor = pre_xor ^ arr[i];
            insert(pre_xor);
            if(i == n - 1)continue;
            String rem = s.substring(i + 1);
            System.out.println("rem = " + rem);
            if(Integer.parseInt(rem) % k == 0){
                result = Math.max(result, query(pre_xor));
                map.putIfAbsent(result, new ArrayList<>());
                map.get(result).add(Integer.parseInt(rem));
                System.out.println(" Divisible " + rem + " res " + result);
            }
        }
        List<Integer> list = map.get(result);
        Collections.sort(list, Collections.reverseOrder());
        int maxnum = list.get(0);
        System.out.println("maxnum = " + maxnum + " result " + result);
        return result;
    }

    /*
    * https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/
    * */
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        range = requests;
        return max;
    }

    int max = 0;
    public int maxsum(ArrayList<Integer> list, int[][] range){
        long[] arr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (long)list.get(i);
        }
        RangeQueryPointUpdate rangeQueryPointUpdate = new RangeQueryPointUpdate(arr);
        int sum = 0;
        for (int i = 0; i < range.length; i++) {
            int start = range[i][0];
            int end = range[i][1];
            sum += rangeQueryPointUpdate.sum(start, end);
        }
        return sum;
    }
    int[][] range;
    public void findPermutationRec(int[] nums, ArrayList<Integer> result, HashSet<Integer> sets) {
        if (nums.length == result.size()) {
            ArrayList<Integer> list = new ArrayList<>(result);
            System.out.println("list.toString() = " + list.toString());
            int currsum = maxsum(list, range);
            System.out.println("currsum = " + currsum);
            max = Math.max(max, currsum);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int ele = nums[i];
            if (sets.contains(ele)) continue;
            result.add(ele);
            sets.add(ele);
            findPermutationRec(nums, result, sets);
            sets.remove(ele);
            result.remove(result.size() - 1);
        }
    }


    List<String> res;
    public List<String> addOperators(String num, int target) {
        res = new ArrayList<>();
        helper(num.toCharArray(), "", 0, target, 0 );
        return res;
    }

    public boolean evaluateExp(String exp){
        Stack<Integer> stack = new Stack<>();
        Stack<Character> cStack = new Stack<>();
        char[] arr = exp.toCharArray();
        int curr = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == '*'){
                int prev = stack.pop();
                curr = curr * prev;
            }else if(arr[i] == '+' || arr[i] == '-'){
                cStack.push(arr[i]);
                stack.push(curr);
            }else{
                curr = curr * 10 + (arr[i] - '0');
            }
        }
        stack.push(curr);
        return false;
    }

    public void helper(char[] nums, String oper, int curr, int target, int start) {
        System.out.println(oper);
        if (curr == target) {
            res.add(oper);
            return;
        }
        if (start == nums.length) {
            return;
        }
        int a = nums[start] - '0';
        if (start == 0) {
            helper(nums, "" + a, a, target, start + 1);
        } else {
            helper(nums, oper + "+" + a, curr + a, target, start + 1);
            helper(nums, oper + "-" + a, curr - a, target, start + 1);
            helper(nums, oper + "*" + a, curr * a, target, start + 1);
        }
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
