package com.pkumar7.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
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
     * https://leetcode.com/problems/valid-tic-tac-toe-state/
     * 794. Valid Tic-Tac-Toe State
     * */
    public boolean validTicTacToe(String[] board) {
        int diag = 0, antiDiag = 0;
        int turns = 0;
        int n = 3;
        int[] rows = new int[n];
        int[] cols = new int[n];

        boolean xWin, OWin;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length(); j++) {
                if(board[i].charAt(j) == 'X'){
                    turns++;
                    if(i == j) diag++;
                    if(i + j == 2) antiDiag++;
                    rows[i]++;
                    cols[j]++;
                }else if(board[i].charAt(j) == 'O'){
                    turns--;
                    rows[i]--;
                    cols[j]--;
                    if(i == j) diag--;
                    if(i + j == 2) antiDiag--;
                }
            }
        }

        xWin = rows[0] == 3 || rows[1] == 3 || rows[2] == 3
                || cols[0] == 3 || cols[1] == 3 || cols[2] == 3
                || diag == 3 || antiDiag == 3;

        OWin = rows[0] == -3 || rows[1] == -3 || rows[2] == -3
                || cols[0] == -3 || cols[1] == -3 || cols[2] == -3
                || diag == -3 || antiDiag == -3;

        if(xWin && turns == 0 || OWin && turns == 1) return false;

        return (turns == 0 || turns == 1);
    }

    /* 1888. Minimum Number of Flips to Make the Binary String Alternating
     * https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
     * */
    public int minFlips(String s) {
        s += s;
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        char ch = '0';
        for (int i = 0; i < s.length(); i++) {
            first.append(ch);
            ch = ch == '0' ? '1' : '0';
            second.append(ch);
        }
        int op1 = 0, op2 = 0;
        int n = s.length() / 2;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n*2; i++) {
            if(s.charAt(i) != first.charAt(i)){
                op1++;
            }
            if(s.charAt(i) != second.charAt(i)){
                op2++;
            }
            if(i >= n){
                if(s.charAt(i - n) != first.charAt(i - n)){
                    op1--;
                }
                if(s.charAt(i - n) != second.charAt(i - n)){
                    op2--;
                }
            }
            if(i >= n - 1){
                ans = Math.min(ans, Math.min(op1, op2));
            }
        }
        return ans;
    }

    /* 1864. Minimum Number of Swaps to Make the Binary String Alternating
    * https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-binary-string-alternating/
    * */
    public int minSwaps(String s) {
        int zero = 0;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '0'){
                zero++;
            }
        }
        int ones = s.length() - zero;
        int abs = Math.abs(ones - zero);
        if(abs > 1) return -1;
        if(ones > zero){
            return helper(s, '1');
        }else if(zero > ones){
            return helper(s, '0');
        }
        return Math.min(helper(s, '1'), helper(s, '0'));
    }

    public int helper(String s, char start){
        int swaps = 0;
        for(char ch : s.toCharArray()){
            if(ch != start){
                swaps++;
            }
            start = start == '0' ? '1' : '0';
        }
        return swaps / 2;
    }

    /*
     * https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/
     * */
    public int minOperations(String s) {
        int res = 0;
        StringBuilder out = new StringBuilder(s);
        char curr = '0';
        for (int i = 0; i < out.length(); i+= 1) {
            if(out.charAt(i) != curr){
                res++;
                out.setCharAt(i, curr);
            }
            curr = curr == '0' ? '1' : '0';
        }
        curr = '1';
        out = new StringBuilder(s);
        int res1 = 0;
        for (int i = 0; i < out.length(); i+= 1) {
            if(out.charAt(i) != curr){
                res1++;
                out.setCharAt(i, curr);
            }
            curr = curr == '0' ? '1' : '0';
        }
        return Math.min(res, res1);
    }

    /* https://leetcode.com/problems/count-number-of-homogenous-substrings/
     * https://leetcode.com/problems/count-number-of-homogenous-substrings/discuss/1064589/JAVA-HashMap
     * */
    public int countHomogenous(String s) {
        int len = s.length();
        long res = 0;
        int mod = (int)1e9 + 7;
        for (int i = 0; i < s.length();) {
            int j = i + 1;
            while (j < len && s.charAt(i) == s.charAt(j)){
                j++;
            }
            long n = j - i;
            long ans = (((n % mod) * ((n + 1) % mod) / 2)) % mod;
            res = (res + ans) % mod;
            i = j;
        }
        return (int)res % mod;
    }

    /* 1704. Determine if String Halves Are Alike
     * https://leetcode.com/problems/determine-if-string-halves-are-alike/
     * */
    public boolean halvesAreAlike(String s) {
        s = s.toLowerCase();
        int n = s.length();
        String a = s.substring(0, n / 2);
        String b = s.substring(n / 2);

        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            char ch = a.charAt(i);
            if(a.charAt(i) == 'a' || a.charAt(i) == 'e' || a.charAt(i) == 'i'
                    || a.charAt(i) == 'o' || a.charAt(i) == 'u'){
                count++;
            }
        }

        for (int i = 0; i < b.length(); i++) {
            char ch = b.charAt(i);
            if (b.charAt(i) == 'a' || b.charAt(i) == 'e' || b.charAt(i) == 'i'
                    || b.charAt(i) == 'o' || b.charAt(i) == 'u') {
                if(--count < 0) return false;
            }
        }
        return count == 0;
    }

    /* 1694. Reformat Phone Number
     * https://leetcode.com/problems/reformat-phone-number/
     * */
    public String reformatNumber(String number) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if(number.charAt(i) == '-' || number.charAt(i) == ' ') continue;
            out.append(number.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        while (out.length() > 4){
            int n = 3;
            while(n-- > 0) {
                sb.append(out.charAt(0));
                out.deleteCharAt(0);
            }
            sb.append("-");
        }
        if(out.length() == 4){
            while(out.length() > 0) {
                sb.append(out.charAt(0));
                out.deleteCharAt(0);
                if(out.length() == 2){
                    sb.append("-");
                }
            }
        }else {
            while(out.length() > 0) {
                sb.append(out.charAt(0));
                out.deleteCharAt(0);
            }
        }
        return sb.toString();
    }

    /* 1684. Count the Number of Consistent Strings
     * https://leetcode.com/problems/count-the-number-of-consistent-strings/
     * */
    public int countConsistentStrings(String allowed, String[] words) {
        HashSet<Character> sets = new HashSet<Character>();
        for(char ch : allowed.toCharArray()){
            sets.add(ch);
        }
        int res = 0;
        outer: for (int i = 0; i < words.length; i++) {
            String w = words[i];
            for(char ch : w.toCharArray()){
                if(!sets.contains(ch)){
                    continue outer;
                }
            }
            res++;
        }
        return res;
    }


    /*1662. Check If Two String Arrays are Equivalent
     * https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent
     * */
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder out = new StringBuilder();
        for(String x : word1){
            out.append(x);
        }

        StringBuilder out1 = new StringBuilder();
        for(String x : word2){
            out1.append(x);
        }
        return out.toString().equals(out1.toString());
    }

    /* 1663. Smallest String With A Given Numeric Value
     * https://leetcode.com/problems/smallest-string-with-a-given-numeric-value/
     * */
    public String getSmallestString(int n, int k) {
        char[] s = new char[n];
        Arrays.fill(s, 'a');
        k -= n;
        char[] arr = new char[26];
        char start = 'a';
        for (int i = 0; i < arr.length; i++) {
            arr[i] = start;
            start++;
        }
        while (k > 0){
            int min = (Math.min(26, k + 1));
            char ch = arr[min-1];
            s[--n] = ch;
            k = k - min + 1;
        }
        return new String(s);
    }

    /* 681. Next Closest Time
     * https://leetcode.com/problems/next-closest-time/
     * */
    public String nextClosestTime(String time) {
        return nextClosestTimeSol1(time);
        //return nextClosestTimeS2(time);
    }
    public String nextClosestTimeS2(String time) {
        List<Integer> list = new ArrayList<>();
        String[] arr = time.split(":");
        for (int i = 0; i < arr.length; i++) {
            String x = arr[i];
            for (int j = 0; j < x.length(); j++) {
                int a = (int) x.charAt(j) - '0';
                list.add(a);
            }
        }
        HashSet<Integer> sets = new HashSet<>(list);
        if(sets.size() == 1 ) return time;

        String hr = arr[0];
        String min = arr[1];
        int mins = (Integer.parseInt(min) + 1) % 60;
        String m = String.format("%02d", mins);
        List<Integer> minsList =  new ArrayList<>();
        minsList.add((int)m.charAt(0) - '0');
        minsList.add((int)m.charAt(1) - '0');
        while (!list.containsAll(minsList)){
            minsList.clear();
            mins = (mins + 1) % 60;
            m = String.format("%02d", mins);
            minsList.add((int)m.charAt(0) - '0');
            minsList.add((int)m.charAt(1) - '0');
        }
        if(mins > Integer.parseInt(arr[1])){
            StringBuilder res = new StringBuilder();
            res.append(arr[0]);
            res.append(":");
            res.append(String.format("%02d", mins));
            return res.toString();
        }
        int hrs = (Integer.parseInt(hr) + 1) % 24;
        List<Integer> hrsList =  new ArrayList<>();
        String h = String.format("%02d", hrs);
        hrsList.add((int)h.charAt(0) - '0');
        hrsList.add((int)h.charAt(1) - '0');
        while (!list.containsAll(hrsList)){
            hrsList.clear();
            hrs = (hrs + 1) % 24;
            h = String.format("%02d", hrs);
            hrsList.add((int)h.charAt(0) - '0');
            hrsList.add((int)h.charAt(1) - '0');
        }
        StringBuilder out = new StringBuilder();
        out.append(String.format("%02d", hrs));
        out.append(":");
        out.append(String.format("%02d", mins));
        return out.toString();
    }

    public String nextClosestTimeSol1(String time) {
        char[] digits = new char[4];
        digits[0] = time.charAt(0);
        digits[1] = time.charAt(1);
        digits[2] = time.charAt(3);
        digits[3] = time.charAt(4);

        HashSet<String> timeSet = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        String candidate = digits[i]+ "" + digits[j] + "" + ":" + digits[k] + "" + digits[l];
                        System.out.println("candidate = " + candidate);
                        if(isValidTime(candidate)){
                            timeSet.add(candidate);
                        }
                    }
                }
            }
        }
        List<String> timesList = new ArrayList<>(timeSet);
        Collections.sort(timesList);
        int idx = timesList.indexOf(time);
        if(idx == timesList.size() - 1) return timesList.get(0);
        else return timesList.get(idx + 1);
    }

    private boolean isValidTime(String candidate) {
        int hrs = Integer.parseInt(candidate.split(":")[0]);
        int mins = Integer.parseInt(candidate.split(":")[1]);
        if(hrs >= 0 && hrs < 24 && mins >= 0 && mins < 60) return true;
        return false;
    }

    /* 1657. Determine if Two Strings Are Close
     * https://leetcode.com/problems/determine-if-two-strings-are-close/
     * */
    public boolean closeStrings(String word1, String word2) {
        if(word1.length() != word2.length()) return false;
        if(word1.equals(word2)) return true;
        int[] freq1 = new int[26];
        int[] freq2 = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            freq1[word1.charAt(i) - 'a']++;
            freq2[word1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if(Math.min(freq1[i], freq2[i]) == 0 && Math.max(freq1[i], freq2[i]) != 0){
                return false;
            }
        }
        Arrays.sort(freq1);
        Arrays.sort(freq2);
        return Arrays.equals(freq1, freq2);
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
