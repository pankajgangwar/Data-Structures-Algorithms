package com.pkumar7.stack;

import java.util.Stack;

public class B {

    /* 1856. Maximum Subarray Min-Product
     * https://leetcode.com/problems/maximum-subarray-min-product/
     * */
    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        Stack<Integer> lStack = new Stack<>();
        Stack<Integer> rStack = new Stack<>();

        int[] l = new int[n];
        for (int i = 0; i < n; i++) {
            while (!lStack.isEmpty() && nums[lStack.peek()] >= nums[i]){
                lStack.pop();
            }
            l[i] = lStack.isEmpty() ? 0 : lStack.peek() + 1;
            lStack.push(i);
        }

        int[] r = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!rStack.isEmpty() && nums[rStack.peek()] >= nums[i]){
                rStack.pop();
            }
            r[i] = rStack.isEmpty() ? n-1 : rStack.peek() - 1;
            rStack.push(i);
        }
        long[] prefixsum = new long[n];
        prefixsum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixsum[i] = prefixsum[i - 1] + nums[i];
        }
        long res = 0;
        long mod = (long)1e9+7;
        for (int i = 0; i < n; i++) {
            long sum = nums[i] * (prefixsum[r[i]] - (l[i] == 0 ? 0 : prefixsum[l[i] - 1]));
            res = Math.max(res, sum);
        }
        return (int)(res % mod);
    }

    /*
    * https://leetcode.com/problems/build-binary-expression-tree-from-infix-expression/
    * 1597. Build Binary Expression Tree From Infix Expression
    * https://en.wikipedia.org/wiki/Shunting_yard_algorithm
    * */
    class NodeExp {
        char val;
        NodeExp left;
        NodeExp right;
        NodeExp() {
            this.val = ' ';
        }
        NodeExp(char val) {
            this.val = val;
        }
        NodeExp(char val, NodeExp left, NodeExp right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public NodeExp expTree(String s) {
        Stack<NodeExp> nodes = new Stack<>();
        Stack<Character> ops  = new Stack<>();
        for (char ch : s.toCharArray()) {
            if(Character.isDigit(ch)){
                nodes.push(new NodeExp(ch));
            }else if(ch == '('){
                ops.push(ch);
            }else if(ch == ')'){
                while (ops.peek() != '('){
                    nodes.push(buildNode(ops.pop(), nodes.pop(),nodes.pop()));
                }
                ops.pop();
            }else {
                while (!ops.isEmpty() && compare(ops.peek(), ch)){
                    nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
                }
                ops.push(ch);
            }
        }
        while (!ops.isEmpty()){
            nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
        }
        return nodes.peek();
    }

    private boolean compare(char ch1, char ch2) {
        if(ch1 == '(' || ch2 == ')') {
            return false;
        }
        return ch1 == '*' || ch1 == '/' || ch2 == '+' || ch2 == '-';
    }

    private NodeExp buildNode(Character ch, NodeExp left, NodeExp right) {
        return new NodeExp(ch, right, left);
    }

    /* 1628. Design an Expression Tree With Evaluate Function
    * https://leetcode.com/problems/design-an-expression-tree-with-evaluate-function/
    * */
    abstract class Node {
        public abstract int evaluate();
        // define your fields here
    };

    /**
     * This is the TreeBuilder class.
     * You can treat it as the driver code that takes the postinfix input
     * and returns the expression tree represnting it as a Node.
     */
    class TreeNode extends Node {

        int eval = 0;
        TreeNode left = null, right = null;

        public TreeNode(int val){
            this.eval = val;
        }

        @Override
        public int evaluate(){
            return eval;
        }
    }

    class TreeBuilder {
        Node buildTree(String[] postfix) {
            Stack<TreeNode> st = new Stack<>();
            int n = postfix.length;

            for(int i = 0; i < n; i++){
                if(postfix[i].equals("*")) {
                    TreeNode first = st.pop();
                    TreeNode second = st.pop();
                    TreeNode res = new TreeNode(first.eval * second.eval);
                    res.left = first;
                    res.right = second;
                    st.push(res);
                }else if(postfix[i].equals("-")){
                    TreeNode first = st.pop();
                    TreeNode second = st.pop();
                    TreeNode res = new TreeNode(second.eval - first.eval);
                    res.left = first;
                    res.right = second;
                    st.push(res);
                }else if(postfix[i].equals("/")){
                    TreeNode first = st.pop();
                    TreeNode second = st.pop();
                    TreeNode res = new TreeNode(second.eval / first.eval);
                    res.left = first;
                    res.right = second;
                    st.push(res);
                }else if(postfix[i].equals("+")){
                    TreeNode first = st.pop();
                    TreeNode second = st.pop();
                    TreeNode res = new TreeNode(first.eval + second.eval);
                    res.left = first;
                    res.right = second;
                    st.push(res);
                }else{
                    int ele = Integer.parseInt(postfix[i]);
                    TreeNode node = new TreeNode(ele);
                    st.push(node);
                }
            }
            return st.pop();
        }
    };

    /* 1541. Minimum Insertions to Balance a Parentheses String
    * https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/
    * */
    public int minInsertions(String s){
        char[] arr = s.toCharArray();
        Stack<Character> st = new Stack<>();
        int insertion = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(ch == '('){
                st.push('(');
            }else {
                if(st.isEmpty()){
                    st.push('(');
                    insertion++;
                }
                if(i + 1 < s.length() && s.charAt(i + 1) == ')') i++;
                else insertion++;
                st.pop();
            }
        }
        return insertion + st.size() * 2;
    }

    /* 1762. Buildings With an Ocean View
    * https://leetcode.com/problems/buildings-with-an-ocean-view/
    * */
    public int[] findBuildings(int[] heights) {
        Stack<int[]> st = new Stack<>();
        for(int i = 0; i < heights.length; i++){
            int h = heights[i];
            while(!st.isEmpty() && st.peek()[0] <= h){
                st.pop();
            }
            st.push(new int[]{h, i});
        }
        int h = st.size();
        int[] res = new int[h];
        for(int i = h - 1; i >= 0; i--){
            res[i] = st.pop()[1];
        }
        return res;
    }
}
