package com.mission.google.bitmagic;

public class LeetCodeProblems {

    public static void main(String args[]){
        LeetCodeProblems leetProblems = new LeetCodeProblems();
        //leetProblems.hammingDistance(1,4);
        //leetProblems.findComplement(5);
        leetProblems.bitwiseComplement(41);
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
        return Integer.bitCount(n);
    }

    /** https://leetcode.com/problems/power-of-two/
     *
     * Input: 16
     * Output: true
     * Explanation: 24 = 16
     */
    public boolean isPowerOfTwo(int n) {
        //return n != 0 && ((n & n -1) == 0);
        System.out.println(Integer.bitCount(n));
        return Integer.bitCount(n) == 1;
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
        int i = 0,  xor = 0;
        for(; i < nums.length; i++){
            xor = xor ^ i ^ nums[i];
            System.out.println(xor);
        }
        return xor ^ i;
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
