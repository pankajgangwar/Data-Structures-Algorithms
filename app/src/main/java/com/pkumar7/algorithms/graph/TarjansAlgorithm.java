package com.pkumar7.algorithms.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pankaj Kumar on 14/September/2020
 */
class TarjansAlgorithm {

    /*
    Strongly Connected components can be thought of as self-contained cycles
    within a directed graph where every vertex in a given cycle can reach every other
    vertex in the same cycle
    Low-Link values of a node is smallest[lowest] node id reachable from that node when
    doing a DFS(including itself)
    */

    public static void main(String[] args) {
        int[][] g1 = new int[][]{
                {1}, {2}, {0}, {4,7},
                {5}, {6,0}, {0,2,4}, {3,5}
        };

        int[][] g2 = new int[][]{
                {1,2}, {0,2}, {0,1}, {1}
        };

        int n = g2.length;
        TarjansAlgorithm solver = new TarjansAlgorithm();
        LinkedList<Integer>[] graph = solver.buildGraph(g2);
        int[] low = solver.solve(graph);
        Map<Integer, List<Integer>> multiMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if(!multiMap.containsKey(low[i])){
                multiMap.put(low[i], new ArrayList<>());
            }
            multiMap.get(low[i]).add(i);
        }
        System.out.printf("Number of Strongly Connected Components: %d\n", solver.sscCount);
        for(List<Integer> scc : multiMap.values()){
            System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
        }
    }

    private int[] ids, low;
    private Deque<Integer> stack;
    private boolean[] onStack;
    private int sscCount, id;
    public int[] solve(LinkedList<Integer>[] graph){
        int n = graph.length;
        ids = new int[n];
        low = new int[n];
        stack = new ArrayDeque<>();
        onStack = new boolean[n];
        Arrays.fill(ids, -1);
        for (int i = 0; i < n; i++) {
            if(ids[i] == -1) dfs(i, graph);
        }
        return low;
    }

    public void dfs(int nodeAt, LinkedList<Integer>[] graph ){
        stack.push(nodeAt);
        onStack[nodeAt] = true;
        ids[nodeAt] = low[nodeAt] = id++;
        for (int nodeTo : graph[nodeAt]) {
            if(ids[nodeTo] == -1){
                dfs(nodeTo, graph);
                low[nodeAt] = Math.min(low[nodeTo], low[nodeAt]);
            }else if(onStack[nodeTo]){
                low[nodeAt] = Math.min(ids[nodeTo], low[nodeAt]);
            }
        }
        if(ids[nodeAt] == low[nodeAt]){ //id and low-link value is same, this node starts the scc
            while(!stack.isEmpty()){
                int node = stack.pop();
                // Assign low-link values for the nodes on the stack i.e for this scc
                // with current low-link value.
                low[node] = low[nodeAt];
                onStack[node] = false;
                if(node == nodeAt) break;
            }
            sscCount++;
        }
    }

    public LinkedList<Integer>[] buildGraph(int[][] g){
        int n = g.length;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            for (int j : g[i]) {
                graph[i].add(j);
            }
        }
        return graph;
    }

    /*  Problem from LeetCode
        https://leetcode.com/discuss/interview-question/124827/Find-minimum-number-of-people-to-reach-to-spread-a-message-across-all-people-in-twitter/
        https://leetcode.com/problems/critical-connections-in-a-network/
        Tarjan's Algorithm(Single DFS) using existing Kosaraju's Algorithm
        Strongly Connected Components
        Revisit: To again understand the algorithm
    */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<Integer>[] graph = new ArrayList[n];
        List<List<Integer>> res = new ArrayList<>();

        for(int i = 0; i < n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < connections.size(); i++){
            int from = connections.get(i).get(0);
            int to = connections.get(i).get(1);
            graph[from].add(to);
            graph[to].add(from);
        }
        ids = new int[n];
        low = new int[n];
        stack = new ArrayDeque<>();
        onStack = new boolean[n];
        Arrays.fill(ids, -1);
        for (int i = 0; i < n; i++) {
            if(ids[i] == -1){
                dfs(i, ids, graph, low, res, i);
            }
        }
        return res;
    }

    public void dfs(int nodeAt, int[] ids, List<Integer>[] graph, int[] low, List<List<Integer>> res, int pre){
        stack.push(nodeAt);
        onStack[nodeAt] = true;
        ids[nodeAt] = low[nodeAt] = id++;
        for (int nodeTo : graph[nodeAt]) {
            if(nodeTo == pre)continue;
            if(ids[nodeTo] == -1){
                dfs(nodeTo, ids, graph, low, res, nodeAt);
                low[nodeAt] = Math.min(low[nodeTo], low[nodeAt]);
                if(low[nodeTo] > ids[nodeAt]){
                    res.add(Arrays.asList(nodeAt, nodeTo));
                }
            }else if(onStack[nodeTo]){
                low[nodeAt] = Math.min(ids[nodeTo], low[nodeAt]);
            }
        }
        if(ids[nodeAt] == low[nodeAt]){ //id and low-link value is same, this node starts the scc
            while(!stack.isEmpty()){
                int node = stack.pop();
                // Assign low-link values for the nodes on the stack i.e for this scc
                // with current low-link value.
                low[node] = low[nodeAt];
                onStack[node] = false;
                if(node == nodeAt) break;
            }
            sscCount++;
        }
    }
}
