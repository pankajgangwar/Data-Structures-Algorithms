package com.pkumar7.segmenttrees;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pankaj Kumar on 01/February/2021
 */
class A {

    /* 327. Count of Range Sum
     * https://leetcode.com/problems/count-of-range-sum/
     * */
    public int countRangeSum(int[] nums, int lower, int upper) {
        Set<Long> sets = new HashSet<>();
        int n = nums.length;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (long) nums[i];
            sets.add(sum);
        }
        Long[] arr = sets.toArray(new Long[0]);
        Arrays.sort(arr);
        SegTreeNode root = buildSegmentTree(arr, 0, arr.length - 1);
        int count = 0;
        /*
         * Let sum[i] be the prefix sum of nums[..i]. Then the range-sum of [i, j] is equal to sum[j] - sum[i - 1].
         * We enumerate all i's. For any fixed i, we need to count the number of j's satisfying lower ≤ sum[j] - sum[i - 1] ≤ upper
         * i.e., lower + sum[i - 1] ≤ sum[j] ≤ upper + sum[i - 1], for all i ≤ j ≤ n.
         * */
        for (int i = n - 1; i >= 0; --i) {
            updateSegmentTree(root, sum);
            sum -= (long) nums[i];
            count += getCount(root, (long) (lower + sum), (long) (upper + sum));
        }
        return count;
    }

    private int getCount(SegTreeNode root, long min, long max) {
        if (root == null) return 0;
        if (min > root.max || max < root.min) return 0;
        if (min <= root.min && max >= root.max) return root.count;
        return getCount(root.left, min, max) + getCount(root.right, min, max);
    }

    public void updateSegmentTree(SegTreeNode root, long value) {
        if (root == null) return;
        if (value >= root.min && value <= root.max) {
            root.count++;
            updateSegmentTree(root.left, value);
            updateSegmentTree(root.right, value);
        }
    }

    private SegTreeNode buildSegmentTree(Long[] arr, int low, int high) {
        if (low > high) return null;
        SegTreeNode root = new SegTreeNode(arr[low], arr[high]);
        if (low == high) return root;
        int mid = (low + high) / 2;
        root.left = buildSegmentTree(arr, low, mid);
        root.right = buildSegmentTree(arr, mid + 1, high);
        return root;
    }

    class SegTreeNode {
        SegTreeNode left, right;
        long min, max;
        int count;

        public SegTreeNode(long min, long max) {
            this.min = min;
            this.max = max;
        }
    }
}
