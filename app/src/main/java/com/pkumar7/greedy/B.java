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