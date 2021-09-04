package com.pkumar7.graph.bellmanford

import java.lang.Double.MIN_VALUE
import java.lang.Double.POSITIVE_INFINITY
import java.util.*
import kotlin.math.max

class A {
    // https://cp-algorithms.com/graph/bellman_ford.html
    /* LC : 743
    https://leetcode.com/problems/network-delay-time/
    */
    fun findMaxBellmanFord(time: Array<IntArray>, N: Int, K: Int): Int {
        // Bellman-Ford O(N*E)
        // Runs N-1 times for relaxation
        // Single source shortest path
        // Can be used to detect negative cycles regardless of the starting node.
        // Doesn't work for negative weight cycles
        val disTo = doubleArrayOf()
        Arrays.fill(disTo, POSITIVE_INFINITY)
        disTo[K - 1] = 0.0
        for (i in 1..N) {
            for (edges in time) {
                val u = edges[0] - 1
                val v = edges[1] - 1
                val w = edges[2]
                if (disTo[u] + w < disTo[v]) {
                    disTo[v] = disTo[u] + w
                }
            }
        }
        var res = MIN_VALUE
        for (`val` in disTo) {
            res = max(res, `val`)
        }
        return if (res == POSITIVE_INFINITY) -1 else res.toInt()
    }
}