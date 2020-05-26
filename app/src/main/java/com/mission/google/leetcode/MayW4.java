package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import sun.reflect.generics.tree.Tree;

public class MayW4 {

    // TODO: 5/15/2020  
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */

    /* https://leetcode.com/problems/couples-holding-hands/ */
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

    public static void main(String[] args) {
        MayW4 test = new MayW4();
        int[][] grid = new int[][]{
                {0,-3}
        };
        
        //boolean res = test.isPalindrome("");
        int res = 0;
        System.out.println("res = " + res);
    }

    /*
    LC : 1302
    Q : https://leetcode.com/problems/deepest-leaves-sum/
    A : https://leetcode.com/problems/deepest-leaves-sum/discuss/652763/JAVA-O(n)-DFS-traversal
    */
    public int deepestLeavesSum(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        helper(root, list, 0);
        int lastlevel = list.size() - 1;
        List<Integer> mList = list.get(lastlevel);
        int ans = 0;
        for (int i : mList) {
            ans += i;
        }
        return ans;
    }

    public void helper(TreeNode root, ArrayList<ArrayList<Integer>> list, int level){
        if(root == null) return;
        if(list.size() == level){
            ArrayList<Integer> levelList = new ArrayList<>();
            levelList.add(root.val);
            list.add(levelList);
        }else{
            ArrayList<Integer> levelList = list.get(level);
            levelList.add(root.val);
            list.set(level, levelList);
        }
        helper(root.left, list, level + 1);
        helper(root.right, list, level + 1);
    }

    /*
    LC : 834
    https://leetcode.com/problems/sum-of-distances-in-tree/
    DP on trees
    */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        if (N == 1) {
            return new int[N];
        }
        if (N == 2) {
            return new int[]{1, 1};
        }

        List<int[]>[] graph = new ArrayList[N];
        for(int i = 0; i < N; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < edges.length; i++){
            graph[edges[i][0]].add(new int[]{edges[i][1], 0, 0});
            graph[edges[i][1]].add(new int[]{edges[i][0], 0, 0});
        }

        int[] result = new int[N];
        boolean[]seen = new boolean[N];

        for(int i = 0; i < N; i++){
            result[i] = dfs(graph, i, seen)[0];
        }
        return result;
    }

    private int[] dfs(List<int[]>[] graph, int i, boolean[] seen){
        seen[i] = true;
        int count = 1;
        int sum = 0;

        for(int[] edge : graph[i]){
            if(seen[edge[0]]) continue;
            if(edge[1] == 0 || edge[2] == 0){
                int[] ans = dfs(graph, edge[0], seen);
                edge[1] = ans[0];
                edge[2] = ans[1];
            }
            count += edge[2];
            sum += (edge[1] + edge[2]);
        }
        seen[i] = false;
        int[] res = new int[]{sum, count};
        return res;
    }

    /*
        LC : 432
        https://leetcode.com/problems/all-oone-data-structure/
        Revisit
    */
    class AllOne {
        class Bucket {
            Bucket pre;
            Bucket next;
            int count;
            Set<String> keySet;
            public Bucket(int count){
                this.count = count;
                keySet = new HashSet<>();
            }
        }

        /** Initialize your data structure here. */
        HashMap<String, Integer> fmap;
        HashMap<Integer, Bucket> countBucketMap;
        private Bucket head;
        private Bucket tail;

        public AllOne() {
            head = new Bucket(Integer.MIN_VALUE);
            tail = new Bucket(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
            fmap = new HashMap<>();
            countBucketMap = new HashMap<>();
        }
        
        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            if(fmap.containsKey(key)){
                changeKey(key, 1);
            }else{
                fmap.put(key, 1);
                if(head.next.count != 1){
                    addBucketAfter(new Bucket(1), head);
                }
                head.next.keySet.add(key);
                countBucketMap.put(1, head.next);
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if(fmap.containsKey(key)) {
                int count = fmap.get(key);
                if (count == 1) {
                    fmap.remove(key);
                    removeKeyFromBucket(countBucketMap.get(count), key);
                } else {
                    changeKey(key, -1);
                }
            }
        }
        
        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            return tail.pre == head ? "" : tail.pre.keySet.iterator().next();
        }
        
        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            return head.next == tail ? "" : head.next.keySet.iterator().next();
        }

        private void changeKey(String key, int offset) {
            int count = fmap.get(key);
            fmap.put(key, count + offset);
            Bucket oldBucket = countBucketMap.get(count);
            Bucket newBucket;
            if(countBucketMap.containsKey(count + offset)){
                newBucket = countBucketMap.get(count + offset);
            }else{
                newBucket = new Bucket(count + offset);
                countBucketMap.put(count + offset, newBucket);
                addBucketAfter(newBucket, offset == 1 ? oldBucket : oldBucket.pre);
            }
            newBucket.keySet.add(key);
            removeKeyFromBucket(oldBucket, key);
        }

        private void removeKeyFromBucket(Bucket bucket, String key) {
            bucket.keySet.remove(key);
            if(bucket.keySet.isEmpty()){
                removeBucketFromList(bucket);
                countBucketMap.remove(bucket.count);
            }
        }

        private void removeBucketFromList(Bucket bucket)  {
            Bucket prev = bucket.pre;
            Bucket next = bucket.next;
            prev.next = next;
            next.pre = prev;
            bucket.pre = null;
            bucket.next = null;
        }

        private void addBucketAfter(Bucket newBucket, Bucket prevBucket) {
            newBucket.pre = prevBucket;
            newBucket.next = prevBucket.next;
            prevBucket.next.pre = newBucket;
            prevBucket.next = newBucket;
        }
    }

    /* 
    LC : 1035
    https://leetcode.com/problems/uncrossed-lines/
    */
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length][B.length];
        for(int i = 0; i < A.length; i++){
            Arrays.fill(dp[i], -1);
        }
        //return helper(dp, A, B, 0, 0 );
        return dphelper(A, B);
    }

    public int dphelper(int[]a , int[] b){
        int m = a.length;
        int n = b.length;

        int[][] dp = new int[m+1][n+1];
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(a[i-1] == b[j-1]){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        return dp[m][n];
    }

    public int helper(int[][] dp, int[] a, int [] b, int x, int y){
        if(x == a.length || y == b.length){
            return 0;
        }
        if(dp[x][y] != -1){
            return dp[x][y];
        }
        int max = 0;
        if(a[x] == b[y]){
            max = Math.max(helper(dp, a, b, x + 1, y + 1) + 1, max);
        }else{
            max = Math.max(helper(dp, a, b, x + 1, y), max);// Skip x
            max = Math.max(helper(dp, a, b, x, y + 1), max);// Skip y
        }

        dp[x][y] = max;
        return dp[x][y];
    }

    /* 
       LC : 886
       https://leetcode.com/problems/possible-bipartition/
       https://leetcode.com/problems/possible-bipartition/discuss/650930/JAVA-Union-find
       Graph Coloring, Union-find
    */
    public boolean possibleBipartitionI(int n, int[][] dislikes) {
        int[] colors = new int[n];
        LinkedList<Integer>[] graph = new LinkedList[n];

        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++ ){
            if(colors[i] != 0) continue;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            colors[i] = 1;

            while(!queue.isEmpty()){
                int curr = queue.poll();

                for(int adj : graph[curr]){
                    if(colors[adj] == 0){
                        colors[adj] = -colors[curr];
                        queue.offer(adj);
                    }else if(colors[adj] != -colors[curr]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] root = new int[n];
        for (int i = 0; i < root.length; i++) {
            root[i] = i;
        }
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<Integer>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++){
            LinkedList<Integer> adjList = graph[i];
            for(int adj : adjList) {
                int xRoot = find(i, root);
                int yRoot = find(adj, root);
                if(xRoot != yRoot){
                    root[find(adjList.get(0), root)] = yRoot;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public int find(int x, int[] root){
        while(x != root[x]){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }


}