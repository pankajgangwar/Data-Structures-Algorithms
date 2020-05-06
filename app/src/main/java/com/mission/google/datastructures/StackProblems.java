package com.mission.google.datastructures;

import java.util.Stack;

public class StackProblems {
	
	public static void main(String[] args) {
		//reverseStringStack();
		reverseString();
	}
	
	public static void reverseStringStack(){
		String str = "PANKAJ";
		String[] stringArr = str.split("");
		Stack<String> mStack = new Stack<>();
		for(int i=0; i<stringArr.length; i++){
			mStack.push(stringArr[i]);
		}
		
		StringBuilder strBuilder = new StringBuilder();
		
		for(int i=0; i< stringArr.length; i++){
			strBuilder.append(mStack.pop());
		}
		
		System.out.println("Reverse String: " + strBuilder.toString());
	}
	
	public static void reverseString(){
		String str = "PANKAJ";
		System.out.println(str.length());
		String[] stringArr = str.split("");
		
		int i=0,j=stringArr.length-1;
		
		while(i < j){
			String temp = stringArr[i];
			stringArr[i] = stringArr[j];
			stringArr[j] = temp;
			i++;
			j--;
		}
		
		for(int k=0; k< stringArr.length; k++){
			System.out.print(stringArr[k]);
		}
	}

}
