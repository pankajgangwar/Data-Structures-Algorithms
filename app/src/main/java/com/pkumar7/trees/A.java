package com.pkumar7.trees;

import com.pkumar7.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 12/August/2020
 */
class A {
    public static void main(String[] args) {
        A current = new A();
        current.increasingBST(null);
    }

    /* 1932. Merge BSTs to Create Single BST
     * https://leetcode.com/problems/merge-bsts-to-create-single-bst/
     * */
    public TreeNode canMerge(List<TreeNode> trees) {
        HashMap<Integer, TreeNode> map = new HashMap<>();
        Set<TreeNode> deletedSet = new HashSet<>();
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        for(TreeNode t : trees){
            map.putIfAbsent(t.val, t);
        }
        for (TreeNode root : trees){
            if(root.left != null && map.containsKey(root.left.val) && !deletedSet.contains(map.get(root.left.val))){
                TreeNode parent = parentMap.getOrDefault(root, null);
                if(parent != map.get(root.left.val)){
                    root.left = map.get(root.left.val);
                    parentMap.put(map.get(root.left.val), root);
                    deletedSet.add(map.get(root.left.val));
                }
            }
            if(root.right != null && map.containsKey(root.right.val) && !deletedSet.contains(map.get(root.right.val))){
                TreeNode parent = parentMap.getOrDefault(root, null);
                if(parent != map.get(root.right.val)){
                    root.right = map.get(root.right.val);
                    parentMap.put(map.get(root.right.val), root);
                    deletedSet.add(map.get(root.right.val));
                }
            }
        }
        if(deletedSet.size() != trees.size() -1){
            return null;
        }
        for(TreeNode r : trees){
            if(!deletedSet.contains(r)){
                if(isValidBST(r)){
                    return r;
                }
            }
        }
        return null;
    }

    public boolean isValidBST(TreeNode root) {
        return validateBSTRec(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean validateBSTRec(TreeNode root,long min, long max){
        if(root == null) return true;

        if(root.val <= min || root.val >= max) return false;

        return validateBSTRec(root.left, min, root.val) && validateBSTRec(root.right, root.val, max);
    }

    /*
     * https://www.geeksforgeeks.org/print-nodes-top-view-binary-tree/
     * https://www.hackerrank.com/challenges/tree-top-view/problem
     * Print top view of of Binary tree
     * */
    void topView(TreeNode root) {
        bfs(root);
    }
    class QueueNode {
        TreeNode treeNode;
        int hd;
        public QueueNode(TreeNode treeNode, int hd) {
            this.treeNode = treeNode;
            this.hd = hd;
        }
    }
    void bfs(TreeNode root){
        if(root == null) return;
        Queue<QueueNode> q = new LinkedList<>();
        q.offer(new QueueNode(root, 0));
        TreeMap<Integer, TreeNode> map = new TreeMap<>();
        map.put(0, root);
        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                QueueNode curr = q.poll();
                if(!map.containsKey(curr.hd)){
                    map.put(curr.hd, curr.treeNode);
                }
                if(curr.treeNode.left != null){
                    q.offer(new QueueNode(curr.treeNode.left, curr.hd - 1));
                }
                if(curr.treeNode.right != null) {
                    q.offer(new QueueNode(curr.treeNode.right, curr.hd + 1));
                }
            }
        }
        for(Map.Entry<Integer, TreeNode> entry : map.entrySet()) {
            System.out.print(entry.getValue().val);
            System.out.print(" ");
        }
    }

    /**
     * 897. Increasing Order Search Tree
     * https://leetcode.com/problems/increasing-order-search-tree/
     * **/
    TreeNode result, pre;
    public TreeNode increasingBST(TreeNode root) {
        //inOrder(root);
        //return result;
        return increasingBSTRec(root, null);
    }

    public TreeNode increasingBSTRec(TreeNode root, TreeNode tail){
        if(root == null) return tail;
        TreeNode r_left = increasingBSTRec(root.left, root);
        root.left = null;
        TreeNode r_right = increasingBSTRec(root.right, tail);
        root.right = r_right;
        return r_left;
    }

    public void inOrder(TreeNode root){
        if(root == null) return ;
        inOrder(root.left);
        if(result == null){
            result = root;
        }else{
            pre.right = root;
        }
        root.left = null;
        pre = root;
        inOrder(root.right);
    }

    /*
     * 440. K-th Smallest in Lexicographical Order
     * https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/
     * Revisit
     * */
    public int findKthNumber(int n, int k) {
        int curr = 1;
        k = k - 1;
        while (k > 0) {
            int steps = calSteps(n, curr, curr + 1);
            if (steps <= k) {
                curr += 1;
                k -= steps;
            } else {
                curr *= 10;
                k -= 1;
            }
        }
        return curr;
    }
    //use long in case of overflow
    public int calSteps(int n, long n1, long n2) {
        int steps = 0;
        while (n1 <= n) {
            steps += Math.min(n + 1, n2) - n1;
            n1 *= 10;
            n2 *= 10;
        }
        return steps;
    }

    /* 1145. Binary Tree Coloring Game
     * https://leetcode.com/problems/shopping-offers/
     */
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if(root == null) return false;
        if(root.val == x){
            int leftNodes = countNodes(root.left);
            int rightNodes = countNodes(root.right);
            if(leftNodes > n / 2 || rightNodes > n / 2){
                return true;
            }else if(leftNodes + rightNodes + 1 < (n + 1) /2){
                return true;
            }else{
                return false;
            }
        }
        return btreeGameWinningMove(root.left, n, x) || btreeGameWinningMove(root.right, n, x);
    }

    public int countNodes(TreeNode root){
        if(root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /* 1469. Find All The Lonely Nodes
    * https://leetcode.com/problems/find-all-the-lonely-nodes/
    * */
    public List<Integer> getLonelyNodes(TreeNode root) {
        res = new ArrayList<>();
        helper(root);
        return res;
    }

    List<Integer> res;
    public void helper(TreeNode root){
        if(root == null) return;
        if(root.left == null && root.right != null) {
            res.add(root.right.val);
        }else if(root.left != null && root.right == null) {
            res.add(root.left.val);
        }
        helper(root.left);
        helper(root.right);
    }

    /* 865. Smallest Subtree with all the Deepest Nodes
    * https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
    * */
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        ArrayDeque<TreeNode> lastLevel = new ArrayDeque<>();
        while (!queue.isEmpty()){
            int size = queue.size();
            lastLevel.clear();
            lastLevel.addAll(queue);
            while (size-- > 0){
                TreeNode curr = queue.poll();
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
        }
        if(lastLevel.size() == 1) return lastLevel.peek();
        return findLowestCommonAncestor(root, lastLevel.peekFirst(), lastLevel.peekLast());
    }

    public TreeNode findLowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2){
        if(root == null) return null;
        if(root.val == node1.val) return root;
        if(root.val == node2.val) return root;
        TreeNode left = findLowestCommonAncestor(root.left, node1, node2);
        TreeNode right = findLowestCommonAncestor(root.right, node1, node2);
        if(left == null && right == null) return null;
        if(left != null && right == null) return left;
        if(left == null && right != null) return right;
        if(left != null && right != null) return root;

        return null;
    }

    /* 919. Complete Binary Tree Inserter
    * https://leetcode.com/problems/complete-binary-tree-inserter/
    * */
    class CBTInserter {
        ArrayDeque<TreeNode> q;
        TreeNode root;
        public CBTInserter(TreeNode root) {
            q = new ArrayDeque<>();
            q.addLast(root);
            while (true){
                TreeNode curr = q.peekFirst();
                if(curr.left != null){
                    q.addLast(curr.left);
                }
                if(curr.right != null){
                    q.addLast(curr.right);
                    q.pollFirst();
                }else{
                    break;
                }
            }
            this.root = root;
        }
        public int insert(int v) {
            TreeNode first = q.peekFirst();
            if(first.left == null){
                first.left = new TreeNode(v);
                q.addLast(first.left);
                return first.val;
            }else if(first.right == null){
                first.right = new TreeNode(v);
                q.addLast(first.right);
                return first.val;
            }
            q.pollFirst();
            return insert(v);
        }

        public TreeNode get_root() {
            return root;
        }
    }

    /*
    * 662. Maximum Width of Binary Tree
    * https://leetcode.com/problems/maximum-width-of-binary-tree/
    */
    public int widthOfBinaryTree(TreeNode root) {
        if(root == null) return 0;

        Queue<TreeNode> q = new LinkedList<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        map.put(root, 1);
        q.offer(root);
        int maxWidth = 0;
        while(!q.isEmpty()){
            int size = q.size();
            int start = 0;
            int end = 0;
            for(int i = 0; i < size; i++){
                TreeNode curr = q.poll();
                if(i == 0) start = map.get(curr);
                if(i == size - 1) end = map.get(curr);
                if(curr.left != null) {
                    q.offer(curr.left);
                    map.put(curr.left, 2*map.get(curr));
                }
                if(curr.right != null) {
                    q.offer(curr.right);
                    map.put(curr.right, 2*map.get(curr) + 1);
                }
            }
            int currMax = end - start + 1;
            maxWidth = Math.max(currMax, maxWidth);
        }
        return maxWidth;
    }

    /* 814. Binary Tree Pruning
    * https://leetcode.com/problems/binary-tree-pruning/
    * */
    public TreeNode pruneTree(TreeNode root) {
        if(root == null) return root;

        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        if(root.left == null && root.right == null && root.val == 0){
            return null;
        }
        return root;
    }

    /*
    * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
    * 889. Construct Binary Tree from Preorder and Postorder Traversal
    */
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        //return helper(pre, post);
        for (int i = 0; i < post.length; i++) {
            postMap.put(post[i], i);
        }
        return dfs(pre, 0, pre.length -1, post, 0, post.length -1);
    }

    HashMap<Integer, Integer> postMap = new HashMap<>();
    public TreeNode dfs(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd){
        if(preStart > preEnd) return null;
        TreeNode root = new TreeNode(pre[preStart]);
        if(preStart == preEnd) return root;
        int len = postMap.get(pre[preStart + 1]) - postStart + 1;
        root.left = dfs(pre, preStart + 1, preStart + len, post, postStart, postMap.get(pre[preStart + 1]) );
        root.right = dfs(pre, preStart + len + 1, preEnd, post, postMap.get(pre[preStart + 1]) + 1, postEnd - 1);
        return root;
    }

    int postIndex = 0, preIndex = 0;
    public TreeNode helper(int[] pre, int[] post){
        TreeNode root = new TreeNode(pre[preIndex++]);
        if(root.val != post[postIndex]){
            root.left = helper(pre, post);
        }
        if(root.val != post[postIndex]){
            root.right = helper(pre, post);
        }
        postIndex++;
        return root;
    }

    /* 331. Verify Preorder Serialization of a Binary Tree
    * https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/
    * */
    public boolean isValidSerialization(String preorder) {
        return sol1(preorder);
    }

    public boolean sol2(String preorder){
        String[] pre = preorder.split(",");
        int need = 1;
        for(String val : pre){
            if(need == 0) return false;
            need -= " #".indexOf(val);
        }
        return need == 0;
    }

    public boolean sol1(String preorder){
        String[] pre = preorder.split(",");
        int count = 1;
        for(String node : pre){
            if(count == 0) return false;
            if(!node.equals("#")) count += 1;
            else{
                count -= 1;
            }
        }
        return count == 0;
    }

    /* 776. Split BST
    * https://leetcode.com/problems/split-bst/
    * */
    public TreeNode[] splitBST(TreeNode root, int V) {
        return helper(root, V);
    }

    public TreeNode[] helper(TreeNode root, int v){
        TreeNode[] res;
        if(root == null) {
            return new TreeNode[]{null, null};
        }
        if(root.val <= v){
            res = helper(root.right, v);
            root.right = res[0];
            res[0] = root;
        }else {
            res = helper(root.left, v);
            root.left = res[1];
            res[1] = root;
        }
        return res;
    }

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
