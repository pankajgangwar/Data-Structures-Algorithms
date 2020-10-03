package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OctoberW1 {

    public static void main(String[] args) {
        OctoberW1 w1 = new OctoberW1();
        int N = 3;
        List<TreeNode> []memo = new ArrayList[N + 1];
        Arrays.fill(memo, null);
        w1.allPossibleFBTMemo(N, memo);

        w1.findLeftMostNodeDFS(null, 1);
    }

    /**
     * https://leetcode.com/problems/all-possible-full-binary-trees/
     * */
    public List<TreeNode> allPossibleFBTRec(int N) {
        List<TreeNode> res = new ArrayList<>();
        if(N == 1){
            res.add(new TreeNode(0));
            return res;
        }

        for (int leftNum = 1; leftNum <= N -1; leftNum += 2) {
            List<TreeNode> left = allPossibleFBTRec(leftNum);
            List<TreeNode> right = allPossibleFBTRec(N - leftNum - 1);
            for (TreeNode currLeft : left) {
                for (TreeNode currRight : right) {
                    TreeNode cur = new TreeNode(0);
                    cur.left = currLeft;
                    cur.right = currRight;
                    res.add(cur);
                }
            }
        }
        return res;
    }

    public List<TreeNode> allPossibleFBTMemo(int N, List<TreeNode> []memo) {
        List<TreeNode> res = new ArrayList<>();
        if(N == 1){
            res.add(new TreeNode(0));
            return res;
        }

        if(memo[N] != null){
            return memo[N];
        }

        for (int leftNum = 1; leftNum <= N -1; leftNum += 2) {
            List<TreeNode> left = allPossibleFBTMemo(leftNum, memo);
            List<TreeNode> right = allPossibleFBTMemo(N - leftNum - 1, memo);
            for (TreeNode currLeft : left) {
                for (TreeNode currRight : right) {
                    TreeNode cur = new TreeNode(0);
                    cur.left = currLeft;
                    cur.right = currRight;
                    res.add(cur);
                }
            }
        }
        memo[N] = res;
        return res;
    }

    /*** https://leetcode.com/problems/find-bottom-left-tree-value/
     * 513. Find Bottom Left Tree Value
     *
     *     2
     *    / \
     *   1   3
     *
     *         1
     *        / \
     *       2   3
     *      /   / \
     *     4   5   6
     *        /
     *       7
     *
     * */
    public int findBottomLeftValueBFS(TreeNode root) {
        Queue<TreeNode> mQueue = new LinkedList<>();
        mQueue.offer(root);
        int lastNode = 0;
        while (!mQueue.isEmpty()){
            int size = mQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = mQueue.poll();
                if(curr.left != null){
                    mQueue.offer(curr.left);
                }
                if(curr.right != null){
                    mQueue.offer(curr.right);
                }
                if(i == 0){
                    lastNode = curr.val;
                }
            }
        }
        return lastNode;
    }

    /**
     * https://leetcode.com/problems/find-bottom-left-tree-value/
     * https://leetcode.com/problems/find-bottom-left-tree-value/discuss/98779/Right-to-Left-BFS-(Python-%2B-Java)
     * **/
    public int findBottomLeftValue(TreeNode root){
        findLeftMostNodeDFS(root, 0);
        return result.leftMostNode;
    }

    public int findBottomLeftValueBFSEasy(TreeNode root){
        Queue<TreeNode> mQueue = new LinkedList<>();
        mQueue.offer(root);
        while (!mQueue.isEmpty()){
            root = mQueue.poll();
            if(root.right != null){
                mQueue.offer(root.right);
            }
            if(root.left != null){
                mQueue.offer(root.left);
            }
        }
        return root.val;
    }

    class Result {
        int leftMostNode = 0;
        int maxDepth = 0;
    }

    Result result = new Result();

    public void findLeftMostNodeDFS(TreeNode root, int currDepth){

        if(result.maxDepth < currDepth && root != null){
            result.leftMostNode = root.val;
            result.maxDepth++;
        }

        if(root == null) {
            return;
        }

        if(root.left != null) {
            findLeftMostNodeDFS(root.left, currDepth + 1);
        }
        if(root.right != null) {
            findLeftMostNodeDFS(root.right, currDepth + 1);
        }
    }


}
