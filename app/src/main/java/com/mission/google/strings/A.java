package com.mission.google.strings;

/**
 * Created by Pankaj Kumar on 13/August/2020
 */
class A {
    /*
    * 158. Read N Characters Given Read4 II - Call multiple times
    * https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
    * */
    class Reader4 {
        public int read4(char[] buf){
            return 0;
        }
    }
    public class Solution extends Reader4 {
        /**
         * @param buf Destination buffer
         * @param n   Number of characters to read
         * @return    The number of actual characters read
         */
        int cnt = 0;
        int idx = 0;
        char[] temp = new char[4];
        int buffLen = 0;
        public int read(char[] buf, int n) {
            buffLen = 0;
            while(buffLen < n ){
                if(cnt == 0){
                    temp = new char[4];
                    cnt = read4(temp);
                    idx = 0;
                }
                if(cnt == 0) break;
                for(int i = idx; i < 4 && cnt > 0 && buffLen < n; i++, idx++, cnt--, buffLen++){
                    buf[buffLen] = temp[i];
                }
            }
            return buffLen;
        }
    }
}
