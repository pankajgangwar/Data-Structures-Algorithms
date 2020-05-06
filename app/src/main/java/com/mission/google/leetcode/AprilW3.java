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

public class AprilW3 {


    public static void main(String[] args) {
        AprilW3 w3 = new AprilW3();
        //w2.stringMatching(new String[]{"leetcoder","leetcode","od","hamlet","am"});
        int[][] a = new int[][]{{0,2},{5,10},{13,23},{24,25}};
        int[][] b = new int[][]{{1,5},{8,12},{15,24},{25,26}};

        int [] arr = new int[]{23,2,6,4,7};
        //boolean res = w3.checkSubarraySum(arr, 6);
        //System.out.println("res = " + res);
        //w3.minSwap();
        //100
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(Arrays.asList(new Integer[]{1,2,3}));
        nums.add(Arrays.asList(new Integer[]{4,5,6}));
        nums.add(Arrays.asList(new Integer[]{7,8,9}));
    }


    /*https://leetcode.com/problems/time-needed-to-inform-all-employees/ */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();

        for(int i = 0; i < n; i++){
            if(manager[i] == -1) continue;
            graph.putIfAbsent(manager[i], new ArrayList<>());
            graph.get(manager[i]).add(i);
        }
        getTimeToInform(graph, headID, informTime, informTime[headID]);
        return time;
    }

    int time = 0;
    public void getTimeToInform(HashMap<Integer, List<Integer>> graph, int headID, int[] informTime, int totalTime){
        if(graph.get(headID) == null){
            time = Math.max(time, totalTime);
            return;
        }
        for(Integer head : graph.get(headID)){
            getTimeToInform(graph, head, informTime, totalTime + informTime[head]);
        }
    }

    /* https://leetcode.com/problems/prison-cells-after-n-days/ */
    public int[] prisonAfterNDays(int[] cells, int N) {
        if(cells==null || cells.length==0 || N<=0) return cells;
        List<String> visited = new ArrayList<>();
        boolean hasCycle = false;
        for (int i = 1; i <= N; i++) {
            int[] next = nextDay(cells);
            String state = Arrays.toString(next);
            if(!visited.contains(state)){
                visited.add(state);
            }else{
                hasCycle = true;
                break;
            }
            cells = next;
        }
        if(hasCycle){
            N = N % visited.size();
            for (int j = 0; j < N; j++) {
                cells = nextDay(cells);
            }
            return cells;
        }else{
            return cells;
        }
    }

    public int[] nextDay(int[] curr){
        int[] next = new int[curr.length];
        for (int i = 1; i < curr.length - 1; i++) {
            next[i] = curr[i-1] == curr[i+1] ? 1 : 0;
        }
        return next;
    }

    /* https://leetcode.com/playground/64FHNybL
    *  Total sub arrays with max element greater than k
    * */
    public static int subArray(int[] arr, int k){
        int count = 0;
        int s = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] <= k){
                count++;
                continue;
            }
            if(arr[i] > k){
                s += (count * count + 1) / 2;
            }
        }
        return s;
    }


    public int minSwap(){
        int[] nums = new int[]{4,5,1,2,3};
        int swaps = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != i + 1){
                swap(nums, i, nums[i]-1);
                swaps++;
                i--;
            }
        }
        System.out.println("nums = " + Arrays.toString(nums) + " minSwaps " + swaps);
        return swaps;
    }

    public void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /* https://leetcode.com/problems/bitwise-and-of-numbers-range/ 
       Revisit
    */
    public int rangeBitwiseAnd(int m, int n) {
        while(n > m){
            n = n & n - 1;
        }
        return m & n;
    }


    /* https://leetcode.com/problems/predict-the-winner/ */
    public boolean PredictTheWinner(int[] nums) {
        return false;
    }

    public boolean helper(int[] nums, int s, int e){
        return false;
    }


    /* https://leetcode.com/problems/continuous-subarray-sum/
    *  Revisit
    * */
    public boolean checkSubarraySum(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        map.put(0, -1);

        for(int i = 0; i < arr.length; i++ ){
            sum += arr[i];
            if(k != 0){
                sum = sum % k;
            }
            Integer prev = map.get(sum);
            if(prev != null){
                if(i - prev > 1){
                    return true;
                }
            }else {
                map.put(sum, i);
            }
        }
        return false;
    }

    /* https://leetcode.com/problems/subarray-sums-divisible-by-k/ */
    public int subarraysDivByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int sum = 0;
        map.put(0, 1);

        for(int i = 0, j = 0; i < arr.length; i++ ){
            sum = Math.floorMod(sum + arr[i], k);
            //sum = (sum + arr[i] % k + k) % k;
            count += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum , 0) +1);
        }
        return count;
    }

    /* https://leetcode.com/problems/find-k-closest-elements/
    * Revisit */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length;
        int low = 0;
        int high = n - k;
        while(low < high){
            int mid = low + (high - low) / 2;
            if(x - arr[mid] > arr[mid+k] - x){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        int[] copyArray = Arrays.copyOfRange(arr, low, low  + k);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < copyArray.length; i++) {
            res.add(copyArray[i]);
        }
        return res;
    }

    public List<Integer> findClosestElementsSlow(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        int idx = Arrays.binarySearch(arr, x);
        int n = arr.length;
        if(idx < 0){
            idx = Math.abs(idx) - 1;
            if(idx > 0){
                if(idx == n) idx--;
                if(Math.abs(x - arr[idx]) < Math.abs(arr[idx-1] - x)){
                    res.add(arr[idx]);
                }else{
                    res.add(arr[idx-1]);
                    idx = idx - 1;
                }
            }else{
                res.add(arr[idx]);
            }
        }else{
            res.add(arr[idx]);
        }

        int low = idx - 1;
        int high = idx + 1;

        while(low >= 0 || high < n){
            if(res.size() >= k){
                break;
            }
            int difflow = Integer.MIN_VALUE;
            if(low >= 0){
                difflow = Math.abs(x - arr[low]);
            }
            int diffhigh = Integer.MIN_VALUE;
            if(high < n){
                diffhigh = Math.abs(x - arr[high]);
            }
            if(difflow == Integer.MIN_VALUE){
                difflow = Integer.MAX_VALUE;
            }else if(diffhigh == Integer.MIN_VALUE){
                diffhigh = Integer.MAX_VALUE;
            }
            if(difflow <= diffhigh){
                res.add(arr[low]);
                low--;
            }else if(difflow > diffhigh){
                res.add(arr[high]);
                high++;
            }
        }
        Collections.sort(res);
        return res;
    }
    
    /* https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/530/week-3/3306/ */

    interface BinaryMatrix {
       int get(int x, int y);
       List<Integer> dimensions();
    };

    public int leftMostColumnWithOne(BinaryMatrix grid) {
        List<Integer> dim =  grid.dimensions();
        int rows = dim.get(0);
        int cols = dim.get(1);
        int i = 0;
        int j = cols - 1 ;
        while(i < rows && j >= 0){
            if(grid.get(i,j) == 1){
                j--;
            }else{
                i++;
            }
        }
        if(j == cols - 1) return -1;
        return j + 1;
    }

    /* https://leetcode.com/problems/most-frequent-subtree-sum/ */
    public int[] findFrequentTreeSum(TreeNode root) {
        if(root == null) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        helper(root, map);

        TreeMap<Integer, List<Integer>> tmap = new TreeMap<>(Collections.reverseOrder());

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            int sum = entry.getKey();
            int freq = entry.getValue();
            tmap.putIfAbsent(freq, new ArrayList<>());
            tmap.get(freq).add(sum);
        }
        List<Integer> list = tmap.pollFirstEntry().getValue();
        int n = list.size();
        int[] res = new int[n];
        for(int i = 0; i < n; i++){
            res[i] = list.get(i);
        }
        return res;
    }

    public int helper(TreeNode root, HashMap<Integer, Integer> map){
        if(root == null) return 0;
        int left = helper(root.left, map);
        int right = helper(root.right, map);
        int sum = left + right + root.val;
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        return sum;
    }

    /* https://leetcode.com/problems/number-of-distinct-islands/ */
    public int numDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Set<String> sets = new HashSet<>();
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1 && !visited[i][j]){
                    StringBuilder out = new StringBuilder("");
                    dfs(grid, visited, i, j, out);
                    sets.add(out.toString());
                }
            }
        }
        return sets.size();
    }

    public void dfs(int[][] grid, boolean[][] visited, int curr_x, int curr_y, StringBuilder out){
        if(!isValid(grid,curr_x, curr_y)) return;
        if(visited[curr_x][curr_y]) return;
        visited[curr_x][curr_y] = true;

        dfs(grid, visited, curr_x + 1, curr_y, out.append("#"));
        dfs(grid, visited, curr_x - 1, curr_y, out.append("#"));
        dfs(grid, visited, curr_x, curr_y + 1, out.append("$"));
        dfs(grid, visited, curr_x, curr_y -1, out.append("$"));
    }

    public boolean isValid(int[][] grid, int x, int y){
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1){
            return true;
        }
        return false;
    }

    /* https://leetcode.com/problems/restore-the-array/ */
    public int numberOfArrays(String s, int k) {
        Integer[] dp = new Integer[s.length()]; // dp[i] is number of ways to print valid arrays from string s start at i
        return dfs(s, k, 0, dp);
    }
    
    int dfs(String s, long k, int i, Integer[] dp) {
        if (i == s.length()) return 1; // base case -> Found a valid way
        if (s.charAt(i) == '0') return 0; // all numbers are in range [1, k] and there are no leading zeros -> So numbers starting with 0 mean invalid!
        if (dp[i] != null) return dp[i];
        int ans = 0;
        long num = 0;
        for (int j = i; j < s.length(); j++) {
            num = num * 10 + s.charAt(j) - '0'; // num is the value of the substring s[i..j]
            if (num > k) break; // num must be in range [1, k]
            ans += dfs(s, k, j + 1, dp);
            ans %= 1_000_000_007;
        }
        return dp[i] = ans;
    }

    public int numberOfArraysDP(String s, int k) {
       char[] arr = s.toCharArray();
       int n = arr.length;
       long dp[] = new long[n + 1];
       dp[0] = 1;
       for(int i = 0 ; i < arr.length; i++) {
           if(arr[i] == '0') continue;
           long u = 0;
           for(int j = i; j < n; j++) {
              u = u * 10 + (arr[j] - '0');
              if(u >= 1 && u <= k) {
                  dp[j+1] += dp[i];
                  dp[j+1] = dp[j+1] % mod;
              }else{
                  break;
              }
           }
       }
        return (int)dp[n];
    }

    /* https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/ */
    public String getHappyString(int n, int k) {
        char[] set = new char[3];
        set[0] = 'a';
        set[1] = 'b';
        set[2] = 'c';
        helper("", n, set);
        if(res.size() >= k){
            return res.get(k-1);    
        }
        return "";
    }
    
    List<String> res = new ArrayList<>();
    public void helper(String ans, int n, char[] set){
        if(n == 0){
            res.add(ans);
            return;
        }
        
        for(int i = 0; i < set.length; i++){
            if(ans.length() > 0 && 
               set[i] == ans.charAt(ans.length()-1)) continue;
            String curr = ans + set[i];
            helper(curr, n -1, set);
        }
    }

    /* https://leetcode.com/problems/find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k/ 
       Revisit
    */
    public int findMinFibonacciNumbers(int k) {
        TreeSet<Integer> fibTerms = new TreeSet<Integer>(); 
        int first = 0;
        int second = 1;
        fibTerms.add(first);
        fibTerms.add(second);
        
        int nextTerm = 0;
        
        while(nextTerm <= k) { 
            nextTerm = first + second;
            fibTerms.add(nextTerm);
            first = second;
            second = nextTerm;
        }
        
        int minFibNumbers = 0;
        while(k != 0){
            k -= fibTerms.floor(k);
            minFibNumbers++;
        }
        return minFibNumbers;
    }

     /* https://leetcode.com/problems/find-permutation/ */
     public int[] findPermutation(String s) {
        int n = s.length();
        int[]arr = new int[n+1];
        
        for(int i = 0; i <= n; i++){
            arr[i] = i + 1;
        }
        
        for(int i = 0; i < n; i++){
            int low = i;
            int high = i;
            while(high < n && s.charAt(high) == 'D'){
                high++;
            }
            reverse(arr, low, high);
            i = high;
        }
        return arr;
    }

    /* https://leetcode.com/problems/minimum-value-to-get-positive-step-by-step-sum/ */
    public int minStartValue(int[] nums) {
        int sum = 0;
        int minSum = 0;
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];
            minSum = Math.min(minSum, sum);
        }
        return 1 - minSum;
    }
    
    public void reverse(int[] arr, int low, int high){
        while(low < high){
            int temp = arr[high];
            arr[high] = arr[low];
            arr[low] = temp;
            low++;
            high--;
        }
    }

    /* https://leetcode.com/problems/largest-sum-of-averages/
    *  DP problem
    *  Revisit once again
    * */
    public double largestSumOfAverages(int[] arr, int k) {
        int n = arr.length;
        int[] sum = new int[n];

        sum[0] = arr[0];

        for(int i = 1; i < n; i++){
            sum[i] = sum[i-1] + arr[i];
        }
        double[][] memo = new double[n][k+1];
        return largestSumOfAvgMemo(arr, k, sum, 0, n, memo);
        //return largestSumOfAvgRec(arr, k, sum, 0, n);
    }

    public double largestSumOfAvgMemo(int[] arr, int k, int[] sum, int s, int len, double[][] memo){
        if(memo[s][k] != 0) return memo[s][k];
        if(k == 1) {
            double curr = ((double)(sum[len-1] - sum[s] + arr[s]) / (len - s));
            memo[s][k] = curr;
            return curr;
        }

        for(int i = s; i + k <= len; i++ ){
            memo[s][k] = Math.max(memo[s][k], ((double) (sum[i] - sum[s] + arr[s])  / (i - s + 1)) +
                    largestSumOfAvgMemo(arr, k - 1, sum, i + 1, len, memo));
        }
        return memo[s][k];
    }

    public double largestSumOfAvgRec(int[] arr, int k, int[] sum, int s, int len){
        if(k == 1) return ((double)(sum[len-1] - sum[s] + arr[s]) / (len - s));
        double num = 0;
        for(int i = s; i + k <= len; i++ ){
            num = Math.max(num, ((double) (sum[i] - sum[s] + arr[s])  / (i - s + 1)) +
                    largestSumOfAvgRec(arr, k - 1, sum, i + 1, len));
        }
        return num;
    }

    public double largestSumOfAveragesDP(int[] arr, int k) {
        int n = arr.length;
        double[][] memo = new double[n+1][n+1];
        double curr = 0;
        for(int i = 0; i < n; i++){
            curr += arr[i];
            memo[i + 1][1] = curr / (i + 1);
        }
        return helper(memo, arr, n, k);
    }

    public double helper(double[][] memo, int[] arr, int n, int k){
        if(memo[n][k] > 0) return memo[n][k];
        if(n < k ) return 0;
        double curr = 0;
        for(int i = n-1; i > 0; --i){
            curr += arr[i];
            memo[n][k] = Math.max(memo[n][k], helper(memo, arr, i, k-1) + curr / ( n -i));
        }
        return memo[n][k];
    }

    /* https://leetcode.com/problems/build-array-where-you-can-find-the-maximum-exactly-k-comparisons/ */
    int mod = (int)1e9+7;

    public int numOfArrays(int n, int m, int cost) {
        Integer[][][] dp = new Integer[n+1][m+1][cost+1];
        return dfs(n, m, cost, 0, 0, 0, dp);
    }

    public int dfs(int n, int m, int k, int i, int currMax, int currCost, Integer[][][] dp){
        if(n == i) {
            if(k == currCost) return 1;
            return 0;
        }
        if(dp[i][currMax][currCost] != null){
            return dp[i][currMax][currCost];
        }
        int val = 0;
        for(int ni = 1; ni <= m; ni++){
            int newMax = currMax;
            int newCost = currCost;
            if(ni > currMax){
                newCost++;
                newMax = ni;
            }
            if(newCost > k){
                break;
            }
            val += dfs(n, m, k, i + 1, newMax, newCost, dp);
            val %= 1_000_000_007;// mod
        }
        return  dp[i][currMax][currCost] = val;
    }

    /* https://leetcode.com/problems/minimum-number-of-frogs-croaking/submissions/ */
    public int minNumberOfFrogs(String t) {
        int[] cnt = new int[5];
        int frogs = 0;
        int max_frogs = 0;
        for(int i = 0; i < t.length(); i++){
            char ch = t.charAt(i);
            int idx = "croak".indexOf(ch);
            ++cnt[idx];
            if(idx == 0){
                max_frogs = Math.max(max_frogs, ++frogs);
            }else if(--cnt[idx -1] < 0){
                return -1;
            }else if(idx == 4){
                --frogs;
            }
        }
        return frogs == 0 ? max_frogs : -1;
    }

    /*https://leetcode.com/problems/display-table-of-food-orders-in-a-restaurant/ */
    public List<List<String>> displayTable(List<List<String>> orders) {
        TreeMap<Integer, HashMap<String, Integer>> map = new TreeMap<>();
        SortedSet<String> foodname = new TreeSet<>();

        for(List<String> order : orders){
            int tableId = Integer.parseInt(order.get(1));
            String food = order.get(2);
            foodname.add(food);
            map.putIfAbsent(tableId, new HashMap<String,Integer>());
            HashMap<String, Integer> a = map.get(tableId);
            a.put(food, a.getOrDefault(food, 0) + 1);
            map.put(tableId, a);
        }

        List<String> firstRow = new ArrayList<>();
        firstRow.add("Table");
        for(String foodName : foodname){
            firstRow.add(foodName);
        }

        List<List<String>> res = new ArrayList<>();
        res.add(firstRow);

        for(Map.Entry<Integer, HashMap<String, Integer>> entry : map.entrySet()){
            List<String> curr = new ArrayList();

            curr.add(String.valueOf(entry.getKey()));

            HashMap<String, Integer> nested = entry.getValue();
            for(String foodName : foodname){
                if(nested.containsKey(foodName)){
                    int freq = nested.get(foodName);
                    curr.add(String.valueOf(freq));
                }else{
                    curr.add(String.valueOf(0));
                }
            }
            res.add(curr);
        }
        return res;
    }

    /* https://leetcode.com/problems/reformat-the-string/ */
    public String reformat(String s) {
        String a = "", b = "";
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')a += s.charAt(i);
            else b += s.charAt(i);
        }
        if(Math.abs(a.length() - b.length()) > 1) return "";

        int len = Math.min(a.length(), b.length());

        StringBuilder res = new StringBuilder("");
        for(int i = 0; i < len; i++){
            res.append(a.charAt(i));
            res.append(b.charAt(i));
        }

        if(a.length() > b.length()) return res.toString() + a.charAt(len);
        if(a.length() < b.length()) return b.charAt(len) + res.toString();
        return res.toString();
    }

    public int consecutiveNumbersSum(int n) {
        int ways = 0;
        for(int i = 1 ; i <= n; i++ ){
            int j = i + 1;
            int sum = i;
            for (; sum < n; j++) {
                sum += j;
            }
            if(sum == n) ways++;
        }
        return ways;
    }

    /* https://leetcode.com/problems/before-and-after-puzzle/ */
    public List<String> beforeAndAfterPuzzles(String[] phrases) {
        HashMap<String, Set<Integer>> map = new HashMap<>();

        for (int i = 0; i < phrases.length; i++) {
            String head = getHead(phrases[i]);
            map.putIfAbsent(head, new HashSet<>());
            map.get(head).add(i);
        }

        Set<String> res = new HashSet<>();
        for (int i = 0; i < phrases.length; i++) {
            String curr = phrases[i];
            String tail = getTail(curr);
            if(!map.containsKey(tail)){
                continue;
            }
            Set<Integer> list =  map.get(tail);
            for(Integer j : list){
                if(i != j){
                    res.add(phrases[i] + phrases[j].substring(tail.length()));
                }
            }
        }
        List<String> list = new ArrayList<>(res);
        Collections.sort(list);
        return list;
    }

    public String getHead(String word){
        String[] arr = word.split(" ");
        return arr[0];
    }

    public String getTail(String word){
        String[] arr = word.split(" ");
        return arr[arr.length-1];
    }


    class Solution {
        HashMap<Integer,ArrayList<Integer>> freq = new HashMap<>();
        Random rand;
        public Solution(int[] nums) {
            rand = new Random();
            for(int i = 0 ; i < nums.length ; i++){
                freq.putIfAbsent(nums[i], new ArrayList<>());
                freq.get(nums[i]).add(i);
            }
        }
        
        public int pick(int target) {
            ArrayList<Integer> indices = freq.get(target);
            int idx = indices.get(rand.nextInt(indices.size()));
            return idx;
        }
    }



    /* https://leetcode.com/problems/powx-n/ */
    public double myPow(double x, int n) {
        if(n < 0) return 1 / x * myPow(1/x, -(n+1));
        if(n == 0) return 1;
        if(n == 2) return x*x;
        if(n%2 == 0) return myPow(myPow(x, n/2), 2);
        else return x * myPow(myPow(x, n/2), 2);
    }

    public abstract class Relation {
        boolean knows(int a, int b){
            return a == b;
        }
    }
    /* https://leetcode.com/problems/find-the-celebrity/ */
    public class Solution1 extends Relation {
    public int findCelebrity(int n) {
        int[][] graph = new int[n][n];
        
        for(int i = 0; i < n; i++){
           for(int j = 0; j < n; j++){
               graph[i][j] = knows(i, j) ? 1 : 0;
           }
        }
        int[] indegree = new int[n];
        int[] outdegree = new int[n];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i == j) continue;
                 indegree[j] += graph[i][j];
                 outdegree[i] += graph[i][j];
           }
        }
        for(int i = 0; i < n; i++){
            if(indegree[i] == n-1 && outdegree[i] == 0){
                return i;
            }
        }
        return -1;
    }
    
}

    /* https://leetcode.com/problems/find-the-town-judge/ */
    public int findJudge(int n, int[][] trust) {
        
        int[] indegree = new int[n];
        int[] outdegree = new int[n];
        
        for(int i = 0; i < trust.length; i++){
            int a = trust[i][0] - 1;
            int b = trust[i][1] - 1;
             outdegree[a]++;
             indegree[b]++;
        }
        for(int i = 0; i < n; i++){
            if(indegree[i] == n-1 && outdegree[i] == 0){
                return i+1;
            }
        }
        return -1;
    }

    /* https://leetcode.com/problems/valid-parenthesis-string/ */
    public boolean checkValidString(String s) {
        return linear(s);
        //return helper(s, 0, 0);
    }

    public boolean linear(String s){
        int low = 0;
        int high = 0;
        char[] arr = s.toCharArray();
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == ')'){
                low--;
                high--;
            }else if(arr[i] == '('){
                low++;
                high++;
            }else if(arr[i] == '*'){
                high++;
                low--;
            }
            if(high < 0) break;
            low = Math.max(low, 0);
        }
        return low == 0;
    }

    public boolean helper(String s, int count, int start){
        if(count < 0 ) return false;
        
        for(int i = start; i < s.length(); i++) {
            if(s.charAt(i) == '('){
                count++;
            }
            if(s.charAt(i) == ')'){
                if(count <= 0) return false;
                count--;
            }
            if(s.charAt(i) == '*'){
                return helper(s, count + 1, i + 1) || helper(s, count - 1, i + 1) 
                    || helper(s, count, i + 1);
            }
        }
        return count == 0;
    }

    /* https://leetcode.com/problems/interval-list-intersections/
    *  Revisit
    * */
    public int[][] intervalIntersection(int[][] a, int[][] b) {
        ArrayList<int[]> res = new ArrayList<>();
        for(int i = 0, j = 0; i < a.length && j < b.length;){
            int start = Math.max(a[i][0], b[j][0]);
            int end = Math.min(a[i][1], b[j][1]);
            if(start <= end){
                res.add(new int[]{start, end});
            }
            if(a[i][1] < b[j][1]){
                i++;
            }else{
                j++;
            }
        }
        int[][] ans = new int[res.size()][2];
        int i = 0;
        for(int [] curr : res){
            ans[i++] = curr;
        }
        return ans;
    }

}