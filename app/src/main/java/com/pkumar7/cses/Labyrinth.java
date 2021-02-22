import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

//Problem : https://cses.fi/problemset/task/1193/
//Sol: https://cses.fi/problemset/result/

public class Labyrinth {
    //SOLUTION BEGIN
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        long ct = System.currentTimeMillis();
        int m = fs.nextInt();
        int n = fs.nextInt();
        char[][] grid = new char[m][n];
        int[] start = new int[]{-1, -1};
        int[] end = new int[]{-1, -1};
        for (int i = 0; i < m; i++) {
            String row = fs.next();
            for (int j = 0; j < n; j++) {
                grid[i][j] = row.charAt(j);
                if (grid[i][j] == 'A') {
                    start[0] = i;
                    start[1] = j;
                } else if (grid[i][j] == 'B') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }
        PrintWriter out = new PrintWriter(System.out);
        if ((start[0] == -1 && start[1] == -1) || (end[0] == -1 && end[1] == -1)) {
            out.printf("NO\n");
        } else {
            bfs(grid, start, end);
        }
        out.flush();
        out.close();
        System.err.println(System.currentTimeMillis() - ct + " ms");
    }

    static class Path {
        String s;
        int x;
        int y;

        public Path(String s, int x, int y) {
            this.s = s;
            this.x = x;
            this.y = y;
        }
    }

    private static void bfs(char[][] map, int start[], int end[]) {
        PrintWriter out = new PrintWriter(System.out);
        Queue<Path> bfs = new LinkedList<>();
        bfs.offer(new Path("", start[0], start[1]));
        int m = map.length;
        int n = map[0].length;
        boolean status = false;
        outer:
        while (!bfs.isEmpty()) {
            int size = bfs.size();
            while (size-- > 0) {
                Path curr = bfs.poll();
                if (curr.x == end[0] && curr.y == end[1]) {
                    out.printf("YES \n");
                    out.printf(curr.s.length() + "\n");
                    out.printf(curr.s + "\n");
                    status = true;
                    break outer;
                }
                int[][] dirs = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
                for (int i = 0; i < dirs.length; i++) {
                    int next_x = dirs[i][0] + curr.x;
                    int next_y = dirs[i][1] + curr.y;
                    if (next_x >= 0 && next_x < m && next_y >= 0 &&
                            next_y < n && (map[next_x][next_y] == '.' || map[next_x][next_y] == 'B')) {
                        map[next_x][next_y] = '#';
                        String dir = "";
                        switch (i) {
                            case 0:
                                dir = "R";
                                break;
                            case 1:
                                dir = "L";
                                break;
                            case 2:
                                dir = "U";
                                break;
                            case 3:
                                dir = "D";
                                break;
                        }
                        bfs.offer(new Path(curr.s + dir, next_x, next_y));
                    }
                }
            }
        }
        if (!status) out.printf("NO\n");
        out.flush();
        out.close();
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