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
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MayW4 {

    // TODO: 5/15/2020  
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /* 
       https://leetcode.com/problems/sum-of-distances-in-tree/ 
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */

    public static void main(String[] args) {
        MayW4 test = new MayW4();
        int[][] grid = new int[][]{
                {0,-3}
        };
        
        //boolean res = test.isPalindrome("");
        int res = 0;
        System.out.println("res = " + res);
    }

    /* 
    LC : 1035
    https://leetcode.com/problems/uncrossed-lines/
    */
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length][B.length];
        for(int i = 0; i < A.length; i++){
            Arrays.fill(dp[i], -1);
        }
        return helper(dp, A, B, 0, 0 );
    }

    public int helper(int[][] dp, int[] a, int [] b, int x, int y){
        if(x == a.length || y == b.length){
            return 0;
        }
        if(dp[x][y] != -1){
            return dp[x][y];
        }
        int max = 0;
        if(a[x] == b[y]){
            max = Math.max(helper(dp, a, b, x + 1, y + 1) + 1, max);
        }else{
            max = Math.max(helper(dp, a, b, x + 1, y), max);// Skip x
            max = Math.max(helper(dp, a, b, x, y + 1), max);// Skip y
        }

        dp[x][y] = max;
        return dp[x][y];
    }

    /* 
       LC : 886
       https://leetcode.com/problems/possible-bipartition/
       https://leetcode.com/problems/possible-bipartition/discuss/650930/JAVA-Union-find
       Graph Coloring, Union-find
    */
    public boolean possibleBipartitionI(int n, int[][] dislikes) {
        int[] colors = new int[n];
        LinkedList<Integer>[] graph = new LinkedList[n];

        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++ ){
            if(colors[i] != 0) continue;

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);

            colors[i] = 1;

            while(!queue.isEmpty()){
                int curr = queue.poll();

                for(int adj : graph[curr]){
                    if(colors[adj] == 0){
                        colors[adj] = -colors[curr];
                        queue.offer(adj);
                    }else if(colors[adj] != -colors[curr]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] root = new int[n];
        for (int i = 0; i < root.length; i++) {
            root[i] = i;
        }
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<Integer>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++){
            LinkedList<Integer> adjList = graph[i];
            for(int adj : adjList) {
                int xRoot = find(i, root);
                int yRoot = find(adj, root);
                if(xRoot != yRoot){
                    root[find(adjList.get(0), root)] = yRoot;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public int find(int x, int[] root){
        while(x != root[x]){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }


}