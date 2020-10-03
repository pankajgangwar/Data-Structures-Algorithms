package com.pkumar7.leetcode;

public class LeetCodeStringProblems {



    public static void main(String args[]){

    }


    /**
     * https://leetcode.com/problems/defanging-an-ip-address/
     * Input: address = "1.1.1.1"
     * Output: "1[.]1[.]1[.]1"
     *
     */
    public String defangIPaddr(String address) {
        String to_append = "[.]";

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < address.length(); i++) {

            if(address.charAt(i) != '.'){
                builder.append(address.charAt(i));
            }else{
                builder.append(to_append);
            }
        }
        return builder.toString();
    }
}
