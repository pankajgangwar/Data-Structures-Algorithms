package com.mission.google.codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main1 {

    /**
     * 4
     * 1 1 2 2
     */
   // private static BufferedOutputStream out = new BufferedOutputStream(System.out);
    //private static FastReader sc = new FastReader();
    public static void main(String args[]) throws IOException{

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] arr = new int[n];

        for (int i = 0 ; i < n; i++) {
            int ele = sc.nextInt();
            arr[i] = ele;
        }

        printXorResult(arr, k);

        //out.flush();
        //out.close();
        sc.close();
    }

    private static void printXorResult(int[] arr, int k) {

        int n = arr.length;
        int[] prefix = new int[n];

        int xor = 0;
        for (int i = 0; i < n; i++) {
            prefix[i] = xor ^ arr[i];
            xor = prefix[i];
        }
    }


}
