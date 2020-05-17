package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

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
import java.util.Set;
import java.util.Stack;

import sun.reflect.generics.tree.Tree;

public class MayW3 {
    // TODO: 5/15/2020  
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/increasing-subsequences/ */
    /* https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/ */
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/

    /* DP on trees */
    /* 
       https://leetcode.com/problems/sum-of-distances-in-tree/ 
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */

    /* https://leetcode.com/problems/brick-wall/ */
    public int leastBricks(List<List<Integer>> wall) {
        return 0;
    }

    public static void main(String[] args) {
        MayW3 test = new MayW3();
        test.simplifiedFractions(4);
    }

    /*
     LC : 1452
     https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/ */
    public List<Integer> peopleIndexes(List<List<String>> fcs) {
        int n = fcs.size();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int a = find(i, parent);
                int b = find(j, parent);
                if(parent[a] == parent[b])continue;
                else if(contains(fcs.get(a), fcs.get(b))){
                    parent[b] = a;
                }else if(contains(fcs.get(b), fcs.get(a))){
                    parent[a] = b;
                }
            }
        }
        Set<Integer> res = new HashSet<>();
        for (int i : parent) {
            res.add(find(i, parent));
        }
        List<Integer> ans = new ArrayList<>(res);
        Collections.sort(ans);
        return ans;
    }

    public boolean contains(List<String> a, List<String> b){
        if(a.size() <= b.size()) return false;
        return a.containsAll(b);
    }

    public int find(int x, int[] parent){
        while (x != parent[x]){
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    /*
    LC : 1451
    https://leetcode.com/problems/rearrange-words-in-a-sentence/ */
    public String arrangeWords(String text) {
        if(text.length() == 0) return text;
        String[] arr = text.split(" ");

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if(a.length() == b.length()){
                    return 0;
                }else{
                    return a.length() - b.length();
                }
            }
        });

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i].toLowerCase());
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length()-1);
        char ch = Character.toUpperCase(builder.charAt(0));
        builder.setCharAt(0, ch);
        return builder.toString();
    }

    /* LC : 1450
       https://leetcode.com/problems/number-of-students-doing-homework-at-a-given-time/
     */
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int count = 0;
        for (int i = 0; i < startTime.length; i++) {
            int start = startTime[i];
            int end = endTime[i];
            if(start <= queryTime && queryTime <= end ){
                count++;
            }
        }
        return count;
    }

    /*
     LC : 1449
     https://leetcode.com/problems/form-largest-integer-with-digits-that-add-up-to-target/ */
    public String largestNumber(int[] cost, int target) {
        int[] dp = new int[target + 1];
        for (int t = 1; t <= target; t++) {
            dp[t] = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if(t >= cost[i]){
                    dp[t] = Math.max(dp[t] , 1 + dp[t- cost[i]]);
                }
            }
        }
        if(dp[target] < 0) return "0";
        StringBuilder res = new StringBuilder();
        for (int i = 8; i >= 0; --i) {
            while (target >= cost[i] && dp[target] == dp[target - cost[i]] + 1){
                res.append(1 + i);
                target -= cost[i];
            }
        }
        return res.toString();
    }

    /*  LC : 1447
        https://leetcode.com/problems/simplified-fractions/
    */
    public List<String> simplifiedFractions(int n) {
        List<String> ans = new ArrayList<>();
        HashMap<String, Boolean> seen = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n ; j++) {
                if(i / j == 1){
                    continue;
                }
                int factor = gcd(i, j);
                int first = i / factor;
                int second = j / factor;
                if(!seen.containsKey(first + "/"+second)){
                    ans.add(i+"/"+j);
                    seen.put(first + "/ "+second, true);
                }
            }
        }
        return ans;
    }

    public int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b%a, a);
    }

    /*
       LC : 1448
       https://leetcode.com/problems/count-good-nodes-in-binary-tree/
    */
    public int goodNodes(TreeNode root) {
        return helper(root, null);
    }

    public int helper(TreeNode root, TreeNode parent){
        if(root == null) return 0;
        int leftGood = helper(root.left, root);
        int rightGood = helper(root.right, root);

        int count = 0;
        if(parent != null){
            if(root.val >= parent.val){
                count++;
            }
        }
        return count + leftGood + rightGood;
    }

    /*
       LC: 1446
       https://leetcode.com/problems/consecutive-characters/
    */
    public int maxPower(String s) {
        int res = 0;
        int i = 0;
        int len = s.length();
        while(i < s.length()){
            int count = 1;
            while(i+1 < len && s.charAt(i) == s.charAt(i+1)){
                i++;
                count++;
            }
            i++;
            res = Math.max(res, count);
        }
        return res;
    }


    /* https://leetcode.com/problems/odd-even-linked-list/ */
    public ListNode oddEvenList(ListNode head) {
        ListNode odd = head;
        if(head == null || head.next == null) {
            return head;
        }
        ListNode even = odd.next;
        ListNode evenStart = even;
        while(even != null && even.next != null){
            ListNode newEven = even.next.next;
            
            ListNode newOdd = even.next;
            
            odd.next = newOdd;
            newOdd.next = evenStart;
            odd = newOdd;
            
            even.next = newEven;
            even = newEven;
        }
        return head;
    }

    /*
        LC : 918
        https://leetcode.com/problems/maximum-sum-circular-subarray/ 
    */
    public int maxSubarraySumCircular(int[] arr) {
        int global_max = Integer.MIN_VALUE;
        int curr_max = 0;
        int global_min = Integer.MAX_VALUE;
        int curr_min = 0;
        int totalsum = 0;
        int n = arr.length;
        for(int a : arr){
            curr_max = Math.max(curr_max + a, a);
            global_max = Math.max(global_max, curr_max);
            
            curr_min = Math.min(curr_min + a, a);
            global_min = Math.min(curr_min, global_min);
            
            totalsum += a;
        }
        return (global_max > 0) ? Math.max(global_max, totalsum - global_min) : global_max;
    }

    /* https://leetcode.com/problems/couples-holding-hands/
    *  LC : 765
    *  Revisit
    *  */
    public int minSwapsCouples(int[] row) {
        int n = row.length / 2;
        UnionFind unionfind = new UnionFind(n);
        for(int i = 0; i < n; i++){
            int a = row[2 * i];
            int b = row[2 * i  +1];

            unionfind.union(a / 2, b / 2);
        }
        return unionfind.count() - n;
    }

    class UnionFind {
        private int count = 0;
        private int[] parent, rank;
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }
        public int count() {
            return count;
        }
    }

}