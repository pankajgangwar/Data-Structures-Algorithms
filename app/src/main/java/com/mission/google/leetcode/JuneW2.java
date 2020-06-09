package com.mission.google.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

class JuneW2 {
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

    public static void main(String[] args) {
        JuneW2 obj = new JuneW2();
        int[][] pairs = new int[][]{
                {1,2},
                {2,3},
                {3,4}
        };
        //obj.findLongestChain(pairs);
        obj.increasingTriplet(new int[]{5,4,1,2,0,3});
    }
    /* https://leetcode.com/problems/number-of-matching-subsequences/ */
    /* https://leetcode.com/problems/shortest-way-to-form-string/ */

    /* https://leetcode.com/problems/analyze-user-website-visit-pattern/
     *
     * username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"]
     * timestamp = [1,2,3,4,5,6,7,8,9,10]
     * website = ["home","about","career","home","cart","maps","home","home","about","career"]
     * */
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        HashMap<String, HashSet<String>> websiteMap = new HashMap<>();
        HashMap<String, HashSet<String>> userMap = new HashMap<>();

        int n = username.length;
        for (int i = 0; i < n; i++) {
            String user = username[i];
            String url = website[i];
            websiteMap.putIfAbsent(url, new HashSet<>());
            websiteMap.get(url).add(user);
        }

        List<String> res = new ArrayList<>();
        //PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> );
        TreeMap<Integer, List<String>> map = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<String, HashSet<String>> entry : websiteMap.entrySet()){
            int size = entry.getValue().size();
            map.putIfAbsent(size, new ArrayList<>());
            map.get(size).add(entry.getKey());
        }

        while (res.size() < 3){
            for(Map.Entry<Integer,List<String>> entry : map.entrySet()){
                List<String> curr = entry.getValue();
                //Collections.sort(curr, (a,b) -> timestamp);
                if(curr.size() <= 3){
                    res.addAll(curr);
                }else{

                }
            }
        }
        return res;
    }

    /*
    LC : 354
    https://leetcode.com/problems/russian-doll-envelopes
    Longest Increasing subsequence(LIS)
    */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a,b) -> a[0] - b[0]);
        int n = envelopes.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < envelopes.length; i++) {
            for (int j = 0; j < i; j++) {
                if(envelopes[i][0] == envelopes[j][0] || envelopes[i][1] == envelopes[j][1])
                    continue;
                if(envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if(max < dp[i]){
                max = dp[i];
            }
        }
        return max;
    }

    /*
    LC : 334
    https://leetcode.com/problems/increasing-triplet-subsequence/
    */
    public boolean increasingTriplet(int[] nums) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        for(int n : nums){
            if(a >= n){
                a = n;
            }else if(b >= n){
                b = n;
            }else{
                return true;
            }
        }
        return false;
    }

    /*
    LC : 646
    https://leetcode.com/problems/maximum-length-of-pair-chain/
    Longest Increasing subsequence(LIS)
    */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a,b) -> a[0] - b[0]);
        return findLongestChainDP(pairs);
    }

    public int findLongestChainDP(int[][] nums) {
        int LIS[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            LIS[i] = 1;
        }
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[j][1] < nums[i][0]){
                    LIS[i] = Math.max(LIS[i], LIS[j]+1);
                }
            }
        }
        int max = 0;
        for(int i = 0; i < LIS.length; i++){
            if(max < LIS[i]){
                max = LIS[i];
            }
        }
        return max;
    }

    /*
    LC : 740
    https://leetcode.com/problems/delete-and-earn/
    */
    public int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        Set<Integer> sets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            sets.add(nums[i]);
        }
        int[] temp = sets.stream().mapToInt(i -> i).toArray();
        int[] memo = new int[temp.length];
        Arrays.fill(memo, -1);
        return helperMaxMemo(temp, temp.length - 1, map, memo);
    }

    public int helperMaxMemo(int[] nums, int n, HashMap<Integer, Integer> map, int[] memo) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        if(memo[n] != -1){
            return memo[n];
        }
        int curr = nums[n];
        int prev = nums[n - 1];
        int max = 0;
        if (curr == prev + 1) {
            max = Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMaxMemo(nums, n - 2, map, memo), helperMaxMemo(nums, n - 1, map, memo));
        } else {
            max = (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMaxMemo(nums, n - 1, map, memo), helperMaxMemo(nums, n - 2, map, memo));
        }
        return memo[n] = max;
    }

    public int helperMax(int[] nums, int n, HashMap<Integer, Integer> map) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        int curr = nums[n];
        int prev = nums[n - 1];

        if (curr == prev + 1) {
            return Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMax(nums, n - 2, map), helperMax(nums, n - 1, map));
        } else {
            return (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMax(nums, n - 1, map), helperMax(nums, n - 2, map));
        }
    }

    /*
     LC : 361
     https://leetcode.com/problems/bomb-enemy/
     */
    class Location{
        int top;
        int bottom;
        int left;
        int right;
    }

    public int maxKilledEnemies(char[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        Location[][] matrix = new Location[m][n];

        for(int i = 0; i < m; i++ ){
            for(int j = 0; j < n; j++){
                matrix[i][j] = new Location();
                if(grid[i][j] == 'W') continue;
                matrix[i][j].top = (i == 0 ? 0 : matrix[i-1][j].top) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].left = (j == 0 ? 0 : matrix[i][j-1].left) + (grid[i][j] == 'E' ? 1 : 0);
            }
        }

        int maxCount = 0;
        for(int i = m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(grid[i][j] == 'W') continue;
                matrix[i][j].bottom = (i == m -1 ? 0 : matrix[i+1][j].bottom) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].right = (j == n -1 ? 0 : matrix[i][j+1].right) + (grid[i][j] == 'E' ? 1 : 0);   

                if(grid[i][j] == '0'){
                    maxCount = Math.max(maxCount, matrix[i][j].top + matrix[i][j].bottom + matrix[i][j].left + matrix[i][j].right);
                }
            }
        }
        return maxCount;
    }

    /*
    LC : 311
    https://leetcode.com/problems/sparse-matrix-multiplication/
    */
    public int[][] multiply(int[][] A, int[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        int[][] res = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                if(A[i][j] != 0) {
                    for (int k = 0; k < colsB; k++) {
                        if (B[j][k] != 0) res[i][k] += A[i][j] * B[j][k];
                    }
                }
            }
        }
        return res;
    }

    /*
    LC : 838
    https://leetcode.com/problems/push-dominoes/
    */
    public String pushDominoes(String dominoes) {
        int right = -1;
        int left = -1;
        char[] out = dominoes.toCharArray();
        int n = dominoes.length();
        for (int i = 0; i <= n ; i++) {
            if(i == n || dominoes.charAt(i) == 'R'){
                if(right > left) {// R...R, ...R
                    while (right < i){
                        out[right++] = 'R';
                    }
                }
                right = i;
            }else if(dominoes.charAt(i) == 'L'){
                if(left > right || (right  == -1 && left == -1)){ // L...L, ...L
                    while (left < i){
                        out[++left] = 'L';
                    }
                }else{ // R...L
                    left = i;
                    int mid = right + (left - right) / 2;
                    if((left - right + 1) % 2 == 1){
                        out[mid] =  '.';
                    }
                    int low = right + 1, high = left - 1;
                    while (low  < high){
                        out[low] = 'R';
                        out[high] = 'L';
                        low++;
                        high--;
                    }
                }
            }
        }
        String res = new String(out);
        System.out.println("out = " + res);
        return res;
    }
}
