package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

class JuneW2 {

    public static void main(String[] args) {
        JuneW2 obj = new JuneW2();
        int[][] pairs = new int[][]{
                {1,2},
                {2,3},
                {3,4}
        };
        //obj.findLongestChain(pairs);
        //obj.printLIS(new int[]{10,9,2,5,3,7,101,18});
        //obj.largestDivisibleSubset(new int[]{2,8,18,19,20,4,16});
        int[][] times = new int[][]{{1,2,1},{2,3,2},{1,3,2}};
        obj.networkDelayTime(times, 3, 1);
        //System.out.println("res = " + res);
        //obj.findLeastNumOfUniqueInts(new int[]{4,3,1,1,3,3,2}, 3);
        /*TreeAncestor treeAncestor = new TreeAncestor(7, new int[]{-1,0,0,1,1,2,2});
        System.out.println("treeAncestor.getKthAncestor(3,1) = " + treeAncestor.getKthAncestor(3,1));
        System.out.println("treeAncestor.getKthAncestor(5,2) = " + treeAncestor.getKthAncestor(5,2));
        System.out.println("treeAncestor.getKthAncestor(6,3) = " + treeAncestor.getKthAncestor(6,3));*/
    }

    /*
    LC : 669
    https://leetcode.com/problems/trim-a-binary-search-tree/ */
    public TreeNode trimBST(TreeNode root, int l, int r) {
        return trim(root, l , r);
    }

    public TreeNode trim1(TreeNode root, int l, int r){
        if(root == null) return null;
        if(root.val == r){
            root.right = null;
            root.left = trim1(root.left, l, r);
            return root;
        }
        if(root.val == l){
            root.left = null;
            root.right = trim1(root.right, l, r);
            return root;
        }
        if(root.val < l && root.val < r){
            return trim1(root.right, l, r);
        }
        if(root.val > r && root.val > l){
            return trim1(root.left, l, r);
        }
        root.left = trim1(root.left, l, r);
        root.right = trim1(root.right, l, r);
        return root;
    }

    public TreeNode trim(TreeNode root, int l, int r) {
        if (root == null) return root;
        if (root.val > r) return trim(root.left, l, r);
        if (root.val < l) return trim(root.right, l, r);

        root.left = trim(root.left, l, r);
        root.right = trim(root.right, l, r);
        return root;
    }

    /* https://cp-algorithms.com/graph/lca_binary_lifting.html
    *  https://leetcode.com/problems/kth-ancestor-of-a-tree-node/
    *  Binary lifting
    */
    static class TreeAncestor {
        Map<Integer, List<Integer>> children = new HashMap<>();
        Integer[][] memo;
        public TreeAncestor(int n, int[] parents) {
            memo = new Integer[n][30];//30 is max depth
            for (int i = 0; i < n; i++) {
                int currNode = i;
                int parent = parents[i];
                children.computeIfAbsent(parent, value ->  new ArrayList<Integer>()).add(currNode);
                if(i > 0)memo[currNode][0] = parent;
            }
            dfs(0);
        }

        private void dfs(int currNode) {
            for (int i = 1; memo[currNode][i-1] != null ; i++) {
                int parent = memo[currNode][i-1];
                memo[currNode][i] = memo[parent][i-1];
            }
            for(int child : children.getOrDefault(currNode, new ArrayList<>())){
                dfs(child);
            }
        }

        public int getKthAncestor(int node, int k) {
            int currPow = 0;
            while (k > 0 ){
                if(k % 2 == 1){
                    if(memo[node][currPow] == null) return -1;
                    node = memo[node][currPow];
                }
                currPow++;
                k = k / 2;
            }
            return node;
        }
    }

    /*
    LC : 5454
    https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/
    */
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            freqMap.put(arr[i], freqMap.getOrDefault(arr[i], 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a,b) -> a.getValue() - b.getValue());
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()){
            pq.offer(entry);
        }
        while (k > 0){
            Map.Entry<Integer, Integer> entry = pq.poll();
            if(entry.getValue() > k){
                entry.setValue(entry.getValue() - k );
                k = entry.getValue() - k;
                pq.offer(entry);
            }else{
                k = k - entry.getValue();
            }
        }
        return pq.size();
    }

    /*
     LC : 5453
     https://leetcode.com/problems/running-sum-of-1d-array/
    */
    public int[] runningSum(int[] nums) {
        int sum = 0;
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            res[i] = sum;
        }
        return res;
    }

    /*
    LC : 1477
    https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/ */
    public int minSumOfLengths(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int n = arr.length;
        int[] minLengthAt = new int[n];
        Arrays.fill(minLengthAt, Integer.MAX_VALUE);
        int sum = 0;
        int res = Integer.MAX_VALUE;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            Integer prevIdx = map.get(sum - target);
            if(prevIdx != null){
                if(prevIdx > -1 && minLengthAt[prevIdx] != Integer.MAX_VALUE) {
                    res = Math.min(res, i - prevIdx + minLengthAt[prevIdx]);//min len of first sub-array + min len of curr sub-array
                }
                best = Math.min(best, i - prevIdx);// min len of curr sub-array
            }
            minLengthAt[i] = best;//min len of curr-sub-array at pos i
            map.put(sum, i);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    /*
     LC : 1476
     https://leetcode.com/problems/subrectangle-queries/ */
    class SubrectangleQueries {
        int[][] matrix;
        public SubrectangleQueries(int[][] rectangle) {
            matrix = rectangle;
        }

        public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
            for (int i = row1; i <= row2 ; i++) {
                for (int j = col1; j <= col2 ; j++) {
                    matrix[i][j] = newValue;
                }
            }
        }
        public int getValue(int row, int col) {
            return matrix[row][col];
        }
    }

    /*
    LC : 1475
    https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop/ */
    public int[] finalPrices(int[] prices) {
        int[] res = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            boolean flag = false;
            for (int j = i + 1; j < prices.length ; j++) {
                if(prices[j] <= prices[i] ){
                    res[i] = prices[i] - prices[j];
                    flag = true;
                    break;
                }
            }
            if(!flag){
                res[i] = prices[i];
            }
        }
        return res;
    }

    /* LC : 743
    https://leetcode.com/problems/network-delay-time/
    */
    public int networkDelayTime(int[][] times, int N, int K) {
        int maxTime = findMaxTimeDijikstra(times, N, K);
        System.out.println("maxTime = " + maxTime);
        return maxTime ;
    }

    // https://cp-algorithms.com/graph/bellman_ford.html
    public int findMaxBellmanFord(int[][] time, int N, int K){
        // Bellman-Ford O(N*E)
        // Runs N-1 times for relaxation
        // Single source shortest path
        // Can be used to detect negative cycles regardless of the starting node.
        double[] disTo = new double[N];
        Arrays.fill(disTo, Double.POSITIVE_INFINITY);
        disTo[K - 1] = 0;
        for (int i = 1; i <= N; i++) {
            for (int[]edges : time){
                int u = edges[0] - 1, v = edges[1] - 1, w = edges[2];
                if(disTo[u] + w < disTo[v]){
                    disTo[v] = disTo[u] + w;
                }
            }
        }
        double res = Double.MIN_VALUE;
        for (double val : disTo){
            res = Math.max(res, val);
        }
        return res == Double.POSITIVE_INFINITY ? -1 : (int)res;
    }

    public int findMaxFloydWarshall(int[][] times, int N, int K){
        // Floyd–Warshall algorithm O(n^3)
        double[][] distTo = new double[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
        }
        for (int i = 0; i < N; i++) {
            distTo[i][i] = 0;
        }
        for (int[] edge : times) {
            int src = edge[0];
            int dst = edge[1];
            distTo[src - 1][dst - 1] = edge[2];
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(distTo[i][j] > distTo[i][k] + distTo[k][j]){
                        distTo[i][j] = distTo[i][k] + distTo[k][j];
                    }
                }
            }
        }
        double max = Double.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            if(distTo[K-1][i] == Double.POSITIVE_INFINITY) return -1;
            max = Math.max(max, distTo[K - 1][i]);
        }
        return (int)max;
    }

    public int findMaxTimeDijikstra(int[][] times, int N, int K){
        LinkedList<int[]>[] graph = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < times.length; i++) {
            int src = times[i][0] - 1;
            int dst = times[i][1] - 1;
            int time = times[i][2];
            graph[src].add(new int[]{dst, time});
        }
        boolean[] visited = new boolean[N];
        Arrays.fill(visited, false);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
        pq.offer(new int[]{K - 1, 0});
        int totalTime = 0;
        while (!pq.isEmpty()){
            int[] cur = pq.poll();
            int currSrc = cur[0];
            int currtime = cur[1];
            if(visited[currSrc]) continue;
            visited[currSrc] = true;
            totalTime = Math.max(totalTime, currtime);
            N--;
            for (int i = 0; i < graph[currSrc].size(); i++) {
                int[] next = graph[currSrc].get(i);
                int nextSrc = next[0];
                int time = next[1];
                time = currtime + time;
                pq.offer(new int[]{nextSrc, time});
            }
        }
        for (int i = 0; i < visited.length; i++) {
            if(!visited[i]){
                totalTime = 0;
                break;
            }
        }
        return N == 0 ? totalTime : -1;
    }

    /*
    LC : 510
    https://leetcode.com/problems/inorder-successor-in-bst-ii/
    */
    class NodeX {
        public int val;
        public NodeX left;
        public NodeX right;
        public NodeX parent;
    };
    public NodeX inorderSuccessor(NodeX node) {
        if(node.right == null){
            NodeX parent = node.parent;
            while (parent != null && parent.val < node.val){
                parent = parent.parent;
            }
            return parent;
        }else {
            node = node.right;
            while (node.left != null){
                node = node.left;
            }
            return node;
        }
    }

    /*
    LC : 285
    https://leetcode.com/problems/inorder-successor-in-bst/
    */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        return inorderSuccessorRec(root, p);
    }
    public TreeNode inorderSuccessorRec(TreeNode root, TreeNode p){
        if(root ==null) return null;

        if(root.val <= p.val){
            return inorderSuccessorRec(root.right, p);
        }else{
            TreeNode left = inorderSuccessorRec(root.left, p);
            return (left != null) ? left : root;
        }
    }

    public TreeNode inorderSuccessorRec1(TreeNode root, TreeNode p){
        while (root != null && root.val <= p.val ){
            root = root.right;
        }
        if(root == null) return null;
        TreeNode left = inorderSuccessorRec1(root.left, p);
        return left != null ? left : root;
    }

    public TreeNode inorderPredecessor(TreeNode root, TreeNode p){
        if(root == null){
            return null;
        }
        if(root.val >= p.val){
            return inorderPredecessor(root.left, p);
        }else{
            TreeNode right = inorderPredecessor(root.right, p);
            return (right != null) ? right : root;
        }
    }

    public TreeNode inorderSuccessorIterative(TreeNode root, TreeNode p) {
        if(p.right != null){
            p = p.right;
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        Stack<TreeNode> stack = new Stack<>();
        int inorder = Integer.MAX_VALUE;
        while (!stack.isEmpty() || root != null){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(inorder == p.val){
                return root;
            }
            inorder = root.val;
            root = root.right;
        }
        return null;
    }

    /*
    LC : 368
    https://leetcode.com/problems/largest-divisible-subset/
    Solution: https://leetcode.com/problems/largest-divisible-subset/discuss/683681/JAVA-DP-solution-lessLISgreater
    https://cp-algorithms.com/sequences/longest_increasing_subsequence.html
    */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if(nums.length == 0){
            return new ArrayList<>();
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int[] parent = new int[nums.length];
        Arrays.fill(parent, -1);
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if((nums[i] % nums[j] == 0) && dp[i] < dp[j] + 1){
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
        }
        int max = dp[0], pos = 0;
        for (int i = 1; i < nums.length ; i++) {
            if(dp[i] > max){
                max = dp[i];
                pos = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (pos != -1){
            res.add(nums[pos]);
            pos = parent[pos];
        }
        Collections.reverse(res);
        return res;
    }

    /*
    LC : 300
    https://leetcode.com/problems/longest-increasing-subsequence/
    */
    public int lengthOfLIS(int[] nums) {
        return lengthOfLISPatienceSort(nums);
    }
    public int lengthOfLisDP(int[] nums) {
        int LIS[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            LIS[i] = 1;
        }
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[j] < nums[i]){
                    LIS[i] = Math.max(LIS[i], LIS[j]+1);
                }
            }
        }
        return Arrays.stream(LIS).max().getAsInt();
    }

    public int lengthOfLISPatienceSort(int[] nums) {
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }
    class Node implements Comparable<Node> {
        int val;
        Node prev;
        public Node(int val){
            this.val = val;
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.val, that.val);
        }
    }

    public void printLIS(int[] nums){
        List<Node> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            Node node = new Node(num);
            int pile = Collections.binarySearch(piles, node);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT, Bitwise Complement
            }
            if(pile != 0){
                node.prev = piles.get(pile-1);
            }
            if (pile == piles.size()) {
                piles.add(node);
            } else {
                piles.set(pile, node);
            }
        }
        extractLIS(piles);
    }

    private void extractLIS(List<Node> piles) {
        List<Integer> result = new ArrayList<>(piles.size());
        for (Node curr = piles.isEmpty() ? null : piles.get(piles.size() - 1); curr != null; curr = curr.prev) {
            result.add(curr.val);
        }
        Collections.reverse(result);
        System.out.println("result = " + result);
    }

    /*
    LC : 792
    https://leetcode.com/problems/number-of-matching-subsequences/
    */
    public int numMatchingSubseq(String target, String[] words) {
        int count = 0;
        HashMap<String, Boolean> map = new HashMap<>();
        for(String source : words){
            if(map.containsKey(source)){
                if(map.get(source)){
                    count++;
                }
                continue;
            }
            if(isSubSequence(source, target)){
                count++;
                map.put(source, true);
            }else{
                map.put(source, false);
            }
        }
        return count;
    }

    public boolean isSubSequence(String s, String t){
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /* 
    LC : 1055
    https://leetcode.com/problems/shortest-way-to-form-string/
    */
    public int shortestWay(String source, String target) {
        for (int i = 0; i < target.length(); i++) {
            String ch = target.charAt(i)+ "";
            if(!source.contains(ch)){ // This character doesn't exists
                return -1;
            }
        }
        Queue<String> q = new LinkedList<>();
        q.offer(target);
        int min_steps = 0;
        while (!q.isEmpty()){
            String curr = q.poll();
            int i = 0, j = 0;
            while (i < curr.length() && j < source.length()){
                if(curr.charAt(i) == source.charAt(j)){
                    i++;
                }
                j++;
            }
            if(i < curr.length()){
                String rem = curr.substring(i);
                q.offer(rem);
            }
            if(i == curr.length()){
                break;
            }
            min_steps++;
        }
        System.out.println("min_steps = " + min_steps);
        return min_steps + 1;
    }


    /* 
    LC : 1253
    https://leetcode.com/problems/reconstruct-a-2-row-binary-matrix/
    */
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        List<List<Integer>> res = new ArrayList<>();
        
        int[][] matrix = new int[2][colsum.length];
        int upCount = 0, lowCount = 0;
        
        for(int i = 0; i < colsum.length; i++){
            if(colsum[i] == 2){
                matrix[0][i] = 1;
                matrix[1][i] = 1;
                upper--;
                lower--;
            }
        }
        
        for(int i = 0; i < colsum.length; i++){
            if(colsum[i] == 1){
                if(upper > 0){
                    matrix[0][i] = 1;    
                    upper--;
                }else if(lower > 0){
                    matrix[1][i] = 1;
                    lower--;
                }else{
                    return res;
                }
            }
        }
        if(upper != 0 || lower != 0){
            return res;
        }
        
        for(int i = 0; i < 2; i++){
            List<Integer> row = new ArrayList<>();
            for(int j = 0; j < colsum.length; j++){
                row.add(matrix[i][j]);
            }
            res.add(row);
        }

        return res;  
    }


    /* 
    LC : 809
    https://leetcode.com/discuss/interview-question/679321/google-onsite-determine-if-word-is-typo-because-of-stuck-key
    https://leetcode.com/problems/expressive-words/
    */
    public int expressiveWords(String s, String[] words) {
        int ans = 0;
        for(String word : words){
            if(isStretchy(s, word)){
                ans++;
            }
        }
        System.out.println("ans = " + ans);
        return ans;
    }

    public boolean isStretchy(String stretchy, String word){
        int wordPtr = 0;
        int strPtr = 0;
        int m = word.length();
        int n = stretchy.length();
        while (true){
            if(wordPtr == m && strPtr == n) return true;
            if(wordPtr == m || strPtr == n) return false;
            if(word.charAt(wordPtr) != stretchy.charAt(strPtr)) return false;
            int prevwordPtr = wordPtr;
            while(wordPtr < m-1 && word.charAt(wordPtr) == word.charAt(wordPtr + 1)){
                wordPtr++;
            }
            int wLen = wordPtr - prevwordPtr + 1;

            int prevstrPtr = strPtr;
            while(strPtr < n-1 && stretchy.charAt(strPtr) == stretchy.charAt(strPtr + 1)){
                strPtr++;
            }

            int sLen = strPtr - prevstrPtr + 1;
            if(sLen == wLen){
                wordPtr++;
                strPtr++;
                continue;
            }
            if(sLen < 3 || sLen < wLen){
                return false;
            }
        }
    }

    /*
    LC : 916
    https://leetcode.com/problems/word-subsets/
    */
    public List<String> wordSubsets(String[] A, String[] B) {
        HashMap<String, HashMap<Character,Integer>> mapA = new HashMap<>();
        for (String a : A){
            char[] arr =  a.toCharArray();
            HashMap<Character, Integer> freqMap = new HashMap<>();
            for (char ch : arr ){
                freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
            }
            mapA.put(a, freqMap);
        }

        HashMap<Character,Integer> mapB = new HashMap<>();
        for (String b : B){
            char[] arr =  b.toCharArray();
            int[] freq = new int[26];
            for (char ch : arr ){
                freq[ch - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                char curr = (char)(i + 'a');
                int maxFreq = Math.max(freq[i], mapB.getOrDefault(curr, 0));
                if(maxFreq > 0) {
                    mapB.put(curr, maxFreq);
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            String str = A[i];
            HashMap<Character, Integer> freqA = mapA.get(str);
            boolean found = true;
            for (Map.Entry<Character, Integer> entry : mapB.entrySet()){
                Character ch = entry.getKey();
                int freq = entry.getValue();
                if(!freqA.containsKey(ch) || freqA.get(ch) < freq){
                    found = false;
                    break;
                }
            }
            if(found) {
                res.add(A[i]);
            }
        }
        return res;
    }


    /* 
    LC : 1170
    https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
    */
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        ArrayList<Integer> res = new ArrayList<Integer>();

        for(String q : queries){
            int qFreq = func(q);
            int ans = 0;
            for(String word : words){
                if(qFreq < func(word)){
                    ans++;
                }
            }
            res.add(ans);
        }
        return res.stream().mapToInt(i->i).toArray();
    }

    public int func(String s){
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        char smallest = arr[0];
        int i = 0;
        while(i < arr.length && smallest == arr[i]){
            i++;
        }
        return i + 1;
    }   

    /*
    LC : 1152
    https://leetcode.com/problems/analyze-user-website-visit-pattern/
    */
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        HashMap<String, ArrayList<String[]>> userMap = new HashMap<>();
        int n = username.length;
        for (int i = 0; i < n; i++) {
            String user = username[i];
            String url = website[i];
            String time = String.valueOf(timestamp[i]);
            userMap.putIfAbsent(user, new ArrayList<>());
            userMap.get(user).add(new String[]{url, time});
        }
        Map<String, Integer> count = new HashMap<>();
        String res = "";
        for (String key : userMap.keySet()) {
            HashSet<String> sets = new HashSet<>();
            List<String[]> list = userMap.get(key);
            Collections.sort(list, (a, b) -> Integer.parseInt(a[1]) - Integer.parseInt(b[1]));

            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    for (int k = j + 1; k < list.size(); k++) {
                        String str = list.get(i)[0] + " " + list.get(j)[0] + " " + list.get(k)[0];
                        if (!sets.contains(str)) {
                            count.put(str, count.getOrDefault(str, 0) + 1);
                            sets.add(str);
                        }
                        if (res.equals("") || count.get(res) < count.get(str) || (count.get(str) == count.get(res) && res.compareTo(str) > 0)) {
                            res = str;
                        }
                    }
                }
            }
        }
        System.out.println("res = " + res);
        String[] arr = res.split(" ");
        return new ArrayList<>(Arrays.asList(arr));
    }

    /*
    LC : 354
    https://leetcode.com/problems/russian-doll-envelopes
    Longest Increasing subsequence(LIS)
    */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a,b) -> a[0] - b[0]);
        int n = envelopes.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < envelopes.length; i++) {
            for (int j = 0; j < i; j++) {
                if(envelopes[i][0] == envelopes[j][0] || envelopes[i][1] == envelopes[j][1])
                    continue;
                if(envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if(max < dp[i]){
                max = dp[i];
            }
        }
        return max;
    }

    /*
    LC : 334
    https://leetcode.com/problems/increasing-triplet-subsequence/
    */
    public boolean increasingTriplet(int[] nums) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        for(int n : nums){
            if(a >= n){
                a = n;
            }else if(b >= n){
                b = n;
            }else{
                return true;
            }
        }
        return false;
    }

    /*
    LC : 646
    https://leetcode.com/problems/maximum-length-of-pair-chain/
    Longest Increasing subsequence(LIS)
    */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a,b) -> a[0] - b[0]);
        return findLongestChainDP(pairs);
    }

    public int findLongestChainDP(int[][] nums) {
        int LIS[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            LIS[i] = 1;
        }
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[j][1] < nums[i][0]){
                    LIS[i] = Math.max(LIS[i], LIS[j]+1);
                }
            }
        }
        int max = 0;
        for(int i = 0; i < LIS.length; i++){
            if(max < LIS[i]){
                max = LIS[i];
            }
        }
        return max;
    }

    /*
    LC : 740
    https://leetcode.com/problems/delete-and-earn/
    */
    public int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        Set<Integer> sets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            sets.add(nums[i]);
        }
        int[] temp = sets.stream().mapToInt(i -> i).toArray();
        int[] memo = new int[temp.length];
        Arrays.fill(memo, -1);
        return helperMaxMemo(temp, temp.length - 1, map, memo);
    }

    public int helperMaxMemo(int[] nums, int n, HashMap<Integer, Integer> map, int[] memo) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        if(memo[n] != -1){
            return memo[n];
        }
        int curr = nums[n];
        int prev = nums[n - 1];
        int max = 0;
        if (curr == prev + 1) {
            max = Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMaxMemo(nums, n - 2, map, memo), helperMaxMemo(nums, n - 1, map, memo));
        } else {
            max = (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMaxMemo(nums, n - 1, map, memo), helperMaxMemo(nums, n - 2, map, memo));
        }
        return memo[n] = max;
    }

    public int helperMax(int[] nums, int n, HashMap<Integer, Integer> map) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        int curr = nums[n];
        int prev = nums[n - 1];

        if (curr == prev + 1) {
            return Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMax(nums, n - 2, map), helperMax(nums, n - 1, map));
        } else {
            return (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMax(nums, n - 1, map), helperMax(nums, n - 2, map));
        }
    }

    /*
     LC : 361
     https://leetcode.com/problems/bomb-enemy/
     */
    class Location{
        int top;
        int bottom;
        int left;
        int right;
    }

    public int maxKilledEnemies(char[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        Location[][] matrix = new Location[m][n];

        for(int i = 0; i < m; i++ ){
            for(int j = 0; j < n; j++){
                matrix[i][j] = new Location();
                if(grid[i][j] == 'W') continue;
                matrix[i][j].top = (i == 0 ? 0 : matrix[i-1][j].top) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].left = (j == 0 ? 0 : matrix[i][j-1].left) + (grid[i][j] == 'E' ? 1 : 0);
            }
        }

        int maxCount = 0;
        for(int i = m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(grid[i][j] == 'W') continue;
                matrix[i][j].bottom = (i == m -1 ? 0 : matrix[i+1][j].bottom) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].right = (j == n -1 ? 0 : matrix[i][j+1].right) + (grid[i][j] == 'E' ? 1 : 0);   

                if(grid[i][j] == '0'){
                    maxCount = Math.max(maxCount, matrix[i][j].top + matrix[i][j].bottom + matrix[i][j].left + matrix[i][j].right);
                }
            }
        }
        return maxCount;
    }

    /*
    LC : 311
    https://leetcode.com/problems/sparse-matrix-multiplication/
    */
    public int[][] multiply(int[][] A, int[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        int[][] res = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                if(A[i][j] != 0) {
                    for (int k = 0; k < colsB; k++) {
                        if (B[j][k] != 0) res[i][k] += A[i][j] * B[j][k];
                    }
                }
            }
        }
        return res;
    }

    /*
    LC : 838
    https://leetcode.com/problems/push-dominoes/
    */
    public String pushDominoes(String dominoes) {
        int right = -1;
        int left = -1;
        char[] out = dominoes.toCharArray();
        int n = dominoes.length();
        for (int i = 0; i <= n ; i++) {
            if(i == n || dominoes.charAt(i) == 'R'){
                if(right > left) {// R...R, ...R
                    while (right < i){
                        out[right++] = 'R';
                    }
                }
                right = i;
            }else if(dominoes.charAt(i) == 'L'){
                if(left > right || (right  == -1 && left == -1)){ // L...L, ...L
                    while (left < i){
                        out[++left] = 'L';
                    }
                }else{ // R...L
                    left = i;
                    int mid = right + (left - right) / 2;
                    if((left - right + 1) % 2 == 1){
                        out[mid] =  '.';
                    }
                    int low = right + 1, high = left - 1;
                    while (low  < high){
                        out[low] = 'R';
                        out[high] = 'L';
                        low++;
                        high--;
                    }
                }
            }
        }
        String res = new String(out);
        System.out.println("out = " + res);
        return res;
    }
}
