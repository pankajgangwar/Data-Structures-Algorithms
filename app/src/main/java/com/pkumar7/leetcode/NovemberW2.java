package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;


public class NovemberW2 {
    public static void main(String args[]) {
        String[] words = new String[]{"Pankaj", "Kumar", "Gangwar","Pankaj","Pooja","Pankaj","Pooja"};
        frequentStrings(words);
    }

    /**
     * 648. Replace Words
     * <p>
     * https://leetcode.com/problems/replace-words/
     * <p>
     * Input: dict = ["cat", "bat", "rat"]
     * sentence = "the cattle was rattled by the battery"
     * Output: "the cat was rat by the bat"
     **/

    static class TrieNode {
        boolean isEndOfWord = false;
        int TOTAL_ALPHABETS = 26;
        TrieNode[] children = new TrieNode[TOTAL_ALPHABETS];
        String word;

        public TrieNode() {
            for (int i = 0; i < TOTAL_ALPHABETS; i++) {
                children[i] = null;
            }
        }
    }

    TrieNode root;

    public String replaceWords(List<String> dict, String sentence) {
        root = new TrieNode();
        for (String dictWord : dict) {
            addword(dictWord);
        }

        String[] tokens = sentence.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String token : tokens) {
            builder.append(getShortestReplacement(token));
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public void addword(String word) {
        TrieNode pCrawl = root;
        int length = word.length();
        for (int level = 0; level < length; level++) {
            int idx = word.charAt(level) - 'a';
            if (pCrawl.children[idx] == null) {
                pCrawl.children[idx] = new TrieNode();
            }
            pCrawl = pCrawl.children[idx];
        }
        pCrawl.isEndOfWord = true;
    }

    public String getShortestReplacement(String token) {
        TrieNode pCrawl = root;
        int length = token.length();
        StringBuilder builder = new StringBuilder();
        for (int level = 0; level < length; level++) {
            int idx = token.charAt(level) - 'a';
            builder.append(token.charAt(level));
            if (pCrawl.children[idx] != null) {
                if (pCrawl.children[idx].isEndOfWord) {
                    return builder.toString();
                }
                pCrawl = pCrawl.children[idx];
            } else {
                return token;
            }
        }
        return token;
    }

    /**
     * https://leetcode.com/problems/group-anagrams/
     * 49. Group Anagrams
     * <p>
     * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
     * Output:
     * [
     * ["ate","eat","tea"],
     * ["nat","tan"],
     * ["bat"]
     * ]
     **/
    public List<List<String>> groupAnagrams(String[] strs) {
        return groupAnagramsWithSorting(strs);
    }

    public List<List<String>> groupAnagramsWithCount(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        int[] count = new int[26];
        for (String str : strs) {
            Arrays.fill(count, 0);

            char[] arr = str.toCharArray();
            for (char ch : arr) {
                count[ch - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                builder.append(count[i]);
                builder.append("#");
            }

            String hashKey = builder.toString();

            if (!map.containsKey(hashKey)) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(hashKey, list);
            } else {
                map.get(hashKey).add(str);
            }
        }
        return new ArrayList(map.values());
    }

    public List<List<String>> groupAnagramsWithSorting(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] arr = str.toCharArray();
            Arrays.sort(arr);

            String hashKey = new String(arr);

            if (!map.containsKey(hashKey)) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(hashKey, list);
            } else {
                map.get(hashKey).add(str);
            }
        }
        return new ArrayList<List<String>>(map.values());
    }

    /*
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm
     * https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
     * https://leetcode.com/problems/shuffle-an-array/
     * */
    class Solution {
        int[] org;
        int[] temp;
        public Solution(int[] nums) {
            org = nums.clone();
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return org;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            temp = org.clone();
            int n = temp.length;
            Random r = new Random();
            for (int i = n - 1 ; i >= 0 ; i--) {
                int val = temp[i];
                int rIdx = r.nextInt(i + 1);
                temp[i] = temp[rIdx];
                temp[rIdx] = val;
            }
            return temp;
        }
    }

    /*
    https://leetcode.com/problems/implement-magic-dictionary/
    676. Implement Magic Dictionary
    */
    static class MagicDictionary {

        /**
         * Initialize your data structure here.
         */
        TrieNode root;
        HashMap<String, List<int[]>> map = new HashMap<>();

        public MagicDictionary() {
            root = new TrieNode();
        }

        /**
         * Build a dictionary through a list of words
         */
        public void buildDict(String[] dict) {
            //addWordsToTrie(dict);
            addToHashMap(dict);
        }

        public void addToHashMap(String[] dict) {
            for (String word : dict) {
                for (int i = 0; i < word.length(); i++) {
                    String key = word.substring(0, i) + word.substring(i + 1);
                    List<int[]> list = map.getOrDefault(key, new ArrayList<int[]>());
                    int[] arr = new int[]{i, word.charAt(i)};
                    list.add(arr);
                    map.put(key, list);
                }
            }
        }

        public void addWordsToTrie(String[] dict) {
            for (String word : dict) {
                TrieNode pCrawl = root;
                int length = word.length();
                for (int level = 0; level < length; level++) {
                    int idx = word.charAt(level) - 'a';
                    if (pCrawl.children[idx] == null) {
                        pCrawl.children[idx] = new TrieNode();
                    }
                    pCrawl = pCrawl.children[idx];
                }
                pCrawl.isEndOfWord = true;
            }
        }

        /**
         * Returns if there is any word in the trie that equals to the given word after modifying exactly one character
         * Input: buildDict(["hello", "leetcode"]), Output: Null
         * Input: search("hello"), Output: False
         * Input: search("hhllo"), Output: True
         * Input: search("hell"), Output: False
         * Input: search("leetcoded"), Output: False
         */
        public boolean search(String word) {
            return searchWithHashMap(word);
            // return searchWithTrie(word);
            // return dfs(root, word, 0, false);
        }

        public boolean searchWithHashMap(String word) {
            int length = word.length();
            for (int i = 0; i < length; i++) {
                String key = word.substring(0, i) + word.substring(i + 1);
                if (map.containsKey(key)) {
                    List<int[]> res = map.get(key);
                    for (int[] arr : res) {
                        if (arr[0] == i && arr[1] != word.charAt(i)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public boolean searchWithTrie(String word) {
            char[] arr = word.toCharArray();
            for (int i = 0; i < word.length(); i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == arr[i]) continue;
                    char oldCh = arr[i];
                    arr[i] = ch;
                    if (helper(new String(arr), root)) {
                        return true;
                    }
                    arr[i] = oldCh;
                }
            }
            return false;
        }

        public boolean helper(String word, TrieNode root) {
            TrieNode pCrawl = root;
            int length = word.length();
            for (int level = 0; level < length; level++) {
                int idx = word.charAt(level) - 'a';
                if (pCrawl.children[idx] == null) {
                    return false;
                }
                pCrawl = pCrawl.children[idx];
            }
            return pCrawl.isEndOfWord;
        }

        public boolean dfs(TrieNode root, String word, int idx, boolean used) {
            if (root == null) return false;
            if (idx == word.length()) return root.isEndOfWord && used;
            char ch = word.charAt(idx);

            for (int i = 0; i < 26; i++) {
                if (used && (ch - 'a') != i) continue;
                if (dfs(root.children[i], word, idx + 1, used || (ch - 'a') != i)) return true;
            }
            return false;
        }
    }


    /* 
    https://leetcode.com/problems/longest-word-in-dictionary/ 
    720. Longest Word in Dictionary

    words = ["w","wo","wor","worl", "world"]
    Output: "world"

    Input: 
    words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
    Output: "apple"

     */
    public String longestWord(String[] words) {
        //return usingTrieDS(words);
        return usingSets(words);
    }

    public String usingSets(String[] words) {
        Arrays.sort(words);
        String result = "";
        Set<String> sets = new HashSet<String>();
        for (int i = 0; i < words.length; i++) {
            String curr = words[i];
            if (!sets.contains(curr) && sets.contains(curr.substring(0, curr.length() - 1))) {
                result = result.length() > curr.length() ? result : curr;
                sets.add(curr);
            }
        }
        return result;
    }

    public String usingTrieDS(String[] words) {
        root = new TrieNode();
        for (String word : words) {
            buildTrie(word);
        }
        String longestString = bfsSearch();
        return longestString;
    }

    public void buildTrie(String word) {
        TrieNode pCrawl = root;
        int length = word.length();

        for (int level = 0; level < length; level++) {
            int idx = word.charAt(level) - 'a';
            if (pCrawl.children[idx] == null) {
                pCrawl.children[idx] = new TrieNode();
            }
            pCrawl = pCrawl.children[idx];
        }
        pCrawl.isEndOfWord = true;
        pCrawl.word = word;
    }

    public String bfsSearch() {
        Queue<TrieNode> mQueue = new LinkedList<>();
        mQueue.offer(root);
        String result = "";
        while (!mQueue.isEmpty()) {
            int size = mQueue.size();
            while (size-- > 0) {
                TrieNode pCrawl = mQueue.poll();
                for (int idx = 25; idx >= 0; idx--) {
                    if (pCrawl.children[idx] != null && pCrawl.children[idx].isEndOfWord) {
                        result = pCrawl.children[idx].word;
                        mQueue.offer(pCrawl.children[idx]);
                    }
                }
            }
        }
        return result;
    }

    

    public int oddCells(int n, int m, int[][] indices) {
        int[][] matrix = new int[n][m];

        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], 0);
        }

        for (int[] idx : indices) {
            int row = idx[0];
            int col = idx[1];

            incrementMatrix(matrix, row, col, n, m);
        }

        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] %2 != 0){
                    res++;
                }
            }
        }
        System.out.println(res);
        return res;
    }

    public void incrementMatrix(int[][] matrix, int row, int col, int n, int m) {

        for (int j = 0; j < m; j++) {
            matrix[row][j]++;
        }

        for (int j = 0; j < n; j++) {
            matrix[j][col]++;
        }
    }

    /**
        https://leetcode.com/problems/number-of-closed-islands/
     */
    public int closedIsland(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(i == 0 || i == grid.length -1 || j == 0 | j == grid[i].length -1){
                    if(grid[i][j] == 0){
                        fill(grid, i, j);
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                res += fill(grid, i, j) > 0 ? 1 : 0;
            }
        }
        return res;
    }

    private int fill(int[][] grid, int i, int j) {
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0){
            return 0;
        }
        return (grid[i][j] = 1) + fill(grid, i + 1, j) + fill(grid, i -1, j)
                + fill(grid, i, j+1) + fill(grid, i, j-1);
    }


    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int[] count = new int[score.length];

        for (char ch: letters ) {
            count[ch - 'a']++;
        }

        return backTracking(words, count, score, 0);
    }

    public int backTracking(String[] words, int[] count, int[] score, int idx){
        int max = 0;
        for(int i = idx; i < words.length; i++){
            char []arr = words[i].toCharArray();
            int res = 0;
            boolean isValid = true;
            for(char ch : arr){
                count[ch - 'a']--;
                res+= score[ch - 'a'];
                
                if (count[ch - 'a'] < 0) isValid = false;
            }

            if(isValid){
                res += backTracking(words, count, score, i + 1);
                max = Math.max(res, max);
            }

            for(char ch : arr){
                count[ch - 'a']++;
                res = 0;
            }            

            
        }
        return max;
    }

    static class CharFreq {
        char ch;
        int freq;
        public CharFreq(char ch, int CharFreq){
            this.ch = ch;
            this.freq = CharFreq;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            for(int n: nums){
                map.put(n, map.getOrDefault(n,0)+1);
            }

            TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
            for(int num : map.keySet()){
                int freq = map.get(num);
                if(!freqMap.containsKey(freq)){
                    freqMap.put(freq, new LinkedList<>());
                }
                freqMap.get(freq).add(num);
            }

            List<Integer> res = new ArrayList<>();
            while(res.size()<k){
                Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
                res.addAll(entry.getValue());
            }
            return res;
        }

    public static char getKthLargestChar(String str, int k) {

        Map<Character, Integer> freqMap = new HashMap<>();

        PriorityQueue<CharFreq> mMinHeap = new PriorityQueue<>(new Comparator<CharFreq>() {

            @Override
            public int compare(CharFreq ch1, CharFreq ch2){
                if(ch1.freq > ch2.freq){
                    return 1;
                } else if(ch1.freq < ch2.freq){
                    return -1;
                }
                return 0;
            }
        });

        for(char ch : str.toCharArray()){
            if(freqMap.get(ch) == null){
                freqMap.put(ch,1);
            }else{
                int oldFreq = freqMap.get(ch);
                freqMap.put(ch, oldFreq+1);
            }
        }

        for(Character ch : freqMap.keySet()){
            CharFreq chFreq = new CharFreq(ch, freqMap.get(ch));
            mMinHeap.offer(chFreq);

            while(mMinHeap.size() >= k){
                CharFreq removed = mMinHeap.poll();
                System.out.println("char removed " + removed.ch + " its freq " + removed.freq);
            }
        }
        char res = '-';
        while(!mMinHeap.isEmpty()){
            CharFreq ch = mMinHeap.poll();
            System.out.println(" ch now " + ch.ch + " freq " + ch.freq);
            res = ch.ch;
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/surrounded-regions/
     **/

  public void solve(char[][] board) {
        for(int i = 0; i < board.length; i++){
            for(int j =0 ; j < board[i].length; j++){
                if(i == 0 || i == board.length -1 || j == 0 || j == board[i].length -1){
                    if(board[i][j] == 'O'){
                        fill(board, i, j);
                    }
                }
            }
        }

        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[i].length; ++j){
                if(board[i][j] == '*') 
                    board[i][j] = 'O';
                else
                    board[i][j] = 'X';
            }
        }
    }
    
    public void fill(char[][] board, int x, int y){
        if(x < 0 || x >= board.length || y < 0 || y >= board[x].length){
            return;
        }
        if(board[x][y] == 'X' || board[x][y] == '*') return;

        board[x][y] = '*';

        if(x+1 < board.length)
            fill(board, x + 1, y);
        if(x-1 > 0)
            fill(board, x - 1, y);
        if(y+1 < board[x].length)
            fill(board, x, y + 1);
        if(y-1 > 0)
            fill(board, x, y - 1);
    }

    
    /*
    https://leetcode.com/problems/game-of-life/
     */
    public void gameOfLife(int[][] board) {
        int[][] clonedBoard = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                clonedBoard[i][j] = board[i][j];
            }
        }

        int[][] dir = new int[][]{{0, 1}, {1, 1}, {1, 0}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (clonedBoard[i][j] == 1) {
                    int liveNeighbors = 0;
                    for (int k = 0; k < dir.length; k++) {
                        int[] next_coord = dir[k];
                        int next_x = next_coord[0] + i;
                        int next_y = next_coord[1] + j;

                        if (isSafe(board, next_x, next_y) && clonedBoard[next_x][next_y] == 1) {
                            liveNeighbors++;
                        }
                    }
                    if (liveNeighbors < 2) { // Under population
                        board[i][j] = 0;
                    } else if (liveNeighbors > 3) { // Over population
                        board[i][j] = 0;
                    }
                } else if (clonedBoard[i][j] == 0) {
                    int liveNeighbors = 0;
                    for (int k = 0; k < dir.length; k++) {
                        int[] next_coord = dir[k];
                        int next_x = next_coord[0] + i;
                        int next_y = next_coord[1] + j;

                        if (isSafe(board, next_x, next_y) && clonedBoard[next_x][next_y] == 1) {
                            liveNeighbors++;
                        }
                    }
                    if (liveNeighbors == 3) { // Dead cell becomes live
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

    public boolean isSafe(int[][] board, int x, int y){
        if(x >= 0 && y >= 0 && x < board.length && y < board[x].length)
            return true;

        return false;
    }


    /**
     https://leetcode.com/problems/longest-valid-parentheses/

     32. Longest Valid Parentheses

     Input: "(()"
     Output: 2
     Explanation: The longest valid parentheses substring is "()"

     Input: ")()())"
     Output: 4
     Explanation: The longest valid parentheses substring is "()()"

     ()()((((())))))

     */
    public int longestValidParentheses(String s) {
        Stack<Integer> mStack = new Stack<Integer>();
        int n = s.length();
        int longestValid = 0;
        char[] arr = s.toCharArray();
        for(int i = 0; i < n; ++i){
            if(arr[i] == '('){
                mStack.push(i);
            }else{
                if(!mStack.isEmpty()){
                    if(arr[mStack.peek()] == '(')
                        mStack.pop();
                    else
                        mStack.push(i);
                }else{
                    mStack.push(i);
                }
            }
        }

        if(mStack.isEmpty()) return n;

        int a = n, b = 0;
        while(!mStack.isEmpty()){
            b = mStack.pop();
            longestValid = Math.max(longestValid, a - b -1);
            a = b;
        }
        longestValid = Math.max(longestValid, a );

        return longestValid;
    }

    /* 494. Target Sum
      https://leetcode.com/problems/target-sum/

      Input: nums is [1, 1, 1, 1, 1], S is 3.
      Output: 5
      Explanation: 

        -1+1+1+1+1 = 3
        +1-1+1+1+1 = 3
        +1+1-1+1+1 = 3
        +1+1+1-1+1 = 3
        +1+1+1+1-1 = 3

        There are 5 ways to assign symbols to make the sum of nums be target 3.

     */

    public int findTargetSumWays(int[] nums, int S) {
        int[][] memo = new int[nums.length][2001];
        for(int i = 0; i < nums.length; i++){
            Arrays.fill(memo[i], Integer.MIN_VALUE);
        }
        return findSumWaysMemo(nums, S, 0, 0, memo);
        //return findSumWays(nums, S, 0);
    }

    public int findSumWaysMemo(int[] nums, int S, int actualSum, int idx, int[][] memo){
        if(idx == nums.length){
            if(S == actualSum){
                return 1;
            }else{
                return 0;
            }
        }

        if(memo[idx][actualSum + 1000] != Integer.MIN_VALUE){
            return memo[idx][actualSum + 1000];
        }

        int sum = findSumWaysMemo(nums, S + nums[idx], actualSum,idx + 1, memo) +
                findSumWaysMemo(nums, S - nums[idx], actualSum,idx + 1, memo);
        memo[idx][actualSum + 1000] = sum;
        return memo[idx][actualSum + 1000];
    }

    int ways = 0;
    public void findSumWaysRecAnother(int[] nums, int S, int idx, int actualSum){
        if(idx == nums.length){
            if(S == actualSum){
                ways++;
            }
            return;
        }
        findSumWaysRecAnother(nums, S, idx + 1, actualSum + nums[idx]);
        findSumWaysRecAnother(nums, S, idx + 1, actualSum - nums[idx]);
    }

    public int findSumWaysRec(int[] nums, int S, int idx){
        if(idx == nums.length){
            if(S == 0){
                return 1;
            }else{
                return 0;
            }
        }
        return findSumWaysRec(nums, S + nums[idx], idx + 1) +
               findSumWaysRec(nums, S - nums[idx], idx + 1);
    }

    /**
    560. Subarray Sum Equals K
    https://leetcode.com/problems/subarray-sum-equals-k/

    Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

    Example 1:
    Input:nums = [1,1,1], k = 2
    Output: 2

    */
    public int subarraySum(int[] nums, int k) {
        //subArraySumRec(nums, k, 0, 0);
        //return bruteForce(nums, k);
        return subArrayPrefixSum(nums, k);
    }

    public int bruteForce(int[] nums, int target){
        int count = 0;
        for(int i = 0; i < nums.length; ++i){
            for(int j = i + 1; j < nums.length; ++j){
                int sum = 0;
                for(int k = i; k < j; ++k){
                    sum+= nums[k];
                }
                if(sum == target){
                    count++;
                }
            }
        }
        return count;
    }


    public int subArrayPrefixSum(int[] nums, int target){
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        
        int sum = 0;
        int count = 0;
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];
            if(prefixSumMap.containsKey(sum - target)){
                count += prefixSumMap.get(sum - target);
            }
            prefixSumMap.put(sum, prefixSumMap.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    /* 1221. Split a String in Balanced Strings
     https://leetcode.com/problems/split-a-string-in-balanced-strings/

     Input: s = "RLRRLLRLRL"
     Output: 4

    */
    public int balancedStringSplitStack(String s) {
        int result = 0;
        Stack<Character> mStack = new Stack<>();

        for(int i = 0; i < s.length(); ++i){
            if(mStack.isEmpty()){
                mStack.push(s.charAt(i));
            }else{
                char curr = mStack.peek();
                if(s.charAt(i) != curr){
                    mStack.pop();
                    if(mStack.isEmpty()){
                        result++;
                    }
                }else{
                    mStack.push(s.charAt(i));
                }
            }
        }
        return result;
    }

    public int balanceStringAnother(String s){
        int result = 0;
        int count = 0;
        for(int i = 0; i < s.length(); ++i){
            result += s.charAt(i) == 'L' ? 1 : -1;
            if(result == 0){
                ++count;
            }
        }
        return count;
    }

    /*  110. Balanced Binary Tree
        https://leetcode.com/problems/balanced-binary-tree/
    */

    public boolean isBalanced(TreeNode root) {
        return isBalancedRec(root);
    }

    public boolean checkBalancedFromHeight(TreeNode root){
        if(root == null) return true;
        int height = getHeight(root);
        return height != -1;
    }

    public int depth(TreeNode root){
        if(root == null)
            return 0;

        int left = depth(root.left);
        int right = depth(root.right);

        return 1 + Math.max(left, right);
    }

    public boolean isBalancedRec(TreeNode root){
        if(root == null)
            return true;

        int left = depth(root.left);
        int right = depth(root.right);

        return Math.abs(left - right) <= 1 && isBalancedRec(root.left) && isBalancedRec(root.right);
    }

    public int getHeight(TreeNode node){
        if(node == null){
            return 0;
        }

        int left = getHeight(node.left);
        if(left == -1)
            return -1;
        
        int right = getHeight(node.right);
        if(right == -1)
            return -1;

        if(left - right < -1 || left - right > 1)
            return -1;
        
        return 1 + Math.max(left, right);
    }

    
    /* ["Pankaj", "Kumar","Pankaj","Gangwar"]*/
     public static void frequentStrings(String[] words){

        Map<String,Integer> map = new HashMap<>();

        for (String word : words){
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        TreeMap<Integer, List<String>> treeMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());

        for(String word : map.keySet()){
            int freq = map.get(word);
            if(treeMap.containsKey(freq)){
                List<String> list = treeMap.get(freq);
                list.add(word);
            }else{
                List<String> list = new ArrayList<>();
                list.add(word);
                treeMap.put(freq, list);
            }
        }

        for(int freq : treeMap.keySet()){
            List<String> list = treeMap.get(freq);
            Collections.sort(list);
            for (String word : list){
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

   
    /*
    https://leetcode.com/problems/4sum/
    **/
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList();
        if(nums == null || nums.length == 0) return result;

        Arrays.sort(nums);
        
        getSumBackTrack(nums, 0, 0, new ArrayList<>(), result, target);
        
        return result;
    }

    public void getSumBackTrack(int[] nums, int sum, int pos, List<Integer> list, List<List<Integer>> result, int target){
        if(list.size() == 4 && sum == target && !result.contains(list)){
            result.add(new ArrayList<>(list));
            return;
        }
        if(list.size() == 4){
            return;
        }
        for(int i = pos; i < nums.length; i++){
             //current number with its biggest possible combination is still less than target, then we should pass to next number;
            if(nums[i] + nums[nums.length -1] * (3 - list.size()) + sum < target) continue;
            //current number with its smallest possible combination is still larger than target, then we should stop here.
            if(nums[i] * (4 - list.size()) + sum > target) return;
            list.add(nums[i]);
            getSumBackTrack(nums, sum + nums[i], i + 1, list, result, target);
            list.remove(list.size() - 1);
        }
    }

   /*
      https://leetcode.com/problems/two-sum-iv-input-is-a-bst/
   */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> sets = new HashSet<Integer>();
        //return findPair(root, k, sets);
        return usingBst(root, k);
    }
    
    public boolean findPair(TreeNode root, int k, Set<Integer> set){
        if(root == null){
            return false;
        }
        if(set.contains(k - root.val)){
            return true;
        }
        set.add(root.val);
        return findPair(root.left, k, set) || findPair(root.right, k, set);
    }
    
    List<Integer> list = new ArrayList<>();
    public boolean usingBst(TreeNode root, int target){
        inorder(root);
        int l = 0;
        int r = list.size() - 1;
        while(l < r){
            if(list.get(l) + list.get(r) == target){
                return true;
            }else if(list.get(l) + list.get(r) < target){
                l++;
            }else{
                r--;
            }
        }
        return false;
    }
    
    public void inorder(TreeNode root){
        if(root == null){
            return;
        }
        list.add(root.val);
        inorder(root.left);
        inorder(root.right);
    }

    /*
    https://leetcode.com/problems/longest-palindromic-substring/
    Input: "babad"
    Output: "bab"
    Note: "aba" is also a valid answer

    Input: "cbbd"
    Output: "bb"

    */
    public String longestPalindrome(String s) {
        //return longestPalindromeBruteForce(s);
        return longestPalindromeAnother(s);
    }

    int lowest;
    int maxlength = 0;
    public String longestPalindromeAnother(String s){
        if(s.length() < 2){
            return s;
        }

        for(int i = 0; i < s.length(); ++i){
            extendPalindrome(s, i, i);
            extendPalindrome(s, i, i+1);
        }

        return s.substring(lowest, lowest + maxlength);
    }

    public void extendPalindrome(String s, int low, int high){
        while(low >= 0 && high < s.length() && s.charAt(low) == s.charAt(high)){
            --low;
            ++high;
        }

        if(maxlength < high - low - 1){
            lowest = low + 1;
            maxlength = high - low - 1;
        }
    }

    public String longestPalindromeBruteForce(String s){
        if(s.length() == 1 || s.length() == 0){
            return s;
        }
        
        String longestPalindrome = "";

        char[] arr = s.toCharArray();
        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){
                String subStr = s.substring(i, j + 1);
                if(isPalindrome(subStr)){
                    if(subStr.length() > longestPalindrome.length()){
                        longestPalindrome = subStr;
                    }
                }
            }
        }
        if(longestPalindrome.length() == 0){
            return "" + s.charAt(0);
        }
        return longestPalindrome;
    }

    public boolean isPalindrome(String s){
        int i = 0; 
        int j = s.length() -1;

        while(i < j){
            if(s.charAt(i) == s.charAt(j)){
                ++i;
                --j;
            }else{
                return false;
            }
        }
        return true;
    }

     
}