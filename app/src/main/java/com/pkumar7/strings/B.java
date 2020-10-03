package com.pkumar7.strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class B {

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
