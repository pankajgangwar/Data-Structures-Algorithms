package com.pkumar7.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pankaj Kumar on 08/November/2020
 */
class A {

    /* 1630. Arithmetic Subarrays
     * https://leetcode.com/problems/arithmetic-subarrays/
     * */
    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> res = new ArrayList<>();
        int m = l.length;
        for (int i = 0; i < m; i++) {
            int start = l[i];
            int end = r[i];
            int[] temp = new int[end - start + 1];
            for(int x = 0, k = start; k <= end; k++, x++){
                temp[x] = nums[k];
            }
            Arrays.sort(temp);
            boolean found = true;
            for (int j = 2; j < temp.length; j++) {
                if(temp[j] - temp[j - 1] != temp[1] - temp[0]){
                    found = false;
                    break;
                }
            }
            res.add(found);
        }
        return res;
    }
}
