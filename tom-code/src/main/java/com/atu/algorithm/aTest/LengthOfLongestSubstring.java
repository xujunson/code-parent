package com.atu.algorithm.aTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2022/7/6 16:00
 * @Description: 找出其中不含有重复字符的 最长子串 的长度
 */
public class LengthOfLongestSubstring {

    /**
     * 双指针法
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();

        //返回值
        int max = 0;

        //第二个指针
        int right = 0;
        for (int left = 0; left < s.length(); left++) {
            if (map.containsKey(s.charAt(left))) {
                right = Math.max(right, map.get(s.charAt(left)) + 1);
            }
            map.put(s.charAt(left), left);
            max = Math.max(max, left - right + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcddfcef"));
    }
}
