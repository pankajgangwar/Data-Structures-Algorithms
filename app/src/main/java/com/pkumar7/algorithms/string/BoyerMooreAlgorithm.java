package com.pkumar7.algorithms.string;

public class BoyerMooreAlgorithm {

    /* Title:     Boyer-Moore string matching algorithm
     * Author:    H.W. Lang
     *            Fachhochschule Flensburg, University of Applied Sciences
     *            Flensburg, Germany
     * Date:      2007
     * Mail:      lang@fh-flensburg.de
     * Web:       http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/bmen.htm
     * Reference: R.S. Boyer, J.S. Moore: A Fast String Searching Algorithm.
     *            Communications of the ACM, 20, 10, 762-772 (1977)
     */

    private char[] p, t;       // pattern, text
    private int m, n;          // pattern length, text length
    private static int alphabetsize = 256;
    private int[] occ;         // occurence function
    private String matches;    // string of match positions
    private char[] showmatches;// char array that shows matches
    private int[] f;
    private int[] s;
    private static String name = "Boyer-Moore";


    public BoyerMooreAlgorithm() {
        occ = new int[alphabetsize];
    }

    public void search(String tt, String pp) {
        setText(tt);
        setPattern(pp);
        bmSearch();
    }

    /**
     * sets the text
     */
    private void setText(String tt) {
        n = tt.length();
        t = tt.toCharArray();
        initmatches();
    }

    /**
     * sets the pattern
     */
    public void setPattern(String pp) {
        m = pp.length();
        p = pp.toCharArray();
        f = new int[m + 1];
        s = new int[m + 1];
        bmPreprocess();
    }

    /**
     * computation of the occurrence function
     */
    private void bmInitocc() {
        char a;
        int j;

        for (a = 0; a < alphabetsize; a++)
            occ[a] = -1;

        for (j = 0; j < m; j++) {
            a = p[j];
            occ[a] = j;
        }
    }

    /**
     * preprocessing according to the pattern (part 1)
     */
    private void bmPreprocess1() {
        int i = m, j = m + 1;
        f[i] = j;
        while (i > 0) {
            while (j <= m && p[i - 1] != p[j - 1]) {
                if (s[j] == 0) s[j] = j - i;
                j = f[j];
            }
            i--;
            j--;
            f[i] = j;
        }
    }

    /**
     * preprocessing according to the pattern (part 2)
     */
    private void bmPreprocess2() {
        int i, j;
        j = f[0];
        for (i = 0; i <= m; i++) {
            if (s[i] == 0) s[i] = j;
            if (i == j) j = f[j];
        }
    }

    /**
     * preprocessing according to the pattern
     */
    private void bmPreprocess() {
        bmInitocc();
        bmPreprocess1();
        bmPreprocess2();
    }

    /**
     * initializes match positions and the array showmatches
     */
    private void initmatches() {
        matches = "";
        showmatches = new char[n];
        for (int i = 0; i < n; i++)
            showmatches[i] = ' ';
    }

    /**
     * searches the text for all occurences of the pattern
     */
    private void bmSearch() {
        int i = 0, j;
        while (i <= n - m) {
            j = m - 1;
            while (j >= 0 && p[j] == t[i + j]) j--;
            if (j < 0) {
                report(i);
                i += s[0];
            } else
                i += Math.max(s[j + 1], j - occ[t[i + j]]);
        }
    }

    /**
     * reports a match
     */
    private void report(int i) {
        matches += i + " ";
        showmatches[i] = '^';
    }

    /**
     * returns the match positions after the search
     */
    public String getMatches() {
        return matches;
    }

    // only for test purposes
    public static void main(String[] args) {
        BoyerMooreAlgorithm bmAlgo = new BoyerMooreAlgorithm();
/*        System.out.println(name);
        String tt, pp;
        tt = "abcdabcd";
        pp = "abc";
        bmAlgo.search(tt, pp);
        System.out.println(pp);
        System.out.println(tt);
        System.out.println(bmAlgo.showmatches);
        System.out.println(bmAlgo.getMatches());
        tt = "abababaa";
        pp = "aba";
        bmAlgo.search(tt, pp);
        System.out.println(pp);
        System.out.println(tt);
        System.out.println(bmAlgo.showmatches);
        System.out.println(bmAlgo.getMatches());*/

        String text = "ABAAAABAACD";
        String patter = "ABA";
        bmAlgo.search(text.toCharArray(), patter.toCharArray());

    }

    /**
     * https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-heuristic/
     * ***/
    public void preprocessing_strong_suffix(int[] shift, int[] bpos, char[] pat,int m){
        int i = m, j = m+1;
        bpos[i] = j;
        while ( i > 0){
            while (j <= m && pat[i -1]!= pat[j-1]){
                if(shift[j] == 0)
                    shift[j] = j-i;
                j = bpos[j];
            }
            i--;
            j--;
            bpos[i] = j;
        }
    }

    public void preprocess_case2(int[] shift, int[] bpos, int m){
        int i, j;
        j = bpos[0];
        for (i = 0; i <=m ; i++) {
            if(shift[i] == 0)
                shift[i] = j;
            if(i == j)
                j = bpos[j];
        }
    }

    public void search(char[] text, char[] pattern){
        int s = 0, j;
        int m = pattern.length;
        int n = text.length;
        int bpos[] = new int[m+1];
        int shift[] = new int[m+1];

        for (int i = 0; i < m+1; i++) {
            shift[i] = 0;
        }
        preprocessing_strong_suffix(shift, bpos, pattern, m);
        preprocess_case2(shift, bpos, m);
        while (s <= n-m){
            j = m-1;
            while (j >= 0 && pattern[j] == text[s+j])
                j--;

            if(j < 0){
                System.out.println("Pattern occurs at shift = " + s);
                s += shift[0];
            }else{
                s+= shift[j+1];
            }
        }
    }

}// end class BoyerMooreAlgorithm
