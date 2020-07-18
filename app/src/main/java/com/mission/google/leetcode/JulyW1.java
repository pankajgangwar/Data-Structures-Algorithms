package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.text.Caret;

import sun.reflect.generics.tree.Tree;

class JulyW1 {

    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /*
       https://codeforces.com/blog/entry/20935
       https://www.spoj.com/problems/PT07X/
       https://leetcode.com/problems/sum-of-distances-in-tree/
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */
    /* Binary search problems*/
    /* https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/ */
    /* https://leetcode.com/problems/k-th-smallest-prime-fraction/ */
    /* https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/ */


    /* https://leetcode.com/problems/divide-chocolate/ */
    /* https://leetcode.com/problems/integer-replacement/ */
    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */

    public static void main(String[] args) {
        JulyW1 curr = new JulyW1();
        int n = 3;
        int[][] edges = new int[][]{{0,1}, {1,2}, {0,2}};

        double[] suc = new double[]{0.5,0.5,0.2};
        int start = 0 , end = 2;
        curr.numSub("00111010");
    }

    /* https://leetcode.com/problems/the-maze-iii/ */

    /* 
    157. Read N Characters Given Read4
    https://leetcode.com/problems/read-n-characters-given-read4/ */
    /**
     * The read4 API is defined in the parent class Reader4.
     *     int read4(char[] buf4);
     */
    class Reader4{
        public int read4(char[] buf4){
            return 0;
        }
    }
    public class Solution extends Reader4 {
        /**
         * @param buf Destination buffer
         * @param n   Number of characters to read
         * @return    The number of actual characters read
         */
        public int read(char[] buf, int n) {
            boolean eof = false;
            char[] temp = new char[4];
            int total = 0;

            while (!eof && total < n) {
                int count = read4(temp);
                eof = count < 4;
                count = Math.min(count, n - total);
                for(int i = 0; i < count; i++){
                    buf[total++] = temp[i];
                }
            }
            return total;
        }
    }

    /*
    605. Can Place Flowers
    https://leetcode.com/problems/can-place-flowers/ */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int len = flowerbed.length;
        for(int i = 0; i < flowerbed.length ;){
            if(i < len && flowerbed[i] == 0 ){
                if(i+1 < len && flowerbed[i+1] == 0){
                    flowerbed[i] = 1;
                    --n;
                    i += 2;
                }else if(i == len -1){
                    flowerbed[i] = 1;
                   --n;
                    i++;
                }else{
                    i += 3;
                }
            }else{
                i += 2;
            }
        }
        System.out.println(Arrays.toString(flowerbed));
        return n <= 0;
    }

    /*
    541. Reverse String II
    https://leetcode.com/problems/reverse-string-ii/ */
    public String reverseStr(String s, int k) {
        int twiceK = 2*k;
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < s.length(); i += twiceK ){
            if(i + twiceK < s.length()){
                //System.out.println("reverse k chars within 2k window");
                StringBuilder out = new StringBuilder();
                for(int j = i; j < i + k; j++){
                    out.append(s.charAt(j));
                }
                out.reverse();
                for(int j = i + k; j < i + twiceK; j++){
                    out.append(s.charAt(j));
                }
                res.append(out.toString());
            }else if( i + k < s.length()){ // reverse k chars
                //System.out.println("reverse k chars");
                StringBuilder out = new StringBuilder();
                for(int j = i; j < i + k; j++){
                    out.append(s.charAt(j));
                }
                out.reverse();
                for(int j = i + k; j < s.length(); j++){
                    out.append(s.charAt(j));
                }
                res.append(out.toString());
            }else{ //reverse all
                //System.out.println("reverse all");
                StringBuilder out = new StringBuilder();
                for(int j = i; j < s.length(); j++){
                    out.append(s.charAt(j));
                }
                out.reverse();
                res.append(out.toString());
            }
        }
        return res.toString();
    }

    /*  
    LC : 401. Binary Watch
    https://leetcode.com/problems/binary-watch/ */
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();

        for(int h = 0; h < 12; h++){
            for(int m = 0 ; m < 60; m++){
                if((Integer.bitCount(h) + Integer.bitCount(m)) == num){
                    res.add(String.format("%d:%02d", h, m));
                }
            }
        }
        return res;
    }

    /* 
    LC : 371. Sum of Two Integers
    https://leetcode.com/problems/sum-of-two-integers/ */
    public int getSum(int a, int b) {
        while(b != 0){
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    /*
     407. Trapping Rain Water II
     https://leetcode.com/problems/trapping-rain-water-ii/ */
    public class Cell {
        int row, col, height;
        public Cell(int r, int c, int h){
            this.row = r;
            this.col = c;
            this.height = h;
        }
    }

    public int trapRainWater(int[][] heights) {
        if(heights == null || heights.length == 0 || heights[0].length == 0){
            return 0;
        }

        PriorityQueue<Cell> q = new PriorityQueue<Cell>( (a,b) -> a.height - b.height );

        int m = heights.length;
        int n = heights[0].length;
        boolean[][]visited = new boolean[m][n];

        for(int i = 0; i < m; i++){
            visited[i][0] = true;
            visited[i][n-1] = true;
            q.offer(new Cell(i, 0, heights[i][0]));
            q.offer(new Cell(i, n-1, heights[i][n-1]));
        }

        for(int j = 0; j < n; j++){
            visited[0][j] = true;
            visited[m-1][j] = true;
            q.offer(new Cell(0, j, heights[0][j]));
            q.offer(new Cell(m-1, j, heights[m-1][j]));
        }

        int[][] dirs = new int[][]{{1,0}, {0,1}, {0,-1}, {-1,0}};
        int res = 0;
        while(!q.isEmpty()){
            Cell curr = q.poll();
            for(int[]dir : dirs){
                int next_x = curr.row + dir[0];
                int next_y = curr.col + dir[1];
                int h = curr.height;

                if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]){
                    visited[next_x][next_y] = true;
                    res += Math.max(0, h - heights[next_x][next_y]);
                    q.offer(new Cell(next_x, next_y, Math.max(h, heights[next_x][next_y])));
                }
            }
        }
        return res;
    }

    /*
    LC : 533
    https://leetcode.com/problems/lonely-pixel-ii/
    */
    public int findBlackPixel(char[][] picture, int N) {
        HashMap<Integer, Integer> mapR = new HashMap<>();
        HashMap<Integer, Integer> mapC = new HashMap<>();
        HashMap<String, Integer> mapSignature = new HashMap<>();
        int m = picture.length;
        int n = picture[0].length;
        String[] rowSign = new String[m];
        for(int i = 0; i < m; i++){
            StringBuilder builder = new StringBuilder();
            for(int j = 0; j < n; j++){
                builder.append(picture[i][j]);
                if(picture[i][j] == 'B'){
                    mapR.put(i, mapR.getOrDefault(i, 0) + 1);
                    mapC.put(j, mapC.getOrDefault(j, 0) + 1);
                }
            }
            String signature = builder.toString();
            rowSign[i] = signature;
            mapSignature.put(signature, mapSignature.getOrDefault(signature, 0) + 1);
        }

        int res = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B' && mapR.containsKey(i) && mapC.containsKey(j)){
                    int countR = mapR.getOrDefault(i, 0);
                    int countC = mapC.getOrDefault(j, 0);
                    if((countR == N && countC == N )){ //Rule 1
                        String sign = rowSign[i];
                        int signatureCount = mapSignature.getOrDefault(sign, 0);
                        if(signatureCount == N){
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    /* LC : 109. Convert Sorted List to Binary Search Tree
    *  https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
    * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
    *  Time : O(nlogn)
    */
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null) return null;
        if(head.next == null) return new TreeNode(head.val);
        ListNode mid = null;
        ListNode fast = head, slow = head;
        ListNode left = head, right = head;
        ListNode prevToMid = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            prevToMid = slow;
            slow = slow.next;
        }
        mid = slow;
        if(mid != head) {
            prevToMid.next = null;
        }else{
            left = null;
        }
        right = mid.next;
        TreeNode root = new TreeNode(mid.val);
        root.left = sortedListToBST(left);
        root.right = sortedListToBST(right);
        return root;
    }

    public TreeNode sortedListToBSTI(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null){
            head = head.next;
            list.add(head.val);
        }
        return createBST(list, 0, list.size() - 1);
    }
    TreeNode createBST(List<Integer> nums, int start, int end){
        if(start > end){
            return null;
        }
        int mid = (start+end) / 2 ;
        TreeNode root = new TreeNode(nums.get(mid));
        root.left = createBST(nums, start, mid-1);
        root.right = createBST(nums, mid+1, end);
        return root;
    }

    /*
    LC : 1513
    https://leetcode.com/problems/number-of-substrings-with-only-1s/ */
    public int numSub(String s) {
        int mod = (int)1e9 + 7;
        int res = 0;
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && s.charAt(j) == '1'){
                j++;
            }
            int n = j - i;
            res += (long) n * (n + 1) / 2 % mod;
            i = j + 1;
        }
        return res;
    }

    /*
      LC : 1514
    * https://leetcode.com/problems/path-with-maximum-probability/
    * */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        return probabilityWithBellmanFord(n, edges, succProb, start, end);
    }

    public double probabilityWithBellmanFord(int n, int[][] edges, double[] succProb, int start, int end){
        // Using Bellman-Ford Algorithm
        HashMap<Integer, List<int[]>> g = new HashMap<>();
        for(int i = 0; i < edges.length; i++){
            int a = edges[i][0];
            int b = edges[i][1];
            g.computeIfAbsent(b, l -> new ArrayList<>()).add(new int[]{a, i});
            g.computeIfAbsent(a, l -> new ArrayList<>()).add(new int[]{b, i});
        }

        double[] probs = new double[n];
        probs[start] = 1d;
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int[] a : g.getOrDefault(cur, new ArrayList<>())){
                int neigh = a[0];
                int idx = a[1];
                if(probs[cur] * succProb[idx] > probs[neigh]){
                    probs[neigh] = probs[cur] * succProb[idx];
                    q.offer(neigh);
                }
            }
        }
        return probs[end];   
    }

    public double probabilityWithDijkstra(int n, int[][] edges, double[] succProb, int start, int end){
        // Using Dijkstra's Algorithm
        HashMap<Integer, HashMap<Integer, Double>> graph = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dst = edges[i][1];
            graph.putIfAbsent(src, new HashMap<Integer, Double>());
            graph.putIfAbsent(dst, new HashMap<Integer, Double>());
            graph.get(src).put(dst, succProb[i]);
            graph.get(dst).put(src, succProb[i]);
        }

        PriorityQueue<double[]> pq = new PriorityQueue<>((a,b) -> -Double.compare(a[0], b[0]));

        pq.offer(new double[]{1, start});
        boolean[] visited = new boolean[n];
        while (!pq.isEmpty()){
            double[]curr = pq.poll();
            int curr_src = (int)curr[1];
            if(visited[curr_src]) continue;
            visited[curr_src] = true;
            if(curr_src == end){
                return curr[0];
            }
            HashMap<Integer, Double> adj = graph.getOrDefault(curr_src, new HashMap<Integer, Double>());
            for(Integer next_src : adj.keySet()){
                double prob = adj.get(next_src) * curr[0];
                pq.offer(new double[]{prob, next_src});
            }
        }
        return 0d;
    }

    public double probabilityWithFloydWarshall(int n, int[][] edges, double[] succProb, int start, int end){
        //Using Floyd-Warshall, all pair max probability
        // Gives TLE for this problem
        double[][] probs = new double[n][n];

        for (int i = 0; i < edges.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            probs[a][b] = probs[b][a] = succProb[i];
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    probs[i][j] = Math.max(probs[i][j], probs[i][k] * probs[k][j]);
                }
            }
        }
        return probs[start][end];
    }



    /*
    * LC : 1512
    * https://leetcode.com/problems/number-of-good-pairs/
    * */
    public int numIdenticalPairs(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int count = 0;
        while (i < nums.length){
            int j = i + 1;
            while (j < nums.length && nums[j] == nums[i]){
                j++;
            }
            int n = j - i;
            count += (n * ( n - 1)) / 2;
            i = j;
        }
        return count;
    }

    /*
    LC : 1509
    https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/ */
    public int minDifference(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        if(n <= 4) return 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i + n - 4 < n; i++) {
            min = Math.min(min, nums[i+n-4] - nums[i]);
        }
        return min;
    }

    /* LC : 1507
    * https://leetcode.com/problems/reformat-date/
    * */
    public int rangeSum(int[] nums, int n, int left, int right) {
        int[] newArr = new int[(n * (n + 1)) / 2];
        int mod = (int)1e9 + 7;
        int idx = 0;
        for (int i = 0; i < nums.length; i++){
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                newArr[idx++] = sum % mod;
            }
        }
        Arrays.sort(newArr);
        int sum = 0;
        for (int i = left - 1; i < right; i++) {
            sum += newArr[i];
            sum = sum % mod;
        }
        return sum;
    }

    /* LC : 1508
     https://leetcode.com/problems/range-sum-of-sorted-subarray-sums/ */
    public String reformatDate(String date) {
        String[] month = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayList<String> monthList = new ArrayList<>(Arrays.asList(month));
        String[] curr = date.split("\\s");
        StringBuilder builder = new StringBuilder();
        builder.append(curr[2]);
        builder.append("-");
        int mnth = monthList.indexOf(curr[1]) + 1;
        if(mnth <= 9){
            builder.append("0" + mnth);
        }else{
            builder.append(mnth);
        }
        builder.append("-");
        StringBuilder dt = new StringBuilder(curr[0]);
        dt.deleteCharAt(dt.length() - 1);
        dt.deleteCharAt(dt.length() - 1);
        int intDt = Integer.valueOf(dt.toString());
        if(intDt <= 9){
            builder.append("0" + intDt);
        }else{
            builder.append(intDt);
        }

        //System.out.println("builder1.toString() = " + builder.toString());
        return builder.toString();
    }

    /*
    LC : 958
    https://leetcode.com/problems/check-completeness-of-a-binary-tree/
    */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        Integer lastLevel = null;
        while (!q.isEmpty()){
            int size = q.size();
            boolean isLastLevel = false;
            while (size-- > 0){
                TreeNode curr = q.poll();
                if(curr.left == null){
                    if(curr.right != null){
                        return false;
                    }
                }
                if(isLastLevel && curr.left != null){ // This node should not have any child as node with left child already encountered
                    return false;
                }
                if(curr.right == null && lastLevel == null) { //We are at last level
                    isLastLevel = true;
                    lastLevel = level + 2;
                }
                if(curr.left != null) {
                    q.offer(curr.left);
                }
                if(curr.right != null) {
                    q.offer(curr.right);
                }
                if(lastLevel != null){
                    if(lastLevel == level){
                        return false;
                    }
                }
            }
            level++;
        }
        return true;
    }

    /* LC : 920
    https://leetcode.com/problems/number-of-music-playlists/
    */
    public int numMusicPlaylists(int N, int L, int K) {
        int mod = (int)1e9 + 7;
        long[][] dp = new long[L+1][N+1];
        dp[0][0] = 1;

        for (int i = 1; i <= L; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = (dp[i-1][j-1] * (N - (j - 1))) % mod;
                if(j > K){
                   dp[i][j] = (dp[i][j] + (dp[i-1][j] * (j - K)) % mod)% mod;
                }
            }
        }
        return (int)dp[L][N];
    }

}
