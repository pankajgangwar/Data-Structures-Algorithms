package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class MarchW1 {
    public static void main(String[] args) {
        MarchW1 w1 = new MarchW1();
        int[][] grid = new int[][]{
                {1,1,1,1},
                {2,2,2,2},
                {1,1,1,1},
                {2,2,2,2}
        };
        int minCost = w1.minCost(grid);
        System.out.println("minCost = " + minCost);
    }

    /* https://leetcode.com/problems/rank-teams-by-votes/ */
    public String rankTeams(String[] votes) {
        if (votes.length == 1) return votes[0];

        /*
           Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
           Output: "ACB"
        */
       HashMap<Character, int[]> map = new HashMap<>();
       int l = votes[0].length();

        for (int i = 0; i < votes.length; i++) {
            String curr = votes[i];
            for (int j = 0; j < curr.length(); j++) {
                char ch = curr.charAt(j);
                map.putIfAbsent(ch, new int[l]);
                map.get(ch)[j]++;
            }
        }

        List<Character> list = new ArrayList<>(map.keySet());
        Collections.sort(list, (a,b) -> {
            for (int i = 0; i < l; i++) {
                if(map.get(a)[i] != map.get(b)[i]){
                    return map.get(b)[i] - map.get(a)[i];
                }
            }
            return a - b;
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : list) {
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    /* https://leetcode.com/problems/linked-list-in-binary-tree/ */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if(head == null) return true;
        if(root == null) return false;
        if(root.val == head.val){
            if(helper(head.next, root.left) || helper(head.next, root.right)){
                return true;
            }
        }
        return isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    public boolean helper(ListNode head, TreeNode root) {
        if(head == null) return true;
        if(root == null) return false;

        if(root.val != head.val){
            return false;
        }else{
            return helper(head.next, root.left) || helper(head.next, root.right);
        }
    }

    /* https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/ */
    public int[] smallerNumbersThanCurrent(int[] arr) {
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sortedArr);
        HashMap<Integer, Integer> rank = new HashMap<>();
        HashMap<Integer, Integer> freq = new HashMap<>();

        List<Integer> duplicate = new ArrayList<>();
        int[] res = new int[arr.length];
        Set<Integer> sets = new HashSet<>();
        for (int i = 0; i < sortedArr.length; i++) {

            freq.put(sortedArr[i], freq.getOrDefault(sortedArr[i], 0) + 1);
            if(i > 0) {
                rank.putIfAbsent(sortedArr[i], rank.get(sortedArr[i - 1]) + freq.get(sortedArr[i - 1]));
            }else{
                rank.putIfAbsent(sortedArr[i], 0);
            }
        }
        for (int i = 0; i < arr.length; ++i) arr[i] = rank.get(arr[i]);
        return arr;
    }

    /* https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/ */
    int INF = (int) 1e9;
    int[][] DIR = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length, cost = 0;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(dp[i], INF);
        Queue<int[]> bfs = new LinkedList<>();
        dfs(grid, 0, 0, dp, cost, bfs);
        while (!bfs.isEmpty()) {
            cost++;
            for (int size = bfs.size(); size > 0; size--) {
                int[] top = bfs.poll();
                int r = top[0], c = top[1];
                for (int i = 0; i < 4; i++) dfs(grid, r + DIR[i][0], c + DIR[i][1], dp, cost, bfs);
            }
        }
        return dp[m - 1][n - 1];
    }

    void dfs(int[][] grid, int r, int c, int[][] dp, int cost, Queue<int[]> bfs) {
        int m = grid.length; int n = grid[0].length;
        if (r < 0 || r >= m || c < 0 || c >= n || dp[r][c] != INF) return;
        dp[r][c] = cost;
        bfs.offer(new int[]{r, c}); // add to try change direction later
        int nextDir = grid[r][c] - 1;
        dfs(grid, r + DIR[nextDir][0], c + DIR[nextDir][1], dp, cost, bfs);
    }
}
