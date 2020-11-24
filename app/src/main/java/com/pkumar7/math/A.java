package com.pkumar7.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.sound.sampled.Line;

/**
 * Created by Pankaj Kumar on 19/August/2020
 */
class A {
    public static void main(String[] args) {
        A cur = new A();
        String res = cur.convertToTitle(52);
        System.out.println("num = " + res);
    }

    /* 168. Excel Sheet Column Title
    * https://leetcode.com/problems/excel-sheet-column-title/
    * Revisit
    * */
    public String convertToTitle(int n) {
        //return iterative(n);
        return dfs(n);
    }
    public String dfs(int n){
        return n == 0 ? "" : dfs(--n / 26) + (char)('A' + (n % 26));
    }
    public String iterativeI(int n) {
        char[] arr = new char[26];
        char ch = 'A';
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ch++;
        }
        StringBuilder out = new StringBuilder();
        while (n > 0){
            n = n - 1;
            out.append(arr[n % 26]);
            n = n >> 1;
        }
        return out.toString();
    }

    public String iterative(int n) {
        HashMap<Integer, Character> map = new HashMap<>();
        char ch = 'A';
        for (int i = 1; i <= 26 ; i++) {
            map.put(i, ch++);
        }
        StringBuilder builder = new StringBuilder();
        while (n > 0){
            n--;
            builder.insert(0, map.get(n % 26 + 1));
            n = n / 26;
        }
        return builder.toString();
    }

    /* 640. Solve the Equation
    * https://leetcode.com/problems/solve-the-equation/
    * */
    public String solveEquation(String equation) {
        String no_sol = "No solution";
        String infinite_sol = "Infinite solutions";
        if(!equation.contains("=")){
            return no_sol;
        }
        String[] arr = equation.split("=");
        int[] lhs = evaluateExpression(arr[0]);
        int[] rhs = evaluateExpression(arr[1]);
        lhs[0] -= rhs[0];
        lhs[1] = rhs[1] - lhs[1];
        if(lhs[0] == 0 && lhs[1] == 0) return infinite_sol;
        if(lhs[0] == 0) return no_sol;
        return "x=" + lhs[1] / lhs[0];
    }

    public int[] evaluateExpression(String exp){
        String[] tokens = exp.split("(?=[-+])");
        int[] res = new int[2];
        for (String token : tokens){
            if(token.equals("+x") || token.equals("x")) res[0] += 1;
            else if(token.equals("-x")) res[0] -= 1;
            else if(token.contains("x")) res[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
            else res[1] += Integer.parseInt(token);
        }
        return res;
    }

    /* 1180. Count Substrings with Only One Distinct Letter
     * https://leetcode.com/problems/count-substrings-with-only-one-distinct-letter/
     * */
    public int countLetters(String s) {
        int j = 0;
        int res = 0;
        for (int i = 0; i < s.length();) {
            j = i;
            while (j < s.length() && s.charAt(i) == s.charAt(j)){
                j++;
            }
            int n = j - i;
            res += (n * (n + 1) / 2);
            i = j;
        }
        return res;
    }

    /* 1610. Maximum Number of Visible Points
     * https://leetcode.com/problems/maximum-number-of-visible-points/
     */
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int overlapWithSelf = 0;
        List<Double> angles = new ArrayList<>();
        for(List<Integer> point : points){
            if(location.get(0) == point.get(0) && location.get(1) == point.get(1)){
                overlapWithSelf++;
            }else {
                angles.add(getAngle(location, point));
                angles.add(getAngle(location, point) + 360);
            }
        }
        Collections.sort(angles);
        int res = 0;
        Queue<Double> q = new LinkedList<>();
        for(Double ang : angles){
            q.offer(ang);
            while (ang - q.peek() > angle){
                q.poll();
            }
            res = Math.max(res, q.size());
        }
        return res + overlapWithSelf;
    }

    private double getAngle(List<Integer> loc, List<Integer> point) {
        double angle = Math.toDegrees(Math.atan2(loc.get(1) - point.get(1), loc.get(0) - point.get(0)));
        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    /* 263. Ugly Number
    * https://leetcode.com/problems/ugly-number/
    */
    public boolean isUgly(int n) {
        return isUglyIterative(n);
    }
    public boolean isUglyIterative(int n) {
        if(n <= 0) return false;
        for(int i = 2; i <= 5; i++){
            while(n % i == 0){
                n = n / i;
            }
        }
        return n == 1;
    }

    public boolean isUglyRecursive(int n) {
        if(n <= 0) return false;
        if(n == 1) return true;
        if(n % 2 == 0){
            return isUgly(n / 2);
        }else if(n % 3 == 0){
            return isUgly(n / 3);
        }else if(n % 5 == 0){
            return isUgly(n / 5);
        }
        return false;
    }

    /* 592. Fraction Addition and Subtraction
    * https://leetcode.com/problems/fraction-addition-and-subtraction/
    * */
    public String fractionAddition(String exp) {
        String[] fracs = exp.split("(?=[-+])");
        List<Long> num = new ArrayList<>();
        List<Long> den = new ArrayList<>();
        for (int i = 0; i < fracs.length; i++) {
            String[]frac = fracs[i].split("/");
            long a = Long.parseLong(frac[0]);
            long b = Long.parseLong(frac[1]);
            num.add(a);
            den.add(b);
        }

        long lcm = (long)den.get(0);
        for(int j = 1; j < den.size(); j++){
            long second = (long)den.get(j);
            lcm  = lcm(lcm, second);
        }
        long denominator = lcm;
        long numerator = 0;
        for(int j = 0; j < num.size(); j++){
            long b = denominator / (long)den.get(j);
            numerator = (numerator + num.get(j) * b);
        }
        long commonFactor = gcd(Math.abs(numerator), denominator);
        numerator = numerator / commonFactor;
        denominator = denominator /commonFactor;
        StringBuilder out = new StringBuilder();
        out.append(numerator);
        out.append("/");
        out.append(denominator);
        return out.toString();
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        if(a == 0){
            return b;
        }
        return gcd(b % a, a);
    }

    /* 1041. Robot Bounded In Circle
    * https://leetcode.com/problems/robot-bounded-in-circle/
    * */
    public boolean isRobotBounded(String instructions) {
        //return isCircular(instructions);
        return isRobotBounded1(instructions);
    }

    public boolean isRobotBounded1(String instructions) {
        int[][] dirs = new int[][]{ {0, 1}, {1, 0}, {0, -1}, { -1, 0} };
        int x = 0, y = 0;
        char[] instr = instructions.toCharArray();
        int dir = 0;
        for (char ch : instr) {
            if (ch == 'R') {
                dir = (dir + 1) % 4;
            } else if (ch == 'L') {
                dir = (dir + 3) % 4;
            } else {
                x += dirs[dir][0];
                y += dirs[dir][1];
            }
        }
        return x == 0 && y == 0 || dir > 0;// If robot is not facing north
    }

    public boolean isCircular(String instructions) {
        char[] path = instructions.toCharArray();
        int x = 0, y = 0;
        int dir = 0;
        int run = 4;
        while(run-- > 0){
            for (int i=0; i < path.length; i++) {
                char move = path[i];
                if (move == 'R')
                    dir = (dir + 1) % 4;
                else if (move == 'L')
                    dir = (dir + 3) % 4;
                else {
                    if (dir == 0)
                        y++;
                    else if (dir == 1)
                        x++;
                    else if (dir == 2)
                        y--;
                    else
                        x--;
                }
            }
            run--;
        }
        return (x == 0 && y == 0);
    }
}
