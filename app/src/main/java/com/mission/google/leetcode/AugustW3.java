package com.mission.google.leetcode;

import com.mission.google.datastructures.GraphProblems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.TreeMap;

public class AugustW3 {
    public static void main(String[] args) {
        AugustW3 augustW3 = new AugustW3();
        int[] well = new int[]{1,2,2};
        int [][]pipe = new int[][]{{1,2,1},{2,3,1}};
        int cost = augustW3.minCostToSupplyWater(3, well, pipe);
        System.out.println( " min cost " + cost);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
     **/
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Queue<Node> mQueue = new LinkedList<>();
        mQueue.offer(root);
        while (!mQueue.isEmpty()) {
            int size = mQueue.size();
            List<Node> nodes_at_level = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                Node curr = mQueue.poll();
                nodes_at_level.add(curr);
                if (curr.left != null) {
                    mQueue.offer(curr.left);
                }
                if (curr.right != null) {
                    mQueue.offer(curr.right);
                }
            }
            Node prev = nodes_at_level.get(0);
            for (int i = 1; i < nodes_at_level.size(); i++) {
                Node curr = nodes_at_level.get(i);
                curr.next = null;
                prev.next = curr;
                prev = curr;
            }
        }
        return root;
    }


    /**
     * https://leetcode.com/problems/sum-of-distances-in-tree/
     * */
    /**
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
     **/
    public Node connectII(Node root) {
        if (root == null) {
            return root;
        }

        Queue<Node> mQueue = new LinkedList<>();
        mQueue.offer(root);
        while (!mQueue.isEmpty()) {
            int size = mQueue.size();
            List<Node> nodes_at_level = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                Node curr = mQueue.poll();
                nodes_at_level.add(curr);
                if (curr.left != null) {
                    mQueue.offer(curr.left);
                }
                if (curr.right != null) {
                    mQueue.offer(curr.right);
                }
            }

            Node prev = nodes_at_level.get(0);
            for (int i = 1; i < nodes_at_level.size(); i++) {
                Node curr = nodes_at_level.get(i);
                prev.next = curr;
                curr.next = null;
                prev = curr;
            }
        }

        return root;
    }

    public Node connectedNodesRec(Node root, Node rightNode) {
        if (root == null) {
            return root;
        }
        root.next = rightNode;
        connectedNodesRec(root.left, root.right);
        connectedNodesRec(root.right, root.next != null ? root.next.left : null);
        return root;
    }

    public Node connectNodeBfs(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null) {
                root.right.next = root.next.left;
            }
        }

        connectNodeBfs(root.left);
        connectNodeBfs(root.right);
        return root;
    }

    public Node connectBFS(Node root) {
        if (root == null) {
            return root;
        }
        Node prev = root;
        Node curr = null;

        while (prev.left != null) {
            curr = prev;
            while (curr != null) {
                curr.left.next = curr.right;
                if (curr.next != null) {
                    curr.right.next = curr.next.left;
                    curr = curr.next;
                }

            }
        }
        return root;
    }

    /**
     * https://leetcode.com/problems/insert-into-a-binary-search-tree/
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return root;
        }
        if (root.val > val) {
            if (root.left == null) { //Insert to left
                root.left = new TreeNode(val);
            } else {
                insertIntoBST(root.left, val);
            }
        }
        if (root.val < val) {
            if (root.right == null) { //Insert to left
                root.right = new TreeNode(val);
            } else {
                insertIntoBST(root.right, val);
            }
        }
        return root;
    }

    /**
     * https://leetcode.com/problems/binary-tree-right-side-view/
     * <p>
     * Input: [1,2,3,null,5,null,4]
     * Output: [1, 3, 4]
     * Explanation:
     * <p>
     * 1            <---
     * /   \
     * 2     3         <---
     * \     \
     * 5     4       <---
     */
    List<Integer> result = new ArrayList<>();

    public List<Integer> rightSideView(TreeNode root) {
        rightViewBFS(root);
        return result;
    }

    public void rightViewRec(TreeNode root, List<Integer> res, int height) {
        if (root == null) {
            return;
        }
        if (res.size() == height) {
            res.add(root.val);
            System.out.print(root.val + " ");
        }
        rightViewRec(root.right, res, height + 1);
        rightViewRec(root.left, res, height + 1);
    }

    public void rightViewBFS(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> mQueue = new LinkedList<>();
        mQueue.offer(root);
        while (!mQueue.isEmpty()) {
            int size = mQueue.size();
            int rightChild = ((LinkedList<TreeNode>) mQueue).peekLast().val;
            result.add(rightChild);
            for (int i = 0; i < size; i++) {
                TreeNode curr = mQueue.poll();
                if (curr.left != null) {
                    mQueue.offer(curr.left);
                }
                if (curr.right != null) {
                    mQueue.offer(curr.right);
                }
            }
        }
    }

    /**
     * 4. Median of Two Sorted Arrays
     * https://leetcode.com/problems/median-of-two-sorted-arrays/
     * https://github.com/mission-peace/interview/blob/master/src/com/interview/binarysearch/MedianOfTwoSortedArrayOfDifferentLength.java
     **/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int x = nums1.length;
        int y = nums2.length;
        int low = 0;
        int high = x;

        while (low <= high) {
            int partitionX = (low + high) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX;

            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ((x + y) % 2 == 0) {
                    return ((double) Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
                } else {
                    return (double) Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                high = partitionX - 1;
            } else {
                low = partitionX + 1;
            }
        }
        return -1;
    }

    /**
     * 543. Diameter of Binary Tree
     * https://leetcode.com/problems/diameter-of-binary-tree/
     * <p>
     * 1
     * / \
     * 2   3
     * / \
     * 4   5
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lHeight = getHeight(root.left);
        int rHeight = getHeight(root.right);

        int lDiameter = diameterOfBinaryTree(root.left);
        int rDiameter = diameterOfBinaryTree(root.right);

        return Math.max(lHeight + rHeight + 1, Math.max(lDiameter, rDiameter));
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left),
                getHeight(root.right));
    }

    class Height {
        int h;
    }

    public int diameterOfBinaryTree(TreeNode root, Height h) {
        if (root == null) {
            return 0;
        }
        Height lH = new Height();
        Height rH = new Height();

        int lD = diameterOfBinaryTree(root.left, lH);
        int rD = diameterOfBinaryTree(root.right, rH);

        h.h = Math.max(lH.h, rH.h) + 1;

        return Math.max(lH.h + rH.h + 1, Math.max(lD, rD));
    }

    /**
     * https://leetcode.com/problems/valid-parentheses/
     * <p>
     * Input: "()[]{}"
     * Output: true
     * <p>
     * Input: "(]"
     * Output: false
     * <p>
     * Input: "{[]}"
     * Output: true
     * <p>
     * Input: "([)]"
     * Output: false
     **/
    public static boolean isValid(String s) {
        if (s.length() == 0) {
            return false;
        }
        Stack<Character> mStack = new Stack<>();
        for (char ch : s.toCharArray()) {

            if (ch == '(' || ch == '{' || ch == '[')
                mStack.push(ch);

            if (ch == ')' || ch == '}' || ch == ']') {
                if (mStack.isEmpty())
                    return false;

                if (!isMatchingPair(mStack.pop(), ch))
                    return false;

            }
        }

        if (mStack.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean isMatchingPair(char first, char second) {
        if (first == '(' && second == ')')
            return true;
        else if (first == '{' && second == '}')
            return true;
        else if (first == '[' && second == ']')
            return true;

        return false;
    }

    /**
     * 1022. Sum of Root To Leaf Binary Numbers
     * <p>
     * https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
     */
    List<String> allPaths = new ArrayList<>();

    public int sumRootToLeaf(TreeNode root) {
        allPathsRec(root, "");
        int sum = 0;
        for (int i = 0; i < allPaths.size(); i++) {
            sum += Integer.parseInt(allPaths.get(i), 2);
        }
        return sum;
    }

    public void allPathsRec(TreeNode root, String path) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            path = path + root.val;
            allPaths.add(path);
            return;
        }
        path = path + root.val;

        allPathsRec(root.left, path);
        allPathsRec(root.right, path);
    }

    /**
     * 129. Sum Root to Leaf Numbers
     * <p>
     * Input: [4,9,0,5,1]
     * 4
     * / \
     * 9   0
     * / \
     * 5   1
     * Output: 1026
     * Explanation:
     * The root-to-leaf path 4->9->5 represents the number 495.
     * The root-to-leaf path 4->9->1 represents the number 491.
     * The root-to-leaf path 4->0 represents the number 40.
     * Therefore, sum = 495 + 491 + 40 = 1026.
     * <p>
     * https://leetcode.com/problems/sum-root-to-leaf-numbers/
     */
    public int sumNumbers(TreeNode root) {
        sumNumbers(root, "");
        int sum = 0;
        for (int i = 0; i < all_paths.size(); i++) {
            sum += Integer.parseInt(all_paths.get(i));
        }
        return sum;
    }

    List<String> all_paths = new ArrayList<>();

    public void sumNumbers(TreeNode root, String path) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            path = path + root.val;
            all_paths.add(path);
            return;
        }

        path = path + root.val;
        sumNumbers(root.left, path);
        sumNumbers(root.right, path);
    }

    public int dfs(TreeNode root, int num, int sum) {
        if (root == null) {
            return sum;
        }
        num = num * 10 + root.val;

        if (root.left == null && root.right == null) {
            sum += num;
            return sum;
        }

        sum = dfs(root.left, num, sum) + dfs(root.right, num, sum);
        return sum;
    }

    /**
     * https://www.geeksforgeeks.org/inorder-successor-in-binary-search-tree/
     * https://leetcode.com/articles/inorder-successor-in-bst/
     **/
    public Node inorderSuccessor(Node root, Node k) {
        //If Node k has right child then successor is min of right childs
        if (k.right != null) {
            k = k.right;
            while (k.left != null) {
                k = k.left;
            }
            return k;
        }
        ArrayDeque<Node> stack = new ArrayDeque<>();
        int inorder = Integer.MIN_VALUE;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (inorder == k.val) return root;
            inorder = root.val;

            root = root.right;
        }
        return null;
    }

    /**
     * 402. Remove K Digits
     * https://leetcode.com/problems/remove-k-digits/
     */
    public static String removeKdigits(String num, int k) {
        if (num.length() == k)
            return "0";

        StringBuilder builder = new StringBuilder(num);
        for (int i = 0; i < k; i++) {
            int j = 0;
            while (j < builder.length() - 1 && builder.charAt(j) <= builder.charAt(j + 1)) {
                j++;
            }
            builder.delete(j, j + 1);
        }
        while (builder.length() > 1 && builder.charAt(0) == '0')
            builder.delete(0, 1);

        if (builder.length() == 0)
            return "0";

        return builder.toString();
    }

    /**
     * 139. Word Break
     * <p>
     * https://leetcode.com/problems/word-break/
     * <p>
     * Input: s = "leetcode", wordDict = ["leet", "code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     * <p>
     * Input: s = "applepenapple", wordDict = ["apple", "pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     * Note that you are allowed to reuse a dictionary word
     * <p>
     * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * Output: false
     **/
    public static boolean wordBreak(String string, List<String> wordDict) {
        int n = string.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(string.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[string.length()];
    }

    public boolean wordBreakDFS(String str, List<String> wordDict){
        Set<Integer> set = new HashSet<>();
        return helper(str, 0, set, wordDict);
    }

    public boolean helper(String s, int idx, Set<Integer> set, List<String> dict){
        if(idx == s.length())return true;
        for (int i = idx + 1; i <= s.length() ; i++) {
            if(set.contains(i))continue;
            if(dict.contains(s.substring(idx, i))){
                if(helper(s, i, set, dict)){
                    return true;
                }
                set.add(i);
            }
        }
        return false;
    }

    public boolean wordBreakBFS(String s, Set<String> dict) {
        if (dict.contains(s)) return true;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(0);
        // use a set to record checked index to avoid repeated work.
        // This is the key to reduce the running time to O(N^2).
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(0);
        while (!queue.isEmpty()) {
            int curIdx = queue.poll();
            for (int i = curIdx + 1; i <= s.length(); i++) {
                if (visited.contains(i)) continue;
                if (dict.contains(s.substring(curIdx, i))) {
                    if (i == s.length()) return true;
                    queue.offer(i);
                    visited.add(i);
                }
            }
        }
        return false;
    }

    /**
     * Input: words = ["cat","bt","hat","tree"], chars = "atach"
     * Output: 6
     * Explanation:
     * The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.
     */
    public int countCharacters(String[] words, String chars) {
        int sum = 0;
        int[] freq = new int[26];
        for (char ch : chars.toCharArray()){
            freq[ch - 'a']++;
        }
        for (String word : words){
            int[] copySeen = Arrays.copyOf(freq, freq.length);
            int count = 0;
            for (char ch : word.toCharArray()){
                if(copySeen[ch - 'a'] > 0){
                    copySeen[ch - 'a']--;
                    count++;
                }
            }
            if(count == word.length()){
                sum += count;
            }
        }

        return sum;
    }

    /**
     * Input: [[1,0,1],[0,0,0],[1,0,1]]
     * Output: 2
     * Explanation:The cell (1, 1) is as far as possible from all the land with distance 2.
     *
     * https://leetcode.com/problems/as-far-from-land-as-possible/
     *
     * **/
    public int maxDistance(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int m = grid.length;
        int n = grid[0].length;
        boolean [][]visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == 1){//Adding all land cells to queue
                    visited[i][j] = true;
                    queue.offer(new int[]{i,j});
                }
            }
        }
        int[][] dirs = new int[][]{{1,0},{0,1},{0,-1},{-1,0}};
        int result = -1;
        while (!queue.isEmpty()){
            int size = queue.size();
            while (size-- > 0){
                int[] curr = queue.poll();
                result = Math.max(result, grid[curr[0]][curr[1]] -1);
                for (int[] dir: dirs){ //Explore all cells neighbors to this land
                    int next_x = curr[0] + dir[0];
                    int next_y = curr[1] + dir[1];
                    if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n
                            && !visited[next_x][next_y]){ //If already not visited
                        visited[next_x][next_y] = true;
                        grid[next_x][next_y] = grid[curr[0]][curr[1]] + 1;
                        queue.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
        return result == 0 ? -1 : result;
    }

    /**
     * 200. Number of Islands
     * https://leetcode.com/problems/number-of-islands/
     * https://www.geeksforgeeks.org/find-number-of-islands/
     * https://www.geeksforgeeks.org/connected-components-in-an-undirected-graph/
     *
     */
    public int numIslands(char[][] grid) {
        if(grid == null && grid.length == 0)return 0;
        int isLands = 0;
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == '1'){
                    isLands++;
                    BFSIsland(grid,i ,j, m , n);
                }
            }
        }
        return isLands;
    }

    public int numIslandsDFS(char[][] grid){
        int m = grid.length;
        int n = grid[0].length;

        boolean [][]visited = new boolean[m][n];
        int num_of_island = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == '1' && !visited[i][j]){
                    DFSUtil(grid, i, j, m, n, visited);
                    num_of_island++;
                }
            }
        }
        return num_of_island;
    }

    private void DFSUtil(char[][] grid,int i, int j, int rows, int cols,boolean[][] visited){
        visited[i][j] = true;
        int [][]dir = new int[][]{{1,0},{-1,0},{0,-1},{0,1},{-1,-1},{1,-1},{1,1},{-1,1}};
        for (int k = 0; k < dir.length ; k++) {
            int next_x = i + dir[k][0];
            int next_y = j + dir[k][1];

            if(next_x >= 0 && next_x < rows && next_y >= 0 && next_y < cols
                    && grid[next_x][next_y] == '1' && !visited[next_x][next_y]){
                DFSUtil(grid, next_x, next_y, rows, cols, visited);
            }
        }
    }

    private void BFSIsland(char[][] grid, int i, int j, int rows, int cols) {
        int dir[][] = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};
        grid[i][j] = '0';
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i,j});

        while (!queue.isEmpty()){
            int size = queue.size();
            while (size-- > 0){
                int[] cur = queue.poll();
                for (int k = 0; k < dir.length; k++) {
                    int next_x = cur[0] + dir[k][0];
                    int next_y = cur[1] + dir[k][1];

                    if(next_x >= 0 && next_x < rows && next_y >= 0
                            && next_y < cols && grid[next_x][next_y] == '1'){

                        grid[next_x][next_y] = '0';
                        queue.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
    }

    /***
     * https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/
     * 1161. Maximum Level Sum of a Binary Tree
     */
    private int maxLevelSum(TreeNode root){
        if(root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int maxSum = Integer.MIN_VALUE;
        int level = 1;
        int max_level = 1;

        while (!queue.isEmpty()){
            int sum = 0;
            int size = queue.size();
            while (size-- > 0){
                TreeNode curr = queue.poll();
                sum += curr.val;
                if(maxSum < sum){
                    maxSum = sum;
                    max_level = level;
                }
                if(curr.left != null){
                    queue.offer(curr.left);
                }
                if(curr.right != null){
                    queue.offer(curr.right);
                }
            }
            level++;

        }
        return max_level;
    }

    /**
     * 79. Word Search
     * https://leetcode.com/problems/word-search/
     *
     * board =
     * [
     *   ['A','B','C','E'],
     *   ['S','F','C','S'],
     *   ['A','D','E','E']
     * ]
     *
     * Given word = "ABCCED", return true.
     * Given word = "SEE", return true.
     * Given word = "ABCB", return false.
     * */
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        if(rows == 1 && cols == 1){
            if(word.charAt(0) == board[rows][cols]){
                return true;
            }
        }

        boolean [][]visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(ifWordExistDFS(board, word, i, j, 0,visited)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean anotherWordDFS(char[][] board, String word, int curr_x, int curr_y, int curr_index, boolean[][] visited){
        if(curr_index == word.length()){
            return true;
        }
        if(curr_x < 0 || curr_x >= board.length || curr_y < 0 || curr_y >= board[0].length
                || board[curr_x][curr_y] != word.charAt(curr_index) || visited[curr_x][curr_y]){
            return false;
        }

        visited[curr_x][curr_y] = true;

        boolean res = anotherWordDFS(board, word, curr_x + 1, curr_y, curr_index+1, visited) ||
        anotherWordDFS(board, word, curr_x , curr_y + 1, curr_index+1, visited) ||
        anotherWordDFS(board, word, curr_x - 1, curr_y, curr_index+1, visited) ||
        anotherWordDFS(board, word, curr_x , curr_y -1, curr_index+1, visited);

        visited[curr_x][curr_y] = false;

        return res;
    }

    public boolean ifWordExistDFS(char[][] board, String word, int curr_i, int curr_j, int curr_index, boolean[][] visited){
        System.out.println(" curr_index " + curr_index);
        if(curr_index == word.length()) {
            return true;
        }
        int [][]dir = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};
        for (int k = 0; k < dir.length; k++) {
            int next_x = curr_i + dir[k][0];
            int next_y = curr_j + dir[k][1];

            if(ifFormsWord(next_x, next_y, board, word, curr_index, visited)){
                visited[next_x][next_y] = true;
                if(ifWordExistDFS(board, word, next_x, next_y, curr_index + 1, visited)){
                    return true;
                }
                visited[next_x][next_y] = false;
            }
        }
        return false;
    }

    private boolean ifFormsWord(int next_x, int next_y, char[][]board, String word, int curr_length, boolean[][] visited){
        if(next_x >= 0 && next_x < board.length && next_y >= 0 && next_y < board[0].length
                    && board[next_x][next_y] == word.charAt(curr_length) && !visited[next_x][next_y]){
            return true;
        }
        return false;
    }

    /** 872. Leaf-Similar Trees
     * https://leetcode.com/problems/leaf-similar-trees/
     * */
    List<Integer> leafTree1 = new ArrayList<>();
    List<Integer> leafTree2 = new ArrayList<>();

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        printLeaf(root1, leafTree1);
        printLeaf(root2, leafTree2);

        if(leafTree1.size() != leafTree2.size()){
            return false;
        }
        for (int i = 0; i < leafTree1.size(); i++) {
            System.out.println(leafTree1.get(i) + " " + leafTree2.get(i));
            if(leafTree1.get(i) != leafTree2.get(i)){
                return false;
            }
        }
        return true;
    }

    public void printLeaf(TreeNode root, List<Integer> allLeaf){
        if(root == null){
            return;
        }
        if(root.left == null && root.right == null){
            allLeaf.add(root.val);
            return;
        }

        printLeaf(root.left, allLeaf);
        printLeaf(root.right, allLeaf);
    }

    /**
     * https://leetcode.com/problems/distribute-candies/
     * Input: candies = [1,1,2,3]
     * Output: 2
     * Explanation: For example, the sister has candies [2,3] and the brother has candies [1,1].
     * The sister has two different kinds of candies, the brother has only one kind of candies.
     *
     * 575. Distribute Candies
     * */
    public int distributeCandies(int[] candies) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < candies.length; i++) {
            set.add(candies[i]);
        }
        return set.size() >= candies.length /2 ? candies.length/2 : set.size();
    }

    /**
     * Important
     * https://leetcode.com/problems/word-search-ii/
     *
     * Input:
     * board = [
     *   ['o','a','a','n'],
     *   ['e','t','a','e'],
     *   ['i','h','k','r'],
     *   ['i','f','l','v']
     * ]
     * words = ["oath","pea","eat","rain"]
     *
     * Output: ["eat","oath"]
     *
     * */
    Set<String> found_words = new HashSet<>();
    public List<String> findWords(char[][] board, String[] words) {
        /*List<String> res = new ArrayList<>();
        for (String word : words) {
            if(exist(board, word)){
                res.add(word);
            }
        }
        return res;*/
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfsTrie(board, i, j, "", trie, visited);
            }
        }

        return new ArrayList<String>(found_words);
    }

    private void dfsTrie(char[][] board, int i, int j, String str, Trie trie, boolean[][] visited) {
        int rows = board.length;
        int cols = board[0].length;

        if(i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j])
            return;

        str = str + board[i][j];
        if(!trie.startsWith(str)){
            return;
        }
        if(trie.search(str)){
            found_words.add(str);
        }
        visited[i][j] = true;

        dfsTrie(board, i+1, j, str, trie, visited);
        dfsTrie(board, i, j+1, str, trie, visited);
        dfsTrie(board, i - 1, j, str, trie, visited);
        dfsTrie(board, i, j -1, str, trie, visited);

        visited[i][j] = false;
    }

    class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        String item = "";
    }
    class Trie {
        public TrieNode root = new TrieNode();

        public void insert(String word){
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if(node.children[ch - 'a'] == null){
                    node.children[ch - 'a'] = new TrieNode();
                }
                node = node.children[ch - 'a'];
            }
            node.item = word;
        }

        public boolean search(String word){
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if(node.children[ch - 'a'] == null)
                    return false;

                node = node.children[ch - 'a'];
            }
            if(node.item.equals(word)){
                return true;
            }else{
                return false;
            }
        }

        public boolean startsWith(String prefix){
            TrieNode node = root;
            for (char ch  : prefix.toCharArray()) {
                if(node.children[ch - 'a'] == null)
                    return false;
                node = node.children[ch - 'a'];
            }
            return true;
        }
    }

    /** Important
     * https://leetcode.com/articles/next-permutation/
     * https://www.programcreek.com/2014/06/leetcode-next-permutation-java/
     * https://leetcode.com/problems/next-permutation/
     * **/
    public void nextPermutation(int[] nums) {
        int p = -1;
        int q = -1;

        for (int i = nums.length -1; i >= 0 ; i--) {
            if(nums[i -1] < nums[i]){
                p = i-1;
                break;
            }
        }
        if(p == -1){
            reverse(nums, 0, nums.length-1);
            return;
        }

        for (int i = nums.length -1; i >= 0 ; i--) {
            if(nums[p] < nums[i]){
                q = i;
                break;
            }
        }
        swap(nums, p, q);

        reverse(nums, p +1, nums.length -1);
    }

    private void reverse(int[] nums, int first, int last) {
        while (last > first){
            swap(nums, last, first);
            last--;
            first++;
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /** 54. Spiral Matrix
     * https://leetcode.com/problems/spiral-matrix/
     *
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     * Output: [1,2,3,6,9,8,7,4,5]
     *
     * */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();

        if (matrix == null || matrix.length == 0) return res;

        int n = matrix.length; int m = matrix[0].length;
        int up = 0, down = n - 1;
        int left = 0, right = m - 1;

        while (res.size() < n*m){
            for (int i = left; i <= right && res.size() < n*m; i++)
                res.add(matrix[up][i]);

            for (int i = up + 1; i <= down - 1 && res.size() < n*m; i++)
                res.add(matrix[i][right]);

            for (int i = right; i >= left && res.size() < n*m; i--)
                res.add(matrix[down][i]);

            for (int i = down - 1; i >= up + 1 && res.size() < n*m; i--)
                res.add(matrix[i][left]);

            left++;right--;up++;down--;
        }
        return res;
    }

    /**
     * 59. Spiral Matrix II
     * https://leetcode.com/problems/spiral-matrix-ii/
     *
     * Given a positive integer n, generate a square matrix
     * filled with elements from 1 to n2 in spiral order.
     *
     * Input: 3
     * Output:
     * [
     *  [ 1, 2, 3 ],
     *  [ 8, 9, 4 ],
     *  [ 7, 6, 5 ]
     * ]
     *
     * */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];

        int filled = 0;

        int left = 0; int right = n - 1;
        int up = 0;   int down = n -1;

        while (filled < n*n){
            for (int i = left; i <= right && filled < n*n ; i++) {
                matrix[up][i] = ++filled;
            }
            for (int i = up +1; i <= down -1 && filled < n*n ; i++) {
                matrix[i][right] = ++filled;
            }

            for (int i = right ; i >= left && filled < n*n ; i--) {
                matrix[down][i] = ++filled;
            }

            for (int i = down - 1 ; i >= up + 1 && filled < n*n ; i--) {
                matrix[i][left] = ++filled;
            }
            left++;right--;up++;down--;
        }
        return matrix;
    }

    /** 441. Arranging Coins
     * https://leetcode.com/problems/arranging-coins/
     *
     * n = 5
     *
     * The coins can form the following rows:
     * ¤
     * ¤ ¤
     * ¤ ¤
     * Because the 3rd row is incomplete, we return 2.
     * */
    public int arrangeCoins(int n) {
        int i = 0;
        while( n > 0){
            i += 1;
            n -= i;
        }
        return n == 0 ? i : i-1;
    }


    /**
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
     * https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/TreeTraversalInSpiralOrder.java
     * */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        //travelDFS(root, ans , 1);
        travelBFS(root, result);

        return result;
    }

    public void travelBFS(TreeNode root, List<List<Integer>> sol){
        if(root == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int level = 0;

        while (!queue.isEmpty()){
            int size = queue.size();
            List<Integer> newLevel = new ArrayList<>();
            Integer[] arr = new Integer[size];

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                arr[i] = curr.val;
                if(curr.left != null){
                    queue.offer(curr.left);
                }
                if(curr.right != null){
                    queue.offer(curr.right);
                }
            }
            if(level % 2 != 0){ //Reverse array
                int i = 0; int j = size -1;
                while (i < j){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    i++;
                    j--;
                }
            }
            newLevel = new ArrayList<Integer>(Arrays.asList(arr));
            sol.add(newLevel);
            level++;
        }
    }

    public void travelDFS(TreeNode root, List<List<Integer>> sol, int level){
        if(root == null){
            return;
        }
        if(sol.size() <= level){
            List<Integer> newlevel = new ArrayList<>();
            sol.add(newlevel);
        }
        List<Integer> level_nodes = sol.get(level);
        System.out.println("level " + level + " node: " + root.val);
        if(level % 2 != 0) {
            level_nodes.add(root.val);
        }else{
            level_nodes.add(0, root.val);
        }
        travelDFS(root.left, sol, level + 1);
        travelDFS(root.right, sol, level + 1);
    }

    /**
     * 173. Binary Search Tree Iterator
     * https://leetcode.com/problems/binary-search-tree-iterator/
     *
     * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
     *
     * Calling next() will return the next smallest number in the BST.
     *
     * BSTIterator iterator = new BSTIterator(root);
     * iterator.next();    // return 3
     * iterator.next();    // return 7
     * iterator.hasNext(); // return true
     * iterator.next();    // return 9
     * iterator.hasNext(); // return true
     * iterator.next();    // return 15
     * iterator.hasNext(); // return true
     * iterator.next();    // return 20
     * iterator.hasNext(); // return false
     *
     * */
    class BSTIterator {
        Stack<TreeNode> mStack = new Stack<>();
        public BSTIterator(TreeNode root) {
            pushElements(root);
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            if(mStack.isEmpty()){
               return -1;
            }
            TreeNode curr = mStack.pop();
            pushElements(curr.right);
            return curr.val;
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return !mStack.isEmpty();
        }

        public void pushElements(TreeNode root){
            for (; root != null; mStack.push(root), root = root.left);
        }
    }

    /**
     * 284. Peeking Iterator
     *
     * https://leetcode.com/problems/peeking-iterator/
     **/

    class PeekingIterator implements Iterator<Integer> {

        Iterator<Integer> iterator;
        int peek = -1;
        int next = -1;
        boolean noSuchElement;
        public PeekingIterator(Iterator<Integer> iterator) {
            this.iterator = iterator;
            if(peek == -1 && iterator.hasNext()){
                peek = iterator.next();
            }
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return peek;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            next = peek;
            if(iterator.hasNext()) {
                peek = iterator.next();
            }else{
                noSuchElement = true;
            }
            return next;
        }

        @Override
        public boolean hasNext() {
            return !noSuchElement;
        }
    }

    /** Important
     * https://leetcode.com/problems/non-decreasing-array/
     * 665. Non-decreasing Array
     *
     *
     * Input: [4,2,3]
     * Output: True
     * Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
     *
     * Input: [4,2,1]
     * Output: False
     * Explanation: You can't get a non-decreasing array by modify at most one element.
     *
     * Input: [2,3,3,2,4]
     * Output: True
     *
     * **/
    public boolean checkPossibility(int[] nums) {
        int cnt = 0;
        for (int i = 1; i < nums.length && cnt <= 1; i++) {
            if(nums[i -1] > nums[i]){
                cnt++;
                if(i -2 < 0 || nums[i-2] <= nums[i]){
                    nums[i-1] = nums[i];
                }else{
                    nums[i] = nums[i-1];
                }
            }
        }

        return cnt <= 1;
    }

    /** https://leetcode.com/problems/palindrome-number/
     * Input: 121
     * Output: true
     * */
    public boolean isPalindrome(int x) {
        String org = String.valueOf(x);
        StringBuilder builder = new StringBuilder();
        builder.append(x);
        builder = builder.reverse();
        String reverse = builder.toString();
        return reverse.equals(org);
    }

    /**
     * https://leetcode.com/problems/reverse-integer/
     * */
    public int reverse(int x) {
        long rev = 0;
        while (x != 0){
            rev = rev*10 + x % 10;
            x = x/10;

            if(rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE){
                return 0;
            }
        }
        return (int)rev;
    }

    /** 542. 01 Matrix
     * https://leetcode.com/problems/01-matrix/
     * **/
    public int[][] updateMatrix(int[][] matrix) {
        Queue<int[]> mQueue = new LinkedList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0){
                    mQueue.offer(new int[]{i,j});
                }else{
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        while (!mQueue.isEmpty()){
            int[] curr = mQueue.poll();
            for (int i = 0; i < dirs.length; i++) {
                int next_x = curr[0] + dirs[i][0];
                int next_y = curr[1] + dirs[i][1];
                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols ||
                        matrix[next_x][next_y] <= matrix[curr[0]][curr[1]] +1) continue;
                mQueue.offer(new int[]{next_x, next_y});
                matrix[next_x][next_y] = matrix[curr[0]][curr[1]] + 1;
            }
        }
        return matrix;
    }

    /**
     * Important
     * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
     * 297. Serialize and Deserialize Binary Tree
     */

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root == null) return "";
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            StringBuilder builder = new StringBuilder();
            while (!queue.isEmpty()){
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode curr = queue.poll();
                    if(curr != null){
                        builder.append(curr.val);
                        builder.append(",");
                        queue.offer(curr.left);
                        queue.offer(curr.right);
                    }else{
                        builder.append("null");
                        builder.append(",");
                    }
                }
            }
            System.out.println(builder.toString());
           return builder.toString();
        }

        // Decodes your encoded data to tree.
        // 1,2,3,null,null,4,5,null,null,null,null,
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

    /** 14. Longest Common Prefix
     * Input: ["flower","flow","flight"]
     * Output: "fl"
     *
     * Input: ["dog","racecar","car"]
     * Output: ""
     * Explanation: There is no common prefix among the input strings.
     *
     * */
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0)return "";
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++) {
            System.out.println(strs[i].indexOf(pre));
            while (strs[i].indexOf(pre) != 0)
                pre = pre.substring(0, pre.length()-1);
        }
        return pre;
    }

    /** 69. Sqrt(x)
     * https://leetcode.com/problems/sqrtx/
     * **/
    public int mySqrt(int x) {
        return (int)Math.sqrt(x);
    }

    /**
     * https://www.geeksforgeeks.org/egg-dropping-puzzle-dp-11/
     *
     * n : eggs
     * k : floors
     *
     */
    public static int eggDrop(int n, int k) {
        if(k == 1 || k == 0){
            return k;
        }
        if(n == 1){
            return k;
        }
        int min = Integer.MAX_VALUE;
        int res;
        for (int x = 1; x <= k; x++) {
            res = Math.max(eggDrop(n - 1,x - 1 ), eggDrop(n,k - x ));
            if(res < min) {
                min = res;
            }
        }
        return min + 1;
    }

    public static int eggDropDP(int n, int k){
        int eggFloor[][] = new int[n+1][k+1];
        int res = 0;
        for (int i = 1; i <= n; i++) {
            eggFloor[i][0] = 0;
            eggFloor[i][1] = 1;
        }

        for (int j = 1; j <= k ; j++) {
                eggFloor[1][j] = j;
        }

        for (int i = 2; i <= n ; i++) {
            for (int j = 2; j <= k ; j++) {
                eggFloor[i][j] = Integer.MAX_VALUE;
                for (int x = 1; x <= j ; x++) {
                    res = 1 + Math.max(eggFloor[i][j - x], eggFloor[i-1][x-1]);
                    if(res < eggFloor[i][j]){
                        eggFloor[i][j] = res;
                    }
                }
            }
        }

        return eggFloor[n][k];
    }

    /** 218. The Skyline Problem
     * https://leetcode.com/problems/the-skyline-problem/
     * https://github.com/mission-peace/interview/blob/master/src/com/interview/geometry/SkylineDrawing.java
     * */
    public class BuildingPoints implements Comparable<BuildingPoints>{
        int x;
        boolean isStart;
        int height;

        @Override
        public int compareTo(BuildingPoints anotherPts) {
            if(this.x != anotherPts.x) {
                return this.x - anotherPts.x;
            }else{
                return (this.isStart ? -this.height : this.height) - (anotherPts.isStart ? -anotherPts.height : anotherPts.height);
            }
        }
    }
    public List<List<Integer>> getSkyline(int[][] buildings) {
        BuildingPoints[] buildingPoints = new BuildingPoints[buildings.length * 2];
        int index = 0;
        for (int building[] : buildings){
            buildingPoints[index] = new BuildingPoints();
            buildingPoints[index].x = building[0];
            buildingPoints[index].isStart = true;
            buildingPoints[index].height = building[2];

            buildingPoints[index + 1] = new BuildingPoints();
            buildingPoints[index  +1].x = building[1];
            buildingPoints[index  +1].isStart = false;
            buildingPoints[index + 1].height = building[2];

            index += 2;
        }
        Arrays.sort(buildingPoints);

        TreeMap<Integer, Integer> queue = new TreeMap<>();
        queue.put(0, 1);

        int prevMaxHeight = 0;

        List<List<Integer>> result = new ArrayList<>();
        for (BuildingPoints points : buildingPoints) {
            if(points.isStart){
                queue.compute(points.height, (key, value) -> {
                    if(value != null){
                        return value + 1;
                    }
                    return 1;
                });
            }else{
                queue.compute(points.height, (key, value) -> {
                   if(value == 1){
                       return null;
                   }
                   return value - 1;
                });
            }

            int currMaxHeight = queue.lastKey();
            if(prevMaxHeight != currMaxHeight){
                List<Integer> new_points = new ArrayList<>();
                new_points.add(points.x);
                new_points.add(currMaxHeight);
                result.add(new_points);
                prevMaxHeight = currMaxHeight;
            }
        }
        return result;
    }

    /**
     *  Input: keyboard = "abcdefghijklmnopqrstuvwxyz", word = "cba"
     * Output: 4
     * Explanation: The index moves from 0 to 2 to write 'c' then to 1 to write 'b' then to 0 again to write 'a'.
     * Total time = 2 + 1 + 1 = 4.
     *
     **/
    public int calculateTime(String keyboard, String word) {
        if(keyboard == null || keyboard.isEmpty()){
            return 0;
        }
        char[] arr = word.toCharArray();
        int time = 0;
        int prev_idx = -1;
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if(prev_idx < 0) {
                prev_idx = keyboard.indexOf(ch);
                time += prev_idx;
            }else{
                time += Math.abs(prev_idx - keyboard.indexOf(ch));
                prev_idx = keyboard.indexOf(ch);
            }
        }
        return time;
    }

    /** 1167. Minimum Cost to Connect Sticks
     * https://leetcode.com/contest/biweekly-contest-7/problems/minimum-cost-to-connect-sticks/
     * */

    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> mMinHeap = new PriorityQueue<>();
        for (int i = 0; i < sticks.length; i++) {
            mMinHeap.offer(sticks[i]);
        }

        int total_cost = 0;
        while (mMinHeap.size() > 1){
            int stick1 = mMinHeap.poll();
            int stick2 = mMinHeap.poll();

            int combine = stick1 + stick2;
            total_cost += (combine);

            mMinHeap.offer(combine);
        }

        return total_cost;
    }
    /**
     * https://leetcode.com/problems/optimize-water-distribution-in-a-village/
     * Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
     * Output: 3
     * **/
    class Graph{
        int V;
        LinkedList<Edge> adjList[];
        public Graph(int v) {
            this.V = v;
            adjList = new LinkedList[V];
            for (int i = 0; i < v; i++) {
                adjList[i] = new LinkedList<>();
            }
        }

        public void addBiDirectionalEdge(int src, int dst, int weight){
            Edge srcEdge = new Edge(src,dst, weight);
            Edge dstEdge = new Edge(src,dst, weight);
            adjList[src].add(srcEdge);
            adjList[dst].add(dstEdge);
        }
    }

    class Edge{
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        int minCost = 0;
        Graph graph = new Graph(n);
        for (int i = 0; i < pipes.length; i++) {
            int[] pipe_cost = pipes[i];
            graph.addBiDirectionalEdge(pipe_cost[0]-1, pipe_cost[1]-1, pipe_cost[2]);
        }
        for (int i = 0; i < wells.length; i++) {
            int currCost = wells[i];
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            boolean visited[] = new boolean[n];
            visited[i] = true;
            while (!queue.isEmpty()){
                int v = queue.poll();
                Iterator<Edge> it = graph.adjList[v].iterator();
                while (it.hasNext()){
                    Edge edge = it.next();
                    System.out.println(" Weight " + edge.weight);
                    currCost += edge.weight;
                    if(!visited[edge.destination]){
                        visited[edge.destination] = true;
                        queue.offer(edge.destination);
                    }
                }
            }
            System.out.println("CurrCost " + currCost);
            if(minCost == 0){
                minCost = currCost;
            }else{
               minCost = Math.min(minCost, currCost);
            }
        }
        return minCost;
    }

    /**
     * https://leetcode.com/problems/count-complete-tree-nodes/
     * 222. Count Complete Tree Nodes
     *
     *  Input:
     *     1
     *    / \
     *   2   3
     *  / \  /
     * 4  5 6
     *
     * Output: 6
     *
     * **/
    public int countNodes(TreeNode root) {
        int h = height(root);
        return h < 0 ? 0 : height(root.right) == h -1 ?
                (1 << h) + countNodes(root.right)
                : (1 << h-1) + countNodes(root.left);
    }

    public int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }

    public int countRec(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return 1 + countRec(root.left) + countRec(root.right);
    }


}
