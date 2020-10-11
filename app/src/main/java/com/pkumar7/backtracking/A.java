package com.pkumar7.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {
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
