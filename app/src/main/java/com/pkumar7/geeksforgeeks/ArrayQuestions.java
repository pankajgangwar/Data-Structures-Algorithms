package com.pkumar7.geeksforgeeks;

import java.util.Stack;

public class ArrayQuestions {
	
public static void main(String[] args) {
	
		ArrayQuestions mArrays = new ArrayQuestions();
		
		int arr[] = {9,3,12,60,45,5};
		
		//System.out.println("######## Unsorted Arr ############");
		//mArrays.printArr(arr);
		
		//int[] A = {1, 3, 6, 4, 1, 2};
		int [] A = {-3, 2, -6, 3, 4};
		
		//System.out.println("int: " + mArrays.findMissing(A, A.length));
		
		//mArrays.findNumberOfMissingChar("");
		
		//int maxHeight = mArrays.getMaxStairHeights(16);
		//System.out.println("Max Height: " + maxHeight);
		
		int twoDimArr[][] = new int[][]{
			{1,2,3,4},
			{5,6,7,8},
			{9,10,11,12},
			{13,14,15,16}
		};
		
		//mArrays.printSpiral(twoDimArr);
		
		mArrays.stringCompression();
	}

    public int getMaxStairHeights(int sampleSize){
    	Stack<Integer> mStack = new Stack<>();
    	for(int i=1; i <= sampleSize; i++){
    		sampleSize = sampleSize - i;
    		mStack.push(i);
    		if(sampleSize < i){
    			break;
    		}
    	}
    	return mStack.peek();
    }

	/*
	 * Utility function that puts all non-positive (0 and negative) numbers on
	 * left side of arr[] and return count of such numbers
	 */
	static int segregate(int arr[], int size) {
		int j = 0, i;
		for (i = 0; i < size; i++) {
			if (arr[i] <= 0) {
				int temp;
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				// increment count of non-positive
				// integers
				j++;
			}
		}

		return j;
	}

	/*
	 * Find the smallest positive missing number in an array that contains all
	 * positive integers
	 */
	int findMissingPositive(int arr[], int size) {
		int i;

		// Mark arr[i] as visited by making
		// arr[arr[i] - 1] negative. Note that
		// 1 is subtracted because index start
		// from 0 and positive numbers start from 1
		System.out.println("Size: " + size);
		for (i = 0; i < size; i++) {
			System.out.println("Element: " + arr[i] + " " + (Math.abs(arr[i]) - 1) + " && ");
			if (Math.abs(arr[i]) - 1 < size && arr[Math.abs(arr[i]) - 1] > 0) {
				arr[Math.abs(arr[i]) - 1] = -arr[Math.abs(arr[i]) - 1];
			}
		}

		// Return the first index value at which
		// is positive
		printArr(arr);

		for (i = 0; i < size; i++) {
			if (arr[i] > 0) {
				System.out.println("Arr[i] " + arr[i] + " :i: " + i);
				return i + 1; // 1 is added becuase indexes
								// start from 0
			}
		}

		System.out.println("Returning size+1 " + size + 1);

		return size + 1;
	}

	/*
	 * Find the smallest positive missing number in an array that contains both
	 * positive and negative integers
	 */
	int findMissing(int arr[], int size) {
		// First separate positive and
		// negative numbers
		int shift = segregate(arr, size);
		int arr2[] = new int[size - shift];
		int j = 0;
		for (int i = shift; i < size; i++) {
			arr2[j] = arr[i];
			j++;
		}
		// Shift the array and call
		// findMissingPositive for
		// positive part
		return findMissingPositive(arr2, j);
	}
	
	private void printArr(int arr[]) {
		// TODO Auto-generated method stub
		for(int i=0; i < arr.length; i++){
			System.out.print(arr[i] + " \t");
		}
		System.out.print("\n");
	}
	
	private void findNumberOfMissingChar(String seq){
		if(seq.length() == 0){
			System.out.println("");
		}
		String arr[] = seq.split("");
		Stack<String> mStack = new Stack<>();
		Stack<String> mTempStack = new Stack<>();
		for(int i=0; i< arr.length; i++){
			if(arr[i].equals("(")){
				mStack.push(arr[i]);
			}else if(mStack.size() > 0){
				mStack.pop();
			}else{
				mTempStack.push(arr[i]);
			}
		}
		int totalReq = mStack.size() + mTempStack.size();
		System.out.println("Paranthesis required: " + totalReq);
	}
	
	public int minAddToMakeValid(String S) {
        if(S.isEmpty())
            return 0;
        if(S.length() == 1)
            return 1;
        Stack<Character> parenthesesStack = new Stack<>();
        for(char c : S.toCharArray()) {
            if(parenthesesStack.isEmpty())
                parenthesesStack.push(c);
            else if (parenthesesStack.peek() == '(' && c == ')')
                parenthesesStack.pop();
            else
                parenthesesStack.push(c);
        }
        return parenthesesStack.size();
    }
	
	public void printSpiral(int arr[][]){
		int length = arr.length;
		System.out.println("Length: " + length);
		for(int i=0; i<length; i++){
			for(int j=0; j<arr[i].length; j++){
				System.out.print(arr[i][j]+"\t");
				break;
			}
		}
	}
	
	public void stringCompression() {
		String sample = "abcdefghijklm";
		StringBuilder builder = new StringBuilder();
		int countVal = 0;
		for(int i=0; i < sample.length(); i++) {
			countVal++;
			if(i+1 >= sample.length() || sample.charAt(i) != sample.charAt(i+1)) {
				builder.append(sample.charAt(i));
				builder.append(countVal);
				countVal = 0;
			}
		}
		String finalCompressedString = sample.length() > builder.length() ? builder.toString() : sample;
		System.out.println("Compressed string: " + finalCompressedString);
	}

}
