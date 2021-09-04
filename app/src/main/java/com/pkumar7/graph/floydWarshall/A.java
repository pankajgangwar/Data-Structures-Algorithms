package com.pkumar7.graph.floydWarshall;

import java.util.Arrays;

public class A {

    /* LC : 743
    https://leetcode.com/problems/network-delay-time/
    */
    public int findMaxFloydWarshall(int[][] times, int N, int K){
        // Floyd–Warshall algorithm O(n^3)
        double[][] distTo = new double[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
        }
        for (int i = 0; i < N; i++) {
            distTo[i][i] = 0;
        }
        for (int[] edge : times) {
            int src = edge[0];
            int dst = edge[1];
            distTo[src - 1][dst - 1] = edge[2];
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(distTo[i][j] > distTo[i][k] + distTo[k][j]){
                        distTo[i][j] = distTo[i][k] + distTo[k][j];
                    }
                }
            }
        }
        double max = Double.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            if(distTo[K-1][i] == Double.POSITIVE_INFINITY) return -1;
            max = Math.max(max, distTo[K - 1][i]);
        }
        return (int)max;
    }
}
