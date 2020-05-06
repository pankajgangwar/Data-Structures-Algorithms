package com.mission.google.codejam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Solution1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
       /* do {
            in.nextInt();
        } while (in.hasNextLine());*/
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int case_number = 1; case_number <= t; ++case_number) {
            int N = in.nextInt();
            int[][] intervals = new int[N][3];
            for (int i = 0; i < N; i++) {
                int[] curr = new int[3];
                curr[0] = in.nextInt();
                curr[1] = in.nextInt();
                curr[2] = i;
                intervals[i] = curr;
            }
            String output = schedule(intervals);
            String res = "Case #"+case_number + ": " + output;
            System.out.println(res);
        }
    }

    public static String schedule(int[][] intervals){
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                int comp = Integer.compare(a[0], b[0]);
                if(comp == 0){
                    return Integer.compare(a[1], b[1]);
                }
                return comp;
            }
        });

        boolean isJFree = true;
        boolean isCFree = true;
        int[] cInt = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] jInt = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        int n = intervals.length;
        char[] res = new char[n];
        for (int[] curr : intervals){
            if(curr[0] >= jInt[1]) {
                isJFree = true;
            }else if(curr[0] >= cInt[1]) {
                isCFree = true;
            }else if(!isCFree && !isJFree){
                //System.out.println("IMPOSSIBLE");
                return new String("IMPOSSIBLE");
            }
            if(isCFree){
                cInt = curr;
                isCFree = false;
                res[curr[2]] = 'C';
            }else if(isJFree){
                jInt = curr;
                isJFree = false;
                res[curr[2]] = 'J';
            }
        }
        //System.out.println("builder = " + builder.toString());
        return new String(res);
    }

}
