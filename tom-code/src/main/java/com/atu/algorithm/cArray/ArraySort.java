package com.atu.algorithm.cArray;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Tom
 * @Date: 2021/7/27 4:50 下午
 * @Description: 数组 排序
 */
public class ArraySort {
    public static int[] MySort(int[] arr) {
        if (arr.length == 1) return arr;

        //解法1：冒泡排序 超时
        /*int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }*/

        // 解法2：希尔排序
        int temp = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    temp = arr[j];
                    arr[j] = arr[j - gap];
                    arr[j - gap] = temp;
                    j = j - gap;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 3, 1, 4};
        System.out.println(JSONObject.toJSONString(MySort(arr)));
    }
}