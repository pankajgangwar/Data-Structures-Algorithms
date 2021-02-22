import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1746/
//Sol: https://cses.fi/problemset/result/1695467
public class IncreasingSequence {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = fs.nextInt();
        }
        long ct = System.currentTimeMillis();
        int ans = lengthOfLISPatienceSort(array);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    public static int lengthOfLISPatienceSort(int[] nums) {
        //https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }

    private static int solve(int[] array, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
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