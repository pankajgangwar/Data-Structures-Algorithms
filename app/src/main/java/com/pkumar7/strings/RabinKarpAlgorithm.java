package com.pkumar7.strings;

public class RabinKarpAlgorithm {

    int A = 31, MX = (int)1e5;
    long mod = (long)1e9+7;
    long[] pow, ipow;

    long pow(long a, long p){
        long o =1;
        while(p>0){
            if((p&1)==1)o = (o*a)%mod;
            a = (a*a)%mod;
            p>>=1;
        }
        return o;
    }

    public void preHash(){
        pow = new long[1+MX]; ipow = new long[1+MX];
        pow[0] = 1;int A = 31;
        for(int i = 1; i<= MX; i++)pow[i] = (pow[i-1]*A)%mod;
        ipow[MX] = pow(pow[MX], mod-2);
        for(int i = MX-1; i>0; i--)ipow[i] = (A*ipow[i+1])%mod;
        ipow[0] = 1;
    }

    long[] fhash;//, rhash;
    public void makeHash(String t){
        int n = t.length();
        fhash = new long[1+n];
        for(int i = 1; i<= n; i++){
            fhash[i] = (pow[i]*(t.charAt(i-1)-'a'+1))%mod;
            fhash[i] += fhash[i-1];
            if(fhash[i]>=mod)fhash[i]-=mod;
        }
    }

    public long getHash(int l, int r){
        return ((mod+fhash[r]-fhash[l-1])%mod*ipow[l])%mod;
    }

}
