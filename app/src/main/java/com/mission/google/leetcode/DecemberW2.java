package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class DecemberW2 {

    /*
        https://leetcode.com/problems/edit-distance/
    */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        if(n*m == 0){
            return n + m;
        }

        int[][] dp = new int[n + 1][m + 1];
        
        for(int i = 0; i < n + 1; i++){
            dp[i][0] = i;
        }

        for(int i = 0; i < m + 1; i++){
            dp[0][i] = i;
        }

        for(int i = 1; i < n + 1; i++){
            for(int j = 1; j < m + 1; j++){
                if(word1.charAt(i -1) == word2.charAt(j -1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = 1 + minOfEditOperations(dp[i-1][j], dp[i][j-1],dp[i-1][j-1]);
                }
            }   
        }
        return dp[n][m];
    }

    public int minOfEditOperations(int a, int b, int c){
        return Math.min(Math.min(a,b),c);
    }

    /*
        https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
    */
    public int subtractProductAndSum(int n) {
        int sum = 0;
        int prod = 1;
        while(n != 0){
            int rem = n % 10;
            sum += rem;
            prod = prod * rem;
            n /= 10;
        }
        return prod - sum;
    }

    /*
        https://leetcode.com/problems/group-the-people-given-the-group-size-they-belong-to/
    */
    public List<List<Integer>> groupThePeople(int[] gz) {
        List<List<Integer>> res = new ArrayList();
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < gz.length; ++i) {
            List<Integer> list = groups.computeIfAbsent(gz[i], k -> new ArrayList());
            list.add(i);
            if (list.size() == gz[i]) {
                res.add(new ArrayList<>(list));
                list.clear();
            }
        }
        return res;
    }

    /*  LC : 1283
        https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/ 
        Binary Search
    */
    public int smallestDivisor(int[] nums, int threshold) {
        int low = 1;
        int high = (int)1e6;

        while(low < high){
            int mid = low + (high - low) / 2;
            int sum = 0;
            for(int n : nums){
                sum += (int)Math.ceil((double)n / mid);
            }
            if(sum > threshold){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    /*
        https://leetcode.com/problems/insert-delete-getrandom-o1/
    */

    class RandomizedSet {

        /** Initialize your data structure here. */
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        Random random = new Random();
        public RandomizedSet() {
            
        }
        
        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(map.containsKey(val)){
                return false;
            }
            map.put(val, list.size());
            list.add(val);
            return true;
        }
        
        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!map.containsKey(val)){
                return false;
            }
            int loc = map.get(val);
            if(loc < list.size() -1 ){
                int lastEle = list.get(list.size() -1);
                map.put(lastEle, loc);
                list.set(loc, lastEle);
            }
            list.remove(list.size()-1);
            map.remove(val);
            return true;
        }
        
        /** Get a random element from the set. */
        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }

    
    /*
        To-do: Revisit
        https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
    */
    public int minDominoRotations(int[] A, int[] B) {

        int n = A.length;

        for(int i = 0, a = 0, b = 0; i < n && (A[i] == A[0] || B[i] == A[0]); ++i){
            if(A[i] != A[0])a++;
            if(B[i] != A[0])b++;
            if(i == n-1)return Math.min(a,b);
        }
        for(int i = 0, a = 0, b = 0; i < n && (A[i] == B[0] || B[i] == B[0]); ++i){
            if(A[i] != B[0])a++;
            if(B[i] != B[0])b++;
            if(i == n-1)return Math.min(a,b);
        }
        return -1;
    }

    public static void main(String[] args) {
        DecemberW2 w2 = new DecemberW2();
        //w2.splitArray(new int[]{7,2,5,10,8}, 2);
        w2.mostCommonWord("a, a, a, a, b,b,b,c, c",
                new String[]{"a"});
    }

    /*  410. Split Array Largest Sum
        https://leetcode.com/problems/split-array-largest-sum/
        To-Do: Revisit, Important
    */
     public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[][] f = new int[n + 1][m + 1];
        int sub[] = new int[n + 1];

        for(int i = 0; i <= n; i++){
            for (int j = 0; j <= m; j++) {
                f[i][j] = Integer.MAX_VALUE;
            }
        }
        for(int i = 0; i < n; i++){
            sub[i+1] = sub[i] + nums[i];
        }
        
        f[0][0] = 0; 

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                for(int k = 0; k < i; k++){
                    f[i][j] = Math.min(f[i][j], Math.max(f[k][j-1], sub[i] - sub[k]));
                }
            }
        }
        return f[n][m];
    }

    public int splitArrayBinarySearch(int[] nums, int n){
        int max = Arrays.stream(nums).max().getAsInt();
        int sum = Arrays.stream(nums).sum();

        long left = max;
        long right = sum;
        while(left <= right){
            long mid = left + (right - left) / 2;
            if(valid(mid, nums, n)){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return (int)left;
    }

    public boolean valid(long target, int[] arr, int m){
        int count = 1;
        int curr = 0;
        for(int a : arr){
            curr += a;
            if(curr > target){
                count++;
                if(count > m){
                    return false;
                }
            }
        }
        return true;
    }

    /*
        https://leetcode.com/problems/most-common-word/submissions/
    */
    public String mostCommonWord(String paragraph, String[] banned) {
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        TreeMap<Integer, List<String>> treeMap = new TreeMap<>(Collections.reverseOrder());

        paragraph = paragraph.replaceAll("\\W+" , " ");
        String[] arr = paragraph.split("\\s+");

        for(String word : arr){
            word = word.toLowerCase();
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()){
            List<String> words_with_same_freq;
            if(!treeMap.containsKey(entry.getValue())){
                words_with_same_freq = new ArrayList<>();
            }else{
                words_with_same_freq = treeMap.get(entry.getValue());
            }
            words_with_same_freq.add(entry.getKey());
            treeMap.put(entry.getValue(),words_with_same_freq);
        }

        List<String> bannedList = Arrays.asList(banned);

        for(Map.Entry<Integer, List<String>> treeEntry : treeMap.entrySet()){
            for(String same_frq_word : treeEntry.getValue()){
                if(!bannedList.contains(same_frq_word)){
                    return same_frq_word;
                }
            }
        }
        return "";
    }

    class RopeNode {
        RopeNode left;
        RopeNode right;
        int weight;
        String word;
        
        public RopeNode(int weight){
            this.weight = weight;
        }
    }

    /*
        https://leetcode.com/problems/single-element-in-a-sorted-array/
        Important : Revisit
    */
    public static int singleNonDuplicate(int[] nums) {
        int start = 0;
        int end = nums.length -1;
        while(start < end){
            int mid = (start + end) / 2;
            if(mid % 2 == 1){
                mid--;
            }

            if(nums[mid] != nums[mid+1]){
                end = mid;
            }else{
                start = mid + 2;
            }
        }
        return nums[start];
    }

    /*  Important Graph problem
        To-Do: Revisit
        Clone Graph problem
        https://leetcode.com/problems/copy-list-with-random-pointer/
        https://leetcode.com/problems/clone-graph/
    */
    class Node{
        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val,Node _next,Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }
    
    HashMap<Node,Node> visitedMap = new HashMap<>();

    public Node copyRandomList(Node head) {
        if(head == null) return null;
        if(visitedMap.containsKey(head)){
            return visitedMap.get(head);//Return cloned node
        }
        Node cloneNode = new Node(head.val, null, null);
        
        visitedMap.put(head, cloneNode);

        cloneNode.next = copyRandomList(head.next);
        cloneNode.random = copyRandomList(head.random);
        return cloneNode;
    }

    /*
        https://leetcode.com/problems/clone-graph
        133. Clone Graph
    */
    class GraphNode {
        public int val;
        public List<GraphNode> neighbors;

        public GraphNode() {}

        public GraphNode(int _val,List<GraphNode> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    };

    HashMap<GraphNode, GraphNode> graphMap = new HashMap<>();
    public GraphNode cloneGraph(GraphNode node) {
        if(node == null){
            return null;
        }
        if(graphMap.containsKey(node)){
            return graphMap.get(node);
        }
        List<GraphNode> new_neighbors = new ArrayList<>();
        GraphNode newNode = new GraphNode(node.val, new_neighbors);
        graphMap.put(node, newNode);
        
        for(GraphNode child : node.neighbors){
            GraphNode newChild = cloneGraph(child);
            new_neighbors.add(newChild);
        }
        return newNode;
    }

    /*
        https://leetcode.com/problems/minimum-time-difference/
    */
    public int findMinDifference(List<String> timePoints) {
            
            boolean[] mark = new boolean[24*60];
            
            for(String time : timePoints){
                String[] hr_min = time.split(":");
                int hr = Integer.parseInt(hr_min[0]);
                int min = Integer.parseInt(hr_min[1]);

                min = hr*60 + min;
                if(mark[min]) return 0;
                
                mark[min] = true;
            }
            
            int minDiff = Integer.MAX_VALUE;
            int prev = 0;
            int first = Integer.MAX_VALUE, last  = Integer.MIN_VALUE;

            for(int i = 0; i < 24*60; i++){
                if(mark[i]){
                    if(first != Integer.MAX_VALUE){
                        minDiff = Math.min(minDiff, i - prev);
                    }
                    first = Math.min(first, i);
                    last = Math.max(last, i);
                    prev = i;
                }
            }
            minDiff = Math.min((24*60 - last + first), minDiff);
            return minDiff;
    }


    /*
        https://leetcode.com/problems/minimum-height-trees/
    */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        List<Integer> res = new ArrayList<>();

        if (n==1) {
            res.add(0);
            return res;
        }

        for(int i = 0 ; i < n ; i++){
            graph[i] = new ArrayList<>();
        }

        int[] degrees = new int[n];

        for(int[] edge : edges){
            
            int first = edge[0];
            int second = edge[1];
            
            graph[first].add(second);
            graph[second].add(first);

            degrees[first]++;
            degrees[second]++;
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();

        for(int i = 0; i < n; i++){
            if(degrees[i] == 0)
                return res;
            else if(degrees[i] == 1){
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()){
            res = new ArrayList<>();
            int size = queue.size();
            for(int i = 0; i < size; i++){
                int curr = queue.poll();
                
                res.add(curr);
                degrees[curr]--;

                for(int idx = 0; idx < graph[curr].size(); idx++){
                    int next = graph[curr].get(idx);
                    if(degrees[next] == 0) continue;
                    if(degrees[next] == 2){
                        queue.offer(next);
                    }
                    degrees[next]--;
                }    
            }
        }
        return res;
    }

    /*https://leetcode.com/problems/word-break/*/
    public boolean wordBreak(String string, List<String> wordDict) {
        
        int n = string.length();

        Set<String> dict = new HashSet<>(wordDict);
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        
        int[] visited = new int[n];

        while(!q.isEmpty()){
            int start = q.poll();
            if(visited[start] == 0){
                for(int end = start + 1; end < n; end++){
                    if(dict.contains(string.substring(start, end))){
                        q.offer(end);
                        if(end == n){
                            return true;
                        }
                    }
                }
            }
            visited[start] = 1;
        }
        return false;
    }

        /*

        Input: low = 100, high = 300
        Output: [123,234]

        Input: low = 1000, high = 13000
        Output: [1234,2345,3456,4567,5678,6789,12345]

        https://leetcode.com/problems/sequential-digits/

        */

    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        for(int i = 1; i <= 9; i++ ){
            helper(res, i, low, high, i+1);
        }
        Collections.sort(res);
        return res;
    }

    public void helper(List<Integer> res, long num, int low, int high, int next){
        if(num > high) return;
        if(num >= low) res.add((int)num);
        if(next == 10) return;
        helper(res, num * 10 + next, low, high, next +1);
    }

    /*
        https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/

        101
        0*2 + 1 -> 1*2 + 0 -> 2*2 + 1 = 5
        1*4 + 0*1 + 1*1 = 5

    */
    int ans = 0;
    public int getDecimalValueIterative(ListNode head) {
        int res = 0;
        while(head != null){
            res = res * 2 + head.val;
            head = head.next;
        }
        return res;
    }

    public int getDecimalRec(ListNode head){
        if(head == null) return 0;
        int num = getDecimalRec(head.next);
        if(head.val == 1) ans += Math.pow(2, num);
        return num + 1;
    }


    /*
        https://leetcode.com/problems/boundary-of-binary-tree/
    */
    List<Integer> res = new ArrayList<>();
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        if(root == null) return res;

        res.add(root.val);
        addLeftView(root.left);
        addLeaves(root.left);
        addLeaves(root.right);
        addRightView(root.right);
        
        return res;
    }
    
    public void addLeftView(TreeNode root){
        if(root == null || (root.left == null && root.right == null)) return;
        
        res.add(root.val);
        
        if(root.left != null)
            addLeftView(root.left);
        else if(root.right != null) 
            addLeftView(root.right);
    }
    
    public void addRightView(TreeNode root){
        if(root == null || (root.left == null && root.right == null)) return;
        
        if(root.right != null)
            addRightView(root.right);
        else if(root.left != null)
            addRightView(root.left);
        
        res.add(root.val);
    }

    public void addLeaves(TreeNode root){
        if(root == null ) return;
        if(root.left == null && root.right == null){
            res.add(root.val);
            return;
        }
        addLeaves(root.left);
        addLeaves(root.right);
    }

    /* 
        https://leetcode.com/problems/binary-tree-postorder-traversal/
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        
        List<Integer> res = new ArrayList<>();

        res.forEach(ele -> System.out.println(ele));
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastNodeVisited = null;
        
        while(!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                TreeNode top = stack.peek();
                if(top.right != null && lastNodeVisited != top.right){
                    root = top.right;
                }else{
                    res.add(top.val);
                    lastNodeVisited = stack.pop();
                }
            }
        }
        return res;
    }

    /*
        https://leetcode.com/problems/redundant-connection/
    */
    public int[] findRedundantConnection(int[][] edges) {
        int[] root = new int[edges.length];
        List<int[]> res = new ArrayList<>();

        for(int i = 0; i < root.length; i++){
            root[i] = i;
        }

        for(int i = 0; i < edges.length; i++){
            int[] edge = edges[i];
            int src = edge[0];
            int dst = edge[1];

            int xRoot = findParent(root, src -1);
            int yRoot = findParent(root, dst -1);
            if(xRoot != yRoot){
                root[xRoot] = yRoot;
            }else{
                res.add(new int[]{src, dst});
            }
        }
        if(!res.isEmpty()){
            return res.get(res.size()-1);
        }else{
            return new int[]{0,0};
        }
    }

    public int findParent(int[] root, int x){
        while(root[x] != x){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }

    /*
      https://leetcode.com/problems/regions-cut-by-slashes/
    */
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int[][] upscaled = new int[n*3][n*3];


        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(grid[i].charAt(j) == '/') upscaled[i*3][j*3 + 2] = upscaled[i*3 + 1][j*3 + 1] = upscaled[i*3 + 2][j*3] = 1;
                if(grid[i].charAt(j) == '\\') upscaled[i*3][j*3] = upscaled[i*3 + 1][j*3 + 1] = upscaled[i*3 + 2][j*3 + 2] = 1;
            }            
        }

        int connectedRegions = 0;
        for(int i = 0; i <n *3; i++){
            for(int j = 0; j < n*3; j++){
                if(upscaled[i][j] == 0){
                    connectedRegions++;
                    dfs(upscaled, i, j);
                }
            }
        }

        return connectedRegions;
    }

    public void dfs(int[][] grid, int i, int j){
        if(i < 0 || i >= grid.length || j < 0 || j >= grid.length || grid[i][j] == 1){
            return;
        }
        grid[i][j] = 1;
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i , j - 1);
        dfs(grid, i , j + 1);
    }

    /* Important: Union-Find with different approach
     https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/ 
    */
    public int removeStones(int[][] stones) {
        HashSet<int[]> visited = new HashSet<>();

        int connectedComponents = 0;
        for(int[] s1 : stones){
            if(!visited.contains(s1)){
                //dfsStones(s1, stones, visited);
                bfs(s1, stones, visited);
                connectedComponents++;
            }
        }
        return stones.length - connectedComponents;
    }

    public void bfs(int[] s1, int[][] stones, HashSet<int[]> visited){
        Queue<int[]> q = new LinkedList<>();
        q.offer(s1);
        visited.add(s1);

        while(!q.isEmpty()){
            int size = q.size();
            
            while(size-- > 0){
                int[] curr = q.poll();
                
                for(int[] s : stones){
                    if(visited.contains(s)) continue;
                    if(s[0] == curr[0] || s[1] == curr[1]){
                        q.offer(s);
                        visited.add(s);
                    }
                }
            }
        }
    }


    public void dfsStones(int[] s1, int[][] stones, HashSet<int[]> visited){
        if(visited.contains(s1)){
            return;
        }
        visited.add(s1);
        for(int[] s2 : stones){
            if(!visited.contains(s2)){
                if(s1[0] == s2[0] || s1[1] == s2[1]){
                    dfsStones(s2, stones, visited);
                }
            }
        }
    }

    /* 
       To-do: Revisit
       Important
       https://leetcode.com/problems/remove-invalid-parentheses/
       Input: "()())()"
       Output: ["()()()", "(())()"]

       Input: "(a)())()"
       Output: ["(a)()()", "(a())()"]
    */
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if(s == null || s.length() == 0) {
            result.add("");
            return result;
        }

        Queue<String> q = new LinkedList<>();

        Set<String> visited = new HashSet<>();

        q.offer(s);
        visited.add(s);
        
        boolean found = false;

        while(!q.isEmpty()){

            int size = q.size();

            while(size-- > 0){
                String curr = q.poll();
                if(isValid(curr)){
                    result.add(curr);
                    found = true;
                }
                if(found) continue;
                for(int i = 0; i < curr.length(); i++){
                        
                    if (curr.charAt(i) != '(' && curr.charAt(i) != ')') continue;

                        String expression = curr.substring(0, i) + curr.substring(i + 1);

                        if(!visited.contains(expression)){
                            q.offer(expression);
                            visited.add(expression);
                        }
                }
            }
        }

        return result;
    }
    
    public boolean isValid(String str){
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(ch == '(') count++;
            if(ch == ')' && count-- == 0)return false;
        }
        return count == 0;
    }

}
