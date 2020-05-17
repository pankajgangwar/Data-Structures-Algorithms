package com.mission.google.hackerrank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MayW3 {

    public static void main(String[] args) {
        //findSubstring("qwtplmnghbdf", 4);
        // findSubstring("pankaj", 6);
        //findSubstring("qwdftr", 2);
        List<String> names = new ArrayList<>();
        names.add("Pankaj");
        names.add("Pankaj");
        names.add("Pankaj");
        names.add("Pankaj");
        names.add("Pankaj");
        names.add("Pankaj");
        names.add("Kumar");
        names.add("Kumar");
        names.add("Gangwar");
        names.add("Rohit");
        names.add("Rohit");
        mostActive(names);
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

    /*
    HackerRank
    Substring of length k with max vowels*/
    public static String findSubstring(String s, int k) {
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
        return res;
    }

    public static boolean isVowel(char ch){
        if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
            return true;
        }
        return false;
    }

}
