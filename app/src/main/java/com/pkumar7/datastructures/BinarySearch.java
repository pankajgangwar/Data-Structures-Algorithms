package com.pkumar7.datastructures;

import java.util.Arrays;

public class BinarySearch {
	public static void main(String[] args) {
		
		BinarySearch binarySearch = new BinarySearch();
		
		int[] numbers = new int[] {1,2,3,4,4,9,56,90};
		
		int[] result = binarySearch.twoSum(numbers, 8);
		
		System.out.println(result[0] + " :: " + result[1]);
	}
	
	public int[] twoSum(int[] numbers, int target) {

		for (int i = 0; i < numbers.length; i++) {
			int first_ele = numbers[i];
			int second_index = Arrays.binarySearch(numbers, target - first_ele);
			
			System.out.println(i + " <--> " + second_index);
			
			if (second_index > i ) { 
				return new int[] { i+1, second_index+1 };
			}else if((i - second_index) == 1/*Duplicates*/) {
				return new int[] {second_index+1, i+1};
			}
		}
		return new int[] { -1, -1 };
	}
}
