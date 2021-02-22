import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1093/
//Sol: https://cses.fi/problemset/result/1704146
// Similar : https://leetcode.com/problems/partition-equal-subset-sum/
public class TwoSetsII {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        long ct = System.currentTimeMillis();
        int n = fs.nextInt();
        long ans = solve(n);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    private static int solve(int n) {
        int mod = (int) 1e9 + 7;
        int target = ((n * (n + 1)) / 2);
        int maxsum = target / 2;
        long[][] dp = new long[n + 1][maxsum + 1];
        dp[0][0] = 1;
        for (int ele = 1; ele <= n; ele++) {
            for (int cursum = 0; cursum <= maxsum; cursum++) {
                long opt1 = dp[ele - 1][cursum];//Without ele
                long opt2 = 0;//With ele
                if (cursum - ele >= 0) {
                    opt2 = dp[ele - 1][cursum - ele] % mod;
                }
                dp[ele][cursum] += (opt1 + opt2) % mod;
            }
        }
        if (target % 2 != 0) return 0; // If total sum is not even, two sets can't be created
        return (int) dp[n - 1][maxsum];
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