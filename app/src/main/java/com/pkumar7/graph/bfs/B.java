package com.pkumar7.graph.bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class B {

    /* 1263. Minimum Moves to Move a Box to Their Target Location
     * https://leetcode.com/problems/minimum-moves-to-move-a-box-to-their-target-location/
     * */
    public boolean isAvailable(int next_x, int next_y, char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if(next_x < 0 || next_x >= m
                || next_y < 0 || next_y >= n
                || grid[next_x][next_y] == '#'){
            return false;
        }
        return true;
    }

    public int minPushBox(char[][] grid) {
        Queue<int[][]> q = new LinkedList<>();
        int m = grid.length;
        int n = grid[0].length;
        int[] start_me = new int[2], start_box = new int[2], target = new int[2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    start_me = new int[]{i, j};
                }
                if(grid[i][j] == 'B'){
                    start_box = new int[]{i, j};
                }
                if(grid[i][j] == 'T'){
                    target = new int[]{i, j};
                }
            }
        }
        q.offer(new int[][]{start_box, start_me});
        boolean[][][] visited = new boolean[m][n][4];
        int step = 0;
        int[] d_row = new int[]{-1,1,0,0};
        int[] d_col = new int[]{0,0,-1,1};
        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int[][] curr = q.poll();
                int[] box = curr[0];
                int[] player = curr[1];
                if(Arrays.equals(box, target)) return step;
                for (int j = 0; j < d_row.length; j++) {
                    if(visited[box[0]][box[1]][j]) continue;

                    int player_next_x = box[0] + d_row[j];
                    int player_next_y = box[1] + d_col[j];

                    if(!isAvailable(player_next_x, player_next_y, grid)) continue;

                    int box_next_x = box[0] - d_row[j];
                    int box_next_y = box[1] - d_col[j];

                    if(!isAvailable(box_next_x, box_next_y, grid)) continue;

                    if(!playerCanReach(player_next_x, player_next_y, curr, grid)) continue;

                    visited[box[0]][box[1]][j] = true;
                    int[][] nextBoxAndPlayer = new int[][]{
                            new int[]{box_next_x, box_next_y},
                            box, // player's next pos will be box curr pos
                    };
                    q.offer(nextBoxAndPlayer);
                }
            }
            step++;
        }
        return -1;
    }

    private boolean playerCanReach(int player_target_x, int player_target_y, int[][] boxAndPlayer, char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();

        int[] d_row = new int[]{-1,1,0,0};
        int[] d_col = new int[]{0,0,-1,1};

        int[] box = boxAndPlayer[0];
        int[] player = boxAndPlayer[1];

        visited[box[0]][box[1]] = true;

        q.offer(player);

        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int[] c = q.poll();
                if(Arrays.equals(new int[]{player_target_x, player_target_y}, c )) return true;
                for (int j = 0; j < d_row.length; j++) {
                    int next_x = c[0] + d_row[j];
                    int next_y = c[1] + d_col[j];
                    if(!isAvailable(next_x, next_y, grid)) continue;
                    if(visited[next_x][next_y]) continue;

                    visited[next_x][next_y] = true;
                    q.offer(new int[]{next_x, next_y});
                }
            }
        }
        return false;
    }
}
