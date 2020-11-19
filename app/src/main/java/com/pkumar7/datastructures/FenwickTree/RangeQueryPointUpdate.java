package com.pkumar7.datastructures.FenwickTree;

import java.util.Arrays;

public class RangeQueryPointUpdate {
    final int N;
    private long[] tree;

    public RangeQueryPointUpdate(int sz){
        tree = new long[(N  = sz + 1)];
    }

    public RangeQueryPointUpdate(long[] values) {
        if(values == null) throw new IllegalArgumentException("Values array cannot be null !!");
        N = values.length;
        values[0] = 0L;

        tree = values.clone();

        for (int i = 0; i < N; i++) {
            int parent = i + lsb(i);
            if(parent < N ) tree[parent] += tree[i];
        }
    }

    private int lsb(int i) {
        return i & -i;
        //return Integer.lowestOneBit(i);
    }

    private long prefixSum(int i) {
        long sum = 0L;
        while (i != 0){
            sum += tree[i];
            i &= ~lsb(i); // Equivalent to , i -= lsb(i);
        }
        return sum;
    }

    public long sum(int left, int right) {
        if(right < left) throw new IllegalArgumentException("Make sure right >= left");
        return prefixSum(right) - prefixSum(left - 1);
    }

    public void add(int i, long v){
        while (i < N){
            tree[i] += v;
            i += lsb(i);
        }
    }

    public void update(int i, long v){
        long val = sum(i, i);
        add(i, v - val);
    }

    @Override
    public String toString() {
        return Arrays.toString(tree);
    }
}
