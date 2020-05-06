package com.mission.google.algorithms;

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
        int nextPowerOfTwo = nextPowerOf2(input.length);
        int[] seg_tree = new int[nextPowerOfTwo * 2 - 1];
        for (int i = 0; i < seg_tree.length; i++) {
            seg_tree[i] = Integer.MAX_VALUE;
        }
        constructSumTree(input, seg_tree,0, input.length -1,0);
        //constructTree(input, seg_tree,0, input.length -1,0);
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

    public void constructTree(int[] input, int[] segTree, int low, int high, int pos){
        if(low == high){
            segTree[pos] = input[low];
            return;
        }
        int mid = (low + high)/2;
        constructTree(input, segTree, low, mid, 2*pos + 1);
        constructTree(input, segTree, mid + 1, high, 2*pos + 2);
        segTree[pos] = Math.min(segTree[2*pos + 1], segTree[2*pos + 2]);
    }

    public int getSum(int[] segTree, int n, int qs, int qe) {
        // Check for erroneous input values
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
        int output = tree.getSum(segTree, n, 1, 3);
        tree.updateValue(segTree, input, n, 1, 10);
        //int output = tree.rangeMinQuery(segTree,0,7, 0, input.length-1, 0);
        int output_new = tree.getSum(segTree, n, 1, 3);
        System.out.println("Old sum " + output + " New sum:" + output_new);
    }

    /**
     * https://leetcode.com/problems/range-sum-query-immutable/
     * 303. Range Sum Query - Immutable
     * */
    static int []arr = new int[]{-2, 0, 3, -5, 2, -1};
    public static int sumRange(int i, int j) {
        int sum = 0;
        if(i < 0 || j > arr.length){
            return sum;
        }

        for (; i <= j ; i++) {
            sum += arr[i];
        }
        System.out.print("Sum: --> " + sum);
        return sum;
    }


}
