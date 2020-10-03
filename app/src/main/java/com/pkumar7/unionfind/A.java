package com.pkumar7.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Pankaj Kumar on 12/August/2020
 */
class A {

    /* 1584. Min Cost to Connect All Points
     * https://leetcode.com/problems/min-cost-to-connect-all-points/discuss/844025/JAVA-Union-find-Similar-to-1135
     * https://leetcode.com/problems/min-cost-to-connect-all-points/
     * Kruskal's Algorithm
     * */
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<List<Integer>> connections = new ArrayList<>();
        HashMap<String, String> visited = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                int x1 = points[i][0];
                int y1 = points[i][1];

                int x2 = points[j][0];
                int y2 = points[j][1];
                if(x1 == x2 && y1 == y2)continue;
                if(i == j) continue;
                String p1 = x1+""+y1;
                String p2 = x2+""+y2;
                if(visited.containsKey(p2))continue;
                visited.put(p1, p2);
                int val = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                List<Integer> curr = new ArrayList<>();
                curr.add(i + 1);
                curr.add(j + 1);
                curr.add(val);
                connections.add(curr);
            }
        }
        int[][] newConn = new int[connections.size()][3];
        for (int i = 0; i < connections.size(); i++) {
            newConn[i] = new int[]{connections.get(i).get(0),
                    connections.get(i).get(1),
                    connections.get(i).get(2)};
        }
        return minimumCost(n, newConn);
    }

    public int minimumCost(int n, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        Arrays.fill(rank, -1);
        int cost = 0;
        for(int i = 0; i < connections.length; i++) {
            int x = connections[i][0] - 1;
            int y = connections[i][1] - 1;
            int xroot = find(parent, x);
            int yroot = find(parent, y);
            if(xroot == yroot){
                continue;
            }
            union(xroot, yroot, parent, rank);
            cost += connections[i][2];
        }
        return cost;
    }


    public void union(int x, int y, int[] parent, int[] rank){
        int xroot = find(parent, x);
        int yroot = find(parent, y);
        if(xroot == yroot) return;
        if(rank[yroot] > rank[xroot]){
            parent[xroot] = yroot;
        }else{
            parent[yroot] = xroot;
            if(rank[xroot] == rank[yroot]){
                rank[xroot]++;
            }
        }
    }


    /* 1579. Remove Max Number of Edges to Keep Graph Fully Traversable
     * https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/
     *  n = 4, edges = [[3,1,2],[3,2,3],[1,1,3],[1,2,4],[1,1,2],[2,3,4]]
     */
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        int[] parentA = new int[n];
        int[] rankA = new int[n];
        int[] parentB = new int[n];
        int[] rankB = new int[n];
        int edgesAdded = 0;

        for (int i = 0; i < n; i++) {
            parentA[i] = i;
            parentB[i] = i;
        }
        Arrays.sort(edges, (a, b) -> (b[0] - a[0]));
        for (int i = 0; i < edges.length; i++) {
            int type = edges[i][0];
            int src = edges[i][1] - 1;
            int dst = edges[i][2] - 1;
            if(type == 1){
                if(addRoute(parentA, rankA, src, dst)){
                    edgesAdded++;
                }
            }else if(type == 2){
                if(addRoute(parentB, rankB, src, dst)){
                    edgesAdded++;
                }
            }else {
                if(addRoute(parentA, rankA, src, dst) && addRoute(parentB, rankB, src, dst)){
                    edgesAdded++;
                }
            }
        }
        int connectedA = 0, connectedB = 0;
        for (int i = 0; i < n; i++) {
            if(parentA[i] == i) connectedA++;
            if(parentB[i] == i) connectedB++;
        }
        return connectedA == 1 && connectedB == 1 ? edges.length - edgesAdded : -1;
    }

    public boolean addRoute(int[] parent, int[] rank, int src, int dst){
        int xRoot = find(parent, src);
        int yRoot = find(parent, dst);
        if (xRoot == yRoot) {
            return false;
        }
        if (rank[xRoot] > rank[yRoot]) {
            parent[xRoot] = yRoot;
        }
        else {
            parent[xRoot] = yRoot;
            if (rank[xRoot] == rank[yRoot]) {
                rank[xRoot]++;
            }
        }
        return true;
    }

    /* 1562. Find Latest Group of Size M
    * https://leetcode.com/problems/find-latest-group-of-size-m/
    * */
    int[] parent, groups, count, rank;
    public int findLatestStep(int[] arr, int m) {
        int n = arr.length;
        groups = new int[n + 1]; // Bucket of size of each group
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n ; i++) {
            parent[i] = i;
        }
        count = new int[n + 1];
        Arrays.fill(count, 1);// By default all groups are of size 1
        boolean[] visited = new boolean[n + 1];
        int lastStep = -1, currStep = 1;
        for(int x : arr){
            int curr = x;
            groups[1]++;// We first add x to group of size 1
            if(curr - 1 > 0 && visited[curr - 1]){
                union(curr, curr - 1);
            }
            if(curr + 1 <= n && visited[curr + 1]){
                union(curr, curr + 1);
            }
            visited[curr] = true;
            if(groups[m] > 0){
                lastStep = currStep;
            }
            currStep++;
        }
        return lastStep;
    }

    public void union(int x, int y){
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);
        if(xRoot != yRoot){
            groups[count[xRoot]]--;
            groups[count[yRoot]]--;
            count[xRoot] += count[yRoot];
            count[yRoot] = count[xRoot];
            groups[count[xRoot]]++;
            if(rank[xRoot] < rank[yRoot]){
                parent[xRoot] = yRoot;
            }else{
                parent[yRoot] = xRoot;
            }
            if(rank[xRoot] == rank[yRoot]){
                rank[xRoot]++;
            }
        }
    }

    /* 1168. Optimize Water Distribution in a Village
    * https://leetcode.com/problems/optimize-water-distribution-in-a-village/
    * Kruskal's Algorithm : Time : O(nlogn)
    */
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        List<int[]> edges = new ArrayList<>();
        int res = 0;
        int[] parent = new int[n + 1];
        for(int i = 0; i < n; i++) {
            parent[i + 1] = i + 1;
            edges.add(new int[]{0, i + 1, wells[i]});
        }

        for(int[] pipe : pipes){
            edges.add(pipe);
        }
        Collections.sort(edges, (a,b) -> a[2] - b[2]);
        for(int[] pipe : edges){
            int a = pipe[0];
            int b = pipe[1];
            int cost = pipe[2];
            int aRoot = find(parent, a);
            int bRoot = find(parent, b);

            if(aRoot != bRoot){
                parent[aRoot] = bRoot;
                res += cost;
            }
        }
        return res;
    }

    /* 1102. Path With Maximum Minimum Value
     * https://leetcode.com/problems/path-with-maximum-minimum-value/
     * Maximum points collections
     * Dijkstra's algo
     * */
    public int maximumMinimumPath(int[][] matrix) {
        return maximumMinimumPathUnionFind(matrix);
    }

    public int maximumMinimumPathUnionFind(int[][] matrix) {
        List<int[]> coord = new ArrayList<>();

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                coord.add(new int[]{i,j});
            }
        }
        Collections.sort(coord, (a, b) -> matrix[b[0]][b[1]] - matrix[a[0]][a[1]]);
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        boolean[][] visited = new boolean[rows][cols];

        UnionFind unionFind = new UnionFind(rows, cols);
        for (int[] curr : coord){
            visited[curr[0]][curr[1]] = true;

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || !visited[next_x][next_y])
                    continue;

                unionFind.union(curr[0], curr[1], next_x, next_y);
            }

            if(unionFind.find(0,0) == unionFind.find(rows -1, cols -1)) return matrix[curr[0]][curr[1]];
        }
        return -1;
    }

    class UnionFind {
        private int[] parent, rank;
        int row, col;

        public UnionFind(int rows, int cols) {
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            this.row = rows;
            this.col = cols;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    parent[i* cols + j] = i * cols + j;
                    rank[i * cols + j] = 1;
                }
            }
        }

        public int find(int x, int y) {
            int p = x * col + y;
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        public void union(int x1, int y1, int x2, int y2) {
            int rootP = find(x1, y1);
            int rootQ = find(x2, y2);
            if (rootP == rootQ) return;//connected
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            }
            else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
        }
    }

    /*1101. The Earliest Moment When Everyone Become Friends
    * https://leetcode.com/problems/the-earliest-moment-when-everyone-become-friends/
    */
    public int earliestAcq(int[][] logs, int N) {
        int[] parent = new int[N];
        for(int i = 0; i < N; i++){
            parent[i] = i;
        }
        Arrays.sort(logs, (a, b) -> a[0] - b[0]);
        for(int i = 0; i < logs.length; i++){
            int a = logs[i][1];
            int b = logs[i][2];

            int aRoot = find(parent, a);
            int bRoot = find(parent, b);
            if(aRoot != bRoot){
                parent[aRoot] = bRoot;
                N--;
            }
            if(N == 1){
                return logs[i][0];
            }
        }
        return -1;
    }

    public int find(int[] parent, int p) {
        while(p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    /*  399. Evaluate Division
        https://leetcode.com/problems/evaluate-division/

        Given a / b = 2.0, b / c = 3.0.
        queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
        return [6.0, 0.5, -1.0, 1.0, -1.0 ]

        equations = [ ["a", "b"], ["b", "c"] ],
        values = [2.0, 3.0]
        queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
    */
    public double[] calcEquation(List<List<String>> e, double[] v, List<List<String>> q) {
        double[] result = new double[q.size()];
        HashMap<String, Double> dist = new HashMap<>();
        HashMap<String, String> root = new HashMap<>();

        for(int i = 0; i < e.size(); i++){
            List<String> curr = e.get(i);
            String a = curr.get(0);
            String b = curr.get(1);

            String parent_a = findParent(a, dist, root);
            String parent_b = findParent(b, dist, root);

            root.put(parent_a, parent_b);
            dist.put(parent_a, dist.get(b) * v[i] / dist.get(a));
        }

        for(int i =0; i < q.size(); i++){
            List<String> curr = q.get(i);

            String a = curr.get(0);
            String b = curr.get(1);

            if(!root.containsKey(a) || !root.containsKey(b)){
                result[i] = -1.0;
                continue;
            }

            String parent_a = findParent(a, dist, root);
            String parent_b = findParent(b, dist, root);

            if(!parent_a.equals(parent_b)){ //Not in same set
                result[i] = -1.0;
                continue;
            }

            result[i] = (double) dist.get(a) / dist.get(b);
        }

        return result;
    }

    public String findParent(String s, HashMap<String, Double> dist, HashMap<String, String> root){
        if(!root.containsKey(s)){
            dist.put(s, 1.0);
            root.put(s, s);
            return s;
        }
        if(root.get(s).equals(s)) return s;
        String last_parent = root.get(s);
        String parent = findParent(last_parent, dist, root);
        root.put(s, parent);
        dist.put(s, dist.get(s) * dist.get(last_parent));
        return parent;
    }
}
