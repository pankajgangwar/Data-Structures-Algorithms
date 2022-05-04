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
