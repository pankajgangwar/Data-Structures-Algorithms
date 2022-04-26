package com.pkumar7.datastructures;;

public class MatrixProblems {

	public static void main(String[] args) {
		
	}

	/*
	 * 1914. Cyclically Rotating a Grid
	 * https://leetcode.com/problems/cyclically-rotating-a-grid/
	 * */
	public int[][] rotateGrid(int[][] grid, int k) {
		int row = grid.length;
		int col = grid[0].length;
		int top = 0, left = 0, right = col - 1, bottom = row - 1;

		while (top < bottom && left < right){
			int[] a = grid[top];
			int localK = k % ((bottom - top) * 2 + ((right - left) * 2));
			for (int i = 0; i < localK; i++) {
				int temp = grid[top][left];

				for (int j = left; j < right ; j++) {
					grid[top][j] = grid[top][j+1];
				}

				for (int j = top; j < bottom ; j++) {
					grid[j][right] = grid[j + 1][right];
				}

				for (int j = right; j > left ; j--) {
					grid[bottom][j] = grid[bottom][j-1];
				}
				for (int j = bottom; j > top ; j--) {
					grid[j][left] = grid[j-1][left];
				}
				grid[top+1][left] = temp;
			}
			top++;
			bottom--;
			left++;
			right--;
		}
		return grid;
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
