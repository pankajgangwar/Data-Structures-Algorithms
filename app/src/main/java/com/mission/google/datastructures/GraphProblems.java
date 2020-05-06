package com.mission.google.datastructures;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphProblems {
	
	public static void main(String[] args) {
		Graph graph = new Graph(4);
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(1, 2);
		graph.addEdge(2, 0);
		graph.addEdge(2, 3);
		graph.addEdge(3, 3);
		//graph.BFS(2);
		graph.DFS(2);
	}
	
	void printPairs() {
		int n = 940;
		int A = 939;
		int B = 1;
	}
	
	static class Graph {
		int V;
		LinkedList<Integer> adjList[];
		public Graph(int v) {
			this.V = v;
			adjList = new LinkedList[v];
			
			for(int i = 0 ; i < v ; i++ ) {
				adjList[i] = new LinkedList<>();
			}
		}
		
		public void addEdge(int src, int dst) {
			adjList[src].add(dst);
			//adjList[dst].add(src);
		}
		
		public void BFS(int s) {
			boolean visited[] = new boolean[V];
			LinkedList<Integer> queue = new LinkedList<>();
			visited[s] = true;
			queue.add(s);
			while(!queue.isEmpty()) {
				s = queue.poll();
				System.out.println( s + " ");
				Iterator<Integer> i = adjList[s].iterator();
				while(i.hasNext()) {
					int n = i.next();
					if(!visited[n]) { 
						visited[n] = true;
						queue.add(n);
					}
				}
			}
		}
		
		public void DFS(int v) {
	      // add code here.
			boolean visited[] = new boolean[V];
	        DFSUtil(v, adjList, visited);
	    }

		private void DFSUtil(int v, LinkedList<Integer> adj[] , boolean[] visited) {
			// TODO Auto-generated method stub
			visited[v] = true;
			System.out.println(v);
			Iterator<Integer> i = adj[v].listIterator();
			while(i.hasNext()) {
				int n = i.next();
				if(!visited[n]) {
					DFSUtil(n, adj, visited);
				}
			}
		}
	}
	
	
}
