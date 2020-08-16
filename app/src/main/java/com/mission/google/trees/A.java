package com.mission.google.trees;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pankaj Kumar on 12/August/2020
 */
class A {

    /*
    1382. Balance a Binary Search Tree
    https://leetcode.com/problems/balance-a-binary-search-tree/
    */
    public TreeNode balanceBST(TreeNode root) {
        return null;
    }

    class NodeTree {
        public int val;
        public List<NodeTree> children;
        public NodeTree() {
            children = new ArrayList<NodeTree>();
        }
        public NodeTree(int _val) {
            val = _val;
            children = new ArrayList<NodeTree>();
        }
        public NodeTree(int _val, ArrayList<NodeTree> _children) {
            val = _val;
            children = _children;
        }
    }

    /*
    1490. Clone N-ary Tree
    https://leetcode.com/problems/clone-n-ary-tree/
    */
    public NodeTree cloneTree(NodeTree root) {
        if(root == null) return root;
        
        ArrayList<NodeTree> children = new ArrayList<>();
        NodeTree copy = new NodeTree(root.val, children);
        
        for(int i = 0; i < root.children.size(); i++){
            children.add(cloneTree(root.children.get(i)));
        }
        return copy;
    }

    
    class Node {
        int val;
        Node left;
        Node right;
        Node random;
        Node() {}
        Node(int val) { this.val = val; }
        Node(int val, Node left, Node right, Node random) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.random = random;
        }
    }
    class NodeCopy {
        int val;
        NodeCopy left;
        NodeCopy right;
        NodeCopy random;
        NodeCopy() {}
        NodeCopy(int val) { this.val = val; }
        NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.random = random;
        }
    }

    /*
    1485. Clone Binary Tree With Random Pointer
    https://leetcode.com/problems/clone-binary-tree-with-random-pointer/
    */
    HashMap<Node, NodeCopy> visited = new HashMap<Node, NodeCopy>();
    public NodeCopy copyRandomBinaryTree(Node root) {
        if(root == null) return null;
        
        if(visited.containsKey(root)) return visited.get(root);
        
        NodeCopy cp = new NodeCopy(root.val, null, null, null);
        visited.put(root, cp);
        
        cp.left = copyRandomBinaryTree(root.left);
        cp.right = copyRandomBinaryTree(root.right);
        
        cp.random = copyRandomBinaryTree(root.random);
        
        return cp;
    }
}
