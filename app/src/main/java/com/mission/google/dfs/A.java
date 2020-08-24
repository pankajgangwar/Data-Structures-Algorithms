package com.mission.google.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 15/August/2020
 */
class A {
    /* 38. Count and Say
    * https://leetcode.com/problems/count-and-say/
    * */
    public String countAndSay(int n) {
        if(n == 1) return "1";

        String prev = countAndSay(n - 1);
        StringBuilder out = new StringBuilder();
        int len = prev.length();
        int i = 0;
        while(i < len){
            int a = 0;
            char ch = prev.charAt(i);
            while(i < len && prev.charAt(i) == ch){
                i++;
                a++;
            }
            out.append(String.valueOf(a));
            out.append(ch);
        }
        return out.toString();
    }

    /* 1559. Detect Cycles in 2D Grid
    * https://leetcode.com/problems/detect-cycles-in-2d-grid/
    * */
    public boolean containsCycle(char[][] grid) {
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(visited[i][j]) continue;
                if(helper(grid, dirs, i, j,-1, -1, grid[i][j], visited)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean helper(char[][] grid, int[][] dirs, int curr_x, int curr_y,
                          int prev_x, int prev_y, char color, boolean[][] visited){

        visited[curr_x][curr_y] = true;
        for (int[] dir : dirs) {
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;
            if(isValid(next_x, next_y, grid, color)){
                if (!(prev_x == next_x && prev_y == next_y)) {
                    if (visited[next_x][next_y]) {
                        return true;
                    } else {
                        if(helper(grid, dirs, next_x, next_y, curr_x, curr_y, color, visited)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValid(int x, int y, char[][] grid, char search){
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[x].length && search == grid[x][y]){
            return true;
        }
        return false;
    }

    /* 582. Kill Process
    * https://leetcode.com/problems/kill-process/
    * */
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < pid.size(); i++){
            int parent = ppid.get(i);
            int child = pid.get(i);
            graph.putIfAbsent(parent, new ArrayList<>());
            graph.get(parent).add(child);
        }
        List<Integer> killed = new ArrayList<>();
        dfs(graph, kill, killed);
        return killed;
    }

    public void dfs(HashMap<Integer, List<Integer>> graph, int src, List<Integer> killed) {
        killed.add(src);
        if(!graph.containsKey(src)) return;
        for(int pid : graph.get(src)){
            dfs(graph, pid, killed);
        }
    }

    /*
    * LC : 473. Matchsticks to Square
    * https://leetcode.com/problems/matchsticks-to-square/
    * Similar: https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    * */
    public boolean makesquare(int[] nums) {
        return makesquare1(nums);
        //return canPartitionKSubsets1(nums, 4);
    }

    public boolean makesquare1(int[] nums){
        int n = nums.length;
        if(n == 0) return false;
        int total = Arrays.stream(nums).sum();
        if(total % 4 != 0) return false;
        int side = total / 4;
        Arrays.sort(nums);
        reverse(nums);
        if(nums[0] > side) return false;
        int[] bucket = new int[4];

        return helperMakeSquare(nums, 0, side, bucket);
    }

    public boolean helperMakeSquare(int[] nums, int idx, int target, int[] bucket){
        if(idx == nums.length){
            if(bucket[0] == target && bucket[1] == target && bucket[2] == target){
                return true;
            }
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if(bucket[i] + nums[idx] > target) continue;
            bucket[i] += nums[idx];
            if(helperMakeSquare(nums, idx + 1, target, bucket)) return true;
            bucket[i] -= nums[idx];
        }
        return false;
    }

    private void reverse(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i < j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    /*
    LC : 698
    https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    */
    public boolean canPartitionKSubsets(int[] nums, int k){
        int n = nums.length;
        if(n == 0) return false;
        int total = Arrays.stream(nums).sum();
        if(total % k != 0) return false;
        int side = total / k;
        Arrays.sort(nums);
        reverse(nums);
        if(nums[0] > side) return false;
        int[] bucket = new int[k];

        return helper(nums, 0, side, bucket);
    }

    public boolean helper(int[] nums, int idx, int target, int[] bucket){
        if(idx == nums.length){
            for(int x : bucket){
                if(x != target) return false;
            }
            return true;
        }
        for (int i = 0; i < bucket.length; i++) {
            if(bucket[i] + nums[idx] > target) continue;
            bucket[i] += nums[idx];
            if(helper(nums, idx + 1, target, bucket)) return true;
            bucket[i] -= nums[idx];
        }
        return false;
    }

    public boolean canPartitionKSubsets1(int[] nums, int k) {
        int n = nums.length;
        if(n == 0) return false;
        int targetSum = 0;
        int maxNum = 0;
        for(int x : nums){
            targetSum += x;
            maxNum = Math.max(maxNum, x);
        }
        if(targetSum % k != 0 || maxNum > targetSum / k){
            return false;
        }

        return helper(nums, k, new boolean[nums.length], 0, targetSum / k, 0);
    }

    public boolean helper(int[] nums, int k, boolean[] visited, int curSum, int targetSum, int nextIndex){
        if(k == 0) return true;
        if(curSum == targetSum){
            return helper(nums, k - 1, visited, 0, targetSum, 0 );
        }

        for(int i = nextIndex; i < nums.length; i++){
            if (!visited[i] && curSum + nums[i] <= targetSum) {
                visited[i] = true;
                if (helper(nums, k, visited, curSum + nums[i], targetSum, i + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }


}
