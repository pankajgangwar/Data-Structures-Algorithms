package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

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
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/increasing-subsequences/ */
    /* https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/ */

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

    /* https://leetcode.com/problems/brick-wall/ */
    public int leastBricks(List<List<Integer>> wall) {
        return 0;
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
