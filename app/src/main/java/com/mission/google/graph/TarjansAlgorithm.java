package com.mission.google.graph;

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
        int n = g1.length;
        TarjansAlgorithm solver = new TarjansAlgorithm();
        LinkedList<Integer>[] graph = solver.buildGraph(g1);
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
        int[] low = new int[n];
        int[] disc = new int[n];
        List<Integer>[] graph = new ArrayList[n];
        List<List<Integer>> res = new ArrayList<>();
        Arrays.fill(disc, -1);

        for(int i = 0; i < n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < connections.size(); i++){
            int from = connections.get(i).get(0);
            int to = connections.get(i).get(1);
            graph[from].add(to);
            graph[to].add(from);
        }
        for(int i = 0; i < n; i++){
            if(disc[i] == -1){
                dfs(i, disc, graph, low, res, i);
            }
        }
        return res;
    }

    int time = 0;
    public void dfs(int u, int[] disc, List<Integer>[] graph, int[] low, List<List<Integer>> res, int pre){
        low[u] = disc[u] = ++time;
        for(int i = 0; i < graph[u].size(); i++ ){
            int v = graph[u].get(i);
            if(v == pre){
                continue;//We reach parent vertex
            }
            if(disc[v] == -1){//u is not discovered yet
                dfs(v, disc, graph, low, res, u);
                low[u] = Math.min(low[u], low[v]);

                if(low[v] > disc[u]){
                    // u ----- v is critical. It belongs to different connected component groups
                    // There is no path for v to reach back to u or previous vertices of u
                    res.add(Arrays.asList(u,v));
                }
            }else {   // If v discovered and is not parent of u, update low[u], cannot use low[v] because u is not subtree of v
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
}
