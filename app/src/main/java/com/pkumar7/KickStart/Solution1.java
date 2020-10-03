package com.pkumar7.KickStart;

import java.util.*;
import java.io.*;
import java.text.*;

public class Solution1 {
    //SOLUTION BEGIN
    void pre() throws Exception {
    }

    void solve(int TC) throws Exception {
        p("Case #" + TC + ": ");
        int n = ni(), q = ni();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            int x = ni();
            int b = bit(x);
            if (b % 2 == 1) set.add(i);
        }
        while (q-- > 0) {
            int p = ni(), v = ni();
            set.remove(p);
            if (bit(v) % 2 == 1) set.add(p);
            if (set.size() % 2 == 0) p(n + " ");
            else {
                int x = set.first(), y = set.last();
                p(Math.max(n - x - 1, y) + " ");
            }
        }
        pn("");
    }

    //SOLUTION END
    void hold(boolean b) throws Exception {
        if (!b) throw new Exception("Hold right there, Sparky!");
    }

    long mod = (long) 1e9 + 7, IINF = (long) 1e18;
    final int INF = (int) 1e9, MX = (int) 2e5 + 1;
    DecimalFormat df = new DecimalFormat("0.00000000000");
    double PI = 3.1415926535897932384626433832792884197169399375105820974944, eps = 1e-8;
    static boolean multipleTC = true, memory = false;
    FastReader in;
    PrintWriter out;

    void run() throws Exception {
        in = new FastReader();
        out = new PrintWriter(System.out);
        int T = (multipleTC) ? ni() : 1;
        pre();
        for (int t = 1; t <= T; t++) solve(t);
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws Exception {
        if (memory) new Thread(null, new Runnable() {
            public void run() {
                try {
                    new Solution1().run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 28).start();
        else new Solution1().run();
    }

    long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    int bit(long n) {
        return (n == 0) ? 0 : (1 + bit(n & (n - 1)));
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
}
