package com.mission.google.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    /**
     * 4
     * 1 1 2 2
     */
   // private static BufferedOutputStream out = new BufferedOutputStream(System.out);
    //private static FastReader sc = new FastReader();
    public static void main(String args[]) throws IOException{

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int[] arr;
        int i = 0;
        for (; i < t; i++) {

            int n = sc.nextInt();
            arr = new int[n];
            int j = 0;
            while (n > 0){
                arr[j] = sc.nextInt();
                j++;
                n--;
            }

            if(ifDancePossible(arr)){
                //out.write("YES".getBytes());
                System.out.print("YES");
            }else{
                //out.write("NO".getBytes());
                System.out.print("No");
            }
        }

        //out.flush();
        //out.close();
        sc.close();
    }

    /**
     * 5
     * 4
     * 1 2 3 4
     * */
    public static boolean ifDancePossible(int [] arr){
        boolean isArraySorted = false;
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i] < arr[i+1]){
                isArraySorted = true;
            }else{
                isArraySorted = false;
                break;
            }
        }
        if(isArraySorted){
            return true;
        }

        return checkIfSortRotated(arr, arr.length);
    }

    static boolean checkIfSortRotated(int arr[], int n) {
        int minEle = Integer.MAX_VALUE;
        int maxEle = Integer.MIN_VALUE;

        int minIndex = -1;

        // Find the minimum element
        // and it's index
        for (int i = 0; i < n; i++) {
            if (arr[i] < minEle) {
                minEle = arr[i];
                minIndex = i;
            }
        }

        boolean flag1 = true;

        // Check if all elements before
        // minIndex are in increasing order
        for (int i = 1; i < minIndex; i++) {
            if (arr[i] < arr[i - 1]) {
                flag1 = false;
                break;
            }
        }

        boolean flag2 = true;

        // Check if all elements after
        // minIndex are in increasing order
        for (int i = minIndex + 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) {
                flag2 = false;
                break;
            }
        }


        //check if the minIndex is 0, i.e the first element is the smallest , which is the case when array is sorted but not rotated.
        if (minIndex == 0) {
            //System.out.print("NO");
            return false;
        }
        // Check if last element of the array
        // is smaller than the element just
        // before the element at minIndex
        if (flag1 && flag2 && (arr[n - 1] < arr[minIndex - 1])) {
            //System.out.println("YES");
            return true;
        } else {
           // System.out.print("NO");
            return false;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = null;
        }

        private String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }


        long nextLong() {
            return Long.parseLong(next());
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static boolean isPossibleToZero(long a[], int n) {
        long sum = 0;
        long max = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            max = Math.max(max, a[i]);
        }
        return (sum%2 == 0 && max <= sum - max);
    }

}
