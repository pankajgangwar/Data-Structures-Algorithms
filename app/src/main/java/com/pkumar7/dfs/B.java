package com.pkumar7.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 08/October/2020
 */
class B {

    public static void main(String[] args) {
        B b = new B();
        b.distributeCandies(7, 4);
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
