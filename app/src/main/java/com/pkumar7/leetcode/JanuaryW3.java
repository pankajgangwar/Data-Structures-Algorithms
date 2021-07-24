package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class JanuaryW3 {

    public static void main(String[] args) {
        int INF = 2147483647;
        int[][] rooms = new int[][]{ 
        { INF , -1 , 0 , INF }, 
        { INF ,INF, INF,  -1 }, 
        { INF , -1 ,INF , -1 },
        { 0 , -1 ,INF ,INF }
        };

        JanuaryW3 w3 = new JanuaryW3();
        //w2.wallsAndGates(rooms);

        //w2.fractionToDecimal(1,2);
        //w2.jump(new int[]{1,1,3,1,1,4});
        //w2.countBinarySubstrings("00100");
        //w2.minFlips(2, 6, 5);
        //w3.longestPalindromeSubseq("pcpdpc");
        //w3.longestCommonSubsequence("ace", "abcde");
        //w3.minDistance("sea","eat");


        //System.out.println("digits = " + digits);
        List<String> result = new ArrayList<>();
        result.add("cat");
        result.add("cats");
        result.add("and");
        result.add("sand");
        result.add("dog");

        //w3.wordBreak("catsanddog", result);
        //w3.generatePalindromes("aaaab");
        //w3.printVertically("TO BE OR NOT TO BE");
        int[][] clips = new int[][]{
                {0,2},{4,6},{8,10},{1,9},{1,5},{5,9}
        };
       // w3.videoStitching(clips, 10);
        //w3.getFactors(4);
        //w3.str2tree("4(2(3)(1))(6(5))");
        w3.compareVersion("","");

    }

    /* https://leetcode.com/problems/one-edit-distance/ 
        s = "ab", t = "acb"
        s = "akacb", t = "akab"
        s = "1203", t = "1213"
    */
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        for(int i = 0 ; i < Math.min(m,n); i++){
            if(s.charAt(i) != t.charAt(i)){
                 if(m > n){ //delete
                     return t.substring(i).equals(s.substring(i + 1));
                 }else if(m < n){//Insert
                     return s.substring(i).equals(t.substring(i + 1));
                 }else{
                     return s.substring(i + 1).equals(t.substring(i + 1));
                 }
            }
        }
        return Math.abs(s.length() - t.length()) == 1;
    }

    /* https://leetcode.com/problems/longest-chunked-palindrome-decomposition/

    Input: text = "ghiabcdefhelloadamhelloabcdefghi"
    Output: 7
    Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"

    Rolling hash
    */
    long BASE = 29L, MOD = 1000000007L;
    public int longestDecompositionRollingHash(String str) {
        int n = str.length();
        long[] pow = new long[n + 1];
        long[] hash = new long[n + 1];
        pow[0] = 1;

        HashSet<Long> set = new HashSet<>();
        
        for(int i = 1; i <= n; ++i){
            hash[i] = (hash[i-1] * BASE + str.charAt(i - 1)) % MOD;
            pow[i] = pow[i-1] * BASE % MOD;
        }

        for(int i = 0; i < n; i++){
            for(int len = 2; i + len <= n; len+= 2){
                int mid = i + len / 2;
                long hash1 = getHash(i, mid, hash, pow);
                long hash2 = getHash(mid, i + len, hash, pow);
                if(hash1 == hash2){
                    set.add(hash1);
                }
            }
        }
        return set.size();
    }

    public long getHash(int l, int r, long[] hash, long[] pow ){
        return (hash[r] - hash[l] * pow[r - l] % MOD + MOD) % MOD;
    }

    public int longestDecomposition(String str) {
        int i = 0;
        int j = str.length() - 1;
        int n = str.length();
        int end = n;
        int start = 0;

        int chunks = 0;
        while(i < j){
            String left = str.substring(start, i+1);
            String right = str.substring(j, end);

            if(left.equals(right)){
                chunks+= 2;
                start = i + 1;
                end = j;
            }
            i++;
            j--;
        }
        if(start < end){
            chunks++;
        }
        return chunks;
    }

    /* https://leetcode.com/problems/palindromic-substrings/ 
       madam, aaa
    */
    public int countSubstrings(String s) {
        return countSubstringsBetter(s);
    }

    public int countSubstringsBetter(String s){
        if(s.length() == 0) return 0;

        int count = 0;

        for(int i = 0; i < s.length(); i++){
            count += countPalindrome(s, i, i);
            count += countPalindrome(s, i, i + 1);
        }
        return count;
    }

    public int countPalindrome(String s, int low, int high){
        int n = s.length();
        int count = 0;
        while(low >=0 && high < n && s.charAt(low) == s.charAt(high)){
            count++;
            low--;
            high++;
        }
        return count;
    }

    public int countSubstringsNaive(String s){
        int n = s.length();
        int count = 0;
        for(int i = 0; i <= n; i++){
            for(int j = i+1; j <= n; j++){
                String sub = s.substring(i, j);
                if(isPalindrome(sub)){
                    count++;
                }
            }    
        }
        return count;
    }
    
    public boolean isPalindrome(String s){
        int low = 0;
        int high = s.length() -1;
        while(low <= high){
            if(s.charAt(low) != s.charAt(high)) {
                return false;
            }
            low++;
            --high;
        }
        return true;
    }

    /*  https://leetcode.com/problems/longest-palindromic-subsequence/ 
        "bbbab" -> 4
        "madakm" -> 5
    */
    public int longestPalindromeSubseq(String s) {
        //helperPalindrome(s, 0, new StringBuilder());
        //return maxLength;
        //return helperWithPointers(0, s.length() -1, s);
        int n = s.length();
        int[][] memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        //return helperWithPointersMemo(0, n - 1, s, memo);
        return longestPalindromeSubseqDP(s);
    }

    public int longestPalindromeSubseqDP(String s) {
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
        return dp[0][n - 1];
    }

    public int helperWithPointersMemo(int l, int r, String s, int[][] memo) {
        if (l == r) return 1;
        if (l > r) return 0;
        if (memo[l][r] != -1) return memo[l][r];
        if (s.charAt(l) == s.charAt(r)) {
            int max = 2 + helperWithPointersMemo(l + 1, r - 1, s, memo);
            memo[l][r] = max;
            return max;
        } else {
            int max = Math.max(helperWithPointersMemo(l + 1, r, s, memo), helperWithPointersMemo(l, r - 1, s, memo));
            memo[l][r] = max;
            return max;
        }
    }

    public int helperWithPointers(int l, int r, String s) {
        if (l == r) return 1;
        if (l > r) return 0;
        if (s.charAt(l) == s.charAt(r)) {
            return 2 + helperWithPointers(l + 1, r - 1, s);
        } else {
            return Math.max(helperWithPointers(l + 1, r, s), helperWithPointers(l, r - 1, s));
        }
    }


    int maxLength = Integer.MIN_VALUE;
    public void helperPalindrome(String s, int idx, StringBuilder helper){
        int n = s.length();
        if(idx == n){
            return;
        }
        if(isPalindrome(helper.toString())){
            maxLength = Math.max(maxLength, helper.toString().length());
        }
        for(int i = idx; i < s.length(); i++){
            helper.append(s.charAt(i));
            helperPalindrome(s, i + 1, helper);
            helper.deleteCharAt(helper.length()-1);
        }
    }

    /*
    https://leetcode.com/problems/longest-common-subsequence/
    */
    public int longestCommonSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();
        
        int[][] memo = new int[n][m];
        for(int i = 0 ; i < n; i++ ){
            Arrays.fill(memo[i], -1);
        }
        //return helperCommonSubSeqMemo(s, t, 0, 0, memo);
        //return helperCommonSubSeq(s, t, 0, 0);

        int[][] dp = new int[n+1][m+1];
        return helperCommonSubSeqDP(s, t, dp);
    }

    public int helperCommonSubSeqDP(String s, String t, int[][] dp){
        int n = s.length();
        int m = t.length();

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[n][m];
    }

    public int helperCommonSubSeqMemo(String s, String t, int i, int j, int[][] memo){
        int n = s.length();
        int m = t.length();

        if(i == n || j == m){
            return 0;
        }
        
        if(memo[i][j] != -1){
            return memo[i][j];
        }
        
        if(s.charAt(i) == t.charAt(j)){
            int currMax = 1 + helperCommonSubSeqMemo(s, t, i + 1, j + 1, memo);
            memo[i][j] = currMax;
            return memo[i][j];
        }else{
            int currMax = Math.max(helperCommonSubSeqMemo(s, t, i + 1, j, memo), helperCommonSubSeqMemo(s, t, i, j + 1, memo));
            memo[i][j] = currMax;
            return memo[i][j];
        }
    }
    
    public int helperCommonSubSeq(String s, String t, int i, int j){
        int n = s.length();
        int m = t.length();
        if(i == n || j == m){
            return 0;
        }
        
        if(s.charAt(i) == t.charAt(j)){
            return 1 + helperCommonSubSeq(s, t, i + 1, j + 1);
        }else{
            return Math.max(helperCommonSubSeq(s, t, i + 1, j), helperCommonSubSeq(s, t, i, j + 1));
        }
    }

    /* https://leetcode.com/problems/delete-operation-for-two-strings/ */
    public int minDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
        
        //return minDistanceRec(s, t, 0, 0);
        int[][] memo = new int[n][m];
        for(int i = 0; i < n; i++){
            Arrays.fill(memo[i], -1);
        }
        //return minDistanceMemo(s, t, 0, 0, memo);
        int [][]dp = new int[n+1][m+1];
        for(int i = 0; i <= n; i++){
            dp[i][0] = i;
        }

        for(int j = 0; j <= m; j++){
            dp[0][j] = j;
        }

        return minDistanceDP(s, t, dp);
    }
    
    public int minDistanceDP(String s, String t, int[][] dp){
        int n = s.length();
        int m = t.length();
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.min(Math.min(dp[i-1][j-1] + 2, dp[i-1][j] + 1), dp[i][j-1] + 1);
                }
            }
        }
        return dp[n][m];
    }
    
    public int minDistanceMemo(String s, String t, int i, int j, int[][] memo) {
        int n = s.length();
        int m = t.length();
        if(i == n){
            return m - j;
        }
        if(j == m){
            return n - i;
        }
        if(memo[i][j] != -1){
            return memo[i][j];
        }
        if(s.charAt(i) == t.charAt(j)){
            memo[i][j] = minDistanceMemo(s, t, i + 1, j + 1, memo);
        }else{
            memo[i][j] =  1 + Math.min(minDistanceMemo(s, t, i + 1, j , memo), minDistanceMemo(s, t, i, j + 1, memo) );
        }
        return memo[i][j];
    }
    
    public int minDistanceRec(String s, String t, int i, int j) {
        int n = s.length();
        int m = t.length();
        
        if(i == n){
            return m - j;
        }
        if(j == m){
            return n - i;
        }
        if(s.charAt(i) == t.charAt(j)){
            return minDistanceRec(s, t, i + 1, j + 1);
        }else{
            return 1 + Math.min(minDistanceRec(s, t, i + 1, j ), minDistanceRec(s, t, i, j + 1) );
        }
    }

    /* https://leetcode.com/problems/rotate-string/ */
    public boolean rotateString(String A, String B) {
        return A.length() == B.length() && (A+A).contains(B);
    }

    /* https://leetcode.com/problems/minimum-moves-to-equal-array-elements/ 
       Revisit
    */
    public int minMoves(int[] nums) {
        //Find max at each stage then increment by 1 all elements apart from max
        int max = 0;
        for(int i = 0; i < nums.length; i++){
            if(max < nums[i]){
                max = nums[i];
            }
        }

        int moves = 0;
        for(int i = 0; i < nums.length; i++){
            if(max != nums[i]){
                moves+= max - nums[i];
            }
        }
        return moves;
    }

    /* https://leetcode.com/problems/campus-bikes/ */
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        int n  = workers.length;
        PriorityQueue<int[]> q = new PriorityQueue<>( (a, b) -> {
            Integer comp = Integer.compare(a[0], b[0]);
            if(comp == 0){
                if(a[1] == b[1]){
                    return Integer.compare(a[2], b[2]);
                }
                return Integer.compare(a[1], b[1]);
            }
            return comp;
        });

        for(int i = 0; i < workers.length; i++ ){
            int[] curr_w = workers[i];
            for(int j = 0; j < bikes.length; j++ ){
                int[] curr_bike = bikes[j];
                int dist = Math.abs(curr_w[0] - curr_bike[0]) + Math.abs(curr_w[1] - curr_bike[1]);
                q.offer(new int[]{ dist, i , j }); // Dist, worker, bike
            }
        }

        int[] res = new int[n];

        Arrays.fill(res, -1);

        HashSet<Integer> bikesAssigned = new HashSet<>();
        while(!q.isEmpty() && bikesAssigned.size() < n){
            int[] curr = q.poll();

            if(bikesAssigned.contains(curr[2]) && res[curr[1]] == -1){
                res[curr[1]] = curr[2];
            }
        }

        return res;
    }

    /* https://leetcode.com/problems/word-break-ii/ 
        Input:
        s = "catsanddog"
        wordDict = ["cat", "cats", "and", "sand", "dog"]
        Output:
        [
          "cats and dog",
          "cat sand dog"
        ]

    */
    public List<String> wordBreak(String s, List<String> wordDict) {
        HashSet<String> dict = new HashSet<>(wordDict);

        //wordBreakRec(s, "", dict, 0);
        return wordBreakMemo(s, dict, new HashMap<String, List<String>>());
        //return result;
    }


    public List<String> wordBreakMemo(String source,HashSet<String> dict, HashMap<String, List<String>> map) {
        int n = source.length();
        if(map.containsKey(source)){
            return map.get(source);
        }
        List<String> result = new ArrayList<>();

        if(n == 0){
            result.add("");
            return result;
        }


        for(String str : dict) {
            if(source.startsWith(str)){
                List<String> sub_list = wordBreakMemo(source.substring(str.length()), dict, map);
                for(String stored : sub_list){
                    result.add(str + (stored.isEmpty() ? "" : " " ) + stored);
                }
            }
        }
        map.put(source, result);
        return result;
    }

    List<String> result = new ArrayList<>();

    public void wordBreakRec(String s, String res, HashSet<String> dict, int start) {
        int n = s.length();
        if(n == 0){
            result.add(res);
            return;
        }
        for(int end = start + 1; end <= n; end++){
            String sub = s.substring(start, end);
            if(dict.contains(sub)){
                wordBreakRec(s.substring(end), res.isEmpty() ? sub : res + " " + sub, dict, 0);
            }
        }
    }

    /* https://leetcode.com/problems/subdomain-visit-count/ */
    public List<String> subdomainVisits(String[] cpdomains) {
        HashMap<String, Integer> map = new HashMap<>();
        
        for(int i = 0; i < cpdomains.length; i++){
            
            int count = Integer.valueOf(cpdomains[i].split(" ")[0]);
            String curr = cpdomains[i].split(" ")[1];
            
            //System.out.println(count + " " + curr);
            
            int n = curr.length();
            int start = 0;
            int end = 0;
            
            map.put(curr, map.getOrDefault(curr, 0) + count);
            
            int first_idx = curr.indexOf('.');
            
            String rem = curr.substring(first_idx + 1);
            map.put(rem, map.getOrDefault(rem, 0) + count);
            
            int last_idx = curr.lastIndexOf('.');
            if(last_idx == first_idx) continue;
            
            String rem_sec = curr.substring(last_idx + 1);
            map.put(rem_sec, map.getOrDefault(rem_sec, 0) + count);
            
            
            /*while(end < n){
                while(end < n && curr.charAt(end) != '.'){
                    //System.out.println(curr.charAt(end));
                    end++;
                }
                
                if(end < n){
                    String rem = curr.substring(end + 1);
                    map.put(rem, map.getOrDefault(rem, 0) + count);
                }
                    
                start = end;
                end++;
            }*/
        }
        
        List<String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            
            result.add(value + " " + key);
        }
        return result;
    }

    /* https://leetcode.com/problems/palindrome-permutation-ii/
    *  Permutations of half of the string
    * */
    public List<String> generatePalindromes(String s) {
        List<String> result = new ArrayList<>();
        HashSet<Character> set = new HashSet<Character>();

        List<Character> list = new ArrayList<>();

        HashMap<Character, Integer> map = new HashMap<>();

        for(char ch : s.toCharArray()){
            map.put(ch, map.getOrDefault(ch, 0)  +1);
            if(set.contains(ch)){
                set.remove(ch);
            }else{
                set.add(ch);
            }
        }
        if(set.size() > 1){
            return result;
        }

        String mid = "";
        for(Map.Entry<Character, Integer> entry : map.entrySet()){
            Character ch = entry.getKey();
            Integer val = entry.getValue();

            if(val % 2 != 0)mid += ch;

            for (int i = 0; i < val / 2; i++) {
                list.add(ch);
            }
        }

        for(Character ch : list){
            //  System.out.print(ch + "\t");
        }

        helper(s, mid, new StringBuilder(), list, new boolean[list.size()] );

        return allPalindrom;
    }

    List<String> allPalindrom = new ArrayList<>();
    public void helper(String s, String mid, StringBuilder builder, List<Character> list, boolean[] used){
        if(list.size() == builder.length()){
            String res = builder.toString() + mid + builder.reverse().toString();
            //System.out.print(" Result " + res);
            allPalindrom.add(res);
            builder.reverse();
            return;
        }


        for(int i = 0; i < list.size(); i++){

            if(i > 0 && list.get(i) == list.get(i-1) && !used[i-1]) continue;

            if(used[i]) continue;

            used[i] = true;

            builder.append(list.get(i));

            helper(s, mid, builder, list, used);

            builder.deleteCharAt(builder.length() -1);
            used[i] = false;
        }
    }

    class MyHashMap {

        /**
         * Initialize your data structure here.
         */
        int[] map = new int[1000000];

        public MyHashMap() {
            Arrays.fill(map, -1);
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            map[key] = value;
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            return map[key];
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            map[key] = -1;
        }
    }

    class MyHashSet {

        /**
         * Initialize your data structure here.
         */
        int[] map = new int[1000000];

        public MyHashSet() {
            Arrays.fill(map, -1);
        }

        public void add(int key) {
            map[key] = key;
        }

        public void remove(int key) {
            map[key] = -1;
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            return map[key] != -1;
        }
    }

    /*
    LC : 785
    https://leetcode.com/problems/is-graph-bipartite/
    Graph coloring, Union - find
    */

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        Arrays.fill(colors, 0);

        for (int i = 0; i < n; i++) {
            if(colors[i] != 0) continue;
            colors[i] = 1;
            Queue<Integer> q = new LinkedList<>();
            q.offer(i);
            while (!q.isEmpty()){
                int curr = q.poll();
                int[] list = graph[curr];
                for (int j = 0; j < list.length; j++) {
                    int next = list[j];
                    if(colors[next] == 0){
                        colors[next] = -colors[curr];
                        q.offer(next);
                    }else if(colors[next] != -colors[curr]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isBipartiteUnionFind(int[][] graph) {
        int[] root = new int[graph.length];
        for (int i = 0; i < root.length; i++) {
            root[i] = i;
        }

        for(int i = 0; i < graph.length; i++){
            int[] adj = graph[i];
            for(int j = 0; j < adj.length;  j++){

                int xRoot = findPathCompression(i, root);
                int yRoot = findPathCompression(adj[j], root);
                if(xRoot != yRoot){
                    root[findPathCompression(adj[0], root)] = yRoot;
                }else{
                    return false;
                }
            }
        }
        return true;
    }
    
    public int find(int i, int[] parent) {
        if (parent[i] == i) {
            return parent[i];
        }
        parent[i] = find(parent[i], parent);
        return parent[i];
    }

    public int findPathCompression(int x, int[] root){
        while(x != root[x]){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }

    /* https://leetcode.com/problems/degree-of-an-array/ */
    public int findShortestSubArray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();//Element, freq

        for(int ele : nums){
            map.put(ele, map.getOrDefault(ele, 0) + 1);
        }

        int max = Integer.MIN_VALUE;
        int max_ele = 0;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            int key = entry.getKey();
            int value = entry.getValue();
            if(max < value ){
                max_ele = key;
            }
        }

        for(int i = 0; i < nums.length; i++){
            
        }

        return 0;
    }

    /* https://leetcode.com/problems/maximum-69-number/ */
    public int maximum69Number(int num) {
        String str = Integer.toString(num);

        int max = num;
        for (int i = 0; i < str.length(); i++) {
            StringBuilder builder = new StringBuilder(str);
            if(str.charAt(i) == '9'){
                builder.setCharAt(i, '6');
            }else{
                builder.setCharAt(i, '9');
            }
            max  = Math.max(max, Integer.valueOf(builder.toString()));
        }
        return max;
    }

    /**
     * Input: s = "HOW ARE YOU"
     * Output: ["HAY","ORO","WEU"]
     * Explanation: Each word is printed vertically.
     *  "HAY"
     *  "ORO"
     *  "WEU"
     */
    /* https://leetcode.com/problems/print-words-vertically/ */
    public List<String> printVertically(String s) {
        String[] arr = s.split(" ");

        ArrayList<StringBuilder> list = new ArrayList<StringBuilder>();
        for (int i = 0; i < arr.length; i++) {
            StringBuilder builder = new StringBuilder(arr[i]);
            list.add(builder);
        }
        ArrayList<String> res = new ArrayList<>();
        boolean notDone = true;
        while (notDone){
            StringBuilder builder = new StringBuilder();
            StringBuilder check = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                StringBuilder curr = list.get(i);
                if(curr.length() == 0){
                    builder.append(" ");
                    check.append("");
                }else{
                    builder.append(curr.charAt(0));
                    curr.deleteCharAt(0);
                    check.append("1");
                }
            }
            String result = builder.toString();
            result = result.replaceFirst("\\s++$", "");
            if(check.length() >= 1){
                notDone = true;
                res.add(result);
            }else{
                notDone = false;
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/delete-leaves-with-a-given-value/ */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        return helperLeaves(root, target);
    }

    public TreeNode helperLeaves(TreeNode root, int target) {
        if(root == null) return root;

        root.left = helperLeaves(root.left, target);
        root.right = helperLeaves(root.right, target);

        if(root.left == null && root.right == null && target == root.val){
            return null;
        }

        return root;
    }

    /* https://leetcode.com/problems/video-stitching/
    *  Revisit
    * */
    public int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, (a,b) -> a[0] - b[0]);

        int[] curr = clips[0];
        if(curr[0] != 0){
            return -1;// None of the interval starts with 0
        }
        return minClipsGreedy(clips, T);
    }

    public int minClipsGreedy(int[][] clips, int T){
        Arrays.sort(clips, (a,b) -> a[0] - b[0]);
        int res = 0;
        for (int i = 0, start = 0, end = 0 ; start < T; start = end ,res++) {
            for (; i < clips.length && start >= clips[i][0] ; ++i) {
                end = Math.max(end, clips[i][1]);
            }
            if(start == end) return -1;
        }
        return res;
    }

    public int minClipsRec(int[][] clips, int T, int[] curr){
        int start = curr[0];
        int end = curr[1];
        if(end == T) return 1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < clips.length; i++) {
            if(end >= clips[i][0] && clips[i][1] > end && clips[i] != curr){
                min = Math.min(min, 1 + minClipsRec(clips, T, clips[i]));
            }
        }
        return min;
    }

    /* https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/ */
    public int minTaps(int n, int[] ranges) {
        int[][] intervals = new int[2][ranges.length];
        for (int i = 0; i < ranges.length; i++) {
            int curr = ranges[i];
            int min = i - curr;
            int max = i + curr;

            int[] curr_interval = new int[]{min, max};
            intervals[i] = curr_interval;
        }

        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);

        int res = 0;
        for (int i = 0, start = 0, end = 0; start < n; start = end, res++) {
            for (; i < intervals.length && start >= intervals[i][0]; ++i) {
                end = Math.max(end , intervals[i][1]);
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/factor-combinations/ */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> allFactors = new ArrayList<>();
        getFactorsRec(allFactors, new ArrayList<>(), n, 2);
        return allFactors;
    }


    public void getFactorsRec(List<List<Integer>> res, List<Integer> item, int n, int start){
        if(n <= 1){
            if(item.size() > 1){
                res.add(new ArrayList<>(item));
            }
            return;
        }
        for (int i = start; i <= n ; i++) {
            if(n%i == 0){
                item.add(i);
                getFactorsRec(res, item, n/i, i);
                item.remove(item.size() -1);
            }
        }
    }


    /* https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/ */
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {val = _val;}

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
    class Codec {

        // Encodes a tree to a single string.
        public String serialize(Node root) {
            if(root == null){
                return "";
            }
            Queue<Node> q = new LinkedList<>();
            q.offer(root);
            StringBuilder builder = new StringBuilder();
            while(!q.isEmpty()){
                int size = q.size();
                while (size-- > 0) {
                    Node curr = q.poll();
                    if(curr.children == null || curr.children.size() == 0){
                        builder.append(curr.val);
                        builder.append("[null]");
                        continue;
                    }
                    builder.append(curr.val);
                    builder.append("[");
                    for (int i = 0; i < curr.children.size(); i++) {
                        Node child = curr.children.get(i);
                        builder.append(child.val);
                        builder.append(",");
                        q.offer(child);
                    }
                    builder.deleteCharAt(builder.length() -1);
                    builder.append("]");
                }
            }
            return builder.toString();
        }

        // Decodes your encoded data to tree.
        public Node deserialize(String data) {
            if(data.isEmpty()) return null;

            Queue<Node> queue = new LinkedList<>();
            StringBuilder builder = new StringBuilder(data);

            String root_val = builder.substring(0, builder.indexOf("["));

            Node root = new Node(Integer.valueOf(root_val));

            queue.offer(root);
            while (builder.length() > 0){
                int size = queue.size();
                while (size-- > 0){
                    Node curr = queue.poll();

                    List<Node> children = new ArrayList<>();
                    int start = builder.indexOf("[") + 1;
                    int end = builder.indexOf("]") ;

                    String sub = builder.substring(start, end);

                    String[] s_children  = sub.split(",");

                    for (int i = 0; i < s_children.length; i++) {
                        if(s_children[i].equals("null")){
                            break;
                        }
                        int node = Integer.valueOf(s_children[i]);
                        Node child = new Node(node);
                        children.add(child);
                        queue.offer(child);
                    }

                    curr.children = children;

                    builder = builder.delete(0, end + 1);
                }
            }
            return root;
        }
    }

    /* https://leetcode.com/problems/serialize-and-deserialize-bst */
    public class CodecBST {

        public String serialize(TreeNode root) {
            if(root == null) return "";
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            StringBuilder builder = new StringBuilder();
            while (!queue.isEmpty()) {
                TreeNode curr = queue.poll();
                if (curr != null) {
                    builder.append(curr.val);
                    builder.append(",");
                    queue.offer(curr.left);
                    queue.offer(curr.right);
                } else {
                    builder.append("null");
                    builder.append(",");
                }
            }
            System.out.println(builder.toString());
            return builder.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(data.isEmpty())return null;

            Queue<TreeNode> queue = new LinkedList<>();
            String[] values = data.split(",");
            TreeNode root = new TreeNode(Integer.parseInt(values[0]));
            queue.offer(root);

            for (int i = 1; i < values.length; i++) {
                TreeNode curr = queue.poll();
                if(!values[i].equals("null")){
                    TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                    curr.left = left;
                    queue.offer(left);
                }
                if(!values[++i].equals("null")){
                    TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                    curr.right = right;
                    queue.offer(right);
                }
            }
            return root;
        }
    }

    /* https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/ */
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        Object[] res = getDeepestAncestor(root);
        return (TreeNode)res[0];
    }

    public Object[] getDeepestAncestor(TreeNode root){
        if(root == null) return null;
        if(root.left == null && root.right == null){
            Object[] arr = new Object[2];
            arr[0] = root;
            arr[1] = 1;
            return arr;
        }
        Object[] left = getDeepestAncestor(root.left);
        Object[] right = getDeepestAncestor(root.right);
        if(left == null){
            Integer depth = (Integer) right[1];
            depth++;
            right[1] = depth;
            return right;
        }
        if(right == null){
            Integer depth= (Integer) left[1];
            depth++;
            left[1] = depth;
            return left;
        }
        Integer leftDepth = (Integer) left[1];
        Integer rightDepth = (Integer) right[1];
        if(leftDepth > rightDepth){
            Object[] res = new Object[2];
            res[0] = left[0];
            res[1] = leftDepth+1;
            return res;
        }else if(leftDepth < rightDepth){
            Object[] res = new Object[2];
            res[0] = right[0];
            res[1] = rightDepth+1;
            return res;
        } else {//Both depths are same
            Object[] res = new Object[2];
            res[0] = root;
            res[1] = leftDepth + 1;
            return res;
        }
    }

    /* https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/ */
    public int longestConsecutive(TreeNode root) {
        longestConsecutiveRec(root, null, 1);
        return globalMax;
    }

    int globalMax = Integer.MIN_VALUE;
    public void longestConsecutiveRec(TreeNode root, TreeNode parent, int max) {
        if(root == null){
            return;
        }
        if(parent != null && parent.val < root.val){
            int res = root.val - parent.val;
            if(res == 1){
                max = max + 1;
            }
        }else{
            max = 1;
        }
        globalMax = Math.max(globalMax, max);
        longestConsecutiveRec(root.left, root, max);
        longestConsecutiveRec(root.right, root, max);
    }

    /* https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/ 
    
    Input:
        2
       / \
      1   3
    Output: 3
    Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].

    */
    public int longestConsecutive2(TreeNode root) {
        longestConsecutiveRec(root);
        return max;
    }

    int max = 0;
    public int[] longestConsecutiveRec(TreeNode root) {
        if(root == null) return new int[]{0,0};

        int incr = 1;
        int decr = 1;
        int[] left =  longestConsecutiveRec(root.left);
        int[] right = longestConsecutiveRec(root.right);

        if(root.left != null){
            if(root.left.val == root.val+1) incr += left[0];
            if(root.left.val == root.val-1) decr += left[1];
        }

        if(root.right != null){
            if(root.right.val == root.val+1) incr = Math.max(incr, right[0] + 1);
            if(root.right.val == root.val-1) decr = Math.max(decr, right[1] + 1);
        }
        
        max = Math.max(max, incr + decr -1);
        return new int[]{incr, decr};
    }

    /* https://leetcode.com/problems/construct-binary-tree-from-string/ */
    public TreeNode str2tree(String s) {
        StringBuilder builder = new StringBuilder(s);
        return helper(builder);
    }

    public TreeNode helper(StringBuilder builder){
        if(builder.length() == 0) {
            return null;
        }else if(builder.length() <= 2){
            int root_val = 0;
            int n = builder.length();
            int i = builder.charAt(0) == '-' ? 1 : 0;
            while(i < n && builder.charAt(i) != '('){
                root_val = root_val*10 + builder.charAt(i) - '0';
                i++;
            }
            root_val = builder.charAt(0) == '-' ? -root_val : root_val;

            return new TreeNode(root_val);
        }

        int root_val = 0;
        int n = builder.length();
        int i = builder.charAt(0) == '-' ? 1 : 0;
        while(i < n && builder.charAt(i) != '('){
            root_val = root_val*10 + builder.charAt(i) - '0';
            i++;
        }
        root_val = builder.charAt(0) == '-' ? -root_val : root_val;
        builder.delete(0, i);

        Stack<Character> stack = new Stack<>();
        int start = 0;
        int end = 0;
        if(end < builder.length()) {
            stack.push(builder.charAt(end++));
            while (!stack.isEmpty() && builder.length() > 0){
                char curr = builder.charAt(end++);
                if(curr == '('){
                    stack.push(curr);
                }else if(curr == ')'){
                    stack.pop();
                }
            }
        }
        StringBuilder left_subs;
        if(start == end){
            left_subs = new StringBuilder();
        }else{
            left_subs = new StringBuilder(builder.substring(start+1, end-1));
        }


        start = end;
        if(end < builder.length()) {
            stack.push(builder.charAt(end++));
            while (!stack.isEmpty() && builder.length() > 0) {
                char curr = builder.charAt(end++);
                if (curr == '(') {
                    stack.push(curr);
                } else if (curr == ')') {
                    stack.pop();
                }
            }
        }

        StringBuilder right_sub;
        if(start == end){
            right_sub = new StringBuilder();
        }else{
            right_sub = new StringBuilder(builder.substring(start+1, end-1));
        }

        TreeNode curr = new TreeNode(root_val);

        curr.left = helper(left_subs);
        curr.right = helper(right_sub);

        return curr;
    }

    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        int length = Math.max(v1.length, v2.length);

        for (int i = 0; i < length; i++) {
            Integer i1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            Integer i2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            int compare = Integer.compare(i1, i2);
            if(compare != 0) return compare;
        }
        return 0;
    }

    /* https://leetcode.com/problems/find-duplicate-file-in-system/ 
    Input:
    ["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
    Output:  
    [["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]

    */
    public List<List<String>> findDuplicate(String[] paths) {
        HashMap<String, List<String>> map = new HashMap<>();
        for(String str : paths){
            String[] arr = str.split(" ");
            String path = arr[0];
            for(int i = 1; i < arr.length; i++){
                String curr = arr[i];
                String content = curr.substring(curr.indexOf("(")+1, curr.indexOf(")"));
                String filename = curr.substring(0, curr.indexOf("(") );
                List<String> stored_paths = new ArrayList<>();
                if(map.containsKey(content)){
                    stored_paths = map.get(content);
                }
                String filePath = path + "/" + filename;
                stored_paths.add(filePath);
                map.put(content, stored_paths);
            }
        }

        List<List<String>> result = new ArrayList<>();

        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            List<String> value = entry.getValue();
            if(value.size() > 1){
                result.add(entry.getValue());
            }
        }
        return result;
    }

}