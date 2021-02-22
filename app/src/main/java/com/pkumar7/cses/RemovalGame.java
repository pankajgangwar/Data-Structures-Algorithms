import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1097/
//Sol: https://cses.fi/problemset/result/1703235

public class RemovalGame {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        long ct = System.currentTimeMillis();
        int n = fs.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = fs.nextInt();
        }
        memo = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        long ans = dfs(coins, 0, n - 1);
        out.printf("%d", ans);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.flush();
        out.close();
    }

    static long[][] memo;

    private static long dfs(int[] arr, int i, int j) {
        if (i > j) {
            return 0;
        }
        if (i + 1 == j) {
            return Math.max(arr[i], arr[j]);
        }
        if (memo[i][j] != -1) return memo[i][j];
        long opt1 = arr[i] + Math.min(dfs(arr, i + 2, j), dfs(arr, i + 1, j - 1));
        long opt2 = arr[j] + Math.min(dfs(arr, i + 1, j - 1), dfs(arr, i, j - 2));
        return memo[i][j] = Math.max(opt1, opt2);
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