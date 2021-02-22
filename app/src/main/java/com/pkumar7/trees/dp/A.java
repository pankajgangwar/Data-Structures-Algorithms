package com.pkumar7.trees.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pankaj Kumar on 26/December/2020
 */
class A {

    /*
     * https://cses.fi/problemset/task/1687/
     */

    /* https://cp-algorithms.com/graph/lca_binary_lifting.html
     * https://leetcode.com/problems/kth-ancestor-of-a-tree-node/
     * https://www.codechef.com/LRNDSA08/problems/ENOC1
     * https://www.codechef.com/problems/CATS
     *  Binary lifting
     */
    static class TreeAncestor {
        Map<Integer, List<Integer>> children = new HashMap<>();
        Integer[][] up;
        int MAX_NODES = 200001;
        int MAX_DEPTH = 20;

        public TreeAncestor(int n, int[] parents) {
            up = new Integer[n][30];//30 is max depth
            for (int i = 0; i < n; i++) {
                int currNode = i;
                int parent = parents[i];
                children.computeIfAbsent(parent, value -> new ArrayList<Integer>()).add(currNode);
                //if(i > 0) up[currNode][0] = parent;//ancestor at 1 level up
            }
            //dfs(0);
            binary_lifting(0, -1);
        }

        public void binary_lifting(int src, int parent) {
            /*
                2^i = 2^(i-1) + 2^(i-1)
            */
            up[src][0] = parent;// node at 2^0 level up
            for (int i = 1; i < MAX_DEPTH; i++) {
                if (up[src][i - 1] != -1) { // node at 2^(i-1) level up
                    int ancestor = up[src][i - 1];
                    up[src][i] = up[ancestor][i - 1];
                } else {
                    up[src][i] = -1;
                }
            }
            for (int child : children.getOrDefault(src, new ArrayList<>())) {
                if (child != parent) {
                    binary_lifting(child, src);
                }
            }
        }

        private void dfs(int currNode) {
            for (int i = 1; up[currNode][i - 1] != null; i++) {
                int parent = up[currNode][i - 1];
                up[currNode][i] = up[parent][i - 1];
            }
            for (int child : children.getOrDefault(currNode, new ArrayList<>())) {
                dfs(child);
            }
        }

        int answer_query(int node, int k) {
            if (k == 0) {
                return node;
            }
            /*
             * Ex: 5 = (101) = 2^2 + 2^0
             * */
            for (int i = MAX_DEPTH; i >= 0 && node != -1; --i) {
                if ((k & (1 << i)) != 0) { // Check if bit is set
                    node = up[node][i];
                }
            }
            return node;
        }

        int query_dfs(int node, int jump_required) {
            if (node == -1 || jump_required == 0) {
                return node;
            }
            for (int i = MAX_DEPTH; i >= 0; --i) {
                if ((jump_required >= (1 << i))) {
                    return query_dfs(up[node][i], jump_required - (1 << i));
                }
            }
            return node;
        }

        public int getKthAncestor(int node, int k) {
            if (k == 0) return node;
            for (int i = 19; i >= 0; --i) {
                long num = (long) Math.pow(2, i);
                if (k >= num) {
                    node = up[i][node];
                    k -= num;
                    if (node == -1) return -1;
                }
            }
            return node;
        }
    }
}
