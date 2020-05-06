package com.mission.google.algorithms;

import java.util.TreeSet;

public class KadanesAlgorithm {

	/**
	 * https://en.wikipedia.org/wiki/Maximum_subarray_problem
	 * https://www.youtube.com/watch?v=86CQq3pKSUw
	 * https://leetcode.com/problems/maximum-subarray/
	 * */
	public static void main(String[] args) {
		KadanesAlgorithm algorithm = new KadanesAlgorithm();
		int[] arr = new int[] {-2, -3, 4, -1, -2, 1, 5, -3};
		int max_subarr_sum = algorithm.maxSumSubArray(arr);
		System.out.println(max_subarr_sum);
	}
	
	public int maxSumSubArray(int [] arr) {
		int max_global = arr[0];
		int max_curr = arr[0];
		
		for(int i = 1; i < arr.length ; i++) {
			max_curr = Math.max(arr[i], max_curr + arr[i]);
			max_global = Math.max(max_curr, max_global);
		}
		return max_global;
	}

	/**
	 * https://stackoverflow.com/questions/39084147/largest-sum-of-contiguous-subarray-no-larger-than-k
	 * Max Sub-array sum no larger than k
	 * */
	public int maxSumSubArrayNoLargerThank(int arr[], int k){
		int max = Integer.MIN_VALUE;
		int sumj = 0;
		TreeSet<Integer> treeSet = new TreeSet<>();
		treeSet.add(0);
		for (int i = 0; i < arr.length; i++) {
			sumj += arr[i];
			Integer gap = treeSet.ceiling( sumj - k);
			if(gap != null){
				max = Math.max(max, sumj - gap);
			}
			treeSet.add(sumj);
		}
		return max;
	}
}

