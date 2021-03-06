package com.pkumar7.dp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    public static void main(String[] args) {
        A curr = new A();
        int res = curr.integerBreak(8);
        System.out.println("res = " + res);
    }

    /* 1808. Maximize Number of Nice Divisors
    * https://leetcode.com/problems/maximize-number-of-nice-divisors/
    * */
    public int maxNiceDivisors(int n) {
        int mod = (int)1e9+7;
        if(n <= 3) return n;
        long maxProduct = n;
        switch (n % 3){
            case 0:
                maxProduct = modpow(3, n / 3, mod);
                break;
            case 1:
                maxProduct = 4 * modpow(3, n / 3 - 1, mod);
                break;
            case 2:
                maxProduct = 2 * modpow(3, n / 3, mod);
                break;
        }
        return (int)(maxProduct  % mod);
    }

    public long modpow(long base, long pow, int mod) {
        /*long result = 1;
        while (pow > 0) {
            if ((pow & 1) == 1)
                result = result * base % modulus;
            base = base * base % modulus;
            pow >>= 1;
        }
        return result;*/
        if (pow == 1) return base;
        if (pow == 0) return 1;
        long k1 = modpow(base, pow / 2, mod);
        if (pow % 2 == 0) {
            return (k1 * k1) % mod;
        } else {
            return (k1*k1*base) % mod;
        }
    }

    /* 1690. Stone Game VII
     * https://leetcode.com/problems/stone-game-vii/
     * */
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int sum = Arrays.stream(stones).sum();
        int i = 0, j = n - 1;
        stonesdp = new int[n][n];
        for (int k = 0; k < n; k++) {
            Arrays.fill(dp[k], 0);
        }
        return dfs(stones, i, j, sum);
    }

    int[][] stonesdp;
    public int dfs(int[] stones, int i, int j, int sum){
        if(i == j){
            return 0;
        }
        if(stonesdp[i][j] != 0){
            return stonesdp[i][j];
        }
        int diffstart = (sum - stones[i]) - dfs(stones, i + 1, j, sum - stones[i]);// difference if i is chosen
        int diffend = (sum - stones[j]) - dfs(stones, i, j - 1, sum - stones[j]);// difference if j is chosen
        int maxDiff = Math.max(diffstart, diffend);
        stonesdp[i][j] = maxDiff;
        return stonesdp[i][j];
    }

    /* 1691. Maximum Height by Stacking Cuboids
     * https://leetcode.com/problems/maximum-height-by-stacking-cuboids/
     * */
    public int maxHeight(int[][] cuboids) {
        int n = cuboids.length;
        for(int[] cube: cuboids){
            Arrays.sort(cube);
        }
        Arrays.sort(cuboids, (a,b) -> (a[0] + a[1] + a[2]) - (b[0] + b[1] + b[2]));
        int max = 0;
        int[] lis = new int[n];
        for (int i = 0; i < n; i++) {
            lis[i] = cuboids[i][2];
            max = Math.max(lis[i], max);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if(cuboids[j][0] <= cuboids[i][0] &&
                        cuboids[j][1] <= cuboids[i][1] &&
                        cuboids[j][2] <= cuboids[i][2]) {
                    lis[i] = Math.max(lis[i], lis[j] + cuboids[i][2]);
                    max = Math.max(max, lis[i]);
                }
            }
        }
        return max;
    }

    /*
     * 801. Minimum Swaps To Make Sequences Increasing
     * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
     * */
    public int minSwap(int[] a, int[] b) {
        int n = a.length;
        int[]swap = new int[n];
        int[]not_swap = new int[n];
        swap[0] = 1;
        for (int i = 1; i < n; i++) {
            swap[i] = not_swap[i] = n;
            if(a[i - 1] < a[i] && b[i - 1] < b[i]){
                not_swap[i] = not_swap[i - 1];
                swap[i] = swap[i - 1] + 1;
            }
            if(a[i - 1] < b[i] && b[i - 1] < a[i]){
                swap[i] = Math.min(not_swap[i - 1] + 1, swap[i]);
                not_swap[i] = Math.min(swap[i - 1], not_swap[i]);
            }
        }
        return Math.min(swap[n - 1], not_swap[n - 1]);
    }

    /* 464. Can I Win
     * https://leetcode.com/problems/can-i-win/
     * */
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if(sum < desiredTotal) return false;
        if(desiredTotal <= 0) return true;
        used = new boolean[maxChoosableInteger + 1];
        return helper(desiredTotal, map);
    }
    public boolean[] used;
    public boolean helper(int desiredTotal, HashMap<Integer, Boolean> map){
        if(desiredTotal <= 0){
            return false;
        }
        int key = format(used);
        if(map.containsKey(key)){
            return map.get(key);
        }
        for (int i = 1; i < used.length; i++) {
            if(!used[i]){
                used[i] = true;
                if(!helper(desiredTotal - i, map)){// If A chooses i and B loses with rem = desiredTotal -i, A wins
                    map.put(key, true);
                    used[i] = false; // A decided to let B choose this number if A wins (Doubt !! )
                    return true;
                }
                used[i] = false;
            }
        }
        map.put(key, false);
        return false;
    }

    private int format(boolean[] used) {
        int num = 0;
        for (int i = 1; i < used.length; i++) {
            num <<= 1;
            if(used[i]) num |= 1;
        }
        return num;
    }

    private StringBuilder getKey(boolean[] used) {
        StringBuilder out = new StringBuilder();
        for (int i = 1; i < used.length; i++) {
            out.append(used[i] ? 1 : 0);
        }
        return out;
    }

    /* 343. Integer Break
     * https://leetcode.com/problems/integer-break/
     * */
    public int integerBreak(int n) {
        return integerBreakDP(n);
    }

    public int integerBreakDP(int n) {
        List<Integer> dp = new ArrayList<>();
        dp.add(0);
        dp.add(0);
        dp.add(1);
        dp.add(2);
        dp.add(4);
        dp.add(6);
        dp.add(9);
        for (int i = 7; i <= n ; i++) {
            dp.add( 3 * dp.get(i - 3));
        }
        return dp.get(n);
    }

    public int integerBreakIterative(int n) {
        if(n <= 3) return n-1;
        int res = 1;
        while (n > 4){
            res = res * 3;
            n = n - 3;
        }
        return n * res;
    }


    /* 1664. Ways to Make a Fair Array
     * https://leetcode.com/problems/ways-to-make-a-fair-array/
     * */
    public int waysToMakeFair(int[] nums) {
        int res = 0;
        int totaleven = 0, totalodd = 0;
        for (int i = 0; i < nums.length; i++) {
            if(i % 2 == 0) totaleven += nums[i];
            else totalodd += nums[i];
        }
        int lefteven = 0, leftodd = 0;
        boolean iseven = false;
        for (int i = 0; i < nums.length; i++) {
            if(i % 2 == 0){
                iseven = true;
                lefteven += nums[i];
            }else {
                iseven = false;
                leftodd += nums[i];
            }
            int rightOdd = 0, rightEven = 0;
            rightOdd = totaleven - lefteven;
            rightEven = totalodd - leftodd;

            /*Delete current element
            from curr running odd even sum*/
            if(iseven){
                lefteven -= nums[i];
            }else{
                leftodd -= nums[i];
            }
            if((rightOdd + leftodd) == (rightEven + lefteven)){
                res++;
            }
            /*Restore current element
            from curr running odd even sum*/
            if(iseven){
                lefteven += nums[i];
            }else{
                leftodd += nums[i];
            }
        }
        return res;
    }

    /* 873. Length of Longest Fibonacci Subsequence
     * https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
     * */
    public int lenLongestFibSubseq(int[] arr) {
        //return lenLongestFibSubseqSets(arr);
        return lenLongestFibSubseqDP(arr);
    }

    public int lenLongestFibSubseqDP(int[] arr) {
        // dp[a,b] = dp[b - a, a] + 1
        HashMap<Integer, Integer> index = new HashMap<>();
        int n = arr.length;
        int[][] dp = new int[n][n];
        int res = 0;
        for (int j = 0; j < n; j++) {
            index.put(arr[j], j);
            for (int i = 0; i < j; i++) {
                int k = index.getOrDefault(arr[j] - arr[i], -1);
                dp[i][j] = (arr[j] - arr[i] < arr[i] && k >= 0) ? dp[k][i] + 1 : 2;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res > 2 ? res : 0;
    }

    public int lenLongestFibSubseqSets(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for(int x : arr) set.add(x);
        int res = 2;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int a = arr[i], b = arr[j];
                int len = 2;
                while (set.contains(a+b)){
                    b = b + a;
                    a = b - a;
                    len += 1;
                }
                res = Math.max(res, len);
            }
        }
        return res < 3 ? 0 : res;
    }

    /*
    1320. Minimum Distance to Type a Word Using Two Fingers
    https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
    */
    public int minimumDistance(String word) {
        location = new HashMap<>();
        int i = 0; int j = 0;
        for(char ch = 'A'; ch <= 'Z'; ch++){
            if(j>0 && j%6==0){
                j = 0;
                i++;
            }
            location.put(ch, new int[]{i, j});
            j++;
        }
        dpfingers = new int[27][27][301];
        for (int k = 0; k < 27; k++) {
            for (int l = 0; l < 27; l++) {
                Arrays.fill(dpfingers[k][l], -1);
            }
        }
        return dfs(word, 0, null, null);
    }

    int[][][] dpfingers;
    HashMap<Character, int[]> location;
    public int dfs(String word, int pos, Character left, Character right){
        if(pos >= word.length()) return 0;
        int leftIdx = left == null ? 0 : left - 'A' + 1;
        int rightIdx = right == null ? 0 : right - 'A' + 1;
        if(dpfingers[leftIdx][rightIdx][pos] != -1) {
            return dpfingers[leftIdx][rightIdx][pos];
        }
        char to = word.charAt(pos);
        dpfingers[leftIdx][rightIdx][pos] = Math.min(cost(left, to) + dfs(word, pos + 1, to, right),
                cost(right, to) + dfs(word, pos + 1, left, to));
        return dpfingers[leftIdx][rightIdx][pos];
    }
    public int cost(Character from, Character to){
        if(from == null || to == null) return 0;
        if(from == to) return 0;
        int[] loc1 = location.get(from);
        int[] loc2 = location.get(to);
        return Math.abs(loc1[0] - loc2[0]) + Math.abs(loc1[1] - loc2[1]);
    }

    /* 1531. String Compression II
     * https://leetcode.com/problems/string-compression-ii/
     * */
    Integer[][][][]dp;
    Set<Integer> add = new HashSet<>(Arrays.asList(1, 9, 99));
    public int getLengthOfOptimalCompression(String s, int k) {
        dp = new Integer[s.length() + 1][27][s.length() + 1][k + 1];
        return dfs(s, 0, (char)('a' + 26), 0, k);
    }
    public int dfs(String s, int idx, char prevChar, int prevCharCount, int k){
        if(k < 0) return Integer.MAX_VALUE;
        if(idx >= s.length()) return 0;
        if(dp[idx][prevChar - 'a'][prevCharCount][k] != null) return dp[idx][prevChar - 'a'][prevCharCount][k];
        int res = 0;
        if(s.charAt(idx) == prevChar) {
            res = dfs(s, idx + 1, prevChar, prevCharCount + 1, k) +
                    (add.contains(prevCharCount) ? 1 : 0);
        }else{
            res = Math.min(dfs(s, idx + 1, s.charAt(idx), 1, k) + 1,
                    dfs(s, idx + 1, prevChar, prevCharCount, k - 1 ));
        }
        dp[idx][prevChar - 'a'][prevCharCount][k] = res;
        return dp[idx][prevChar - 'a'][prevCharCount][k];
    }

    /* 651. 4 Keys Keyboard
     * https://leetcode.com/problems/4-keys-keyboard/
     * */
    public int maxA(int n){
        return dpMaxA(n);
    }

    public int dfs(int n){
        if(n <= 6){
            return n;
        }
        int max = n;
        for(int i = 1; i < n - 2; i++){
            max = Math.max(max, dfs(i) * (n - i - 1) );
        }
        return max;
    }

    public int dpMaxA(int n){
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; i <= i - 3 ; i++) {
                dp[i] = Math.max(dp[i], dp[j] * ( i - j - 1));
            }
        }
        return dp[n];
    }

    /*
     * 650. 2 Keys Keyboard
     * https://leetcode.com/problems/2-keys-keyboard/
     */
    public int minSteps(int n) {
        if(n == 1) return 0;
        for (int i = 2; i < n; i++) {
            if(n % i == 0){
                return i + minSteps(n / i);
            }
        }
        return n;
    }

    /* 1626. Best Team With No Conflicts
     * https://leetcode.com/problems/best-team-with-no-conflicts/
     * Longest Increasing subsequence
     * */
    public int bestTeamScore(int[] scores, int[] ages) {
        int[][] scoresByAge = new int[ages.length][2];
        for (int i = 0; i < scores.length; i++) {
            scoresByAge[i][0] = ages[i];
            scoresByAge[i][1] = scores[i];
        }
        Arrays.sort(scoresByAge, (a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        return findMaxScoreLIS(scoresByAge);
    }

    public int findMaxScoreLIS(int[][] nums) {
        int n = nums.length;
        int LIS[] = new int[n];
        for(int i = 0; i < n; i++){
            LIS[i] = nums[i][1];
        }
        for(int i = 1; i < n; i++){
            for(int j = 0; j < i; j++){
                if(nums[j][1] <= nums[i][1]){
                    LIS[i] = Math.max(LIS[i], LIS[j] + nums[i][1]);
                }
            }
        }
        return Arrays.stream(LIS).max().getAsInt();
    }

    /* 871. Minimum Number of Refueling Stops
     * https://leetcode.com/problems/minimum-number-of-refueling-stops/
     * */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        //return usingPriorityQueue(target, startFuel, stations);
        return usingDp(target, startFuel, stations);
    }

    public int usingDp(int target, int startFuel, int[][] stations){
        int n = stations.length;
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0 && dp[j] >= stations[i][0]; j--) {
                dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
            }
        }
        for (int i = 0; i <= n; i++) {
            if(dp[i] >= target) return i;
        }
        return -1;
    }

    public int usingPriorityQueue(int target, int startFuel, int[][] stations){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> -a + b);
        long curr = startFuel;
        int i = 0;
        int n = stations.length;
        int res = 0;
        while (true){
            while (i < n && stations[i][0] <= curr){
                maxHeap.offer(stations[i][1]);
                i++;
            }
            if(curr >= target) return res;
            if(maxHeap.isEmpty()) return -1;
            curr += maxHeap.poll();
            res++;
        }
    }

    /* 1594. Maximum Non Negative Product in a Matrix
     * https://leetcode.com/problems/maximum-non-negative-product-in-a-matrix/
     * */
    public int maxProductPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[][] max = new long[m][n];
        long[][] min = new long[m][n];
        max[0][0] = min[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {// first col
            max[i][0] = min[i][0] = max[i-1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {// first row
            max[0][i] = min[0][i] = max[0][i-1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(grid[i][j] < 0){
                    max[i][j] = Math.min(min[i][j-1], min[i-1][j]) * grid[i][j];
                    min[i][j] = Math.max(max[i][j-1], max[i-1][j]) * grid[i][j];
                }else{
                    min[i][j] = Math.min(min[i][j-1], min[i-1][j]) * grid[i][j];
                    max[i][j] = Math.max(max[i][j-1], max[i-1][j]) * grid[i][j];
                }
            }
        }
        int mod = (int)1e9 + 7;
        long ans = max[m - 1][n - 1] % mod;
        return ans < 0 ? -1 : (int)ans;
    }

    public int maxProductPath1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        dfs(grid, 0, 0, grid[0][0] );
        return maxprod < 0 ? -1 : (int)(maxprod % mod );
    }
    long maxprod = Long.MIN_VALUE;
    int mod = (int)1e9 + 7;
    public void dfs(int[][] grid, int curr_x, int curr_y, long prod){
        int m = grid.length;
        int n = grid[0].length;
        if(curr_x == m - 1 && curr_y == n - 1 ){
            maxprod = Math.max(maxprod, prod);
            return;
        }
        int[][] dirs = new int[][]{{1,0},{0,1}};
        for (int i = 0; i < dirs.length; i++) {
            int next_x = dirs[i][0] + curr_x;
            int next_y = dirs[i][1] + curr_y;
            if(isValid(grid, next_x, next_y)){
                if(grid[next_x][next_y] == 0){
                    maxprod = Math.max(maxprod, 0);
                    continue;
                }
                dfs(grid, next_x, next_y, prod * (long)grid[next_x][next_y] );
            }
        }
    }

    public boolean isValid(int[][] grid, int next_x, int next_y ){
        int m = grid.length;
        int n = grid[0].length;
        if(next_x >= m || next_x < 0 || next_y >= n || next_y < 0 ){
            return false;
        }
        return true;
    }



    /* 1027. Longest Arithmetic Subsequence
     * https://leetcode.com/problems/longest-arithmetic-subsequence/
     * */
    public int longestArithSeqLength(int[] arr) {
        int n = arr.length;
        int res = 2;
        HashMap<Integer, Integer>[] dp = new HashMap[n];
        dp[0] = new HashMap<>();
        for (int j = 1; j < n; j++) {
            dp[j] = new HashMap<>();
            for (int i = 0; i < j; i++) {
                int diff = arr[j] - arr[i];
                dp[j].put(diff, dp[i].getOrDefault(diff, 1) + 1);
                res = Math.max(res, dp[j].get(diff));
            }
        }
        return res;
    }


    /*
    * 1563. Stone Game V
    * https://leetcode.com/problems/stone-game-v/
    * */
    int[] prefixsum;
    int[][] memo;
    public int stoneGameV(int[] stones) {
        int n = stones.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        prefixsum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixsum[i + 1] = stones[i] + prefixsum[i];
        }
        int res = dfs(stones, 0, n - 1);
        System.out.println("res = " + res);
        return res;
    }

    public int dfs(int[] arr, int i, int j) {
        if(i == j) return 0;
        if(memo[i][j] != -1) return memo[i][j];
        memo[i][j] = 0;
        for (int p = i + 1; p <= j; p++){
            int l = prefixsum[p] - prefixsum[i];
            int r = prefixsum[j + 1] - prefixsum[p];
            if(l < r){
                memo[i][j] = Math.max(memo[i][j], l + dfs(arr, i, p - 1));
            }else if(l > r){
                memo[i][j] = Math.max(memo[i][j], r + dfs(arr, p, j));
            }else{
                memo[i][j] = Math.max(memo[i][j],
                        l + Math.max(dfs(arr, i, p - 1), dfs(arr,p, j)));
            }
        }
        return memo[i][j];
    }

    /* 1473. Paint House III
    * https://leetcode.com/problems/paint-house-iii/
    * */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m][n][target + 1];
        for (int i = 0; i < m; i++ ) {
            for(int j = 0; j < n; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        int res = dfs(houses, cost, m, n, target, 0, 0, -1, dp);
        return res == max ? -1 : res;
    }

    int max = (int)1e9;
    public int dfs(int[] h, int[][] c, int m, int n, int target, int housePainted, int currTarget, int neighColor, int[][][] dp) {
        if(housePainted == m) {
            if(target == currTarget) return 0;
            return max;
        }
        if(currTarget == target + 1) return max;
        if(neighColor != -1){
            if(dp[housePainted][neighColor][currTarget] != -1) return dp[housePainted][neighColor][currTarget];
        }
        int res = max;
        for (int currColor = 0; currColor < n; currColor++) {
            boolean needPaint = true;
            if (h[housePainted] != 0) { // If house is already painted
                if(h[housePainted] != currColor + 1){ // If color is not same as neighbor, skip it
                    continue;
                }else{
                    needPaint = false;
                }
            }
            if (currColor != neighColor) {
                res = Math.min(res, dfs(h, c, m, n, target, housePainted + 1, currTarget + 1, currColor, dp) + ((!needPaint) ? 0 : c[housePainted][currColor]));
            }else {
                res = Math.min(res, dfs(h, c, m, n, target, housePainted + 1, currTarget, currColor, dp) + ((!needPaint) ? 0 : c[housePainted][currColor]));
            }
        }
        if(neighColor != -1) {
            dp[housePainted][neighColor][currTarget] = res;
        }
        return res;
    }

    /*1553. Minimum Number of Days to Eat N Oranges
    * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
    * */
    HashMap<Integer, Integer> dpDays = new HashMap<>();
    public int minDaysDp(int n) {
        if(n <= 1) return n;
        if(dpDays.containsKey(n)) return dpDays.get(n);
        int res = 1 + Math.min(n % 2 + minDaysDp(n / 2), n % 3 + minDaysDp(n / 3));
        dpDays.put(n, res);
        return res;
    }
}
