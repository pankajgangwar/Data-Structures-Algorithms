package com.pkumar7.hashcode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			do{
				in.nextInt();
			}while(in.hasNextLine());
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int case_number = 1; case_number <= t; ++case_number) {
          int number_of_instructins = in.nextInt();
          int rows = in.nextInt();
          int columns = in.nextInt();
          int start_row = in.nextInt();
          int start_col = in.nextInt();
          
          System.out.println(": instr: " + number_of_instructins + " rows--> " + rows + 
	      		  " columns-> " + columns + " start_row-> " + start_row + " start->col: " + start_col );
	        
          String directions = in.next();
	      System.out.println("Given Directions " + directions);
	      String output = getReobotFinalPos(directions.toCharArray(), rows, columns, start_row, start_col, case_number);
	      System.out.println(output);
        }
      }
      
      static String getReobotFinalPos(char[] directions, int rows, int cols, int x, int y, int case_number) {
		
		int[][] grid = new int[rows][cols];
		
		System.out.println(rows + " :: " + cols);
		
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				grid[row][col] = -1;
			}
		}
		
		x = x -1;
		y = y -1;
		System.out.println("************** "+ x + ","+y);
		
		grid[x][y] = 1;
		
		for (int i = 0; i < directions.length; i++) {
			char dir = directions[i];
			switch (dir) {
				case 'E': 
					y++;
					while(grid[x][y] == 1) {
						System.out.println("E incr" + grid[x][y]);
						y++;
					}
					System.out.println(x + ","+y);
					grid[x][y] = 1;
					
					break;
	
				case 'W':
					y--;
					while(grid[x][y] == 1) {
						System.out.println("W dec " + grid[x][y]);
						y--;
					}
					System.out.println("W incr " + x+"," + y);
					grid[x][y] = 1;
					break;
					
				case 'N':
					x--;
					while(grid[x][y] == 1) {
						System.out.println("N decr" + grid[x][y]);
						x--;
					}
					System.out.println("N incr "+ x+"," + y);
					grid[x][y] = 1;
					break;
	
				case 'S':
					x++;
					while(grid[x][y] == 1) {
						System.out.println("S decr " + grid[x][y]);
						x++;
					}
					System.out.println("S incr " + x+"," + y);
					grid[x][y] = 1;
					break;
			}
		}
		
		System.out.println( x + ": " + y);
		
		x++;
		y++;
		
		return "Case #"+case_number+": " + x +" "+ y;
	}
}
