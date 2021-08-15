package com.pkumar7.graph.unionfind;

public class UnionFindMatrix {
    private int[] parent, rank;
    int row, col;

    public UnionFindMatrix(int rows, int cols) {
        parent = new int[rows * cols];
        rank = new int[rows * cols];
        this.row = rows;
        this.col = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                parent[i* cols + j] = i * cols + j;
                rank[i * cols + j] = 1;
            }
        }
    }

    public int find(int x, int y) {
        int p = x * col + y;
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public void union(int x1, int y1, int x2, int y2) {
        int rootP = find(x1, y1);
        int rootQ = find(x2, y2);
        if (rootP == rootQ) return;//connected
        if (rank[rootQ] > rank[rootP]) {
            parent[rootP] = rootQ;
        }
        else {
            parent[rootQ] = rootP;
            if (rank[rootP] == rank[rootQ]) {
                rank[rootP]++;
            }
        }
    }
}
