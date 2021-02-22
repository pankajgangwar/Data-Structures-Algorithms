import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1667/
//Sol: https://cses.fi/problemset/result/
public class MessageRoute {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            graph.putIfAbsent(a, new ArrayList<Integer>());
            graph.putIfAbsent(b, new ArrayList<Integer>());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        long ct = System.currentTimeMillis();
        List<Integer> ans = solve(graph, n);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        if (ans.size() != 0) {
            out.printf("%d\n", ans.size());
            for (int i = ans.size() - 1; i >= 0; i--) {
                out.printf("%d ", ans.get(i));
            }
        } else {
            out.printf("IMPOSSIBLE");
        }
        out.flush();
        out.close();
    }

    private static List<Integer> solve(HashMap<Integer, ArrayList<Integer>> graph, int n) {
        Queue<Integer> bfs = new LinkedList<>();
        HashMap<Integer, Integer> prevTrack = new HashMap<>();
        bfs.offer(1);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(1);
        prevTrack.put(1, 0);
        boolean isReachable = false;
        while (!bfs.isEmpty()) {
            int computer = bfs.poll();
            if (computer == n) {
                isReachable = true;
                break;
            }
            if (graph.containsKey(computer)) {
                for (int adj : graph.get(computer)) {
                    if (visited.contains(adj)) continue;
                    visited.add(adj);
                    prevTrack.put(adj, computer);
                    bfs.offer(adj);
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        if (!isReachable) {
            return ans;
        }
        while (n != 0) {
            ans.add(n);
            n = prevTrack.get(n);
        }
        return ans;
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