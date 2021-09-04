package com.pkumar7.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {

    /*
    * https://leetcode.com/problems/sudoku-solver/
    * 37. Sudoku Solver
    * */
    public boolean solveSudoku(char[][] board) {
        boolean isFilled = true;
        int row_to_fill = 0, col_to_fill = 0;
        outer : for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    row_to_fill = i;
                    col_to_fill = j;
                    isFilled = false;
                    break outer;
                }
            }
        }
        if (isFilled) return true;
        for (char num = '1' ; num <= '9'; num++) {
            if (isSafe(num, row_to_fill, col_to_fill, board)) {
                board[row_to_fill][col_to_fill] = num;
                if (solveSudoku(board)) {
                    return true;
                }
                board[row_to_fill][col_to_fill] = '.';
            }
        }
        return false;
    }

    public boolean isSafe(char num, int i, int j, char[][] board) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][j] == num) return false;
        }
        for (int col = 0; col < board[0].length; col++) {
            if (board[i][col] == num) return false;
        }

        int start_row = i - i % 3;
        int start_col = j - j % 3;
        for (int row = start_row; row < start_row + 3; row++) {
            for (int col = start_col; col < start_col + 3; col++) {
                if (board[row][col] == num) return false;
            }
        }
        return true;
    }

    /*
     * 51. N-Queens
     * https://leetcode.com/problems/n-queens/
     * */
    int N;
    List<List<String>> mList = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        this.N = n;
        int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 0;
            }
        }
        solveNQUtil(board, 0);
        return mList;
    }

    public void solveNQUtil(int[][] board, int queen) {
        if (queen == N) {
            printSolution(board);
            return;
        }
        for (int i = 0; i < N; i++) {
            if (isSafeToMove(board, i, queen)) {
                board[i][queen] = 1;
                solveNQUtil(board, queen + 1);
                board[i][queen] = 0;
            }
        }
    }

    public boolean isSafeToMove(int[][] board, int rows, int cols) {
        int i,j;
        for(j = cols; j >= 0; j-- )
            if(board[rows][j] == 1)
                return false;
        for(i = rows, j = cols; i >= 0 && j >= 0; i--, j-- )
            if(board[i][j] == 1)
                return false;
        for(i = rows, j = cols; i < N && j >= 0; i++, j-- )
            if(board[i][j] == 1)
                return false;
        return true;
    }

    public void printSolution(int board[][]) {
        ArrayList<String> mcol = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    builder.append("Q");
                } else {
                    builder.append(".");
                }
            }
            mcol.add(builder.toString());
        }
        mList.add(mcol);
    }

    /* 1239. Maximum Length of a Concatenated String with Unique Characters
     * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
     * */
    public int maxLength(List<String> arr) {
        helper(arr, "", 0);
        return max;
    }

    int max = 0;
    public void helper(List<String> arr, String prev, int idx){
        for (int i = idx; i < arr.size(); i++) {
            String curr = arr.get(i);
            if(!ifStringUnique(curr)) continue;
            if(prev.isEmpty()){
                helper(arr, curr, i + 1);
            }
            if(isAllUnique(curr, prev)){
                prev += curr;
                helper(arr, prev, i + 1);
                prev = prev.substring(0, prev.length() - curr.length());
            }
        }
        if(max < prev.length()){
            max = Math.max(prev.length(), max);
        }
    }

    public boolean ifStringUnique(String a){
        char[] freqA = new char[26];
        for (char ch : a.toCharArray()){
            if(freqA[ch - 'a'] > 0) return false;
            freqA[ch - 'a']++;
        }
        return true;
    }

    public boolean isAllUnique(String a, String b){
        char[] freqA = new char[26];
        for (char ch : a.toCharArray()){
            if(freqA[ch - 'a'] > 0) return false;
            freqA[ch - 'a']++;
        }
        char[] freqB = new char[26];
        for (char ch : b.toCharArray()){
            if(freqB[ch - 'a'] > 0) return false;
            freqB[ch - 'a']++;
        }
        for (char ch : b.toCharArray()){
            if(freqA[ch - 'a'] > 0){
                return false;
            }
        }
        return true;
    }
}
