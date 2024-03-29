package com.pkumar7.binaryindexedtrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Created by Pankaj Kumar on 17/November/2020
 */
class A {
    public static void main(String[] args) {
        A current = new A();
    }

    /*
    * https://leetcode.com/problems/range-frequency-queries/
    * 2080. Range Frequency Queries
    * */
    static class RangeFreqQuery {
        HashMap<Integer, TreeSet<Integer>> mapToTreeSet = new HashMap<>();
        HashMap<Integer, List<Integer>> mapToList = new HashMap<>();
        public RangeFreqQuery(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                mapToTreeSet.putIfAbsent(arr[i], new TreeSet<>());
                mapToTreeSet.get(arr[i]).add(i);

                mapToList.putIfAbsent(arr[i], new ArrayList<>());
                mapToList.get(arr[i]).add(i);
            }
        }

        public int query(int left, int right, int value) {
            if(!mapToTreeSet.containsKey(value)) return 0;
            TreeSet<Integer> indices = mapToTreeSet.get(value);
            List<Integer> list = mapToList.get(value);

            Integer lower = indices.ceiling(left);
            Integer higher = indices.floor(right);
            if(lower == null || higher == null){
                return 0;
            }
            if(higher < lower) return 0;
            return Collections.binarySearch(list, higher) - Collections.binarySearch(list, lower) + 1;
        }
    }

    /* HackerRank problems
     * https://leetcode.com/discuss/interview-question/820003/Sorted-Sums/677325
     * */
    static class BIT {
        int size = 0;
        long[] tree;
        public BIT(int n){
            size = n;
            tree = new long[n];
        }
        private void update(int i, int delta) {
            while(i < size){
                tree[i] += delta;
                i += lsb(i);
            }
        }
        public long add(int i){
            long s = 0;
            while(i > 0){
                s += tree[i];
                i -= lsb(i);
            }
            return s;
        }
        public int lsb(int i){
            return i & -i;
        }
    }
    public int sortedSum(List<Integer> a) {
        int n = (int)1e6 + 1, m = (int)1e9 + 7;
        int len = a.size();
        BIT prefix = new BIT(n);
        BIT postFix = new BIT(n);
        long fn = a.get(0);
        long ans = fn, totalSum = fn;
        prefix.update(a.get(0), 1);
        postFix.update(a.get(0), a.get(0));
        for (int i = 1; i < len; i++) {
            int rank = (int)prefix.add(a.get(i)) + 1;
            long sumGreaterThanI = totalSum - postFix.add(a.get(i));
            fn = (fn + rank * 1l * a.get(i) + sumGreaterThanI) % m;
            ans = (ans + fn) % m;
            prefix.update(a.get(i), 1);
            postFix.update(a.get(i), a.get(i));
            totalSum += a.get(i);
        }
        return (int)ans;
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
    class NumArray {
        FenwickTree tree;
        public NumArray(int[] nums) {
            int n = nums.length;
            tree = new FenwickTree(nums, n);
        }

        public void update(int index, int val) {
            tree.update(index, val);
        }

        public int sumRange(int left, int right) {
            return tree.rangeSum(left, right);
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
        long res = 0;
        FenwickTree tree = new FenwickTree(nums, nums.length);
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            int left = tree.rangeSum(0, curr - 1);
            int right = tree.rangeSum(curr + 1, max + 1);
            res += Math.min(left, right);
            res = res % mod;
            tree.update(curr, 1);
        }
        return (int)res;
    }
}
