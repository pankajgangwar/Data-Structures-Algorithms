package com.pkumar7.binaryindexedtrees;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Pankaj Kumar on 17/November/2020
 */
class A {
    public static void main(String[] args) {
        A current = new A();
        int[] arr = new int[]{1, 3, 5};
        int[] arr1 = new int[]{1,2,3,6,5,4};
        int[] arr2 = new int[]{1,3,3,3,2,4,2,1,2};
        //int res = current.createSortedArray(new int[]{1,3,3,3,2,4,2,1,2});
        String[][] matrix = new String[][]{{"a","b"},{"c","d"}};
        String[] mat = Stream.of(matrix).flatMap(Stream::of).toArray(String[]::new);
        System.out.println("Arrays.toString(mat) = " + Arrays.toString(mat));

        /*NumArray numArray = new NumArray(arr);
        System.out.println("res = " + numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println("res = " + numArray.sumRange(0, 2));*/
    }

    /* 308. Range Sum Query 2D - Mutable
    * https://leetcode.com/problems/range-sum-query-2d-mutable/
    * */
    class NumMatrix {
        int[][] tree;
        int[][] mat;
        int m, n;
        public NumMatrix(int[][] matrix) {
            if(matrix.length == 0 || matrix[0].length == 0) return;
            m = matrix.length;
            n = matrix[0].length;
            mat = new int[m][n];
            tree = new int[m + 1][n + 1];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        //time should be O(log(m) * log(n))
        public void update(int row, int col, int val) {
            if(m == 0 || n == 0) return;
            int delta = val - mat[row][col];
            mat[row][col] = val;
            for (int i = row + 1; i <= m; i += lsb(i) ) {
                for (int j = col + 1; j <= n ; j += lsb(j)) {
                    tree[i][j] += delta;
                }
            }
        }

        //time should be O(log(m) * log(n))
        public int sumRegion(int row1, int col1, int row2, int col2) {
            if(m == 0 || n == 0) return 0;
            return sum(row2+1, col2+1) - sum(row1, col2 + 1) - sum(row2 + 1, col1)
                    + sum(row1, col1);
        }

        public int sum(int row, int col){
            int sum = 0;
            for (int i = row; i > 0 ; i -= lsb(i)) {
                for (int j = col; j > 0 ; j -= lsb(j)) {
                    sum += tree[i][j];
                }
            }
            return sum;
        }

        private int lsb(int i) {
            return i & -i;
            //return Integer.lowestOneBit(i);
        }
    }

    /* 307. Range Sum Query - Mutable
    * https://leetcode.com/problems/range-sum-query-mutable/
    * */
    static class NumArray {
        int[] tree;
        final int n;
        public NumArray(int[] nums) {
            n = nums.length;
            tree = new int[n + 1];
            for (int i = 0; i < n; i++) {
                update(i, nums[i]);
            }
        }

        public void update(int i, int newval) {
            int prevval = sumRange(i, i);
            add(i, newval - prevval);
        }

        public void add(int i, long v){
            i += 1;
            while (i < n + 1){
                tree[i] += v;
                i += lsb(i);
            }
        }

        public int sumRange(int i, int j) {
            i += 1;
            j += 1;
            if(j < i) throw new IllegalArgumentException("Make sure j >= i");
            return prefixSum(j) - prefixSum(i - 1);
        }

        private int prefixSum(int i) {
            int sum = 0;
            while (i != 0){
                sum += tree[i];
                i &= ~lsb(i); // Equivalent to , i -= lsb(i);
            }
            return sum;
        }

        private int lsb(int i) {
            return i & -i;
            //return Integer.lowestOneBit(i);
        }
    }

    /* 303. Range Sum Query - Immutable
    * https://leetcode.com/problems/range-sum-query-immutable/
    * */
    class NumArray1 {
        int[] tree;
        final int n;
        public NumArray1(int[] nums) {
            n = nums.length;
            tree = new int[n + 1];
            for (int i = 0; i < n; i++) {
                update(i, nums[i]);
            }
        }

        private void update(int i, int new_val) {
            int prev_val = sumRange(i, i);
            add(i, new_val - prev_val);
        }

        public void add(int i, long val){
            i += 1;
            while (i < n + 1){
                tree[i] += val;
                i = i + lsb(i);
            }
        }

        public int prefixSum(int i){
            int sum = 0;
            while (i != 0){
                sum += tree[i];
                i = i - lsb(i);
            }
            return sum;
        }

        public int lsb(int i){
            return Integer.lowestOneBit(i);
        }

        public int sumRange(int i, int j) {
            if(j < i) throw new IllegalArgumentException(" Make sure j >= i");
            i += 1;
            j += 1;
            return prefixSum(j) - prefixSum(i - 1);
        }
    }

    /* 1649. Create Sorted Array through Instructions
     * https://leetcode.com/problems/create-sorted-array-through-instructions/
     * */
    public int createSortedArray(int[] nums) {
        int mod = (int)1e9 + 7;
        int n = nums.length;
        int max = Arrays.stream(nums).max().getAsInt();
        int[] A = new int[max + 10];
        long res = 0;
        FenwickTree tree = new FenwickTree(A);
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            int left = tree.sumRange(0, curr - 1);
            int right = tree.sumRange(curr + 1, max + 1);
            res += Math.min(left, right);
            res = res % mod;
            tree.update(curr, 1);
        }
        return (int)res;
    }
    class FenwickTree{
        int[] tree;
        int A[];
        int arr[];
        public FenwickTree(int[] A){
            this.A = A;
            arr = new int[A.length];
            tree = new int[A.length + 1];
            for (int i = 0; i < A.length; i++) {
                update(i, A[i]);
            }
        }
        private void update(int i, int val) {
            arr[i] += val;
            i++;
            while (i < tree.length){
                tree[i] += val;
                //i += (i & -i);
                i += lsb(i);
            }
        }
        public int sumRange(int i, int j){
            return pre(j + 1) - pre(i);
        }
        public int pre(int i){
            int sum = 0;
            while (i != 0){
                sum += tree[i];
                //i-=(i&-i);
                i &= ~lsb(i); // Equivalent to , i -= lsb(i);
            }
            return sum;
        }

        private int lsb(int i) {
            //return i & -i;
            return Integer.lowestOneBit(i);
        }
    }
}
