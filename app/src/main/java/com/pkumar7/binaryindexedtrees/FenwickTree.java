package com.pkumar7.binaryindexedtrees;

public class FenwickTree {
        int[] tree;
        int n;
        public FenwickTree(int[] arr, int n){
            tree = new int[n + 1];
            this.n = n;
            for(int i = 0; i < n; i++){
                update(i, arr[i]);
            }
        }

        public void update(int idx, int val){
            long updateVal = val - rangeSum(idx, idx);
            idx += 1;
            while(idx < n + 1){
                tree[idx] += updateVal;
                idx += lsb(idx);
            }
        }

        public int rangeSum(int from, int to){
            return prefixSum(to) - prefixSum(from - 1);
        }

        public int prefixSum(int idx){
            idx++;
            int sum = 0;
            while(idx != 0){
                sum += tree[idx];
                idx -= lsb(idx);
            }
            return sum;
        }

        public int lsb(int i){
            return i & -i;//return Integer.lowestOneBit(i);
        }
}
