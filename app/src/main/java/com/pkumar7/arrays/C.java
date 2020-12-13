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

    /*
     * 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
     * https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
     * */
    public int minPartitions(String str) {
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            max = Math.max(max, str.charAt(i) - '0');
        }
        return max;
    }

    public int solution(String str){
        StringBuilder out = new StringBuilder(str);
        int res = 0;
        while (out.length() > 0){
            StringBuilder tempB = new StringBuilder(out);
            StringBuilder mB = new StringBuilder();
            StringBuilder tempres = new StringBuilder();
            for (int i = tempB.length() -1; i >= 0; --i) {
                int a = tempB.charAt(i) - '0';
                if(a != 0){
                    mB.append(1);
                }else{
                    mB.append(a);
                }
            }
            mB.reverse();
            boolean isZero = true;
            for (int i = 0; i < mB.length(); i++) {
                int a = out.charAt(i) - '0';
                int b = mB.charAt(i) - '0';
                if(a - b != 0){
                    isZero = false;
                }
                tempres.append(a - b);
            }
            if(isZero){
                tempres.setLength(0);
            }
            out = tempres;
            res++;
        }
        return res;
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
