package com.pkumar7.hackerrank.hackfest2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pankaj Kumar on 12/October/2020
 */
class A {

    /*
    * https://www.hackerrank.com/contests/hackerrank-hackfest-2020/challenges/stones-piles
    */
    public static int maximumStones(List<Integer> arr) {
        int even = 0, odd = 0;
        for (int i = 0; i < arr.size(); i++) {
            if(i % 2 == 0){
                even += arr.get(i);
            }else{
                odd += arr.get(i);
            }
        }
        int min = Math.min(even, odd);
        return min  * 2;
    }

    /*
    * https://www.hackerrank.com/contests/hackerrank-hackfest-2020/challenges/strictly-increasing-sequence/
    * */
    public static String whoIsTheWinner(List<Integer> arr) {
        if(arr.size() == 0) return "First";
        Set<Integer> sets = new HashSet<>();
        int n = arr.size();
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if(sets.contains(arr.get(i))){
                flag = true;
                break;
            }
            sets.add(arr.get(i));
        }
        if(!flag){
            return "First";
        }else{
            if((n - 2) % 2 == 0){
                return "Second";
            }else{
                return "First";
            }
        }
    }

    /*
    * https://www.hackerrank.com/contests/hackerrank-hackfest-2020/challenges/cyclic-binary-string/
    * */
    public static int maximumPower(String s) {
        int n = s.length();
        int max = 0;
        int i = 0;
        int j = n - 1;
        if(s.charAt(i) == '0' && s.charAt(j) == '0'){
            while (i < n && s.charAt(i) == '0'){
                i++;
            }
            while (j > i && s.charAt(j) == '0'){
                j--;
            }
            max = (n - j - 1) + (i);
        }
        for ( ;i <= j; ) {
            int k = i;
            while (k < n && s.charAt(k) == '0'){
                k++;
            }
            max = Math.max(max, k - i);
            i = i == k ? i + 1 : k + 1;
        }
        if(max == n){
            return -1;
        }
        return max;
    }
}
