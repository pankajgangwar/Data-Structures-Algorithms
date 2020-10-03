package com.pkumar7.datastructures;
import java.math.BigInteger;

public class DynamicProgramming {
	
	public static void main(String[] args) {
		
		//Fibonacci fibonacci = new Fibonacci();
		
		//System.out.println("Fibonacci: " + fibonacci.fib(6));
		
		//int n = 7;
		//System.out.println("Arragements to form " + n +" is " + solve(n));
		
		int arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 }; 
        int n = arr.length; 
        //System.out.println("Length of lis is " + LIS.lis(arr, n)); 
        
        
        int stock [] = new int[] {1,3,1,2};
        //System.out.println(" Stock max: " + stockmax(stock));
        int a[] = {3,5,1,2};
        int b[] = {5,1,2,6};
        //longestCommonSubsequence(a, b);
        
        int res = Solution.fibonacciModified();
        System.out.println(" Res -> " + res);
        
	}
	
	static class LIS 
	{ 
	//https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/
	static int max_ref; // stores the LIS 
	
	/* To make use of recursive calls, this function must return 
	   two things: 
	   1) Length of LIS ending with element arr[n-1]. We use 
	      max_ending_here for this purpose 
	   2) Overall maximum as the LIS may end with an element 
	      before arr[n-1] max_ref is used this purpose. 
	   The value of LIS of full array of size n is stored in 
	   *max_ref which is our final result */
	   static int _lis(int arr[], int n) 
	   { 
	       // base case 
	       if (n == 1) {
	    	   System.out.println("Returning 1");
	           return 1; 
	       }
	  
	       // 'max_ending_here' is length of LIS ending with arr[n-1] 
	       int res, max_ending_here = 1; 
	  
	        /* Recursively get all LIS ending with arr[0], arr[1] ... 
	           arr[n-2]. If   arr[i-1] is smaller than arr[n-1], and 
	           max ending with arr[n-1] needs to be updated, then 
	           update it */
	        for (int i = 1; i < n; i++) 
	        { 
	            res = _lis(arr, i);
	            System.out.println("res: " + res + " arr[i-1] " + arr[i-1] + " arr[n-1]: " + arr[n-1]);
	            if (arr[i-1] < arr[n-1] && res + 1 > max_ending_here) 
	                max_ending_here = res + 1; 
	        } 
	  
	        // Compare max_ending_here with the overall max. And 
	        // update the overall max if needed 
	        if (max_ref < max_ending_here) 
	           max_ref = max_ending_here; 
	  
	        // Return length of LIS ending with arr[n-1] 
	        System.out.println("max_ending_here: " + max_ending_here);
	        return max_ending_here; 
	   } 
	  
	    // The wrapper function for _lis() 
	    static int lis(int arr[], int n) 
	    { 
	        // The max variable holds the result 
	         max_ref = 1; 
	  
	        // The function _lis() stores its result in max 
	        _lis( arr, n); 
	  
	        // returns max 
	        return max_ref; 
	    } 
	}
	
	//https://www.geeksforgeeks.org/solve-dynamic-programming-problem/
	/*
	 * Given 3 numbers {1, 3, 5}, we need to find
	   the total number of ways we can form a number 'N' 
	   using the sum of the given three numbers.
	   (allowing repetitions and different arrangements)
	 * */
	// Find total number of arrangements to form n
	public static int solve(int n){
		if(n < 0)
			return 0;
		if(n == 0)
			return 1;
		
		return solve(n-1) + solve(n-3) + solve(n-5);
	}
	
	//Memoization (Top-Down)
	static class Fibonacci {
		
		final int MAX = 1000;
		final int NIL = -1;
		int[] lookup = new int[100];
		public Fibonacci(){
			for(int i=0; i<lookup.length; i++)
				lookup[i] = NIL;
		}
		
		public int fib(int n){
			if(lookup[n] == NIL){
				if(n <= 1)
					lookup[n] = n;
				else
					lookup[n] = (int) (fib(n-1) + Math.pow(fib(n-2),2));
			}
			return lookup[n];
		}
	}
	
	//5, 3, 2
	//1, 2, 3, 100
	//1, 3, 1, 2
	static int stockmax(int[] prices) {
        int max = prices[0];
        int min = prices[0];
        int[] profits = new int[prices.length];
        profits[0] = 0;

        for(int i = 0 ; i < prices.length; i++){
			
			if (max < prices[i]) {
				profits[i] = prices[i] - min;
				min += max;
				max = prices[i];
			}
			
        }

        int max_profit = 0;
        for(int i = 0; i < profits.length; i++){
            if(max_profit < profits[i]){
                max_profit = profits[i];
            }
        }
        return max_profit;

    }
	
	//Longest common subsequence
	
	static int[] longestCommonSubsequence(int[] a, int[] b) {
		int[] result = lcsRecursive(a, b, a.length, b.length);

		for (int i = 0; i < result.length -1; i++) {
			System.out.print(result[i] + " , " );
		}
		return result;
	}

	static int[] lcsRecursive(int[] a, int [] b, int m, int n){
        int LCS[][] = new int[m + 1][n + 1];
        
        for(int i = 0 ; i <= m; i++) {
            for(int j = 0 ; j <= n; j++) {
                if(i == 0 || j == 0) {
                    LCS[i][j] = 0;
                }else if(a[i - 1] == b[j - 1]) {
                    LCS[i][j] = LCS[i-1][j-1] + 1;
                }else {
                    LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
                }
            }
        }
        int index = LCS[m][n];
        int[] lcs = new int[index+1];
        
        int i = m, j = n;
        while(i > 0 && j > 0) {
        	
            if(a[i-1] == b[j-1]) {
                lcs[index -1] = a[i-1];
                i--;
                j--;
                index--;
            }else if(LCS[i-1][j] > LCS[i][j-1]) {
                i--;
            }else {
                j--;
            }
        }
        return lcs;
    }
	
	/*
	 LC : 392
	 https://leetcode.com/problems/is-subsequence/
	 Longest common subsequence
	 */
	public boolean isSubsequence(String s, String t) {

        //int length = subSequenceDP(s.toCharArray(), t.toCharArray());
        //return length == s.length();
		boolean res = isSubSequence(s, t);
		return res;
    }

    public boolean isSubSequence(String s, String t){
		int i = 0, j = 0;
		while (i < s.length() && j < t.length()){
			if(s.charAt(i) == t.charAt(j)){
				i++;
			}
			j++;
		}
		return i == s.length();
	}
    
     public int subSequenceDP(char s[], char t[]) {
         int m = s.length;
         int n = t.length;
         
         int dp[][] = new int[m + 1][ n + 1];
         
         for(int i = 1 ; i <= m; i++ ) {
             for(int j = 1; j <= n; j++ ) {
                 if(s[i-1] == t[j-1]){
                     dp[i][j] = 1 + dp[i-1][j-1];
                 } else {
                     dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                 }
             }
         }
         return dp[m][n];
    }
    

    //https://leetcode.com/problems/min-cost-climbing-stairs/
    //[1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
    //[10, 15, 20]
    public int minCostClimbingStairs(int[] cost) {
    	 for(int i = 2 ; i < cost.length ; i++) {
             cost[i]+= Math.min(cost[i-1], cost[i-2]);
         }
         
         return Math.min(cost[cost.length -1], cost[cost.length -2]);
    }
    
    /**
	 *  55. Jump Game
     * 	Input: [2,3,1,1,4]
		Output: true
		Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
	 	https://leetcode.com/problems/jump-game/
     **/
    public boolean canJump(int[] nums) {
        int[] jumps = new int[nums.length];
        int n = nums.length;
        if(n == 0 || nums[0] == 0) {
        	return false;
        }
        jumps[0] = 0;
       
        for(int i =0; i < n ; i++) {
        	jumps[i] = Integer.MAX_VALUE;
        	for(int j =0; j < i; j++) {
        		//if(i <= j + )
        	}
        }
        return false;
    }
    
    public static class Solution {		
        final static int MAX = 1000;
        final static BigInteger NIL = BigInteger.valueOf(-1);
		static BigInteger[] lookup = new BigInteger[100];
		static int t1 = 0;
		static int t2 = 1;
		static int n = 10;

		// Complete the fibonacciModified function below.
		/***
		 * https://www.hackerrank.com/challenges/fibonacci-modified/problem
		 *
		 */
	    static int fibonacciModified() {
	            for(int i=0; i<lookup.length; i++)
	                lookup[i] = NIL;
			
			lookup[0] = BigInteger.valueOf(-1);
			lookup[1] = BigInteger.valueOf(t1);
	        lookup[2] = BigInteger.valueOf(t2);;
	        
			BigInteger res = fib(n);
			
			System.out.println("Result " + res);
			return -1;
	    }
    
	    public static BigInteger fib(int n){
			if(n <= 0){
				return BigInteger.valueOf(0);
			}
			if(lookup[n]== NIL){
				
				BigInteger first = fib(n - 2);
				BigInteger second = fib(n - 1);
				second = second.multiply(second);

				lookup[n] =  first.add(second);
			}
			return lookup[n];
		}
    }
    
}
 