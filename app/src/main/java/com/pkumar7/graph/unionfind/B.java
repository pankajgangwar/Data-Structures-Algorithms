package com.pkumar7.graph.unionfind;

import com.pkumar7.graph.unionfind.UnionFind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class B {
	public static void main(String[] args) {

	}

	/* 1722. Minimize Hamming Distance After Swap Operations
	 * https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
	 * */
	public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
		int n = source.length;
		List<Integer> t = new ArrayList<>();
		for (int i = 0; i < target.length; i++) {
			t.add(target[i]);
		}
		UnionFind unionfind = new UnionFind(n);
		for (int[] s : allowedSwaps) {
			unionfind.union(s[0], s[1]);
		}
		HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<Integer, HashMap<Integer, Integer>>();
		for (int i = 0; i < source.length; i++) {
			int a = source[i];
			int root = unionfind.find(i);
			map.putIfAbsent(root, new HashMap<Integer, Integer>());
			HashMap<Integer, Integer> store = map.get(root);
			store.put(a, store.getOrDefault(a, 0) + 1);
		}
		int res = 0;
		for (int i = 0; i < target.length; i++) {
			int a = target[i];
			int root = unionfind.find(i);
			HashMap<Integer, Integer> store = map.getOrDefault(root, new HashMap<Integer, Integer>());
			if (store.getOrDefault(a, 0) == 0) res++;
			else store.put(a, store.get(a) - 1);
		}
		return res;
	}

	/* 990. Satisfiability of Equality Equations
	https://leetcode.com/problems/satisfiability-of-equality-equations/
	*/
	public boolean equationsPossible(String[] equations) {
		Arrays.sort(equations, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (s1.contains("==") && s2.contains("!="))
					return -1;
				else if (s1.contains("!=") && s2.contains("=="))
					return 1;
				else return 0;
			}
		});
		int n = equations.length;

		HashSet<Character> variables = new HashSet<Character>();
		for (int i = 0; i < n; i++) {
			String a = equations[i];
			char first = a.charAt(0);
			char second = a.charAt(a.length() - 1);
			variables.add(first);
			variables.add(second);
		}
		List<Character> list = new ArrayList<Character>(variables);
		UnionFind unionFind = new UnionFind(list.size());
		for (int i = 0; i < n; i++) {
			String a = equations[i];
			char first = a.charAt(0);
			char second = a.charAt(a.length() - 1);
			int idx1 = list.indexOf(first);
			int idx2 = list.indexOf(second);
			if (a.contains("==")) {
				unionFind.union(idx1, idx2);
			} else {
				if (unionFind.find(idx1) == unionFind.find(idx2)) {
					return false;
				}
			}
		}
		return true;
	}

	/* 839. Similar String Groups
	 https://leetcode.com/problems/similar-string-groups/
	*/
	public int numSimilarGroups(String[] strs) {
		int n = strs.length;
		UnionFind unionFind = new UnionFind(n);
		for (int i = 0; i < strs.length - 1; i++) {
			for (int j = i + 1; j < strs.length; j++) {
				String a = strs[i];
				String b = strs[j];
				if (isSimilar(a, b)) {
					unionFind.union(i, j);
				}
			}
		}
		return unionFind.getDisjointSets();
	}

	public boolean isSimilar(String a, String b) {
		int n = Math.min(a.length(), b.length());
		int diff =  0;
		for (int i = 0; i < n; i++) {
			diff += (a.charAt(i) - b.charAt(i)) != 0 ? 1 : 0;
		}
		return diff == 2;
	}

	/* 778. Swim in Rising Water
	https://leetcode.com/problems/swim-in-rising-water/
	*/
	public int swimInWater(int[][] grid) {
		int N = grid.length;
		UnionFind uf = new UnionFind(N * N);
		int[] map = new int[N * N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int idx = i * N + j;
				map[grid[i][j]] = idx;
			}
		}
		int[] dx = new int[] {0, 0, 1, -1};
		int[] dy = new int[] {1, -1, 0, 0};
		for (int time = 0; time < N * N; time++) {
			int idx = map[time];
			int row = idx / N;
			int col = idx % N;
			for (int i = 0; i < dx.length; i++) {
				int nx = row + dx[i];
				int ny = col + dy[i];
				if (nx >= 0 && nx < N && ny >= 0 && ny < N && grid[nx][ny] <= time) {
					uf.union(idx, nx * N + ny);
				}
			}
			if (uf.isConnected(0, (N * N) - 1)) return time;
		}
		return -1;
	}

	/* 952. Largest Component Size by Common Factor
	https://leetcode.com/problems/largest-component-size-by-common-factor/
	*/
	public int largestComponentSize(int[] arr) {
		int n = arr.length;
		HashMap<Integer, Integer> map = new HashMap<>();
		List<int[]> edges = new ArrayList<>();
		MaxSizeComponent sizeComponent = new MaxSizeComponent(n);
		MaxSizeComponent.UnionFind uf = sizeComponent.unionFind;
		for (int i = 0; i < n; i++) {
			int a = arr[i];
			for (int j = 2; j <= Math.sqrt(a); j++) {
				if (a % j == 0) {
					if (!map.containsKey(j)) map.put(j, i);
					else uf.union(map.get(j), i);

					if (!map.containsKey(a / j)) map.put(a / j, i);
					else uf.union(map.get(a / j), i);
				}
			}
			if (!map.containsKey(a)) map.put(a, i);
			else uf.union(map.get(a), i);
		}
		return uf.getMaxSize();
	}

	/* 1135. Connecting Cities With Minimum Cost
	 * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
	 * Kruskals Algorithm
	 * Minimum Spanning tree
	 * */
	public int minimumCost(int n, int[][] connections) {
		Arrays.sort(connections, (a, b) -> a[2] - b[2]);
		UnionFind unionfind = new UnionFind(n);
		int cost = 0;
		for (int i = 0; i < connections.length; i++) {
			int x = connections[i][0] - 1;
			int y = connections[i][1] - 1;
			if (unionfind.find(x) == unionfind.find(y)) continue;
			unionfind.union(x, y);
			cost += connections[i][2];
		}
		return cost;
	}

	class MaxSizeComponent {
		UnionFind unionFind;
		public MaxSizeComponent(int n) {
			unionFind = new UnionFind(n);
		}
		class UnionFind {
			private int count = 0;
			private int[] parent, rank;
			int max = 0;
			public UnionFind(int n) {
				count = n;
				parent = new int[n];
				rank = new int[n];
				for (int i = 0; i < n; i++) {
					parent[i] = i;
					rank[i] = 1;
				}
			}
			public int find(int p) {
				while (p != parent[p]) {
					parent[p] = parent[parent[p]];    // path compression by halving
					p = parent[p];
				}
				return p;
			}
			public void union(int p, int q) {
				int rootP = find(p);
				int rootQ = find(q);
				if (rootP == rootQ) return;
				parent[rootP] = rootQ;
				rank[rootQ] += rank[rootP];
				max = Math.max(max, rank[rootQ]);
				count--;
			}
			public int getMaxSize() {
				return max;
			}
			public int count() {
				return count;
			}
		}
	}

}
