package com.pkumar7.dp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
class B {
    public static void main(String[] args) {
        B current = new B();
        current.findNumberOfLIS(new int[] {1, 3, 5, 4, 7});
    }

    /*
     * https://leetcode.com/problems/painting-a-grid-with-three-different-colors/
     * 1931. Painting a Grid With Three Different Colors
     * */
    int mod = (int) 1e9 + 7;
    List<String> moves = new ArrayList<>();
    int[][] memo;

    public int colorTheGrid(int m, int n) {
        moves.clear();
        fill("", m, -1);
        memo = new int[n + 1][moves.size() + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        int res = 0;
        for (int idx = 0; idx < moves.size(); idx++) {
            res = (res + solve(n - 1, idx, m) % mod) % mod;
        }
        return res;
    }

    private int solve(int n, int lastIdx, int m) {
        if (n == 0) return 1;
        int res = 0;
        if (memo[n][lastIdx] != -1) return memo[n][lastIdx];
        String lastMove = moves.get(lastIdx);
        for (int idx = 0; idx < moves.size(); idx++) {
            String currMove = moves.get(idx);
            boolean same = false;
            for (int i = 0; i < m; i++) {
                if (currMove.charAt(i) == lastMove.charAt(i)) same = true;
            }
            if (!same) res = (res + solve(n - 1, idx, m) % mod) % mod;
        }
        memo[n][lastIdx] = res % mod;
        return memo[n][lastIdx];
    }

    public void fill(String s, int row, int prevCol) {
        if (row == 0) {
            moves.add(s);
            return;
        }
        for (int i = 1; i <= 3; i++) {
            if (i == prevCol) continue;
            String mCol = String.valueOf(i);
            fill(s + mCol, row - 1, i);
        }
    }

    /* 1014. Best Sightseeing Pair
     * https://leetcode.com/problems/best-sightseeing-pair/
     * */
    public int maxScoreSightseeingPair(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];

        left[0] = arr[0];
        int res = 0;
        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1] - 1, arr[i]); // -1 for moving towards second spot
            res = Math.max(res, left[i - 1] - 1 + arr[i]);// maximize ans with curr + prev_max - 1
        }
        return res;
    }

    /*
    * https://leetcode.com/problems/minimum-falling-path-sum/
    * 931. Minimum Falling Path Sum
    * */
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(i == 0){
                    dp[i][j] = matrix[i][j];
                }else{
                    int min1 = Integer.MAX_VALUE;
                    int min2 = Integer.MAX_VALUE;
                    if(j >= 1){
                        min1 = Math.min(dp[i - 1][j] + matrix[i][j], dp[i - 1][j - 1] + matrix[i][j]);
                    }
                    if(j < n - 1){
                        min2 = Math.min(dp[i - 1][j] + matrix[i][j], dp[i - 1][j + 1] + matrix[i][j]);
                    }
                    dp[i][j] = Math.min(min1, min2);
                }
            }
        }
        int res =  Arrays.stream(dp[m - 1]).min().getAsInt();
        return res;
    }

    /*
     * https://leetcode.com/problems/maximum-number-of-points-with-cost/
     * 1937. Maximum Number of Points with Cost
     * */
    public long maxPoints(int[][] points) {
        //return solve(points, 0, -1);
        int r = points.length;
        int c = points[0].length;
        long[][] memo = new long[r + 10][c + 10];
        for (int i = 0; i <= r; i++) {
            Arrays.fill(memo[i], -1);
        }
        for (int i = 0; i < c; i++) {
            memo[0][i] = solveMemo(points, 0, i, memo);
        }
        System.out.println(Arrays.toString(memo[0]));
        return Arrays.stream(memo[0]).max().getAsLong();
    }

    public long solveDPOptimal(int[][] points){
        int r = points.length;
        int c = points[0].length;
        long[][] dp = new long[r][c];
        for (int i = 0; i < c; i++) {
            dp[0][i] = points[0][i];
        }
        for (int i = 1; i < r ; i++) {
            long[] leftDp = new long[c];
            long[] rightDp = new long[c];
            leftDp[0] = dp[i - 1][0];
            for (int j = 1; j < c; j++) {
                leftDp[j] = Math.max(leftDp[j - 1] - 1, dp[i - 1][j] );
            }
            rightDp[c - 1] = dp[i - 1][c - 1];
            for (int j = c - 2 ; j >= 0; --j) {
                rightDp[j] = Math.max(rightDp[j + 1] - 1, dp[i - 1][j]);
            }

            for (int j = 0; j < c; j++) {
                dp[i][j] = Math.max(leftDp[j], rightDp[j]) + points[i][j];
            }
        }
        return Arrays.stream(dp[r - 1]).max().getAsLong();
    }

    public long solveDP(int[][] points){
        int r = points.length;
        int c = points[0].length;
        long[][] dp = new long[r + 1][c + 1];
        for (int i = 1; i <= r ; i++) {
            for (int j = 1; j <= c; j++) {
                if(i == 1){
                    dp[i][j] = points[i -1][j - 1];
                }else{
                    for (int k = 1; k <= c; k++) {
                        dp[i][j] = Math.max(dp[i][j],
                                points[i - 1][j - 1] - Math.abs(k - j) + dp[i - 1][k]);
                    }
                }
            }
        }
        return Arrays.stream(dp[r]).max().getAsLong();
    }

    public long solveMemo(int[][] points, int currRow, int currCol, long[][] memo){
        int r = points.length;
        int c = points[0].length;
        if(currRow == r){
            return 0;
        }
        if(memo[currRow][currCol] != -1) {
            return memo[currRow][currCol];
        }
        long maxPoints = 0;
        for (int col = 0; col < c; col++) {
            long currPoints = points[currRow][col] - (currRow == 0 ? 0 : Math.abs(currCol - col))
                    + solveMemo(points, currRow + 1, col, memo);
            maxPoints = Math.max(maxPoints,currPoints);
        }
        return memo[currRow][currCol] = maxPoints;
    }

    public long solve(int[][] points, int currRow, int prevCol){
        int r = points.length;
        int c = points[0].length;
        if(currRow == r){
            return 0;
        }
        long maxPoints = 0;
        for (int currCol = 0; currCol < c; currCol++) {
            maxPoints = Math.max(maxPoints,
                    points[currRow][currCol] - (prevCol == -1 ? 0 : Math.abs(prevCol - currCol))
                            + solve(points, currRow + 1, currCol));
        }
        return maxPoints;
    }

    /* 1220. Count Vowels Permutation
    * https://leetcode.com/problems/count-vowels-permutation/
    * */
    public int countVowelPermutation(int n) {
        int vowels = 5;
        long[][] dp = new long[n + 1][vowels + 1];
        int mod = (int)1e9 + 7;
        // a  = 1, e = 2, i = 3, o = 4 , u = 5
        // dp[i][j] : No. of strings of len i that ends with vowels j
        // dp[0][0] : 0
        // dp[1][j] : 1
        // allowed = ea, ae, ie, io, uo, au
        // not allowed = ii

        for(int j = 1; j <= vowels; j++){
            dp[1][j] = 1;
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= vowels; j++){
                if(j == 1){
                    dp[i][j] += (dp[i - 1][j + 1] % mod);
                }else if(j == 2){
                    dp[i][j] += (dp[i - 1][j + 1] % mod) + (dp[i - 1][j - 1] % mod);
                }else if(j == 4){
                    dp[i][j] += (dp[i - 1][j - 1] % mod) + (dp[i - 1][j + 1] % mod);
                }else if(j == 5){
                    dp[i][j] += dp[i - 1][j - 4] % mod;
                }else{
                    dp[i][j] += dp[i - 1][j - 1] + dp[i - 1][j - 2] + dp[i - 1][j + 1] + dp[i - 1][j + 2];
                }
            }
        }

        long res = 0;
        for(int j = 1; j <= vowels; j++){
            long ans = (dp[n][j] + mod) % mod;
            res = (res + ans) % mod;
        }
        return (int)res;
    }

    /*
     * https://leetcode.com/problems/student-attendance-record-ii/
     * 552. Student Attendance Record II
     * */
    public int checkRecord(int n) {
        long[][] dp = new long[n + 1][3];

        dp[0][0] = dp[0][1] = dp[0][2] = 1;

        dp[1][0] = 1; // ends with P
        dp[1][1] = 1; // ends with L
        dp[1][2] = dp[1][0] + dp[1][1];// ends with P or L
        // dp[i][2] = dp[i][0] + dp[i][1]
        int mod = (int)1e9 + 7;
        for(int i = 2; i <= n; i++){
            dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % mod;
            dp[i][1] = (dp[i - 1][0] + dp[i - 2][0]) % mod;
            dp[i][2] = (dp[i][0] + dp[i][1]) % mod;
        }
        long res = dp[n][2];
        for(int i = 0; i < n; i++){ // insert A at i
            long temp = (dp[i][2] * dp[n - i - 1][2]) % mod; // ways on left * ways on right
            res = (res + temp) % mod;
        }
        return (int)res;
    }

    /*
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
     * https://leetcode.com/problems/maximum-alternating-subsequence-sum/
     * 1911. Maximum Alternating Subsequence Sum
     * */
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long maxDiff = nums[0];
        for (int i = 1; i < n; i++) {
            maxDiff += Math.max(nums[i] - nums[i - 1], 0);
        }
        return maxDiff;
    }

    /*
     * https://leetcode.com/problems/minimum-skips-to-arrive-at-meeting-on-time/
     * */
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int[][] dp = new int[dist.length + 1][dist.length + 1];
        for (int i = 0; i <= dist.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        int n = dist.length;
        for (int i = 0; i <= dist.length; i++) {
            if(dfs(dp, dist, speed, n - 1, i) <= (long) hoursBefore * speed){
                return i;
            }
        }
        return -1;
    }
    public int dfs(int[][] dp, int[] arr, int speed, int i, int k){
        if(i < 0) return 0;
        if(dp[i][k] != -1) return dp[i][k];

        int res = (dfs(dp, arr, speed, i-1, k) + arr[i] + speed - 1) / speed * speed;
        if(k > 0) // can use skip
            res = Math.min(res, arr[i] + dfs(dp, arr, speed, i-1, k-1));

        return dp[i][k] = res;
    }

    /*  1774. Closest Dessert Cost
        https://leetcode.com/problems/closest-dessert-cost/
    */
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int res = 0;
        int max = 30000;
        boolean[] dp = new boolean[max];
        dp[0] = true; // can have no toppings at all.
        for (int t : toppingCosts) {
            for (int i = max - 1; i >= 0; i--) {
                if (dp[i]) { // If we can choose this topping
                    if (i + t < max)dp[i + t] = true; // current topping 1 time
                    if (i + t * 2 < max) dp[i + 2 * t] = true; // current topping at most 2 times
                }
            }
        }
        int best = 99999999;
        for (int b : baseCosts) {
            for (int i = 0; i < max; i++) {
                if (dp[i]) {
                    int v = b + i;
                    if (
                        (Math.abs(v - target) < Math.abs(best - target)) ||
                        (Math.abs(v - target) == Math.abs(best - target)) &&
                        v < best
                    ) {
                        best = v;
                    }
                }
            }
        }
        return best;
    }

    /* 1771. Maximize Palindrome Length From Subsequences
     * https://leetcode.com/problems/maximize-palindrome-length-from-subsequences/
     * */
    public int longestPalindrome(String word1, String word2) {
        String s = word1 + word2;
        int n = s.length();
        int[][] dp = longestPalindromeSubseqDP(s);
        int res = 0;
        for (int i = 0; i < word1.length(); i++) {
            for (int j = word2.length() - 1; j >= 0; --j) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    res = Math.max(res, dp[i][j + word1.length()]);
                }
            }
        }
        return res;
    }

    public int[][] longestPalindromeSubseqDP(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }


    /*
     * https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/
     * */
    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length;
        int i = 0, j = n - 1;
        int m = multipliers.length;
        dp = new int[m + 1][m + 1];
        return dfs(nums, i, 0, multipliers);
    }

    int[][] dp;

    public int dfs(int[] nums, int left, int currIdx, int[] multiplier) {
        if (currIdx >= multiplier.length) return 0;
        int n = nums.length;

        int right = n - (currIdx - left) - 1;
        if (currIdx == multiplier.length - 1) {
            return Math.max(nums[left] * multiplier[currIdx], nums[right] * multiplier[currIdx]);
        }
        if (dp[left][currIdx] != 0) return dp[left][currIdx];
        int start = (nums[left] * multiplier[currIdx]) + dfs(nums, left + 1, currIdx + 1, multiplier);
        int end = (nums[right] * multiplier[currIdx]) + dfs(nums, left, currIdx + 1, multiplier);
        return dp[left][currIdx] = Math.max(start, end);
    }

    /* 413. Arithmetic Slices
     * https://leetcode.com/problems/arithmetic-slices/
     * */
    public int numberOfArithmeticSlices(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 0);
        if (n <= 2) return 0;
        for (int i = 2; i < arr.length; i++) {
            int first = arr[i - 1] - arr[i - 2];
            int second = arr[i] - arr[i - 1];
            if (first == second) {
                dp[i] = 1 + dp[i - 1];
            }
        }
        return Arrays.stream(dp).sum();
    }

    /* 1745. Palindrome Partitioning IV
     * https://leetcode.com/problems/palindrome-partitioning-iv/
     * */
    public boolean checkPartitioning(String s) {
        boolean[][] isPal = new boolean[2001][2001];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                isPal[i][j] = s.charAt(i) == s.charAt(j) && (i + 1 >= j || isPal[i + 1][j - 1]);
            }
        }
        for (int i = 2; i < s.length(); i++) {
            if (isPal[i][s.length() - 1]) {
                for (int j = 1; j < i; j++) {
                    if (isPal[j][i - 1] && isPal[0][j - 1]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* 673. Number of Longest Increasing Subsequence
    * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
    * */
    public int findNumberOfLIS(int[] nums) {
        int len[] = new int[nums.length];
        int cnt[] = new int[nums.length];

        int max_len = 0, res =  0;
        for (int i = 0; i < nums.length; i++) {
            len[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (len[i] == len[j] + 1) {
                        cnt[i] += cnt[j];
                    } else if (len[i] < len[j] + 1) {
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }

            if (max_len == len[i]) {
                res += cnt[i];
            } else if (max_len < len[i]) {
                max_len = len[i];
                res = cnt[i];
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/constrained-subsequence-sum/
    * 1425. Constrained Subsequence Sum
    * */
    public int constrainedSubsetSum(int[] nums, int k) {
        Deque<Integer> deq = new ArrayDeque<>();
        int maxSum = nums[0];
        for (int i = 0; i < nums.length; ++i) {
            nums[i] += !deq.isEmpty() ? nums[deq.peekFirst()] : 0;
            maxSum = Math.max(maxSum, nums[i]);
            while (!deq.isEmpty() && deq.peekFirst() < i - k + 1) {
                deq.pollFirst();
            }
            while (!deq.isEmpty() && nums[deq.peekLast()] < nums[i]) {
                deq.pollLast();
            }
            if (nums[i] > 0) {
                deq.offer(i);
            }
        }
        return maxSum;
    }
}
