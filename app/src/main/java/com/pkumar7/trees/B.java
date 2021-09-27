package com.pkumar7.trees;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class B {

    /* 1522. Diameter of N-Ary Tree
     * https://leetcode.com/problems/diameter-of-n-ary-tree/
     * */
    class DiameterNArrayTree{
        class Node {
            public int val;
            public List<Node> children;
            public Node() {
                children = new ArrayList<Node>();
            }
            public Node(int _val) {
                val = _val;
                children = new ArrayList<Node>();
            }
            public Node(int _val,ArrayList<Node> _children) {
                val = _val;
                children = _children;
            }
        };

        int diameter = 0;
        public int diameter(Node root) {
            maxDepth(root);
            return diameter;
        }

        public int maxDepth(Node root){
            if(root == null) return 0;
            if(root.children.isEmpty()) return 1;
            List<Integer> depths = new ArrayList<>();
            for(Node ch : root.children){
                int d = maxDepth(ch);
                depths.add(d);
            }
            int currMax = Collections.max(depths);
            int max = 0;
            int secondMax = 0;
            for(int d : depths){
                if(max < d){
                    secondMax = max;
                    max = d;
                }else if(secondMax < d){
                    secondMax = d;
                }
            }
            diameter = Math.max(diameter, max + secondMax);
            return currMax + 1;
        }
    }

    /* 1676. Lowest Common Ancestor of a Binary Tree IV
    * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv/
    * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        if(root == null) return null;

        for(TreeNode n : nodes){
            if(root == n){
                return root;
            }
        }

        TreeNode l = lowestCommonAncestor(root.left, nodes);
        TreeNode r = lowestCommonAncestor(root.right, nodes);

        if(l != null && r == null) return l;
        if(l == null && r != null) return r;
        if(l != null && r != null) return root;

        return null;
    }

    /*
    * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/
    * 1644. Lowest Common Ancestor of a Binary Tree II
    * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = findLCA(root, p, q);
        if(sets.contains(p.val) && sets.contains(q.val)) return lca;
        return null;
    }

    HashSet<Integer> sets = new HashSet<>();
    public void helper(TreeNode root){
        if(root == null) return;
        sets.add(root.val);
        helper(root.left);
        helper(root.right);
    }

    public TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return root;
        if(root == p || root == q) {
            dfs(root);
            return root;
        }
        sets.add(root.val);
        TreeNode left = findLCA(root.left, p, q);
        TreeNode right = findLCA(root.right, p, q);
        if(left != null && right != null){
            return root;
        }
        if(left != null && right == null){
            return left;
        }
        if(right != null && left == null){
            return right;
        }
        return null;
    }

    /*
    * https://leetcode.com/problems/range-sum-of-bst/
    * 938. Range Sum of BST
    * */
    public int rangeSumBST(TreeNode root, int low, int high) {
        if(root == null) return 0;
        int sum = 0;
        if(root.val > high) return rangeSumBST(root.left, low, high);
        if(root.val < low) return rangeSumBST(root.right, low, high);
        sum += root.val;
        sum += rangeSumBST(root.left, low, high);
        sum += rangeSumBST(root.right, low, high);
        return sum;
    }

    /* 623. Add One Row to Tree
    * https://leetcode.com/problems/add-one-row-to-tree/https://leetcode.com/problems/add-one-row-to-tree/
    * */
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        return bfs(root, val, depth);
    }

    public TreeNode bfs(TreeNode root, int val , int depth) {
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        int currD = 0;
        outer : while (!q.isEmpty()) {
            int size = q.size();
            currD += 1;
            while (size-- > 0) {
                TreeNode curr = q.poll();
                if (currD + 1 == depth) {
                    TreeNode left = new TreeNode(val);
                    if(curr.left != null){
                        TreeNode prevLeft = curr.left;
                        curr.left = left;
                        left.left = prevLeft;
                    }else{
                        curr.left = left;
                    }

                    TreeNode right = new TreeNode(val);
                    if(curr.right != null){
                        TreeNode prevRight = curr.right;
                        curr.right = right;
                        right.right = prevRight;
                    }else{
                        curr.right = right;
                    }
                }else{
                    if(curr.left != null){
                        q.offer(curr.left);
                    }
                    if(curr.right != null){
                        q.offer(curr.right);
                    }
                }
            }
        }
        return root;
    }

    /*
    * https://leetcode.com/problems/balance-a-binary-search-tree/
    * 1382. Balance a Binary Search Tree
    * */
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> sortedList = new ArrayList<>();
        inorder(root, sortedList);
        return constructBst(sortedList, 0, sortedList.size() - 1);
    }

    public TreeNode constructBst(List<Integer> list, int low, int high){
        if(low > high) return null;
        int mid = low + (high - low) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = constructBst(list, low, mid -1);
        root.right = constructBst(list, mid + 1, high);
        return root;
    }

    public void inorder(TreeNode root, List<Integer> list){
        if(root == null) return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

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
