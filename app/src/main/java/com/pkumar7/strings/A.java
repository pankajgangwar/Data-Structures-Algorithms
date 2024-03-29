package com.pkumar7.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Pankaj Kumar on 13/August/2020
 */
class A {
    public static void main(String[] args) {
        A a = new A();
        a.shortestPalindrome("abcd");
    }

    /*
     * 1781. Sum of Beauty of All Substrings
     * https://leetcode.com/problems/sum-of-beauty-of-all-substrings/
     * */
    public int beautySum(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];
            int max = Integer.MIN_VALUE;
            int min = 0;
            for (int j = i; j < s.length(); j++) {
                int ch = s.charAt(j) - 'a';
                max = Math.max(max, ++freq[ch]);
                if (min >= freq[ch] - 1){
                    min = freq[ch];
                    for (int k = 0; k < 26; k++) {
                        min = Math.min(min, freq[k] == 0 ? Integer.MAX_VALUE : freq[k]);
                    }
                }
                res += (max - min);
            }
        }
        return res;
    }


    /*
    564. Find the Closest Palindrome
    https://leetcode.com/problems/find-the-closest-palindrome/
    */
    public String nearestPalindromic(String s) {
        int len = s.length();
        long num = Long.parseLong(s);
        if(num == 0) return String.valueOf(-1);
        if(num <= 10 || (num % 10 == 0
                && Long.parseLong(s.substring(1)) == 0)) {
            return "" + (num - 1);
        }
        if(num == 11 || (num % 10 == 1)
                && Long.parseLong(s.substring(1, len - 1)) == 0){
            return "" + (num - 2);
        }

        if(isAllDigitNine(s)){
            return "" + (num + 2);
        }
        boolean isEvenPalindrome = len % 2 == 0;

        String palindromeRootStr = isEvenPalindrome ? s.substring(0, len / 2) :
                s.substring(0, len / 2 + 1);
        int palindromeRoot = Integer.parseInt(palindromeRootStr);
        long equal = toPalindromeDigits("" + palindromeRootStr, isEvenPalindrome);
        long equalDiff = Math.abs(num - equal);

        long bigger = toPalindromeDigits("" + (palindromeRoot + 1) , isEvenPalindrome);
        long biggerDiff = Math.abs(num - bigger);

        long smaller = toPalindromeDigits("" + (palindromeRoot - 1), isEvenPalindrome);
        long smallerDiff = Math.abs(num - smaller);

        long closest = (biggerDiff < smallerDiff) ? bigger : smaller;
        long minDiff = Math.min(biggerDiff, smallerDiff);

        if(equalDiff != 0){
            if(equalDiff == minDiff){
                closest = Math.min(equal, closest);
            }else if(equalDiff < minDiff){
                closest = equal;
            }
        }
        return "" + closest;
    }

    private long toPalindromeDigits(String rootStr, boolean isEvenPalindrome) {
        StringBuilder builder = new StringBuilder(rootStr).reverse();
        String res = isEvenPalindrome ? rootStr + builder.toString() :
                rootStr + (builder.deleteCharAt(0)).toString();
        return Long.parseLong(res);
    }

    private boolean isAllDigitNine(String s) {
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '9') return false;
        }
        return true;
    }

    /*
    * 1556. Thousand Separator
    * https://leetcode.com/problems/thousand-separator/
    */
    public String thousandSeparator(int n) {
        String num = String.valueOf(n);
        int len = num.length();
        if(len <= 3){
            return String.valueOf(n);
        }

        StringBuilder out = new StringBuilder();
        int count = 0;
        for(int i = num.length() - 1; i >= 0; --i){
            out.append(num.charAt(i));
            if(++count == 3 ){
                out.append(".");
                count = 0;
            }
        }
        if(out.charAt(out.length() -1 ) == '.'){
            out.deleteCharAt(out.length() - 1);
        }
        return out.reverse().toString();
    }


    /* 880. Decoded String at Index
    https://leetcode.com/problems/decoded-string-at-index/
    */
    public String decodeAtIndex(String s, int k) {
        //return iterative(s, k);
        return dfs(s, (long)k);
    }

    public String dfs(String s, long k){
        long len = 0;
        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                if(len * (s.charAt(i) - '0') >= k){
                    long rem = k % len == 0 ? len : k % len;
                    return dfs(s, rem);
                }
                len = len * (s.charAt(i) - '0');
            }else{
                if(++len == k){
                    return "" + s.charAt(i);
                }
            }
        }
        return null;
    }

    public String iterative(String s, int k){
        long length = 0;
        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                length *= s.charAt(i) - '0';    
            }else{
                length++;
            }
        }
        for(int i = s.length() - 1; i >= 0; i--){
            if(Character.isDigit(s.charAt(i))){
                length /= s.charAt(i) - '0'; 
                k = (int)(k % length);
            }else{
                if(k == 0 || k == length){
                    return "" + s.charAt(i);
                }
                length--;
            }
        }
        return "";
    }

    /* 12. Integer to Roman
    * https://leetcode.com/problems/integer-to-roman/
    * */
    public String intToRoman(int num) {
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = new String[]{"M","CM","D", "CD", "C", "XC", "L", "XL","X","IX","V","IV","I"};
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            while (num >= nums[i]){
                num = num - nums[i];
                res.append(roman[i]);
            }
        }
        return res.toString();
    }

    /* 214. Shortest Palindrome
    * https://leetcode.com/problems/shortest-palindrome/
    * */
    public String shortestPalindrome(String s) {
        s = "aa";
        String curr = s + "#" + new StringBuilder(s).reverse().toString();
        int[] table = computeKMPTable(curr.toCharArray());
        String res = new StringBuilder(
                s.substring(table[table.length - 1])).reverse().toString()
                + s;
        return res;
    }

    /* Longest palindromic substring which starts at index 0
       Reverse the rest of substring after palindrome
       and add this to head of org string s
       TLE
    */
    public String shortestPalindrome1(String s){
        int n = s.length();
        int i = 1;
        String pal = "";
        int j = 0;
        while(i <= n) {
            if(isPalindrome(s.substring(0, i))){
                pal = s.substring(0, i);
                System.out.println("Max palindrome = " + pal);
                j = i;
            }
            i++;
        }
        if(j < n){
            String res = new StringBuilder(s.substring(j)).reverse() + s;
            System.out.println("res = " + res);
            return res;
        }else{
            return s;
        }
    }

    /* https://leetcode.com/problems/hexspeak/
    * 1271. Hexspeak
    * */
    public String toHexspeak(String num) {
        char[] x = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'I', 'O'};
        long longNum = Long.parseLong(num);
        String hex = Long.toHexString(longNum).toUpperCase();
        Set<Character> sets = new HashSet<>();
        for(char ch : x) {
            sets.add(ch);
        }
        char[] arr = hex.toCharArray();
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == '1'){
                arr[i] = 'I';
            }else if(arr[i] == '0'){
                arr[i] = 'O';
            }
            if(!sets.contains(arr[i])) return "ERROR";
        }
        return new String(arr);
    }

    /*
    * 158. Read N Characters Given Read4 II - Call multiple times
    * https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
    * */
    class Reader4 {
        public int read4(char[] buf){
            return 0;
        }
    }
    public class Solution extends Reader4 {
        /**
         * @param buf Destination buffer
         * @param n   Number of characters to read
         * @return    The number of actual characters read
         */
        int cnt = 0;
        int idx = 0;
        char[] temp = new char[4];
        int buffLen = 0;
        public int read(char[] buf, int n) {
            buffLen = 0;
            while(buffLen < n ){
                if(cnt == 0){
                    temp = new char[4];
                    cnt = read4(temp);
                    idx = 0;
                }
                if(cnt == 0) break;
                for(int i = idx; i < 4 && cnt > 0 && buffLen < n; i++, idx++, cnt--, buffLen++){
                    buf[buffLen] = temp[i];
                }
            }
            return buffLen;
        }
    }

    //https://leetcode.com/problems/permutation-in-string/
    public boolean checkInclusion(String s1, String s2) {
        return findAnagrams(s2, s1).size() > 0;
    }

    public List<Integer> findAnagrams(String s, String p) {
        int plen = p.length();
        int slen = s.length();

        HashMap<Character, Integer> p_map = new HashMap<>();

        for(char ch : p.toCharArray()){
            p_map.put(ch, p_map.getOrDefault(ch, 0) + 1);
        }

        int counter = p_map.size();
        int begin = 0;
        List<Integer> result = new ArrayList<>();
        for (int end = 0; end < slen; end++) {
            char ch = s.charAt(end);
            if (p_map.containsKey(ch)) {
                p_map.put(ch, p_map.get(ch) - 1);
                if (p_map.get(ch) == 0) counter--;
            }
            while (counter == 0) {
                char tempch = s.charAt(begin);
                if (p_map.containsKey(tempch)) {
                    p_map.put(tempch, p_map.get(tempch) + 1);
                    if (p_map.get(tempch) > 0) counter++;
                }
                if (end - begin + 1 == plen)
                    result.add(begin);

                begin++;
            }
        }
        return result;
    }

    //https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
    public String removeDuplicatesRec(String inputString) {
        for (int i = 0; i < inputString.length() - 1; i++) {
            if (inputString.charAt(i) == inputString.charAt(i + 1)) {
                inputString = inputString.substring(0, i) + inputString.substring(i + 2);
                return removeDuplicatesRec(inputString);
            }
        }
        return inputString;
    }

    //https://leetcode.com/problems/robot-return-to-origin/
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        //int currentDirection = 1;//Four possible directions 1 - N, 2 -S, 3 -E, 4 -W

        char[] arr = moves.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case 'L':
                    x--;
                    break;
                case 'R':
                    x++;
                    break;
                case 'U':
                    y++;
                    break;
                case 'D':
                    y--;
                    break;

                default:
                    break;
            }
        }
        if (x == 0 && y == 0) return true;
        return false;
    }

    //https://leetcode.com/problems/binary-search/
    public static int search() {
        int[] nums = new int[] {-1,0,3,5,9,12,17};
        int target = 9;
        int high = nums.length -1;
        int low = 0;
        while(low <= high) {
            int mid = (low + high ) / 2;
            if(nums[mid] == target) {
                return mid;
            }else if(target < nums[mid]) {
                high = mid -1;
            }else {
                low = mid + 1;
            }
        }
        return -1;
    }

    static String mergeStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i =0 , j = 0;
        while(i < a.length() && j < b.length()) {
            result.append(a.charAt(i));
            result.append(b.charAt(j));
            i++;
            j++;
        }
        if(i < a.length()) {
            result.append(a.substring(i));
        }

        if(j < b.length()) {
            result.append(b.substring(j));
        }
        return result.toString();
    }

    public static String gcdOfStrings(String str1, String str2) {
        String result = GCD(str1,str2);
        System.out.println( "Resultant string: " + result);
        return result;
    }

    public static String GCD(String str1, String str2) {
        if(str1.length() == 0)
            return str2;
        if(str2.length() == 0)
            return str1;

        if(str1.equals(str2))
            return str1;
        if(str1.length() > str2.length()) {
            for(int i = 0; i < str2.length(); i++) {
                if(str1.charAt(i) != str2.charAt(i)) {
                    return "";
                }
            }
            String tmp = str1.substring(str2.length());
            return GCD(tmp, str2);
        }
        else if(str1.length() < str2.length()) {
            return GCD(str2, str1);
        }else {
            return "";
        }
    }

    //https://www.geeksforgeeks.org/naive-algorithm-for-pattern-searching/
    public int numberOfoccurrences() {
        String text = 	  "ABCDABECAB";
        String pattern = "AB";
        int occurance = 0;

        int N = text.length();
        int M = pattern.length();

        for(int i = 0; i <= N-M; i++) {

            int j = 0;
            for(; j < M; j++) {
                if(text.charAt(i+j) != pattern.charAt(j))
                    break;
            }
            if(j == M) {
                System.out.println("Pattern found at : " + i);
                occurance++;
            }
        }

		/*for (int i = 0; i < (textArr.length - patternArr.length); i++) {

			int k = i;
			int j = 0;

			while (k < textArr.length && j < patternArr.length && patternArr[j] == textArr[k]) {
				System.out.println("textArr: " + textArr[k] + " Pattern " + patternArr[j]);
				k++;
				j++;
			}

			if (j == patternArr.length) {
				occurance++;
			}
		}*/
        return occurance;
    }

    //https://leetcode.com/problems/buddy-strings/
	 /*
	  * Input: A = "aaaaaaabc", B = "aaaaaaacb"
		Output: true

		Input: A = "ab", B = "ab"
		Output: false

		ip: "abcaa", "abcbb"
		op: false

		ip: "ababa", "ababa"
		op: true

	  */
    public boolean buddyStrings(String strA, String strB) {
        if (strA == null || strB == null) {
            return false;
        }
        if(strA.length() != strB.length()) {
            return false;
        }

        char[] charA = strA.toCharArray();
        char[] charB = strB.toCharArray();

        if(strA.equals(strB)) {
            Set<Character> mDuplicateCheck = new HashSet<>();
            int i =0;
            for(; i < strA.length() ; i++) {
                if(!mDuplicateCheck.add(strA.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        Queue<Integer> indexes = new LinkedList<>();

        for(int i =0; i < charA.length; i++) {
            if(charA[i] != charB[i]) {
                indexes.offer(i);
            }
        }

        if(indexes.isEmpty() || indexes.size() > 2) {
            return false;
        }

        int index_a = indexes.poll();
        int index_b = indexes.poll();
        char temp = charA[index_a];
        charA[index_a] = charA[index_b];
        charA[index_b] = temp;

        if(strB.equals(new String(charA))) {
            return true;
        }

        return false;
    }

    /*
     * Computer longest prefix which is also a suffix
     * or appears anywhere in the pattern
     * Create pie table first
     * */
    private int[] computeKMPTable(char[] pattern) {
        int lps[] = new int[pattern.length];
        int len = 0;
        int i = 1;
        lps[0] = 0;
        int M = pattern.length;
        while( i < M) {
            if(pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            }else {
                if(len != 0) {
                    len = lps[len - 1];
                }else { // if (len == 0)
                    lps[i] = len;
                    i++;
                }
            }
        }
        return lps;
    }

    // https://leetcode.com/problems/repeated-substring-pattern/
    // https://www.geeksforgeeks.org/find-given-string-can-represented-substring-iterating-substring-n-times/
    // https://leetcode.com/problems/repeated-string-match/
	/*
	 * Input: "abab"
	   Output: True

	   Input: "abcabcabcabc"
	   Output: True

	   Input: "aba"
	   Output: False

		Longest repeat substring
	 * */
    public boolean repeatedSubstringPattern(String inputStr) {
        inputStr = "abaababaab";

        int n = inputStr.length();
        int lps[] = computeKMPTable(inputStr.toCharArray());
        int len = lps[n -1];

        int substring = lps.length - len - 1;
        System.out.println("find substring from 0 to: " + substring);
        if( len > 0 && n % (n - len) == 0) {
            System.out.println(" Repeated pattern found ");
            return true;
        }else {
            System.out.println("No repeated pattern");
            return false;
        }

    }

    //https://leetcode.com/problems/reverse-words-in-a-string-iii/
    /**
     * Input: "Let's take LeetCode contest"
     Output: "s'teL ekat edoCteeL tsetnoc"
     * */

    public String reverseWords(String inputStr) {
        inputStr = "Let's take LeetCode contest";
        String[] words = inputStr.split(" ");

        StringBuilder builder = new StringBuilder();

        for(String word : words) {
            char[] wordChar = word.toCharArray();
            int i = 0;
            int j = wordChar.length -1;
            while(i < j) {
                char temp = wordChar[i];
                wordChar[i] = wordChar[j];
                wordChar[j] = temp;
                i++;
                j--;
            }
            builder.append(wordChar);
            builder.append(" ");
        }

        System.out.println("Output: " + builder.toString());
        return builder.toString().trim();
    }

    //https://leetcode.com/problems/valid-palindrome/
    /**
     * Input: "A man, a plan, a canal: Panama"
     Output: true
     */
    public boolean isPalindrome(String inputStr) {
        if(inputStr.isEmpty()) {
            return true;
        }
        char[] inputArr = inputStr.toLowerCase().toCharArray();
        List<Character> final_char_list = new ArrayList<>();
        for(int i = 0; i < inputArr.length; i++ ) {
            if(Character.isLetter(inputArr[i]) || Character.isDigit(inputArr[i])) {
                final_char_list.add(inputArr[i]);
            }
        }
        int i = 0;
        int j = final_char_list.size() - 1;
        if(j == 0) {
            return false;
        }
        while(i < j) {
            if(final_char_list.get(i) != final_char_list.get(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /** https://leetcode.com/problems/valid-palindrome-ii/
     * Given a non-empty string s, you may delete at most one character.
     * Judge whether you can make it a palindrome.
     * ip: "abca"
     * op : false
     * */
    public boolean validPalindrome(String inputStr) {
        inputStr = "aguokepatgbnvfqmgmlcupuufxoohdfpgjdmysgvhmvffcnqxjjxqncffvmhvgsymdjgpfdhooxfuupuculmgmqfvnbgtapekouga";
        if(inputStr.isEmpty()) {
            return true;
        }
        int left = 0;
        int right = inputStr.length() - 1;
        if(right == 0) {
            return false;
        }

        while(left < right) {
            if(inputStr.charAt(left) == inputStr.charAt(right)){
                left++;
                right--;
            }else {
                break;
            }
        }
        if(left >= right) return true;
        if(isPalindrome(left + 1, right, inputStr) || isPalindrome(left, right - 1 , inputStr)) {
            return true;
        }

        return false;
    }

    public boolean isPalindrome(int left, int right, String inputStr) {
        while(left < right) {
            if(inputStr.charAt(left) == inputStr.charAt(right)) {
                left++;
                right--;
            }else {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/implement-strstr/
     * Return the index of the first occurrence of needle in haystack,
     * or -1 if needle is not part of haystack.
     * Input: haystack = "hello", needle = "ll"
     Output: 2
     * */
    public int strStr(String haystack, String needle) {
        if(needle.isEmpty()) {
            return 0;
        }
        int[] lps = computeKMPTable(needle.toCharArray());
        int i = 0;
        int j = 0;
        int N = haystack.length();
        int M = needle.length();

        while(i < N) {
            if(haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            if(j == M) {
                System.out.println("Pattern found at: " + (i - j) );
                return (i-j);
            }else if(i < N && haystack.charAt(i) != needle.charAt(j)) {
                if(j != 0) {
                    j = lps[j -1];
                }else {
                    i++;
                }
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/add-binary/
     *  Input: a = "11", b = "1"
     Output: "100"

     Input: a = "1010", b = "1011"
     Output: "10101"
     **/
    public String addBinary(String stringA, String stringB) {
        stringA = "1010";
        stringB = "1011";
        int N = stringA.length();
        int M = stringB.length();

        if(N > M) {
            int zeroToConcat = N - M;
            StringBuilder builder = new StringBuilder();
            while(zeroToConcat > 0) {
                builder.append("0");
                zeroToConcat--;
            }
            builder.append(stringB);
            stringB = builder.toString();
        }else if(M > N) {
            int zeroToConcat = M - N;
            StringBuilder builder = new StringBuilder();
            while(zeroToConcat > 0) {
                builder.append("0");
                zeroToConcat--;
            }
            builder.append(stringA);
            stringA = builder.toString();

        }else {
            System.out.println("Both String have equal length");
        }
        System.out.println(stringA + " and " + stringB);

        int right = stringA.length() -1 ;//Now both the strings have equal length, pick any

        String result = "";
        String carry= "";
        String sum = "";
        while(right >= 0) {
            char binaryA = stringA.charAt(right);
            char binaryB = stringB.charAt(right);
            System.out.println(binaryA + " : " + binaryB);

            if(binaryA == '0' && binaryB == '0' ) {
                if(carry.equals("1")) {
                    sum = "1";
                    carry = "0";
                }else {
                    sum = "0";
                    carry = "0";
                }
            }else if(binaryA == '1' && binaryB == '0' ) {
                if(carry.equals("1")) {
                    sum = "0";
                    carry = "1";
                }else {
                    sum = "1";
                    carry = "0";
                }
            }else if(binaryA == '0' && binaryB == '1' ) {
                if(carry.equals("1")) {
                    sum = "0";
                    carry = "1";
                }else {
                    sum = "1";
                    carry = "0";
                }
            }else if(binaryA == '1' && binaryB == '1' ) {
                if(carry.equals("1")) {
                    sum = "1";
                    carry = "1";
                }else {
                    sum = "0";
                    carry = "1";
                }
            }
            result = sum + result;
            right--;
        }
        result = carry + result;
        System.out.println("binary addition " + result);
        int where_is_1 = result.indexOf("1");
        if(where_is_1 >= 0) {
            result = result.substring(where_is_1);
        }else {
            result = "0";
        }
        System.out.println("Final binary addition " + result);
        return result.toString();
    }

    /**
     *  https://leetcode.com/problems/valid-parentheses/
     *  Use recursion
     *
     * Input: "()[]{}"
     Output: true

     Input: "{[]}"
     Output: true

     Input: "([)]"
     Output: false

     Input: "{{}[][[[]]]}"
     Output: true

     Stack

     * **/
    public boolean isValid(String inputStr) {
        Stack<Character> mStack = new Stack<>();
        int i = 0;
        while(i < inputStr.length()) {
            char bracket = inputStr.charAt(i);
            if(!mStack.isEmpty()) {
                boolean isOpen = isOpened(bracket);

            }else {
                mStack.push(bracket);
            }
            i++;
        }
        return false;
    }

    boolean isClosed(char ch) {
        String closed = "{([";
        char[] closedArr = closed.toCharArray();
        for(char closed_ch : closedArr) {
            if(ch == closed_ch) {
                return true;
            }
        }
        return false;
    }

    boolean isOpened(char ch) {
        String openChars = "})]";
        char[] openArr = openChars.toCharArray();
        for(char open_ch : openArr) {
            if(ch == open_ch) {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/valid-anagram/
     * **/
    public boolean isAnagram(String source, String dest) {

        char[] frequency = new char[24];
        if(source.isEmpty() || dest.isEmpty())return false;

        int N = source.length();
        int M = dest.length();

        if(N != M || N < M || N > M) return false;

        for(int i = 0; i < N; i++) {
            int index = source.charAt(i) - 'a';
            frequency[index]++;
        }

        for(int i = 0; i < M; i++) {
            int index = dest.charAt(i) - 'a';
            if(frequency[index] > 0) {
                frequency[index]--;
            }else {
                return false;
            }
        }

        return true;
    }

    /**
     * https://leetcode.com/problems/multiply-strings/
     * Input: num1 = "2", num2 = "3"
     Output: "6"

     Input: num1 = "123", num2 = "456"
     Output: "56088"
     * **/
    public String multiply(String num1, String num2) {
        return "";
    }

    public String[] findOcurrences(String text, String first, String second) {
        text = "alice is a good girl she is a good student";
        first = "a";
        second = "good";

        String[] words = text.split(" ");
        List<String> arrayList = new ArrayList<>();

        for(int i = 2; i < words.length; i++) {
            if(words[i - 2].equals(first) && words[i -1].equals(second)) {
                System.out.println("" + words[i]);
                arrayList.add(words[i]);
            }
        }
        String[] result = new String[arrayList.size()];
        for(int i =0; i < result.length; i++) {
            result[i] = arrayList.get(i);
        }
        if(!arrayList.isEmpty()) {
            return result;
        }else {
            return new String[] {};
        }

    }
}

