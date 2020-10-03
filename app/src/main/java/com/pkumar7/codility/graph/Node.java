package com.pkumar7.codility.graph;

import com.pkumar7.codility.tower.State;
import java.util.LinkedList;
import java.util.List;



public class Node {
    private int depth = 0;
    List<Node> children = new LinkedList<>();
    Node parent;
    State state;

    public Node(State state) {
        this.state = state;
    }

    public List<Node> getChildren() {
        List<State> states = state.buildStates();
        states.forEach(state -> add(new Node(state)));
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public void add(Node child) {
        child.parent = this;
        child.depth = depth + 1;
        children.add(child);
    }

    public String getKey() {
        return state.toString();
    }

    public int getDepth() {
        return depth;
    }
}
