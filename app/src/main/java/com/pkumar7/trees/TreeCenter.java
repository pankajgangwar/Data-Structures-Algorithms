package com.pkumar7.trees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 13/September/2020
 */
class TreeCenter {
    public static void main(String[] args) {
        int[][] g1 = new int[][]{
                {1}, {0,2}, {1,9,3,6}, {2,4,5},
                {3}, {3}, {2,7,8}, {6}, {6}, {2}
        };
        int[][] g2 = new int[][]{
                {1}, {3,4,0}, {3}, {2,6,7,1},
                {1,5,8}, {4}, {3,9}, {3}, {4}, {6}
        };
        int n = g1.length;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList();
        }
        for (int i = 0; i < n; i++) {
            for (int j : g1[i]) {
                graph[i].add(j);
            }
        }
        TreeCenter curr = new TreeCenter();
        List<Integer> treeCenters = curr.findCenter(graph);
        System.out.println("center = " + treeCenters.toString());
    }

    /*
    * Finding the center node (Root node of Tree)from an undirected graph
    * 1) Find longest path among the undirected graph between 2 leaves,
    *    middle node of this path is center of graph.
    * 2) Trim the outer leaf nodes and repeat the process
    *    until left with last layer, Similar to peeling an Onion outer layer
    *    until last layer is left.
    */
    public List<Integer> findCenter(LinkedList<Integer>[] graph){
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
        List<Integer> centers = new ArrayList<>();
        while (!q.isEmpty()){
            int size = q.size();
            if(size <= 2){
                centers.clear();
                for(int x : q){
                    centers.add(x);
                }
            }
            while(size-- > 0 && !q.isEmpty()){
                int a = q.poll();
                degree[a] = 0;
                for(int adj : graph[a]){
                    if(--degree[adj] == 1){
                        if(visited.contains(adj)) continue;
                        visited.add(adj);
                        q.offer(adj);
                    }
                }
            }
        }
        return centers;
    }
}
