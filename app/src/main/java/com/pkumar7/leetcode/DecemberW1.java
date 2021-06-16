package com.pkumar7.leetcode;
import com.pkumar7.TreeNode;

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
import java.util.TreeSet;

public class DecemberW1 {
    
    public static void main(String ars[]){
        DecemberW1 decemberW1 = new DecemberW1();
        String minlen = decemberW1.minWindow("adobecodebanc","abc");
        System.out.println(minlen);
    }

    /*  To-Do: Revisit this again
        https://leetcode.com/problems/minimum-window-substring/
        https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
        https://leetcode.com/problems/longest-substring-without-repeating-characters/
    */
    public String minWindow(String s, String t) {
        int counter = t.length();
        int begin = 0;
        int end = 0;
        int d = Integer.MAX_VALUE;
        int head = 0;

        int[] map = new int[128];
        for(int idx = 0; idx < t.length(); idx++){
            map[t.charAt(idx) - 'a']++;
        }

        while(end < s.length()){
            if(map[s.charAt(end++) - 'a']-- > 0) counter--;

            while(counter == 0){
                if((end - begin) < d){
                    head = begin;
                    d = end - begin;
                }
                if(map[s.charAt(begin++) - 'a']++ == 0){
                    counter++;
                }
            }
        }
        return d == Integer.MAX_VALUE ? "" : s.substring(head, head + d);
    }

    /*
     *
        Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
        Output: "B"
        Explanation: "B" wins.
        "X  "    "X  "    "XX "    "XXO"    "XXO"    "XXO"
        "   " -> " O " -> " O " -> " O " -> "XO " -> "XO "
        "   "    "   "    "   "    "   "    "   "    "O  "
     */
    public String tictactoe(int[][] moves) {
        boolean A = true;
        char[][] board = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i],'-');
        }

        for(int[] move : moves){
            int x = move[0];
            int y = move[1];
            if (A) {
                board[x][y] = 'X';
                A = false;
            } else {
                board[x][y] = 'O';
                A = true;
            }
        }
        if(searchForWinner(board, 'O')){
            return "B";
        }else if(searchForWinner(board, 'X')){
            return "A";
        }else if(searchForPending(board)){
            return "Pending";
        }else{
            return "Draw";
        }
    }

    public boolean searchForPending(char[][] board) {
        int row = 2;
        while (row >= 0) {
            for (int i = 0; i < 3; i++) { //Search cols for this row x
                if (board[row][i] == '-') {
                    return true;
                }
            }
            row--;
        }

        int col = 2;
        while (col >= 0) {
            for (int i = 0; i < 3; i++) { //Search cols for this row x
                if (board[i][col] == '-') {
                    return true;
                }
            }
            col--;
        }
        return false;
    }

    public boolean searchForWinner(char[][] board, char search) {
        int row = 2;
        while (row >= 0) {
            boolean winnerFound = true;
            for (int i = 0; i < 3; i++) { //Search cols for this row x
                if (board[row][i] != search) {
                    winnerFound = false;
                }
            }
            row--;
            if (winnerFound) {
                return true;
            }
        }

        int col = 2;
        while (col >= 0) {
            boolean winnerFound = true;
            for (int i = 0; i < 3; i++) { //Search cols for this row x
                if (board[i][col] != search) {
                    winnerFound = false;
                }
            }
            col--;
            if (winnerFound) {
                return true;
            }
        }
        if (board[0][0] == search && board[1][1] == search && board[2][2] == search)
            return true;

        if (board[2][0] == search && board[1][1] == search && board[0][2] == search)
            return true;

        return false;
    }


    /*Amazon : Find nums of connected islands surround with water for given target
     char[][] grid = new char[][]{  {'0','1','0','1','0'},
                                    {'1','1','0','0','0'},
                                    {'0','0','0','0','1'},
                                    {'0','0','1','0','1'},
                                    {'0','0','1','0','1'},
                                    {'1','0','0','0','0'},
                                   };
    target = 3;

    */
    public int numIslandsBFS(char[][] grid, int target) {
        if(grid == null || grid.length == 0)return 0;
        int isLandsWithTarget = 0;
        int m = grid.length;
        int n = grid[0].length;
        
        boolean visited[][] = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == '1' && !visited[i][j]){
                    if(BFSIsland(grid,i ,j, m , n, visited, target)){
                        isLandsWithTarget++;
                    }
                }
            }
        }
        return isLandsWithTarget;
    }

    private boolean BFSIsland(char[][] grid, int i, int j, int rows, int cols, boolean[][] visited, int target) {
        int [][]dir = new int[][]{{1,0},{-1,0},{0,-1},{0,1},{-1,-1},{1,-1},{1,1},{-1,1}};
        Queue<int[]> queue = new LinkedList<>();
        
        visited[i][j] = true;
        queue.offer(new int[]{i,j});
        
        int isLandWithTarget = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            isLandWithTarget += size;
            
            while (size-- > 0){
                int[] cur = queue.poll();
                for (int k = 0; k < dir.length; k++) {
                    int next_x = cur[0] + dir[k][0];
                    int next_y = cur[1] + dir[k][1];

                    if(next_x >= 0 && next_x < rows && next_y >= 0
                            && next_y < cols && grid[next_x][next_y] == '1' && !visited[next_x][next_y]){
                        visited[next_x][next_y] = true;
                        queue.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
        return isLandWithTarget == target;
    }

        /*
        Input: tomatoSlices = 17, cheeseSlices = 4
        Output: []
        Explantion: There will be no way to use all ingredients to make small and jumbo burgers.

        *
        Input: tomatoSlices = 16, cheeseSlices = 7
        Output: [1,6]
        https://leetcode.com/problems/number-of-burgers-with-no-waste-of-ingredients
        To-Do: Revisit to understand the equation
        https://leetcode.com/problems/number-of-burgers-with-no-waste-of-ingredients/discuss/441342/JavaPython-3-Solve-a-linear-equations-group
        * */
    public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
        int twoX = tomatoSlices - 2 * cheeseSlices, x = twoX / 2, y = cheeseSlices - x;
        if (twoX >= 0 && twoX % 2 == 0 && y >= 0)
            return Arrays.asList(x, y);
        return Arrays.asList();
    }

    /*
        106. Construct Binary Tree from Inorder and Postorder Traversal
        https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder == null || inorder.length == 0 || postorder == null || postorder.length == 0)
            return null;

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
        postIdx = postorder.length - 1;
        return buildFromPostOrder( 0, inorder.length -1, postorder, map);
    }

    int postIdx = 0;
    public TreeNode buildFromPostOrder(int left, int right, int[] postorder, HashMap<Integer,Integer> mapInorder){
        if(left > right) return null;
        TreeNode root = new TreeNode(postorder[postIdx--]);
        int ri = mapInorder.get(root.val);

        root.right = buildFromPostOrder(ri + 1, right , postorder, mapInorder);
        root.left = buildFromPostOrder(  left, ri - 1, postorder, mapInorder);

        return root;
    }

    /*  105. Construct Binary Tree from Preorder and Inorder Traversal
        https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    */
    int preIdx = 0;
    public TreeNode buildTreePreInor(int[] preorder, int[] inorder) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
        return helper(preorder, 0, preorder.length - 1, map);
    }

    public TreeNode helper(int[] preorder, int left, int right, HashMap<Integer,Integer> mapInorder){
        if(left > right) return null;
        TreeNode root = new TreeNode(preorder[preIdx++]);
        int inIdx = mapInorder.get(root.val);
        root.left = helper(preorder, left, inIdx - 1, mapInorder);
        root.right = helper(preorder, inIdx + 1, right, mapInorder);
        return root;
    }

    /*
        https://leetcode.com/problems/paint-fence/
    */
    public int numWays(int n, int k) {
        if(n == 0) return 0;
        if(n == 1) return k;
        int differentColorCount = k * (k -1);
        int sameColorCount = k;
        
        for(int i = 2; i < n; i++){
            int temp = differentColorCount;
            
            differentColorCount = (differentColorCount + sameColorCount) * (k-1);
            sameColorCount = temp;
        }
        return differentColorCount + sameColorCount;
    }


    /*
        https://leetcode.com/problems/course-schedule-ii/

        Topological sorting
    */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        
        int[] order = new int[numCourses];
        int index = 0;
        
        for(int i = 0; i < prerequisites.length; i++){
            
            indegrees[prerequisites[i][0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++){
            if(indegrees[i] == 0){
                order[index++] = i;
                queue.offer(i);
            }
        }
        
        while(!queue.isEmpty()){
            int course = queue.poll();
             for(int i = 0; i < prerequisites.length; i++){
                 if(prerequisites[i][1] == course){
                     indegrees[prerequisites[i][0]]--;
                     
                     if(indegrees[prerequisites[i][0]] == 0){
                         order[index++] = prerequisites[i][0];
                         queue.offer(prerequisites[i][0]);
                     }
                 }
             }
        }
        return numCourses == index ? order : new int[0];
    }

    /*
        https://leetcode.com/problems/graph-valid-tree/

        Input: n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]]
        Output: true
    */
    
    public boolean validTree(int n, int[][] edges) {
        if(edges.length == n - 1) return false;

        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        for(int[] edge : edges){
            int xParent = findParent(parent, edge[0]);
            int yParent = findParent(parent, edge[1]);
            if(xParent == yParent){
                return false;
            }

            //Union of these two parents to single disjoint subsets
            //union(parent, xParent, yParent);
            parent[xParent] = yParent;
        }

        return true;
    }

    public void union(int[] parent, int src, int dst){
        int xRoot = findParent(parent, src);
        int yRoot = findParent(parent, dst);
        parent[xRoot] = yRoot;
    }

    public int findParent(int[] parent, int src){
        if(parent[src] == -1)
            return src;

        return findParent(parent, parent[src]);
    }

    /*
        https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
    */
    public int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        for(int i = 0; i < edges.length; i++){
            int xRoot = findParent(parent, edges[i][0]);
            int yRoot = findParent(parent, edges[i][1]);

            if(xRoot == yRoot){
                continue;
            }

            parent[xRoot] = yRoot;
        }

        int connectedComponents = 0;
        for(int i = 0; i < parent.length; i++){
            if(parent[i] == -1){
                connectedComponents++;
            }
        }
        return connectedComponents;
    }

    public int countComponentsPathCompression(int n, int[][] edges) {
        int[] parent = new int[n];
        for(int i = 0 ; i < parent.length; i++){
            parent[i] = i;
        }

        for(int i = 0; i < edges.length; i++){
            int xRoot = findRoot(parent, edges[i][0]);
            int yRoot = findRoot(parent, edges[i][1]);

            if(xRoot != yRoot){
                parent[xRoot] = yRoot;
            }
        }

        int connectedComponents = 0;
        for(int i = 0; i < parent.length; i++){
            if(parent[i] == i){
                connectedComponents++;
            }
        }
        return connectedComponents;
    }

    /*Path Compression*/
    private int findRoot(int[] root, int i){
        while(root[i] != i){
            root[i] = root[root[i]];
            i = root[i];
        }
        return i;
    }

    /*
        https://leetcode.com/problems/friend-circles/

        [[1,1,0],
        [1,1,0],
        [0,0,1]]
    */
    public int findCircleNum(int[][] M) {
        int[] root = new int[M.length];
        for(int i = 0; i < root.length; i++){
            root[i] = i;
        }

        for(int i = 0; i < M.length; i++){
            for(int j = 0; j < M[0].length; j++){
                if(M[i][j] == 1) {
                    int xRoot = findParentPathCompression(root, i);
                    int yRoot = findParentPathCompression(root, j);
                    if(xRoot != yRoot){
                        root[xRoot] = yRoot;
                    }
                }
            }            
        }

        int friend_circles = 0;
        for(int i = 0; i < root.length; i++ ){
            if(root[i] == i){
                friend_circles++;
            }
        }
        return friend_circles;
    }

    public int findParentPathCompression(int[] root, int i){
        while(root[i] != i){
            root[i] = root[root[i]];
            i = root[i];
        }
        return i;
    }

    /* Template for Union-Find algorithm */
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
            }
            else {
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

    /*  https://leetcode.com/problems/meeting-rooms-ii/
        Input: [[0, 30],[5, 10],[15, 20]]
        Output: 2
    */
    public int minMeetingRooms(int[][] intervals) {
        if(intervals.length == 0) return 0;

        //If two intervals overlaps.. different meeting rooms required
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));

        PriorityQueue<Integer> min_heap = new PriorityQueue<>();

        min_heap.offer(intervals[0][1]);

        for(int i = 1; i < intervals.length; i++){
            
            int min_end_time = min_heap.peek();

            if(min_end_time <= intervals[i][0]){
                min_heap.poll();   
            }

            min_heap.offer(intervals[i][1]);
        }

        return min_heap.size();
    }

    /*https://leetcode.com/problems/meeting-rooms/
        Input: [[7,10],[2,4]]
        Output: true
    */
    public boolean canAttendMeetings(int[][] intervals) {
        if(intervals == null || intervals.length == 0) return false;

        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));

        System.out.println(intervals);

        int curr_end_time = intervals[0][1];

        for(int i=1; i < intervals.length; i++){
            if(curr_end_time > intervals[i][0]){
                return false;
            }
            curr_end_time = intervals[i][1];
        }
        return true;
    }

    /*
        https://leetcode.com/problems/count-square-submatrices-with-all-ones/
        https://leetcode.com/problems/maximal-square/

        Input: matrix =
        [
          [0,1,1,1],
          [1,1,1,1],
          [0,1,1,1]
        ]
        Output: 15
        Explanation: 
        There are 10 squares of side 1.
        There are 4 squares of side 2.
        There is  1 square of side 3.
        Total number of squares = 10 + 4 + 1 = 15
    */
    public int countSquares(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m+1][n+1];

        for(int[] rows : dp){
            Arrays.fill(rows, 0);    
        }

        for(int i = 1; i < dp.length; i++){
            for(int j = 1; j < dp[i].length; j++){
                if(matrix[i-1][j-1] == 1){
                    dp[i][j] = Math.min( Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]) + 1;
                }
            }            
        }

        int possibleSquares = 0;

        for(int i = 0; i < dp.length; i++){
            for(int j = 0; j < dp[i].length; j++){
                possibleSquares += dp[i][j];
            }
        }
        return possibleSquares;

    }

    /*

    https://leetcode.com/problems/task-scheduler/
    https://leetcode.com/problems/task-scheduler/discuss/104501/Java-PriorityQueue-solution-Similar-problem-Rearrange-string-K-distance-apart
    https://leetcode.com/problems/rearrange-string-k-distance-apart/

    */
    public int leastInterval(char[] tasks, int n) {
        HashMap<Character, Integer> map = new HashMap<>();

        for(char task : tasks){
            map.put(task, map.getOrDefault(task, 0) + 1);
        }
        PriorityQueue<Integer> maxPriorityQ = new PriorityQueue<>(Collections.reverseOrder());
        for(Character task : map.keySet()){
            maxPriorityQ.offer(map.get(task));
        }

        HashMap<Integer, Integer> coolDownMap = new HashMap<>();
        int currTime = 0;
        while(!maxPriorityQ.isEmpty() || !coolDownMap.isEmpty()){

            if(coolDownMap.containsKey(currTime - n -1)){
                maxPriorityQ.offer(coolDownMap.remove(currTime -n -1));
            }

            if(!maxPriorityQ.isEmpty()){
                int left = maxPriorityQ.poll() -1;
                if(left != 0){
                    coolDownMap.put(currTime, left);
                }
            }
            currTime++;
        }
        return currTime;
    }

    /*https://leetcode.com/problems/rearrange-string-k-distance-apart/
        Revisit : To Understand this problem type
        how to tackle problems with k distance or k intervals
    */

    public String rearrangeString(String s, int k) {
        char[] arr = s.toCharArray();
        Map<Character,Integer> map = new HashMap<>();
        for(char c : arr){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Map.Entry<Character,Integer>> maxPriorityQ = new PriorityQueue<>(
                                            (a,b) -> b.getValue() - a.getValue()
                                        );

        maxPriorityQ.addAll(map.entrySet());
        
        StringBuilder builder = new StringBuilder();
        
        Queue<Map.Entry<Character,Integer>> waitingQueue = new LinkedList<>();

        while(!maxPriorityQ.isEmpty()){
            Map.Entry<Character, Integer> entry = maxPriorityQ.poll();
            builder.append(entry.getKey());

            entry.setValue(entry.getValue() -1);

            waitingQueue.offer(entry);
            
            if(waitingQueue.size() >= k){
                Map.Entry<Character, Integer> entryWaiting = waitingQueue.poll();
                if(entryWaiting.getValue() > 0){
                    maxPriorityQ.offer(entryWaiting);
                }
            }
        }

        return builder.length() == s.length() ? builder.toString() : "";
    }

    /*https://leetcode.com/problems/long-pressed-name/*/
    public boolean isLongPressedName(String name, String typed) {
        int m = name.length();
        int n = typed.length();
        int i = 0;

        for(int j = 0; j < n; j++){
            if(i < m || name.charAt(i) == typed.charAt(j))
                ++i;
            else if(typed.charAt(j) != typed.charAt(j-1))
                return false;
        }
        return true;
    }

    /*
        https://leetcode.com/problems/reorder-data-in-log-files/
    */
    public String[] reorderLogFiles(String[] logs) {

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                String[] arr1 = str1.split(" ", 2);
                String[] arr2 = str2.split(" ", 2);

                boolean isDigit1 = Character.isDigit(arr1[1].charAt(0));
                boolean isDigit2 = Character.isDigit(arr2[1].charAt(0));
                if(!isDigit1 && !isDigit2){
                    int comp = arr1[1].compareTo(arr2[1]);
                    if(comp == 0) return arr1[0].compareTo(arr2[0]);
                    else return comp;
                }else if(isDigit1 && isDigit2){
                    return 0;
                }else if(isDigit1 && !isDigit2){
                    return 1;
                }else{
                    return -1;
                }
            }
        };

        Arrays.sort(logs, comparator);
        return logs;
    }

    /*
        https://leetcode.com/problems/max-area-of-island/
    */
    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null || grid.length == 0)return 0;
        int maxAreaOfIsland = 0;
        int m = grid.length;
        int n = grid[0].length;
        
        boolean visited[][] = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == 1 && !visited[i][j]){
                    maxAreaOfIsland = Math.max(maxAreaOfIsland, BFSIslandArea(grid,i ,j, m , n, visited));
                }
            }
        }
        
        return maxAreaOfIsland;
    }

    private int BFSIslandArea(int[][] grid, int i, int j, int rows, int cols, boolean[][] visited) {
        int [][]dir = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};
        Queue<int[]> queue = new LinkedList<>();
        
        visited[i][j] = true;
        queue.offer(new int[]{i,j});
        
        int isLandArea = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            isLandArea += size;
            
            while (size-- > 0){
                int[] cur = queue.poll();
                for (int k = 0; k < dir.length; k++) {
                    int next_x = cur[0] + dir[k][0];
                    int next_y = cur[1] + dir[k][1];

                    if(next_x >= 0 && next_x < rows && next_y >= 0
                            && next_y < cols && grid[next_x][next_y] == 1 && !visited[next_x][next_y]){
                        visited[next_x][next_y] = true;
                        queue.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
        return isLandArea;
    }

    /*
        https://leetcode.com/problems/minimum-absolute-difference-in-bst/
        https://leetcode.com/problems/minimum-distance-between-bst-nodes/

        Minimum difference between any nodes in Binary search Tree(BST)
        or Binary Tree(BT)
    */
    public int getMinimumDifference(TreeNode root) {
        if(root == null) return 0;
        return minAbsDifferenceBST(root);
    }
    
    Integer min = Integer.MAX_VALUE;
    Integer prev = null;
    public int minAbsDifferenceBST(TreeNode root){
        if(root == null){
            return min;
        }

        minAbsDifferenceBST(root.left);
        
        if(prev != null){
            min = Math.min(min, Math.abs(prev - root.val));
        }
        
        prev = root.val;

        minAbsDifferenceBST(root.right);
        return min;
    }

    /* Revisit : As this sheds some light on treemap implementation 
        Traverse and store in array, sort the array in O(nlogn) and compute min
    */
    TreeSet<Integer> set = new TreeSet<>();
    int min_bt = Integer.MAX_VALUE;
    public int getMinimumDifferenceBinaryTree(TreeNode root) {
        if (root == null) return min;
        
        if (!set.isEmpty()) {
            if (set.floor(root.val) != null) {
                min_bt = Math.min(min_bt, root.val - set.floor(root.val));
            }
            if (set.ceiling(root.val) != null) {
                min_bt = Math.min(min_bt, set.ceiling(root.val) - root.val);
            }
        }
        set.add(root.val);
        
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        
        return min;
    }

    /*

    https://leetcode.com/problems/shortest-bridge/
    */
    public int shortestBridge(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        boolean isFound = false;
        
        Queue<int[]> queue = new LinkedList<>();

        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

        for(int i = 0; i < m; i++){
            if(isFound) break;
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == 1){
                    dfs(matrix, i, j, queue, dirs, visited);
                    isFound = true;
                    break;
                }
            }
        }
        
        int minBridgeLength = 0;
        
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-- > 0 ){
                int[] curr = queue.poll();
                for(int[] dir : dirs){
                    int next_x = curr[0] + dir[0];
                    int next_y = curr[1] + dir[1];
                     if(next_x >= 0 && next_x < matrix.length && next_y >= 0 && next_y < matrix[0].length 
                        && !visited[next_x][next_y]){
                        if( matrix[next_x][next_y] == 1){
                            return minBridgeLength;
                        }
                        queue.offer(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                     }
                }
            }
            minBridgeLength++;
        }
        return minBridgeLength;
    }

    public void dfs(int[][] matrix, int row, int col, Queue<int[]> queue, int[][] dirs, boolean[][] visited){
        if(row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length || visited[row][col] || matrix[row][col] == 0 )
            return;

        visited[row][col] = true;
        
        queue.offer(new int[]{row, col});

        for(int[] dir : dirs){
            dfs(matrix, row + dir[0], col + dir[1], queue, dirs, visited);
        }
    }   

     /*
        https://leetcode.com/problems/design-tic-tac-toe/*
     */
     class TicTacToe {

        /** Initialize your data structure here. */
        char[][] board;
        int n;
        int[] rows;
        int[] cols;
        int diagonal;
        int antiDiagonal;
        public TicTacToe(int n) {
            board = new char[n][n];
            rows = new int[n];
            cols = new int[n];
            this.n = n;
        }
        
        /** Player {player} makes a move at ({row}, {col}).
            @param row The row of the board.
            @param col The column of the board.
            @param player The player, can be either 1 or 2.
            @return The current winning condition, can be either:
                    0: No one wins.
                    1: Player 1 wins.
                    2: Player 2 wins. */
        public int move(int row, int col, int player) {
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;

            if(row == col){
                diagonal += toAdd;
            }
            if(col == (n - row - 1)){
                antiDiagonal += toAdd;
            }
            if(Math.abs(rows[row]) == n  || Math.abs(cols[col]) == n || Math.abs(diagonal) == n
                  || Math.abs(antiDiagonal) == n ){
                return player;
            }
            return 0;
        }

        public int moveLinearTime(int row, int col, int player) {
            if(player == 1){
                board[row][col] = 'X';
                if(searchForWinner(board, row, col, 'X', n)){
                    return player;
                }else{
                    return 0;
                }
            }else{
                board[row][col] = '0';
                if(searchForWinner(board, row, col, '0', n)){
                    return player;
                }else{
                    return 0;
                }
            }
        }

        public boolean searchForWinner(char[][] board, int x, int y, char marked, int n){
            int winningStreak = 0;
            
            for(int i = 0; i < n; i++){
                if(board[x][i] == marked)
                    winningStreak++;
            }
            if(winningStreak == n ){
                return true;
            }

            winningStreak = 0;
            for(int i = 0; i < n; i++){
                if(board[i][y] == marked)
                    winningStreak++;
            }

            if(winningStreak == n ){
                return true;
            }
            
            //Search diagonally
            int row = 0;
            int col = 0;
            winningStreak = 0;
            while(row < n && col < n ){
                if(board[row++][col++] == marked)
                    winningStreak++;
            }
            if(winningStreak == n){
                return true;
            }

            row = 0;
            col = n - 1;
            winningStreak = 0;
            while(row < n  && col >= 0){
                if(board[row++][col--] == marked)
                    winningStreak++;
            }
            if(winningStreak == n){
                return true;
            }
            
            return false;
        }
    }

    /*
        https://leetcode.com/problems/happy-number/
    */

    public boolean isHappy(int n) {
        Set<Integer> sets = new HashSet<>();
        while(n != 1){
            n = getNext(n);
            if(!sets.contains(n)){
                sets.add(n);
            }else{
                return false;
            }
        }
        return true;
    }

    public int getNext(int n){
        int sum = 0;
        while(n != 0){
            double rem = n % 10;
            sum += Math.pow(rem,2);
            n /= 10;
        }
        return sum;
    }

}