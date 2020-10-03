package com.pkumar7.bfs;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    /* 815. Bus Routes
     * https://leetcode.com/problems/bus-routes/
     */
    public int numBusesToDestination(int[][] routes, int s, int t) {
        if(s == t) return 0;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                int a = routes[i][j];
                map.putIfAbsent(a, new ArrayList<>());
                map.get(a).add(i);
            }
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        HashSet<Integer> visited = new HashSet<>();
        int res = 0;
        while (!q.isEmpty()){
            int size = q.size();
            res++;
            while (size-- > 0){
                int stop = q.poll();
                List<Integer> buses = map.get(stop);
                for(int b : buses){
                    if(visited.contains(b))continue;
                    visited.add(b);
                    for (int i = 0; i < routes[b].length; i++) {
                        if(routes[b][i] == t ) return res;
                        q.offer(routes[b][i]);
                    }
                }
            }
        }
        return -1;
    }

    /* 1559. Detect Cycles in 2D Grid
    * https://leetcode.com/problems/detect-cycles-in-2d-grid/
    */
    public boolean containsCycle(char[][] grid) {
        int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] < 'a') continue;
                int[] start = new int[]{i, j};
                //if(sets.contains(i+""+j)) continue;
                if(bfs(grid, dirs, start , grid[i][j])){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean bfs(char[][] grid, int[][] dirs, int[] start, char color){
        Queue<int[]> q = new LinkedList<>();
        q.offer(start);
        while (!q.isEmpty()){
            int size = q.size();
            Queue<int[]> tempQ = new LinkedList<>();
            while (size-- > 0){
                int[] curr = q.poll();
                if(grid[curr[0]][curr[1]] < 'a') return true;
                //if(sets.contains(curr[0]+""+curr[1])) return true;
                // sets.add(curr[0]+""+curr[1]);
                grid[curr[0]][curr[1]] -= 26;
                for (int[] dir : dirs) {
                    int next_x = dir[0] + curr[0];
                    int next_y = dir[1] + curr[1];
                    if(isValid(next_x, next_y, grid, color)){
                        int[] next = new int[]{next_x, next_y};
                        tempQ.offer(next);
                    }
                }
            }
            q = tempQ;
        }
        return false;
    }

    public boolean isValid(int x, int y, char[][] grid, char color){
        if(x >= 0 && x < grid.length && y >= 0 && y < grid[x].length && color == grid[x][y]){
            return true;
        }
        return false;
    }

    /* 199. Binary Tree Right Side View
    * https://leetcode.com/problems/binary-tree-right-side-view/
    */
    List<Integer> result = new ArrayList<>();
    public List<Integer> rightSideView(TreeNode root) {
        rightViewBFS(root);
        //rightViewRec(root, result, 0);
        return result;
    }

    public void rightViewBFS(TreeNode root){
        if(root == null){
            return;
        }
        Queue<TreeNode> mQueue = new LinkedList<>();
        mQueue.offer(root);
        while (!mQueue.isEmpty()){
            int size = mQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = mQueue.poll();
                if(i == size-1){
                    result.add(curr.val);
                }
                if(curr.left != null){
                    mQueue.offer(curr.left);
                }
                if(curr.right != null){
                    mQueue.offer(curr.right);
                }
            }
        }
    }

    public void rightViewRec(TreeNode root, List<Integer> res, int height){
        if(root == null){
            return;
        }
        if(res.size() == height){
            res.add(root.val);
        }
        rightViewRec(root.right, res, height+1);
        rightViewRec(root.left, res, height+1);
    }

    /***
     * https://leetcode.com/problems/shortest-path-in-binary-matrix/
     * https://leetcode.com/problems/shortest-path-in-binary-matrix/discuss/312706/JAVA-BFS
     * https://www.geeksforgeeks.org/shortest-path-in-a-binary-maze/
     * **/
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if(grid[0][0] == 1 || grid[m -1][n-1] == 1){
            return -1;
        }
        int[][] paths = new int[][]{{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};

        Queue<int[]> mQueue = new LinkedList<>();
        mQueue.offer(new int[]{0,0});
        boolean visited[][] = new boolean[m][n];
        int min_distance = 0;

        while (!mQueue.isEmpty()){
            int size = mQueue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = mQueue.poll();
                if(curr[0] == m - 1 && curr[1] == n -1){
                    return min_distance +  1;
                }
                for (int j = 0; j < paths.length; j++) {
                    int next_x = paths[j][0] + curr[0];
                    int next_y = paths[j][1] + curr[1];
                    if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]
                            && grid[next_x][next_y] == 0){
                        mQueue.offer(new int[]{next_x,next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
            min_distance++;
        }
        return -1;
    }

    /*1553. Minimum Number of Days to Eat N Oranges
    * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
    */
    public int minDaysBfs(int n) {
        Queue<Integer> dq = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(n, 0);
        dq.add(n);
        while (true){
            int curr = dq.poll();
            if(curr == 0) return map.get(curr);
            int days = map.get(curr);
            if(!map.containsKey(curr - 1)){
                map.put(curr -1 , days + 1);
                dq.add(curr - 1);
            }
            if(curr % 2 == 0 && !map.containsKey(curr / 2)){
                map.put(curr / 2, days + 1 );
                dq.add(curr / 2);
            }

            if(curr % 3 == 0 && !map.containsKey( curr / 3)){
                map.put(curr / 3, days + 1 );
                dq.add(curr / 3);
            }
        }
    }
}
