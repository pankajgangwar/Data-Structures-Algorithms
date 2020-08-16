package com.mission.google.dfs;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 15/August/2020
 */
class A {
    /*
    * LC : 473. Matchsticks to Square
    * https://leetcode.com/problems/matchsticks-to-square/
    * Similar: https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    * */
    public boolean makesquare(int[] nums) {
        return makesquare1(nums);
        //return canPartitionKSubsets1(nums, 4);
    }

    public boolean makesquare1(int[] nums){
        int n = nums.length;
        if(n == 0) return false;
        int total = Arrays.stream(nums).sum();
        if(total % 4 != 0) return false;
        int side = total / 4;
        Arrays.sort(nums);
        reverse(nums);
        if(nums[0] > side) return false;
        int[] bucket = new int[4];

        return helperMakeSquare(nums, 0, side, bucket);
    }

    public boolean helperMakeSquare(int[] nums, int idx, int target, int[] bucket){
        if(idx == nums.length){
            if(bucket[0] == target && bucket[1] == target && bucket[2] == target){
                return true;
            }
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if(bucket[i] + nums[idx] > target) continue;
            bucket[i] += nums[idx];
            if(helperMakeSquare(nums, idx + 1, target, bucket)) return true;
            bucket[i] -= nums[idx];
        }
        return false;
    }

    private void reverse(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i < j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    /*
    LC : 698
    https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    */
    public boolean canPartitionKSubsets(int[] nums, int k){
        int n = nums.length;
        if(n == 0) return false;
        int total = Arrays.stream(nums).sum();
        if(total % k != 0) return false;
        int side = total / k;
        Arrays.sort(nums);
        reverse(nums);
        if(nums[0] > side) return false;
        int[] bucket = new int[k];

        return helper(nums, 0, side, bucket);
    }

    public boolean helper(int[] nums, int idx, int target, int[] bucket){
        if(idx == nums.length){
            for(int x : bucket){
                if(x != target) return false;
            }
            return true;
        }
        for (int i = 0; i < bucket.length; i++) {
            if(bucket[i] + nums[idx] > target) continue;
            bucket[i] += nums[idx];
            if(helper(nums, idx + 1, target, bucket)) return true;
            bucket[i] -= nums[idx];
        }
        return false;
    }

    public boolean canPartitionKSubsets1(int[] nums, int k) {
        int n = nums.length;
        if(n == 0) return false;
        int targetSum = 0;
        int maxNum = 0;
        for(int x : nums){
            targetSum += x;
            maxNum = Math.max(maxNum, x);
        }
        if(targetSum % k != 0 || maxNum > targetSum / k){
            return false;
        }

        return helper(nums, k, new boolean[nums.length], 0, targetSum / k, 0);
    }

    public boolean helper(int[] nums, int k, boolean[] visited, int curSum, int targetSum, int nextIndex){
        if(k == 0) return true;
        if(curSum == targetSum){
            return helper(nums, k - 1, visited, 0, targetSum, 0 );
        }

        for(int i = nextIndex; i < nums.length; i++){
            if (!visited[i] && curSum + nums[i] <= targetSum) {
                visited[i] = true;
                if (helper(nums, k, visited, curSum + nums[i], targetSum, i + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }


}
