package com.pkumar7.graph.dijkstra;

import java.util.Comparator;
import java.util.PriorityQueue;

public class A {

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
