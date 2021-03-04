package com.pkumar7.graph.topologicalsort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class A {
    /* 1557. Minimum Number of Vertices to Reach All Nodes
    * https://leetcode.com/problems/minimum-number-of-vertices-to-reach-all-nodes/
    * */
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        int[] indegree = new int[n];
        for(List<Integer> edge : edges){
            int src = edge.get(0);
            int dst = edge.get(1);
            ++indegree[dst];
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(indegree[i] == 0){
                res.add(i);
            }
        }
        return res;
    }

    /*  1293. Shortest Path in a Grid with Obstacles Elimination
        https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
    */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        if(grid[0][0] == 1 || grid[m -1][n-1] == 1){
            return -1;
        }
        int[][] paths = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

        Queue<int[]> mQueue = new LinkedList<>();
        mQueue.offer(new int[]{0,0,0});

        boolean visited[][][] = new boolean[m][n][k+1];
        int min_distance = 0;

        visited[0][0][0] = true;

        while (!mQueue.isEmpty()){
            int size = mQueue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = mQueue.poll();
                if(curr[0] == m - 1 && curr[1] == n -1){
                    return min_distance;
                }
                for (int j = 0; j < paths.length; j++) {
                    int next_x = paths[j][0] + curr[0];
                    int next_y = paths[j][1] + curr[1];
                    if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n){

                        if(grid[next_x][next_y] == 0 && !visited[next_x][next_y][curr[2]]){
                            mQueue.offer(new int[]{next_x,next_y, curr[2]});
                            visited[next_x][next_y][curr[2]] = true;

                        }else if(grid[next_x][next_y] == 1 && curr[2] < k && !visited[next_x][next_y][curr[2]+1]){
                            mQueue.offer(new int[]{next_x,next_y, curr[2]+1});
                            visited[next_x][next_y][curr[2]+1] = true;
                        }
                    }
                }
            }
            min_distance++;
        }
        return -1;
    }

    /*  444. Sequence Reconstruction
        https://leetcode.com/problems/sequence-reconstruction/
    */
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();
        Map<Integer, HashSet<Integer>> dep = new HashMap<Integer, HashSet<Integer>>();
        Set<Integer> unique = new HashSet<>();

        if(seqs.isEmpty()) return false;
        if(org.length == 0) return false;

        for(List<Integer> list : seqs){
            if(list.isEmpty()) continue;
            int first_ele = list.get(0);
            unique.add(first_ele);

            if(!indegree.containsKey(first_ele)){
                indegree.put(first_ele, 0);
            }
            for(int i = 1; i < list.size(); i++){
                HashSet<Integer> sets = new HashSet<>();
                int curr = list.get(i);
                int prev = list.get(i - 1);

                unique.add(curr);
                unique.add(prev);

                if(dep.containsKey(prev)){
                    sets = dep.get(prev);
                }
                if(!sets.contains(curr)){
                    sets.add(curr);
                    dep.put(prev, sets);
                    indegree.put(curr, indegree.getOrDefault(curr, 0) + 1);
                }
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for(Integer curr : indegree.keySet()){
            if(indegree.get(curr) == 0){
                q.offer(curr);
            }
        }
        int idx = 0;
        while(!q.isEmpty()){
            int size = q.size();
            if(size > 1) return false;
            int curr = q.poll();
            if(idx == org.length || curr != org[idx++]) return false;
            HashSet<Integer> dependencies = dep.get(curr);
            if(dependencies == null) continue;
            for(Integer next : dependencies){
                int degree = indegree.get(next);
                degree--;
                indegree.put(next, degree);
                if(degree == 0){
                    q.offer(next);
                }
            }
        }
        return idx == org.length && idx == unique.size();
    }
}
