package com.mission.google.hashcode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FooBarChallenge {
	public static void main(String[] args) {
		int[] x = new int [] {13, 5, 6, 2, 5};
				
		int[] y = new int[]  {5, 2, 5, 13};
		
		int unique = solution(x,y);
		System.out.println(unique);
	}
	
	 public static int solution(int[] x, int[] y) {
		 HashMap<Integer,Integer> mMap = new HashMap<>();
		 
		 for(int ele : x) 
			 mMap.put(ele, 1);
		 
		 for(int ele : y) {
			 if(mMap.containsKey(ele))
				 mMap.put(ele, 0);
			 else
				 mMap.put(ele, 1);
		 }
		 
		 Set<Integer> keys = mMap.keySet();
		 Iterator<Integer> it = keys.iterator();
		 while(it.hasNext()) {
			 int key = it.next();
			 if(mMap.get(key) == 1)
				 return key;
		 }
		 return -1;
	 }
	 
}
