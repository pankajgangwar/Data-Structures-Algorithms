package com.mission.google.skillenzila;
import java.util.*;

class solution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        
        for (int n = 0; n < t; n++) {
             int N = sc.nextInt();
             String[][] chess_board = new String[N][N];
             for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                  String a = sc.next();
                  chess_board[i][j] = a;
                } 
             }
            
           // MaxPointCollectionBFS collectionBFS = new MaxPointCollectionBFS();
            
            //collectionBFS.printMaxPossiblePath(chess_board);
            
            KingMaxCollectionDP tracking = new KingMaxCollectionDP(N);
		    int x = N -1;
		    int y = x;
		    chess_board[x][y] = "2";
		    
		    long start = System.currentTimeMillis();
    		
		    tracking.printMaxPossiblePath(chess_board, x, y, 0, N);
    		if(tracking.maxPoints > 0) {
    		    tracking.maxPoints = tracking.maxPoints - 2;
    		}
    		if(tracking.maxPoints == 0) {
    		    tracking.totalPaths = 0;
    		}
		
	    	System.out.println(tracking.maxPoints + " " + tracking.totalPaths);
	    	long end = System.currentTimeMillis();
	    	long exec = end - start;
	    	System.out.println(" exec " + exec);
		
        }
        

    }
    
    static class KingMaxCollection {
		int maxPoints = 0;
		int totalPaths = 0;

		public void printMaxPossiblePath(String[][] chessboard, int x, int y, int sum, int N) {
			if (x == 0 && y == 0) {
				if (maxPoints < sum) {
					maxPoints = sum;
					totalPaths = 1;
				} else if (maxPoints > 0 && maxPoints == sum) {
					totalPaths++;
				}
				return;
			}

            
			if (isPathValid(x, y, chessboard, N)) {// Safe path

				int pos = Integer.parseInt(chessboard[x][y]);

				sum += pos;

				chessboard[x][y] = "x";

				printMaxPossiblePath(chessboard, x, y - 1, sum, N);

				printMaxPossiblePath(chessboard, x - 1, y - 1, sum, N);

				printMaxPossiblePath(chessboard, x - 1, y, sum, N);

				chessboard[x][y] = "" + pos;
			}
		}

		public boolean isPathValid(int x, int y, String[][] chess, int N) {

			if (x >= 0 && x < N && y >= 0 && y < N && !chess[x][y].equals("x") ) {
				return true;
			}
			return false;
		}
	}
    
  //https://skillenza.com/challenge//samsung-online-challenge-software-engineer-may/checkpoint/submit/1358
  	/**
  	 * e 2 3
  	   2 x 2
  	   1 2 s
  	   Should use BFS or DFS
  	   
  	   https://www.careercup.com/question?id=21423662
  	   https://www.hackerearth.com/practice/notes/dynamic-programming-problems-involving-grids/
  	   https://leetcode.com/problems/unique-paths-ii/discuss?currentPage=1&orderBy=most_votes&query=
  	   https://leetcode.com/problems/unique-paths/discuss/182143/Recursive-memoization-and-dynamic-programming-solutions
  	 ***/
  	static class KingMaxCollectionDP {
  		int maxPoints = 0;
  		int totalPaths = 0;
  		int[][] memo;

  		public KingMaxCollectionDP(int N) {
  			memo = new int[N][N];
  			for(int i =0 ;i < N; i++) {
  				for(int j=0; j < N; j++) {
  					memo[i][j] = -1;
  				}
  			}
  		}

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
  				//memo[x][y] = maxPoints;
  				return memo[x][y];
  			}

  			if (isPathValid(x, y, chessboard, N)) {
  				if(memo[x][y] != -1) {
  					return memo[x][y];
  				}
  				int pos = Integer.parseInt(chessboard[x][y]);

  				sum += pos;
  				
  				int top = printMaxPossiblePath(chessboard, x - 1, y, sum, N);
  				int left = printMaxPossiblePath(chessboard, x, y-1, sum, N);
  				int diagonal = printMaxPossiblePath(chessboard, x - 1, y-1, sum, N);
  				
  				int max = Math.max(top, Math.max(left, diagonal));
  				
  				memo[x][y] = max + pos;
  				
  				return memo[x][y];
  			}
  			return Integer.MIN_VALUE;
  		}

  		public boolean isPathValid(int x, int y, String[][] chess, int N) {

  			if (x >= 0 && x < N && y >= 0 && y < N && !chess[x][y].equals("x")) {
  				return true;
  			}
  			return false;
  		}
  	}
  	
  	static class MaxPointCollectionBFS{
  		int[][] moves = new int[][] {{-1,0}, {-1,-1}, {0,-1}};
  		
  		public int printMaxPossiblePath(String[][] board) {
  			
  			int rows = board.length;
  			int cols = board[0].length;
  			
  			int dist[] = new int[rows];
  			
  			if(!board[rows-1][cols-1].equals("s") || !board[0][0].equals("e")) {
  				return 0;
  			}
  			boolean [][] visited = new boolean [rows][cols];
  			visited[rows-1][cols-1] = true;
  			
  			Queue<int[]> queue = new LinkedList<>();
  			queue.add(new int[] {rows-1,cols-1});//Add src location
  			int max = 0;
  			
  			while(!queue.isEmpty()) {
  				int size = queue.size();
  				for(int i = 0; i < size; i++) {
  					int[] cord = queue.poll();
  					if(cord[0] == 0 && cord[1] == 0) { //We have reached the source (origin)
  						return max;
  					}
  					
  					for(int j = 0; j < moves.length; j++) {
  						int next_x = moves[j][0] + cord[0];
  						int next_y = moves[j][1] + cord[1];
  						
  						if(isSafeMove(next_x, next_y, visited, board, rows, cols)) {
  							
  							int min = Integer.MIN_VALUE;
  							int max_index = -1;
  							
  							/*if(!visited[i] && dist[i] > max ) {
									max = dist[i];
									max_index = i;
							}*/
  							
  							queue.add(new int[] {next_x, next_y});
  							visited[next_x][next_y] = true;
  							
  							int point_collected = Integer.parseInt(board[next_x][next_y]);
  							
  							System.out.println("Collected " + point_collected);
  							
  							max += Integer.parseInt(board[next_x][next_y]);
  						}
  					}
  					
  				}
  			}
  			System.out.println("Max points collected: " + max);
  			return max;
  		}
  		
  		public boolean isSafeMove(int x, int y,boolean[][] visited, String[][] board, int rows, int cols) {

  			if (x >= 0 && x < rows && y >= 0 && y < cols && !board[x][y].equals("x") && !visited[x][y] 
  					&& !board[x][y].equals("e")) {
  				return true;
  			}
  			return false;
  		}
  		
  		
  	}
}