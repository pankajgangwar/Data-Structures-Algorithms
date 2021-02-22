package com.pkumar7.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.Spring;

/**
 * Created by Pankaj Kumar on 02/February/2021
 * https://cses.fi/problemset/task/1139
 */
class DistinctColors {

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        long n = fs.nextLong();
        ans =  new long[(int)n + 1];
        HashMap<Long, List<Long>> tree = new HashMap<>();
        HashMap<Long, Set<Long>> colors = new HashMap<>();
        for(long i = 1; i <= n; i++){
            colors.putIfAbsent(i, new HashSet<>());
            colors.get(i).add(fs.nextLong());
        }
        for (int i = 0; i < n - 1; i++) {
            long a = fs.nextLong();
            long b = fs.nextLong();
            tree.putIfAbsent(a, new ArrayList<>());
            tree.putIfAbsent(b, new ArrayList<>());
            tree.get(a).add(b);
        }
        dfs(0L, -1L, tree, colors);
        for (int i = 1; i <= n; i++) {
            out.print(ans[i] + ' ');
        }
        out.flush();
        out.close();
    }

    static long ans[];
    static long t = 0;
    static void dfs(long src, long parent, HashMap<Long, List<Long>> tree, HashMap<Long, Set<Long>> colors) {
        for (long child : tree.getOrDefault(src, new ArrayList<>())){
            if (child != parent){
                dfs(child, src, tree, colors);
                if(colors.getOrDefault(child, new HashSet<>()).size() > colors.getOrDefault(src, new HashSet<>()).size()){
                    Set<Long> xx = colors.getOrDefault(child, new HashSet<>());
                    Set<Long> yy = colors.getOrDefault(src, new HashSet<>());
                    colors.put(child, yy);
                    colors.put(src, xx);
                }
                for(long col : colors.getOrDefault(child, new HashSet<>())){
                    colors.get(src).add(col);
                }
                colors.get(child).clear();
            }
        }
        ans[(int)src] = colors.get(src).size();
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
