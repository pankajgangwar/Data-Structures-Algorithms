package com.pkumar7.datastructures;

public class SegTreeRangeAnd {

    public static void main(String[] args) {
        SegTreeRangeAnd current = new SegTreeRangeAnd();

        int[] arr = {1, 3, 5, 7};
        SegmentTree st = new SegmentTree(arr);

        System.out.println(st.query(0, 3)); // 1
        System.out.println(st.query(2, 3)); // 3

        st.update(3, 10);

        System.out.println(st.query(0, 3)); // 2
        System.out.println(st.query(2, 3)); // 2
    }


    public static class SegmentTree {

        private int[] st;
        private int[] arr;
        int n;

        public SegmentTree(int[] arr) {
            n =  arr.length;
            this.arr = arr;
            int height = (int) Math.ceil(Math.log(n) / Math.log(2));
            int size = 2 * (int) Math.pow(2, height) - 1;
            st = new int[size];
            build(arr, 0, n - 1, 0);
        }

        private void build(int[] arr, int ss, int se, int si) {
            if (ss == se) {
                st[si] = arr[ss];
                return;
            }

            int mid = ss + (se - ss) / 2;
            build(arr, ss, mid, si * 2 + 1);
            build(arr, mid + 1, se, si * 2 + 2);

            st[si] = st[2 * si + 1] & st[2 * si + 2];
        }

        public int query(int qs, int qe) {
            return query(qs, qe, 0, 0, n - 1);
        }

        private int query(int qs, int qe, int si, int ss, int se) {
            if (qs > se || qe < ss) {
                return ~0;
            }

            if (qs <= ss && qe >= se) {
                return st[si];
            }

            int mid = ss + (se - ss) / 2;
            int left = query(qs, qe, 2 * si + 1, ss, mid);
            int right = query(qs, qe, 2 * si + 2, mid + 1, se);

            return left & right;
        }

        public void update(int index, int value) {
            if(index < 0 || index >= n) {
                throw new IllegalArgumentException("Invalid index");
            }
            arr[index] = value;
            update(index, value, 0, 0, n - 1);
        }

        private void update(int index, int value, int si, int ss, int se) {
            if (index < ss || index > se) {
                return;
            }

            if (ss == se) {
                st[si] = value;
                return;
            }

            int mid = ss + (se - ss) / 2;
            if(index <= mid){
                update(index, value, 2 * si + 1, ss, mid);
            }else{
                update(index, value, 2 * si + 2, mid + 1, se);
            }
            st[si] = st[2 * si + 1] & st[2 * si + 2];
        }
    }

    public int minimumDifference(int[] arr, int k) {
        int n = arr.length;
        int ans = Integer.MAX_VALUE;
        SegmentTree tree = new SegmentTree(arr);
        for (int i = 0; i < n; i++) {
            int low = i, high = n - 1;
            while (low <= high){
                int mid = low + (high - low) / 2;
                int res = tree.query(i, mid);
                ans = Math.min(ans, Math.abs(k - res));
                if( res < k){
                    high = mid - 1;
                }else if(res > k){
                    low = mid + 1;
                }else{
                    return ans;
                }
            }
        }
        return ans;
    }
}
