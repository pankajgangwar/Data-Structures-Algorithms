package com.mission.google.datastructures.FenwickTree;

import com.sun.corba.se.spi.activation.BadServerDefinition;

class RangeUpdatePointQuery {

    final int N;
    private long[] originalTree;
    private long[] currentTree;

    public RangeUpdatePointQuery(long[] values){
        if(values == null) throw new IllegalArgumentException("Values array cannot be null !!");

        N = values.length;
        values[0] = 0L;
        long[] fenwickTree = values.clone();
        for (int i = 0; i < N; i++) {
            int parent = i + lsb(i);
            if(parent < N ) fenwickTree[parent] += fenwickTree[i];
        }
        originalTree = fenwickTree;
        currentTree = fenwickTree.clone();
    }

    public void updateRange(int left, int right, long val){
        add(left, val);
        add(right + 1, -val);
    }

    private void add(int index, long val) {
        while (index < N){
            currentTree[index] += val;
            index += lsb(index);
        }
    }

    public long get(int i){
        return prefixSum(i, currentTree) - prefixSum(i - 1, originalTree);
    }

    private long prefixSum(int i, long[] tree) {
        long sum = 0L;
        while (i != 0){
            sum += tree[i];
            i &= ~lsb(i); // i = i - lsb(i);
        }
        return sum;
    }

    private int lsb(int i) {
        return i & -i;
        //return Integer.lowestOneBit(i);
    }
}
