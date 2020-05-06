package com.mission.google.datastructures;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BitManipulation {
	
	public static void main(String[] args) {
		BitManipulation bitManipulation = new BitManipulation();
		int nums[] = new int[] {4,1,2,1,2};
		int single_number = bitManipulation.singleNumber(nums);
		
		System.out.println(single_number);
	}
	
	
	// https://leetcode.com/problems/single-number/
	// [2,2,1]
	// [4,1,2,1,2]
	public int singleNumber(int[] nums) {
		HashMap<Integer, Integer> freq_map = new HashMap<>();
		for (int ele : nums) {
			freq_map.put(ele, freq_map.getOrDefault(ele, 0) + 1);
		}

		Set<Integer> keys = freq_map.keySet();
		Iterator<Integer> iterator = keys.iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (freq_map.get(key) == 1) {
				return key;
			}
		}
		return -1;
	}

}
