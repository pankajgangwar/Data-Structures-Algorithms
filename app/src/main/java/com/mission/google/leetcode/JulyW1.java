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

    /* https://leetcode.com/problems/lonely-pixel-ii/ */
    public int findBlackPixel(char[][] picture, int N) {
        HashMap<Integer, Integer> mapR = new HashMap<>();
        HashMap<Integer, Integer> mapC = new HashMap<>();

        int m = picture.length;
        int n = picture[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B'){
                    mapR.put(i, mapR.getOrDefault(i, 0) + 1);
                    mapC.put(j, mapC.getOrDefault(j, 0) + 1);
                }
            }
        }

        int res = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B' && mapR.containsKey(i) && mapC.containsKey(j)){
                    int countR = mapR.getOrDefault(i, 0);
                    int countC = mapC.getOrDefault(j, 0);
                    if((countR == N && countC == N )&& (countR == countC)){
                        res++;
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
