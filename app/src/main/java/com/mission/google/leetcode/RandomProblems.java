package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class RandomProblems {
	public static void main(String[] args) {
		int[] nums = new int[]{9,6,4,2,3,5,7,0,1};
		//System.out.println("Missing number: " + findMissingPositive(nums, nums.length));
		//System.out.println("Missing number: " + findMissingNumber(nums));
		//System.out.println(removeOuterParentheses());
		//productArray(nums,nums.length);
		//int[] ans = productExceptSelf(nums);
//		for(int x : ans) {
//			System.out.print(x+",");
//		}
		
		String strArr[] = new String[]{"abc"};
		//uniqueMorseRepresentations(strArr);
		
		//System.out.println(divisorGame());
		//System.out.println(numJewelsInStones("AabB", "cc"));
		
		//preorder(null);
		int[][] costs = new int[] [] {
				{10,20},
				{30, 200},
				{400, 50},
				{30,20}
		};
		//int cost = twoCitySchedCost(costs);
		//System.out.println(cost);
		
		//int index = peakIndexInMountainArray(new int[] {0,2,1,0});
		//System.out.println("Index: " + index);
		//String s = "hello";
		//char[] reverse = reverseString(s.toCharArray());
		//System.out.println(reverse);
		
		Set<Integer> mSet = new HashSet<>();
		mSet.add(10);
		mSet.add(9);
		mSet.add(8);
		
		int ele = (Integer)mSet.toArray()[1];
		System.out.println(ele);
		
	}
	
	//https://leetcode.com/problems/first-missing-positive/
	static int findMissingPositive(int arr[], int size) { 
      int i; 
       
      // Mark arr[i] as visited by making  
      // arr[arr[i] - 1] negative. Note that  
      // 1 is subtracted because index start  
      // from 0 and positive numbers start from 1 
      for(i = 0; i < size; i++) { 
        int x = Math.abs(arr[i]); 
        System.out.print(" x : " + x +", ");
        if(x - 1 < size && arr[x - 1] > 0) { 
          arr[x - 1] = -arr[x - 1];
          System.out.print(" Negatives: " + arr[x-1] + " ");
        }
        
      } 
       
      // Return the first index value at which  
      // is positive 
      for(i = 0; i < size; i++) 
        if (arr[i] > 0) 
          return i+1;  // 1 is added becuase indexes  
                       // start from 0 
       
      return size+1; 
    } 
	
	//https://leetcode.com/problems/product-of-array-except-self/submissions/
	//https://stackoverflow.com/questions/51300360/given-a-list-of-numbers-and-a-number-k-return-whether-any-two-numbers-from-the
	public static int[] productExceptSelf(int[] nums) {
		int[] result = new int[nums.length];
        for (int i=0; i < nums.length; i++) {
        	int product = 1;
        	for(int j=0; j < nums.length; j++) {
        		if(i == j) {
        			continue;
        		}else {
        			product = product*nums[j];	
        		}
        	}
        	result[i] = product;
        }
        return result;
    }
	
	static void productArray(int arr[], int n) { 
        // Initialize memory to all arrays 
        int left[] = new int[n]; 
        int right[] = new int[n]; 
        int prod[] = new int[n]; 
  
        int i, j; 
  
        /* Left most element of left array is always 1 */
        left[0] = 1; 
  
        /* Rightmost most element of right array is always 1 */
        right[n - 1] = 1; 
  
        /* Construct the left array */
        for (i = 1; i < n; i++) {
            left[i] = arr[i - 1] * left[i - 1]; 
        }
        printArr(left);
        System.out.println();
  
        /* Construct the right array */
        for (j = n - 2; j >= 0; j--) {
            right[j] = arr[j + 1] * right[j + 1]; 
        }
        printArr(right);
        System.out.println();
  
        /* Construct the product array using 
           left[] and right[] */
        for (i = 0; i < n; i++) {
            prod[i] = left[i] * right[i]; 
        }
        printArr(prod);
        System.out.println();
  
        /* print the constructed prod array */
        for (i = 0; i < n; i++) {
            System.out.print(prod[i] + " "); 
        }
         
    }
	
	static void printArr(int[] arr) {
		for(int x : arr) {
			System.out.print(x+",");
		}
	}
	
	//https://leetcode.com/problems/missing-number/
	static int findMissingNumber(int[] arr) {
		int n = arr.length;
		int originalSize = (n*(n + 1))/2;
		
		int actualSize = 0;
		for(int i = 0 ; i < n; i++ ) {
			actualSize+= arr[i];
		}
		
		System.out.println(originalSize + ": " + actualSize);
		return originalSize - actualSize;
		
	}
	
	//https://leetcode.com/contest/weekly-contest-131/problems/remove-outermost-parentheses/
	public static String removeOuterParentheses() {
		String S = "(()(()))"; // (())

		if (S.length() == 0) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		int opened = 0;
		for (char c : S.toCharArray()) {
			if (c == '(' && opened++ > 0)
				s.append(c);
			if (c == ')' && opened-- > 1)
				s.append(c);
		}
		return s.toString();
        /*char[] seq = S.toCharArray();
        LinkedList<Character> mQueue = new LinkedList<>();
        
        int open = 0;
        StringBuilder output = new StringBuilder();
        
        for(int i= 0 ; i < seq.length; i++) {
        	if(seq[i] == '(') {
        		open++;
        	}else {
        		open--;
        	}
        	mQueue.offer(seq[i]);
        	if(seq[i] == ')' && open == 0) {
        		if(mQueue.size() > 2) {
        			//print
        			mQueue.removeLast();
        			mQueue.removeFirst();
        			
        			while(!mQueue.isEmpty()) {
        				output.append(mQueue.poll());
        			}
        		}else {
        			mQueue.clear();
        		}
        	}
        }
        return output.toString();*/
    }
	
	//https://www.hackerrank.com/challenges/balanced-brackets/problem
	static String isBalanced(String s) {
		s = "{{[[(())]]}}";
		String allCloseChar = ")]}";
		String allOpenChar = "([{";
		Stack<Character> mStack = new Stack<>();
		/*for(int i=0; i < s.length(); i++ ) {
			if(allCloseChar.contains('a')){
				
			}
			mStack.push(s.charAt(i));
		}*/
		return null;
    }
	
	public static boolean divisorGame() {
		recoverFromPreorder();
		int N = 3;
        boolean AliceChance = true;
        boolean BobChance = false;
        int x = 0;
        Random random = new Random();
       /* while(N >= 1){
            if((x > 0 && x < N) && N % x == 0){
            	x = random.nextInt(N - 1);
            	System.out.println(x);
            }
            N = N - x;
            System.out.println("Int n: " + N + " x: " + x);
           if(AliceChance) {
        	   BobChance = true;
        	   AliceChance = false;
           }else {
        	   AliceChance = true;
        	   BobChance = false;
           }
        }*/
        return AliceChance;
    }
	
	 public static void recoverFromPreorder() {
		 String S = "1-2--3---4-5--6---7";
	        int numDashes = 0;
	        for(int i = 0; i < S.length(); i++){
	        	if(S.charAt(i) != '-') {
	        		//Its an integer
	        		int number = Integer.parseInt(""+S.charAt(i));
	        		numDashes = 0;
	        	}else {
	        		numDashes++;
	        	}
	           
	            System.out.print(numDashes + " : ");
	        }
	    }
	 
	 //https://leetcode.com/problems/unique-morse-code-words/
	public static int uniqueMorseRepresentations(String[] words) {
		 String[] transformArr = new String[] {".-","-...","-.-.","-..",".","..-.",
												 "--.","....","..",".---",
												 "-.-",".-..","--",
												 "-.","---",".--.","--.-",".-.","...","-","..-",
												 "...-",".--","-..-","-.--","--.."};
		Set<String> mUniqueTransform = new HashSet<>();
		for (String str : words) {
			int i = 0;
			String transformedString = "";
			for (; i < str.length(); i++) {
				char c = str.charAt(i);
				int indexInArray = c - 'a';
				transformedString += transformArr[indexInArray];
			}
			mUniqueTransform.add(transformedString);
		}
		return mUniqueTransform.size();
	}

	//Input: J = "aA", S = "aAAbbbb"
	//https://leetcode.com/problems/jewels-and-stones/submissions/
	public static int numJewelsInStones(String J, String S) {
		int results = 0;
		Set<Character> mJewelSets = new HashSet<>();
		for (int i = 0; i < J.length(); i++) {
			 mJewelSets.add(J.charAt(i));
		}
		
		for (int stone = 0; stone < S.length(); stone++) {
			char stones = S.charAt(stone);
			if (mJewelSets.contains(stones)) {
				results++;
			}
		}
		return results++;
	}
	
	public int[] sortArrayByParity(int[] A) {
        for(int i=0; i < A.length; i++){
            if(A[i] % 2 != 0) { //odd
                int j = A.length -1;
                while(j > i){
                    if(A[j] % 2 == 0){
                        int temp = A[j];
                        A[j] = A[i];
                        A[i] = temp;
                        break;
                    }
                    j--;
                }
            }
        }
        return A;
    }
	
	class Node {
	    public int val;
	    public List<Node> children;
	    public Node() {}
	    public Node(int _val,List<Node> _children) {
	        val = _val;
	        children = _children;
	    }
	};
	
	public static List<Integer> preorder(Node root) {
		int i = 1;
		--i;
		System.out.println(i);
		Stack<Node> mStack = new Stack<>();
		
		ArrayList<Integer> result = new ArrayList<>();
		
		mStack.push(root);
		
		/*while(!mStack.isEmpty()) {
			Node curr = mStack.pop();
			System.out.println(curr.val);
			ans.add(curr.val);
			int i = curr.children.size();
			for(; i >= 0; --i ) {
				mStack.push(curr.children.get(i));
			}
		}*/
		
		return result;
    }
	
	//https://leetcode.com/contest/weekly-contest-133/problems/two-city-scheduling/
	public static int twoCitySchedCost(int[][] costs) {
	        
		int n = costs.length;
		int totalCost = 0;
		
		Arrays.sort(costs, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				// TODO Auto-generated method stub
				System.out.println( (a[0] - a[1] )+ " : " + ( b[0] - b[1]) );
				System.out.println( "Diff: " + ((a[0] - a[1] ) - (b[0] - b[1])));
				return ( a[0] - a[1] ) - (b[0] - b[1]); 
			}
		});

		for (int i = 0; i < n/2; i++) {
			totalCost += costs[i][0];
		}
		
		for (int i = n/2; i < n; i++) {
			totalCost += costs[i][1];
		}

		return totalCost;
	}
	
	public static int peakIndexInMountainArray(int[] A) {
		int l = 0, r = A.length - 1, mid = 0;
		
		while(l < r) {
			mid = ( l + r )/ 2;
			if(A[mid] < A[mid + 1]) {
				l = mid;
			}else if(A[mid - 1] > A[mid]){
				r = mid;
			}else {
				return mid;
			}
		}
		return 0;
    }
	
	public static char[] reverseString(char[] s) {
        int l = 0, r = s.length -1;
        System.out.println("Length: " + s.length);
        while(l < r ){
        	System.out.println("Length: " + l);
            char tmp = s[r];
            s[r] = s[l];
            s[l] = tmp;
            l++;
            r--;
        }
        return s;
    }
	
	public int[] numMovesStones(int a, int b, int c) {
		int[] arr = new int[] { a, b, c };
		Arrays.sort(arr);

		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] + 1 != arr[i + 1]) {
				// Not sequential
				//arr[i];
			}
		}
		return arr;
	}
	
	public int[] twoSum(int[] nums, int target) {
		List<Integer> numsList = new ArrayList<>();
		
		for(int i= 0 ; i < nums.length; i++) {
			numsList.add(nums[i]);
		}
		
		for(int i= 0 ; i < nums.length; i++) {
			int ele = nums[i];
			int sec_ele = Math.abs(target - ele);
			
			if(Collections.binarySearch(numsList, sec_ele) >= 0) {
				return new int[] {i,numsList.indexOf(sec_ele)};
			}
		}
		return new int[] {};
    }
	
	//https://leetcode.com/problems/k-closest-points-to-origin/
	
}
