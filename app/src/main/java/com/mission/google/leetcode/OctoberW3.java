package com.mission.google.leetcode;

import com.mission.google.algorithms.string.BoyerMooreAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class OctoberW3 extends BoyerMooreAlgorithm {


    /**
     * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
     * 581. Shortest Unsorted Continuous Subarray
     *
     * Input: [2, 6, 4, 8, 10, 9, 15]
     * Output: 5
     * Explanation: You need to sort [6, 4, 8, 10, 9] in
     * ascending order to make the whole array sorted in ascending order.
     *
     * */
    public int findUnsortedSubarray(int[] nums) {
        int[] cloneNums = nums.clone();
        Arrays.sort(cloneNums);

        int left = cloneNums.length;
        int right = 0;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != cloneNums[i]){
                left = Math.min(left, i);
                right = Math.max(right, i);
            }
        }

        System.out.println(right + " : " + left);

        return right - left >= 0 ? right - left + 1 : 0 ;
    }

    /***
     * 454. 4Sum II
     * https://leetcode.com/problems/4sum-ii/
     */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> first = new HashMap<>();

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                first.put(A[i] + B[j], first.getOrDefault(A[i] + B[j], 0) + 1);
            }
        }

        int count = 0;

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                count+= first.getOrDefault(- C[i] - D[j], 0);
            }
        }
        return count;
    }


    /**
     * https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
     */
    public int solution(int[] A, int K, int L) {

        for (int i = 1; i < A.length; ++i)
            A[i] += A[i - 1];
        if(L + K -1 >= A.length){
            return -1;
        }
        int res = A[L + K - 1], Lmax = A[L - 1], Mmax = A[K - 1];
        for (int i = L + K; i < A.length; ++i) {
            Lmax = Math.max(Lmax, A[i - K] - A[i - L - K]);
            Mmax = Math.max(Mmax, A[i - L] - A[i - L - K]);
            res = Math.max(res, Math.max(Lmax + A[i] - A[i - K], Mmax + A[i] - A[i - L]));
        }
        return res;
    }

    /**
     * https://leetcode.com/contest/weekly-contest-159/problems/check-if-it-is-a-straight-line/
     * */
    public boolean checkStraightLine(int[][] coordinates) {
        for (int i = 0; i < coordinates.length - 2; i++) {
            int[] first = coordinates[i];
            int[] second = coordinates[i+1];
            int[] third = coordinates[i+2];

            if(!collinear(first[0], first[1], second[0], second[1], third[0], third[1])){
                return false;
            }
        }
        return true;
    }

    public boolean collinear(int x1, int y1, int x2,
                          int y2, int x3, int y3) {


        int a = x1 * (y2 - y3) +
                x2 * (y3 - y1) +
                x3 * (y1 - y2);

        return a == 0;
    }

    /**
     * https://leetcode.com/contest/weekly-contest-159/problems/remove-sub-folders-from-the-filesystem/
     *
     * Input: folder = ["/a","/a/b/c","/a/b/d"]
     * Output: ["/a"]
     * Explanation: Folders "/a/b/c" and "/a/b/d/" will be removed because they are subfolders of "/a".
     *
     * */
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder,Comparator.comparing(s -> s.length()));
        Set<String> seen = new HashSet<>();

        outer:
        for (String s : folder) {
            for (int i = 2; i < s.length(); ++i) {
                if(s.charAt(i) == '/' && seen.contains(s.substring(0,i))){
                    continue outer;
                }
            }
            seen.add(s);
        }
        return new ArrayList<>(seen);
    }

    /**
     * Sliding Window
     * Input: s = "QWER"
     * Output: 0
     * Explanation: s is already balanced
     *
     * Input: s = "QQWE"
     * Output: 1
     * Explanation: We need to replace a 'Q' to 'R', so that "RQWE" (or "QRWE") is balanced.
     *
     * https://leetcode.com/problems/replace-the-substring-for-balanced-string/
     *
     * To-Do
     *
     * */
    public int balancedString(String s) {
        int count[] = new int [128];
        for (char ch : s.toCharArray()) {
            count[ch]++;
        }

        int i = 0;
        int n = s.length();
        int res = n;
        int need = n / 4;
        for (int j = 0; j < n; j++) {
            --count[s.charAt(j)];
            while(i < n && count['Q'] <= need && count['W'] <= need && count['E'] <= need && count['R'] <= need){
                res = Math.min(res, j - i + 1);
                ++count[s.charAt(i++)];
            }
        }
        return res;
    }

    /**
     * To-Do
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
     * */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int [][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]);
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0,0);

        for (int[] job : jobs) {
            int cur = dp.floorEntry(job[0]).getValue() + job[2];
            if(cur > dp.lastEntry().getValue()){
                dp.put(job[1], cur);
            }
        }
        return dp.lastEntry().getValue();
    }

    /** 127. Word Ladder
     * https://leetcode.com/problems/word-ladder/
     * **/
    class WordNode {
        String word;
        int stepCount;

        public WordNode(String currWord, int numSteps){
            word = currWord;
            stepCount = numSteps;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        LinkedList<WordNode> queue = new LinkedList<WordNode>();
        Set<String> wordDict = new HashSet<String>(wordList);

        wordDict.add(endWord);

        queue.offer(new WordNode(beginWord, 1));

        while(!queue.isEmpty()){
            WordNode top = queue.poll();
            String word = top.word;

            if(word.equals(endWord)){
                return top.stepCount;
            }
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++ ) {
                for (char ch = 'a'; ch <= 'z' ; ch++ ) {
                    char temp = arr[i];
                    if(arr[i] != ch){
                        arr[i] = ch;
                    }

                    String newWord = new String(arr);
                    if(wordDict.contains(newWord)){
                        queue.offer(new WordNode(newWord, top.stepCount+1));
                        wordDict.remove(newWord);
                    }

                    arr[i] = temp;
                }
            }
        }

        return 0;
    }

    /** 433. Minimum Genetic Mutation
     * https://leetcode.com/problems/minimum-genetic-mutation/
     * **/
    public int minMutation(String start, String end, String[] bank) {
        char[] charSet = new char[]{'A','C','G','T'};
        Set<String> visited = new HashSet<>();
        Set<String> wordDict = new HashSet<String>();
        for(String s : bank){
            wordDict.add(s);
        }

        Queue<String> queue = new LinkedList<String>();
        int level = 0;

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0){
                String topWord = queue.poll();
                if (topWord.equals(end)) {
                    return level;
                }
                char[] arr = topWord.toCharArray();

                for (int i = 0; i < arr.length; i++) {
                    for (char ch : charSet) {
                        char old = arr[i];
                        arr[i] = ch;

                        String newWord = new String(arr);

                        if (!visited.contains(newWord) && wordDict.contains(newWord)) {
                            queue.offer(newWord);
                            visited.add(newWord);
                        }

                        arr[i] = old;
                    }
                }
            }
            level++;
        }
        return -1;

    }

    /***
     126. Word Ladder II

     https://leetcode.com/problems/word-ladder-ii/

     Input:
     beginWord = "hit",
     endWord = "cog",
     wordList = ["hot","dot","dog","lot","log","cog"]

     Output:
     [
     ["hit","hot","dot","dog","cog"],
     ["hit","hot","lot","log","cog"]
     ]

     ***/
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        Map<String, ArrayList<String>> neighbours = new HashMap<String, ArrayList<String>>();
        List<List<String>> res = new ArrayList<List<String>>();
        ArrayList<String> solution = new ArrayList<String>();
        Map<String, Integer> distance = new HashMap<String,Integer>();
        dict.add(beginWord);

        bfs(beginWord, endWord, neighbours, distance, dict);
        dfs(beginWord, endWord, neighbours, solution, res, dict, distance);
        return res;
    }

    public void bfs(String start, String end, Map<String, ArrayList<String>> nodeNeighbours,
                    Map<String,Integer> distance, Set<String> dict){

        for(String str: dict){
            nodeNeighbours.put(str, new ArrayList<String>());
        }

        Queue<String> queue = new LinkedList<String>();
        distance.put(start, 0);

        queue.offer(start);

        while(!queue.isEmpty()){
            int size = queue.size();
            boolean foundEnd = false;
            while(size-- > 0){
                String cur = queue.poll();
                int currDistance = distance.get(cur);
                ArrayList<String> neighbours = getNeighbours(cur, dict);

                for (String neighbour : neighbours) {
                    nodeNeighbours.get(cur).add(neighbour);

                    if(!distance.containsKey(neighbour)){
                        distance.put(neighbour, currDistance + 1);
                        if(neighbour.equals(end)){
                            foundEnd = true;
                        }else{
                            queue.offer(neighbour);
                        }
                    }
                }
            }

            if(foundEnd){
                break;
            }
        }

    }

    public ArrayList<String> getNeighbours(String cur, Set<String> dict){
        char arr[] = cur.toCharArray();

        ArrayList<String> res = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                char old = arr[i];
                if(old == ch) continue;

                arr[i] = ch;

                String newWord = new String(arr);
                if(dict.contains(newWord)){
                    res.add(newWord);
                }

                arr[i] = old;
            }
        }
        return res;
    }


    public void dfs(String currentWord, String endWord,
                    Map<String, ArrayList<String>> neighbours, ArrayList<String> solution,List<List<String>> res,
                    Set<String> dict, Map<String, Integer> distance){

        solution.add(currentWord);
        if(currentWord.equals(endWord)){
            res.add(new ArrayList<>(solution));
        }else{
            for(String next : neighbours.get(currentWord)){
                if(distance.get(next) == distance.get(currentWord) + 1){
                    dfs(next, endWord, neighbours, solution, res, dict, distance);
                }
            }
        }
        solution.remove(solution.size() - 1);
    }

    /***
     https://leetcode.com/problems/queue-reconstruction-by-height/

     Input:
     [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

     Output:
     [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

     ***/
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (p1,p2) -> p1[0] == p2[0] ? p1[1] - p2[1] : p2[0] - p1[0]);
        List<int[]> res = new LinkedList<>();

        for (int[]  curr : people) {
            res.add(curr[1], curr);
        }
        return res.toArray(new int[res.size()][]);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            ArrayList<Integer> indices = map.getOrDefault(nums[i], new ArrayList<>());
            if(!indices.isEmpty()){
                for (int prev_idx : indices) {
                    if(i - prev_idx <= k){
                        return true;
                    }
                }
            }
            indices.add(i);
            map.put(nums[i], indices);
        }
        return false;
    }

    public static void main(String[] args) {
        OctoberW3 w3 = new OctoberW3();
        int[][] grid = new int[][]{{1,2} ,{ 5,6}, {1,1}};

        w3.minPathSum(grid);

    }
    /***
     * LC : 64
     * https://leetcode.com/problems/minimum-path-sum/
     ***/

    public int minPathSum(int[][] grid) {
        /*int[][] memo = new int[grid.length + 1][grid[0].length + 1];
        for (int i = 0; i < grid.length ; i++ ) {
            Arrays.fill(memo[i], -1);
        }
        return minimumSumRec(grid, 0, 0, memo);*/

        return minPathSumDp(grid);
    }

    public int minPathSumDp(int[][] grid){
        int row = grid.length;
        int col = grid[0].length;

        int dp[][] = new int[row+1][col+1];

        for(int i = 1; i <= row; i++){
            for(int j = 1; j <= col; j++){
                if(i == 1) {
                    dp[i][j] = dp[i][j-1] + grid[i-1][j-1];
                } else if(j == 1){
                    dp[i][j] = dp[i-1][j] + grid[i-1][j-1];
                }else {
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i-1][j-1];
                }
            }
        }
        return dp[row][col];
    }


    public int minimumSumRec(int[][] grid, int row, int col, int[][] memo){

        if(row == grid.length -1 && col == grid[0].length -1){
            return grid[row][col];
        }
        if(memo[row][col] != -1){
            return memo[row][col];
        }

        int rowInc = Integer.MAX_VALUE;
        int colInc = Integer.MAX_VALUE;

        if(row < grid.length -1){
            rowInc = minimumSumRec(grid, row + 1, col, memo);
        }

        if(col < grid[0].length -1){
            colInc = minimumSumRec(grid, row, col + 1, memo);
        }

        memo[row][col] = Math.min(rowInc , colInc) + grid[row][col];

        return memo[row][col];
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        for (int i = 0; i < nums.length; i++) {

        }
        return false;
    }

    /**
     * 295. Find Median from Data Stream
     https://leetcode.com/problems/find-median-from-data-stream/

     addNum(1)
     addNum(2)
     findMedian() -> 1.5
     addNum(3)
     findMedian() -> 2

     **/
    public class MedianFinder {

        /** initialize your data structure here. */
        PriorityQueue<Integer> highers;
        PriorityQueue<Integer> lowers;
        public MedianFinder() {
            int size = 2;
            highers = new PriorityQueue<>(size);
            lowers = new PriorityQueue<>(size, Collections.reverseOrder());
        }

        public void addNum(int num) {
            if(lowers.size() == 0 || num < lowers.peek()){
                lowers.add(num);
            }else {
                highers.add(num);
            }

            PriorityQueue<Integer> smaller = lowers.size() < highers.size() ? lowers : highers;
            PriorityQueue<Integer> bigger = highers.size() > lowers.size() ? highers : lowers;
            if(bigger.size() - smaller.size() >= 2){
                smaller.add(bigger.poll());
            }

        }

        public double findMedian() {
            PriorityQueue<Integer> smaller = lowers.size() < highers.size() ? lowers : highers;
            PriorityQueue<Integer> bigger = highers.size() > lowers.size() ? highers : lowers;

            if(smaller.size() == bigger.size()){
                int min = lowers.peek();
                int max = highers.peek();
                return (double) (min + max) / 2;
            }else {
                return (double) bigger.peek();
            }
        }
    }

}
