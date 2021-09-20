package com.pkumar7.math;

import java.util.HashMap;
import java.util.LinkedList;

public class B {

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
