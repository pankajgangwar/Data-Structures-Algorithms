import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

//Problem : https://cses.fi/problemset/task/1140/
//Sol: https://cses.fi/problemset/result/1708022

public class Projects {
    static class Project {
        int start, end, reward;

        public Project(int start, int end, int reward) {
            this.start = start;
            this.end = end;
            this.reward = reward;
        }
    }

    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        long ct = System.currentTimeMillis();
        long ans = solve(fs);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d", ans);
        out.flush();
        out.close();
    }

    private static long solve(FastScanner fs) {
        int n = fs.nextInt();
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int start = fs.nextInt();
            int end = fs.nextInt();
            int reward = fs.nextInt();
            Project p = new Project(start, end, reward);
            projects.add(p);
        }
        Collections.sort(projects, (a, b) -> a.end - b.end);
        long dp[] = new long[n];
        dp[0] = projects.get(0).reward;
        for (int i = 1; i < n; i++) {
            long opt1 = dp[i - 1];
            long opt2 = projects.get(i).reward;
            int search = findBestProject(projects, i);
            if (search != -1) opt2 += dp[search];
            dp[i] = Math.max(opt1, opt2);
        }
        return dp[n - 1];
    }

    private static int findBestProject(List<Project> projects, int idx) {
        int low = 0, high = idx - 1;
        while (low <= high) {
            int mid = (high + low) / 2;
            if (projects.get(mid).end < projects.get(idx).start) {
                if (projects.get(mid + 1).end < projects.get(idx).start) {
                    low = mid + 1;
                } else {
                    return mid; // One based index
                }
            } else {
                high = mid - 1;
            }
        }
        return -1;
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