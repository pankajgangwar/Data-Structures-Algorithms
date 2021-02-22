import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1745/
//Sol: https://cses.fi/problemset/result/1701568
// 0-1 knapsack
public class MoneySum {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int[] coins = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            coins[i] = fs.nextInt();
        }
        //long ct = System.currentTimeMillis();
        List<Integer> ans = solve(coins, n);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        Iterator<Integer> it = ans.iterator();
        out.printf("%d\n", ans.size());
        while (it.hasNext()) {
            int val = it.next();
            out.printf("%d ", val);
        }
        out.flush();
        out.close();
    }

    private static List<Integer> solve(int[] coins, int n) {
        int maxsum = Arrays.stream(coins).sum();
        boolean[][] dp = new boolean[n + 1][maxsum + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= maxsum; j++) {
                boolean opt1 = dp[i - 1][j]; // If its possible to make amount j using i -1 coins without ith coin
                boolean opt2 = false;// Or if its possible to make amount j using i - 1 coins with using ith coin
                if (j - coins[i] >= 0) {
                    opt2 = dp[i - 1][j - coins[i]];
                }
                dp[i][j] = opt1 || opt2;
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int j = 1; j <= maxsum; j++) {
            if (dp[n][j]) res.add(j);
        }
        return res;
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