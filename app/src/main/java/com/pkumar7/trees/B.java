package com.pkumar7.trees;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class B {

    /*
    2096. Step-By-Step Directions From a Binary Tree Node to Another
    * https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
    * */
    HashMap<Integer, TreeNode> map = new HashMap<>();
    HashMap<TreeNode, TreeNode> parent = new HashMap<>();
    public String getDirections(TreeNode root, int startValue, int destValue) {
        TreeNode lca = findLCA(root, startValue, destValue);
        TreeNode s = map.get(startValue);
        TreeNode d = map.get(destValue);
        StringBuilder path = new StringBuilder();
        while (s != lca){
            s = parent.get(s);
            path.append("U");
        }
        String p = "";
        dfs(root, d, p);
        return path + p ;
    }

    public void dfs(TreeNode root, TreeNode dst, String path){
        if(root == null) return;
        if(root == dst) return;
        dfs(root.left, dst, path + "L");
        dfs(root.right, dst, path + "R");
    }

    public TreeNode findLCA(TreeNode root, int p, int q) {
        if(root == null) return root;
        map.put(root.val, root);
        if(root.left != null){
            parent.put(root.left, root);
        }
        if(root.right != null){
            parent.put(root.right, root);
        }
        if(root.val == p || root.val == q) {
            return root;
        }
        TreeNode left = findLCA(root.left, p, q);
        TreeNode right = findLCA(root.right, p, q);
        if(left != null && right != null){
            return root;
        }
        if(left != null){
            return left;
        }
        if(right != null){
            return right;
        }
        return null;
    }

    /*
    * https://leetcode.com/problems/binary-tree-upside-down/
    * 156. Binary Tree Upside Down
    * */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if(root == null || root.left == null){
            return root;
        }
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }

    /*
     * https://leetcode.com/problems/count-nodes-with-the-highest-score/
     * https://leetcode.com/problems/count-nodes-with-the-highest-score/
     * */
    public int countHighestScoreNodes(int[] parents) {
        int n = parents.length;
        LinkedList<Integer>[] tree = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new LinkedList<>();
        }
        for (int i = 1; i < n; i++) {
            int p = parents[i];
            tree[p].add(i);
        }
        long[] score = new long[n];
        dfs(tree, 0, score);
        long max = Arrays.stream(score).max().getAsLong();
        return (int)Arrays.stream(score).filter(v -> (v == max)).count();
    }

    public long dfs(LinkedList<Integer>[] list, int u, long[] score){
        long prod = 1L, sum = 1L;
        int n = list.length;
        for(int v : list[u]){
            long cnt = dfs(list, v, score);
            prod *= cnt;
            sum += cnt;
        }
        long rem = Math.max(1, n - sum);
        prod *= rem;
        score[u] = prod;
        return sum;
    }


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

    /*
     * https://leetcode.com/problems/longest-path-with-different-adjacent-characters/
     * 2246. Longest Path With Different Adjacent Characters
     * */
    int res = 0;
    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        LinkedList<Integer>[] tree = new LinkedList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new LinkedList<>();
        }
        for (int i = 1; i < n; i++) {
            int p = parent[i];
            tree[p].add(i);
        }
        dfs(tree, 0, s);
        return res;
    }

    public int dfs(LinkedList<Integer>[] tree, int root, String s){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b -a);
        for(int child : tree[root]){
            int len = dfs(tree, child, s);
            if(s.charAt(root) != s.charAt(child)){
                maxHeap.offer(len);
            }
        }
        int max = maxHeap.isEmpty() ? 0 : maxHeap.poll();
        int secondMax = maxHeap.isEmpty() ? 0 : maxHeap.poll();

        res = Math.max(res, 1 + max + secondMax);
        return max + 1;
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
