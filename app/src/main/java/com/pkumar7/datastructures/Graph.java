package com.pkumar7.datastructures;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	
	int V, E;
	Edge edge[];

	class Edge {
		int src, dst;
	}
	
	public Graph(int v, int e) {
		V = v;
		E = e;
		int i = 0;
		edge = new Edge[E];
		
		for(; i < e; ++i) {
			edge[i] = new Edge();
		}
	}
	
	public static void main(String[] args) {
		     
		/*     
	     *  0 	
		 * | \
		 * |  \
		 * |   \
		 * | 	\ 
		 * |	 \
		 * 1------2
		 */
		int V = 3, E = 3;
		Graph graph = new Graph(V, E);
		graph.edge[0].src = 0;
		graph.edge[0].dst = 1;
		
		graph.edge[1].src = 0;
		graph.edge[1].dst = 2;
		
		graph.edge[2].src = 1;
		graph.edge[2].src = 2;
		
		if(graph.isCycleExists(graph) == 1) {
			System.out.println("Graph contains cycle");
		}else {
			System.out.println("Graph doesn't contains cycle");
		}
	}

	//https://www.geeksforgeeks.org/union-find/
	private int isCycle(Graph graph) {
		// TODO Auto-generated method stub
		int[] parent = new int[graph.V];
		for(int i = 0; i < graph.V; ++i)
			parent[i] = -1;
		
		for(int i = 0; i < graph.E; ++i) {
			int x = graph.find(parent, graph.edge[i].src);
			int y = graph.find(parent, graph.edge[i].dst);
			if(x == y)
			  return 1;
				
			graph.union(parent, x, y);
		}
		return 0;
	}

	private void union(int[] parent, int x, int y) {
		// TODO Auto-generated method stub
		int xSet = find(parent, x);
		int ySet = find(parent, y);
		parent[xSet] = ySet;
	}

	private int find(int[] parent, int src) {
		// TODO Auto-generated method stub
		if(parent[src] == -1)
			return src;
		
		return find(parent, parent[src]);
	}
	
	//https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
	class subset {
		int parent; 
		int rank;
	}
	
	private int isCycleExists(Graph graph) {
		subset[] subsets = new subset[V];
		for(int v = 0; v < V; v++) {
			subsets[v] = new subset();
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}
		for(int e = 0 ; e < E; e++) {
			int x = findsubset(subsets, graph.edge[e].src);
			int y = findsubset(subsets, graph.edge[e].dst);
			if(x == y) {
				return 1;
			}
			UnionOfSets(subsets,x,y);
			
		}
		return 0;
	}

	private void UnionOfSets(subset[] subsets, int x, int y) {
		// TODO Auto-generated method stub
		int xRoot = findsubset(subsets, x);
		int yRoot = findsubset(subsets, y);
		if(subsets[xRoot].rank < subsets[yRoot].rank) {
			subsets[xRoot].parent = yRoot;
		}else if(subsets[xRoot].rank > subsets[yRoot].rank  ) {
			subsets[yRoot].parent = xRoot; 
		}else {
			subsets[xRoot].parent = yRoot;
			subsets[yRoot].rank++;
		}
	}

	//Uses path compression technique
	private int findsubset(subset[] subsets, int src) {
		// TODO Auto-generated method stub
		if(subsets[src].parent != src)
			subsets[src].parent = findsubset(subsets, subsets[src].parent);
		
		return subsets[src].parent;
	}
	
	//Kruskal's Algorithm for minimum spanning tree (Minimum cost spanning)
	
	
	
	
	//"loveleetcode"
	// [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
	// You need to find shortest distance for EACH Character in the String 'S' to Character 'C'
	 public int[] shortestToChar(String S, char C) {
		 return new int[]{1};
	 }
	
	
}
