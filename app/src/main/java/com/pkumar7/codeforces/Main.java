package com.pkumar7.codeforces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int t = fs.nextInt();
        while (t-- > 0) {
            int n = fs.nextInt();
            int eggs = fs.nextInt();
            int cb = fs.nextInt();
            int costO = fs.nextInt();
            int costM = fs.nextInt();
            int costC = fs.nextInt();
            int res = dfs(n, eggs, cb, costO, costM, costC);
            out.println(res);
        }
        out.flush();
        out.close();
    }

    /*
     * 2 egg = omelette
     * 3 cb  = milkshake
     * 1 egg + 1 cb = cake
     *
     * N = 5, Egg = 4, CB = 4, costO = 2, costM = 2, costC = 2
     * */
    static int cost = 0;
    static public int dfs(int n, int eggs , int cb, int costO, int costM, int costC){
        if(n == 0) return 0;
        if(n >= 1 && eggs <= 0 && cb <= 0) return -1;
        int rem1 = dfs(n - 1, eggs - 2, cb, costO, costM, costC);
        int rem2 = dfs(n - 1, eggs, cb - 3, costO, costM, costC);
        int rem3 = dfs(n - 1, eggs - 1, cb - 1, costO, costM, costC);

        if(rem1 < 0 || rem2 < 0 || rem3 < 0) return -1;

        int opt1 = costO + rem1;
        int opt2 = costM + rem2;
        int opt3 = costC + rem3;
        int minOpt12 = Math.min(opt1, opt2);
        int minOpt23 = Math.min(opt2, opt3);
        int min = Math.min(minOpt12, minOpt23);
        return min;
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