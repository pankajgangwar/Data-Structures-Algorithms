package com.pkumar7.oa;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class AmazonOA {

    public static void main(String[] args) {
        AmazonOA current = new AmazonOA();
        current.maxDeviation("aacaaca");
    }

    /*
    * https://leetcode.com/discuss/interview-question/1802061/Amazon-SDE2-OA
    * Q2. Given an array ranks of ranks of students in a school.
    * All students need to be split into groups k. Find the total 'imbalance' of all groups.
    * An imabalance of a group can be found as :

    Sorting each group in the order of their ranks.
    A group contributes to imbalance if any 2 students in the sorted array have a rank difference of more than 1.
    Find the total sum of imbalance of all such groups.
    * */
    private static int totalImbalance(int[] rank) {
        int rankSize = rank.length;
        int[] ldp = new int[rankSize];
        int[] rdp = new int[rankSize];
        Arrays.fill(ldp, -1);
        Arrays.fill(rdp, rankSize);
        HashMap<Integer, Integer> check = new HashMap<Integer, Integer>();
        for(int i=0; i < rankSize; i++)
        {
            if(check.containsKey(rank[i]+1))
                ldp[i] = check.get(rank[i]+1);
            check.put(rank[i], i);
        }
        check = new HashMap<Integer, Integer>();
        for(int i=rankSize-1; i >=0; i--)
        {
            if(check.containsKey(rank[i]+1))
                rdp[i] = check.get(rank[i]+1);
            check.put(rank[i], i);
        }
        int ans = 0;
        for(int i=0; i < rankSize; i++)
        {
            int start = i-ldp[i];
            int end = rdp[i]-i;
            int j =i, k=i;
            while(j > ldp[i] && rank[j] <= rank[i])
                j--;
            while(k < rdp[i] && rank[k] <= rank[i])
                k++;
            int max = start*end;
            int min = (i-j)*(k-i);
            ans+= (max-min);
        }
        return ans;
    }

    /*
    * https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/dynamic-programming/maximum_sum_subarray_with_at_least_k_elements/topic
    * Max deviation among all substrings
    * https://leetcode.com/problems/substring-with-largest-variance/
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
                int sum = modifiedKadanes(list.stream().mapToInt(i -> i).toArray(), 2);
                if(sum > ans ){
                    ans = sum;
                }
            }
        }
        return ans;
    }

    public int modifiedKadanes(int[] arr, int k) {
        int[] maxSum = new int[arr.length];
        int n = arr.length;
        if(n < k) return 0;
        // use kadane's
        maxSum[0] = arr[0];
        for (int i = 1 ; i < arr.length; i++) {
            maxSum[i] = Math.max(arr[i], maxSum[i - 1] + arr[i]);
        }
        int sum = 0 ;
        for (int i = 0 ; i < k; i++) {
            sum += arr[i];
        }
        int ans = sum;
        for (int i = k ; i < arr.length; i++) {
            sum = sum + arr[i] - arr[i - k];
            ans = Math.max(ans, sum);
            ans = Math.max(ans, sum + maxSum[i - k]);
        }
        return ans;
    }
}
