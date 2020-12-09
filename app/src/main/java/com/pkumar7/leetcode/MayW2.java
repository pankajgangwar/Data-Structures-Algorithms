package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;
import com.pkumar7.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class MayW2 {
    // TODO: 5/15/2020  
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/increasing-subsequences/ */
    /* https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/ */
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/

    /* DP on trees */
    /* 
       https://leetcode.com/problems/sum-of-distances-in-tree/ 
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */

    public static void main(String[] args) {
        MayW2 w2 = new MayW2();
        //w2.leastBricks(new ArrayList<>());
        ListNode head = new ListNode(3);
        head.next = new ListNode(1);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(2);
        //w2.sortList(head);
        List<String> list1 = Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com");
        List<String> list2 = Arrays.asList("John", "johnnybravo@mail.com");
        List<String> list3 = Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com");
        List<String> list4 = Arrays.asList("Mary", "mary@mail.com");
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(list1);
        accounts.add(list2);
        accounts.add(list3);
        accounts.add(list4);

        w2.accountsMerge(accounts);
    }

    /*
    https://leetcode.com/problems/couples-holding-hands/
    */
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

    /* 
        LC : 1135
        https://leetcode.com/problems/connecting-cities-with-minimum-cost/
        Kruskals Algorithm
    */
    public int minimumCost(int n, int[][] connections) {
        Arrays.sort(connections, (a,b) -> a[2] - b[2]);
        UnionFind unionfind = new UnionFind(n);
        int cost = 0;
        for(int i = 0; i < connections.length; i++) {
            int x = connections[i][0] - 1;
            int y = connections[i][1] - 1;
            
            int xroot = unionfind.find(x);
            int yroot = unionfind.find(y);
            
            if(xroot == yroot){ // These 2 nodes are already connected
                continue;
            }
            unionfind.union(x, y); // Connect the 2 nodes
            
            cost += connections[i][2];
        }
        if(unionfind.count() != 1){
            return -1;            
        }
        return cost;
    }
    
    /*
        LC : 1229
        https://leetcode.com/problems/meeting-scheduler/
        Ans: https://leetcode.com/problems/meeting-scheduler/discuss/631154/JAVA-O(n)-using-2-pointers
    */
    public List<Integer> minAvailableDuration(int[][] s1, int[][] s2, int d) {
        ArrayList<Integer> res = new ArrayList<>();

        Arrays.sort(s1, (a,b) -> a[1] - b[1]);
        Arrays.sort(s2, (a,b) -> a[1] - b[1]);

        int i = 0, j = 0;
        for(; i < s1.length && j < s2.length; ){
            int start = Math.max(s1[i][0], s2[j][0]);
            int end = start + d;
            if(end <= Math.min(s1[i][1], s2[j][1])){
                res.add(start);
                res.add(end);
                break;
            }else if(s1[i][1] <  s2[j][1]){
                i++;
            }else if(s2[j][1] <  s1[i][1]){
                j++;
            }else{
                i++;
                j++;
            }
        }
        return res;
    }

    /* LC : 435
    * https://leetcode.com/problems/non-overlapping-intervals/
    * Ans : https://leetcode.com/problems/non-overlapping-intervals/discuss/630989/JAVA-Based-on-highest-voted-O(nlogn)
    * Revisit : Why can't we sort the interval with start time
    * */
    public int eraseOverlapIntervals(int[][] intervals) {
        // Arrays.sort(intervals, (a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        Arrays.sort(intervals, (a,b) -> a[1] - b[1]); // Sort the intervals with end time

        int n = intervals.length;

        if(n == 1 || n == 0) return 0;

        List<int[]> res = new ArrayList<>();
        int[] prev = intervals[0];
        res.add(prev);
        for(int i = 1; i < intervals.length; i++){
            int[] curr = intervals[i];
            if(prev[1] <= curr[0]){
                res.add(curr);
                prev = curr;
            }
        }
        return n - res.size();
    }

    /* https://leetcode.com/problems/largest-bst-subtree/ 
       Ans: https://leetcode.com/problems/largest-bst-subtree/discuss/630909/JAVA-0ms-O(n)-solution
       Revisit
    */
    public int largestBSTSubtree(TreeNode root) {
        int[] res = largestBST(root);
        return res[1]; //returns size of largest BST
    }

    public int[] largestBST(TreeNode root) {
        if(root == null) {
            // { is BST, max length of BST, max from left, min from right}
            return new int[]{1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        }

        if(root.left == null && root.right == null) {
            // { Is BST, length of leaf is 1, max is root, min is root}
            return new int[]{1, 1, root.val, root.val };
        }

        int[] leftRes = largestBST(root.left);
        int[] rightRes = largestBST(root.right);

        int[] res = new int[]{0, 0, root.val, root.val };

        if(leftRes[0] == 1 && rightRes[0] == 1){ //If left and right both are BST
            int max = leftRes[3];
            int min = rightRes[2];

            if(max < root.val && root.val < min){ // root should be between max from left tree and min from right
                 res[0] = 1; // Its a BST
                 res[1] = leftRes[1] + rightRes[1] + 1;
                 res[2] = Math.min(leftRes[2], res[2]); // min from left tree
                 res[3] = Math.max(res[3], rightRes[3]); // max from right tree
            }else{
                res[1] = Math.max(leftRes[1], rightRes[1]); // return max length from left and right subtree
            }
        }else{
            res[1] = Math.max(leftRes[1], rightRes[1]);
        }
        return res;
    }

    /* https://leetcode.com/problems/monotone-increasing-digits/ */
    public int monotoneIncreasingDigits(int number) {
        char[] arr = String.valueOf(number).toCharArray();
        int n = arr.length;

        int startIdx = n - 1;
        for(int i = n - 1; i > 0; i-- ){
            if(arr[i] < arr[i-1]){
                startIdx = i - 1;
                arr[i-1]--;
            }
        }

        for(int i = startIdx + 1; i < n; i++){
            arr[i] = '9';
        }

        String ans = new String(arr);
        return Integer.valueOf(ans);
    }

    /**
     * 402. Remove K Digits
     * https://leetcode.com/problems/remove-k-digits/
     */
    public String removeKdigits(String num, int k) {
        int n = num.length();
        if(k == n) return "0";
        Stack<Character> stack = new Stack<>();
        int i = 0;
        while (i < n) {
            while(k > 0 && !stack.isEmpty() && stack.peek() > num.charAt(i)){
                stack.pop();
                k--;
            }
            stack.push(num.charAt(i++));
        }
        //If the numbers are in increasing order i.e 123456
        while(k > 0){
            stack.pop();
            k--;
        }
        StringBuilder builder = new StringBuilder();
        while(!stack.isEmpty()){
            builder.append(stack.pop());
        }
        builder.reverse();
        while(builder.length() > 0 && builder.charAt(0) == '0'){
            builder.deleteCharAt(0);
        }
        if(builder.length() == 0){
            builder.append('0');
        }
        return builder.toString();
    }

    /* https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
    * Ans : https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/discuss/629813/JAVA-Simple-Greedy-Sol*/
    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) return 0;

        Arrays.sort(points, (a,b) -> a[1] - b[1]);

        List<int[]> res = new  ArrayList<>();
        res.add(points[0]);

        for(int[] point : points){
            int[] prev = res.get(res.size()-1);
            if(prev[1] < point[0]) {
                res.add(point);
            }
        }
        return res.size();
    }

    /* https://leetcode.com/problems/accounts-merge/ */
    /*
       Similar to https://leetcode.com/problems/smallest-string-with-swaps/
    */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> mapEmailWithId = new HashMap<>();
        HashMap<String, String> mapWithNames = new HashMap<>();

        UnionFind unionFind = new UnionFind();
        int id = 0;
        for(List<String> account : accounts){
            for(int i = 1; i < account.size(); i++){
                String email = account.get(i);
                mapWithNames.put(email, account.get(0));
                
                if(!mapEmailWithId.containsKey(email)){
                    mapEmailWithId.put(email, id++);
                }
                unionFind.union(mapEmailWithId.get(account.get(1)), mapEmailWithId.get(email));
            }
        }

        Map<Integer, ArrayList<String>> mapWithHead = new HashMap<>();

        for(String email : mapWithNames.keySet()){
            int root = unionFind.find(mapEmailWithId.get(email));
            mapWithHead.putIfAbsent(root, new ArrayList<>());
            mapWithHead.get(root).add(email);
        }

        List<List<String>> res = new ArrayList<>();
        for(Map.Entry<Integer, ArrayList<String>> entry : mapWithHead.entrySet()){
            ArrayList<String> accountList = entry.getValue();
            String name = mapWithNames.get(accountList.get(0));
            List<String> currlist = new ArrayList<>();
            Collections.sort(accountList);
            currlist.add(name);
            currlist.addAll(accountList);
            res.add(currlist);
        }
        return res;
    }

    class UnionFind {
        private int count = 0;
        private int[] parent, rank;
        int n = 10001;
        public UnionFind() {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

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

    /* https://leetcode.com/problems/sort-list/ */
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    public ListNode mergeSort(ListNode head){
        ListNode fast = head;
        ListNode middle = head;
        ListNode prev = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            prev = middle;
            middle = middle.next;
        }
        if(head == middle) return head;

        prev.next = null; // split into 2 halves

        ListNode first = mergeSort(head);
        ListNode second = mergeSort(middle);
        ListNode sorted = mergeSortedListIterative(first, second);
        return sorted;
    }

    /* https://leetcode.com/problems/merge-two-sorted-lists/ */
    public ListNode mergeSortedListIterative(ListNode l1, ListNode l2) {
        ListNode a = l1;
        ListNode b = l2;
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (a != null && b != null) {
            if (a.val < b.val) {
                dummy.next = a;
                a = a.next;
            } else {
                dummy.next = b;
                b = b.next;
            }
            dummy = dummy.next;
        }

        if (a != null) {
            dummy.next = a;
        }
        if (b != null) {
            dummy.next = b;
        }
        return head.next;
    }


    /* https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/ */
    public final TreeNode getTargetCopy(final TreeNode o, final TreeNode c, final TreeNode t) {
        if(o == null && c == null) return null;
        if(t.val == c.val){
            return c;
        }
        TreeNode resLeft = getTargetCopy(o.left, c.left, t);
        TreeNode resRight = getTargetCopy(o.right, c.right, t);

        if(resLeft != null) return resLeft;
        return resRight;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node() {}
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };
    /* https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/ */
    public Node treeToDoublyList(Node root) {
        if(root == null)
            return null;

        helper(root);
        first.left = last;
        last.right = first;
        return first;
    }

    Node last = null, first = null;
    public void helper(Node root){
        if(root != null){
            helper(root.left);
            if(last != null){
                last.right = root;
                root.left = last;
            }else{
                first = root;
            }
            last = root;
            helper(root.right);
        }
    }

    /* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/ */
    public int maxProfit(int[] prices) {
        int t1Cost = Integer.MAX_VALUE;
        int t2Cost = Integer.MAX_VALUE;
        int t1Profit = 0, t2Profit = 0;
        for(int i = 0; i < prices.length; i++){
            int price = prices[i];
            t1Cost = Math.min(t1Cost, price);
            t1Profit = Math.max(t1Profit, price - t1Cost);
            t2Cost = Math.min(t2Cost, price - t1Profit);
            t2Profit = Math.max(t2Profit, price - t2Cost);
        }
        return t2Profit;
    }

    /* https://leetcode.com/problems/excel-sheet-column-number/ 
       Revisit
    */
    public int titleToNumber(String s) {
        int n = s.length();
        if( n == 1) return s.charAt(0) - 'A' + 1;

        int base = 26;
        int number = 0;
        for(int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'A' + 1;
            number += idx * Math.pow(base, n - i -1);
        }
        return number;
    }

    /* https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/ */
     public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        LinkedList<Integer> adjList[] = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new LinkedList<>();
        }
        for(int[] edge : edges){
            int src = edge[0];
            int dst = edge[1];
            adjList[src].add(dst);
            adjList[dst].add(src);
        }
        HashSet<Integer> apples = new HashSet<>();
        for (int i = 0; i < hasApple.size(); i++) {
            if(hasApple.get(i)){
                apples.add(i);
            }
        }
        boolean[] visited = new boolean[n];
        return helper(adjList, 0, apples, visited);
    }

    public int helper(LinkedList<Integer>[] graph, int src, HashSet<Integer> apples, boolean[] visited){
        visited[src] = true;
        int totalTime = 0;
        for (int i = 0; i < graph[src].size(); i++) {
            int dst = graph[src].get(i);
            if(visited[dst]) continue;
            totalTime += helper(graph, dst, apples, visited);
        }

        if((totalTime > 0 || apples.contains(src)) && src != 0) totalTime += 2;
        return totalTime;
    }

    /* https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/
    *  Revisit
    * */
    public int countTriplets(int[] arr) {
        int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int a = arr[i];
            for (int j = i + 1; j < n; j++) {
                a ^= arr[j];
                if(a == 0){
                    res += j - i;
                }
            }
        }
        return res;
    }

    public int countTripletsI(int[] arr) {
        int n = arr.length + 1;
        int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i-1] ^ arr[i-1];
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if(prefix[i] == prefix[j]){
                    res += j - i - 1;
                }
            }
        }
        return res;
    }

    public int solutionTLE(int[] arr) {
        int res = 0;
        int n = arr.length;
        for (int i = 0; i < n ; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j; k < n; k++) {
                    int a = 0;
                    int b = 0;
                    for (int l = i; l <= j - 1 ; l++) {
                        a ^= arr[l];
                    }
                    for (int l = j; l <= k ; l++) {
                        b ^= arr[l];
                    }
                    if(a == b) res++;
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/build-an-array-with-stack-operations/ */
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int idx = 0;
        for (int i = 1; i <= n && idx < target.length; i++) {
            stack.push(i);
            res.add("Push");
            if(stack.peek() != target[idx]){
                stack.pop();
                res.add("Pop");
            }else{
                idx++;
            }
        }
        return res;
    }


    /* https://leetcode.com/problems/valid-perfect-square/ */
    public boolean isPerfectSquare(int n) {
        String number = String.valueOf(n);
        int last = number.charAt(number.length()-1) - '0';
        if(last == 2 || last == 3 || last == 7 || last == 8){
            return false;
        }
        for (int i = 1; i * i <= n; i++) {
            if(n % i == 0 && (n / i  == i)) return true;
        }
        return false;
    }

    public boolean binarySearch(int num){
        if(num < 2) return true;
        long left = 2;
        long right = num / 2;
        while (left <= right){
            long mid = left + (right - left) / 2;
            long guess = mid * mid;
            if(guess == num) return true;
            if(guess > num){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return false;
    }


}
