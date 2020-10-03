package com.pkumar7.datastructures;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class Heap {
	
	class PQNode{
		public int data;
		public int nextIndex;
		public int arrayIndex;
		public PQNode(int data, int nextIndex, int arrayIndex) {
			this.data = data;
			this.nextIndex = nextIndex;
			this.arrayIndex = arrayIndex;
		}
	}
	
	public static void main(String[] args) {
		
		Heap heap = new Heap();
		
		List<String> sameCharList = new ArrayList<String>();
		sameCharList.add("a");
		sameCharList.add("i");
		sameCharList.add("pankaj");
		sameCharList.add("ajkal");
		
		for(String str: sameCharList) {
			//System.out.println(str);
		}
		
		Collections.sort(sameCharList, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				// TODO Auto-generated method stub
				if(s1.charAt(0)-'a' > s2.charAt(0) - 'b') {
					return 1;
				}else if(s1.charAt(0)-'a' < s2.charAt(0) - 'b') {
					return -1;
				}
				return 0;
			}
		});
		
		
		
		
		//Merge K sorted arrays/LinkedList using heap
		/*int k = 3, n =  4;
		int arr[][] = { {1, 3, 5, 7},
	            {2, 4, 6, 8},
	            {0, 9, 10, 11}} ;*/
		
		
		//System.out.println("Arr Len: " + arr.length);
		
		//int []res = heap.getMergedArray(arr, n, k);
		
		/*for(int i=0; i<res.length; i++){
			System.out.println("Res: " + res[i]);
		}*/
		
		/*int[] ropesArr = {4,3,2,6};
		int minCost = heap.minimumCostToConnectNRopes(ropesArr);
		System.out.println("minCost: " + minCost);*/
		
		//Find median from stream of data using heap
		/*int array[] = {10,1,2,3,4,5,6,7,8,9,10};
		double[] medians = heap.getMedians(array);
		
		for(int i=0; i< medians.length;i++){
			System.out.println("Median: " + medians[i]);
		}*/
		
		//int array[] = {9,6,1,3,12,70,43,7};
		//heap.sort(array);
		
		//heap.selectionSort(array);
		
		//heap.insertionSort(array);
		
		//heap.printArray(array);
		
		/*int kSweetness = 10;
		//int[] A = {1, 2, 3, 9, 10, 12};
		int[] A = {1,1,1};
		int operations = heap.cookies(kSweetness, A);
		System.out.println("Opeartions: " + operations);*/
		
		//https://leetcode.com/problems/kth-largest-element-in-a-stream/
		/*int[] stream = new int[] {4,5,8,2};
		int k = 3;
		KthLargest kthLargest = new KthLargest(k, stream);
		
		System.out.println(kthLargest.add(3));
		System.out.println(kthLargest.add(5));
		System.out.println(kthLargest.add(10));
		System.out.println(kthLargest.add(9));
		System.out.println(kthLargest.add(4));*/
		
		//{3,3},{5,-1},{-2,4}
		/*int[][]points = new int[][] {{3,3},{5,-1},{-2,4},{-1,1}};
		int K = 3;
		int[][] result = heap.kClosest(points, K);
		
		 for(int i = 0; i < result.length; i++) {
	         System.out.println("points: " + result[i][0] + "," + result[i][1]);
		 }*/
		 
		 //heap.frequencySort("Aabb");
		//heap.firstUniqChar("Pankaj");
		
		Integer[] nums = new Integer [] {1,2,3,1,2,2};
		//System.out.println("Status ---> " + heap.isPossible(nums));
		ArrayList<Integer> ids = new ArrayList<Integer>(Arrays.asList(nums));
		int m = 3;
		//int res = deleteProducts(ids, m);
		//System.out.println( " Res -> "+ res);
		
		/*LRUCache cache = new LRUCache(10);
		String[] operations = new String[] {"put","put","put","put","put","get","put","get","get","put","get","put","put","put",
				"get","put","get","get","get","get","put","put","get","get","get","put","put","get","put","get","put","get",
				"get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put",
				"put","get","put","put","get","put","put","put","put","put","get","put","put","get","put","get","get","get",
				"put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put","put",
				"get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put",
				"put","put","put","put","put"};
		String []values = new String[] {"10,13","3,17","6,11","10,5","9,10","13","2,19","2","3","5,25","8","9,22","5,5","1,30",
				"11","9,12","7","5","8","9","4,30","9,3","9","10","10","6,14","3,1","3","10,11","8","2,14","1","5","4","11,4",
				"12,24","5,18","13","7,23","8","12","3,27","2,12","5","2,9","13,4","8,18","1,7","6","9,29","8,21","5","6,30","1,12",
				"10","4,15","7,22","11,26","8,17","9,29","5","3,4","11,30","12","4,29","3","9","6","3,4","1","10","3,29","10,28",
				"1,20","11,13","3","3,12","3,8","10,9","3,26","8","7","5","13,17","2,27","11,15","12","9,19","2,15","3,16","1","12,17",
				"9,1","6,19","4","5","5","8,1","11,7","5,2","9,28","1","2,2","7,4","4,22","7,24","9,26","13,28","11,26"};
		
		for(int i = 0 ; i < operations.length; i++) {
			String op = operations[i];
			if(op.equals("put")) {
				int key = Integer.parseInt(values[i].split(",")[0]);
				int value = Integer.parseInt(values[i].split(",")[1]);
				cache.put(key, value);
			}else {
				int key = Integer.parseInt(values[i]);
				cache.get(key);
			}
		}*/
		int[] arr = new int[]{3, 2, 0, 1, 0, 0, 0, 0, 0, 0};
		int mostFrequent = heap.topKFrequent(arr,1).get(0);
		System.out.println( "Most frequent: " + mostFrequent);
	}
	
	public int[] getMergedArray(int arr[][],int n,int k){
		PriorityQueue<PQNode> minHeap = new PriorityQueue<>(k,new Comparator<PQNode>() {

			@Override
			public int compare(PQNode o1, PQNode o2) {
				// TODO Auto-generated method stub
				return o1.data - o2.data;
			}
		});
		
		
		//Enter first element of each array
		for(int i=0; i < k; i++){
			int firstElement = arr[i][0];
			minHeap.add(new PQNode(firstElement, 1, i));
		}
		
		int resArr[] = new int[n*k];
		
		int indexJ = 0;
		
		//Add all elements to resultant array from minHeap
		while(!minHeap.isEmpty()){
			PQNode node = minHeap.poll();
			resArr[indexJ] = node.data;
			if(node.nextIndex < n){
				minHeap.add(new PQNode(arr[node.arrayIndex][node.nextIndex], node.nextIndex+1, node.arrayIndex));
			}
			indexJ++;
		}
		return resArr;
	}
	
	public void addNumber(int number,PriorityQueue<Integer> lowers,PriorityQueue<Integer> highers){
		if(lowers.size() == 0 || number < lowers.peek()){
			lowers.add(number);
		}else{
			highers.add(number);
		}
	}
	
	public void rebalance(PriorityQueue<Integer> lowers,PriorityQueue<Integer> highers){
		PriorityQueue<Integer> smallerHeap = lowers.size() < highers.size() ? lowers : highers;
		PriorityQueue<Integer> biggerHeap = highers.size() > lowers.size() ? highers : lowers;
		if(biggerHeap.size() - smallerHeap.size() >= 2){
			smallerHeap.add(biggerHeap.poll());
		}
	}
	
	public double getMedian(PriorityQueue<Integer> lowers,PriorityQueue<Integer> highers){
		PriorityQueue<Integer> smallerHeap = lowers.size() < highers.size() ? lowers : highers;
		PriorityQueue<Integer> biggerHeap = highers.size() > lowers.size() ? highers : lowers;
		
		if(smallerHeap.size() == biggerHeap.size()){
			return (double)(smallerHeap.peek() + biggerHeap.peek())/2;
		}else{
			return biggerHeap.peek();
		}
	}
	
	public double[] getMedians(int[] array){
		double []medians = new double[array.length];
		PriorityQueue<Integer> lowers = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				// TODO Auto-generated method stub
				return -1 * a.compareTo(b);
			}
		});
		
		PriorityQueue<Integer> highers = new PriorityQueue<Integer>();
		
		for(int i=0; i <array.length; i++){
			int number = array[i];
			addNumber(number,lowers,highers);
			rebalance(lowers,highers);
			medians[i] = getMedian(lowers,highers);
		}
		return medians;
	}
	
	//Connect n ropes with minimum cost
	private int minimumCostToConnectNRopes(int ropesArr[]){
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		
		for(int i=0; i<ropesArr.length; i++){
			int ropeLength = ropesArr[i];
			minHeap.add(ropeLength);
		}
		
		int minCost =0;
		
		
		while(!minHeap.isEmpty() && minHeap.size() >= 2){
			int sum = minHeap.poll() + minHeap.poll();
			minCost = minCost + sum;
			minHeap.add(sum);
		}
		return minCost;
		
	}
	//https://www.hackerrank.com/challenges/jesse-and-cookies/problem
	// K: minimum sweetness required
	// A[] : Array of cookies
	private int cookies(int k, int[] A) {
       PriorityQueue<Integer> minHeap = new PriorityQueue<>();
       for(int i=0; i< A.length;i++ ){
    	   minHeap.add(A[i]);
       }
       int operations = 0;
       while(!minHeap.isEmpty()){
    	   if(minHeap.peek() >= k){
    		   break;
    	   }
			if (minHeap.size() >= 2) {
				int first = minHeap.poll();
				int second = minHeap.poll();
				int sweetness = getSweetness(first, second);
				minHeap.add(sweetness);
			}else{
				return -1;
			}
    	   operations++;
       }
       return operations;
    }
	
	private int getSweetness(int first,int second){
		return (1*first) + (2*second);
	}
	
	//HeapSort Algo
	private void sort(int arr[]){
		int n = arr.length;
		for(int i=n/2-1; i>=0; i--){
			heapify(arr,n,i);
		}
		
		for(int i=n-1; i>=0; i--){
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			heapify(arr, i, 0);
		}
	}

	private void heapify(int[] arr, int n, int i) {
		// TODO Auto-generated method stub
		int largest = i;
		int left = 2*i+1;
		int right = 2*i+2;
		
		if(left < n && arr[left] > arr[largest] )
			largest = left;
		if(right < n && arr[right] > arr[largest] )
			largest = right;
		
		if(largest != i){
			int swap = arr[i];
			arr[i] = arr[largest];
			arr[largest] = swap;
			heapify(arr, n, largest);
		}
	}
	
	//SelectionSort algorithm
	private void selectionSort(int arr[]){
		System.out.println("############ Selection Sort ########### ");
		for(int i=0; i < arr.length; i++){
			int min = i;
			for(int j=i+1; j< arr.length; j++){
				if(arr[min] > arr[j]){
					min = j;
				}
			}
			if (min != i) {
				int swap = arr[min];
				arr[min] = arr[i];
				arr[i] = swap;
			}
		}
	}
	
	private void insertionSort(int arr[]){
		System.out.println("############ Insertion Sort ########### ");
		for(int i=1; i < arr.length; i++){

			int key = arr[i];
			
			int j = i-1;
			
			while( j>= 0 && arr[j] > key){
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = key;
		}
	}
	
	/* A utility function to print array of size n */
    private void printArray(int arr[]) {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
    
    static class KthLargest {
    	PriorityQueue<Integer> mCurrentHeap = new PriorityQueue<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer arg0, Integer arg1) {
				// TODO Auto-generated method stub
				return arg1 - arg0;
			}
    		
		});
    	
    	PriorityQueue<Integer> mMaxHeap = new PriorityQueue<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer arg0, Integer arg1) {
				// TODO Auto-generated method stub
				return arg1 - arg0;
			}
    		
		});
    	
    	
    	
    	int kthLargest;
    	
    	ArrayList<Integer> mSortedList = new ArrayList<>();
    	
    	//PriorityQueue<Integer> mMaxHeap;
    	// Use min heap for this
	    public KthLargest(int k, int[] nums) {
	        kthLargest = k;
	        for(int ele : nums) {
	        	mCurrentHeap.add(ele);
	        }
	        
	        Collections.sort(mSortedList,Collections.reverseOrder());
	       // mSortedList.get(index)
	       
	    }
	    
	    public int add(int val) {
	        mCurrentHeap.add(val);
	        mMaxHeap.addAll(mCurrentHeap);
	        int k = kthLargest;
	        
	        System.out.println("***********************");
	        
	        for(int ele : mMaxHeap) {
	        	System.out.print(ele + " :");
	        }
	        
	        while(k > 1) {
	        	System.out.println("Removing ");
	        	mMaxHeap.poll();
	        	k--;
	        }
	        int result = mMaxHeap.peek();
	        mMaxHeap.clear();
	        return result;
	    }
    }
    
    class Point{
    	double distanceFromOrigin;
    	int pointA, pointB;
    	public Point(double distance, int pointA, int pointB) {
    		this.distanceFromOrigin = distance;
    		this.pointA = pointA;
    		this.pointB = pointB;
    	}
    }
    //https://leetcode.com/problems/k-closest-points-to-origin/
    //points = [[1,3],[-2,2]], K = 1
    public int[][] kClosest(int[][] points, int K) {
    	
        PriorityQueue<Point> mMaxHeap = new PriorityQueue<>(new Comparator<Point>() {

			@Override
			public int compare(Point p1, Point p2) {
				// TODO Auto-generated method stub
				if(p1.distanceFromOrigin > p2.distanceFromOrigin) {
					return -1;
				}
				else if(p1.distanceFromOrigin < p2.distanceFromOrigin) {
					return 1;
				}
				return 0;
			}
		});
        
        for(int i = 0; i < points.length; i++) {
        	int a = points[i][0];
        	int b = points[i][1];
        	double distanceFromOrigin = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        	
        	System.out.println("Distance: " + distanceFromOrigin + " Points " + a + "," + b);
        	
        	Point mPoint = new Point(distanceFromOrigin, a, b);
        	
        	mMaxHeap.offer(mPoint);
        	
        	while(mMaxHeap.size() > K) {
        		mMaxHeap.poll();
        	}
        }
        int i = 0;
        
        int[][] closestPoints = new int[mMaxHeap.size()][2];
        
        while(!mMaxHeap.isEmpty()) {
        	Point currentPoint = mMaxHeap.poll();
        	closestPoints[i][0] = currentPoint.pointA;
        	closestPoints[i][1] = currentPoint.pointB;
        	i++;
        }
        return closestPoints;
    }
    
    //https://leetcode.com/problems/top-k-frequent-elements/
    static class Element{
        int val;
        int frequency;
        public Element(int val){
            this.val = val;
        }
    }
    
    public List<Integer> topKFrequent(int[] nums, int k) {
    	
    	Map<Integer, Integer> mMap = new HashMap<>();
    	
    	PriorityQueue<Element> mMinHeap = new PriorityQueue<>(new Comparator<Element>(){
            @Override
 			public int compare(Element e1, Element e2) {
 				// TODO Auto-generated method stub
 				if(e1.frequency > e2.frequency) {
 					return 1;
 				}
 				else if(e1.frequency < e2.frequency) {
 					return -1;
 				}
 				return 0;
 			} 
         });
        
    	for(int i = 0; i < nums.length; i++ ){
            int thisElement = nums[i];
            if(mMap.get(thisElement) == null) {
            	mMap.put(thisElement,1);
            }else {
            	int frequencyForThis = mMap.get(thisElement); 
            	System.out.println("thisElement " +  thisElement + " frequencyForThis : " + frequencyForThis);
                mMap.put(thisElement, frequencyForThis+1);
            }
        }
    	
    	
    	for(Integer entry : mMap.keySet()) {
    		Element ele = new Element(entry);
    		ele.frequency = mMap.get(entry);
    		
    		System.out.println("Adding: " + ele.val + " freq: " + ele.frequency);
    		mMinHeap.offer(ele);
    		
    		while(mMinHeap.size() > k) {
    			Element removed = mMinHeap.poll();
    			System.out.println("Removing: " + removed.val + " freq: " + removed.frequency);
    			
    		}
    	}
    	
    	List<Integer> mList = new ArrayList<>();
    	while(!mMinHeap.isEmpty()) {
    		Element element = mMinHeap.poll();
    		mList.add(element.val);
    	}
    	Collections.reverse(mList);
    	return mList;
    }

	public List<Integer> usingBucketSort(int[] nums, int k){
		List<Integer>[] bucket = new List[nums.length];
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
		}

		for(Map.Entry<Integer, Integer> entry : map.entrySet()){
			List<Integer> list = null;
			if(bucket[entry.getValue()] == null){
				bucket[entry.getValue()] = new ArrayList<>();
			}
			bucket[entry.getValue()].add(entry.getKey());
		}

		List<Integer> result = new ArrayList<>(k);
		for (int i = bucket.length - 1; i >= 0 && result.size() < k ; --i) {
			if(bucket[i] != null){
				result.addAll(bucket[i]);
			}
		}
		return result;
	}

	public List<Integer> usingTreeMap(int[] nums, int k){

    	Map<Integer, Integer> map = new HashMap<>();

    	for (int n : nums){
    		map.put(n , map.getOrDefault(n, 0) + 1);
		}

    	Set<Integer> keys = map.keySet();
		TreeMap<Integer, List<Integer>> treeMap = new TreeMap<>(Collections.reverseOrder());
    	for(int ele : keys){
    		int freq = map.get(ele);
    		if(treeMap.get(freq) == null){
    			List<Integer> list = new ArrayList<>();
    			list.add(ele);
    			treeMap.put(freq, list);
			}else{
    			treeMap.get(freq).add(ele);
			}
		}
    	List<Integer> result = new ArrayList<>();
    	Set<Integer> treeKey = treeMap.keySet();
    	Iterator<Integer> it = treeKey.iterator();
    	while (it.hasNext() && result.size() < k){
			result.addAll(treeMap.get(it.next()));
		}
    	return result;
	}
    
    //https://leetcode.com/problems/sort-characters-by-frequency/
    class CharFreq {
		char c;
		int feq;

		public CharFreq(char c) {
			this.c = c;
		}
	}
	
    public String frequencySort(String s) {
    	s = "";
    	if(s == null || s.isEmpty()) {
    		return null;
    	}
    	char[] arr = s.toCharArray();
    	
    	Map<Character, Integer> mMap = new HashMap<>();
    	for(int i = 0; i < arr.length; i++) {
    		char thisChar = arr[i];
    		
    		if(mMap.get(thisChar) == null) {
    			mMap.put(thisChar, 1);
    		}else {
    			int feq = mMap.get(thisChar);
    			mMap.put(thisChar, feq + 1);
    		}
    	}
    	
    	PriorityQueue<CharFreq> mMaxHeap = new PriorityQueue<>(new Comparator<CharFreq>() {

			@Override
			public int compare(CharFreq ch1, CharFreq ch2) {
				// TODO Auto-generated method stub
				if(ch1.feq > ch2.feq) {
					return -1;
				}else if(ch1.feq < ch2.feq) {
					return 1;
				}
				return 0;
			}
		});
    	
    	for(char keys : mMap.keySet()) {
    		CharFreq charFr = new CharFreq(keys);
    		charFr.feq = mMap.get(keys);
    		mMaxHeap.offer(charFr);
    	}
    	
    	StringBuilder result = new StringBuilder();
    	
    	while(!mMaxHeap.isEmpty()) {
    		CharFreq charFreq = mMaxHeap.poll();
    		int frequency = charFreq.feq;
    		while(frequency > 0) {
    			result.append(charFreq.c);	
    			frequency--;
    		}
    	}
    	System.out.println(result);
    	return result.toString();
    }
    
    //https://leetcode.com/problems/first-unique-character-in-a-string/
    public int firstUniqChar(String s) {
    	s = "leetcode";
    	HashMap<Character, Integer> mMap = new HashMap<>();
        char[] arr = s.toCharArray();
        
        for(int i = 0; i < arr.length; i++){
        	char key = arr[i];
        	 System.out.println(" Key: " + key);
        	if(mMap.get(key) == null) {
        		mMap.put(key, 1);	
        	}else {
        		int freq = mMap.get(key);
        		mMap.put(key, freq + 1);
        	}
        }
        
        int minIndex = Integer.MAX_VALUE;
        for(char key : mMap.keySet()) {
        	if(mMap.get(key) == 1) {
        		int thisIndex = s.indexOf(key);
        		 System.out.println(" Key: " + key + " thisIndex: " + thisIndex);
        		if(minIndex > thisIndex) {
        			minIndex = thisIndex;
        		}
        	}
        }
        System.out.println(minIndex);
        if(minIndex == Integer.MAX_VALUE) {
        	return -1;
        }
        return minIndex;
    }
    
  //https://leetcode.com/contest/weekly-contest-137/problems/last-stone-weight/
  	public int lastStoneWeight(int[] stones) {
          PriorityQueue<Integer> mMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
          int i = 0;
          for(; i < stones.length; i++) {
          	mMaxHeap.offer(stones[i]);
          }
          
          while(mMaxHeap.size() > 1) {
          	int max = mMaxHeap.poll();
          	int secondMax = mMaxHeap.poll();
          	System.out.println(max + ":: " + secondMax);
          	if(max > secondMax) {
          		mMaxHeap.offer(max - secondMax);
          	}
          }
          if(mMaxHeap.size() == 1)return mMaxHeap.peek();
          return 0;
      }
  	
  	//https://leetcode.com/problems/split-array-into-consecutive-subsequences/
  	/*
  	 * Input: [1,2,3,3,4,5]
	   Output: True
	   Explanation:
	   		You can split them into two consecutive subsequences : 
			1, 2, 3
			3, 4, 5
  	 */
	public boolean isPossible(int[] nums) {
		Map<Integer,Integer> freq = new HashMap<>();
		Map<Integer,Integer> append_freq = new HashMap<>();
		
		for(int ele : nums) {
			freq.put(ele, freq.getOrDefault(ele, 0) + 1);
		}
		
		for(int ele : nums) {
			if(freq.get(ele) == 0) continue;
			//else if()
		}
		return true;
    }
	
	//DialPad coding round
	 public static int deleteProducts(List<Integer> ids, int m) {
		    // Write your code here
		 Map<Integer, Integer> mMap = new HashMap<>();
	    	
	    	PriorityQueue<Element> mMinHeap = new PriorityQueue<>(new Comparator<Element>(){
	            @Override
	 			public int compare(Element e1, Element e2) {
	 				// TODO Auto-generated method stub
	 				if(e1.frequency > e2.frequency) {
	 					return 1;
	 				}
	 				else if(e1.frequency < e2.frequency) {
	 					return -1;
	 				}
	 				return 0;
	 			} 
	         });
	        
	    	for(int i = 0; i < ids.size(); i++ ){
	            int thisElement = ids.get(i);
	            if(mMap.get(thisElement) == null) {
	            	mMap.put(thisElement,1);
	            }else {
	            	int frequencyForThis = mMap.get(thisElement); 
	            	//System.out.println("thisElement " +  thisElement + " frequencyForThis : " + frequencyForThis);
	                mMap.put(thisElement, frequencyForThis+1);
	            }
	        }
	    	
	    	
	    	for(Integer entry : mMap.keySet()) {
	    		Element ele = new Element(entry);
	    		ele.frequency = mMap.get(entry);
	    		
	    		System.out.println("Adding: " + ele.val + " freq: " + ele.frequency);
	    		mMinHeap.offer(ele);
	    	}
	    	
	    	while(!mMinHeap.isEmpty() && m > 0) {
	    		Element element = mMinHeap.poll();
	    		element.frequency--;
	    		System.out.println("Reducing ele with freq 0 : " + element.val);
	    		if(element.frequency > 0) {
	    			mMinHeap.offer(element);
	    		}
	    		m--;
	    	}
	    	
	    	Set<Integer> all_unique_products = new HashSet<>();
	    	while(!mMinHeap.isEmpty()) {
	    		System.out.println( " Ele with heap"+ mMinHeap.peek().val);
	    		all_unique_products.add(mMinHeap.poll().val);
	    	}
	    	return all_unique_products.size();
	 }
	 
	 /**
	  * 378. Kth Smallest Element in a Sorted Matrix
	  * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
	  * 
	  * matrix = [
			   		[ 1,  5,  9],
			   		[10, 11, 13],
			   		[12, 13, 15]
				 ],
		k = 8,
		return 13.
	  * **/
	 public int kthSmallest(int[][] matrix, int k) {
		//return kthSmallestBest(matrix, k);
		 double[][] mat = new double[matrix.length][matrix[0].length];
		 for (int i = 0; i < matrix.length; i++) {
			 for (int j = 0; j < matrix[i].length; j++) {
				 mat[i][j] = (double) matrix[i][j];
			 }
		 }
		 for (int i = 0; i < mat.length; i++) {
			 System.out.println(Arrays.toString(mat[i]));
		 }
		 double res = kthSmallestBest(mat, k);
		 System.out.println("res = " + res);
		 return (int)res;
	 }

	public double kthSmallestBest(double[][] matrix, int k) {
		int m = matrix.length;
		int n = matrix[0].length;

		double low = matrix[0][0];
		double high = matrix[m-1][n-1];

		while(low < high) {
			double mid = low + (high - low) / 2;

			int j = n - 1;
			int count = 0;
			for(int i = 0; i < m; i++) {
				while(j >= 0 && matrix[i][j] > mid) {
					j--;
				}
				count += (j + 1);//Count the number of elements less than mid
			}
			if(count < k) low = mid + 1; // if smaller elements are less than k, increase the range
			else high = mid; // otherwise smaller elements are more than k, reduce the range
		}
		return low;
	}


	public int kthSmallestBest(int[][] matrix, int k) {
		int m = matrix.length;
		int n = matrix[0].length;

		int low = matrix[0][0];
		int high = matrix[m-1][n-1];

		while(low < high) {
			int mid = low + (high - low) / 2;

			int j = n - 1;
			int count = 0;
			for(int i = 0; i < m; i++) {
				while(j >= 0 && matrix[i][j] > mid) {
					j--;
				}
				count += (j + 1);//Count the number of elements less than mid
			}
			if(count < k) low = mid + 1; // if smaller elements are less than k, increase the range
			else high = mid; // otherwise smaller elements are more than k, reduce the range
		}
		return low;
	}


	public int kthSmallestBetter(int[][] matrix, int k) {
		PriorityQueue<int[]> mMinHeap = new PriorityQueue<>((a,b) -> a[0] - b[0]);
		for (int i = 0; i < matrix[0].length; i++) {
			mMinHeap.offer(new int[]{matrix[0][i], 0, i});
		}
		int[] curr = new int[3];
		while (k > 0){
			curr = mMinHeap.poll();
			int next_ele = 0;
			int row = curr[1]+1;
			int col = curr[2];
			System.out.println("Next location " + row  + "," + col);
			if(row >= matrix.length) {
				next_ele = Integer.MAX_VALUE;
			}else{
				next_ele = matrix[row][col];
			}
			mMinHeap.offer(new int[]{next_ele, row, col});
			k--;
		}
		return curr[0];
	}

	public int kthSmallestWorst(int[][] matrix, int k) {
		PriorityQueue<Integer> mMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
		for(int rows = 0; rows < matrix.length; rows++) {
			for(int columns = 0; columns < matrix[0].length; columns++) {
				System.out.println("Matrix ele: " + matrix[rows][columns]);
				mMaxHeap.offer(matrix[rows][columns]);
			}
		}

		while(mMaxHeap.size() > k && !mMaxHeap.isEmpty()) {
			int removed = mMaxHeap.poll();
			System.out.println("Removed " + removed);
		}

		return mMaxHeap.peek();
	}
	 

	static class LRUCache {
		class Element {
			int key;
			int val;
			long last_used;

			public Element(int key, int val) {
				this.key = key;
				this.val = val;
				last_used = System.currentTimeMillis();
			}
			public void updateAccessTime() {
				last_used = System.currentTimeMillis();
			}
		}

		int allowedCapacity;
		PriorityQueue<Element> mMinHeap = new PriorityQueue<Element>(new Comparator<Element>() {

			@Override
			public int compare(Element e1, Element e2) {
				// TODO Auto-generated method stub
				if(e1.last_used > e2.last_used) {
					return 1;
				}else if(e1.last_used < e2.last_used) {
					return -1;
				}
				return 0;
			}
		});

		public LRUCache(int capacity) {
			allowedCapacity = capacity;
		}

		public int get(int key) {
			Iterator<Element> it = mMinHeap.iterator();
			Element to_update = null;
			while(it.hasNext()) {
				Element element = it.next();
				if(element.key == key) {
					element.updateAccessTime();
					//System.out.println("Returning " + element.val + " for " + key);
					//System.out.println(mMinHeap.peek().last_used + " <-- fre: val --> " + mMinHeap.peek().val);
					to_update = element;
					break;
				}
			}
			if(to_update != null) {
				 mMinHeap.remove(to_update);
				 mMinHeap.offer(to_update);
				 return to_update.val;
			}
			System.out.println("Key " + key + " not found -1");
			return -1;
		}

		public void put(int key, int value) {
			if(mMinHeap.size() >= allowedCapacity) {
				Element ele = mMinHeap.poll();
				//System.out.println("Removing element to maintain its capacity value " + ele.key);
			}
			Iterator<Element> it = mMinHeap.iterator();
			Element to_update = null;
			while(it.hasNext()) {
				Element element = it.next();
				if(element.key == key) {
					 element.val = value;//Update the value
					 element.updateAccessTime();
					 to_update = element;
					 //System.out.println("Updating value " + value + " for " + key);
					 break;
				}
			}
			if(to_update != null) {
				 mMinHeap.remove(to_update);
				 mMinHeap.offer(to_update);
			}else {
				Element element = new Element(key, value);
				System.out.println("Inserting " + key);
				mMinHeap.offer(element);
			}
		}
	}

}
