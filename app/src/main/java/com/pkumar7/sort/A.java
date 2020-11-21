package com.pkumar7.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pankaj Kumar on 08/November/2020
 */
class A {
    public static void main(String[] args) {
        A current = new A();
        current.countRangeSum(new int[]{-2, 5, -1}, -2, 2);
    }

    /* 327. Count of Range Sum
     * https://leetcode.com/problems/count-of-range-sum/
     * */
    int count = 0;
    int lower, upper;
    public int countRangeSum(int[] nums, int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < nums.length; i++) {
            sums[i + 1] = (long)nums[i] + sums[i];
        }
        long[] temp = new long[n + 1];
        mergesort(sums, 0, n, temp);
        return count;
    }

    private void mergesort(long[] sums, int start, int end, long[] temp) {
        if(start >= end){
            return;
        }
        int mid = start + ((end - start)  / 2);
        mergesort(sums, start, mid, temp);
        mergesort(sums, mid + 1, end, temp);
        merge(sums, start, mid, end, temp);
    }

    private void merge(long[] sums, int start, int mid, int end, long[] temp) {
        int right = mid + 1;
        int index = start;
        int low = mid + 1, high = mid + 1;
        for (int left = start; left <= mid ; left++) {
            while (low <= end && sums[low] - sums[left] < lower){
                low++;
            }
            while (high <= end && sums[high] - sums[left] <= upper){
                high++;
            }
            while (right <= end && sums[right] < sums[left]){
                temp[index++] = sums[right++];
            }
            temp[index++] = sums[left];
            count += high - low;
        }
        while (right <= end){
            temp[index++] = sums[right++];
        }
        for (int i = start; i <= end ; i++) {
            sums[i] = temp[i];
        }
    }

    /* 1630. Arithmetic Subarrays
     * https://leetcode.com/problems/arithmetic-subarrays/
     * */
    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> res = new ArrayList<>();
        int m = l.length;
        for (int i = 0; i < m; i++) {
            int start = l[i];
            int end = r[i];
            int[] temp = new int[end - start + 1];
            for(int x = 0, k = start; k <= end; k++, x++){
                temp[x] = nums[k];
            }
            Arrays.sort(temp);
            boolean found = true;
            for (int j = 2; j < temp.length; j++) {
                if(temp[j] - temp[j - 1] != temp[1] - temp[0]){
                    found = false;
                    break;
                }
            }
            res.add(found);
        }
        return res;
    }
}
