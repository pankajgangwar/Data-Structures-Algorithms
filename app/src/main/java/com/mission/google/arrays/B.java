package com.mission.google.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 05/September/2020
 */
class B {

    /* 1582. Special Positions in a Binary Matrix
     * https://leetcode.com/problems/special-positions-in-a-binary-matrix/
     * */
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] rows = new int[m];
        int[] cols = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 1){
                    rows[i]++;
                    cols[j]++;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 1 && (rows[i] == 1 && cols[j] == 1)) {
                    res++;
                }
            }
        }
        return res;
    }

    /* 41. First Missing Positive
    * https://leetcode.com/problems/first-missing-positive/
    */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++){
            if(nums[i] <= 0 || nums[i] > n){
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if(nums[i] > n + 1){
                continue;
            }
            int x = Math.abs(nums[i]) -1 ;
            if( x < n && nums[x] > 0 ){
                nums[x] = -nums[x];
            }
        }

        for(int i = 0; i < n; i++){
            if(nums[i] > 0){
                return i+1;
            }
        }
        return n+1;
    }

    public int firstMissingPositive1(int[] nums){
        int[] arr = findMissing(nums);
        int size = arr.length;
        for(int i = 0; i < size; i++){
            int x = Math.abs(arr[i]);
            if( x-1 < size && arr[x-1] > 0 ){
                arr[x-1] = -arr[x-1];
            }
        }

        for(int i = 0; i < size; i++){
            if(arr[i] > 0){
                return i+1;
            }
        }
        return size+1;
    }

    public int[] findMissing(int arr[]){
        int shift = segregate(arr);
        int arr2[] = new int[arr.length - shift];
        for(int i = shift, j = 0; i < arr.length; i++, j++ ){
            arr2[j] = arr[i];
        }
        return arr2;
    }

    public int segregate(int arr[]){
        int temp = 0;
        int j = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i] <= 0){
                temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
                j++;
            }
        }
        return j;
    }

    /**
     * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
     * 448. Find All Numbers Disappeared in an Array
     * **/
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i] ) - 1;
            if(nums[val] > 0){
                nums[val] = -nums[val];
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                res.add(i + 1);
            }
        }
        return res;
    }
}
