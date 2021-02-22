import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1666/
//Sol: https://cses.fi/problemset/result/1721256
public class BuildingRoads {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int a = fs.nextInt() - 1;
            int b = fs.nextInt() - 1;
            graph.putIfAbsent(a, new ArrayList<Integer>());
            graph.putIfAbsent(b, new ArrayList<Integer>());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        long ct = System.currentTimeMillis();
        List<int[]> ans = solve(graph, n);
        System.err.println(System.currentTimeMillis() - ct + " ms");
        out.printf("%d\n", ans.size());
        for (int i = 0; i < ans.size(); i++) {
            int[] curr = ans.get(i);
            out.printf("%d %d\n", curr[0] + 1, curr[1] + 1);
        }
        out.flush();
        out.close();
    }

    private static List<int[]> solve(HashMap<Integer, ArrayList<Integer>> graph, int n) {
        List<int[]> ans = new ArrayList<>();
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            if (graph.containsKey(i)) {
                int first = i;
                for (int adj : graph.get(i)) {
                    if (unionFind.find(first) == unionFind.find(adj)) continue;
                    unionFind.union(first, adj);
                }
            }
        }
        int first = 0;
        for (int another = 1; another < n; another++) {
            if (unionFind.find(first) != unionFind.find(another)) {
                ans.add(new int[]{first, another});
                unionFind.union(first, another);
            }
        }
        return ans;
    }

    private static class UnionFind {
        private int count = 0;
        private int[] parent, rank;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ; // p points to q
            } else {
                parent[rootQ] = rootP; // q points to p
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }

        public int count() {
            return count;
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