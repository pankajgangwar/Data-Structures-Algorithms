package com.pkumar7.leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class SeptemberW2 {
    public static void main(String[] args) {
        SeptemberW2 w2 = new SeptemberW2();
        int[] nums = new int[]{1,2,2};
        w2.subsetsWithDup(nums);
    }

    /**
     * https://leetcode.com/problems/generate-parentheses/
     *
     * for n  = 3;
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     *
     * */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParanthesisRec(result, 0, 0, "" , n);
        return result;
    }

    public void generateParanthesisRec(List<String> paranthesis, int open, int close, String curr, int max){
        if(curr.length() == max * 2){
            paranthesis.add(curr);
            return;
        }
        if(open < max){
            generateParanthesisRec(paranthesis, open + 1, close, curr +"(", max);
        }
        if(close < open){
            generateParanthesisRec(paranthesis, open, close + 1, curr +")", max);
        }
    }

    /**
     * 17. Letter Combinations of a Phone Number
     *
     * Input: "23"
     * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     *
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
     * **/
    public List<String> letterCombinations(String digits) {
        if(digits.length() == 0){
            return new ArrayList<>();
        }
        String table[] = new String[]{
                "", "", "abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"
        };
        int[] numbers = new int[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            numbers[i] = Integer.valueOf(digits.charAt(i));
        }

        List<String> result = letterCombinationUtil(numbers, numbers.length, table);
        return result;
    }

    public List<String> letterCombinationUtil(int[] number, int n, String[] table){
        ArrayList<String> list = new ArrayList<>();
        Queue<String> mQueue = new LinkedList<>();

        mQueue.add("");

        while(!mQueue.isEmpty()) {
            String result = mQueue.poll();
            if(result.length() == n){
                list.add(result);
            }else{
                String val = table[number[result.length()]];
                for (int i = 0; i < val.length(); i++) {
                    mQueue.add(result + val.charAt(i));
                }
            }
        }
        return list;
    }

    /***
     * 78. Subsets
     * https://leetcode.com/problems/subsets/
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subsetsRec(result, new ArrayList<>(), nums, 0);
        return result;
    }

    public void subsetsRec(List<List<Integer>> result, List<Integer> tempList, int[] nums, int start){
        result.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            subsetsRec(result, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    /** Subsets II
     * https://leetcode.com/problems/subsets-ii/
     * */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subsetWithDupRec(nums, result, new ArrayList<>(), 0);
        return result;
    }

    public void subsetWithDupRec(int[] nums, List<List<Integer>> result, List<Integer> tempList, int start){
        result.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            if(i > start && nums[i] == nums[i-1])continue;
            tempList.add(nums[i]);
            subsetWithDupRec(nums, result, tempList, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/combination-sum/
     * 39. Combination Sum
     * */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        helperCombinationSum(result, new ArrayList<>(), candidates, target,  0);
        return result;
    }

    public void helperCombinationSum(List<List<Integer>> result, List<Integer> tempList, int[] candidates, int remaining, int start){
        if(remaining < 0) {
            return;
        }else if(remaining == 0) {
            result.add(new ArrayList<>(tempList));
        }
        for (int i = start; i < candidates.length; i++) {
            tempList.add(candidates[i]);
            helperCombinationSum(result, tempList, candidates, remaining - candidates[i], i );
            tempList.remove(tempList.size() - 1);
        }
    }


}
