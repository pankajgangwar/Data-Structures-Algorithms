package com.pkumar7.KickStart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution2 {

    static boolean multipleTC = true, memory = false;
    FastReader in;
    PrintWriter out;

    public static void main(String[] args) throws Exception {
        if (memory) new Thread(null, new Runnable() {
            public void run() {
                try {
                    new Solution2().run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 28).start();
        else new Solution2().run();
    }

    class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws Exception {
            br = new BufferedReader(new FileReader(s));
        }

        String next() throws Exception {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new Exception(e.toString());
                }
            }
            return st.nextToken();
        }

        String nextLine() throws Exception {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                throw new Exception(e.toString());
            }
            return str;
        }
    }

    void pre() throws Exception {
    }

    void p(Object o) {
        out.print(o);
    }

    void pn(Object o) {
        out.println(o);
    }

    void pni(Object o) {
        out.println(o);
        out.flush();
    }

    String n() throws Exception {
        return in.next();
    }

    String nln() throws Exception {
        return in.nextLine();
    }

    int ni() throws Exception {
        return Integer.parseInt(in.next());
    }

    long nl() throws Exception {
        return Long.parseLong(in.next());
    }

    double nd() throws Exception {
        return Double.parseDouble(in.next());
    }

    void solve(int TC) throws Exception {
        p("Case #" + TC + ": ");
        int n = ni(), m = ni(), q = ni();
        //p("N " + n + " m " + m + " q " + q);
        ArrayList<Integer> tornOut = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            tornOut.add(ni());
        }

        int total_pages = 0;
        for (int i = 0; i < q; i++) {
            int Ri = ni();
            for (int j = Ri; j <= n; j+= Ri) {
                if(!tornOut.contains(j)){
                    total_pages++;
                }
            }
        }
        p(total_pages + " ");
        pn("");
    }

    void run() throws Exception {
        in = new Solution2.FastReader();
        out = new PrintWriter(System.out);
        int T = (multipleTC) ? ni() : 1;
        pre();
        for (int t = 1; t <= T; t++) solve(t);
        out.flush();
        out.close();
    }

}
