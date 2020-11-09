package com.pkumar7.strings;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class B {

    public static void main(String[] args) {
        B obj = new B();
        boolean status = obj.checkPalindromeFormation("abdef","fecab");
        System.out.println("status = " + status);
    }

    /*
     *1573. Number of Ways to Split a String
     *https://leetcode.com/problems/number-of-ways-to-split-a-string/
     */
    public int numWays(String s) {
        int count1 = 0;
        int n = s.length();
        int mod = (int)1e9 + 7;
        for(int i = 0; i < n; i++){
            count1 += (int)(s.charAt(i) - '0');
        }
        if(count1 == 0) return (int)(((n - 1L) * (n - 2L)) / 2) % mod;
        if(count1 % 3 != 0) return 0;
        int need = count1 / 3;
        long x = 0, y = 0;
        for(int i = 0, count = 0; i < n; i++){
            count += s.charAt(i) - '0';
            if(count == need){
                ++x;
            }else if(count == 2 * need){
                ++y;
            }
        }
        return (int)(x * y % mod);
    }

    public int numWays1(String s) {
        int ones = 0, n = s.length();
        int m = 1_000_000_007;
        for (int i = 0; i < n; ++i) {
            ones += s.charAt(i) - '0';
        }
        if (ones == 0) {
            return (int)((n - 2L) * (n - 1L) / 2 % m);
        }
        if (ones % 3 != 0) {
            return 0;
        }
        int onesInEachSplitedBlock = ones / 3;
        long waysOfFirstCut = 0, waysOfSecondCut = 0;
        for (int i = 0, count = 0; i < n; ++i) {
            count += s.charAt(i) - '0';
            if (count == onesInEachSplitedBlock) {
                ++waysOfFirstCut;
            }else if (count == 2 * onesInEachSplitedBlock) {
                ++waysOfSecondCut;
            }
        }
        return (int)(waysOfFirstCut * waysOfSecondCut % m);
    }


    /* 1616. Split Two Strings to Make Palindrome
     * https://leetcode.com/problems/split-two-strings-to-make-palindrome/
     * */
    public boolean checkPalindromeFormation(String a, String b) {
        if(a.length() != b.length()) return false;
        return helper(a, b) || helper(b, a);
    }

    public boolean helper(String a, String b){
        int low = 0;
        int high = b.length() - 1;
        while(low <= high){
            if(a.charAt(low) != b.charAt(high)) {
                break;
            }
            low++;
            --high;
        }
        return helper(a, low, high) || helper(b, low, high);
    }
    public boolean helper(String s, int low, int high){
        while(low <= high){
            if(s.charAt(low) != s.charAt(high)) {
                return false;
            }
            low++;
            --high;
        }
        return true;
    }

    /* https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/
     * 1614. Maximum Nesting Depth of the Parentheses
     * */
    public int maxDepth(String s) {
        int res = 0;
        int depth = 0;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '('){
                res = Math.max(res, ++depth);
            }else if(s.charAt(i) == ')'){
                depth--;
            }
        }
        return res;
    }


    /* 1592. Rearrange Spaces Between Words
     * https://leetcode.com/problems/rearrange-spaces-between-words/
     * */
    public String reorderSpaces(String text) {
        List<String> words = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int spaces = 0;
        int n = text.length();
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == ' '){
                while (i < n && text.charAt(i) == ' '){
                    spaces++;
                    i++;
                }
                i = i - 1;
                if(builder.length() > 0){
                    words.add(builder.toString());
                }
                builder = new StringBuilder();
            }else{
                builder.append(text.charAt(i));
            }
        }
        if(builder.length() > 0){
            words.add(builder.toString());
        }

        int req = 0;
        if(spaces < words.size()){
            return text;
        }else if(spaces == words.size()){
            req = 1;
        }else{
            if(words.size() - 1 == 0 ){
                req = spaces;
            }else{
                req = spaces / (words.size() - 1);
            }
        }
        int currLen = 0;
        int i = 0;
        StringBuilder out = new StringBuilder();
        while (currLen < n && i < words.size()){
            String word = words.get(i++);
            currLen += word.length();
            out.append(word);
            if(currLen == n){
                break;
            }
            int temp = req;
            while (temp > 0 && currLen < n){
                out.append(" ");
                temp--;
                currLen++;
            }
        }
        while (currLen < n) {
            out.append(" ");
            currLen++;
        }
        return out.toString();
    }

    /* 65. Valid Number
    * https://leetcode.com/problems/valid-number/
    * */
    public boolean isNumber(String s) {
        s = s.trim();
        boolean eSeen = false;
        boolean pointSeen = false;
        boolean numberSeen = false;
        boolean numberAfterE = true;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                numberSeen = true;
                numberAfterE = true;
            }else if(s.charAt(i) == '.'){
                if(pointSeen || eSeen){
                    return false;
                }
                pointSeen = true;
            }else if(s.charAt(i) == 'e'){
                if(eSeen || !numberSeen){
                    return false;
                }
                numberAfterE = false;
                eSeen = true;
            }else if(s.charAt(i) == '+' || s.charAt(i) == '-'){
                if(i > 0 && s.charAt(i - 1) != 'e'){
                    return false;
                }
            }else{
                return false;
            }
        }
        return numberSeen && numberAfterE;
    }

    /* 1576. Replace All ?'s to Avoid Consecutive Repeating Characters
     * https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/
     * */
    public String modifyString(String s) {
        char[] arr = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if(arr[i] == '?'){
                for (char ch = 'a'; ch <= 'z' ; ch++) {
                    if(i - 1 >= 0 && arr[i - 1] == ch)continue;
                    if(i + 1 < arr.length && arr[i + 1] == ch)continue;
                    arr[i] = ch;
                    break;
                }
            }
        }
        return new String(arr);
    }
}
