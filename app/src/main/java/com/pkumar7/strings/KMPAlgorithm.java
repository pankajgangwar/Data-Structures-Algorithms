package com.pkumar7.strings;

public class KMPAlgorithm {
    public static void main(String[] args) {
        String src = "abxabcabcaby";
        String pattern = "abcaby";
        KMPAlgorithm obj = new KMPAlgorithm();
        obj.runKMP(src, pattern);
    }

    public void runKMP(String src, String pattern){
        int[] lps = computeKMPTable(pattern.toCharArray());
        int srclen = src.length();
        int patlen = pattern.length();
        int i = 0, j = 0;
        while (i < srclen){
            if(src.charAt(i) ==  pattern.charAt(j)){
                i += 1;
                j += 1;
            }
            if(j == patlen){
                System.out.println("Pattern found at " + (i - j));
                j = lps[j - 1];
            }else if(i < srclen && src.charAt(i) != pattern.charAt(j)){
                if(j != 0){
                    j = lps[j - 1];
                }else{
                    i += 1;
                }
            }
        }
    }

    /*
     * Computer longest prefix which is also a suffix
     * or appears anywhere in the pattern
     * Creates Pie Table/Prefix Table
     * */
    private int[] computeKMPTable(char[] pattern) {
        int n = pattern.length;
        int[] prefix = new int[n];
        int i = 1;
        int j = 0;
        while (i < n){
            if(pattern[i] == pattern[j]){
                prefix[i] = j + 1;
                i += 1;
                j += 1;
            }else{
                if(j != 0){
                    j = prefix[j - 1];
                }else{
                    prefix[i] = 0;
                    i += 1;
                }
            }
        }
        return prefix;
    }
}
