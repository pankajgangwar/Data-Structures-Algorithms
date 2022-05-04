package com.pkumar7.design;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class A {

    /* 1586. Binary Search Tree Iterator II
    * https://leetcode.com/problems/binary-search-tree-iterator-ii/
    * */
    static class BSTIterator {
        Stack<TreeNode> next = new Stack<>();
        List<Integer> prev = new ArrayList<>();
        int pos = -1;

        public BSTIterator(TreeNode root) {
            traverseLeft(root);
        }

        public void traverseLeft(TreeNode root){
            while (root != null){
                next.push(root);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return !next.isEmpty() || pos + 1 < prev.size();
        }

        public int next() {
            if(++pos == prev.size()){
                TreeNode curr = next.pop();
                traverseLeft(curr.right);
                prev.add(curr.val);
            }
            return prev.get(pos);
        }

        public boolean hasPrev() {
            return pos > 0;
        }

        public int prev() {
            return prev.get(--pos);
        }
    }
}
