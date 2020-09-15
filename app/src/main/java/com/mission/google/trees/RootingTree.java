package com.mission.google.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 14/September/2020
 */
class RootingTree {
    public static void main(String[] args) {
        int[][] g1 = new int[][]{
                {1}, {0,2}, {1,9,3,6}, {2,4,5},
                {3}, {3}, {2,7,8}, {6}, {6}, {2}
        };

        int[][] g2 = new int[][]{
                {1}, {3,4,0}, {3}, {2,6,7,1},
                {1,5,8}, {4}, {3,9}, {3}, {4}, {6}
        };
        int n = g2.length;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList();
        }
        for (int i = 0; i < n; i++) {
            for (int j : g2[i]) {
                graph[i].add(j);
            }
        }
        TreeCenter treeCenter = new TreeCenter();
        List<Integer> treeCenters = treeCenter.findCenter(graph);
        RootingTree tree = new RootingTree();
        for(int nodeId : treeCenters){
            TreeNode rootNode = tree.rootTree(graph, nodeId, null);
            System.out.println(rootNode);
        }
    }

    class TreeNode{
        List<TreeNode> children;
        TreeNode parent;
        int nodeId;
        public TreeNode(int nodeId, TreeNode parent){
            this.nodeId = nodeId;
            this.parent = parent;
            children = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();
            out.append("Node id " + nodeId + "\n");
            out.append("Parent id " + (parent == null ? " no parent " : parent.nodeId) + "\n" );
            out.append("Children " + children.size());
            return out.toString();
        }
    }

    public TreeNode rootTree(LinkedList<Integer>[] graph, int rootId, TreeNode parent){
        if(graph[rootId].isEmpty()) return null;
        TreeNode root = new TreeNode(rootId, parent);
        for (int child : graph[rootId]){
            if(parent != null && parent.nodeId == child) continue;
            root.children.add(rootTree(graph, child, root));
        }
        return root;
    }
}
