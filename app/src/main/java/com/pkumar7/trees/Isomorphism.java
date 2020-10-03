package com.pkumar7.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.pkumar7.trees.RootingTree.TreeNode;

/**
 * Created by Pankaj Kumar on 14/September/2020
 */
class Isomorphism {
    public static void main(String[] args) {
        int[][] g1 = new int[][]{
                {1}, {0,2,4}, {1}, {4,5},
                {1,3}, {3}
        };
        int[][] g2 = new int[][]{
                {1}, {0,2}, {1,4}, {4},
                {2,3,5}, {4}
        };
        Isomorphism isomorphism = new Isomorphism();
        LinkedList<Integer>[] graph1 = isomorphism.buildGraph(g1);
        LinkedList<Integer>[] graph2 = isomorphism.buildGraph(g2);
        boolean status = isomorphism.isIsomorphic(graph1, graph2);
        System.out.println(status ? "Both are Isomorphic " : "Not Isomorphic");
    }

    public LinkedList<Integer>[] buildGraph(int[][] g){
        int n = g.length;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            for (int j : g[i]) {
                graph[i].add(j);
            }
        }
        return graph;
    }
    /*
    * AHU (Aho, Hopcroft, Ullman) algorithm uses clever serialization technique
    * for representing a tree as a unique string.
    * 1) Determine tree isomorphism in time O(|V|^2).
    * 2) Uses complete history of degree spectrum of the vertex descendants as a complete
    *    invariant.
    * */
    public boolean isIsomorphic(LinkedList<Integer>[] graph1, LinkedList<Integer>[] graph2){
        TreeCenter centers1 = new TreeCenter();
        List<Integer> a = centers1.findCenter(graph1);
        List<Integer> b = centers1.findCenter(graph2);
        System.out.println("Center for a " + a.get(0) + "\nCenter for b " + b.get(0));
        RootingTree tree = new RootingTree();
        TreeNode rootNode = tree.rootTree(graph1, a.get(0), null);
        String tree1_encode = encode(rootNode);
        System.out.println("tree1_encode = " + tree1_encode);
        for(int centers : b){
            TreeNode root = tree.rootTree(graph2, centers, null);
            String tree2_encode = encode(root);
            System.out.println("tree2_encode = " + tree2_encode);
            if(tree1_encode.equals(tree2_encode)){
                return true;
            }
        }
        return false;
    }

    public String encode(TreeNode root){
        if(root == null) return "";
        List<String> labels = new ArrayList<>();
        for(TreeNode adj : root.children){
            labels.add(encode(adj));
        }
        Collections.sort(labels);
        StringBuilder out = new StringBuilder();
        for(String child : labels){
            out.append(child);
        }
        return "(" + out.toString() + ")";// Knuth Tuple
    }
}
