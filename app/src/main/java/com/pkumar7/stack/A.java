package com.pkumar7.stack;

/**
 * Created by Pankaj Kumar on 27/September/2020
 */
class A {

    /* 1598. Crawler Log Folder
    * https://leetcode.com/problems/crawler-log-folder/
    * */
    public int minOperations(String[] logs) {
        int steps = 0;
        for (int i = 0; i < logs.length; i++) {
            if(logs[i].equals("../")){
                if(steps > 0){
                    steps--;
                }
            }else if(logs[i].equals("./")){
                steps = steps;
            }else{
                steps++;
            }
        }
        return steps;
    }
}
