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
    int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

    /* 1601. Maximum Number of Achievable Transfer Requests
     * https://leetcode.com/problems/maximum-number-of-achievable-transfer-requests/
     * */
    public int maximumRequests(int n, int[][] requests) {
        int[] count = new int[n];
        helper(0, 0, count, requests);
        return res;
    }
    int res = 0;
    public void helper(int index, int num, int[] count, int[][] request){
        if(index == request.length){
            for (int i = 0; i < count.length; i++) {
                if(count[i] != 0){
                    return;
                }
            }
            res = Math.max(res, num);
            return;
        }
        int from = request[index][0];
        int to = request[index][1];
        count[from]++;
        count[to]--;
        helper(index + 1, num + 1, count, request);
        count[to]++;
        count[from]--;
        helper(index + 1, num, count, request);
    }

    /* 1600. Throne Inheritance
     * https://leetcode.com/problems/throne-inheritance/
     * */
    static class Person{
        String name;
        boolean isAlive;
        public Person(String s, boolean alive){
            this.isAlive = alive;
            this.name = s;
        }
    }

    static class ThroneInheritance {
        String kingName = "";
        HashMap<Person, List<Person>> map = new HashMap<>();
        HashMap<String, Person> personHashMap = new HashMap<>();
        HashSet<Person> allP = new HashSet<>();
        public ThroneInheritance(String kingName) {
            this.kingName = kingName;
            Person p = new Person(kingName, true);
            personHashMap.put(kingName, p);
            map.putIfAbsent(p, new ArrayList<>());
        }

        public void birth(String parentName, String childName) {
            Person parent = personHashMap.get(parentName);
            Person child = new Person(childName, true);
            personHashMap.put(childName, child);

            map.putIfAbsent(parent, new ArrayList<>());
            map.putIfAbsent(child, new ArrayList<>());

            map.get(parent).add(child);

        }

        public void death(String name) {
            Person p = personHashMap.get(name);
            p.isAlive = false;
        }

        List<String> res = new ArrayList<>();
        public List<String> getInheritanceOrder() {
            res = new ArrayList<>();
            helper(kingName);
            return res;
        }

        public void helper(String name){
            Person cur = personHashMap.get(name);
            if(cur.isAlive){
                res.add(name);
            }
            for(Person p : map.get(cur)){
                helper(p.name);
            }
        }
    }

    /* 980. Unique Paths III
    * https://leetcode.com/problems/unique-paths-iii/
    * */
    public int uniquePathsIII(int[][] grid) {
        int[][] dirs = new int[][]{ {1,0}, {0,1}, {-1,0}, {0,-1} };
        int m = grid.length;
        int n = grid[0].length;
        int remPath = 0;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0){
                    remPath++;
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 1){
                    helper(grid, i, j, visited, remPath + 1, dirs, "");
                }
            }
        }
        return paths;
    }

    int paths = 0;
    public void helper(int[][] grid, int x, int y, boolean[][] visited, int remPath, int[][] dirs, String s){
        int m = grid.length;
        int n = grid[0].length;

        if(grid[x][y] == 2){
            if(remPath == 0){
                paths++;
            }
            return;
        }
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + x;
            int next_y = dirs[i][1] + y;
            if(isValid(grid, next_x, next_y, visited)){
                visited[next_x][next_y] = true;
                helper(grid, next_x, next_y, visited, remPath - 1, dirs, s + i);
                visited[next_x][next_y] = false;
            }
        }
    }

    public boolean isValid(int[][] grid, int x, int y, boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;
        if(x >= m || x < 0 || y >= n || y < 0 || grid[x][y] == -1 || grid[x][y] == 1 || visited[x][y]){
            return false;
        }
        return true;
    }

    /* 1593. Split a String Into the Max Number of Unique Substrings
     * https://leetcode.com/problems/split-a-string-into-the-max-number-of-unique-substrings/
     * */
    public int maxUniqueSplit(String s) {
        return helper(s, new HashSet<>());
    }

    public int helper(String s, HashSet<String> sets){
        int max = 0;
        for (int i = 1; i <= s.length(); i++) {
            String sub = s.substring(0, i);
            if(!sets.contains(sub)){
                sets.add(sub);
                max = Math.max(max, 1 + (helper(s.substring(i), sets)));
                sets.remove(sub);
            }
        }
        return max;
    }

    /* 132. Palindrome Partitioning II
    * https://leetcode.com/problems/palindrome-partitioning-ii/
    * */
    public int minCut(String s) {
        int n = s.length();
        boolean[][] palindrome = new boolean[n][n];
        int[] cuts = new int[n];
        char[] arr = s.toCharArray();

        int min = 0;
        for (int i = 0; i < n; i++) {
            min = i;
            for (int j = 0; j <= i; j++) {
                if(arr[j] == arr[i] && (j + 1 > i - 1 || palindrome[j + 1][i - 1]) ){
                    palindrome[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, cuts[j - 1] + 1);
                }
            }
            cuts[i] = min;
        }
        return cuts[n-1];
    }

    /*
     * 131. Palindrome Partitioning
     * https://leetcode.com/problems/palindrome-partitioning/
     */
    List<List<String>> res = new ArrayList<>();
    List<String> currList = new ArrayList<>();
    public List<List<String>> partition(String s) {
        helper(s, 0);
        return res;
    }

    public void helper(String s, int start){
        if(start >= s.length() && currList.size() > 0){
            res.add(new ArrayList<>(currList));
            return;
        }
        for(int i = start; i < s.length(); i++) {
            if(isPalindrome(s, start, i)){
                if(i == start){
                    currList.add("" + s.charAt(i));
                }else{
                    currList.add(s.substring(start, i+1));
                }
                helper(s, i + 1);
                currList.remove(currList.size()-1);
            }
        }
    }

    public boolean isPalindrome(String s, int start, int end){
        if(start == end) return true;
        while(start <= end){
            if(s.charAt(start) == s.charAt(end)){
                start++;
                end--;
            }else{
                return false;
            }
        }
        return true;
    }

    public List<List<String>> partitionI(String s) {
        int idx = 0;
        int start = 0;
        helperI(s, 0, new ArrayList<String>(), new StringBuilder());
        return res;
    }

    List<List<String>> result = new ArrayList<>();
    public void helperI(String s, int start, List<String> ans, StringBuilder builder){
        if(start == s.length()){
            res.add(new ArrayList<>(ans));
            return;
        }

        for(int i = start; i < s.length(); i++){
            ans.add(""+s.charAt(i));
            builder.append(s.charAt(i));
            helperI(s, i + 1, ans, builder);
            ans.remove(ans.size()-1);
        }
    }


    /*
     * https://leetcode.com/problems/number-of-ways-to-reorder-array-to-get-same-bst/
     * 1569. Number of Ways to Reorder Array to Get Same BST
     */
    public int numOfWays(int[] nums) {
        int n = nums.length;
        List<Integer> a = new ArrayList<>();
        for(int x : nums){
            a.add(x);
        }
        return (int)dfs(a, getPascalTriangle(n)) - 1;
    }

    public long dfs(List<Integer> nums, long[][] triangle){
        long mod = 1000_000_000 + 7;
        if(nums.size() <= 2){
            return 1;
        }
        int root = nums.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int n : nums){
            if(n < root){
                left.add(n);
            }else if( n > root){
                right.add(n);
            }
        }
        long leftRes = dfs(left, triangle) % mod;
        long rightRes = dfs(right, triangle) % mod;
        return (((triangle[left.size() + right.size()][left.size()] * leftRes) % mod ) * rightRes ) % mod;
    }

    public long[][] getPascalTriangle(int n){
        n = n + 1;
        long mod = 1000_000_000 + 7;
        //Combination of 4C2 = triangle[4][2] = 6;
        long[][] triangle = new long[n][n];
        for (int i = 0; i < n; i++) {
            triangle[i][0] = triangle[i][i] = 1;
        }
        for (int i = 2; i < n; i++) {
            for (int j = 1; j < i; j++) {
                triangle[i][j] = (triangle[i-1][j-1] + triangle[i-1][j]) % mod;
            }
        }
        return triangle;
    }


    /* 1568. Minimum Number of Days to Disconnect Island
     * https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/
     * */
    public int minDays(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int isLands = countIslands(grid);
        if(isLands > 1 || isLands == 0) {
            return 0;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == 1){
                    grid[i][j] = 0;
                    isLands = countIslands(grid);
                    grid[i][j] = 1;
                    if(isLands > 1 || isLands == 0) {
                        return 1;
                    }
                }
            }
        }
        return 2;
    }

    private int countIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int isLands = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == 1 && !visited[i][j]){
                    dfs(grid, i, j, visited);
                    isLands++;
                }
            }
        }
        return isLands;
    }

    public void dfs(int[][] grid, int curr_x, int curr_y, boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;
        if(curr_x < 0 || curr_x >= m || curr_y < 0 || curr_y >= n
                || visited[curr_x][curr_y] || grid[curr_x][curr_y] == 0){
            return;
        }
        visited[curr_x][curr_y] = true;
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + curr_x;
            int next_y = dirs[i][1] + curr_y;
            dfs(grid, next_x, next_y, visited);
        }
    }

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
