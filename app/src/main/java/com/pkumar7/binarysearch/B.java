package com.pkumar7.binarysearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class B {

    /*
     * https://leetcode.com/problems/last-day-where-you-can-still-cross/
     * 1970. Last Day Where You Can Still Cross
     * */
    public int latestDayToCrossBinarySearch(int row, int col, int[][] cells) {
        int low = 1;
        int high = cells.length;

        int res = 1;
        while (low <= high){
            int mid = (low + high) / 2;
            if(canWalkFromTopToBottom(mid, row, col, cells)){
                res = mid;
                low = mid + 1;
            }else{
                high = mid -1;
            }
        }
        return res;
    }

    private boolean canWalkFromTopToBottom(int mid, int row, int col, int[][] cells) {
        int[][] grid = new int[row][col];

        for (int i = 0; i < mid; i++) {
            int[] c = cells[i];
            grid[c[0] - 1][c[1] - 1] = 1;
        }
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < col; i++) {
            if(grid[0][i] == 0){
                visited[0][i] = true;
                q.offer(new int[]{0, i});
            }
        }
        return canWalk(q, grid, visited);
    }

    public boolean canWalk(Queue<int[]> q, int[][] grid, boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;

        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int[] c = q.poll();
                if(c[0] == m - 1) return true;
                int[] dx = new int[] {0, 0, 1, -1};
                int[] dy = new int[] {1, -1, 0, 0};
                for (int i = 0; i < dx.length; i++) {
                    int next_x = dx[i] + c[0];
                    int next_y = dy[i] + c[1];
                    if(next_x >=0 && next_x < m && next_y >=0 && next_y < n
                            && grid[next_x][next_y] == 0 && !visited[next_x][next_y]){
                        q.offer(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * https://leetcode.com/problems/find-the-longest-valid-obstacle-course-at-each-position/
     * https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf
     * */
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        return lisPatienceSort(obstacles);
    }

    public int[] lisPatienceSort(int[] nums) {
        List<Integer> piles = new ArrayList<>(nums.length);
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int pile = binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
            res[i] = pile + 1;
        }
        return res;
    }

    public int binarySearch(List<Integer> piles, int num){
        int low = 0, high = piles.size() - 1;
        while(low <= high){
            int mid = (high + low) / 2;
            int mid_ele = piles.get(mid);
            if(mid_ele <= num){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return -(low + 1);
    }
}
