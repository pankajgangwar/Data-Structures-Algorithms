import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1195/
//Sol: https://cses.fi/problemset/result/1748143
public class FlightDiscount {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        HashMap<Integer, List<long[]>> graph = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            int src = fs.nextInt();
            int dest = fs.nextInt();
            int cost = fs.nextInt();
           graph.putIfAbsent(src, new ArrayList<>());
           graph.get(src).add(new long[]{dest, cost});
        }
        //long ct = System.currentTimeMillis();
        long ans = solve(graph, n);
        //System.err.println(System.currentTimeMillis() - ct + " ms");
        out.println(ans);
        out.flush();
        out.close();
    }

    /*  Dijikstra */
    private static long solve(HashMap<Integer, List<long[]>> graph, int n) {
        int mod = (int) 1e9 + 7;
        long max = (long)1e18;
        long[][] dist = new long[n + 1][2];
        for (int i = 1; i <= n; i++) {
            dist[i][0] = max;dist[i][1] = max;
        }
        //[src, cost, flag]
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{1L, 0L, 0L});
        while (!pq.isEmpty()){
            long[] curr = pq.poll();
            int src = (int)curr[0];
            long totalCost = curr[1];
            int flag = (int)curr[2];
            if(src == n) return totalCost;
            for(long[] edge : graph.getOrDefault(src, new ArrayList<>())){
                int dest = (int)edge[0];
                long cost = edge[1];
                if(dist[dest][flag] > totalCost + cost){
                    dist[dest][flag] = totalCost + cost;
                    pq.offer(new long[]{dest, dist[dest][flag], flag});
                }
                if(flag == 0){
                    if(dist[dest][1] > totalCost + (cost / 2)){
                        dist[dest][1] = totalCost + (cost / 2);
                        pq.offer(new long[]{dest, dist[dest][1], 1});
                    }
                }
            }
        }
        return -1;//This should never happen
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