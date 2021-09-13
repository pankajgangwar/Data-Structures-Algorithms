package com.pkumar7.trees;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class B {

    /*
     * 1650. Lowest Common Ancestor of a Binary Tree III
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/submissions/
     * */
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };
    public Node lowestCommonAncestor(Node p, Node q) {
        Deque<Node> p_path = new ArrayDeque<>();
        int pval = p.val;
        int qval = q.val;
        while(p != null){
            p_path.add(p);
            if(p.val == qval) return p;
            p = p.parent;
        }

        Deque<Node> q_path = new ArrayDeque<>();
        while(q != null){
            q_path.add(q);
            if(q.val == pval) return q;
            q = q.parent;
        }

        int plen = p_path.size();
        int qlen = q_path.size();
        if(plen < qlen){
            while (q_path.size() != plen){
                q_path.pollFirst();
            }
        }else{
            while (p_path.size() != qlen){
                p_path.pollFirst();
            }
        }
        while(!p_path.isEmpty() && !q_path.isEmpty()){
            Node p_node = p_path.pollFirst();
            Node q_node = q_path.pollFirst();
            if(p_node.val == q_node.val){
                return p_node;
            }
        }
        return null;
    }

    /* 1973. Count Nodes Equal to Sum of Descendants
    * https://leetcode.com/problems/count-nodes-equal-to-sum-of-descendants/
    * */
    int count = 0;
    public int dfs(TreeNode root){
        if(root == null) return 0;
        int sumOfDesc = 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        sumOfDesc += left + right;
        if(sumOfDesc == root.val){
            count += 1;
        }
        return sumOfDesc + root.val;
    }

    public int equalToDescendants(TreeNode root) {
        dfs(root);
        return count;
    }

}
