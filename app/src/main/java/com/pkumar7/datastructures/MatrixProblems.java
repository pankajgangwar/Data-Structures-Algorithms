package com.pkumar7.datastructures;;

public class MatrixProblems {

	public static void main(String[] args) {
		
	}
	
	static int findMinOpeartion(int matrix[][], int n) {
		int[] sumRow = new int[n];
		int[] sumCol = new int[n];

		for (int i = 0; i < n; ++i)

			for (int j = 0; j < n; ++j) {
				sumRow[i] += matrix[i][j];
				sumCol[j] += matrix[i][j];
			}

		int maxSum = 0;
		for (int i = 0; i < n; ++i) {
			maxSum = Math.max(maxSum, sumRow[i]);
			maxSum = Math.max(maxSum, sumCol[i]);
		}

		int count = 0;
		for (int i = 0, j = 0; i < n && j < n;) {
			int diff = Math.min(maxSum - sumRow[i], maxSum - sumCol[j]);

			matrix[i][j] += diff;
			sumRow[i] += diff;
			sumCol[j] += diff;
			count += diff;

			if (sumRow[i] == maxSum)
				++i;

			if (sumCol[j] == maxSum)
				++j;
		}
		return count;
	}
}
