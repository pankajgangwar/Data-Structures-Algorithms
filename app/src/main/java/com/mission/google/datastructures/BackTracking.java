package com.mission.google.datastructures;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BackTracking {
	
	public static void main(String[] args) {
		KnightTour knightTour = new KnightTour();
		//knightTourRec.isTourPossible(8);
		
		SolutionNQ nq = new SolutionNQ();
		//nq.solveNQueens();
		
		SolutionSubset subset = new SolutionSubset();
		ArrayList<Integer> mList = new ArrayList<>();
		mList.add(2);
		mList.add(4);
		mList.add(6);
		mList.add(10);
		mList.add(12);
		mList.add(16);
		//subset.subsetSums(mList, 16);
		
		//subset.printAllSubsets();
		
		int[] superSet = new int[] {2,4,6};
		//boolean isSubSetPossible = subset.subsetBackTracking(superSet, superSet.length, 16);
		//System.out.println("Is Subset possible: " + isSubSetPossible);
		
		//boolean ifSubsetPossible = subset.subsetDP(superSet, superSet.length, 50);
		//System.out.println("Is Subset possible with DP: " + ifSubsetPossible);
		
		//subset.canPartitionKSubsets(superSet, 4);
		
		SolutionRat solutionRat = new SolutionRat();
		int[][] m = new int[][] { {1, 0, 0, 0},
								  {1, 1, 0, 1},
								  {1, 1, 0, 0},
								  {0, 1, 1, 1}};
								  
		//solutionRat.printPath(m, 4);
								  
		SolutionPermutation intPerm = new SolutionPermutation();
		int[] nums = new int[] {1,2,3};
        //List<List<Integer>> result = intPerm.permute(nums);
        
       /* for(List<Integer> list : result ) {
        	System.out.println("*******************");
        	System.out.print(list);
        	System.out.println();
        }
        */
		
		//numTilePossibilities("");
		/**
		 * e 2 3
		   2 x 2
		   1 2 s
		   
		   e 1 2
		   1 x 1
		   2 1 s
		   
		   e 1 1
		   x x x
		   1 1 s
		 **/
		/*Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();

		for (int n = 0; n < t; n++) {
			int N = sc.nextInt();
			int[][] chess_board = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int a = sc.nextInt();
					chess_board[i][j] = a;
				}
			}

			KingMaxCollection tracking = new KingMaxCollection(N);
			int x = N -1;
			int y = x;

			tracking.findMaxPoints(chess_board, 0, 0, 0);


			System.out.println(tracking.maxPoints + " " + tracking.totalPaths);
		}*/
		
		int N = 3;
		String[][] chess_board = new String[][]{
												{"1","1","1"},
										  		{"1","x","1"},
										  		{"1","1","1"},
										  		};


		KingMaxCollection tracking = new KingMaxCollection(N);
		int x = N - 1;
		int y = x;
		chess_board[x][y] = "0";
		
		//int max = tracking.findMaxPoints(chess_board, x, y, N);
		//System.out.println("Max " + max);

		tracking.printMaxPossiblePath(chess_board, x, y, 0, N);
		//System.out.println(tracking.maxPoints + " " + tracking.totalPaths);
	        
	       /*  int[][] grid = new int[][] {{0,1},{0,1}};
	        
	        int N = grid.length;
	        ShortestPathInGrid pathInGrid = new ShortestPathInGrid(N);
	        pathInGrid.shortestPathBinaryMatrix(grid, 0, 0, 0, N); */

	}
	
	//https://www.codesdope.com/course/algorithms-knights-tour-problem/
	//https://dailycodingproblem.com/blog/knights-tour/
	//https://www.geeksforgeeks.org/the-knights-tour-problem-backtracking-1/
	public static class KnightTour {
		int N;
		
		public boolean isValid(int next_i, int next_j, int[][] sol) {
			if(next_i >= 1 && next_i <= N && next_j >= 1 && next_j <= N && sol[next_i][next_j] == -1 ) {
				return true;
			}
			
			return false;
		}
		
		public boolean knightTour(int i, int j, int[][] sol, int[] x_move, int[] y_move,int step_count) {
			if(step_count == N*N) {
				return true;
			}
			
			for (int k = 0; k < x_move.length; k++) {
				int next_i = x_move[k] + i;
				int next_j = y_move[k] + j;

				if (isValid(next_i, next_j, sol)) {

					sol[next_i][next_j] = step_count;
					
					if (knightTour(next_i, next_j, sol, x_move, y_move, step_count + 1)) {
						return true;
					}
					sol[next_i][next_j] = -1;
				}

			}
			return false;
		}
		
		public void isTourPossible(int N) {
			this.N = N;
			int[][] sol = new int[N + 1][N + 1];
			
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					sol[i][j] = -1;
				}
			}
			
			int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
			int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };
			
			sol[1][1] = 0;
			
			boolean isTourPossible = knightTour(0, 0, sol, x_move, y_move, 1);
			
			if (isTourPossible) {
				for (int i = 1; i <= N; i++) {
					for (int j = 1; j <= N; j++) {
						System.out.print(sol[i][j] + "\t");
					}
					System.out.println("\n");
				}
			}else {
				System.out.println("No possible tour");
			}
		}
	}
	
	static class SolutionNQ {
		int N;
		ArrayList<ArrayList<String>> mList = new ArrayList<ArrayList<String>>();
		
		public ArrayList<ArrayList<String>> solveNQueens() {
			int n = 4;
			this.N = n;
			
			int[][] board = new int[n][n];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					board[i][j] = 0;
				}
			}

			if (solveNQUtil(board, 0)) {
				return mList;
			}else {
			    System.out.println("No Solution");
			    return null;
			}
		}

		boolean res = false;
		public boolean solveNQUtil(int[][] board, int col) {
			if (col == N) {
				printSolution(board);
				return true; // All queens are placed
			}

			for (int i = 0; i < N; i++) {
				if (isSafeToMove(board, i, col)) {
					board[i][col] = 1;
					
					res = solveNQUtil(board, col + 1) || res;
					
					board[i][col] = 0;
				}
			}

			return res;

		}

		public boolean isSafeToMove(int[][] board, int rows, int cols) {
			int i, j;
			// all rows to left for this col
			for(j = cols; j >= 0; j-- )
	            if(board[rows][j] == 1)
	                return false;

			// All diagonal rows,col to the left and up
			for (i = rows, j = cols; i >= 0 && j >= 0; i--, j--)
				if (board[i][j] == 1)
					return false;
			// All diagonal rows,col to the left and down
			for (i = rows, j = cols; i < N && j >= 0; i++, j--)
				if (board[i][j] == 1)
					return false;

			return true;

		}
		
		public void printSolution(int board[][]) {

			System.out.println("");
			System.out.println("********************");
			
			ArrayList<String> mcol = new ArrayList<>();
			
			for (int i = 0; i < N; i++) {
				StringBuilder builder = new StringBuilder();
				for (int j = 0; j < N; j++) {
					if (board[i][j] == 1) {
						builder.append("Q");
						//System.out.print("Q");
					} else {
						builder.append(".");
						//System.out.print(".");
					}
				}
				System.out.print(builder.toString() + ",");
				mcol.add(builder.toString());
				
			}
			
			mList.add(mcol);
		}

	}
	
	public static class SolutionSubset {
		HashSet<ArrayList<Integer>> subsets = new HashSet<ArrayList<Integer>>();

		public void subsetSums(ArrayList<Integer> mList, int requiredSum) {

			int actualSum = 0;
			for (int j = 0; j < mList.size(); j++) {
				actualSum += mList.get(j);
			}

			if (requiredSum == actualSum) {
				System.out.println("Required sum is 0 now !!");
				subsets.add(mList);
				return;
			}

			int i = 0;

			for (; i < mList.size(); i++) {
				ArrayList<Integer> newList = new ArrayList<>();
				newList.addAll(mList);
				newList.remove(i);
				subsetSums(newList, requiredSum);
			}
		}

		/**
		 * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
		 *
		 */
		public void canPartitionKSubsets(int[] nums, int k) {
			
			ArrayList<Integer> mList = new ArrayList<>();
	        for(int i : nums){
	            mList.add(i);
	        }
	        getEqualSumSubset(mList);
	        
	        Set<Integer> mKeySet = mMap.keySet();
	        Iterator<Integer> mIterator = mKeySet.iterator();
	        while(mIterator.hasNext()) {
	        	int sum = mIterator.next();
	        	int noOfSubSets = mMap.get(sum);
	        	System.out.println("Sum: " + sum + " noOfSubSets: " + noOfSubSets);
	        }
		}
		
		//Map with sum as key and no. of subsets with this sum as value
		HashMap<Integer, Integer> mMap = new HashMap<>();
		
		public void getEqualSumSubset(ArrayList<Integer> mList) {
			if(mList.size() == 0) {
				return;
			}
			int actualSum = 0;

			for (int j = 0; j < mList.size(); j++) {
				actualSum += mList.get(j);
			}
			
			if(mMap.get(actualSum) == null) {
				mMap.put(actualSum, 1);
			}else {
				int subSetsWithThisSum = mMap.get(actualSum);
				mMap.put(actualSum, subSetsWithThisSum + 1);
			}

			int i = 0;

			for (; i < mList.size(); i++) {
				ArrayList<Integer> newList = new ArrayList<>();
				newList.addAll(mList);
				newList.remove(i);
				getEqualSumSubset(newList);
			}
		}
		
		public void printAllSubsets() {
			System.out.println("****************************************");
			int k = 1;
			for(ArrayList<Integer> list : subsets) {
				System.out.println("Subsets -" + k);
				for(int i : list) {
					System.out.print(i +"\t");
				}
				k++;
				System.out.println();
			}
		}
		
		//https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
		//Naive Recursive
		public boolean subsetBackTracking(int[] subset, int n, int sum) {
			if(sum == 0)
				return true;
			
			if(n == 0 && sum != 0)
				return false;
			
			if(subset[n-1] > sum)
				return subsetBackTracking(subset, n - 1, sum);
			
			return subsetBackTracking(subset, n - 1, sum) || subsetBackTracking(subset, n - 1, sum - subset[n-1]);
		}

		/**
		 * https://leetcode.com/problems/partition-equal-subset-sum/
		 * https://www.geeksforgeeks.org/partition-problem-dp-18/
		 * https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
		 * NP-Hard
		 * **/
		public boolean subsetDP(int [] arr, int n, int sum) {
			boolean [][] dp = new boolean [sum+1][n+1];
			
			for(int i = 0; i <= n; i++)
				dp[0][i] = true;//If Sum is zero, answer is true 
			
			for(int i = 0; i <= sum; i++)
				dp[i][0] = false;//If sum is not zero and set is empty then return false
			
			for(int i = 1; i <= sum; i++) {
				for(int j = 1; j <= n; j++) {
					dp[i][j] = dp[i][j-1];
					if(i >= arr[j-1]) {
						dp[i][j] = dp[i][j] || dp[i - arr[j-1]][j-1];
					}
				}
			}
			
			return dp[sum][n];
		}
	}
	
	static class SolutionRat {
		 public ArrayList<String> printPath(int[][] m, int n) {
	          //Your code here
	          m[0][0] = 1;
	          
	          int x_move[] = new int[] {0, 1};
	          int y_move[] = new int[] {1, 0};
	          
	          int[][] sol = new int[n][n];
	          for(int i = 0; i < n; i++) {
	        	  for(int j = 0; j < n; j++) {
		        	  sol[i][j] = 0;
		          }
	          }
	          
	          /*if(!solveRatUtil(0, 0, m, n -1 , x, y)) {
	        	  System.out.println("Solution doesn't exist !!");
	          }else {
	        	  System.out.println("Solution EXIST !!");
	          }*/
	          ArrayList<String> pathsList = new ArrayList<>();
	          
	          printAllRatPath(0, 0, m, n , sol,"", pathsList);
	          
	          printSolution(n, sol);
	          
	          return null;
	     }
		 
		private void printSolution(int N, int[][] sol) {
			// TODO Auto-generated method stub
			System.out.println("****************************");
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					System.out.print("" + sol[i][j] + " ");
				}
				System.out.println();
			}
			
		}
		boolean res = false;
		
		StringBuilder pathsBuilder = new StringBuilder();
		int t = 0;

		private void printAllRatPath(int x, int y, int[][] m, int n, int[][] sol, String path,ArrayList<String> listOfPath) {
			// TODO Auto-generated method stub
			if (x == n - 1 && y == n - 1) {
				System.out.println("Reached destination -> " + path);
				listOfPath.add(path);
				return;
			}
			if (isPathValid(x, y, m, n)) {

				m[x][y] = 0;

				printAllRatPath(x + 1, y, m, n, sol, path + "D", listOfPath);

				printAllRatPath(x, y + 1, m, n, sol, path + "R", listOfPath);

				m[x][y] = 1;
			}
		}
		
		public boolean isPathValid(int x, int y, int[][] m, int N) {
			
			 if(x >= 0 && x < N && y >= 0 && y < N && m[x][y] == 1) {
				 return true;
			 }
			 return false;
		}
		
		private boolean solveRatUtil(int x, int y, int[][] m, int n, int[] x_move, int[] y_move) {
			// TODO Auto-generated method stub
			 System.out.println(" solveRatUtil for: " + x + "," + y );
			if(x == n && y == n) {
				//printSolution(m);
				return true;
			}
			
			for(int k = 0 ; k < 2; k++ ) {
				//System.out.println("K : " + k);
				int next_x = x + x_move[k];
				int next_y = y + y_move[k];
				if(isPathValid(next_x, next_y, m, n)) {
					if(solveRatUtil(next_x, next_y, m , n , x_move, y_move)) {
						return true;
					}else {
						return false;
					}
				}
			}
			
			return false;
		}
		
	}
	
	static class SolutionStringPerm {
		ArrayList<String> all_permutations = new ArrayList<>();
	    public List<String> letterCasePermutation(String S) {
	    	findPermutations(S.toCharArray(), "", 0);
	    	return all_permutations;
	    }
	    
	    boolean res;
	    public void findPermutations(char[] arr, String ans, int index) {
	    	//System.out.println(index + " index ");
	    	
			if (arr.length == index) {
				System.out.println("all perm " + ans);
				all_permutations.add(ans);
			} else {
				char ch = arr[index];

				if (Character.isLetter(ch)) {

					//arr[index] = Character.toLowerCase(ch);
					arr[index] = Character.toUpperCase(ch);

					findPermutations(arr, ans + arr[index], index + 1);

					//arr[index] = Character.toUpperCase(ch);
					arr[index] = Character.toLowerCase(ch);

				}
				findPermutations(arr, ans + arr[index], index + 1);
			}
	    }
	}

	/**
	 * https://leetcode.com/problems/permutations/
	 */
	static class SolutionPermutation {
		//Input: [1,2,3]
	    public List<List<Integer>> permute(int[] nums) {
	    	ArrayList<Integer> mList = new ArrayList<>();

	    	for(Integer ele : nums) {
	        	mList.add(ele);
	        }
	    	ArrayList<Integer> mResult = new ArrayList<>();
	    	
	    	findPermutationRec(mList, mResult);
	        
	    	return all_permutations;
	    }
	    
	    List<List<Integer>> all_permutations = new ArrayList<>();
	    
	    public void findPermutationRec(ArrayList<Integer> nums, ArrayList<Integer> result) {
	    	if(nums.size() == 0) {
	    		System.out.println("Adding to list -> " + result);
	    		ArrayList<Integer> final_list = new ArrayList<>();
	    		final_list.addAll(result);
	    		all_permutations.add(final_list);
	    		return;
	    	}
	    	
	    	for(int i = 0; i < nums.size(); i++) {
	    		
	    		int ele_without = nums.get(i);
	    		
	    		result.add(ele_without);
	    		
	    		ArrayList<Integer> new_list = new ArrayList<>(nums);
	    		
	    		new_list.remove(i);
	    		
	    		findPermutationRec(new_list, result);
	    		
	    		System.out.println("Removing: " + ele_without);
	    		
	    		result.remove(new Integer(ele_without));
	    		
	    	}
	    }
	}
	
	/**
	 * https://leetcode.com/problems/letter-tile-possibilities
	 * 
	 * Input: "AAB"
		Output: 8
		Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
	 * 
	 * **/
	public static int numTilePossibilities(String tiles) {
		tiles = "AB";
		int[] freq = new int[26];
		
		for(char ch : tiles.toCharArray()) {
			freq[ch - 'A']++;
		}
		int possibilites = dfsPossibleTiles(freq);
		
		System.out.println("num of Tile Possible " + possibilites);
		
		return possibilites;
	}
	
	private static int dfsPossibleTiles(int[] freq) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0 ; i < freq.length; i++) {
			if(freq[i] == 0) continue;
			
			sum++; 
			freq[i]--; 
			sum+= dfsPossibleTiles(freq); 
			freq[i]++; 
		}
		return sum;
	}

		
	/**
	 * https://skillenza.com/challenge/samsung-online-challenge-software-engineer-may/checkpoint/submit/1358
	 * e 2 3
	   2 x 2
	   1 2 s
	   https://loveforprogramming.quora.com/Backtracking-Memoization-Dynamic-Programming
	   https://www.geeksforgeeks.org/maximum-path-sum-starting-cell-0-th-row-ending-cell-n-1-th-row/
	   https://www.geeksforgeeks.org/count-number-ways-reach-destination-maze/
	   https://www.geeksforgeeks.org/count-number-of-ways-to-reach-a-given-score-in-a-matrix/
	   https://www.geeksforgeeks.org/printing-longest-common-subsequence/

	 ***/
	static class KingMaxCollection {
		int maxPoints = 0;
		int totalPaths = 0;
		int[][] memo;
		int N = 0;

		boolean visited[][][] = new boolean[N][N][maxPoints];
		int dp[][][] = new int[N][N][maxPoints];

		public KingMaxCollection(int N) {
			memo = new int[N][N];
			this.N = N;
			for(int i =0 ;i < N; i++) {
				for(int j=0; j < N; j++) {
					memo[i][j] = -1;
				}
			}
		}

		public int findMaxPoints(int[][] chessboard, int x, int y, int maxCollected){
			if(x == N -1 && y == N-1){
				return maxCollected;
			}

			if(x < 0 || y < 0)
				return 0;

			System.out.println(x + " : " + y);
			if (visited[x][y][maxCollected])
				return dp[x][y][maxCollected];

			if (isPathSafe(x, y, chessboard, N)) {

				visited[x][y][maxCollected] = true;

				dp[x][y][maxCollected] = findMaxPoints(chessboard, x - 1, y, maxCollected + chessboard[x][y]) +

						findMaxPoints(chessboard, x, y - 1, maxCollected + chessboard[x][y]) +

						findMaxPoints(chessboard, x - 1, y - 1, maxCollected + chessboard[x][y]);


				return dp[x][y][maxCollected];
			}
			return 0;
		}

		public boolean isPathSafe(int x, int y, int[][] chess, int N) {

			if (x >= 0 && x < N && y >= 0 && y < N && chess[x][y] != -1) {
				return true;
			}
			return false;
		}

		/**
		 * {"1","1","1"},
		 * {"1","x","1"},
		   {"1","1","1"},
		 *                                                **/
		public int printMaxPossiblePath(String[][] chessboard, int x, int y, int sum, int N) {
			if (x == 0 && y == 0) {

				System.out.println("Max points " + sum);
				if(memo[x][y] < sum) {
					memo[x][y] = sum;
					maxPoints = sum;
					totalPaths = 1;
				}else if(memo[x][y] == sum) {
					totalPaths++;
				}
				return memo[x][y];
			}

			if (isPathValid(x, y, chessboard, N)) {

				if(memo[x][y] != -1) {
					System.out.println(" --> x,y " + x + "," + y + " cache " + memo[x][y] );
					return memo[x][y];
				}
				int pos = Integer.parseInt(chessboard[x][y]);

				sum += pos;
				//System.out.println("x,y " + x + "," + y);
				int top = printMaxPossiblePath(chessboard, x - 1, y, sum, N);
                int diagonal = printMaxPossiblePath(chessboard, x - 1, y-1, sum, N);
				int left = printMaxPossiblePath(chessboard, x, y-1, sum, N);

				int max = Math.max(top, Math.max(left, diagonal));

				//System.out.println("top: " + top +" left: " + left + " diagonal: " + diagonal);

				memo[x][y] = max - pos ;
				System.out.println("x,y " + x + "," + y + " stored "  +memo[x][y] );
				return memo[x][y];
			}
			return 0;
		}

		public boolean isPathValid(int x, int y, String[][] chess, int N) {

			if (x >= 0 && x < N && y >= 0 && y < N && !chess[x][y].equals("x")) {
				return true;
			}
			return false;
		}

	}
	
	
}
