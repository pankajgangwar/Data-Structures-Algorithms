package com.pkumar7.graph;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class A {

    /*
     * 1631. Path With Minimum Effort
     * https://leetcode.com/problems/path-with-minimum-effort/
     * */
    public int minimumEffortPath(int[][] matrix) {
        return minimumEffortPathDijikstra(matrix);
    }

    public int minimumEffortPathDijikstra(int[][] matrix) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int m = matrix.length;
        int n = matrix[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[][] distance = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(distance[i], Integer.MAX_VALUE);
        }
        pq.offer(new int[]{0, 0, 0});
        while (!pq.isEmpty()){
            int size = pq.size();
            while (size-- > 0){
                int[] curr = pq.poll();
                int currD = curr[0];
                int curr_x = curr[1];
                int curr_y = curr[2];
                if(curr_x == m - 1 && curr_y == n - 1) {
                    return currD;
                }
                for (int[] d : dirs) {
                    int next_x = d[0] + curr_x;
                    int next_y = d[1] + curr_y;
                    if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n){
                        int newDistance = Math.max(currD, Math.abs(matrix[curr_x][curr_y] - matrix[next_x][next_y]));
                        if(distance[next_x][next_y] > newDistance){
                            distance[next_x][next_y] = newDistance;
                            pq.offer(new int[]{newDistance, next_x, next_y});
                        }
                    }
                }
            }
        }
        return 0;
    }

    /*
     * https://leetcode.com/problems/maximal-network-rank/
     * 1615. Maximal Network Rank
     * */
    public int maximalNetworkRank(int n, int[][] roads) {
        int[] degree = new int[n];
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }

        for (int i = 0; i < roads.length; i++) {
            int src = roads[i][0];
            int dst = roads[i][1];
            graph[src].add(dst);
            graph[dst].add(src);
            degree[src]++;
            degree[dst]++;
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i == j) continue;
                if(graph[i].contains(j)){
                    res = Math.max(res, degree[i] + degree[j] - 1);
                }else{
                    res = Math.max(res, degree[i] + degree[j]);
                }
            }
        }
        return res;
    }

    /* 841. Keys and Rooms
    *  https://leetcode.com/problems/keys-and-rooms/
    * */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(i).size(); j++) {
                graph[i].add(rooms.get(i).get(j));
            }
        }
        HashSet<Integer> visited = new HashSet<>();
        helper(graph, visited, 0);
        return visited.size() == n;
    }

    public void helper(LinkedList<Integer>[] graph, HashSet<Integer> visited, int src){
        if(visited.contains(src)) return;
        visited.add(src);
        for (int i = 0; i < graph[src].size(); i++) {
            int adj = graph[src].get(i);
            helper(graph, visited, adj);
        }
    }

    /* 1135. Connecting Cities With Minimum Cost
     * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
     * Kruskals Algorithm
     * Minimum Spanning tree
     * */
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

    public int find(int[] parent, int p) {
        while(p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    /*
     * Prims Algorithm
     * Minimum Spanning tree
     * */
    public int minCostConnectPointsPrims(int[][] points) {
        int n = points.length, i = 0, connected = 0;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        int res = 0;
        while (++connected < n){
            visited[i] = true;
            for (int j = 0; j < n; j++) {
                int manhattan_dist = Math.abs(points[i][0] - points[j][0]) +
                        Math.abs(points[i][1] - points[j][1]);
                if(!visited[j]){
                    pq.offer(new int[]{manhattan_dist, j});
                }
            }
            while (!pq.isEmpty() && visited[pq.peek()[1]]){
                pq.poll();
            }
            int[] curr = pq.poll();
            res += curr[0];
            i = curr[1];
        }
        return res;
    }

    /* Prims Algorithm
     * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
     * https://leetcode.com/problems/connecting-cities-with-minimum-cost/discuss/859931/JAVA-Prim's-Algorithm-with-PriorityQueue
     * */
    public int minimumCostPrims(int n, int[][] connections) {
        int i = 0, connected = 0;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        HashMap<Integer, LinkedList<int[]>> map = new HashMap<>();
        for (int j = 0; j < n; j++) {
            map.put(j, new LinkedList<>());
        }
        for (int j = 0; j < connections.length; j++) {
            int src = connections[j][0] - 1;
            int dst = connections[j][1] - 1;
            int cost = connections[j][2];
            map.get(src).add(new int[]{cost, dst});
            map.get(dst).add(new int[]{cost, src});
        }
        int res = 0;
        while (++connected < n){
            visited[i] = true;
            LinkedList<int[]> adj = map.get(i);
            for (int j = 0; j < adj.size(); j++) {
                int cost = adj.get(j)[0];
                int next = adj.get(j)[1];
                if(!visited[next]){
                    pq.offer(new int[]{cost, next});
                }
            }
            while (!pq.isEmpty() && visited[pq.peek()[1]]){
                pq.poll();
            }
            if(pq.isEmpty()) return -1;
            int[] curr = pq.poll();
            res += curr[0];
            i = curr[1];
        }
        return res;
    }

    /* https://leetcode.com/problems/path-with-maximum-minimum-value/
     *  Maximum points collections
     *  Dijkstra's algo
     * */
    public int maximumMinimumPath(int[][] matrix) {
        return maximumMinimumPathDijkstra2(matrix);
    }

    public int maximumMinimumPathDijkstra2(int[][] matrix) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        PriorityQueue<int[]> q = new PriorityQueue<>( (a, b) -> b[2] - a[2] ) ;
        q.offer(new int[]{0,0, matrix[0][0]});

        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        visited[0][0] = true;
        while (!q.isEmpty()){
            int[] curr = q.poll();

            if(curr[0] == rows-1 && curr[1] == cols-1)
                return curr[2];

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || visited[next_x][next_y])
                    continue;

                visited[next_x][next_y] = true;
                q.offer(new int[]{next_x, next_y, Math.min(curr[2], matrix[next_x][next_y]) } );
            }
        }
        return -1;
    }

    public int maximumMinimumPathDijkstra1(int[][] matrix) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        PriorityQueue<int[]> q = new PriorityQueue<>( (a,b) -> b[2] - a[2] );
        q.offer(new int[]{0,0, matrix[0][0]});

        int rows = matrix.length;
        int cols = matrix[0].length;

        ArrayList<Integer> result = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];

        visited[0][0] = true;
        int maxScore = matrix[0][0];
        while (!q.isEmpty()){
            int[] curr = q.poll();

            result.add(matrix[curr[0]][curr[1]]);

            maxScore = Math.min(maxScore, curr[2]);

            if(curr[0] == rows-1 && curr[1] == cols-1) {
                System.out.println("visited = " + result.toString());
                return maxScore;
            }

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || visited[next_x][next_y])
                    continue;

                visited[next_x][next_y] = true;
                q.offer(new int[]{next_x, next_y, matrix[next_x][next_y]});
            }
        }

        return -1;
    }
}
