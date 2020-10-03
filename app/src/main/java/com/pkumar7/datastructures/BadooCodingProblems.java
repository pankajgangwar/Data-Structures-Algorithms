package com.pkumar7.datastructures;
import java.util.HashMap;
import java.util.List;

public class BadooCodingProblems {
	public static void main(String[] args) {
		/*List<Integer> mSongList = new ArrayList<>();
		mSongList.add(10);
		mSongList.add(50);
		mSongList.add(90);
		mSongList.add(30);
		//mSongList.add(40);
		int pairs = playlist(mSongList);
		System.out.println("Pairs: " + pairs);*/
		
		int arr[] = {50, 10, 70, 40, 5}; 
		//int arr[] = {9, 7, 5, 10, 6, 8};
		int k = 60; 
		divisibleSumPairs(arr, k);
	}
	
	/*public static long playlist(List<Integer> songs){
		
		long possiblePairs = 0;
		
		for( int i=0; i<songs.size()-1; i++ ){
			for(int j=i+1; j<songs.size(); j++){
				int sum = songs.get(i) + songs.get(j);
				if(sum % 60 == 0){
					possiblePairs++;
				}
			}
		}
		
		return possiblePairs;
	}*/
	
	/*
	 * The idea is that you separate elements into buckets depending on their
	 * mod k. For example, you have the elements: 1 3 2 6 4 5 9 and k = 3
	 * 
	 * mod 3 == 0 : 3 6 9 mod 3 == 1 : 1 4 mod 3 == 2 : 2 5
	 * 
	 * Now, you can make pairs like so: Elements with mod 3 == 0 will match with
	 * elements with (3 - 0) mod k = 0, so other elements in the mod 3 == 0
	 * list, like so:
	 * 
	 * (3, 6) (3, 9) (6, 9)
	 * 
	 * There will be n * (n - 1) / 2 such pairs, where n is length of the list,
	 * because the list is the same and i != j. Elements with mod 3 == 1 will
	 * match with elements with (3 - 1) mod k = 2, so elements in the mod 3 == 2
	 * list, like so:
	 * 
	 * (1, 2) (1, 5) (4, 2) (4, 5)
	 * 
	 * There will be n * k such elements, where n is the length of the first
	 * list, and k of the second.
	 * 
	 * Because you don't need to print the actual pairs, you need to store only
	 * the number of elements in each list.
	 * 
	 */
	public static int playlist(List<Integer> songs){
		
		int freq[] = new int[songs.size()];
		
        int i = 0; 
        int ans = 0; 
        int n = songs.size();
        
        int k = 60;
        
        HashMap<Integer, Integer> hm = new HashMap<>();
        
        for (i = 0; i < n ; i++){
        	
        	int rem = songs.get(i) % k;
        	if(!hm.containsKey(rem))
        		hm.put(rem, 0);
        	
        	hm.put(rem, hm.get(rem) + 1);
        }
        
        for (i = 0; i < n ; i++){	
        	
        	int rem = songs.get(i) % k;
        	if (2 * rem == k){ 
                // Then there must be even occurrences of 
                // such remainder 
                if (hm.get(rem) % 2 == 1) 
                    return 0; 
            }else if (rem == 0){ 
                // Then there must be even occurrences of 
                // such remainder 
                if (hm.get(rem) % 2 == 1) 
                    return 0; 
            }else{ 
                if (hm.get(k - rem) != hm.get(rem)) 
                    return 0; 
            } 
        }
        return k;
	}
	
	    // Returns true if arr[0..n-1] can be divided into pairs 
	    // with sum divisible by k. 
	   public static boolean canPairs(int ar[], int k){ 
	        // An odd length array cannot be divided into pairs 
	        if (ar.length % 2 == 1) 
	            return false; 
	          
	        // Create a frequency array to count occurrences 
	        // of all remainders when divided by k. 
	        HashMap<Integer, Integer> hm = new HashMap<>(); 
	          
	        // Count occurrences of all remainders 
	        for (int i = 0; i < ar.length; i++)  
	        { 
	            int rem = ar[i] % k; 
	            if (!hm.containsKey(rem))  
	            { 
	                hm.put(rem, 0); 
	            } 
	            hm.put(rem, hm.get(rem) + 1); 
	        } 
	          
	        // Traverse input array and use freq[] to decide 
	        // if given array can be divided in pairs 
	        for (int i = 0; i < ar.length; i++)  
	        { 
	             // Remainder of current element 
	            int rem = ar[i] % k; 
	              
	            // If remainder with current element divides 
	            // k into two halves. 
	            if (2 * rem == k)  
	            { 
	                // Then there must be even occurrences of 
	                // such remainder 
	                if (hm.get(rem) % 2 == 1) 
	                    return false; 
	            }  
	              
	            // If remainder is 0, then there must be two  
	            // elements with 0 remainder 
	            else if (rem == 0)  
	            { 
	                // Then there must be even occurrences of 
	                // such remainder 
	                if (hm.get(rem) % 2 == 1) 
	                    return false; 
	            } 
	              
	            // Else number of occurrences of remainder 
	            // must be equal to number of occurrences of 
	            // k - remainder 
	            else 
	            { 
	                if (hm.get(k - rem) != hm.get(rem)) 
	                    return false; 
	            } 
	        } 
	        return true; 
	    } 
	
	   //https://www.hackerrank.com/challenges/divisible-sum-pairs/problem
	public static void divisibleSumPairs(int arr[], int k) {
		// Create a frequency array to count
		// occurrences of all remainders when
		// divided by 4
		int freq[] = new int[k];
		
		int ans;
		
		for(int j=0; j < k; j++ ){
			freq[j] = 0;
		}

		for (int i = 0; i < arr.length; i++) {
			freq[arr[i] % k]++;
			//System.out.println(arr[i] % k + " :: " + freq[arr[i] % k]);
		}

		int sum = 0;
		sum += (freq[0] * (freq[0] - 1)) / 2; //check exact of k multiples n(n-1)/2
		
		for (int i = 1; i <= k / 2 && i != k - i; i++) {
			sum += freq[i] * freq[k - i];
		}
		if (k % 2 == 0) {
			sum += (freq[k / 2] * (freq[k / 2] - 1)) / 2;
		}
		
		System.out.println("sum: "  + sum);
		    
	} 
	
}
