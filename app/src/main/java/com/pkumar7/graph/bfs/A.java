package com.pkumar7.graph.bfs;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    /*
     *  1926. Nearest Exit from Entrance in Maze
     * https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/
     * */
    public int nearestExit(char[][] maze, int[] entrance) {
        Queue<int[]> bfs = new LinkedList<>();
        bfs.offer(entrance);
        int m = maze.length;
        int n = maze[0].length;

        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        int steps = 0;
        while (!bfs.isEmpty()) {
            int size = bfs.size();
            while (size-- > 0) {
                int[] curr = bfs.poll();
                if (hasReachedBorder(curr, maze) && !Arrays.equals(entrance, curr)) {
                    return steps;
                }
                for (int i = 0; i < dirs.length; i++) {
                    int next_x = dirs[i][0] + curr[0];
                    int next_y = dirs[i][1] + curr[1];
                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && maze[next_x][next_y] == '.') {
                        maze[next_x][next_y] = '+';
                        bfs.offer(new int[]{next_x, next_y});
                    }
                }
            }
            steps += 1;
        }
        return steps;
    }

    public boolean hasReachedBorder(int[] curr, char[][] maze) {
        int m = maze.length;
        int n = maze[0].length;
        return curr[0] == 0 || curr[0] == m - 1 || curr[1] == 0 || curr[1] == n - 1;
    }

    /* 1871. Jump Game VII
     * https://leetcode.com/problems/jump-game-vii/
     * */
    public boolean canReach(String s, int minJump, int maxJump) {
        /*
        * i + minJump <= j <= min(i + maxJump, s.length - 1), and
          s[j] == '0'
        * */
        //return dfs(0, minJump, maxJump, s);
        return usingDeque(s, minJump, maxJump);
    }

    public boolean bfs(String s, int minJump, int maxJump){
        Queue<Integer> bfs = new LinkedList<>();
        bfs.offer(0);
        int max = 0;
        while(!bfs.isEmpty()){
            int i = bfs.poll();
            for (int j = Math.max(i + minJump, max + 1); j <= Math.min(i + maxJump, s.length() -1); j++) {
                if(s.charAt(j) == '0'){
                    if(j == s.length() - 1) return true;
                    bfs.offer(j);
                }
            }
            max = Math.max(max, i + maxJump);
        }
        return false;
    }

    public boolean usingDeque(String s, int minJump, int maxJump){
        Deque<Integer> deQueue = new ArrayDeque<>();
        deQueue.offer(0);
        int len = s.length();
        for (int i = 1; i < len; i++) {
            while (!deQueue.isEmpty() && deQueue.peekFirst() < i - maxJump){
                deQueue.pollFirst();
            }
            if(s.charAt(i) == '0' && !deQueue.isEmpty() && deQueue.peekFirst() <= i - minJump){
                deQueue.offer(i);
            }
        }
        return !deQueue.isEmpty() && deQueue.peekLast() == s.length() -1;
    }

    HashMap<Integer, Boolean> cache = new HashMap<>();
    public boolean dfs(int curr, int minJump, int maxJump, String s){
        int len = s.length();
        if(curr == len - 1) return true;
        if(curr < 0 || curr >= len) return false;
        if(cache.containsKey(curr)) return cache.get(curr);
        int minPos = curr + minJump;
        int maxPos = Math.min(curr + maxJump, len - 1);
        for (int j = minPos; j <= maxPos ; j++) {
            if(s.charAt(j) == '0'){
                if(dfs(j, minJump, maxJump, s)){
                    return true;
                }
            }
        }
        cache.put(curr, false);
        return false;
    }

    /* 711. Number of Distinct Islands II
    * https://leetcode.com/problems/number-of-distinct-islands-ii/
    * https://en.wikipedia.org/wiki/Dihedral_group
    * */
    public int numDistinctIslands2(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0 ) return 0;
        int m = grid.length;
        int n = grid[0].length;
        HashSet<String> islands = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == 1){
                    List<int[]> cells = new ArrayList<>();
                    dfs(grid, i, j, cells);
                    String key = findDihedral(cells);
                    islands.add(key);
                }
            }
        }
        return islands.size();
    }
    int[][] trans = new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}};
    private String findDihedral(List<int[]> cells) {
        List<String> forms = new ArrayList<>();
        for(int[] ts : trans){
            List<int[]> list1 = new ArrayList<>();
            List<int[]> list2 = new ArrayList<>();
            for(int[] c : cells){
                list1.add(new int[]{c[0]*ts[0], c[1]*ts[1]});
                list2.add(new int[]{c[1]*ts[1], c[0]*ts[0]});
            }
            forms.add(getKey(list1));
            forms.add(getKey(list2));
        }
        Collections.sort(forms);
        return forms.get(0);
    }

    private String getKey(List<int[]> cells) {
        Collections.sort(cells, (a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        StringBuilder out = new StringBuilder();
        int x = cells.get(0)[0], y = cells.get(0)[1];
        for(int[] c : cells){
            out.append((c[0]-x) + ":" + (c[1]-y) + ":");
        }
        return out.toString();
    }

    private void dfs(int[][] grid, int curr_x, int curr_y, List<int[]> cells) {
        int m = grid.length;
        int n = grid[0].length;
        cells.add(new int[]{curr_x, curr_y});
        int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
        grid[curr_x][curr_y] = 0;
        for (int i = 0; i < dirs.length; i++) {
             int next_x = dirs[i][0] + curr_x;
             int next_y = dirs[i][1] + curr_y;
             if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n &&
                                grid[next_x][next_y] == 1){
                 dfs(grid, next_x, next_y, cells);
             }
        }
    }

    /*
     * 675. Cut Off Trees for Golf Event
     * https://leetcode.com/problems/cut-off-trees-for-golf-event/
     * */
    public int cutOffTree(List<List<Integer>> forest) {
        PriorityQueue<int[]> queue = new PriorityQueue<>( (a, b) -> a[0] - b[0]); // [height, x, y]
        int[][] matrix = new int[forest.size()][forest.get(0).size()];
        for(int i = 0; i < forest.size(); i++){
            for (int j = 0; j < forest.get(i).size(); j++) {
                int h = forest.get(i).get(j);
                if(h == 0)continue;
                queue.offer(new int[]{h, i, j});
                matrix[i][j] = h;
            }
        }
        int res = 0;
        int[] src = new int[2];
        while (!queue.isEmpty()){
            int[] next = queue.poll();
            int[] dest = new int[]{next[1], next[2]};
            int steps = minSteps(matrix, src, dest);
            if(steps == -1){
                return -1;
            }
            res += steps;
            src = dest;
        }
        return res;
    }

    public int minSteps(int[][] matrix, int[] src, int[] dest){
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[src[0]][src[1]] = true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(src);
        int steps = 0;
        int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int[]curr = q.poll();
                if(Arrays.equals(curr, dest)) return steps;
                for (int i = 0; i < dirs.length; i++) {
                    int next_x = curr[0] + dirs[i][0];
                    int next_y = curr[1] + dirs[i][1];
                    if(next_x >=0 && next_x < m && next_y >= 0 && next_y < n
                            && matrix[next_x][next_y] != 0 && !visited[next_x][next_y]){
                        q.offer(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    /*
     * 1609. Even Odd Tree
     * https://leetcode.com/problems/even-odd-tree/
     * */
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()){
            int size = q.size();
            int prev = 0;
            if(level % 2 == 0 ){
                prev = Integer.MIN_VALUE;
            }else{
                prev = Integer.MAX_VALUE;
            }
            while (size-- > 0 ){
                TreeNode curr = q.poll();
                int val = curr.val;
                if(level % 2 == 0){
                    if(val % 2 == 0 || prev >= val){
                        return false;
                    }
                    prev = val;
                }else{
                    if(val % 2 != 0 || prev <= val){
                        return false;
                    }
                    prev = val;
                }
                if(curr.left != null){
                    q.offer(curr.left);
                }
                if(curr.right != null){
                    q.offer(curr.right);
                }
            }
            level++;
        }
        return true;
    }

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
        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] < 'a') continue;
                int[] start = new int[]{i, j};
                //if(sets.contains(i+""+j)) continue;
                /*
                if(visited[i][j]) continue;
                if(helper(grid, dirs, i, j,-1, -1, grid[i][j], visited)){
                    return true;
                }
                * */
                if (bfs(grid, dirs, start, grid[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] grid, int[][] dirs, int curr_x, int curr_y,
                       int prev_x, int prev_y, char color, boolean[][] visited) {

        visited[curr_x][curr_y] = true;
        for (int[] dir : dirs) {
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;
            if (isValid(next_x, next_y, grid, color)) {
                if (!(prev_x == next_x && prev_y == next_y)) {
                    if (visited[next_x][next_y]) {
                        return true;
                    } else {
                        if (dfs(grid, dirs, next_x, next_y, curr_x, curr_y, color, visited)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean bfs(char[][] grid, int[][] dirs, int[] start, char color) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(start);
        while (!q.isEmpty()) {
            int size = q.size();
            Queue<int[]> tempQ = new LinkedList<>();
            while (size-- > 0) {
                int[] curr = q.poll();
                if (grid[curr[0]][curr[1]] < 'a') return true;
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
