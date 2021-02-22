//package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SubtreeQueries {
    //SOLUTION BEGIN
    static int MAX_NODES = 200001;
    static int MAX_DEPTH = 20;
    static LinkedList<Integer>[] tree;
    static long val[];
    static int tin[], tout[];
    static int t = 0;

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int q = fs.nextInt();
        val = new long[MAX_NODES];
        tin = new int[MAX_NODES];
        tout = new int[MAX_NODES];
        tree = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            val[i] = fs.nextLong();
            tree[i] = new LinkedList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = fs.nextInt() - 1;
            int v = fs.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        dfs(0, -1);
        BIT tree = new BIT(n);
        for (int i = 0; i < n; i++) {
            tree.update(tin[i], val[i]);
        }
        for (int i = 0; i < q; i++) {
            int type = fs.nextInt(), src = fs.nextInt();
            switch (type) {
                case 1: {
                    src -= 1;
                    long value = fs.nextLong();
                    long diff = value - val[src];
                    tree.update(tin[src], diff);
                    val[src] = value;
                }
                break;
                case 2: {
                    src -= 1;
                    long sum = tree.rangesum(tin[src], tout[src]);
                    out.printf("%d\n", sum);
                }
                break;
            }
        }
        out.close();
    }

    static void dfs(int src, int parent) {
        tin[src] = t++;
        for (int child : tree[src]) {
            if (child != parent) {
                dfs(child, src);
            }
        }
        tout[src] = t - 1;
    }

    static class BIT {
        int n;
        long[] tree;

        public BIT(int n) {
            this.n = n;
            tree = new long[n + 2];
        }

        void update(int i, long value) {
            i += 1;
            while (i <= n) {
                tree[i] += value;
                i += (i & -i);
            }
        }

        long read(int i) {
            i += 1;
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= (i & -i);
            }
            return sum;
        }

        long rangesum(int from, int to) {
            return read(to) - read(from - 1);
        }
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        public String next() {
            while (!st.hasMoreElements())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}