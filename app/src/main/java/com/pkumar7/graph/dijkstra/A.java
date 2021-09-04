package com.pkumar7.graph.dijkstra;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class A {


    /* LC : 743
    https://leetcode.com/problems/network-delay-time/
    */
    public int networkDelayTime(int[][] times, int N, int K) {
        int maxTime = findMaxTimeDijikstra(times, N, K);
        System.out.println("maxTime = " + maxTime);
        return maxTime ;
    }

    public int findMaxTimeDijikstra(int[][] times, int N, int K){
        LinkedList<int[]>[] graph = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < times.length; i++) {
            int src = times[i][0] - 1;
            int dst = times[i][1] - 1;
            int time = times[i][2];
            graph[src].add(new int[]{dst, time});
        }
        boolean[] visited = new boolean[N];
        Arrays.fill(visited, false);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
        pq.offer(new int[]{K - 1, 0});
        int totalTime = 0;
        while (!pq.isEmpty()){
            int[] cur = pq.poll();
            int currSrc = cur[0];
            int currtime = cur[1];
            if(visited[currSrc]) continue;
            visited[currSrc] = true;
            totalTime = Math.max(totalTime, currtime);
            N--;
            for (int i = 0; i < graph[currSrc].size(); i++) {
                int[] next = graph[currSrc].get(i);
                int nextSrc = next[0];
                int time = next[1];
                time = currtime + time;
                pq.offer(new int[]{nextSrc, time});
            }
        }
        for (int i = 0; i < visited.length; i++) {
            if(!visited[i]){
                totalTime = 0;
                break;
            }
        }
        return N == 0 ? totalTime : -1;
    }

    /*
    778. Swim in Rising Water
	https://leetcode.com/problems/swim-in-rising-water/
    */
    public int swimInWaterDijkstra(int[][] grid) {
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(Comparator.comparingInt(a -> a[0]));
        int ans = 0;
        int m  = grid.length;
        int n = grid[0].length;
        queue.offer(new int[] {grid[0][0], 0, 0}); // [ weight, x, y]
        int[] dx = new int[] {0, 0, 1, -1};
        int[] dy = new int[] {1, -1, 0, 0};
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            ans = Math.max(ans, curr[0]);
            if (curr[1] == m - 1 && curr[2] == n - 1) {
                return ans;
            }
            for (int i = 0; i < dx.length; i++) {
                int nx = curr[1] + dx[i];
                int ny = curr[2] + dy[i];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[] {grid[nx][ny], nx, ny});
                }
            }
        }
        return -1;
    }
}
