package com.pkumar7.oa;


import java.util.Arrays;
import java.util.LinkedList;

public class AmazonOA {

    public static void main(String[] args) {
        AmazonOA current = new AmazonOA();
        current.maxDeviation("aacaaca");
    }
    /*
    * https://leetcode.com/discuss/interview-question/1742621/Amazon-or-OA-or-Max-deviation-among-all-substrings
    * https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/dynamic-programming/maximum_sum_subarray_with_at_least_k_elements/topic
    * Max deviation among all substrings
    *
    * Let's have a string: abbbcacbcdce

    For substring abbb, you have most frequent letter b -> 3 and least frequent letter a -> 1.
    So the deviation is = most frequent - least frequent = 3 - 1 = 2. You need to look at all the substrings and find the max deviation.

    Here substring cacbcdc has the max deviation.
    Frequency is like below:
    c -> 4, a ->1, b->1, d->1.
    So max freq - min freq = 4 - 1 = 3.

    Among all substrings deviation, this is the max. So need to return it.

    String length is 10^4. So you can't check each substring.
    * */
    public int maxDeviation(String str) {
        int ans = 0;
        for (char ch1 = 'a'; ch1 <= 'z'; ch1++) {
            for (char ch2 = 'a'; ch2 <= 'z'; ch2++) {
                if (ch1 == ch2) {
                    continue;
                }
                LinkedList<Integer> list = new LinkedList<>();
                for (char ch : str.toCharArray()) {
                    if (ch == ch1) {
                        if (!list.isEmpty() && list.peekLast() != -1) {
                            list.addLast(list.pollLast() + 1);
                        } else {
                            list.add(1);
                        }
                    } else if (ch == ch2) {
                        list.addLast(-1);
                    }
                }
                int sum = modifiedKadane(list, 2);
                if(sum > ans ){
                    System.out.println("ch1 = " + ch1 + " , ch2 = " + ch2 + " list = " + list);
                    ans = sum;
                }
            }
        }
        System.out.println("ans = " + ans);
        return ans;
    }

    public int modifiedKadane(LinkedList<Integer> list, int k){
        int n = list.size();
        if(n < k) return 0;
        int[] maxSum = new int[n];
        for (int i = 1; i < n; i++) {
            maxSum[i] = Math.max(list.get(i) , maxSum[i -1] + list.get(i));
        }
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += list.get(i);
        }
        int ans = sum;
        for (int i = k; i < n ; i++) {
            sum += list.get(i) - list.get(i - k);
            ans = Math.max(ans, sum);
            ans = Math.max(ans, sum + maxSum[i - k]);
        }
        return ans;
    }
}
