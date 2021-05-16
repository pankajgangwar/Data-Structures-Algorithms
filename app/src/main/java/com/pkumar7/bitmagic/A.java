package com.pkumar7.bitmagic;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 17/August/2020
 */
class A {
    public static void main(String[] args) {

    }
    /*
     * 1863. Sum of All Subset XOR Totals
     * https://leetcode.com/problems/sum-of-all-subset-xor-totals/
     * */
    public int subsetXORSum(int[] nums) {
        int n = nums.length;
        int bits = 0;
        for (int i = 0; i < n; ++i){
            bits |= nums[i];
        }
        int ans = bits * (int)Math.pow(2, n-1);
        return ans;
    }

    /* 1611. Minimum One Bit Operations to Make Integers Zero
     * https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero/
     * */
    public int minimumOneBitOperations(int n) {
        if(n <= 1) return n;
        int bit = 0;
        while ((1 << bit) <= n){
            bit++;
        }
        return ((1 << bit) - 1) - minimumOneBitOperations(n - (1 << bit - 1));
    }

    /* 393. UTF-8 Validation
    * https://leetcode.com/problems/utf-8-validation/
    * */
    public boolean validUtf8(int[] data) {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            int x = data[i];
            if(count == 0){
                if(x >> 5 == 0b110){ // Divide by 32
                    count = 1;
                }else if(x >> 4 == 0b1110) { // Divide by 16
                    count = 2;
                }else if(x >> 3 == 0b11110) { // Divide by 8
                    count = 3;
                }else if(x >> 7 != 0) { // Divide by 128
                    return false;
                }
            }else {
                if(x >> 6 != 0b10) { // Divide by 64
                    return false;
                }
                count--;
            }
        }
        return count == 0;
    }

    /* 477. Total Hamming Distance
    * https://leetcode.com/problems/total-hamming-distance/
    * */
    public int totalHammingDistance(int[] nums) {
        int distance = 0;
        int n = nums.length;
        for(int i = 0; i < 32; i++){
            int count = 0;
            for(int j = 0; j < n; j++){
                count += (nums[j] >> i & 1) == 0 ? 1 : 0;
            }
            distance += count * (n - count);
        }
        return distance;
    }

    /*693. Binary Number with Alternating Bits
    * https://leetcode.com/problems/binary-number-with-alternating-bits/
    * */
    public boolean hasAlternatingBits(int n) {
        boolean isSame = false;
        int prev = n & 1;
        n = n >> 1;
        while(n != 0) {
            if((n & 1) == prev) return false;
            prev = n & 1;
            n = n >> 1;
        }
        return true;
    }

    /**
     * 461. Hamming Distance
     * https://leetcode.com/problems/hamming-distance/
     * ***/
    public void hammingDistance(int x, int y) {
        int total_pos = Integer.bitCount(x ^ y);
        System.out.println(total_pos);
    }

    /**
     * https://leetcode.com/problems/number-complement/
     * https://leetcode.com/problems/complement-of-base-10-integer/
     * **/
    public int bitwiseComplement(int N) {
        //return findComplement(N);
        return solution1(N);
    }

    public int solution2(int num){
        return (Integer.highestOneBit(num) << 1 ) - num - 1;
    }

    public int solution1(int num){
        if(num == 0) return 1;
        int bitmask = num;
        bitmask |= bitmask >> 1;
        bitmask |= bitmask >> 2;
        bitmask |= bitmask >> 4;
        bitmask |= bitmask >> 8;
        bitmask |= bitmask >> 16;
        return bitmask ^ num;
    }

    /*https://leetcode.com/problems/number-complement/ */
    public int findComplement(int num) {
        String binary = Integer.toBinaryString(num);
        char[] arr = binary.toCharArray();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < arr.length; i++) {
            int curr = arr[i] - '0';
            curr = curr ^ 1;
            builder.append(curr);
        }
        String transformed = builder.toString();
        return Integer.parseInt(transformed, 2);
    }


    /****
     *https://www.geeksforgeeks.org/count-total-bits-number/
     */
    public void countBits(){
        int n = 5;
        int count = 0;
        while(n != 0){
            count++;
            n >>= 1;
            System.out.println("Int " + n);
        }
        System.out.println("Total bits " + count);
    }

    /**
     * https://www.geeksforgeeks.org/count-set-bits-in-an-integer/
     * **/
    public void countSetBits(){
        int n = 5;
        String binary = Integer.toBinaryString(n);
        int i = 0;
        int bitSets = 0;
        while(i < binary.length() ){
            if(binary.charAt(i) == '1'){
                bitSets++;
            }
            i++;
        }
        System.out.println(Integer.bitCount(n));
        System.out.println("Total bits set " + bitSets);

        //Another approach
        /*int count = 0;
        while(n != 0){
            count+= n & 1;
            n >>= 1;
        }
        int val = n & 1;
        System.out.println("count " + count);*/
    }

    /***
     * https://leetcode.com/problems/number-of-1-bits/
     * **/
    public int hammingWeight(int n) {
        int res = 0;
        for(int i = 1; i <= 32; i++){
            res += (n >>> i) & 1;
        }
        return res;
        /*while(n != 0){
            res += (n & 1);
            n = n >>> 1;
        }
        return res;
        return Integer.bitCount(n);
        */
    }

    /** https://leetcode.com/problems/power-of-two/
     *
     * Input: 16
     * Output: true
     * Explanation: 24 = 16
     */
    public boolean isPowerOfTwo(int n) {
        if (n == 0) {
            return false;
        }
        while (n != 1) {
            if (n % 2 != 0) {
                return false;
            }
            n = n / 2;
        }
        return true;
    }

    /***
     * https://leetcode.com/problems/counting-bits/
     * 0 <= i <= num
     * Input: 2
     * Output: [0,1,1]
     *
     * Input: 5
     * Output: [0,1,1,2,1,2]
     * */
    public int[] countBits(int num) {
        int []result = new int[num + 1];
        /*for(int i =0; i <= num; i++){
            result[i] = Integer.bitCount(i);
        }
        return result;*/
        result[0] = 0;
        for(int i = 1 ; i <= num; i++){
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/missing-number/
     * Input: [3,0,1]
     * Output: 2
     *
     * Input: [9,6,4,2,3,5,7,0,1]
     * Output: 8
     * */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n*(n + 1) / 2;
        int actualSum = 0;
        for(int a : nums){
            actualSum += a;
        }
        return sum - actualSum;
    }

    /**
     * https://leetcode.com/problems/find-the-duplicate-number/
     * Hint: How to detect cycles in linkedlist
     * **/
    public int findDuplicate(int[] nums) {
        if (nums.length <= 1) {
            return -1;
        }
        int slow = nums[0];
        int fast = nums[nums[slow]];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
