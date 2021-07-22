package com.atu.algorithm.cArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2021/7/13 5:51 下午
 * @Description: 最长无重复子数组
 * 给定一个数组arr，返回arr的最长无重复元素子数组的长度，无重复指的是所有数字都不相同。
 * 子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
 */
public class MaxArrLength {

    /**
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public static int maxLength(int[] arr) {
        //解法1：双指针法
        if (null == arr) return 0;
        int maxVal = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                j = Math.max(j, map.get(arr[i]) + 1);
            }
            map.put(arr[i], i);
            maxVal = Math.max(maxVal, i - j + 1);
        }
        return maxVal;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 1, 2, 2, 3, 4, 5, 6};
        System.out.println(maxLength(arr));
    }
}
