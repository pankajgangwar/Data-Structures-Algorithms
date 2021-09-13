package com.pkumar7.stack;

import java.util.Stack;

public class B {

    /* 1762. Buildings With an Ocean View
    * https://leetcode.com/problems/buildings-with-an-ocean-view/submissions/
    * */
    public int[] findBuildings(int[] heights) {
        Stack<int[]> st = new Stack<>();
        for(int i = 0; i < heights.length; i++){
            int h = heights[i];
            while(!st.isEmpty() && st.peek()[0] <= h){
                st.pop();
            }
            st.push(new int[]{h, i});
        }
        int h = st.size();
        int[] res = new int[h];
        for(int i = h - 1; i >= 0; i--){
            res[i] = st.pop()[1];
        }
        return res;
    }
}
