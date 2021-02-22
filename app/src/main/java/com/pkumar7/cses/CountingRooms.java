import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1192/
//Sol: https://cses.fi/problemset/result/1710922

public class CountingRooms {
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

    private static int solve(FastScanner fs) {
        int n = fs.nextInt();
        int m = fs.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            String row = fs.next();
            for (int j = 0; j < m; j++) {
                map[i][j] = row.charAt(j) == '#' ? -1 : 1;
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 1) {
                    bfs(map, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void bfs(int[][] map, int curr_x, int curr_y) {
        Queue<int[]> bfs = new LinkedList<>();
        bfs.offer(new int[]{curr_x, curr_y});
        int m = map.length;
        int n = map[0].length;
        while (!bfs.isEmpty()) {
            int size = bfs.size();
            while (size-- > 0) {
                int[] curr = bfs.poll();
                int[][] dirs = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
                for (int i = 0; i < dirs.length; i++) {
                    int next_x = dirs[i][0] + curr[0];
                    int next_y = dirs[i][1] + curr[1];
                    if (next_x >= 0 && next_x < m && next_y >= 0 &&
                            next_y < n && map[next_x][next_y] == 1) {
                        map[next_x][next_y] = -1;
                        bfs.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
    }

    private static void dfs(int[][] map, int curr_x, int curr_y) {
        map[curr_x][curr_y] = -1;
        int m = map.length;
        int n = map[0].length;
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        for (int k = 0; k < dirs.length; k++) {
            int next_x = dirs[k][0] + curr_x;
            int next_y = dirs[k][1] + curr_y;
            if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && map[next_x][next_y] == 1) {
                map[next_x][next_y] = -1;
                dfs(map, next_x, next_y);
            }
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

        public int[] readArray(int n) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}