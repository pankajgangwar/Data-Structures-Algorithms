package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class JulyW3 {
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
        JulyW3 curr = new JulyW3();
        int[][] edges = new int[][]{{0,1}, {1,2}, {0,3}};
        curr.countSubTrees(4,  edges, "bbbb");
    }

    /*
      LC : 1520. Maximum Number of Non-Overlapping Substrings
      https://leetcode.com/problems/maximum-number-of-non-overlapping-substrings/ */
    public List<String> maxNumOfSubstrings(String s) {
        return new ArrayList<>();
    }

    /*
    LC : 1518. Water Bottles
    https://leetcode.com/problems/water-bottles/ */
    public int numWaterBottles(int numBottles, int numExchange) {
        int ans = numBottles;
        while (numBottles >= numExchange){
            int remainder = numBottles % numExchange;
            numBottles /= numExchange;
            ans += numBottles;
            numBottles += remainder;
        }
        return ans;
    }

    /*
    LC : 1519 Number of Nodes in the Sub-Tree With the Same Label
    https://leetcode.com/problems/number-of-nodes-in-the-sub-tree-with-the-same-label/ */
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        LinkedList<Integer>[] tree = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new LinkedList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dst = edges[i][1];
            tree[src].add(dst);
            tree[dst].add(src);
        }
        int[] res = new int[n];
        boolean[] visited = new boolean[n];
        dfs(tree, 0, visited, labels, res);
        //System.out.println("Arrays.toString(res) = " + Arrays.toString(res));
        return res;
    }

    public int[] dfs(LinkedList<Integer>[] tree, int src,
                     boolean[] visited, String lables, int[] res){
        int []cnt = new int[26];
        if(visited[src]) return cnt;
        visited[src] = true;
        char color = lables.charAt(src);
        for (int i = 0; i < tree[src].size(); i++) {
            int adj = tree[src].get(i);
            int[] sub = dfs(tree, adj, visited, lables, res);
            for (int j = 0; j < sub.length; j++) {
                cnt[j] += sub[j];
            }
        }
        cnt[color - 'a']++;
        res[src] = cnt[color - 'a'];
        return cnt;
    }

}
