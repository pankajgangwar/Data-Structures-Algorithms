package com.mission.google.strings;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class B {

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
