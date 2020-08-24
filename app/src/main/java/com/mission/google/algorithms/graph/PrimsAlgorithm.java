package com.mission.google.algorithms.graph;

import java.util.Arrays;

public class PrimsAlgorithm {
	//https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/
	public static void main(String[] args) {
		//Represented with adjacency matrix
		
		int graph[][] = new int[][] {{0, 2, 0, 6, 0},
								   {2, 0, 3, 8, 5},
								   {0, 3, 0, 0, 7},
								   {6, 8, 0, 0, 9},
								   {0, 5, 7, 9, 0}};
								   
        MST mst = new MST();
        mst.primsMST(graph);
        
	}
	
	static class MST {
		
		//No. of vertices in the graph
		public static int V = 5;
		
		public void primsMST(int [][] graph) {
			int[] parent = new int[V];
			
			int[] key = new int[V];
			
			Boolean mstSet[] = new Boolean[V];
			
			Arrays.fill(mstSet, false);
			Arrays.fill(key, Integer.MAX_VALUE);
			
			key[0] = 0;
			parent[0] = -1;
			
			for(int i = 0; i < V -1; i++) {
				
				int minKey = getMinKey(key, mstSet);
				
				mstSet[i] = true;//Mark this as visited
				
				for(int j = 0; j < V; j++ ) {
					if(graph[minKey][j]!= 0 && !mstSet[j] && graph[minKey][j] < key[j]) {
						parent[j] = minKey;
						key[j] = graph[minKey][j]; 
					}
				}
			}
			
			printMST(graph,parent,V);
		}

		private void printMST(int[][] graph, int[] parent, int V) {
			// TODO Auto-generated method stub
			for(int i = 1; i < V; i++) {
				System.out.println(parent[i]+ " - " + i + "\t" + graph[i][parent[i]]);
			}
		}

		private int getMinKey(int[] key, Boolean[] mstSet) {
			// TODO Auto-generated method stub
			int min = Integer.MAX_VALUE;
			int min_index = -1;
			for(int i = 0; i < key.length; i++) {
				if(key[i] < min && !mstSet[i]) {
					min = key[i];
					min_index = i;
				}
			}
			return min_index;
		}
	}
	
	
}
