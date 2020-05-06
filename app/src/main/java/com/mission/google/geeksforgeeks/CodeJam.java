package com.mission.google.geeksforgeeks;

import java.util.Scanner;

public class CodeJam {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

	    int testcases = scanner.nextInt();
	    for(int i=0; i < testcases; i++){
	        String sum = scanner.next();
	        int k = scanner.nextInt();
	        System.out.println("Sum: " + sum + " k " + k );
	    }
	}
}
