package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SeptemberW4 {

    public static void main(String[] args) {
        SeptemberW4 w4 = new SeptemberW4();
        int res = w4.catalanNumber(4);
        System.out.println(res);
    }

    /**
     * https://leetcode.com/problems/rotting-oranges/
     *
     * 994. Rotting Oranges
     *
     * Input: [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     *
     * **/
    public int orangesRotting(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0) return 0;

        Queue<int[]> bfs = new LinkedList<>();

        int rows = grid.length;
        int cols = grid[0].length;


        int fresh_oranges = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j] == 2){
                    bfs.offer(new int[]{i,j});
                }else if(grid[i][j] == 1){
                    fresh_oranges++;
                }
            }
        }

        System.out.println(bfs.size());

        if(fresh_oranges == 0) return 0;

        int []dir_x = new int[]{1, 0, -1, 0 };
        int []dir_y = new int[]{0, 1, 0, -1 };

        int x_mins = 0;

        while (!bfs.isEmpty()){
            x_mins++;
            int size = bfs.size();
            for (int i = 0; i < size; i++) {
                int []curr = bfs.poll();
                for (int j = 0; j < dir_x.length; j++) {
                    int next_x = dir_x[j] + curr[0];
                    int next_y = dir_y[j] + curr[1];

                    System.out.println(next_x + " , " + next_y);

                    if(isValid(next_x, next_y, grid.length, grid[0].length, grid)){
                        grid[next_x][next_y] = 2;
                        bfs.offer(new int[]{next_x,next_y});
                        fresh_oranges--;
                        System.out.println(fresh_oranges);
                    }
                }
            }
        }
        return fresh_oranges == 0 ?  x_mins : -1;
    }

    private boolean isValid(int next_x, int next_y, int rows, int cols,
                            int[][] grid) {
        if(next_x > 0 && next_x <= rows && next_y > 0 && next_y <= cols
        && grid[next_x][next_y] == 1){
            return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/
     * 124. Binary Tree Maximum Path Sum
     *
     * **/
    public int maxPathSum(TreeNode root) {
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxPathSumRec(root, max);
        return max[0];
    }

    public int maxPathSumRec(TreeNode root, int[] max){
        if (root == null) {
            return 0;
        }
        int lSum = maxPathSumRec(root.left, max);
        int rSum = maxPathSumRec(root.right, max);

        int curr = Math.max(Math.max(lSum + root.val, rSum + root.val ), root.val);
        max[0] = Math.max(max[0], Math.max( curr , rSum + lSum + root.val));
        return curr;
    }

    /** 988. Smallest String Starting From Leaf
     * https://leetcode.com/problems/smallest-string-starting-from-leaf/
     * */
    public String smallestFromLeaf(TreeNode root) {
        smallestFromLeafRec(root, new StringBuilder());
        return minString;
    }

    String minString;
    public void smallestFromLeafRec(TreeNode root, StringBuilder builder) {
        if(root == null ) return;

        char curr = (char) ('a' - root.val);

        builder.append(curr);

        if(root.left == null && root.right == null){
            builder.reverse();
            String S = builder.toString();
            builder.reverse();

            if(S.compareTo(minString) < 0) {
                minString = S;
            }
        }

        smallestFromLeafRec(root.left, builder);
        smallestFromLeafRec(root.right, builder);
        builder.deleteCharAt(builder.length() - 1);
    }


    /**
     * https://leetcode.com/contest/biweekly-contest-9/problems/minimum-knight-moves/
     *
     * Input: x = 2, y = 1
     * Output: 1
     * Explanation: [0, 0] → [2, 1]
     * */
    public int minKnightMoves(int final_x, int final_y) {

        int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };

        final_x = Math.abs(final_x);
        final_y = Math.abs(final_y);

        int max = Math.max(final_x, final_y);

        boolean [][]visited = new boolean[1000][1000];

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                visited[i][j] = false;
            }
        }

        visited[offset][offset] = true;

        return knightTourBFS(final_x, final_y, x_move, y_move, visited);
    }

    int offset = 300;

    public int knightTourBFS(int final_x, int final_y, int[] x_move, int[] y_move, boolean[][] visited){
        int min_moves = 0;
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{0,0});


        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int []curr_loc = queue.poll();
                int curr_x = curr_loc[0];
                int curr_y = curr_loc[1];

                if(curr_x == final_x && curr_y == final_y){
                    return min_moves;
                }

                for (int k = 0; k < x_move.length; k++) {
                    int next_x = x_move[k] + curr_x;
                    int next_y = y_move[k] + curr_y;

                    if (isValidKnightMove(next_x, next_y, final_x, final_y, visited)) {
                        queue.offer(new int[]{next_x, next_y});
                        visited[next_x + offset][next_y + offset] = true;
                    }
                }
            }
            min_moves++;
        }
        return -1;
    }

    public boolean isValidKnightMove(int next_x, int next_y, int final_x, int final_y, boolean[][] visited ) {
        if(next_x >= -300 && next_x <= 300 && next_y >= -300  && next_y <= 300
                && !visited[offset + next_x][offset + next_y]) {
            return true;
        }
        return false;
    }

    /** 96. Unique Binary Search Trees
     * https://leetcode.com/problems/unique-binary-search-trees/
     *
     * Input: 3
     * Output: 5
     * Explanation:
     * Given n = 3, there are a total of 5 unique BST's:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     *
     * **/
    public int numTrees(int n) {
        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 1;
        return catalanMemo(n, memo);
        //return catalanNumber(n);
    }

    public int catalanNumber(int n){
        int res = 0;
        if(n <= 1){
            return 1;
        }
        for (int i = 0; i < n; i++) {
            res += catalanNumber(i) * catalanNumber(n - i - 1);
        }
        return res;
    }

    public int catalanMemo(int n, int[] memo){
        if(n <= 1){
            return memo[n];
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + catalanNumber(i) * catalanNumber(n - i - 1);
            memo[n] = sum;
        }
        return memo[n];
    }

}
