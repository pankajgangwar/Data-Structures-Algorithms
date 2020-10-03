package com.pkumar7.KickStart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class C {

    static boolean multipleTC = true, memory = false;
    FastReader in;
    PrintWriter out;

    public static void main(String[] args) throws Exception {
        if (memory) new Thread(null, new Runnable() {
            public void run() {
                try {
                    new C().run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 28).start();
        else new C().run();
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
        int n = ni();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ni();
        }
        HashMap<Integer, Long> map = new HashMap<>();
        int sum= 0;
        long ans = 0;
        long min = 0;
        map.put(sum, 1L);
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            min = Math.min(min, sum);
            for (int d = 0; sum - d*d >= min; d++){
                ans += map.getOrDefault(sum - d*d, 0L);
            }
            map.put(sum, map.getOrDefault(sum , 0L)+1);
        }
        p(ans + " ");
        pn("");
    }

    void run() throws Exception {
        in = new C.FastReader();
        out = new PrintWriter(System.out);
        int T = (multipleTC) ? ni() : 1;
        pre();
        for (int t = 1; t <= T; t++) solve(t);
        out.flush();
        out.close();
    }

}
