package com.pkumar7.datastructures.FenwickTree;

class FenwickTreeTest {
    public static void main(String[] args) {
        int n = 10;
        long[] values = new long[]{0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L};

        RangeQueryPointUpdate rangeQueryPointUpdate = new RangeQueryPointUpdate(values);
        long sum = rangeQueryPointUpdate.sum(0, 4);
        System.out.println("sum = " + sum);

        rangeQueryPointUpdate.add(4, 1L);
        long sum1 = rangeQueryPointUpdate.sum(1, 1);
        System.out.println("sum = " + sum1);

        rangeQueryPointUpdate.set(1, 4L);
        long sum2 = rangeQueryPointUpdate.sum(1, 1);
        System.out.println("sum = " + sum2);

        RangeUpdatePointQuery pointQuery = new RangeUpdatePointQuery(values);
        pointQuery.updateRange(1, 2, 1L);
        long sum3 = pointQuery.get(2);
        System.out.println("sum3 = " + sum3);

    }
}
