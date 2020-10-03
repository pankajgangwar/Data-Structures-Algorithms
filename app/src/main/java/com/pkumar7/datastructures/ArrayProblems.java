package com.pkumar7.datastructures;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;


public class ArrayProblems {
	
	public static void main1(String[] args) {
		int q[] = {2,1,5,3,4}; 
		//int q[] = {2,5,1,3,4}; 
		//minimumBribes(q);
		
		//int arr[] = {0,2,1,1,1,2,0,0,1};
		//solveFlag(arr);
		
		//sort012(arr, arr.length);
		
		//int n=9,k=10,b=2;
		//bonetrousle(n, k, b);
		
		ArrayList<Integer> inputSeq = new ArrayList<>();
		inputSeq.add(6);
		inputSeq.add(2);
		inputSeq.add(4);
		inputSeq.add(1);
		inputSeq.add(5);
		//int seq = countSubSequence(inputSeq, 10);
		
		int arr[] = { -1,-10,1,10,11,10,2,23 };
		int n = arr.length;
		int sum = 23;
		//System.out.println(" Total Number of subseq: " + subArraySum(arr, n, sum));
		
		subArraySum();
		
		//HackerRankExpenseProblem();
		
		//findSecondSmallest();
		
		//checkPermutationOfPalindrome();
		
		//onEditAway();
		
		ArrayProblems arrayProblems = new ArrayProblems();
		
		//arrayProblems.thirdMax();
		
		int[][] points = new int[][] {{0,0},{0,2},{2,1}};
		//[[0,0],[0,2],[2,1]]
		//boolean status = arrayProblems.isBoomerang(points);
		//System.out.println("Are on same line ?  " + status);
		
		//arrayProblems.isRobotBounded();
		
		//sortArrayByParityII();
		
		//arrayProblems.heightChecker();
		int[] barcodes = new int[] {1,1,1,1,2,2,3,3};
		//arrayProblems.rearrangeBarcodes(barcodes);
		
		int nums[] = new int[] {1, 7, 3, 6, 5, 6};
		//int pivotIndex = arrayProblems.pivotIndex(nums);
		
		//System.out.println("PivotIndex: " + pivotIndex);
		
	}
	
	//Dutch National flag problem {G,H,K,G,H,G,K,H} -> {G,G,G,K,K,H,H,H}
	//https://leetcode.com/problems/sort-colors/submissions/
	static void solveFlag(int arr[]){
		int low = 0;
		int high = arr.length -1;
		int mid = 0;
		while(mid <= high){
			switch (arr[mid]){
			case 0:
				int temp = arr[mid];
				arr[mid] = arr[low];
				arr[low] = temp;
				low++;
				mid++;
				break;
			case 1:
				mid++;
				break;
			case 2:
				int temp1 = arr[mid];
				arr[mid] = arr[high];
				arr[high] = temp1;
				high--;
				break;
			}
		}
		
		for(int val=0; val<arr.length;val++){
			System.out.print(arr[val] + " \t");
		}
	}
	
	
	static void minimumBribes(int[] q) {
		
		boolean swapped = false;
		int count = 0;
		do{
			swapped = false;
			for(int i=1;i < q.length; i++){
				if(q[i-1] -i > 2){
					System.out.println("Too chaotic");
					return;
				}
				if(q[i-1] > q[i]){
					int temp = q[i];
					q[i] = q[i-1];
					q[i-1] = temp;
					count++;
					swapped = true;
				}
			}
			
		}while(swapped);
		
		System.out.println("Total Bribes: " + count);
	}
	
	/*
     * Complete the bonetrousle function below.
     * https://www.hackerrank.com/challenges/bonetrousle/problem
     * n,k and b, the number of sticks to buy, the number of boxes for 
     * sale and the number of boxes to buy on this trip to the store.
     * Sample input: 
     *  12 8 3
		10 3 3
		9 10 2
		9 10 2
     */
    static void bonetrousle(long n, long k, int b) {
        // n number of sticks to buy
        // k boxes available for purchase
        // b boxes to buy

        // (x+1) + (x+2) + (x+3)...(x+b)
        // b*x + b*(b+1)/2 = n
    	int min = b*(b+1)/2;
    	int max = (int) (b*(2*k - b +1)/2);
        
    }
    
    static int countSubSequence(ArrayList<Integer> inputSeq, int targetSum) {
    	
    	int N = inputSeq.size();
    	int subSeq = 0;
    	for(int i=0;i<N;i++){
    		if(inputSeq.get(i) == targetSum){
    			subSeq++;
    		}else {
    			int expectedSum = 0;
    			int j=i;
    			while(expectedSum != targetSum){
    				if(j >= N)break;
    				expectedSum+= inputSeq.get(j);
    				if(expectedSum > targetSum){ 
    					break ;
    				}
    				if(expectedSum != targetSum){ 
    					j++;
    				}
    			}
    			if(expectedSum == targetSum){ 
    				subSeq++;
    				i = j;
				}
    			
    		}
    	}
    	return subSeq;

    }
    
	public static void subArraySum() {
		int arr[] = {1,2,4,3,6,1,1,1,1,5};
		int n = arr.length;
		int sum = 4;
		int curr_sum = arr[0], start = 0, i;

		int count = 0;

		for (i = 1; i <= n; i++) {
			while (curr_sum > sum && start < i - 1) {
				curr_sum = curr_sum - arr[start];
				start++;
			}

			// If curr_sum becomes equal to sum, then increment count
			if (curr_sum == sum) {
				int p = i - 1;
				System.out.println("Sum found between indexes " + start + " and " + p);
				count++;
			}

			// Add this element to curr_sum
			if (i < n)
				curr_sum = curr_sum + arr[i];

		}

		System.out.println("Count: " + count);
	}

	//Traveler fund
	/*
	 * A traveler is traveling from city zeta to omega. He starts with X amount of money. 
	 * Every day he spends some money and also he may work on some days to earn money. 
	 * He may find good work some day and end up earning more than what he spends that day. It also
	 * may happen that he spends more than what he earns on any day.
	 * 
	 * You are given an array of integers which represents his net savings (earning + expense) on any day. You need
	 * to find out minimum amount the traveler should begin with to ensure that he always has some money
	 * at the end of any day.
	 * 
	 *   -200 <= a <= 200 ( where a is array element)
	 *   0 < i <= 100, where i is the array length
	 *   X >= 0
	 *   3 // Array length
	 *   4 //Array starts
	 *   2
	 *   -3
	 *   
	 *   Output: 
	 *   0
	 * */
	private static void HackerRankExpenseProblem(){
		int[] expensePerDay = new int[] { 1, 2, -4, -5, 9, -8, -9, };

		// int[] expensePerDay = new int[] {4,2,-3};
		int startingAmount = 1;

		int sumTillZero = startingAmount;
		for (int i = 0; i < expensePerDay.length; i++) {
			if (sumTillZero + expensePerDay[i] > 0) {
				sumTillZero = sumTillZero + expensePerDay[i];
			} else {
				startingAmount += 1 - (sumTillZero + expensePerDay[i]);
				sumTillZero = 1;
			}
		}
		System.out.println("Starting amount :" + startingAmount);
	}
	
	private static void findSecondSmallest(){
		int arr[] = {8,10,-1,6,4,3,2,0,9};
		int first=Integer.MAX_VALUE,second=Integer.MAX_VALUE;
		for(int i=0; i < arr.length; i++){
			if(arr[i] < first){
				second = first;
				first = arr[i];
			}else if(arr[i] < second && arr[i] != first){
				second = arr[i];
			}
		}
		System.out.println("First: " + first + " second: " + second);
	}
	
	public static void findStartingAmount(){
		int arr[] = {1,2,-4,-5,9,-8,-9};
		
		int initialX = 0;
		
		for(int i=0; i < arr.length;i++){
			initialX+= arr[i];
		}
		System.out.println(initialX);
	}
	
	private static void checkPermutationOfPalindrome() {
		String input = "aaabb";
		int[] table = buildCharFrequencyTable(input);
		
		boolean isPermutation = checkMaxOneOdd(table);
		System.out.println(input + " is permutation: " + isPermutation);
	}
	
	private static boolean checkMaxOneOdd(int[] table) {
		boolean foundOdd = false;
		for(int count : table) {
			if(count%2 == 1) {
				if(foundOdd) {
					return false;
				}
				foundOdd = true;
			}
		}
		return true;
	}
	
	private static int[] buildCharFrequencyTable(String phrase) {
		int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
		for(char c : phrase.toCharArray()) {
			int X = getCharNumber(c);
			if(X != -1) {
				table[X]++;
			}
		}
		return table;
	}

	private static int getCharNumber(char c) {
		// TODO Auto-generated method stub
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		int val = Character.getNumericValue(c);
		if(a <= val && val <= z) {
			return val - a;
		}
		return -1;
	}
	
	private static void oneWay() {
		String input1 = "pale";
		String input2 = "ble";
		
		if(input1.length() - input2.length() > 1) {
			System.out.println("FALSE");
			return;
		}
		HashMap<Character, Integer> mHashMap = new HashMap<>();
		for(char ch : input1.toCharArray()) {
			if(mHashMap.containsKey(ch)) {
				mHashMap.put(ch, mHashMap.get(ch) + 1);
			}else {
				mHashMap.put(ch, 1);
			}
		}
		
		boolean found = false;
		for(char ch : input2.toCharArray()) {
			if(mHashMap.containsKey(ch)) {
				mHashMap.put(ch, mHashMap.get(ch) -1);
			}else if(found){
				System.out.println("FALSE");
				return;
			}else {
				found = true;
			}
		}
		//find key who's value is less than zero, if there are more than 1 key return false
		System.out.println("TRUE" + mHashMap.size());
		
	}
	
	private static void onEditAway() {
		// Given 2 strings, check if there are 1 edit away
		// pale, ple (true)
		// pales, pale (true)
		// pale, bale (true)
		// pale, bake (false)
		String first = "pale";
		String second = "ple";
		boolean status = false;
		if(first.length() == second.length()) {
			status = oneEditReplace(first,second);
		}else if(first.length() - 1 == second.length()) {
			System.out.println("*************");
			status = oneEditInsert(first,second);
		}else if(first.length() + 1  == second.length()) {
			status = oneEditInsert(first,second);
		}
		
		System.out.println("Status: " + status);
	}

	private static boolean oneEditInsert(String first, String second) {
		// TODO Auto-generated method stub
		int index1 = 0;
		int index2 = 0;
		while(index2 < second.length() && index1 < first.length()) {
			System.out.println(first.charAt(index1) + " , " + second.charAt(index2));
			if(first.charAt(index1) != second.charAt(index2)) {
				if(index1 != index2) {
					return false;
				}
				index1++;
			} else {
				index1 ++;
				index2 ++;
			}
		}
		return true;
	}

	private static boolean oneEditReplace(String first, String second) {
		// TODO Auto-generated method stub
		boolean foundDifference = false;
		for(int i=0; i < first.length(); i++) {
			System.out.println(first.charAt(i) + " , " + second.charAt(i));
			if(first.charAt(i) != second.charAt(i)) {
				if(foundDifference) {
					return false;
				}
				foundDifference = true;
			}
		}
		return true;
	}

	//https://leetcode.com/problems/third-maximum-number/
	public int thirdMax() {
		int nums[] = new int[] {};
		Integer max = null;
		Integer secondMax = max;
		Integer thirdMax = secondMax;

		for (int i = 0; i < nums.length; i++) {
			Integer thisNumber = nums[i];
			if (thisNumber.equals(max) || thisNumber.equals(secondMax) || thisNumber.equals(thirdMax))
				continue;

			if (max == null || thisNumber > max) {
				thirdMax = secondMax;
				secondMax = max;
				max = thisNumber;
			} else if (secondMax == null || thisNumber > secondMax) {
				thirdMax = secondMax;
				secondMax = thisNumber;
			} else if (thirdMax == null || thisNumber > thirdMax) {
				thirdMax = thisNumber;
			}

			System.out.println(
					"This: " + nums[i] + " Max: " + max + " SecondMax: " + secondMax + " ThirdMax: " + thirdMax);
		}
		return thirdMax == null ? max : thirdMax;
	}
	
	 public int thirdMax(int[] nums) {
	        Integer max1 = null;
	        Integer max2 = null;
	        Integer max3 = null;
	        for (Integer n : nums) {
	            if (n.equals(max1) || n.equals(max2) || n.equals(max3)) continue;
	            if (max1 == null || n > max1) {
	                max3 = max2;
	                max2 = max1;
	                max1 = n;
	            } else if (max2 == null || n > max2) {
	                max3 = max2;
	                max2 = n;
	            } else if (max3 == null || n > max3) {
	                max3 = n;
	            }
	        }
	        return max3 == null ? max1 : max3;
	    }
	
	 	
		 // Slope : y2−y1/x2−x1 = y3−y1/x3−x1
	 //https://leetcode.com/contest/weekly-contest-135/problems/valid-boomerang/
	 //https://www.geeksforgeeks.org/program-check-three-points-collinear/
	 public boolean isBoomerang(int[][] points) {
			// return (p[0][0] - p[1][0]) * (p[0][1] - p[2][1]) != (p[0][0] - p[2][0]) * (p[0][1] - p[1][1]);
			int x1 = points[0][0];
			int y1 = points[0][1];

			int x2 = points[1][0];
			int y2 = points[1][1];

			int x3 = points[2][0];
			int y3 = points[2][1];

			int a = x1 * (y2 - y3) +  x2 * (y3 - y1) +  x3 * (y1 - y2); 
	        if (a == 0)
	            return false;
			
	         return true;
	    }
	    
	    static boolean pointIsOnLine(int m, int c, int x, int y) { 
	    // If (x, y) satisfies the equation  
	    // of the line 
	        if (y == ((m * x) + c)) 
	            return true; 

	        return false; 
	    }  
	    
	    //https://leetcode.com/problems/find-common-characters/
	    public ArrayList<String> commonChars(String[] A) {
	        
	        int[] count = new int[26];
	        Arrays.fill(count,Integer.MAX_VALUE);
	         
	        ArrayList<String> result = new ArrayList<>();
	        
	         for(String str : A){
	             
	             int[] cnt = new int[26];
	             for(char c : str.toCharArray()){
	                 ++cnt[c-'a'];
	             }
	             for(int i = 0; i < 26; i++){
	                 count[i] = Math.min(count[i],cnt[i]);
	             }
	             
	         }
	         
	         for(char c = 'a'; c <= 'z'; c++ ){
	             while(count[c - 'a']-- > 0){
	                 result.add(""+c);
	             }
	         }
	         
	         return result;
	             
	     }
	    
	    /*
	     * "GGLLGG"
	     * "GG"
	     * "GL"
	     * "G": go straight 1 unit;
			"L": turn 90 degrees to the left;
			"R": turn 90 degrees to the right.
	     * */
	    
	    //https://leetcode.com/problems/robot-bounded-in-circle/
	    public boolean isRobotBounded() {
	    	String instructions = "GGLLGG";
	        char[] path = instructions.toCharArray();
	        boolean isCircular = isCircular(path);
	        System.out.println(isCircular);
	        return isCircular;
	    }

		private boolean isCircular(char[] path) {
			// TODO Auto-generated method stub
			int x = 0 , y = 0;
			int direction = 3; // 3 - N, 2 - W , 4 - S , 1 - E
			int instructionsStep = 4 ;
			while(instructionsStep > 0) {
				for(int i = 0 ; i< path.length; i++) {
					char currChar = path[i];
					switch(currChar) {
						case 'G':
							if(direction == 3)
								y++;
							else if(direction == 1)
								x++;
							else if(direction == 2)
								x--;
							else
								y--;
							
							break;
						case 'L':
							if(direction == 3)
								direction = 2;
							else if(direction == 1)
								direction = 3;
							else if(direction == 2)
								direction = 4;
							else
								direction = 1;
							break;
							
						case 'R':
							if(direction == 3)
								direction = 1;
							else if(direction == 1)
								direction = 4;
							else if(direction == 2)
								direction = 3;
							else
								direction = 2;
							break;
					}
				}
				instructionsStep--;
			}
			return x == 0 && y == 0;
		}
		
		//https://leetcode.com/problems/two-sum/
		class Solution {
		    public int[] twoSum(int[] nums, int target) {

				ArrayList<Integer> numsList = new ArrayList<>();

				for (int i = 0; i < nums.length; i++) {
					numsList.add(nums[i]);
				}

				Collections.sort(numsList);

				for (int i = 0; i < nums.length; i++) {
					int ele = nums[i];
					
		            int sec_ele = target - ele;
					int sec_ele_index = Collections.binarySearch(numsList, sec_ele);
		            
		            System.out.println("sec_ele: " + sec_ele + " its index: " +  sec_ele_index + " i: " + i);
		            
					if (sec_ele_index >= 0 ) {
						int sec_index = getElementIndex(nums, sec_ele);
		                if(sec_index != i) {
		                    System.out.println(" Original index : " +  sec_index);
						    return new int[] { i, sec_index };
		                }
					}
				}
				return new int[] {};
			}
				 
			public int getElementIndex(int[] nums, int ele) {
				for (int i = 0; i < nums.length; i++) {
					if (ele == nums[i]) {
						return i;
					}
				}
				return -1;
			}
			
			public int[] twoSumAnother(int[] numbers, int target) {
			    int[] result = new int[2];
			    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			    for (int i = 0; i < numbers.length; i++) {
			        if (map.containsKey(target - numbers[i])) {
			            result[1] = i + 1;
			            result[0] = map.get(target - numbers[i]);
			            return result;
			        }
			        map.put(numbers[i], i + 1);
			    }
			    return result;
			}
		}
	
	//https://leetcode.com/problems/two-sum/
	public int[] intersection(int[] nums1, int[] nums2) {
		Set<Integer> mSet = new HashSet<>();

		Set<Integer> mSet2 = new HashSet<>();

		for (int i = 0; i < nums1.length; i++) {
			mSet.add(nums1[i]);
		}

		for (int i = 0; i < nums2.length; i++) {
			mSet2.add(nums2[i]);
		}

		ArrayList<Integer> mResult = new ArrayList<>();

		Iterator<Integer> iterator = mSet.iterator();

		while (iterator.hasNext()) {
			int ele = iterator.next();
			if (mSet2.contains(ele)) {
				mResult.add(ele);
			}
		}

		int[] result = new int[mResult.size()];

		Object[] re = mResult.toArray();

		for (int i = 0; i < re.length; i++) {
			result[i] = (int) re[i];
		}

		return result;

	}
	
	//https://leetcode.com/problems/search-insert-position/submissions/
	public int searchInsert(int[] nums, int target) {
		int index = Arrays.binarySearch(nums, target);
		System.out.println("Index : " + index);
		if(index >= 0) {
			return index;
		}else {
			return Math.abs(index);
		}
	}
	
	
	//https://leetcode.com/problems/sort-array-by-parity-ii/
	public static int[] sortArrayByParityII() {
		int[] A = new int[] {4,2,5,7,1,9,6,10};
		int n = A.length;
		for (int i = 0; i < n; i++) {
			
			System.out.println("ele: " + A[i] + " at " + i);

			if(i % 2 == 0) { //pos is even
				if (A[i] % 2 != 0) {//ele is not even
					int j = i + 1;
					while (j < n) {
						System.out.println("ele at j: " + A[j] + " for " + j);
						if (A[j] % 2 == 0) {
							int temp = A[j];
							A[j] = A[i];
							A[i] = temp;
							break;
						}
						j ++;
					}
				}
			}else { // pos is odd
				if (A[i] % 2 == 0) {//ele is not even
					int j = i + 1;
					while (j < n) {
						System.out.println("ele at j: " + A[j] + " for " + j);
						if (A[j] % 2 != 0) {
							System.out.println("Swapping : " + A[i] + " " + A[j]);
							int temp = A[j];
							A[j] = A[i];
							A[i] = temp;
							break;
						}
						j ++;
					}
				}
			}
			
		}
		
		for(int val = 0; val < A.length; val++){
			System.out.print(A[val] + " \t");
		}

		return A;

	}
	
	//https://leetcode.com/contest/weekly-contest-138/problems/height-checker/
	//[1,1,4,2,1,3]
	//[1,1,1,2,3,4]
	 public int heightChecker() {
		int[] heights = new int[] {1,1,4,2,1,3};
	    int[] sortedHeights = Arrays.copyOf(heights, heights.length);
		Arrays.sort(sortedHeights);
		
		System.out.println(heights.length + " :: " + sortedHeights.length);
		int wrongPos = 0;
		for(int i =0; i < heights.length; i++) {
			if(sortedHeights[i] != heights[i]) {
				wrongPos+= 1;
			}
		}
		System.out.println(wrongPos);
		return wrongPos;
	 }
	 
	 //https://leetcode.com/contest/weekly-contest-138/problems/distant-barcodes/
	 class Element{
	        int val;
	        int frequency;
	        public Element(int val){
	            this.val = val;
	        }
	 }
	 
	public int[] rearrangeBarcodes(int[] barcodes) {
		Map<Integer, Integer> mMap = new HashMap<>();
		PriorityQueue<Element> mMaxHeap = new PriorityQueue<>(new Comparator<Element>(){
            @Override
 			public int compare(Element e1, Element e2) {
 				// TODO Auto-generated method stub
 				if(e1.frequency > e2.frequency) {
 					return -1;
 				}
 				else if(e1.frequency < e2.frequency) {
 					return 1;
 				}
 				return 0;
 			} 
         });
		
		for(int i = 0; i < barcodes.length; i++ ){
            int thisElement = barcodes[i];
            if(mMap.get(thisElement) == null) {
            	mMap.put(thisElement,1);
            }else {
            	int frequencyForThis = mMap.get(thisElement); 
                mMap.put(thisElement, frequencyForThis+1);
            }
        }
    	
    	//int[] barcodes = new int[] {1,1,1,1,2,2,3,3};
    	for(Integer entry : mMap.keySet()) {
    		Element ele = new Element(entry);
    		ele.frequency = mMap.get(entry);
    		
    		System.out.println("Adding: " + ele.val + " freq: " + ele.frequency);
    		mMaxHeap.offer(ele);
    	}
    	
    	int result[] = new int[barcodes.length];
    	int i = 0;
    	Element prev = new Element(-1);
    	
    	while(!mMaxHeap.isEmpty()) {
    		Element element = mMaxHeap.peek();
    		mMaxHeap.poll();
    		
    		result[i] = element.val;
    		System.out.println("Adding " + element.val);
    		
    		if(prev.frequency > 0) {
    			mMaxHeap.offer(prev);
    		}
    		
    		element.frequency--;
    		prev = element;
    		
    		i++;
    	}
    	
    	for(int ele : result) {
    		System.out.println(ele);
    	}
    	
    	return result;
    	
	}
	
	//https://www.geeksforgeeks.org/find-next-greater-number-set-digits/
	
	//https://leetcode.com/problems/find-pivot-index/
	//[1, 7, 3, 6, 5, 6] 
	public int pivotIndex(int[] nums) {
		nums = new int[] {-1,-1,0,1,1,0};
        if(nums.length < 3) {
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
       
        System.out.println(sumFromStart + " initial <----> " + sumFromEnd);
        for(i = 0; i < nums.length - 1; i++) {
        	
        	sumFromStart+= nums[i];
        	sumFromEnd-= nums[i+1];
        	//j++;
        	System.out.println(sumFromStart + " <----> " + sumFromEnd);
        	if(sumFromStart == sumFromEnd) {
        		return i+1;
        	}
        }
        
        return -1;
	}
	
	public static void main(String[] args) {
		int[] arr = new int[]{1,0};
		duplicateZeros(arr);
	}

	public static void duplicateZeros(int[] arr) {
		System.out.println("Calling duplicateZero");
	   int[] arrCopy = Arrays.copyOf(arr, arr.length);

	   int j = 0;
	   for(int i = 0; i < arrCopy.length && j < arrCopy.length; i++){
		   if(arrCopy[i] != 0){
				arr[j++] = arrCopy[i];
			}else{
				arr[j++] = 0;
				if(j < arr.length)
					arr[j++] = 0;
			}
	   }
	   
    }

}


