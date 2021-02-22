
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1744/
//Sol: https://cses.fi/problemset/result/1700582
public class RectangleCutting {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int a = fs.nextInt();
        int b = fs.nextInt();
        long ct = System.currentTimeMillis();
        int ans = solve(a, b);
        /*memo = new int[a + 1][b + 1];
        for (int i = 1; i <= a; i++) {
            Arrays.fill(memo[i], -1);
        }
        int ans = dfs(a, b);*/
        out.printf("%d", ans);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.flush();
        out.close();
    }

    private static int solve(int len, int width) {
        int[][] dp = new int[len + 1][width + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= width; j++) {
                if (i == j) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = 1; k <= j - 1; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[i][j - k] + 1);
                    }
                    for (int k = 1; k <= i - 1; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - k][j] + dp[k][j] + 1);
                    }
                }
            }
        }
        return dp[len][width];
    }

    static int[][] memo;

    private static int dfs(int len, int width) {
        if (len == width) return 0;
        if (memo[len][width] != -1) return memo[len][width];
        int horizontal = Integer.MAX_VALUE;
        int vertical = Integer.MAX_VALUE;
        for (int i = 1; i <= len - 1; i++) {
            vertical = Math.min(vertical, 1 + dfs(i, width) + dfs(len - i, width));
        }
        for (int j = 1; j <= width - 1; j++) {
            horizontal = Math.min(horizontal, 1 + dfs(len, j) + dfs(len, width - j));
        }
        return memo[len][width] = Math.min(vertical, horizontal);
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