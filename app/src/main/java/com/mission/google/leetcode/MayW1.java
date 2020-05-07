package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class MayW1 {
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/ */
        

    public static void main(String[] args) {
        MayW1 mayW1 = new MayW1();
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(3,4));
        list.add(Arrays.asList(4,5));
        list.add(Arrays.asList(5));
        int res = mayW1.maxDiff(123456);
        System.out.println("res = " + res);
    }

    /* https://leetcode.com/problems/minesweeper/ */
    public char[][] updateBoard(char[][] board, int[] click) {
        helper(board, click[0], click[1]);
        return board;
    }

    public void helper(char[][] board, int curr_x, int curr_y){
        if(curr_x < 0 || curr_x > board.length -1 || curr_y < 0 || curr_y > board[0].length -1){
            return;
        }
        
        if(board[curr_x][curr_y] == 'M') {
            board[curr_x][curr_y] = 'X';
            return;
        }
        
        if(board[curr_x][curr_y] != 'E') return;

        int[][] dirs = new int[][]{{0, 1}, {1, 1}, {1, 0}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

        board[curr_x][curr_y] = 'B';

        int totalMines = 0;
        for(int[] dir : dirs){
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;

            if(next_x >= 0 && next_x < board.length && next_y >= 0 && next_y < board[0].length){
                if(board[next_x][next_y] == 'M'){
                    totalMines++;
                }
            }
        }
        if(totalMines > 0){
            board[curr_x][curr_y] = (char) (totalMines + '0');
        }else{
                for(int[] dir : dirs){
                int next_x = dir[0] + curr_x;
                int next_y = dir[1] + curr_y;

                if(next_x >= 0 && next_x < board.length && next_y >= 0 && next_y < board[0].length){
                    if(board[next_x][next_y] == 'E'){
                        helper(board, next_x, next_y);
                    }
                }
            }
        }
        
    }


    /* https://leetcode.com/problems/find-k-pairs-with-smallest-sums/ */
    /* https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/ 
       Revisit
    */
    public int kthSmallest(int[][] mat, int k) {
        int[] row = mat[0];
        for(int r = 1; r < mat.length; r++){
            row = kSmallestPairs(row, mat[r]);
        }
        return row[k-1];
    }
    
    public int[] kSmallestPairs(int[] nums1, int[] nums2) {
         int k = 200;
         PriorityQueue<int[]> mMinHeap = new PriorityQueue<>((a, b)->a[0]+a[1]-b[0]-b[1]);

        ArrayList<Integer> res = new ArrayList<>();
        if(nums1.length==0 || nums2.length==0 || k==0) return new int[0];

        for (int i = 0; i < nums1.length && i < k; i++) {
            mMinHeap.offer(new int[]{nums1[i], nums2[0], 0});
        }

        for(int i = 0; i < k && !mMinHeap.isEmpty(); i++){
            int[] curr = mMinHeap.poll();
            int sum = curr[0] + curr[1];
            res.add(sum);

            if(curr[2] == nums2.length -1)continue;
            mMinHeap.offer(new int[]{curr[0], nums2[curr[2]+1], curr[2]+1});
        }
        int[] ans = new int[res.size()];
        for(int i = 0; i < ans.length; i++){
            ans[i] = res.get(i);
        }
        return ans;
    }

    /* https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/*/
    public int maxDiff(int num) {
        char[] curr = String.valueOf(num).toCharArray();
        char[] min = Arrays.copyOf(curr, curr.length);
        char[] max = Arrays.copyOf(curr, curr.length);
        
        char toReplace = '#';
        char withReplace = '#';
        for(int i = 0 ; i < min.length; i++){
            if(i == 0 && min[i] != '1'){
                toReplace = min[i];
                min[0] = '1';
                withReplace = '1';
                break;
            }else if(i > 0 && min[i] != '0' && min[i] != '1' ){
                toReplace = min[i];
                min[i] = '0';
                withReplace = '0';
                break;
            }
        }

        String minStr = new String(min);
        minStr = minStr.replaceAll(toReplace+"", withReplace+"");
        toReplace = '#';
        withReplace = '#';

        for(int i = 0 ; i < max.length; i++){
            if(max[i] != '9'){
                toReplace = max[i];
                max[0] = '9';
                withReplace = '9';
                break;
            }
        }

        String maxStr = new String(max);
        maxStr = maxStr.replaceAll(toReplace+"", withReplace+"");

        int maxInt = Integer.valueOf(maxStr);
        int minInt = Integer.valueOf(minStr);
        return maxInt - minInt;
    }


    /* https://leetcode.com/problems/max-consecutive-ones-ii/ */
    public int findMaxConsecutiveOnes(int[] nums) {
        int start = 0;
        int k = 1;
        int zeroCount = 0;
        int res = 0;
        for(int end = 0; end < nums.length; end++){
            if(nums[end] == 0)zeroCount++;
            while(zeroCount > k){
                if(nums[start] == 0) zeroCount--;
                start++;
            }
            res = Math.max(res, end - start + 1);
        }
        return res;
    }

    /* https://leetcode.com/problems/binary-subarrays-with-sum/ */
    public int numSubarraysWithSum(int[] arr, int s) {
        return atMostSum(arr, s) - atMostSum(arr, s - 1);
    }

    public int atMostSum(int[] arr, int sum){
        int res = 0;
        int start = 0;
        if(sum < 0 ) return 0;
        for(int end = 0; end < arr.length; end++){
            sum -= arr[end];
            while(sum < 0){
                sum += arr[start];
                start++;
            }
            res += end - start + 1;
        }
        return res;
    }

    /**
     https://www.geeksforgeeks.org/number-subarrays-m-odd-numbers/
     https://leetcode.com/contest/weekly-contest-161/problems/count-number-of-nice-subarrays/

     Input: nums = [1,1,2,1,1], k = 3
     Output: 2
     Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

     Input: nums = [2,4,6], k = 1
     Output: 0
     Explanation: There is no odd numbers in the array.

     **/
    public int numberOfSubarrays(int[] a, int k) {
        return atMostKOdd(a, k) - atMostKOdd(a, k -1);
    }

    public int atMostKOdd(int[] a, int k){
        Map<Integer, Integer> map = new HashMap<>();
        int j = 0, res = 0;
        for (int i = 0; i < a.length; i++) {
            if(map.getOrDefault(a[i],0) == 0) k--;
            if(a[i] % 2 == 1){
                map.put(a[i], map.getOrDefault(a[i], 0) + 1);
            }
            while (k < 0){
                map.put(a[j], map.getOrDefault(a[j], 0) -1);
                if(map.get(a[j]) == 0) k++;
                j++;
            }
            res += i - j + 1;
        }
        return res;
    }

    /* https://leetcode.com/problems/subarrays-with-k-different-integers/ */
    public int subarraysWithKDistinct(int[] A, int K) {
        return atMostK(A, K) - atMostK(A, K - 1);
    }
    int atMostK(int[] A, int K) {
        int i = 0, res = 0;
        Map<Integer, Integer> count = new HashMap<>();
        for (int j = 0; j < A.length; ++j) {
            if (count.getOrDefault(A[j], 0) == 0) K--;
            count.put(A[j], count.getOrDefault(A[j], 0) + 1);
            while (K < 0) {
                count.put(A[i], count.get(A[i]) - 1);
                if (count.get(A[i]) == 0) K++;
                i++;
            }
            res += j - i + 1;
        }
        return res;
    }


    /* https://leetcode.com/problems/ransom-note/ */
    public boolean canConstruct(String src, String target) {
        int[] freq = new int[26];

        for(char ch  : target.toCharArray()){
            freq[ch - 'a']++;
        }
        for(char ch  : src.toCharArray()){
            if(--freq[ch - 'a'] < 0){
                return false;
            }
        }
        return true;
    }

    public String destCity(List<List<String>> paths) {
        HashMap<String, String> map = new HashMap<>();
        HashSet<String> sets = new HashSet<>();
        for(int i = 0; i < paths.size(); i++){
            String src = paths.get(i).get(0);
            String dst = paths.get(i).get(1);
            map.put(src, dst);
            sets.add(src);
            sets.add(dst);
        }
        Iterator<String> it = sets.iterator();
        while(it.hasNext()){
            String src = it.next();
            if(!map.containsKey(src)) return src;
        }
        return "";
    }

    /* [8,2,4,7], limit = 4
    *  [10,1,2,4,7,2], limit = 5
    *  [4,2,2,2,4,4,2,2], limit = 0
    *  [4,8,5,1,7,9] 6
    */
    public int longestSubarray(int[] nums, int limit) {
        int start = 0;
        Deque<Integer> mindq = new ArrayDeque<>();
        Deque<Integer> maxdq = new ArrayDeque<>();
        int end = 0;
        for (; end < nums.length ; end++) {
            while (!mindq.isEmpty() && mindq.peekLast() > nums[end]) mindq.pollLast();
            while (!maxdq.isEmpty() && maxdq.peekLast() < nums[end]) maxdq.pollLast();

            mindq.offer(nums[end]);
            maxdq.offer(nums[end]);

            if (maxdq.peekFirst() - mindq.peekFirst() > limit) {
                if(maxdq.peekFirst() == nums[end]) maxdq.pollFirst();
                if(mindq.peekFirst() == nums[end]) mindq.pollFirst();
                start++;
            }
        }
        return start - end;
    }

    /* [1,0,0,1,0,1], k = 2 */
    /* https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away/ */
    public boolean kLengthApart(int[] nums, int k) {
        int lastOne = nums.length -1;
        for (int i = nums.length -1; i >= 0; i--) {
            if(nums[i] == 1) {
                lastOne = i;
                break;
            }
        }
        int dist = k;
        for (int i = 0; i < nums.length; ) {
            if(i == lastOne) return true;
            if(nums[i] == 1){
                i++;
                dist = 0;
                while (dist < k && nums[i] != 1){
                    i++;
                    dist++;
                }
                if(dist != k) return false;

            }else{
                i++;
            }
        }
        return true;
    }

    /* [[3,4],[4,5],[5]] */
    int mod = (int)1e9+7;
    /* https://leetcode.com/problems/number-of-ways-to-wear-different-hats-to-each-other/ */
    public int numberWays(List<List<Integer>> hats) {
        int[] memo = new int[hats.size()];
        Arrays.fill(memo, -1);
        helper(0, hats, new ArrayList<>(), memo);
        return memo[hats.size()-1];
    }

    public int helper(int start, List<List<Integer>> hats, ArrayList<Integer> sets, int[] memo){
        if(start == hats.size()){
            return 1;
        }
        if(memo[start] != -1){
            return memo[start];
        }
        int res = 0;
        for(int i = 0; i < hats.get(start).size(); i++){
            int curr = hats.get(start).get(i);
            if(sets.contains(curr)) continue;
            sets.add(curr);
            int ways = helper(start + 1, hats, sets, memo);
            ways = ways % mod;
            res+= ways;
            sets.remove(sets.size() -1);
        }
        int min = Math.min(memo[start],res);
        memo[start] = min;
        return res;
    }

    /* https://leetcode.com/problems/check-if-a-string-can-break-another-string/ */
    public boolean checkIfCanBreak(String s1, String s2) {
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        boolean isLess = arr1[0] < arr2[0];
        int i = 0;
        for (; i < arr1.length; i++) {
            if(arr1[i] == arr2[i]) continue;
            if(arr1[i] < arr2[i]){
                isLess = true;
                break;
            }else if(arr1[i] > arr2[i]){
                isLess = false;
                break;
            }
        }
        for (; i < arr1.length; i++) {
            if(arr1[i] < arr2[i] && !isLess){
                return false;
            }else if(arr1[i] > arr2[i] && isLess){
                return false;
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/ */
    public List<Boolean> kidsWithCandies(int[] arr, int extra) {
        List<Boolean> res = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        for(int i = 0; i < arr.length; i++){
            if(arr[i] + extra >= max){
                res.add(true);
            }else{
                res.add(false);
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/palindrome-partitioning/
    Input: "aab"
    Output:
    [
      ["aa","b"],
      ["a","a","b"]
      Revisit
    ]
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

    /* https://leetcode.com/problems/distinct-subsequences/ */
    /* Input: S = "rabbbit", T = "rabbit"
       Output: 3
    */
    public int numDistinct(String s, String t) {
        StringBuilder builder = new StringBuilder();
        //return helper(s, t, 0, builder);
        return helperdp(s, t);
        //return helperRec(s, t, 0, 0);
       /*int[][] memo = new int[t.length()+1][s.length()+1];
        for(int i = 0; i < t.length(); i++ ){
            Arrays.fill(memo[i], -1);
        }
        return helperMemo(s, t, 0, 0, memo);*/
    }

    public int helperRec(String s, String t, int i, int j){
        if(t.length() == i){
            return 1;
        }
        if(j >= s.length()) return 0;

        if(s.charAt(j) == t.charAt(i)){
            return helperRec(s, t, i + 1, j + 1) + helperRec(s, t, i, j + 1);
        }else{
            return helperRec(s, t, i, j + 1);
        }
    }

    public int helperMemo(String s, String t, int i, int j, int[][] memo){
        if(t.length() == i){
            return 1;
        }
        if(j >= s.length()) return 0;
        if(memo[i][j] > 0) return memo[i][j];

        if(s.charAt(j) == t.charAt(i)){
            memo[i][j] = helperMemo(s, t, i + 1, j + 1, memo) + helperMemo(s, t, i, j + 1, memo);
        }else{
            memo[i][j] = helperMemo(s, t, i, j + 1, memo);
        }
        return memo[i][j];
    }

    public int helperdp(String s, String t){
        int[][] dp = new int[t.length()+1][s.length()+1];
        for(int i = 0; i <= s.length(); i++){
            dp[0][i] = 1;
        }
        for(int i = 1; i <= t.length(); i++){
            for(int j = 1; j <= s.length(); j++){
                if(s.charAt(j-1) == t.charAt(i-1)){
                    dp[i][j] = dp[i-1][j-1] + dp[i][j-1];
                }else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[t.length()][s.length()];
    }

    // TLE
    public int helper(String s, String t, int start, StringBuilder builder){
        if(!t.startsWith(builder.toString())) return 0;
        if(builder.toString().length() > t.length()) return 0;

        if(builder.toString().equals(t)) return 1;

        int res = 0;
        for(int i = start; i < s.length(); i++){
            builder.append(s.charAt(i));
            res += helper(s, t, i + 1, builder);
            builder.deleteCharAt(builder.length()-1);
        }
        return res;
    }
    
}