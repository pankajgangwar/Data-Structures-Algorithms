package com.mission.google.leetcode;
import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class NovemberW4 {

    public static void main(String[] args){
        NovemberW4 w4 = new NovemberW4();
        //w4.knightDialer(1);
        w4.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3);
    }

    /*
        https://leetcode.com/problems/sliding-window-maximum/
        239. Sliding Window Maximum
    */
    
    public int[] maxSlidingWindow(int[] nums, int k) {
        return maxSlidingWindowUsingDeque(nums, k);
    }

    public int[] maxSlidingWindowUsingDeque(int[] nums, int k) {
        int n = nums.length;
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int[] result = new int[n - k + 1];
        
        int ri = 0;
        Deque<Integer> dq = new ArrayDeque<>();

        for(int i = 0; i < n; i++){
            while(!dq.isEmpty() && dq.peek() < i - k + 1){
                dq.poll();//Remove max ele index
            }
            while(!dq.isEmpty() && nums[dq.peekLast()] < nums[i]){
                dq.pollLast(); //Remove index with smaller ele, not required
            }

            dq.offer(i);
            if(i >= k -1){
                result[ri++] = nums[dq.peek()];
            }
        }
        return result;
    }
    
    public int[] maxSlidingWindowUsingHeap(int[] nums, int k) {
        int n = nums.length;
        if(n == 0) return new int[0];
        int[] result = new int[n - k + 1];
        
        int start = 0;
        int end = 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(; end < nums.length; end++){
            maxHeap.offer(nums[end]);
            
            if(maxHeap.size() > k){
                maxHeap.remove(nums[start]);
                start++;
            }
            
            if(maxHeap.size() == k){
                result[start] = maxHeap.peek();
            }
        }
        return result;
    }

    /*
        Revisit : To solve in O(1) space
        https://leetcode.com/problems/find-mode-in-binary-search-tree/
    */
    int max = 0;
    public int[] findMode(TreeNode root) {

        if(root==null) return new int[0]; 

        findModeRec(root);
        
        PriorityQueue<int[]> max_heap = new PriorityQueue<>( (a,b) -> -a[0]-a[1] + b[0]+b[1]);//Bucket sort
        for(Map.Entry<Integer,Integer> entry : freq_map.entrySet()){
            int[] res = new int[2];
            res[0] = entry.getKey();
            res[1] = entry.getValue();
            max_heap.offer(res);
        }
        
        List<Integer> result = new ArrayList<>();
        while(!max_heap.isEmpty()){
            int[] curr = max_heap.poll();
            if(curr[1] == max){ // If more than 1 freq
                result.add(curr[0]);
            }
        }
        
        int[] op = new int[result.size()];
        
        for(int idx = 0; idx < result.size(); idx++){
            op[idx] = result.get(idx);
        }
        return op;
    }
    
    Map<Integer,Integer> freq_map = new HashMap<>();
    public void findModeRec(TreeNode root){
        if(root == null) return;
        findModeRec(root.left);
        int val = root.val;
        freq_map.put(root.val, freq_map.getOrDefault(root.val, 0) + 1);
        max = Math.max(max, freq_map.get(root.val));
        findModeRec(root.right);
    }

    int maxUniValueLength = 0;
    public int longestUnivaluePath(TreeNode root) {
        return maxUniValueLength;
    }

    public int getMaxUniValue(TreeNode root){
        if(root == null) return 0;

        int left = getMaxUniValue(root.left);
        int right = getMaxUniValue(root.right);

        int maxLeft = 0;// As this node cuts the streak of longest path with same values
        if(root.left != null && root.val == root.left.val){
            maxLeft = left + 1;
        }
        int maxRight = 0;
        if(root.right != null && root.val == root.right.val){
            maxRight = right + 1;
        }
        maxUniValueLength = Math.max(maxUniValueLength, maxLeft + maxRight);
        return Math.max(maxLeft, maxRight);
    }

    
    int maxDiameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return maxDiameter;
    }

    private int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        maxDiameter = Math.max(maxDiameter, left + right);

        return Math.max(left, right) + 1;
    }

    /*
        https://leetcode.com/problems/validate-binary-search-tree/
    */
     public boolean isValidBST(TreeNode root) {
        return validateBSTRec(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean validateBSTRec(TreeNode root,long min, long max){
        if(root == null) return true;
        
        if(root.val <= min || root.val >= max) return false;

        return validateBSTRec(root.left, min, root.val) && validateBSTRec(root.right, root.val, max);
    }
    
    //Tree Inorder Traversal
    public boolean validateBSTUsingStack(TreeNode root){
        TreeNode prev = null;
        if(root == null) return true;
        Stack<TreeNode> stack = new Stack<TreeNode>();

        while(root != null || !stack.isEmpty()){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(prev != null && prev.val >= root.val) return false;
            prev = root;
            root = root.right;
        }
        return true;
    }

    /*https://leetcode.com/problems/kth-smallest-element-in-a-bst/*/
    public int kthSmallest(TreeNode root, int k) {
        int kthSmallest = 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while(root != null || !stack.isEmpty()){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(--k == 0) break;
            root = root.right;
        }
        if(root != null){
            kthSmallest = root.val;
        }
        return kthSmallest;
    }

    /*
      https://leetcode.com/problems/pacific-atlantic-water-flow/
      417. Pacific Atlantic Water Flow

       Pacific ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

        Revisit : To-Do// With DFS

    */

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;

        int m = matrix.length;
        int n = matrix[0].length;

        if(matrix == null || m == 0 || n == 0) return res;

        Queue<int[]> aQueue = new LinkedList<>();
        Queue<int[]> pQueue = new LinkedList<>();

        boolean[][] aVisited = new boolean[m][n];
        boolean[][] pVisited = new boolean[m][n];

        for(int row = 0; row < m; row++ ){
            pQueue.offer(new int[]{row, 0});
            aQueue.offer(new int[]{row, n - 1});

            pVisited[row][0] = true;
            aVisited[row][n - 1] = true;
        }

        for(int col = 0; col < n; col++ ){
            pQueue.offer(new int[]{0, col});
            aQueue.offer(new int[]{m - 1, col});

            pVisited[0][col] = true;
            aVisited[m -1][col] = true;
        }

        bfs(matrix, pQueue, pVisited);
        bfs(matrix, aQueue, aVisited);

        for(int row = 0; row < m; row++){
            
            for(int col = 0; col < n; col++){
                if(pVisited[row][col] && aVisited[row][col]){
                    List<Integer> curr = new ArrayList<>();
                    curr.add(row);
                    curr.add(col);
                    res.add(curr);
                }
            }            
        }
        return res;

    }

    public void bfs(int[][] matrix, Queue<int[]> queue, boolean[][] visited) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};

        int m = matrix.length;
        int n = matrix[0].length;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            for(int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= m || next_y < 0 || next_y >= n || visited[next_x][next_y] || matrix[next_x][next_y] < matrix[curr[0]][curr[1]] )
                    continue;

                queue.offer(new int[]{next_x, next_y});
                visited[next_x][next_y] = true;
            }
        }
    }


    /* 
        863. All Nodes Distance K in Binary Tree
        https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
        ToDo : DFS
        Important
    */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();
        if(root == null || K < 0) return res;
        buildMap(root, null);
        
        if(!map.containsKey(target)) return res;

        Set<TreeNode> visited = new HashSet<>();
        visited.add(target);

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(target);

        while(!q.isEmpty()){

            if(K == 0){
                while(!q.isEmpty()){
                    res.add(q.poll().val);
                }
            }

            int size = q.size();

            while(size-- > 0){
                TreeNode curr = q.poll();
                visited.add(curr);

                List<TreeNode> neighbours = map.get(curr);
                for(TreeNode adj : neighbours){
                    if(visited.contains(adj)) continue;
                    q.offer(adj);
                    visited.add(adj);
                }
            }
            K--;
        }
        return res;
    }

    Map<TreeNode, List<TreeNode>> map = new HashMap<>();
    public void buildMap(TreeNode root, TreeNode parent){
        if(root == null) return;
        if(!map.containsKey(root)){
            map.put(root, new ArrayList<TreeNode>());
            if(parent != null){
                map.get(root).add(parent);
                map.get(parent).add(root);
            }
            buildMap(root.left, root);
            buildMap(root.right, root);
        }
    }

    /*  Geometry, Math
        https://leetcode.com/problems/minimum-time-visiting-all-points/
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int result = 0;
        for (int i = 1; i < points.length; i++) {
            int[] prev = points[i];
            int[] curr = points[i - 1];
            result += Math.max(Math.abs(curr[0] - prev[0]), Math.abs(curr[1] - prev[1]));
        }
        return result;
    }


    /*
    * https://leetcode.com/problems/count-servers-that-communicate/
      1267. Count Servers that Communicate

    * */
    public int countServers(int[][] grid) {
        int[] rows = new int[grid.length];
        int[] cols = new int[grid[0].length];

        for(int i = 0; i < rows.length; i++){
            for (int j = 0; j < cols.length; j++) {
                if(grid[i][j] == 1){
                    rows[i]++;
                    cols[j]++;
                }
            }
        }

        int result = 0;
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < cols.length; j++) {
                if(grid[i][j] == 1 && (rows[i] > 1 || cols[j] > 1)){
                    result++;
                }
            }
        }

        return result;
    }


    class TrieNode {
        boolean isEndOfWord = false;
        int TOTAL_ALPHABETS = 26;
        TrieNode[] children = new TrieNode[TOTAL_ALPHABETS];
        List<String> list_words = new ArrayList<>();

        public TrieNode() {
            for (int i = 0; i < TOTAL_ALPHABETS; i++) {
                children[i] = null;
            }
        }
    }

    TrieNode root;
    /*
        https://leetcode.com/problems/search-suggestions-system/
    */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        root = new TrieNode();
        for(String product : products) {
            addword(product);
        }

        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < searchWord.length(); i++) {
            String search_sub = searchWord.substring(0, i + 1);
            List<String> all_sub_products = search(search_sub);
            result.add(all_sub_products);
        }
        return result;
    }

    public void addword(String word) {
        TrieNode pCrawl = root;
        int length = word.length();
        for (int level = 0; level < length; level++) {
            int idx = word.charAt(level) - 'a';
            if (pCrawl.children[idx] == null) {
                pCrawl.children[idx] = new TrieNode();
            }
            List<String> wordsWithThisChar = pCrawl.children[idx].list_words;
            if(wordsWithThisChar.size() < 3){
                wordsWithThisChar.add(word);
            }
            pCrawl = pCrawl.children[idx];
        }
        pCrawl.isEndOfWord = true;
    }

    public List<String> search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if(node.children[ch - 'a'] == null)
                return new ArrayList<>();

            node = node.children[ch - 'a'];
        }
        return node.list_words;
    }


    /* 1269. Number of Ways to Stay in the Same Place After Some Steps
    https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/ 
    */
     public int numWays(int steps, int arrLen) {
        long[][] memo = new long[steps/2 + 1][steps + 1];
        for(long[] arr : memo){
            Arrays.fill(arr, -1);
        }
        return (int)numWaysMemo(0, steps, arrLen, memo);
        //return numWaysRec(0, steps, arrLen);
    }

    static int mod = (int)Math.pow(10, 9) + 7;
    
    public long numWaysMemo(int i, int steps, int arrLen, long[][] memo){
        if(steps == 0 && i == 0){
            return 1;
        }
        if(i < 0 || i >= arrLen || steps == 0 || i > steps ){
            return 0;
        }
        if(memo[i][steps] != -1){
            return memo[i][steps];
        }
        memo[i][steps] = ((numWaysMemo(i + 1, steps -1, arrLen, memo)  % mod + numWaysMemo(i - 1, steps -1, arrLen, memo) % mod )
            + numWaysMemo(i, steps - 1, arrLen, memo)) % mod;
        return memo[i][steps];
    }
    
    public int numWaysRec(int i, int steps, int arrLen){
        if(steps == 0 && i == 0){
            return 1;
        }
        if(i < 0 || i >= arrLen || steps == 0 || i > steps ){
            return 0;
        }
        return ((numWaysRec(i + 1, steps -1, arrLen)  % mod + numWaysRec(i - 1, steps -1, arrLen) % mod) 
            + numWaysRec(i, steps - 1, arrLen)) % mod;
    }

    /*  935. Knight Dialer
        https://leetcode.com/problems/knight-dialer/
        https://leetcode.com/problems/knight-dialer/discuss/189287/O(n)-time-O(1)-space-DP-solution-%2B-Google-interview-question-writeup
    */

    static int mod_knight = (int)1e9+7;

    public int knightDialer(int N) {
        int[][] grid = new int[4][3];
        int x = 1;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++) {
                if((i == 3 && j == 0) || (i == 3 && j == 2) ){
                    grid[i][j] = -1;
                }else if(i == 3 && j == 1){
                    grid[i][j] = 0;
                }else{
                    grid[i][j] = x;
                    x++;
                }
            }
        }

        long memo[][][] = new long[N+1][4][3];

        long uniquePaths = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + "\t");
                 if(grid[i][j] != -1){
                    uniquePaths = (uniquePaths + knightDialerMem(N, grid, i, j, memo)) % mod_knight;
                }
            }
            System.out.println();
        }
        return (int)uniquePaths;
    }

    public long knightDialerMem(int n, int[][]grid, int curr_x, int curr_y, long[][][] memo){
        if(n == 1){
            return 1;
        }
        if(memo[n][curr_x][curr_y] > 0){
            return memo[n][curr_x][curr_y];
        }
        int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };

        for(int i = 0 ; i < x_move.length; i++) {
            int next_x = x_move[i] + curr_x;
            int next_y = y_move[i] + curr_y;

            if(next_x < 0 || next_y < 0 || next_x >= grid.length || next_y >= grid[0].length
                    || grid[next_x][next_y] == -1){
                continue;
            }
            memo[n][curr_x][curr_y] += knightDialerMem(n -1, grid, next_x, next_y, memo) % mod_knight;
        }
        return memo[n][curr_x][curr_y];
    }

    public int knightDialerRec(int N, int[][] grid, int curr_x, int curr_y) {
        if(N == 1){
            return 1;
        }
        int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };
        int ways = 0;
        for(int i = 0 ; i < x_move.length; i++) {
            int next_x = x_move[i] + curr_x;
            int next_y = y_move[i] + curr_y;

            if(next_x < 0 || next_y < 0 || next_x >= grid.length || next_y >= grid[0].length
                    || grid[next_x][next_y] == -1){
                continue;
            }
            ways += knightDialerRec(N -1, grid, next_x, next_y) % mod_knight;
        }
        return ways;
    }

    /*
        https://leetcode.com/problems/construct-string-from-binary-tree/
    */
    public String tree2str(TreeNode t) {
        if(t == null) return "";
        formStringTree(t,builder);
        return builder.toString();
    }
    
    StringBuilder builder = new StringBuilder();
    public void formStringTree(TreeNode t, StringBuilder builder){
        builder.append(t.val);

        if(t.left == null && t.right == null) return;

        if(t.left != null){
            builder.append("(");
            formStringTree(t.left, builder);
            builder.append(")");
        }

        if(t.right != null){
            if(t.left == null){
                builder.append("()");       
            }
            builder.append("(");
            formStringTree(t.right, builder);
            builder.append(")");
        }
    }

    public String tree2StrRec(TreeNode t){
        if(t == null) return "";
        String result = t.val + "";

        String left = tree2StrRec(t.left);
        String right = tree2StrRec(t.right);
        if(left == "" && right == "") return "";
        if(left == "") return result + "()" + "(" + right +")";
        if(right == "") return result + "(" + left +")" + "()";
        return result + "(" + left + ")" + "(" + right + ")";
    }

    /*
        https://leetcode.com/problems/cousins-in-binary-tree/
    */
    public boolean isCousins(TreeNode root, int x, int y) {
        isCousinsDFS(root, x, y, 0, null);
        if(xDepth == yDepth){
            return xParent != yParent;
        }
        return false;
    }
    
    int xDepth = 0;
    int yDepth = 0;
    TreeNode xParent = null;
    TreeNode yParent = null;
    public void isCousinsDFS(TreeNode root, int x, int y, int depth, TreeNode parent) {
        if(root == null) return;
        if(root != null){
            if(root.val == x){
                xDepth = depth;
                xParent = parent;
            }
            if(root.val == y){
                yDepth = depth;
                yParent = parent;
            }
        }

        isCousinsDFS(root.left, x, y, depth + 1, root);
        isCousinsDFS(root.right, x, y, depth + 1, root);
        
    }

    public boolean isCousinsBfs(TreeNode root, int x, int y) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        TreeNode xParent = null;
        TreeNode yParent = null;

        while(!q.isEmpty()){
            int size = q.size();
            while(size-- > 0){
                TreeNode curr = q.poll();
                if(curr.left != null){
                    if(curr.left.val == x){
                        xParent = curr;
                    }else if(curr.left.val == y){
                        yParent = curr;
                    }
                    q.offer(curr.left);
                }
                if(curr.right != null){
                    if(curr.right.val == x){
                        xParent = curr;
                    }else if(curr.right.val == y){
                        yParent = curr;
                    }
                    q.offer(curr.right);
                }
                if(xParent != null && yParent != null){
                    break;
                }
            }

            if(xParent != null && yParent != null){
                return xParent != yParent;
            }

            if((xParent == null && yParent != null) || (xParent != null && yParent == null)){
                return false;
            }
        }
        
        return false;
    }

    /*
        https://leetcode.com/problems/find-largest-value-in-each-tree-row/
    */
    public List<Integer> largestValues(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();

        q.offer(root);

        List<Integer> result = new ArrayList<>();

        while(!q.isEmpty()){
            int size = q.size();
            int max = -1;
            while(size-- > 0){
                TreeNode curr = q.poll();
                max = Math.max(curr.val, max);
                if(curr.left != null){
                    q.offer(curr.left);
                }
                
                if(curr.right != null){
                    q.offer(curr.right);
                }
            }
            result.add(max);
        }
        return result;
    }

    /*
        https://leetcode.com/problems/maximum-width-of-binary-tree/
    */
    public int widthOfBinaryTree(TreeNode root) {
        if(root == null) return 0;
        
        Queue<TreeNode> q = new LinkedList<>();
        
        Map<TreeNode, Integer> map = new HashMap<>();
        
        map.put(root, 1);
        q.offer(root);
        
        int maxWidth = 0;
        while(!q.isEmpty()){
            int size = q.size();
            int start = 0;
            int end = 0;

            for(int i = 0; i < size; i++){
                TreeNode curr = q.poll();
                if(i == 0) start = map.get(curr);
                if(i == size - 1) end = map.get(curr);
                if(curr.left != null) {
                    q.offer(curr.left);
                    map.put(curr.left, 2*map.get(curr));
                }
                if(curr.right != null) {
                    q.offer(curr.right);
                    map.put(curr.right, 2*map.get(curr) + 1);
                }

                int currMax = end - start + 1;

                maxWidth = Math.max(currMax, maxWidth);
            }
        }
        return maxWidth;
    }

    /*
        https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
    */
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        
        TreeMap<Integer, TreeMap<Integer,PriorityQueue<Integer>>> map = new TreeMap<>();
        findHorizontalDistance(root, 0, 0, map);
        List<List<Integer>> result = new ArrayList<>();
        
        for(TreeMap<Integer,PriorityQueue<Integer>> yValues : map.values()){
            result.add(new ArrayList<>());
            for(PriorityQueue<Integer> pq : yValues.values()){
                while(!pq.isEmpty()){
                    result.get(result.size()-1).add(pq.poll());
                }
            }
        }
        
        return result;
    }
    
    
    public void findHorizontalDistance(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer,PriorityQueue<Integer>>> map){
        if(root == null){
            return;
        }
        if(map.get(x) == null){
            map.put(x, new TreeMap<>(Collections.reverseOrder()));
        }
        if(map.get(x).get(y) == null){
            map.get(x).put(y, new PriorityQueue<>());
        }
        map.get(x).get(y).offer(root.val);
        
        findHorizontalDistance(root.left, x - 1, y - 1, map);
        findHorizontalDistance(root.right, x + 1, y - 1, map);
    }

    /*
        https://leetcode.com/problems/find-duplicate-subtrees/
    */
    List<TreeNode> result = new ArrayList<>();
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        HashMap<String, Integer> hashmap = new HashMap<String,Integer>();
        inorderFindDuplicates(root, hashmap);
        return result;
    }

    public String inorderFindDuplicates(TreeNode root, HashMap<String, Integer> hashmap){
        if(root == null){
            return "";
        }

        String str = "(";
        str += inorderFindDuplicates(root.left, hashmap);
        str += root.val;
        str += inorderFindDuplicates(root.right, hashmap);
        str += ")";

        if(hashmap.getOrDefault(str, 0) == 1) result.add(root);
        hashmap.put(str, hashmap.getOrDefault(str, 0) + 1);

        return str;
    }

    /*
        https://leetcode.com/problems/search-a-2d-matrix-ii/

        [
         [1,   4,  7, 11, 15],
         [2,   5,  8, 12, 19],
         [3,   6,  9, 16, 22],
         [10, 13, 14, 17, 24],
         [18, 21, 23, 26, 30]
        ]

        target = 5
        target = 20
    */
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int i = 0;
        int j = cols - 1;
        while(i <= rows - 1 && j >= 0){
            if(matrix[i][j] == target) return true;
            else if(matrix[i][j] < target) i++;
            else if(matrix[i][j] > target) j--;
        }
        return false;
    }

    /* https://leetcode.com/problems/find-positive-integer-solution-for-a-given-equation/  */
    /*
 * // This is the custom function interface.
 * // You should not implement it, or speculate about its implementation
 * class CustomFunction {
 *     // Returns f(x, y) for any given positive integers x and y.
 *     // Note that f(x, y) is increasing with respect to both x and y.
 *     // i.e. f(x, y) < f(x + 1, y), f(x, y) < f(x, y + 1)
 *     public int f(int x, int y);
 * };
 */
    class CustomFunction {
         public int f(int x, int y){
             return x + y;
         }
    };
    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {
        int low = 1;
        int high = z;
        List<List<Integer>> result = new ArrayList<>();
        while(low < high){
            if(customfunction.f(low, high) == z){
                List<Integer> pair = new ArrayList<>();
                pair.add(low);
                pair.add(high);
                result.add(pair);
                low++;
            }if(customfunction.f(low, high) < z){
                low++;
            }else if(customfunction.f(low, high) > z){
                high--;
            }
        }
        return result;
    }

    /*https://leetcode.com/problems/search-a-2d-matrix/ */
    public boolean searchMatrixAnother(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int rows = matrix.length;
        int cols = matrix[0].length;

        int i = 0;
        int j = cols - 1;
        while(i < rows && j >= 0){
            if(matrix[i][j] == target) return true;
            else if(matrix[i][j] > target)--j;
            else if(matrix[i][j] < target)++i;
        }
        return false;
    }

    /* https://leetcode.com/problems/unique-paths-ii/ 
    to-:do : Solve with DP
    */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
         if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
            return 0;
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if(obstacleGrid[0][0] == 1) return 0;
        
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
         
         return countPathsMemo(obstacleGrid, m - 1, n - 1, memo);
    }
    
    public int countPathsMemo(int[][] grid, int row, int cols, int[][] memo){
        if(row < 0 || cols < 0) {
            return 0;
        }
        if(row == 0 && cols == 0){
            return 1;
        }
        if(memo[row][cols] > 0) {
            return memo[row][cols];
        }
        if(grid[row][cols] == 1){
            return 0;
        }
        memo[row][cols] = countPathsMemo(grid, row - 1, cols, memo) + countPathsMemo(grid, row, cols - 1, memo);
        return memo[row][cols];
    }
 
   /* Wrong approach*/
    public int uniquePathsWithObstaclesBFS(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
            return 0;
        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;

        if(rows == 1 && cols == 1){
            if(obstacleGrid[0][0] == 0)
                return 1;
            else
                return 0;
        }

        boolean [][] visited = new boolean[rows][cols];

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0,0});

        int uniquePaths = 0;

        int[][] dirs = new int[][]{{0,1},{1,0}};
        
        while(!q.isEmpty()){
            int size = q.size();

            while(size-- > 0){
                int[] curr = q.poll();
                
                for(int i = 0; i < dirs.length; i++){
                    int next[] = dirs[i];
                    int next_x = next[0] + curr[0];
                    int next_y = next[1] + curr[1];
                    
                    if(next_x >= 0 && next_x < rows && next_y >= 0 && next_y < cols && !visited[next_x][next_y]){
                        if(next_x == rows -1 && next_y == cols -1){
                           uniquePaths++;
                           continue;
                        }
                        if(obstacleGrid[next_x][next_y] == 0){
                            q.offer(new int[]{next_x, next_y});
                            visited[next_x][next_y] = true;
                        }
                    }
                }
            }
        }
        return uniquePaths;
    }

    /* 
        https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
    */

    public int maxAncestorDiff(TreeNode root) {
        return maxAncestorDFS(root, root.val, root.val);
    }

    public int maxAncestorDFS(TreeNode root, int min, int max){
        if(root == null) return max - min;
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);

        return Math.max(maxAncestorDFS(root.left, min, max), maxAncestorDFS(root.right, min, max));
    }

    /*
        https://leetcode.com/problems/n-queens-ii/
    */
    public int totalNQueens(int n) {
        int[][] board = new int[n][n];
        for(int[] rows : board){
            Arrays.fill(rows, 0);
        }
        solveNQueensRec(board, n, 0);
        return total_distinct_paths;
    }

    int total_distinct_paths = 0;
    public void solveNQueensRec(int[][] board, int N, int col ){
        if(col == N){
            total_distinct_paths++;
            return;
        }
        for(int i = 0; i < N; i++){
            if(isSafeToPlaceHere(board, i, col)){
                board[i][col] = 1;

                solveNQueensRec(board, N, col+1);

                board[i][col] = 0;
            }
        }
    }

    public boolean isSafeToPlaceHere(int[][] board, int row, int col){
        int i, j;

        for(j = col; j >= 0; --j){
            if(board[row][j] == 1){
                return false;
            }
        }


        for(i = row, j = col; i >= 0 && j >= 0; --i, --j){
            if(board[i][j] == 1){
                return false;
            }
        }

        for(i = row , j = col; i >= 0 && j >= 0; --i, ++j){
            if(board[i][j] == 1){
                return false;
            }
        }
        return true;
    }


    /*  https://leetcode.com/problems/reorder-list/
        Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
    */
    public void reorderList(ListNode head) {
        if(head == null && head.next == null) return;

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        ListNode l1 = head;

        while(fast != null && fast.next != null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        prev.next = null;

        ListNode l2 = reverse(slow);

        merge(l1,l2);
    }

    public ListNode reverse(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public void merge(ListNode l1, ListNode l2){
        ListNode next1 = null;
        ListNode next2 = null;
        while(l1 != null && l2 != null){
            next1 = l1.next;
            next2 = l2.next;
            l1.next = l2;
            
            if(next1 == null) break;

            l2.next = next1;

            l1 = next1;
            l2 = next2;
        }
    }

    /*  
        https://leetcode.com/problems/super-egg-drop/
        https://leetcode.com/problems/super-egg-drop/discuss/159055/Java-DP-solution-from-O(KN2)-to-O(KNlogN)
    */
    public int superEggDrop(int K, int N) {
        int[][] memo = new int[K+1][N+1];

        for(int i = 0 ; i < K; i++){
            Arrays.fill(memo[i], -1);
        }
        return superEggDropMemoBinarySearch(K, N, memo);
    }

    public int superEggDropMemoBinarySearch(int K, int N, int[][] memo){
        if(K == 1){
            return N;
        }
        if(N == 0 || N == 1){
            return N;
        }
        if(memo[K][N] > 0){
            return memo[K][N];
        }

        int high = N;
        int min = N;
        int low = 1;
        while(low < high){
            int mid = low + (high - low) / 2;
            int broken = superEggDropMemoBinarySearch(K- 1, mid - 1, memo);
            int not_broken = superEggDropMemoBinarySearch(K, N - mid, memo);
            min = Math.min(min, Math.max(broken, not_broken) + 1);

            if(broken == not_broken){
                break;
            }else if(broken < not_broken){
                low = mid + 1;
            }else{
                high = mid;
            }
        }

        memo[K][N] = min;
        return memo[K][N];
    }

    public int superEggDropMemo(int K, int N, int[][] memo){
        if(K == 1){
            return N;
        }
        if(N == 0 || N == 1){
            return N;
        }
        if(memo[K][N] > 0){
            return memo[K][N];
        }

        int min = N;
        for(int floor = 1; floor <= N; floor++){
            int broken = superEggDropMemo(K- 1, floor -1, memo);
            int not_broken = superEggDropMemo(K, N - floor, memo);
            min = Math.min(min, Math.max(broken, not_broken) + 1 /* we cover maximum floors */);//Minimum attempts
        }
        memo[K][N] = min;
        return memo[K][N];
    }


}