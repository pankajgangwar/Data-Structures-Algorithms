package com.pkumar7.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 10/December/2020
 */
class C {
    public static void main(String[] args) {

    }

    /* 1877. Minimize Maximum Pair Sum in Array
     * https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
     * */
    public int minPairSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int i = 0, j = n - 1;
        int max = 0;
        while (i < n && j >= 0 ){
            max = Math.max(nums[i] + nums[j], max);
            i++;
            j--;
        }
        return max;
    }

    /* 1806. Minimum Number of Operations to Reinitialize a Permutation
     * https://leetcode.com/problems/minimum-number-of-operations-to-reinitialize-a-permutation/
     * */
    public int reinitializePermutation(int n) {
        int[] perm = new int[n];
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        Arrays.fill(arr, 0);
        int[] orgPerm = perm.clone();
        int count = 0;
        do{
            Arrays.fill(arr, 0);
            for (int i = 0; i < n; i++) {
                if(i%2==0){
                    arr[i] = perm[i / 2];
                }else{
                    arr[i] = perm[n / 2 + (i - 1) / 2];
                }
            }
            perm = Arrays.copyOf(arr, arr.length);
            System.out.println(Arrays.toString(perm));
            count++;
        } while (!Arrays.equals(perm, orgPerm));

        return count;
    }

    /* 1710. Maximum Units on a Truck
     * https://leetcode.com/problems/maximum-units-on-a-truck/
     * */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a,b) -> -a[0] + b[0]);
        int ans = 0;
        for (int i = 0; i < boxTypes.length; i++) {
            int boxes = boxTypes[i][0];
            int unit = boxTypes[i][1];
            if(truckSize == 0) break;
            if(truckSize >= boxes){
                ans += (unit * boxes);
                truckSize -= boxes;
            }else{
                int rem = truckSize;
                ans += (unit * rem);
                truckSize = 0;
            }
        }
        return ans;
    }

    /* Minimum Number of Platforms Required for a Railway/Bus Station
     * https://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station/
     * */
    public int findPlatform(int arr[], int dep[], int n) {
        Arrays.sort(arr);
        Arrays.sort(dep);
        int i = 0, j = 0;
        int stations_needed = 0;
        int max_station = 0;
        while(i < n && j < n){
            if(arr[i] <= dep[j]){
                i++;
                max_station += 1;
                stations_needed = Math.max(stations_needed, max_station);
            }else if(arr[i] >= dep[j]){
                j++;
                max_station -= 1;
            }
        }
        return stations_needed;
    }


    /* 1695. Maximum Erasure Value
     * https://leetcode.com/problems/maximum-erasure-value/
     * */
    public int maximumUniqueSubarray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int maxScore = 0;
        int n = nums.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        for (int i = 0, j = 0; i < nums.length; i++) {
            j = Math.max(j, map.getOrDefault(nums[i], -1) + 1);
            maxScore = Math.max(maxScore, prefix[i + 1] - prefix[j]);
            map.put(nums[i], i);
        }
        return maxScore;
    }

    public int maximumUniqueSubarrayI(int[] arr) {
        int i = 0, j = 1;
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(arr[0]);
        int sum = arr[0];
        int maxsum = sum;
        while (i < arr.length - 1 && j < arr.length) {
            if (!set.contains(arr[j])) {
                sum = sum + arr[j];
                maxsum = Math.max(sum,maxsum);
                set.add(arr[j++]);
            } else {
                sum -= arr[i];
                set.remove(arr[i++]);
            }
        }
        return maxsum;
    }

    /* https://leetcode.com/problems/find-pivot-index/
     * 724. Find Pivot Index
     * */
    public int pivotIndexIII(int[] nums) {
        int sum = 0, left = 0;
        for (int i = 0; i < nums.length; i++) sum += nums[i];

        for (int i = 0; i < nums.length; i++) {
            if (i != 0) left += nums[i - 1];
            if (sum - left - nums[i] == left) return i;
        }
        return -1;
    }

    public int pivotIndex(int[] nums){
        int sum = 0, left = 0;
        int n = nums.length;
        int[] prefix = new int[n + 1];
        for(int i = 0; i < nums.length; i++){
            prefix[i + 1] = prefix[i] + nums[i];
        }
        for(int i = 1; i <= n ; i++){
            if((prefix[n] - prefix[i]) == (prefix[i] - nums[i-1])){
                return i - 1;
            }
        }
        return -1;
    }

    public int pivotIndexII(int[] nums) {
        if(nums.length < 2) {
            return -1;
        }
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];

        left[0] = nums[0];
        for(int i = 1; i < n; i++){
            left[i] = nums[i] + left[i-1];
        }

        right[n-1] = nums[n-1];
        for(int i = n - 2; i >= 0 ; --i){
            right[i] = nums[i] + right[i+1];
        }

        for(int i = 0; i < n; i++){
            if(left[i] == right[i]){
                return i;
            }
        }
        return -1;
    }
    public int pivotIndexI(int[] nums) {
        if(nums.length < 2) {
            return -1;
        }
        int i = 0;
        int j = nums.length -1;
        int sumFromEnd = 0;
        int sumFromStart = 0;
        while(j >= 1) {
            sumFromEnd+= nums[j];
            j--;
        }
        if(sumFromStart == sumFromEnd) {
            return i;
        }
        for(i = 0; i < nums.length - 1; i++) {
            sumFromStart+= nums[i];
            sumFromEnd-= nums[i+1];
            if(sumFromStart == sumFromEnd) {
                return i+1;
            }
        }
        return -1;
    }

    /* 1217. Minimum Cost to Move Chips to The Same Position
     * https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/
     * */
    public int minCostToMoveChips(int[] position) {
        int evens = 0, odds = 0;
        for (int i = 0; i < position.length; i++) {
            if(position[i] % 2 == 0){
                evens++;
            }else{
                odds++;
            }
        }
        return Math.min(evens, odds);
    }

    /* 1014. Best Sightseeing Pair
     * https://leetcode.com/problems/best-sightseeing-pair/
     * */
    public int maxScoreSightseeingPair(int[] arr) {
        int maxscore = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                maxscore = Math.max(maxscore, arr[i] + arr[j] - (j - i));
            }
        }
        return maxscore;
    }

    /* 1685. Sum of Absolute Differences in a Sorted Array
     * https://leetcode.com/problems/sum-of-absolute-differences-in-a-sorted-array/
     * */
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int[] res = new int[nums.length];
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        int sub = 0;
        for (int i = 0; i < nums.length; i++) {
            sum -= nums[i];
            res[i] = (sub  + (i * nums[i] ) - ((n - i - 1) * nums[i]) + sum );
            sub -= nums[i];
        }
        return res;
    }

    /*
     * 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
     * https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
     * */
    public int minPartitions(String str) {
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            max = Math.max(max, str.charAt(i) - '0');
        }
        return max;
    }

    public int solution(String str){
        StringBuilder out = new StringBuilder(str);
        int res = 0;
        while (out.length() > 0){
            StringBuilder tempB = new StringBuilder(out);
            StringBuilder mB = new StringBuilder();
            StringBuilder tempres = new StringBuilder();
            for (int i = tempB.length() -1; i >= 0; --i) {
                int a = tempB.charAt(i) - '0';
                if(a != 0){
                    mB.append(1);
                }else{
                    mB.append(a);
                }
            }
            mB.reverse();
            boolean isZero = true;
            for (int i = 0; i < mB.length(); i++) {
                int a = out.charAt(i) - '0';
                int b = mB.charAt(i) - '0';
                if(a - b != 0){
                    isZero = false;
                }
                tempres.append(a - b);
            }
            if(isZero){
                tempres.setLength(0);
            }
            out = tempres;
            res++;
        }
        return res;
    }

    /* 1380. Lucky Numbers in a Matrix
     * https://leetcode.com/problems/lucky-numbers-in-a-matrix/
     * */
    public List<Integer> luckyNumbers (int[][] matrix) {
        int[] rows = new int[matrix.length];
        int[] cols = new int[matrix[0].length];
        List<Integer> res = new ArrayList<>();
        List<Integer> maxInCols = new ArrayList<>();
        for (int i = 0; i < matrix[0].length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                max = Math.max(max, matrix[j][i]);
            }
            maxInCols.add(max);
        }
        for (int i = 0; i < matrix.length; i++) {
            int min = Arrays.stream(matrix[i]).min().getAsInt();
            if(maxInCols.contains(min)){
                res.add(min);
            }
        }
        return res;
    }
}
