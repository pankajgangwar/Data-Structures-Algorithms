package com.pkumar7.algorithms.graph;

import java.util.Arrays;

public class DijkstraAlgorithm {
	//https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
	
	public static void main(String[] args) {
		
		int graph[][] = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0}, 
						            {4, 0, 8, 0, 0, 0, 0, 11, 0}, 
						            {0, 8, 0, 7, 0, 4, 0, 0, 2}, 
						            {0, 0, 7, 0, 9, 14, 0, 0, 0}, 
						            {0, 0, 0, 9, 0, 10, 0, 0, 0}, 
						            {0, 0, 4, 14, 10, 0, 2, 0, 0}, 
						            {0, 0, 0, 0, 0, 2, 0, 1, 6 }, 
						            {8, 11, 0, 0, 0, 0, 1, 0, 7}, 
						            {0, 0, 2, 0, 0, 0, 6, 7, 0} 
           							}; 
        ShortestPath path = new ShortestPath();
        path.dijkstra(graph, 0);
        
	}
	
	static class ShortestPath {
		public static final int V = 9;
		
		public int minDistance(Boolean[] visitedSet, int dist[]) {
			int min = Integer.MAX_VALUE;
			int min_index = -1;
			for(int i = 0; i < V; i++) {
				if(!visitedSet[i] && dist[i] < min ) {
					min = dist[i];
					min_index = i;
				}
			}
			return min_index;
		}
		
		public void dijkstra(int[][] graph, int src) {
			Boolean[] visitedVertices = new Boolean[V];
			int dist[] = new int[V];
			Arrays.fill(visitedVertices, false);
			Arrays.fill(dist, Integer.MAX_VALUE);
			dist[0] = 0;
			for(int count = 0; count < V-1; count++) {
				int u = minDistance(visitedVertices, dist);
				visitedVertices[u] = true;
				for(int v=0; v < V;v++) {
					
					if(!visitedVertices[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE
							&& dist[u]+graph[u][v] < dist[v]) {
						dist[v] = dist[u] + graph[u][v];
					}
				}
			}
			printSolution(dist, V);
		}
		
		public void printSolution(int[] dist, int V) {
			for(int i =0; i < V; i++) {
				System.out.println(i +" tt " + dist[i]);
			}
		}
	}

}
