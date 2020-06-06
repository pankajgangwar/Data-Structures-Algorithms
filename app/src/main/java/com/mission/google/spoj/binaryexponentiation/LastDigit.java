package com.mission.google.spoj.binaryexponentiation;

import java.util.Scanner;

class LastDigit {
    /*
    https://www.spoj.com/problems/LASTDIG/
    */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int mod = 10;
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            long res = 1;
            while (b > 0){
                if(b % 2 == 1) res = (res * a) % mod;
                a = (a * a) % mod;
                b = b / 2;
            }
            String str = String.valueOf(res);
            int ans = str.charAt(str.length() - 1) - '0';
            System.out.println(ans);
        }
        sc.close();
    }
}
