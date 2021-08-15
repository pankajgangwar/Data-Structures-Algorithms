package com.pkumar7.math.binaryexponentiation;

public class A {
    public static void main(String[] args) {
        A a = new A();
        int res = a.minNonZeroProduct(4);
        System.out.println(res);
    }

    /*
    * 1969. Minimum Non-Zero Product of the Array Elements
    * https://leetcode.com/problems/minimum-non-zero-product-of-the-array-elements/
    * */
    public int minNonZeroProduct(int p) {
        long mod = (long)1e9 + 7;
        long N = (long)Math.pow(2, p);
        if(p == 1) return p;
        if(p == 2) return (int)((N - 1) * (N - 2));

        long res = modpow(N-2, (N-4)/2 + 1);
        res = res % mod;
        return (int) ((res % mod) * ((N-1)% mod) % mod);
    }

    long modpow(long a, long b){
        long mod = (long)1e9 + 7;
        a = a % mod;
        long res = 1;
        while (b > 0){
            if((b & 1) == 1) res = (res * a) % mod;
            a = (a * a) % mod;
            b = b / 2;
        }
        return res;
    }
}
