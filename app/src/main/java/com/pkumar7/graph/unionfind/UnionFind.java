package com.pkumar7.graph.unionfind;

/**
 * Created by Pankaj Kumar on 20/December/2020
 */
public class UnionFind {
    private int count = 0;
    private int[] parent, rank;
    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public boolean isConnected(int a, int b) {
        return (find(a) == find(b));
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (rank[rootQ] > rank[rootP]) {
            parent[rootP] = rootQ; // p points to q
        } else {
            parent[rootQ] = rootP; // q points to p
            if (rank[rootP] == rank[rootQ]) {
                rank[rootP]++;
            }
        }
        count--;
    }
    public int getDisjointSets() {
        return count;
    }
}
