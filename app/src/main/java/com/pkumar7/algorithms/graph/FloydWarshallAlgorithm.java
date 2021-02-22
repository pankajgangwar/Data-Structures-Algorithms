package com.pkumar7.algorithms.graph;

public class FloydWarshallAlgorithm {
    //https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
    //All pair shortest path problems
    public static final int V = 4;
    public static final int INF = 99999999;

    public static void main(String[] args) {

        int graph[][] = {{0, 5, INF, 10},
                {INF, 0, 3, INF},
                {INF, INF, 0, 1},
                {INF, INF, INF, 0}
        };
        FloydWarshallAlgorithm algorithm = new FloydWarshallAlgorithm();
        algorithm.allPairShortestPath(graph);
    }


    public void allPairShortestPath(int[][] graph) {
        int dist[][] = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        printsolution(dist);
    }

    private void printsolution(int[][] dist) {
        // TODO Auto-generated method stub
        System.out.println("Shortest distance between every pair of vertices");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF" + "\t");
                } else {
                    System.out.print(dist[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }


}
