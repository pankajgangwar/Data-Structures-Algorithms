package com.pkumar7.spoj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://www.spoj.com/problems/DQUERY/
 */
class DQuery {
    static int BLOCK_SIZE = 0;
    static int MAX_ELEMENTS = 1_00_00_00;

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
        }
        int q = fs.nextInt();
        BLOCK_SIZE = (int) Math.sqrt(n);
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            int l = fs.nextInt() - 1;
            int r = fs.nextInt() - 1;
            queries[i] = new Query(l, r, i);
        }
        int[] res = solve(arr, queries);
        for (int i = 0; i < res.length; i++) {
            out.printf(res[i] + "\n");
        }
        out.flush();
        out.close();
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
