package com.pkumar7.dfs;

import com.pkumar7.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pankaj Kumar on 08/October/2020
 */
class B {

    public static void main(String[] args) {
        B b = new B();
        b.distributeCandies(7, 4);
    }

    /* 1947. Maximum Compatibility Score Sum
     * https://leetcode.com/problems/maximum-compatibility-score-sum/
     * */
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int[] m = new int[mentors.length];
        for (int i = 0; i < mentors.length; i++) {
            m[i] = i;
        }
        int score = 0;
        do{
            int curr = 0;
            for (int i = 0; i < m.length; i++) {
                curr += genCompat(students[i], mentors[m[i]]);
            }
            score = Math.max(score, curr);
        }while (Utils.nextPermutation(m));

        return score;
    }

    public int genCompat(int[] a, int b[]){
        int score = 0;
        for (int i = 0; i < a.length; i++) {
            if(a[i] == b[i]) score++;
        }
        return score;
    }

    public int maxCompatibilitySumDfs(int[][] students, int[][] mentors) {
        int n = students.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        boolean[] visited = new boolean[n];
        return dfs(students, mentors, 0, dp, visited);
    }

    public int dfs(int[][] s, int[][] m, int sIdx, int[] dp, boolean[] visited){
        if(sIdx == s.length) return 0;
        int score = 0;
        for (int i = 0; i < m.length; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            score = Math.max(score, genCompat(s[sIdx], m[i]) + dfs(s, m, sIdx + 1, dp, visited));
            visited[i] = false;
        }
        return score;
    }

    /*
     * 827. Making A Large Island
     * https://leetcode.com/problems/making-a-large-island/
     * */
    public int largestIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int max_area = 1;
        int color = 2;
        map.put(0, 0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 1){
                    int area = paint(i, j, grid, color);
                    map.put(color, area);
                    color++;
                }
            }
        }
        max_area = map.getOrDefault(2, 0);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0){
                    Set<Integer> neig = new HashSet<>();
                    if(i > 0 && grid[i - 1][j] > 1) neig.add(grid[i - 1][j]);
                    if(j > 0 && grid[i][j - 1] > 1) neig.add(grid[i][j - 1]);
                    if(i < row - 1 && grid[i + 1][j] > 1) neig.add(grid[i + 1][j]);
                    if(j < col - 1 && grid[i][j + 1] > 1) neig.add(grid[i][j + 1]);

                    int size = 1;
                    for(int island : neig) size += map.get(island);
                    max_area = Math.max(max_area, size);
                }
            }
        }

        return max_area;
    }

    public int paint(int curr_x, int curr_y, int[][]grid, int color) {
        int row = grid.length;
        int col = grid[0].length;
        grid[curr_x][curr_y] = color;
        int[][] dirs = new int[][]{{1,0},{0,1},{0,-1},{-1,0}};
        int area = 0;
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + curr_x;
            int next_y = dirs[i][1] + curr_y;
            if(next_x < 0 || next_x >= row || next_y < 0 || next_y >= col
                    || grid[next_x][next_y] == 0 || grid[next_x][next_y] == color) continue;

            area += paint(next_x, next_y, grid, color);
        }
        return area + 1;
    }

    /* 1905. Count Sub Islands
     * https://leetcode.com/problems/count-sub-islands/
     * */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int res = 0;
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[i].length; j++) {
                if(grid2[i][j] == 1){
                    if(isSubIsland(grid1, grid2, i, j)){
                        ++res;
                    }
                }
            }
        }
        return res;
    }

    private boolean isSubIsland(int[][] grid1, int[][] grid2,
                                int curr_x, int curr_y) {
        if(!isSafe(curr_x, curr_y, grid2)) return true;
        grid2[curr_x][curr_y] = 0;
        boolean status = true;
        status &= isSubIsland(grid1, grid2, curr_x - 1 , curr_y);
        status &= isSubIsland(grid1, grid2, curr_x  , curr_y - 1);
        status &= isSubIsland(grid1, grid2, curr_x + 1 , curr_y);
        status &= isSubIsland(grid1, grid2, curr_x  , curr_y + 1);
        return (status && grid1[curr_x][curr_y] == 1);
    }

    boolean isSafe(int i, int j, int[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        if(i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0 ){
            return false;
        }
        return true;
    }

    /* 1743. Restore the Array From Adjacent Pairs
     * https://leetcode.com/problems/restore-the-array-from-adjacent-pairs/
     * */
    public int[] restoreArray(int[][] adjacentPairs) {
        int n = adjacentPairs.length + 1;
        int[] res = new int[n];
        HashMap<Integer,List<Integer>> graph = new HashMap<>();
        for (int[] adj : adjacentPairs) {
            int a = adj[0];
            int b = adj[1];
            graph.putIfAbsent(a, new ArrayList<>());
            graph.putIfAbsent(b, new ArrayList<>());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        int root = 0;
        for(Map.Entry<Integer, List<Integer>> e : graph.entrySet()){
            if(e.getValue().size() == 1){
                root = e.getKey();
                break;
            }
        }
        HashSet<Integer> visited = new HashSet<>();
        List<Integer> ans = new ArrayList<>();
        dfs(root, visited,ans, graph);
        for (int i = 0; i < ans.size(); i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    public void dfs(int src, HashSet<Integer> visited, List<Integer> ans, HashMap<Integer, List<Integer>> graph){
        if(visited.contains(src)) return;
        ans.add(src);
        visited.add(src);
        for(int child : graph.get(src)){
            dfs(child, visited, ans, graph);
        }
    }

    /* 1706. Where Will the Ball Fall
     * https://leetcode.com/problems/where-will-the-ball-fall/
     * https://leetcode.com/problems/regions-cut-by-slashes/
     * Upscaling the grid
     * */
    public int[] findBall(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] upscaled = new int[rows*3][cols*3];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 1){
                    upscaled[i*3][j*3] = upscaled[i*3 + 1][j*3 + 1] = upscaled[i*3 + 2][j*3 + 2] = 1;
                }else{
                    upscaled[i*3][j*3 + 2] = upscaled[i*3 + 1][j*3 + 1] = upscaled[i*3 + 2][j*3] = 1;
                }
            }
        }

        int[] res = new int[cols];
        for (int i = 0; i < cols; i++) {
            boolean[][] visited = new boolean[rows*3][cols*3];
            int status = dfs(upscaled, 0, i*3 + 1, visited);
            res[i] = status;
        }
        return res;
    }

    public int dfs(int[][] grid, int row, int col, boolean[][] visited){
        int[][] dirs = new int[][]{{1,0},{0,1},{0,-1}};
        if(visited[row][col]) return -1;
        visited[row][col] = true;
        int m = grid.length;
        int n = grid[0].length;
        if(row == m - 1) return col / 3;
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + row;
            int next_y = dirs[i][1] + col;
            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && grid[next_x][next_y] == 0){
                int res = dfs(grid, next_x, next_y, visited);
                if(res != -1){
                    return res;
                }
            }
        }
        return -1;
    }

    /* 293. Flip Game
     * https://leetcode.com/problems/flip-game/
     * */
    public List<String> generatePossibleNextMoves(String s) {
        List<String> res = new ArrayList<>();
        StringBuilder out = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            if(i + 1 < s.length() && s.charAt(i) == '+' && s.charAt(i + 1) == '+'){
                out.setCharAt(i, '-');
                out.setCharAt(i + 1, '-');
                res.add(out.toString());
                out.setCharAt(i, '+');
                out.setCharAt(i + 1, '+');
            }
        }
        return res;
    }


    /* 294. Flip Game II
     * https://leetcode.com/problems/flip-game-ii/
     * */
    public boolean canWin(String s) {
        boolean status = false;
        StringBuilder out = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            if(i + 1 < s.length() && s.charAt(i) == '+' && s.charAt(i + 1) == '+'){
                out.setCharAt(i, '-');
                out.setCharAt(i + 1, '-');
                if(!canWin(out.toString())){
                    return true;
                }
                out.setCharAt(i, '+');
                out.setCharAt(i + 1, '+');
            }
        }
        return status;
    }

    /* 1103. Distribute Candies to People
    * https://leetcode.com/problems/distribute-candies-to-people/
    * */
    public int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        int count = 1;
        for (int i = 0; candies > 0; i++) {
            if(count >= candies){
                count = candies;
            }
            res[i % num_people] += count;
            candies -= count;
            count++;
        }
        return res;
    }

    /* 690. Employee Importance
    * https://leetcode.com/problems/employee-importance/
    * */
    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    };
    public int getImportance(List<Employee> employees, int id) {
        int res = 0;
        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).id == id){
                res += employees.get(i).importance;
                List<Integer> children = employees.get(i).subordinates;
                for (int j = 0; j < children.size(); j++) {
                    res += getImportance(employees, children.get(j));
                }
            }
        }
        return res;
    }

    /*
     *   1625. Lexicographically Smallest String After Applying Operations
     *   https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/
     *   https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/discuss/899507/JAVA-Brute-force-%2B-DFS
     * */
    public String findLexSmallestString(String s, int a, int b) {
        res = s;
        HashSet<String> visited = new HashSet<>();
        helper(s, a, b, visited);
        return res;
    }

    String res ;
    public void helper(String s, int a, int b, HashSet<String> visited){
        if(visited.contains(s)) return;
        visited.add(s);
        if(s.compareTo(res) < 0){
            res = s;
        }
        int splitPoint = s.length() - b;
        String rotated = s.substring(splitPoint) + s.substring(0, splitPoint);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if(i % 2 != 0){
                int d = s.charAt(i) - '0';
                d = d + a;
                if(d > 9){
                    d = d % 10;
                }
                out.append(d);
            }else{
                out.append(s.charAt(i));
            }
        }
        String added = out.toString();
        helper(added, a, b, visited);
        helper(rotated, a, b, visited);
    }
    
    /* 526. Beautiful Arrangement
    * https://leetcode.com/problems/beautiful-arrangement/
    * */
    public int countArrangement(int n) {
        int[] nums = new int[n];
        
        for(int i = 1; i <= n; i++){
            nums[i-1] = i;
        }
        permute(nums, 0);
        return count;
    }
    int count = 0;
    
    public void permute(int[] nums, int l){
        int n = nums.length;
        if(l == n ){
            count++;
            return;
        }
        
        for(int i = l; i < n; i++){
            swap(nums, i, l);
            if(nums[l] % ( l + 1 ) == 0 || (l + 1) % nums[l] == 0){
                permute(nums, l + 1);
            }
            swap(nums, i, l);
        }
    }
    
    public void swap(int[] nums, int i, int l){
        int a = nums[i];
        nums[i] = nums[l];
        nums[l] = a;
    }

    /* 87. Scramble String
     * https://leetcode.com/problems/scramble-string/
     * */
    public boolean isScramble(String s1, String s2) {
        if(s1.length() != s2.length()) return false;
        if(s1.equals(s2)){
            return true;
        }
        int [] arr1 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            arr1[s1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s2.length(); i++) {
            if(--arr1[s2.charAt(i) - 'a'] < 0) return false;
        }
        int len = s1.length();
        for (int i = 1; i <= len - 1; i++) {
            if(isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))){
                return true;
            }
            if(isScramble(s1.substring(0, i), s2.substring(len - i)) && isScramble(s1.substring(i), s2.substring(0, len - i))){
                return true;
            }
        }
        return false;
    }
}
