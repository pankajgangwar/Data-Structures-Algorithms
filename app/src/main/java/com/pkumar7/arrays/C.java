package com.pkumar7.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pankaj Kumar on 10/December/2020
 */
class C {
    public static void main(String[] args) {

    }

    /* 1380. Lucky Numbers in a Matrix
     * https://leetcode.com/problems/lucky-numbers-in-a-matrix/
     * */
    public List<Integer> luckyNumbers (int[][] matrix) {
        int[] rows = new int[matrix.length];
        int[] cols = new int[matrix[0].length];
        List<Integer> res = new ArrayList<>();
        List<Integer> maxInCols = new ArrayList<>();
        for (int i = 0; i < matrix[0].length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                max = Math.max(max, matrix[j][i]);
            }
            maxInCols.add(max);
        }
        for (int i = 0; i < matrix.length; i++) {
            int min = Arrays.stream(matrix[i]).min().getAsInt();
            if(maxInCols.contains(min)){
                res.add(min);
            }
        }
        return res;
    }
}
