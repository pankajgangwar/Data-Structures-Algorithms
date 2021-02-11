package com.pkumar7.datastructures;

import java.util.Arrays;

public class SegmentTree {

    /**
     *https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/SegmentTreeMinimumRangeQuery.java
     * https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
     */

    public int nextPowerOf2(int num){
        if(num ==0){
            return 1;
        }
        if(num > 0 && (num & (num-1)) == 0){
            return num;
        }
        while((num & (num-1)) > 0){
            num = num & (num-1);
        }
        return num<<1;
    }

    public int[] createSegmentTree(int[] input){
        /*int n = input.length;
        int x = (int)Math.ceil(Math.log(n)/ Math.log(2)); // Height of tree
        int max_size = 2 *(int)Math.pow(2, x) -1;
        int[] tree = new int[max_size];*/
        int nextPowerOfTwo = nextPowerOf2(input.length);
        int[] tree = new int[nextPowerOfTwo * 2 - 1];
        Arrays.fill(tree, Integer.MIN_VALUE);
        //constructSumTree(input, seg_tree,0, input.length -1,0);
        //constructMinTree(input, seg_tree,0, input.length -1,0);
        constructMaxTree(input,tree,0, input.length -1,0);
        return tree;
    }

    public void constructSumTree(int[] input, int[] segTree, int low, int high, int pos){
        if(low == high){
            segTree[pos] = input[low];
            return;
        }
        int mid = (low + high)/2;
        constructSumTree(input, segTree, low, mid, 2*pos + 1);
        constructSumTree(input, segTree, mid + 1, high, 2*pos + 2);
        segTree[pos] = segTree[2*pos + 1] + segTree[2*pos + 2];
    }

    public int constructMaxTree(int[] input,int[] tree, int low, int high, int pos){
        if(low == high){
            tree[pos] = input[low];
            return tree[pos];
        }
        int mid = low + (high - low)/2;
        tree[pos] = Math.max(constructMaxTree(input, tree, low, mid, pos*2 + 1),
                constructMaxTree(input, tree, mid + 1, high, pos*2 + 2));
        return tree[pos];
    }

    public void constructMinTree(int[] input, int[] segTree, int low, int high, int pos){
        if(low == high){
            segTree[pos] = input[low];
            return;
        }
        int mid = (low + high)/2;
        constructMinTree(input, segTree, low, mid, 2*pos + 1);
        constructMinTree(input, segTree, mid + 1, high, 2*pos + 2);
        segTree[pos] = Math.min(segTree[2*pos + 1], segTree[2*pos + 2]);
    }

    public int getSum(int[] segTree, int n, int qs, int qe) {
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return getSumUtil(segTree,0, n - 1, qs, qe, 0);
    }

    int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    int getSumUtil(int[] segTree, int ss, int se, int qs, int qe, int si) {
        // If segment of this node is a part of given range, then return
        // the sum of the segment
        if (qs <= ss && qe >= se)
            return segTree[si];

        // If segment of this node is outside the given range
        if (se < qs || ss > qe)
            return 0;

        // If a part of this segment overlaps with the given range
        int mid = getMid(ss, se);
        return getSumUtil(segTree, ss, mid, qs, qe, 2 * si + 1) +
                getSumUtil(segTree, mid + 1, se, qs, qe, 2 * si + 2);
    }

    public int getMax(int[] segTree, int n, int qs, int qe) {
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return rangeMaxQuery(segTree,0, n - 1, qs, qe, 0);
    }

    public int rangeMaxQuery(int[] tree, int low, int high, int qLow, int qHigh, int pos){
        if(qLow <= low  && qHigh >= high){//total overlap
            return tree[pos];
        }
        if(qLow > high || qHigh < low) { //No overlap
            return -1;
        }
        int mid = (low + high) / 2;
        return Math.max(rangeMaxQuery(tree, low, mid, qLow, qHigh, 2*pos + 1),
                rangeMaxQuery(tree, mid+1 , high, qLow, qHigh, 2*pos + 2));
    }

    public int rangeMinQuery(int[] segTree, int qLow, int qHigh, int low, int high, int pos){
        if(qLow <= low && qHigh >= high){//total overlap
            return segTree[pos];
        }
        if(qLow > high || qHigh < low) { //No overlap
            return Integer.MAX_VALUE;
        }
        int mid = (low + high) / 2;
        return Math.min(rangeMinQuery(segTree, qLow, qHigh, low, mid, 2*pos + 1),
                rangeMinQuery(segTree, qLow, qHigh, mid + 1, high, 2*pos + 2));
    }

    void updateValueUtil(int[] segTree, int ss, int se, int i, int diff, int pos) {
        if (i < ss || i > se)
            return;
        segTree[pos] = segTree[pos] + diff;
        if (se != ss) {
            int mid = getMid(ss, se);
            updateValueUtil(segTree, ss, mid, i, diff, 2 * pos + 1);
            updateValueUtil(segTree, mid + 1, se, i, diff, 2 * pos + 2);
        }
    }

    void updateValue(int[] segTree, int arr[], int n, int i, int new_val) {
        // Check for erroneous input index
        if (i < 0 || i > n - 1) {
            System.out.println("Invalid Input");
            return;
        }
        int diff = new_val - arr[i];
        arr[i] = new_val;
        updateValueUtil(segTree, 0, n - 1, i, diff, 0);
    }

    public static void main(String[] args){
        SegmentTree tree = new SegmentTree();
        int []input = {0, 3, 4, 2, 1, 6, -1};
        int n = input.length;
        int[] segTree = tree.createSegmentTree(input);
        int output = tree.getMax(segTree, n, 1, 3);
        tree.updateValue(segTree, input, n, 1, 10);
        //int output = tree.rangeMinQuery(segTree,0,7, 0, input.length-1, 0);
        int output_new = tree.getMax(segTree, n, 1, 3);
        System.out.println("Old max " + output + " New max:" + output_new);
    }

    /**
     * https://leetcode.com/problems/range-sum-query-immutable/
     * 303. Range Sum Query - Immutable
     * */
    class NumArray {
        int []arr;
        int[] segTree;
        int n;
        public NumArray(int[] nums) {
            n = nums.length;
            arr = nums;
            if(n <= 0){
                return ;
            }
            segTree = createSegmentTree(nums);
        }
        public int nextPowerOf2(int num){
            if(num ==0){
                return 1;
            }
            if(num > 0 && (num & (num-1)) == 0){
                return num;
            }
            while((num & (num-1)) > 0){
                num = num & (num-1);
            }
            return num<<1;
        }

        public int[] createSegmentTree(int[] input){
            int nextPowerOfTwo = nextPowerOf2(input.length);
            int[] seg_tree = new int[nextPowerOfTwo * 2 - 1];
            for (int i = 0; i < seg_tree.length; i++) {
                seg_tree[i] = Integer.MAX_VALUE;
            }
            constructSumTree(input, seg_tree,0, input.length -1,0);
            return seg_tree;
        }

        public void constructSumTree(int[] input, int[] segTree, int low, int high, int pos){
            if(low == high){
                segTree[pos] = input[low];
                return;
            }
            int mid = (low + high)/2;
            constructSumTree(input, segTree, low, mid, 2*pos + 1);
            constructSumTree(input, segTree, mid + 1, high, 2*pos + 2);
            segTree[pos] = segTree[2*pos + 1] + segTree[2*pos + 2];
        }

        int getMid(int s, int e) {
            return s + (e - s) / 2;
        }

        public int getSum(int[] segTree, int n, int qs, int qe) {
            if (qs < 0 || qe > n - 1 || qs > qe) {
                System.out.println("Invalid Input");
                return -1;
            }
            return getSumUtil(segTree,0, n - 1, qs, qe, 0);
        }

        int getSumUtil(int[] segTree, int ss, int se, int qs, int qe, int si) {
            if (qs <= ss && qe >= se)
                return segTree[si];

            if (se < qs || ss > qe)
                return 0;
            int mid = getMid(ss, se);
            return getSumUtil(segTree, ss, mid, qs, qe, 2 * si + 1) +
                    getSumUtil(segTree, mid + 1, se, qs, qe, 2 * si + 2);
        }

        public int sumRange(int i, int j) {
            if(n <= 0){
                return 0;
            }
            int sum = getSum(segTree, n, i, j);
            return sum;
        }
    }


}
