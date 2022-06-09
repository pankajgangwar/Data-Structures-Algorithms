package com.pkumar7.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class B {

    /*
     * https://leetcode.com/problems/squirrel-simulation/
     * 573. Squirrel Simulation
     * */
    public int minDistance(int height, int width, int[] t, int[] s, int[][] nuts) {
        int sum = 0;
        int maxDiff = Integer.MIN_VALUE;
        for (int[] n : nuts) {
            int dist = getDistance(t, n);
            sum += 2*dist;
            maxDiff = Math.max(maxDiff, dist - getDistance(s, n));
        }
        return sum - maxDiff;
    }

    int getDistance(int[] a, int[] b){
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    /*
     * https://leetcode.com/problems/count-lattice-points-inside-a-circle/
     * 2249. Count Lattice Points Inside a Circle
     * */
    public int countLatticePoints(int[][] circles) {
        HashSet<String> points = new HashSet<>();
        for(int[] c : circles){
            int x = c[0];
            int y = c[1];
            int r = c[2];
            for(int i = x - r; i <= x + r; i++){
                for(int j = y - r; j <= y+r; j++){
                    if((x-i)*(x-i) + (y-j)*(y-j) <= r*r){
                        String coord = i + ","+j;
                        points.add(coord);
                    }
                }
            }
        }
        return points.size();
    }

    /*
     * 1828. Queries on Number of Points Inside a Circle
     * https://leetcode.com/problems/queries-on-number-of-points-inside-a-circle/
     * */
    public int[] countPoints(int[][] points, int[][] queries) {
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int x = q[0], y = q[1], r = q[2];
            int count = 0;
            for (int[] p : points) {
                int xx = (int)Math.pow((x-p[0]), 2);
                int yy = (int)Math.pow((y-p[1]), 2);
                if(xx + yy <= r*r){
                    count += 1;
                }
            }
            res[i] = count;
        }
        return res;
    }

    /*
    https://leetcode.com/problems/the-kth-factor-of-n/
    */
    public int kthFactor(int n, int k) {
        int sqrt = (int)Math.sqrt(n);

        for(int i = 1; i <= sqrt; i++) {
            if(n % i == 0 && --k == 0) {
                return i;
            }
        }
        for (int i = sqrt; i >=0 ; i--) {
            if(i * i == n) continue;
            if(n % i == 0 && --k == 0){
                return n / i;
            }
        }
        return -1;
    }

    class DetectSquares {
        HashMap<Integer, LinkedList<Integer>> xMap = new HashMap<>();
        HashMap<Integer, LinkedList<Integer>> yMap = new HashMap<>();
        HashMap<String, Integer> count = new HashMap<>();
        public DetectSquares() {

        }

        public void add(int[] point) {
            int x = point[0];
            int y = point[1];
            xMap.putIfAbsent(x, new LinkedList<>());
            yMap.putIfAbsent(y, new LinkedList<>());

            xMap.get(x).add(y);
            yMap.get(y).add(x);

            String s = x + "," + y;
            count.put(s, count.getOrDefault(s, 0) + 1);
        }

        public int count(int[] point) {
            int x = point[0];
            int y = point[1];
            int sum = 0;
            for(int y1 : xMap.getOrDefault(x, new LinkedList<>())){
                if(y1 == y ) continue;
                int d = Math.abs( y1 - y);
                if(x + d <= 1000){
                    sum += count.getOrDefault(getCoord(x + d, y), 0) * count.getOrDefault(getCoord(x + d, y1), 0);
                }
                if(x >= d){
                    sum += count.getOrDefault(getCoord(x - d, y), 0) * count.getOrDefault(getCoord(x - d, y1), 0);
                }
            }
            return sum;
        }

        public String getCoord(int x, int y){
            return x + "," + y;
        }
    }

    /*
     * 1925. Count Square Sum Triples
     * https://leetcode.com/problems/count-square-sum-triples/
     * */
    public int countTriples(int n) {
        int res = 0;
        for (int a = 1; a * a < n * n; a++) {
            for (int b = 1; b * b < n * n; b++) {
                for (int c = 1; c <= n; c++) {
                    if (a == b || b == c) {
                        continue;
                    }
                    if (a * a + b * b == c * c) {
                        res += 1;
                    }
                }
            }
        }
        return res;
    }
}
