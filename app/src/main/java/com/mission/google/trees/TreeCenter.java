package com.mission.google.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 13/September/2020
 */
class TreeCenter {
    public static void main(String[] args) {
        int n = 10;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList();
        }
        int[][] g1 = new int[][]{
                {1}, {0,2}, {1,9,3,6}, {2,4,5},
                {3}, {3}, {2,7,8}, {6}, {6}, {2}
        };

        int[][] g2 = new int[][]{
                {1}, {0,2}, {1,9,3,6}, {2,4,5},
                {3}, {3}, {2,7,8}, {6}, {6}, {2}
        };
        for (int i = 0; i < n; i++) {
            for (int j : g1[i]) {
                graph[i].add(j);
            }
        }
        TreeCenter curr = new TreeCenter();
        int center = curr.findCenter(graph);
        System.out.println("center = " + center);
    }

    /*
    * Finding the center node (Root node of Tree)from an undirected graph
    * 1) Find longest path among the undirected graph between 2 leaves,
    *    middle node of this path is center of graph.
    * 2) Trim the outer leaf nodes and repeat the process
    *    until left with last layer, Similar to peeling an Onion outer layer
    *    until last layer is left.
    */
    public int findCenter(LinkedList<Integer>[] graph){
        int n = graph.length;
        int[] degree = new int[n];
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        for (int i = 0; i < graph.length; i++) {
            degree[i] = graph[i].size();
        }
        for (int i = 0; i < n; i++) {
            if(degree[i] == 1){
                q.offer(i);
                visited.add(i);
            }
        }
        while (!q.isEmpty()){
            int size = q.size();
            if(size <= 2){
                return q.peek();
            }
            while(size-- > 0){
                int a = q.poll();
                --degree[a];
                for(int adj : graph[a]){
                    if(--degree[adj] == 1){
                        if(visited.contains(adj)) continue;
                        visited.add(adj);
                        q.offer(adj);
                    }
                }
            }
        }
        return -1;
    }
}
