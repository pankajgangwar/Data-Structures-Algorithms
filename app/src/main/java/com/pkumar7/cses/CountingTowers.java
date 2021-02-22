
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/2413/
//Sol: https://cses.fi/problemset/result/1696606
public class CountingTowers {
    //SOLUTION BEGIN
    /*
     dp[i] : ways to build the tower of height i
     linked -> 1 : if i - 1 position has a width of 2
     linked -> 0 : if i - 1 position has 2 tiles of width of 1 each,
     either extend both, or close one extend other or close both

     dp[i, 0] => 1. Do not extend any tile => dp[i + 1][0] + dp[i + 1][1]
                 2. Extend only one => 2 * dp[i + 1][0]
                 3. Extend both : dp[i + 1][0]

     dp[i][1] => Extend both the tiles

     dp[i - 1][1] + dp[i - 1][0] + dp[i - 1, 0]
     ans : dp[2, 0] + dp[2, 1]

     */
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int t = fs.nextInt();
        //long ct = System.currentTimeMillis();
        int max = (int) 1e6;
        long[][] dp = new long[max + 1][2];
        int mod = (int) 1e9 + 7;
        solve(max, dp);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        while (t-- > 0) {
            int n = fs.nextInt();
            int ans = (int) (dp[n][0] + dp[n][1]) % mod;
            out.printf("%d\n", ans);
        }
        out.flush();
        out.close();
    }

    private static void solve(int n, long[][] dp) {
        int mod = (int) 1e9 + 7;
        dp[1][0] = dp[1][1] = 1;
        for (int i = 2; i <= n; i++) {
            long opt1 = (dp[i - 1][0] + dp[i - 1][1]) % mod;
            long opt2 = (2 * dp[i - 1][0]) % mod;
            long opt3 = dp[i - 1][0];
            long opt4 = dp[i - 1][1];
            dp[i][0] = (opt1 + opt2 + opt3) % mod;
            dp[i][1] = (opt1 + opt4) % mod;
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