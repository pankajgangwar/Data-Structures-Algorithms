package com.pkumar7.hackerrank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MayW3 {



    /* HackerRank problems*/
    public static int sortedSum(List<Integer> a) {
        int mod = (int)1e9+7;
        int sum = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            int ele = a.get(i);
            int loc = Collections.binarySearch(list, ele);
            list.add( Math.abs(loc) - 1, ele);
            int currSum = 0;
            for (int j = 0; j < list.size(); j++) {
                currSum += (j + 1) * list.get(j);
                currSum = ((currSum % mod ) + mod ) % mod;
            }
            sum += currSum;
            sum =( (sum % mod) + mod ) % mod;
        }
        return sum;
    }

    /* HackerRank problems*/
    public static long taskOfPairing(List<Long> freq) {
        long pairs = 0L;
        LinkedList<Long> freqs = new LinkedList<>(freq);

        Long prevWeight = 0L;
        while(!freqs.isEmpty()){
            Long dumbbells = freqs.pollFirst() + prevWeight;
            if(dumbbells % 2 == 0){
                pairs += dumbbells / 2;
                prevWeight = 0L;
            }else if(dumbbells > 2){
                prevWeight = 1L;
                pairs += dumbbells / 2;
            }else{
                prevWeight = dumbbells;
            }
        }
        BigInteger big = BigInteger.valueOf(pairs);
        return big.longValue();
    }

    /* HackerRank
     *  Customers activity more than 5%
     * */
    public static List<String> mostActive(List<String> customers) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String name : customers){
            map.put(name, map.getOrDefault(name, 0) + 1);
        }
        int total = customers.size();
        List<String> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            double perc = (double)entry.getValue() / total;
            perc = perc * 100;
            if( perc >= 5){
                res.add(entry.getKey());
            }
        }
        System.out.println("res = " + res);
        Collections.sort(res);
        return res;
    }
    public static void main(String[] args) {
        //findSubstring("qwtplmnghbdf", 4);
        // findSubstring("pankaj", 6);
        //findSubstring("qwdftr", 2);
        int res = findSubstring("tryhard", 4);
        System.out.println("res = " + res);
    }


    /*
    HackerRank
    Substring of length k with max vowels*/
    public static int findSubstring(String s, int k) {
        int start = 0;
        int end = 0;
        int len = s.length();
        char[] arr = s.toCharArray();
        int count = 0;

        for(; end < k; end++){
            if(isVowel(arr[end])){
                count++;
            }
        }
        String res = "";
        if(count > 0){
            res = s.substring(start, end);
        }
        int max = count;
        end = k;
        start = 0;
        while(end < len){
            if(isVowel(arr[end++])){
                count++;
            }
            if(isVowel(arr[start++])){
                count--;
            }
            if(max < count){
                res = s.substring(start, end);
                max = count;
            }
        }
        char[] ans = res.toCharArray();
        int maxAns = 0;
        for (int i = 0; i < ans.length; i++) {
            if(isVowel(ans[i])){
                maxAns++;
            }
        }
        return maxAns;
    }

    public static boolean isVowel(char ch){
        if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
            return true;
        }
        return false;
    }

}
