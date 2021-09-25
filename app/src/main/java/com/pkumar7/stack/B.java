package com.pkumar7.stack;

import java.util.Stack;

public class B {

    /* 1541. Minimum Insertions to Balance a Parentheses String
    * https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/
    * */
    public int minInsertions(String s){
        char[] arr = s.toCharArray();
        Stack<Character> st = new Stack<>();
        int insertion = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(ch == '('){
                st.push('(');
            }else {
                if(st.isEmpty()){
                    st.push('(');
                    insertion++;
                }
                if(i + 1 < s.length() && s.charAt(i + 1) == ')') i++;
                else insertion++;
                st.pop();
            }
        }
        return insertion + st.size() * 2;
    }

    /* 1762. Buildings With an Ocean View
    * https://leetcode.com/problems/buildings-with-an-ocean-view/
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
