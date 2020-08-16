package com.mission.google.graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class A {

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
