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

	/* 1958. Check if Move is Legal
	 * https://leetcode.com/problems/check-if-move-is-legal/
	 * */
	public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
		int rows = board.length;
		int cols = board[0].length;

		int[][] dirs = new int[][]{{0, 1}, {1, 1}, {1, 0}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
		for (int i = 0; i < dirs.length; i++) {
			int next_x = dirs[i][0] + rMove;
			int next_y = dirs[i][1] + cMove;
			int count = 0;
			while (next_x < rows && next_y < cols && next_x >= 0 && next_y >= 0 ){
				count += 1;
				if(board[next_x][next_y] == color){
					if(count >= 2){
						return true;
					}
					break;
				}else if(board[next_x][next_y] == '.'){
					break;
				}
				next_x += dirs[i][0];
				next_y += dirs[i][1];
			}
		}
		return false;
	}

	/*
	* https://leetcode.com/problems/maximum-number-of-weeks-for-which-you-can-work/
	* */
	public long numberOfWeeks(int[] milestones) {
		long[] arr = Arrays.stream(milestones).mapToLong(i -> i).toArray();
		long sum = Arrays.stream(arr).sum();
		int max = Arrays.stream(milestones).max().getAsInt();

		if(max > (sum - max)){
			return 2 * (sum - max) + 1;
		}else{
			return sum;
		}
	}

	/*
	 * https://leetcode.com/problems/minimum-cost-to-reach-destination-in-time/
	 * 1928. Minimum Cost to Reach Destination in Time
	 * Dijiktra algorithm
	 * */
	public int minCost(int maxTime, int[][] edges, int[] passingFees) {
		// [src, time, cost]
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
		HashMap<Integer, List<int[]>> graph = new HashMap<>();
		int n = passingFees.length;

		int[] minTime = new int[n];
		int[] minCost = new int[n];
		Arrays.fill(minTime, Integer.MAX_VALUE);
		Arrays.fill(minCost, Integer.MAX_VALUE);

		for (int i = 0; i < edges.length; i++) {
			int src = edges[i][0];
			int dst = edges[i][1];
			int time = edges[i][2];
			graph.putIfAbsent(src, new ArrayList<>());
			graph.putIfAbsent(dst, new ArrayList<>());
			graph.get(src).add(new int[]{dst, time});
			graph.get(dst).add(new int[]{src, time});
		}

		pq.offer(new int[]{0, 0, passingFees[0]});

		// [src, time, cost]
		while (!pq.isEmpty()) {
			int[] curr = pq.poll();
			int src = curr[0];
			int time = curr[1];
			int cost = curr[2];
			minCost[src] = cost;
			minTime[src] = time;
			for (int[] adj : graph.get(src)) {
				int next_src = adj[0];
				int next_time = adj[1];
				int total_time = time + next_time;
				if (total_time <= maxTime) {
					if (minCost[next_src] > cost + passingFees[next_src]) {
						minCost[next_src] = cost + passingFees[next_src];
						minTime[next_src] = total_time;
						pq.offer(new int[]{next_src, minTime[next_src], minCost[next_src]});
					} else if (minTime[next_src] > total_time) {
						minTime[next_src] = total_time;
						pq.offer(new int[]{next_src, minTime[next_src], cost + passingFees[next_src]});
					}
				}
			}
		}
		int ans = minCost[n - 1];
		if (ans == Integer.MAX_VALUE) {
			return -1;
		}
		return ans;
	}

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