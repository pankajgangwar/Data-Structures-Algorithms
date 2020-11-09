package com.pkumar7.arrays;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 05/September/2020
 */
class B {
    public static void main(String[] args) throws ParseException {
        String []arr = new String[] {"02:45PM-09:00PM","09:00AM-12:15PM","12:30PM-02:00PM"};
        ArrayChallenge(arr);
    }

    /* 1018. Binary Prefix Divisible By 5
     * https://leetcode.com/problems/binary-prefix-divisible-by-5/
     * */
    public List<Boolean> prefixesDivBy5(int[] arr) {
        List<Boolean> res = new ArrayList<>();
        int remainder = 0;
        for (int i = 0; i < arr.length; i++) {
            remainder = (2 * remainder + arr[i]) % 5;
            if(remainder == 0){
                res.add(true);
            }else{
                res.add(false);
            }
        }
        return res;
    }

    /* 548. Split Array with Equal Sum
     * https://leetcode.com/problems/split-array-with-equal-sum/
     * */
    public boolean splitArray(int[] nums) {
        int n = nums.length;
        if(n < 7) return false;
        int[] sum = new int[n];
        sum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        for (int j = 3; j < n - 3; j++) {
            HashSet<Integer> sets = new HashSet<>();
            for (int i = 1; i < j - 1; i++) {
                if(sum[i - 1] == sum[j - 1] - sum[i] ){
                    sets.add(sum[i - 1]);
                }
            }
            for (int k = j + 2; k < n - 1; k++) {
                int quaterSum = sum[k - 1] - sum[j];
                if(sum[n -1] - sum[k] == quaterSum && sets.contains(quaterSum)){
                    return true;
                }
            }
        }
        return false;
    }

    /* 1646. Get Maximum in Generated Array
    * https://leetcode.com/problems/get-maximum-in-generated-array/
    * */
    public int getMaximumGenerated(int n) {
        if(n == 0) return 0;
        if(n == 1) return 1;
        int[] nums = new int[n + 1];
        nums[0] = 0;
        nums[1] = 1;
        for (int i = 1; (2 * i + 1) <= n; i++) {
            nums[2 * i] = nums[i];
            nums[2 * i + 1] = nums[i] + nums[i + 1];
        }
        int max = Arrays.stream(nums).max().getAsInt();
        return max;
    }

    /* 682. Baseball Game
     * https://leetcode.com/problems/baseball-game/
     * */
    public int calPoints(String[] ops) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            switch (op){
                case "+": {
                    int last = list.size() - 1;
                    int secondLast = last - 1;
                    list.add(list.get(last) + list.get(secondLast));
                    break;
                }
                case "C":
                    list.remove(list.size() - 1);
                    break;
                case "D": {
                    int last = list.size() - 1;
                    list.add(list.get(last) * 2);
                    break;
                }
                default:
                    list.add(Integer.valueOf(op));
            }
        }
        int sum = 0;
        for (int x : list){
            sum += x;
        }
        return sum;
    }

    /* 867. Transpose Matrix
     * https://leetcode.com/problems/transpose-matrix/
     * */
    public int[][] transpose(int[][] grid) {
        int i = 0, j = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] res = new int[cols][rows];
        while (i < rows && j < cols){
            int tempr = 0;
            int tempc = 0;
            while (tempr <= i && tempc <= j){
                int first = grid[tempr][j];
                int second = grid[i][tempc];
                res[tempr][j] = second;
                res[i][tempc] = first;
                tempr++;
                tempc++;
            }
            i++;
            j++;
        }
        if(rows == cols){
            return res;
        }else{
            res = new int[cols][rows];
            int tempR = 0, tempC = 0;
            for (int k = 0; k < rows; k++) {
                for (int l = 0; l < cols; l++) {
                    if(tempR == cols){
                        tempR = 0;
                        tempC++;
                    }
                    res[tempR++][tempC] = grid[k][l];
                }
            }
        }
        return res;
    }

    /* 1624. Largest Substring Between Two Equal Characters
     * https://leetcode.com/problems/largest-substring-between-two-equal-characters/
     * */
    public int maxLengthBetweenEqualCharacters(String s) {
        int n = s.length();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int lastIdx = s.lastIndexOf(s.charAt(i)+"");
            if(i < lastIdx){
                max = Math.max(max, lastIdx - i - 1);
            }
        }
        return max == Integer.MIN_VALUE ? -1 : max;
    }

    /*
    * A simple program to find maximum free time between different intervals
    * */
    public static String ArrayChallenge(String[] strArr) throws ParseException {
        int n = strArr.length;
        Date[][] arr = new Date[n][2];
        for (int i = 0; i < strArr.length; i++) {
            String currtime = strArr[i];
            DateFormat sd = new SimpleDateFormat("hh:mmaa");
            String starttime = currtime.split("-")[0];
            String endtime = currtime.split("-")[1];
            Date startdt = sd.parse(starttime);
            Date enddt = sd.parse(endtime);
            arr[i][0] = startdt;
            arr[i][1] = enddt;
        }
        Arrays.sort(arr, (d1, d2) -> {
            if(d1[0].after(d2[0])){
                return 1;
            }
            if(d1[0].before(d2[0])){
                return -1;
            }
            return 0;
        });

        Date lastendDt = arr[0][1];
        long maxm = 0;
        for (int i = 1; i < n; i++) {
            Date startDt = arr[i][0];
            maxm = Math.max(maxm, startDt.getTime() - lastendDt.getTime());
            lastendDt = arr[i][1];
        }
        maxm = (maxm / 1000) / 60;
        int hr = Math.toIntExact(maxm / 60);
        int mn = Math.toIntExact(maxm % 60);
        String res = String.format("%02d:%02d", hr, mn);
        System.out.println("res = " + res);
        return res;
    }

    /*
    * 1608. Special Array With X Elements Greater Than or Equal X
    * https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/
    * */
    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < nums.length ; i++) {
            if(n - i <= nums[i] ){
                if(i > 0 && n - i <= nums[i - 1]){
                    continue;
                }
                return n - i;
            }
        }
        return -1;
    }

    /* https://leetcode.com/problems/sum-of-all-odd-length-subarrays/
     * 1588. Sum of All Odd Length Subarrays
     * */
    public int sumOddLengthSubarrays(int[] arr) {
        int sum = 0;
        sum += Arrays.stream(arr).sum();
        int n = arr.length;
        int prefixs[] =  new int[n];
        prefixs[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prefixs[i] = arr[i] + prefixs[i - 1];
        }
        int len = 3;
        while (len <= n){
            for (int i = 0; i + len - 1 < n; i++) {
                if(i > 0){
                    sum += prefixs[i + len - 1] - prefixs[i - 1];
                }else{
                    sum += prefixs[i + len - 1];
                }
            }
            len += 2;
        }
        System.out.println("sum = " + sum);
        return sum;
    }

    /* 1590. Make Sum Divisible by P
     * https://leetcode.com/problems/make-sum-divisible-by-p/
     * */
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;
        int mod = 0;
        for(int x : nums){
            mod = (mod + x) % p;
        }
        if(mod == 0) {
            return 0;
        }
        int min = n;
        HashMap<Integer,Integer> posMap = new HashMap<Integer,Integer>();
        posMap.put(0, -1);
        int r_mod = 0;
        for(int i = 0; i < n; i++ ) {
            r_mod = (r_mod + nums[i]) % p;
            int comp = (p - mod + r_mod) % p;
            if(posMap.containsKey(comp)){
                min = Math.min(min, i - posMap.get(comp));
            }
            posMap.put(r_mod, i);
        }
        min = min >= n ? -1 : min;
        System.out.println("mn = " + min);
        return min;
    }

    /* 1582. Special Positions in a Binary Matrix
     * https://leetcode.com/problems/special-positions-in-a-binary-matrix/
     * */
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] rows = new int[m];
        int[] cols = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 1){
                    rows[i]++;
                    cols[j]++;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 1 && (rows[i] == 1 && cols[j] == 1)) {
                    res++;
                }
            }
        }
        return res;
    }

    /* 41. First Missing Positive
    * https://leetcode.com/problems/first-missing-positive/
    */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++){
            if(nums[i] <= 0 || nums[i] > n){
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if(nums[i] > n + 1){
                continue;
            }
            int x = Math.abs(nums[i]) -1 ;
            if( x < n && nums[x] > 0 ){
                nums[x] = -nums[x];
            }
        }

        for(int i = 0; i < n; i++){
            if(nums[i] > 0){
                return i+1;
            }
        }
        return n+1;
    }

    public int firstMissingPositive1(int[] nums){
        int[] arr = findMissing(nums);
        int size = arr.length;
        for(int i = 0; i < size; i++){
            int x = Math.abs(arr[i]);
            if( x-1 < size && arr[x-1] > 0 ){
                arr[x-1] = -arr[x-1];
            }
        }

        for(int i = 0; i < size; i++){
            if(arr[i] > 0){
                return i+1;
            }
        }
        return size+1;
    }

    public int[] findMissing(int arr[]){
        int shift = segregate(arr);
        int arr2[] = new int[arr.length - shift];
        for(int i = shift, j = 0; i < arr.length; i++, j++ ){
            arr2[j] = arr[i];
        }
        return arr2;
    }

    public int segregate(int arr[]){
        int temp = 0;
        int j = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i] <= 0){
                temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
                j++;
            }
        }
        return j;
    }

    /**
     * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
     * 448. Find All Numbers Disappeared in an Array
     * **/
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i] ) - 1;
            if(nums[val] > 0){
                nums[val] = -nums[val];
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                res.add(i + 1);
            }
        }
        return res;
    }
}
