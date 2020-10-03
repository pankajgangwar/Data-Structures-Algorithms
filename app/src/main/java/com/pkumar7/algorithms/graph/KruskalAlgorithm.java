package com.pkumar7.algorithms.graph;

import java.util.Arrays;

public class KruskalAlgorithm {
	
	public static void main(String[] args) {
		/* Let us create following weighted graph 
		        10 
		   0--------1 
		   | \      | 
		  6|  5\    |15 
		   |     \  | 
		   2--------3 
		       4       
		*/
		int V = 4;
		int E = 5;
		Graph graph = new Graph(E, V);
		graph.edge[0].src = 0;
		graph.edge[0].dest = 2;
		graph.edge[0].weight = 6;
		
		graph.edge[1].src = 2;
		graph.edge[1].dest = 3;
		graph.edge[1].weight = 4;
		
		graph.edge[2].src = 3;
		graph.edge[2].dest = 1;
		graph.edge[2].weight = 15;
		
		graph.edge[3].src = 0;
		graph.edge[3].dest = 1;
		graph.edge[3].weight = 10;
		
		graph.edge[4].src = 0;
		graph.edge[4].dest = 3;
		graph.edge[4].weight = 5;
		
		graph.formMST();
		
	}
	
	static class Graph{
		int V, E;
		Edge[] edge;
		
		public Graph(int e, int v) {
			this.E = e;
			this.V = v;
			edge = new Edge[e];
			for(int i = 0; i < e; i++) {
				edge[i] = new Edge();
			}
		}
		
		class subset {
			int parent;
			int rank;
		}
		
		class Edge implements Comparable<Edge> {
			int src;
			int dest;
			int weight;
			
			@Override
			public int compareTo(Edge comparedEdge) {
				// TODO Auto-generated method stub
				return weight - comparedEdge.weight;
			}
		}
		
		public int find(subset[] subsets, int i) {
			if(subsets[i].parent != i) {
				subsets[i].parent = find(subsets, subsets[i].parent);
			}
			return subsets[i].parent;
		}
		
		public void Union(subset[] subsets, int x, int y) {
			int xroot = find(subsets, x);
			int yroot = find(subsets, y);
			
			if(subsets[xroot].rank < subsets[yroot].rank) {
				subsets[xroot].parent = yroot;
			}else if(subsets[xroot].rank >  subsets[yroot].rank) {
				subsets[yroot].parent = xroot;
			}else {
				subsets[xroot].parent = yroot;
				subsets[yroot].rank++;
			}
		}
		
		public void formMST() {
			subset[] subsetArr = new subset[V];
			for(int v = 0 ; v < V; v++) {
				subsetArr[v] = new subset();
			}
			
			for(int v = 0 ; v < V; v++) {
				subsetArr[v].parent = v;
				subsetArr[v].rank = 0;
			}
			
			Edge[] result = new Edge[V];
			
			Arrays.sort(edge);
			int e = 0, i = 0;
			
			while(e < V-1) {
				
				Edge next_edge = edge[i++];
				int x = find(subsetArr, next_edge.src);
				int y = find(subsetArr, next_edge.dest);
				
				if(x != y) {//Doesn't form a cycle
					result[e++] = next_edge;
					Union(subsetArr, x, y);
				}
			}
			
			System.out.println("Following are the edges in constructed MST");
			for(int k = 0; k < e; k++) {
				System.out.println(result[k].src + " ---- " + result[k].dest + " ==== " + result[k].weight);
			}
		}
		
	}
}
