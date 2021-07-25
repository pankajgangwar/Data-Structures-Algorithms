package com.pkumar7.utils;

public class Utils {

    public static boolean nextPermutation(int[] nums) {
        int p = -1;
        int q = -1;

        for (int i = nums.length -1; i > 0 ; i--) {
            if(nums[i -1] < nums[i]){
                p = i-1;
                break;
            }
        }
        if(p == -1){
            return false;
        }
        for (int i = nums.length -1; i >= 0 ; i--) {
            if(nums[p] < nums[i]){
                q = i;
                break;
            }
        }
        swap(nums, p, q);
        reverse(nums, p + 1, nums.length -1);
        return true;
    }

    private static void reverse(int[] nums, int first, int last) {
        while (last > first){
            swap(nums, last, first);
            last--;
            first++;
        }
    }

    private static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
