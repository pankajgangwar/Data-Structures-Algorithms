package com.pkumar7.greedy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 4/March/2021
*/

public class B {

	/* 1749. Maximum Absolute Sum of Any Subarray
	 * https://leetcode.com/problems/maximum-absolute-sum-of-any-subarray/
	 * */
	public int maxAbsoluteSum(int[] nums) {
		int sum = 0;
		int max = 0, min = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			min = Math.min(min, sum);
			max = Math.max(max, sum);
		}
		return max - min;
	}

	/*
	 * https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
	 * */
	public int maxDistance(int[] nums1, int[] nums2) {
		int max_distance = 0;
		int i = 0, j = 0;
		while (i < nums1.length && j < nums2.length){
			if(nums1[i] > nums2[j]){
				i++;
			}else{
				max_distance = Math.max(max_distance, j++ - i);
			}
		}
		return max_distance;
	}

	/*
	* https://leetcode.com/problems/maximum-score-of-a-good-subarray/
	* */
	public int maximumScore(int[] nums, int k) {
		int i = k, j = k;
		int n = nums.length;
		int res = nums[k], min = nums[k];
		while (i > 0 || j < n - 1 ){
			if(i == 0){
				j++;
			}else if(j == n - 1){
				i--;
			}else if(nums[i - 1] < nums[j + 1]){
				j++;
			}else{
				i--;
			}
			min = Math.min(min, Math.min(nums[i], nums[j]));
			res = Math.max(res, min * (j - i + 1));
		}
		return res;
	}

	/*  135. Candy
		https://leetcode.com/problems/candy/
	*/
	public int candy(int[] ratings) {
		int n = ratings.length;
		int[] left = new int[n];
		int[] right = new int[n];

		Arrays.fill(left, 1);
		Arrays.fill(right, 1);

		for (int i = 1; i < n; i++) {
			if (ratings[i] > ratings[i - 1]) {
				left[i] = left[i - 1] + 1;
			}
		}

		for (int i = n - 2; i >= 0 ; --i) {
			if (ratings[i] > ratings[i + 1]) {
				right[i] = right[i + 1] + 1;
			}
		}
		int count = 0;
		for (int i = 0; i < n; i++) {
			count += Math.max(left[i], right[i]);
		}
		return count;
	}

	public int candyBrootforce(int[] ratings) {
		int n = ratings.length;
		int[] candies = new int[n];
		boolean updated = true;
		while (updated) {
			updated = false;
			for (int i = 0; i < n; i++) {
				if (i >= 1) {
					if ( ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
						candies[i] = candies[i - 1] + 1;
						updated = true;
					}
				}
				if (i + 1 < n ) {
					if ( ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
						candies[i] = candies[i + 1] + 1;
						updated = true;
					}
				}
			}
		}
		return Arrays.stream(candies).sum();
	}
}