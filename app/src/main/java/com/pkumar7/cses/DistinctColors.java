package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1139
 */
class DistinctColors {
    static int BLOCK_SIZE = 0;
    static int MAX_ELEMENTS = 2_00_00_00;
    static int MAX_COLORS = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        LinkedList<Integer>[] tree = new LinkedList[MAX_ELEMENTS + 1];
        for (int i = 0; i < n; i++) {
            tree[i] = new LinkedList<>();
        }
        LinkedList<Integer>[] colors = new LinkedList[MAX_ELEMENTS + 1];
        for (int i = 0; i < n; i++) {
            colors[i] = new LinkedList<>();
        }
        for (int i = 0; i < n; i++) {
            colors[i].add(fs.nextInt());
        }
        for (int i = 0; i < n - 1; i++) {
            int a = fs.nextInt() - 1;
            int b = fs.nextInt() - 1;
            tree[a].add(b);
            tree[b].add(a);
        }
        dfs(0, -1, tree, colors);
        int q = n;
        BLOCK_SIZE = (int) Math.sqrt(n);
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            out.printf("range for src : %d is %d and %d \n", i, tin[i], tout[i]);
            queries[i] = new Query(tin[i], tout[i], i);
        }
        out.flush();
        out.close();
    }

    static int tin[] = new int[MAX_ELEMENTS + 1];
    static int tout[] = new int[MAX_ELEMENTS + 1];
    static int t = 0;

    static void dfs(int src, int parent, LinkedList<Integer>[] tree, LinkedList<Integer>[] colors) {
        tin[src] = t++;
        for (int child : tree[src]) {
            if (child != parent) {
                dfs(child, src, tree, colors);
                if (colors[child].size() > colors[src].size()) {

                }
            }
        }
        tout[src] = t - 1;
    }

    public void swap(LinkedList<Integer> colorsSrc, LinkedList<Integer> colorsChild) {

    }

    static int[] solve(int[] arr, Query[] queries) {
        Arrays.parallelSort(queries);
        int start = queries[0].left, end = start;
        int[] frequencies = new int[MAX_ELEMENTS + 1];
        int[] ans = new int[queries.length];
        frequencies[arr[start]]++;
        int count = 1;
        for (Query q : queries) {
            while (start < q.left) {
                frequencies[arr[start]]--;
                if (frequencies[arr[start]] == 0) count--;
                start++;
            }
            while (start > q.left) {
                start--;
                frequencies[arr[start]]++;
                if (frequencies[arr[start]] == 1) count++;
            }
            while (end < q.right) {
                end++;
                frequencies[arr[end]]++;
                if (frequencies[arr[end]] == 1) count++;
            }
            while (end > q.right) {
                frequencies[arr[end]]--;
                if (frequencies[arr[end]] == 0) count--;
                end--;
            }
            ans[q.index] = count;
        }
        return ans;
    }

    static class Query implements Comparable<Query> {
        int left, right, index;

        public Query(int left, int right, int index) {
            this.left = left;
            this.right = right;
            this.index = index;
        }

        @Override
        public int compareTo(Query other) {
            int thisLeftIndex = this.left / BLOCK_SIZE;
            int thatLeftIndex = other.left / BLOCK_SIZE;
            int comp = Integer.compare(thisLeftIndex, thatLeftIndex);
            if (comp == 0) {
                return Integer.compare(this.right, other.right);
            }
            return comp;
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
