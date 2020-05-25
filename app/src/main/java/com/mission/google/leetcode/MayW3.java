package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

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
import java.util.Set;
import java.util.Stack;

public class MayW3 {
    

    public static void main(String[] args) {
        MayW3 test = new MayW3();
        int[][] grid = new int[][]{
                {0,-3}
        };
        //int res = test.calculateMinimumHP(grid);
        //String res = test.minWindow("cnhczmccqouqadqtmjjzl" ,"czm");
        //System.out.println("res = " + res);
        //int res = test.isPrefixOfWord("i love eating burger", "burg");
        boolean res = test.isPalindrome("");
        System.out.println("res = " + res);
    }
    
    /* LC : 787
       https://leetcode.com/problems/cheapest-flights-within-k-stops/
    */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Using Dijkstra's Algorithm
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < flights.length; i++) {
            int curr[] = flights[i];
            map.putIfAbsent(curr[0], new HashMap<Integer, Integer>());
            map.get(curr[0]).put(curr[1], curr[2]);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[]{0, src, k + 1});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int stops = curr[2];
            int curr_src = curr[1];
            int cost = curr[0];
            if(stops == 0){
                return cost;
            }
            if(stops > 0){
                HashMap<Integer, Integer> adj = map.getOrDefault(curr_src, new HashMap<Integer, Integer>());
                for(Integer next_src : adj.keySet()){
                    pq.offer(new int[]{adj.get(next_src), next_src, stops - 1});
                }
            }
        }
        return -1;
    }

    /* LC : 491
       https://leetcode.com/problems/increasing-subsequences/
    */
    public List<List<Integer>> findSubsequences(int[] nums) {
        helper(nums, 0, new ArrayList<>());
        List<List<Integer>> ans = new ArrayList<>();
        Iterator<List<Integer>> it = res.iterator();
        while(it.hasNext()){
            List<Integer> next = it.next();
            ans.add(next);
        }
        return ans;
    }

    HashSet<List<Integer>> res = new HashSet<>();
    public void helper(int[] nums, int start, List<Integer> curr){
        //System.out.println(curr);
        if(curr.size() > 1){
            res.add(new ArrayList<>(curr));
        }

        for(int i = start; i < nums.length; i++){
            if(curr.size() == 0){
                curr.add(nums[i]);
            }else {
                int last = curr.get(curr.size() - 1);
                if(last <= nums[i]){
                    curr.add(nums[i]);
                }
            }
            helper(nums, i + 1 , curr);
            if(curr.get(curr.size() - 1) == nums[i]){
                curr.remove(curr.size() - 1);
            }
        }
    }

    /*
    LC : 1458
    https://leetcode.com/problems/max-dot-product-of-two-subsequences/
    */
    public int maxDotProduct(int[] A, int[] B) {
        int n = A.length;
        int m = B.length;
        int dp[][] = new int[n+1][m+1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j]); // If i is not selected
                dp[i][j] = Math.max(dp[i][j], dp[i][j-1]); // If j is not selected
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1]);// If i & j  both are not selected
                dp[i][j] = Math.max(dp[i][j], Math.max(dp[i-1][j-1], 0) + A[i-1] * B[j-1] ); //If both i & j  are selected
            }
        }
        return dp[n][m];
    }

    /*  LC : 1456
        https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/
    */
    public int maxVowels(String s, int k) {
        int start = 0;
        int end = 0;
        int len = s.length();
        char[] arr = s.toCharArray();
        int count = 0;

        for(; end < k; end++){
            if(isVowel(arr[end])){
                count++;
            }
        }
        String res = "";
        if(count > 0){
            res = s.substring(start, end);
        }
        int max = count;
        end = k;
        start = 0;
        while(end < len){
            if(isVowel(arr[end++])){
                count++;
            }
            if(isVowel(arr[start++])){
                count--;
            }
            if(max < count){
                res = s.substring(start, end);
                max = count;
            }
        }
        char[] ans = res.toCharArray();
        int maxAns = 0;
        for (int i = 0; i < ans.length; i++) {
            if(isVowel(ans[i])){
                maxAns++;
            }
        }
        return maxAns;
    }

    public boolean isVowel(char ch){
        if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
            return true;
        }
        return false;
    }

    /*
    LC : 1457
    https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/
    */
    public int pseudoPalindromicPaths (TreeNode root) {
        return helperTree(root, "");
    }

    public int helperTree(TreeNode root, String path){
        if(root == null) return 0;
        if(root.left == null && root.right == null){
            if(isPalindrome(path + "" + root.val)) return 1;
        }
        int left = helperTree(root.left, path + "" + root.val + "");
        int right = helperTree(root.right, path + "" + root.val + "");
        return left + right;
    }

    public boolean isPalindrome(String s){
        char[] arr = s.toCharArray();
        int[] intarr = new int[10];
        for (char ch : arr){
            int ele = (int)ch - '0';
            intarr[ele]++;
        }
        int odd = 0;
        for (int i = 0; i < 10; i++) {
            if(odd > 1) return false;
            if(intarr[i] % 2 != 0){
                odd++;
            }
        }
        if(s.length() % 2 == 0 && odd > 0){
            return false;
        }
        if(s.length() % 2 != 0 && odd > 1) return false;
        return true;
    }

    /*
    LC : 1455
    https://leetcode.com/problems/check-if-a-word-occurs-as-a-prefix-of-any-word-in-a-sentence/
    */
    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] words = sentence.split(" ");
        int minIdx = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            String curr = words[i];
            if(curr.startsWith(searchWord)){
                if(minIdx > i + 1){
                    minIdx = i + 1;
                }
            }
        }
        return minIdx != Integer.MAX_VALUE ? minIdx : -1;
    }

    /* 
    LC : 1273
    https://leetcode.com/problems/delete-tree-nodes/ 
    */
    public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
        List<List<Integer>> tree = constructTree(nodes, parent);
        int[] ans = helper(tree, value, 0);
        return ans[1];
    }

    public int[] helper(List<List<Integer>> tree, int[] values, int root) {
        int sum = values[root];
        int count = 1; // including root;

        for (int child : tree.get(root)) {
            int[] childRes = helper(tree, values, child);
            sum += childRes[0];
            count += childRes[1];
        }

        int[] res = new int[]{sum, sum == 0 ? 0 : count};
        return res;
    }

    public List<List<Integer>> constructTree(int nodes, int[] parent) {
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            tree.add(new ArrayList<>());
        }
        for (int i = 0; i < nodes; i++) {
            if(parent[i] != -1){
                tree.get(parent[i]).add(i);
            }
        }
        return tree;
    }

    /*
    LC : 698
    https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    */
    public boolean canPartitionKSubsets(int[] nums, int k) {
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

    /*
    LC : 727
    https://leetcode.com/problems/minimum-window-subsequence/
    */
    public String minWindow(String s, String t) {
        int slen = s.length();
        int tlen = t.length();
        int si = 0;
        int ti = 0;
        int len = slen;
        int start = -1;
        while (si < slen){
            if(s.charAt(si) == t.charAt(ti)){
                if(++ti == tlen){
                    int end = si+1;
                    while (--ti >= 0){ // cabcczam czm
                        while (s.charAt(si) != t.charAt(ti)){
                            si--;
                        }
                    }
                    ++si;
                    ++ti;
                    if(end - si < len){
                        len = end - si;
                        start = si;
                    }
                }
            }
            ++si;
        }
        return start == -1 ? "" : s.substring(start, start + len);
    }

     /*
     LC : 895
     https://leetcode.com/problems/maximum-frequency-stack/ 
     */
    class FreqStack {
        HashMap<Integer, Integer> freqMap;
        HashMap<Integer, Stack<Integer>> stackMap;
        int maxFreq = 0;
        public FreqStack() {
            freqMap = new HashMap<>();
            stackMap = new HashMap<>();
        }
        
        public void push(int x) {
            int f = freqMap.getOrDefault(x, 0) + 1;
            freqMap.put(x, f);
            maxFreq = Math.max(maxFreq, f);
            stackMap.putIfAbsent(f, new Stack<>());
            stackMap.get(f).add(x);
        }
        
        public int pop() {
            int res = stackMap.get(maxFreq).pop();
            freqMap.put(res, maxFreq - 1);
            if(stackMap.get(maxFreq).isEmpty()){
                stackMap.remove(maxFreq);
                maxFreq--;
            }
            return res;
        }
    }

    /*
    LC : 174
    https://leetcode.com/problems/dungeon-game/
    Revisit
    */
    public int calculateMinimumHP(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        int dp[][] = new int[row+1][col+1];

        for (int[]a : dp) {
            Arrays.fill(a, Integer.MAX_VALUE);
        }

        dp[row-1][col] = 1;
        dp[row][col-1] = 1;

        for(int i = row -1; i >= 0; --i){
            for (int j = col - 1; j >= 0; --j) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - grid[i][j];
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }
        return dp[0][0];
    }


    /*
    LC : 1003
    https://leetcode.com/problems/check-if-word-is-valid-after-substitutions/
    */
    public boolean isValid(String S) {
        String t = "abc";

        if(S.equals(t)|| S.length() == 0) return true;

        int idx = S.indexOf(t);
        if(idx < 0){
            return false;
        }
        StringBuilder builder = new StringBuilder(S);
        builder.delete(idx, idx + t.length());

        return isValid(builder.toString());
    }

    /* 
    LC : 901
    https://leetcode.com/problems/online-stock-span/
    Revisit
    */
    class StockSpanner {
        Stack<int[]> stack;

        public StockSpanner() {
            stack = new Stack<>();
        }

        public int next(int price) {
            int len = 0;
            int span = 1;
            int[] curr = new int[]{price, span};

            while (!stack.isEmpty() && stack.peek()[0] <= price) {
                span += stack.pop()[1];
            }
            curr[1] = span;
            stack.push(curr);
            return span;
        }
    }

    /*  LC : 909
        https://leetcode.com/problems/snakes-and-ladders/ 
    */
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] arr = new int[n*n];
        Arrays.fill(arr, -1);
        boolean flip = false;
        int index = 0;
        for(int i = n-1; i >= 0; i--){
            if(flip){
                for(int j = n-1; j >= 0; j--){
                    index++;
                    if(board[i][j] != -1){
                        arr[index-1] = board[i][j] - 1;
                    }
                }
                flip = false;
            }else{
                for(int j = 0; j < n; j++){
                    index++;
                    if(board[i][j] != -1){
                        arr[index-1] = board[i][j] - 1;
                    }
                }
                flip = true;
            }
        }
        
        Queue<Integer> bfs = new LinkedList<Integer>();
        int minSteps = 0;
        boolean[] visited = new boolean[arr.length];
        int start = arr[0] > -1 ? arr[0] : 0;
        
        visited[start] = true;
        bfs.offer(start);
        
        while(!bfs.isEmpty()){
            int size = bfs.size();
            while(size-- > 0){
                int loc = bfs.poll();
                if(loc == arr.length -1){
                    return minSteps;
                }

                for(int next = loc + 1; next <= Math.min(6 + loc, arr.length -1); next++){
                    int dest = arr[next] == -1 ? next : arr[next];
                    if(!visited[dest]){
                        visited[dest]= true;
                        bfs.offer(dest);
                    }
                }
            }
            minSteps++;
        }
        return -1;
    }

    /* 
        LC : 686
        https://leetcode.com/problems/repeated-string-match/ 
    */
    public int repeatedStringMatch(String A, String B) {
        return bruteForce(A, B);
    }
    
    public int bruteForce(String A, String B) {
        StringBuilder builder = new StringBuilder();
        int minSteps = 0;
        while(builder.length() < B.length()){
            builder.append(A);
            minSteps++;
        }
        if(builder.toString().contains(B)) return minSteps;
        if(builder.append(A).toString().contains(B)) return minSteps + 1;
        return -1;
    }


    /* 
        LC : 459
        https://leetcode.com/problems/repeated-substring-pattern/ 
    */
    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();

        for(int i = len / 2; i >= 1; --i){
            if(len % i == 0){
                int factor = len / i;
                String first = s.substring(0, i);
                StringBuilder builder = new StringBuilder();
                for(int j = 0 ; j < factor; j++){
                    builder.append(first);
                }
                if(s.equals(builder.toString())){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> findAnagrams(String s, String p) {
        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> temp = new HashMap<>();
        
        int counter = 0;
        for(char ch : p.toCharArray()){
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            counter++;
        }
        
        temp = map;
        int start = 0;
        int tempCounter = counter;
        List<Integer> res = new ArrayList<>();
        for(int end = 0; end < s.length(); end++){
            char endch = s.charAt(end);
            if(temp.containsKey(endch)){
                
                if(temp.get(endch) > 0 ){
                    temp.put(endch, temp.get(endch) -1);
                    tempCounter--;
                }
            }
            if(tempCounter == 0){
                res.add(start);
            }
            char startch = s.charAt(start);
            if(temp.containsKey(startch)){
                temp.put(startch, temp.get(startch) + 1);
                counter++;
            }
        }
        return res;
    }

    /*
     LC : 1452
     https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/ */
    public List<Integer> peopleIndexes(List<List<String>> fcs) {
        int n = fcs.size();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int a = find(i, parent);
                int b = find(j, parent);
                if(parent[a] == parent[b])continue;
                else if(contains(fcs.get(a), fcs.get(b))){
                    parent[b] = a;
                }else if(contains(fcs.get(b), fcs.get(a))){
                    parent[a] = b;
                }
            }
        }
        Set<Integer> res = new HashSet<>();
        for (int i : parent) {
            res.add(find(i, parent));
        }
        List<Integer> ans = new ArrayList<>(res);
        Collections.sort(ans);
        return ans;
    }

    public boolean contains(List<String> a, List<String> b){
        if(a.size() <= b.size()) return false;
        return a.containsAll(b);
    }

    public int find(int x, int[] parent){
        while (x != parent[x]){
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    /*
    LC : 1451
    https://leetcode.com/problems/rearrange-words-in-a-sentence/ */
    public String arrangeWords(String text) {
        if(text.length() == 0) return text;
        String[] arr = text.split(" ");

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if(a.length() == b.length()){
                    return 0;
                }else{
                    return a.length() - b.length();
                }
            }
        });

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i].toLowerCase());
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length()-1);
        char ch = Character.toUpperCase(builder.charAt(0));
        builder.setCharAt(0, ch);
        return builder.toString();
    }

    /* LC : 1450
       https://leetcode.com/problems/number-of-students-doing-homework-at-a-given-time/
     */
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int count = 0;
        for (int i = 0; i < startTime.length; i++) {
            int start = startTime[i];
            int end = endTime[i];
            if(start <= queryTime && queryTime <= end ){
                count++;
            }
        }
        return count;
    }

    /*
     LC : 1449
     https://leetcode.com/problems/form-largest-integer-with-digits-that-add-up-to-target/ */
    public String largestNumber(int[] cost, int target) {
        int[] dp = new int[target + 1];
        for (int t = 1; t <= target; t++) {
            dp[t] = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if(t >= cost[i]){
                    dp[t] = Math.max(dp[t] , 1 + dp[t- cost[i]]);
                }
            }
        }
        if(dp[target] < 0) return "0";
        StringBuilder res = new StringBuilder();
        for (int i = 8; i >= 0; --i) {
            while (target >= cost[i] && dp[target] == dp[target - cost[i]] + 1){
                res.append(1 + i);
                target -= cost[i];
            }
        }
        return res.toString();
    }

    /*  LC : 1447
        https://leetcode.com/problems/simplified-fractions/
    */
    public List<String> simplifiedFractions(int n) {
        List<String> ans = new ArrayList<>();
        HashMap<String, Boolean> seen = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n ; j++) {
                if(i / j == 1){
                    continue;
                }
                int factor = gcd(i, j);
                int first = i / factor;
                int second = j / factor;
                if(!seen.containsKey(first + "/"+second)){
                    ans.add(i+"/"+j);
                    seen.put(first + "/ "+second, true);
                }
            }
        }
        return ans;
    }

    public int gcd(int a, int b){
        if(a == 0) return b;
        return gcd(b % a, a);
    }

    /*
       LC : 1448
       https://leetcode.com/problems/count-good-nodes-in-binary-tree/
    */
    public int goodNodes(TreeNode root) {
        return helper(root, null);
    }

    public int helper(TreeNode root, TreeNode parent){
        if(root == null) return 0;
        int leftGood = helper(root.left, root);
        int rightGood = helper(root.right, root);

        int count = 0;
        if(parent != null){
            if(root.val >= parent.val){
                count++;
            }
        }
        return count + leftGood + rightGood;
    }

    /*
       LC: 1446
       https://leetcode.com/problems/consecutive-characters/
    */
    public int maxPower(String s) {
        int res = 0;
        int i = 0;
        int len = s.length();
        while(i < s.length()){
            int count = 1;
            while(i+1 < len && s.charAt(i) == s.charAt(i+1)){
                i++;
                count++;
            }
            i++;
            res = Math.max(res, count);
        }
        return res;
    }

    /* https://leetcode.com/problems/odd-even-linked-list/ */
    public ListNode oddEvenList(ListNode head) {
        ListNode odd = head;
        if(head == null || head.next == null) {
            return head;
        }
        ListNode even = odd.next;
        ListNode evenStart = even;
        while(even != null && even.next != null){
            ListNode newEven = even.next.next;
            
            ListNode newOdd = even.next;
            
            odd.next = newOdd;
            newOdd.next = evenStart;
            odd = newOdd;
            
            even.next = newEven;
            even = newEven;
        }
        return head;
    }

    /*
        LC : 918
        https://leetcode.com/problems/maximum-sum-circular-subarray/ 
    */
    public int maxSubarraySumCircular(int[] arr) {
        int global_max = Integer.MIN_VALUE;
        int curr_max = 0;
        int global_min = Integer.MAX_VALUE;
        int curr_min = 0;
        int totalsum = 0;
        int n = arr.length;
        for(int a : arr){
            curr_max = Math.max(curr_max + a, a);
            global_max = Math.max(global_max, curr_max);
            
            curr_min = Math.min(curr_min + a, a);
            global_min = Math.min(curr_min, global_min);
            
            totalsum += a;
        }
        return (global_max > 0) ? Math.max(global_max, totalsum - global_min) : global_max;
    }

    /* https://leetcode.com/problems/couples-holding-hands/
    *  LC : 765
    *  Revisit
    *  */
    public int minSwapsCouples(int[] row) {
        int n = row.length / 2;
        UnionFind unionfind = new UnionFind(n);
        for(int i = 0; i < n; i++){
            int a = row[2 * i];
            int b = row[2 * i  +1];

            unionfind.union(a / 2, b / 2);
        }
        return unionfind.count() - n;
    }

    class UnionFind {
        private int count = 0;
        private int[] parent, rank;
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }
        public int count() {
            return count;
        }
    }

}