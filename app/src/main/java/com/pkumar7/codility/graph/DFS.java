package com.pkumar7.codility.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DFS {

    private Node solution;
    private Stack<Node> stack = new Stack<>();

    private Map<String, Node> discovered = new HashMap<>();
    private Node root;

    public DFS(Node node) {
        root = node;
        stack.push(root);
    }


    public Node search(Node result) {

        while (!stack.empty()) {

            Node current = stack.pop();
            if (current.getKey().equals(result.getKey())) {
                if (solution == null || current.getDepth() < solution.getDepth()) {
                    solution = current;
                }
            }

            List<Node> children = current.getChildren();
            for (Node child : children) {
                if (!discovered.containsKey(child.getKey())) {
                    discovered.put(child.getKey(), child);
                    stack.push(child);
                } else {
                    Node discoveredState = discovered.get(child.getKey());
                    if (discoveredState.getDepth() > current.getDepth() + 1) {
                        discovered.put(child.getKey(), child);
                    }
                }
            }
        }

        return solution;
    }


}
