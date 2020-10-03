package com.pkumar7.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class AugustW2 {

    public static void main(String args[]){
        //kSmallestPairs(new int[]{1,1,2}, new int[]{1,2,3},4);

        //searchRange(new int[]{1,2,3}, 2);
       // numRollsToTarget(30,30,500);
        AugustW2 w2 = new AugustW2();
        w2.findWords(new String[]{"Alaska"});

    }

    /****
     * 304. Range Sum Query 2D - Immutable
     * https://leetcode.com/problems/range-sum-query-2d-immutable/
     */
    int[][] matrix;
    private int[][] dp;
    public void NumMatrix(int[][] matrix){
        if(matrix.length == 0){
            return;
        }
        this.matrix = matrix;
        dp = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] + matrix[i - 1][j - 1] - dp[i - 1][j - 1];
            }
        }

    }
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if(matrix.length == 0){
            return 0;
        }
        row1++;
        col1++;
        row2++;
        col2++;

        return dp[row2][col2] - dp[row1 - 1][col2] - dp[row2][col1 - 1] + dp[row1 - 1][col1 - 1];
    }

    /**
     * 599. Minimum Index Sum of Two Lists
     * */
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> mMap = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            mMap.put(list1[i],i);
        }

        int leastIndexSum = Integer.MAX_VALUE;
        List<String> res = new ArrayList<>();
        for (int i = 0; i < list2.length; i++) {
            Integer j = mMap.get(list2[i]);
            if(j != null && i + j <= leastIndexSum) {
                if(i+j < leastIndexSum){
                    res.clear();
                    leastIndexSum = i + j;
                }
                res.add(list2[i]);
            }
        }
        return res.toArray(new String[res.size()]);
    }

    /** 404. Sum of Left Leaves
     * https://leetcode.com/problems/sum-of-left-leaves/
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
     *
     * */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int sumOfLeftLeaves(TreeNode root) {
       /* if(root == null){
            return 0;
        }
        int maxUniValLength = 0;
        if(root.left != null){
            if(root.left.left == null && root.left.right == null)maxUniValLength+= root.left.val;
            maxUniValLength += sumOfLeftLeaves(root.left);
        }
        maxUniValLength += sumOfLeftLeaves(root.right);
        return maxUniValLength;*/
        Queue<TreeNode> mQueue = new LinkedList<>();
        if(root == null || root.left == null && root.right == null){
            return 0;
        }
        mQueue.offer(root);
        int res = 0;
        while (!mQueue.isEmpty()){
            TreeNode curr = mQueue.poll();
            if(curr.left != null &&
                    curr.left.left == null && curr.left.right == null){
                res += curr.left.val;
            }
            if(curr.left != null)
                mQueue.offer(curr.left);

            if(curr.right != null)
                mQueue.offer(curr.right);

        }
        return res;
    }

    /**
     * 718. Maximum Length of Repeated Subarray
     * https://www.hackerrank.com/challenges/dynamic-programming-classics-the-longest-common-subsequence/problem
     * https://leetcode.com/problems/maximum-length-of-repeated-subarray/
     * Common subsequence of contigous subarray
     */
    public int findLength(int[] A, int[] B) {
        if(A.length == 0 || B.length == 0)
            return 0;

        return longestCommonSubsequence(A,B);
    }

    public int longestCommonSubsequence(int[] A, int[] B){
        int m = A.length;
        int n = B.length;

        int[][] lcs = new int[m + 1][n + 1];
        int length = 0;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n ; j++) {
                if(i == 0 || j == 0)
                    lcs[i][j] = 0;
                else {
                    if (A[i -1] == B[j - 1]){
                        lcs[i][j] = lcs[i - 1][j - 1] + 1;
                        length = Math.max(length, lcs[i][j]);
                    }
                }
            }
        }
        return length;
    }

    /**
     * 985. Sum of Even Numbers After Queries
     *https://leetcode.com/problems/sum-of-even-numbers-after-queries/
     *
     * Input: A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
     * Output: [8,6,2,4]
     * Explanation:
     * At the beginning, the array is [1,2,3,4].
     * After adding 1 to A[0], the array is [2,2,3,4], and the sum of even values is 2 + 2 + 4 = 8.
     * After adding -3 to A[1], the array is [2,-1,3,4], and the sum of even values is 2 + 4 = 6.
     * After adding -4 to A[0], the array is [-2,-1,3,4], and the sum of even values is -2 + 4 = 2.
     * After adding 2 to A[3], the array is [-2,-1,3,6], and the sum of even values is -2 + 6 = 4.
     */

    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int[] res = new int[queries.length];

        int j = 0;
        for (int[] query : queries) {
            int eleToUpdate = A[query[1]];
            eleToUpdate += query[0];
            A[query[1]] = eleToUpdate;

            int sum = 0;
            for (int i = 0; i < A.length; i++) {
                if(Math.abs(A[i]) %2 == 0){
                    sum += A[i];
                }
            }
            res[j] = sum;
            j++;
        }
        return res;
    }

    /**
     * 34. Find First and Last Position of Element in Sorted Array
     *
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
     *
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     *
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     * */
    public static int[] searchRange(int[] nums, int target) {
        int first_idx = Arrays.binarySearch(nums, target);
        int second_idx = -1;
        if(first_idx > 0){
            second_idx = Arrays.binarySearch(nums, first_idx+1, nums.length-1, target);
        }else{
            first_idx = -1;
        }
        if(second_idx < 0){
            second_idx = -1;
        }
        return new int[]{first_idx, second_idx};
        /*int i = 0;
        int j = nums.length -1;
        if(nums.length == 1){
            if(target == nums[0]){
                return new int[]{0,0};
            }
        }

        boolean firstFound = false, secondFound = false;
        while(i <= j){
            if(nums[i] == target){
                firstFound = true;
            }else{
                i++;
            }

            if(nums[j] == target){
                secondFound = true;
            }else{
                j--;
            }

            if(firstFound && secondFound){
                break;
            }
        }

        if(!firstFound  && !secondFound){
            i = -1;
            j = -1;
        }else if(firstFound && !secondFound){
            j = i;
        }else if(secondFound && !firstFound){
            i = j;
        }

        System.out.println(i + "," + j);
        return new int[]{i, j};*/
    }

    /**
     * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/
     * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation
     * 373. Find K Pairs with Smallest Sums
     *
     * Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     Output: [[1,2],[1,4],[1,6]]
     Explanation: The first 3 pairs are returned from the sequence:
     * **/
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

        PriorityQueue<int[]> mMinHeap = new PriorityQueue<>((a, b)->a[0]+a[1]-b[0]-b[1]);

        List<List<Integer>> res = new ArrayList<>(k);

        if(nums1.length==0 || nums2.length==0 || k==0) return res;

        for (int i = 0; i < nums1.length && i < k; i++) {
            mMinHeap.offer(new int[]{nums1[i], nums2[0], 0});
        }

        while (k-- > 0 && !mMinHeap.isEmpty()){
            int[] curr = mMinHeap.poll();
            List<Integer> curr_pair = new ArrayList<>();
            curr_pair.add(curr[0]);
            curr_pair.add(curr[1]);
            res.add(curr_pair);

            if(curr[2] == nums2.length -1)continue;
            mMinHeap.offer(new int[]{curr[0], nums2[curr[2]+1], curr[2]+1});
        }
        return res;
    }

    /**
     * 520. Detect Capital
     * https://leetcode.com/problems/detect-capital/
     *
     * Input: "USA"
     * Output: True
     *
     * Input: "FlaG"
     * Output: False
     *
     * */
    public boolean detectCapitalUse(String word) {
        return word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]+");

        /*boolean isAllCaps = true;
        for (int i = 0; i < word.length(); i++) {
            if(!Character.isUpperCase(word.charAt(i))){
                isAllCaps = false;
            }
        }
        if(isAllCaps){
            return isAllCaps;
        }
        boolean onlyFirstCaps = Character.isUpperCase(word.charAt(0));
        for (int i = 1; i < word.length() && onlyFirstCaps; i++) {
            if(onlyFirstCaps && !Character.isLowerCase(word.charAt(i))) {
                onlyFirstCaps = false;
                break;
            }
        }
        if(onlyFirstCaps){
            return true;
        }

        boolean isAllLower = true;
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLowerCase(word.charAt(i))){
                isAllLower = false;
            }
        }

        if(isAllLower){
            return true;
        }
        return false;*/

    }

    /**
     * 102. Binary Tree Level Order Traversal
     * https://leetcode.com/problems/binary-tree-level-order-traversal/
     * **/
    public List<List<Integer>> levelOrder(TreeNode root) {
        int height = getHeight(root);
        List<List<Integer>> nodesList = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            List<Integer> nodes_list = new ArrayList<>();
            printNodesAtLevel(root, i, nodes_list);
            nodesList.add(nodes_list);
            System.out.println();
        }
        return nodesList;
    }

    public void printNodesAtLevel(TreeNode root, int level, List<Integer> nodes_list){
        if(root == null){
            return;
        }
        if(level == 1){
            nodes_list.add(root.val);
        }else if(level > 1){
            printNodesAtLevel(root.left, level -1, nodes_list);
            printNodesAtLevel(root.right, level -1, nodes_list);
        }
    }

    public int getHeight(TreeNode root){
        if(root == null){
            return 0;
        }
        int lHeight = 1 + getHeight(root.left);
        int rHeight = 1 + getHeight(root.right);
        return Math.max(lHeight, rHeight);
    }

    /**
     * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     *    [
     *   [15,7],
     *   [9,20],
     *   [3]
     * ]
     *
     * **/
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        int height = getHeight(root);
        List<List<Integer>> nodesList = new ArrayList<>();
        for (int i = height; i >= 1; i--) {
            List<Integer> nodes_list = new ArrayList<>();
            printNodesAtLevel(root, i, nodes_list);
            nodesList.add(nodes_list);
            System.out.println();
        }
        return nodesList;
    }

    /**
     * https://leetcode.com/problems/average-of-levels-in-binary-tree/
     * 637. Average of Levels in Binary Tree
     *
     * Input:
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * Output: [3, 14.5, 11]
     * Explanation:
     * The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
     *
     * */
    public List<Double> averageOfLevels(TreeNode root) {
        int height = getHeight(root);
        List<Double> nodesList = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            List<Integer> nodes_list = new ArrayList<>();
            printNodesAtLevel(root, i, nodes_list);
            double sum = 0;
            for (int j = 0; j < nodes_list.size(); j++) {
                sum += nodes_list.get(j);
            }
           /* DecimalFormat df = new DecimalFormat("0.00");
            float avg = sum / nodes_list.size();
            Double number = Double.valueOf(df.format(avg));*/
            nodesList.add(sum / nodes_list.size());
        }
        return nodesList;
    }

    /**
     * 485. Max Consecutive Ones
     * https://leetcode.com/problems/max-consecutive-ones/
     *
     * Input: [1,1,0,1,1,1]
     * Output: 3
     * Explanation: The first two digits or the last three digits are consecutive 1s.
     * The maximum number of consecutive 1s is 3.
     *
     * **/
    public int findMaxConsecutiveOnes(int[] nums) {
        int i = -1;
        int j = nums.length;
        int max = 0;
        while(i <= j){
            int current = 0;
            i++;
            j--;
            while (nums[i] == 1) {
                current++;
                i++;
            }
            max = Math.max(max, current);

            current = 0;
            while (nums[j] == 1){
                current++;
                j--;
            }
            max = Math.max(max, current);
        }
        return max;
    }

    /**
     * 1004. Max Consecutive Ones III
     * https://leetcode.com/problems/max-consecutive-ones-iii/
     *
     * Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * Output: 6
     * Explanation:
     * [1,1,1,0,0,1,1,1,1,1,1]
     * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
     *
     ***/
    public int longestOnes(int[] A, int K) {
        int start = 0;
        int res = 0;
        int zeroCount = 0;

        for (int i = 0; i < A.length; i++) {
            if(A[i] == 0) zeroCount++;
            while (zeroCount > K){
                if(A[start] == 0) zeroCount--;
                start++;
            }
            res = Math.max(res, i - start+1);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/fair-candy-swap/
     *
     * 888. Fair Candy Swap
     *
     * Input: A = [1,2,5], B = [2,4]
     * Output: [5,4]
     *
     * */
   /* public int[] fairCandySwap(int[] A, int[] B) {
        for (int i = 0; i < A.length; i++) {

        }
    }*/

    /**
     * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
     *
     * Input:
     * 30 - d
     * 30 - f
     * 500 - x
     * Expected: 222616187
     *
     * */
    public int numRollsToTarget(int d, int f, int target) {
        return findWays(f, d, target);
    }

    public static int findWays(int m, int n, int x){
        int mod = 1000000007;
        //int modulo = 1_000_000_000 + 7;
        int[][] table = new int[n+1][x+1];

        for(int j = 1; j <= m && j <= x; j++)
            table[1][j] = 1;

        for(int i = 2; i <= n;i ++){
            for(int j = 1; j <= x; j++){
                for(int k = 1; k < j && k <= m; k++){
                    table[i][j] += table[i-1][j-k];
                    if(table[i][j] > mod) table[i][j] = table[i][j] % mod;
                }
            }
        }
        return table[n][x];
    }

    /**
     * Input: date = "2003-03-01"
     * Output: 60
     *
     * Input: date = "2019-01-09"
     * Output: 9
     * Explanation: Given date is the 9th day of the year in 2019.
     *
     * */
    public static int dayOfTheYear(){
        String date = "2019-01-09";
        String[] dateArr = date.split("-");
        GregorianCalendar gc = new GregorianCalendar();
        System.out.println(Integer.parseInt(dateArr[0]) + "-" + Integer.parseInt(dateArr[1]) + "-" + Integer.parseInt(dateArr[2]));
        gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(dateArr[2]));
        gc.set(GregorianCalendar.MONTH, Integer.parseInt(dateArr[1])-1);
        gc.set(GregorianCalendar.YEAR, Integer.parseInt(dateArr[0]));
        System.out.println(gc.get(GregorianCalendar.DAY_OF_YEAR));
        return gc.get(GregorianCalendar.DAY_OF_YEAR);
    }


    /**
     * https://www.geeksforgeeks.org/edit-distance-dp-5/
     *
     * 72. Edit Distance
     * https://leetcode.com/problems/edit-distance/
     * */
    int max = 1000;
    int[][] memo;
    public int minDistance(String word1, String word2) {
        memo = new int[word1.length()][max];
        for (int i = 0; i < word1.length(); i++) {
            Arrays.fill(memo[i], -1);
        }

        return editDistanceMemo(word1, word2, word1.length(), word2.length(), memo);
        //return editDistance(word1, word2, word1.length(), word2.length());
    }

    public int editDistanceMemo(String str1, String str2, int m, int n, int[][] memo){
        if(m == 0)
            return n;

        if(n == 0)
            return m;

        if(memo[m-1][n-1] != -1){
            return memo[m-1][n-1];
        }
        if(str1.charAt(m-1) == str2.charAt(n-1)){
            return memo[m-1][n-1] = editDistanceMemo(str1, str2, m-1, n-1, memo);
        }

        return memo[m-1][n-1] = 1 + min_op(editDistanceMemo(str1, str2, m, n-1, memo),//Insert
                editDistanceMemo(str1, str2, m-1, n, memo), //Remove
                editDistanceMemo(str1, str2, m-1, n-1, memo)); //Replace
    }

    public int editDistance(String str1, String str2, int m, int n){
        if(m == 0) return n;//If srt2 is empty, insert all chars to str2
        if(n == 0) return m;// If str1 is empty, remove all chars from str1

        if(str1.charAt(m-1) == str2.charAt(n-1)){
            return editDistance(str1, str2, m-1, n-1);
        }
        return 1 + min_op(editDistance(str1, str2, m, n-1),
                editDistance(str1, str2, m-1, n),
                editDistance(str1, str2, m-1, n-1));
    }

    // x = 4, y = 2, z = 6
    public int min_op(int x, int y, int z){
       int min_a = Math.min(x,y);
       return Math.min(min_a,z);
    }

    /**
     * https://leetcode.com/problems/largest-number/
     * 179. Largest Number
     *
     * Input: [3,30,34,5,9]
     * Output: "9534330"
     *
     */
    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> (b+a).compareTo(a+b));

        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            builder.append(str);
        }

        while (builder.charAt(0) == '0' && builder.length() > 1)
            builder.deleteCharAt(0);

        return builder.toString();
    }

    /**
     * https://leetcode.com/problems/find-the-town-judge/
     * Input: N = 3, trust = [[1,3],[2,3]]
     * Output: 3
     *
     */
    static class People {
        int identity;

        int incoming_edges = 0;
        int outgoing_edges = 0;

        public People(int identity){
            this.identity = identity;
        }

        List<People> all_known = new ArrayList<>();

        public void knows(People second){
            all_known.add(second);
            second.incoming_edges++;
            outgoing_edges++;
        }
    }

    static class TownGraph {
        List<Integer> all_pop = new ArrayList<>();
        public void addEdge(People first, People second){
            first.knows(second);

            if(!all_pop.contains(first.identity))
                all_pop.add(first.identity);
        }
    }

    public int findJudge(int N, int[][] trust) {
        TownGraph townGraph = new TownGraph();

        if(trust.length == 0 && N == 1){
            return 1;
        }

        Map<Integer, People> mMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int first = trust[i][0];
            int second = trust[i][1];

            People first_per, sec_per;

            if(mMap.containsKey(first)){
                first_per = mMap.get(first);
            }else{
                first_per = new People(first);
                mMap.put(first, first_per);
            }

            if(mMap.containsKey(second)){
                sec_per = mMap.get(second);
            }else{
                sec_per = new People(second);
                mMap.put(second, sec_per);
            }

            townGraph.addEdge(first_per, sec_per);
        }

        for (int i = 1; i <= N; i++) {
            People curr = mMap.get(i);
            if(curr != null && curr.incoming_edges == N-1){
                if(curr.outgoing_edges == 0 ){
                   return curr.identity;
                }
            }
        }

        return -1;
    }

    /**
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/
     * */

    public Map<Integer, Set<Integer>> buildGraph(int N, int[][] paths){
        Map<Integer, Set<Integer>> mMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            mMap.put(i, new HashSet<>());
        }

        for (int i = 0; i < paths.length; i++) {
            int first = paths[i][0];
            int second = paths[i][1];
            mMap.get(first -1).add(second -1);
            mMap.get(second -1).add(first -1);
        }
        return mMap;
    }

    /** 1,2,3,4
     * a --> b
     * a --> c
     * a --> d
     * */
    public int[] gardenNoAdj(int N, int[][] paths) {
        Map<Integer, Set<Integer>> mMap = buildGraph(N, paths);

        int []res = new int[N];
        for (int garden = 0; garden < N; garden++) {

            int [] colors = new int[5];

            for (int neighbor : mMap.get(garden)) {
                colors[res[neighbor]] = 1;
            }

            for (int c = 4; c > 0 ; --c) {
                if(colors[c] == 0) {
                    res[garden] = c;
                }
            }
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/keyboard-row/
     *
     * Input: ["Hello", "Alaska", "Dad", "Peace"]
     * Output: ["Alaska", "Dad"]
     *
     * */
    public String[] findWords(String[] words) {

        String str1 = "qwertyuiop";
        String str2 = "asdfghjkl";
        String str3 = "zxcvbnm";

        List<String> res = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            String curr_word = words[i];
            curr_word = curr_word.toLowerCase();
            Set<Character> set = new HashSet<>();
            for (int j = 0; j < curr_word.length(); j++) {
                set.add(curr_word.charAt(j));
            }

            Iterator<Character> iterator = set.iterator();
            boolean to_be_added = true;
            while (iterator.hasNext()){
                Character ch = iterator.next();
                if(str1.indexOf(ch) >= 0)continue;
                else {
                    to_be_added = false;
                    break;
                }
            }
            if(to_be_added){
                res.add(words[i]);
                continue;
            }
            Iterator<Character> iterator1 = set.iterator();
            if(!to_be_added){
                to_be_added = true;
                while (iterator1.hasNext()){
                    Character ch = iterator1.next();
                    if(str2.indexOf(ch) >= 0)continue;
                    else {
                        to_be_added = false;
                        break;
                    }
                }
            }
            if(to_be_added){
                res.add(words[i]);
                continue;
            }
            Iterator<Character> iterator2 = set.iterator();
            if(!to_be_added){
                to_be_added = true;
                while (iterator2.hasNext()){
                    Character ch = iterator2.next();
                    if(str3.indexOf(ch) >= 0)continue;
                    else {
                        to_be_added = false;
                        break;
                    }
                }
            }
            if(to_be_added){
                res.add(words[i]);
            }
        }

        return res.toArray(new String[res.size()]);
    }

}
