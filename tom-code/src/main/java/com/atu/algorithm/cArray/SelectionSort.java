package com.atu.algorithm.cArray;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Tom
 * @Date: 2022/2/17 10:17
 * @Description: 选择排序
 */
public class SelectionSort {
    public static int[] MySort(int[] arr) {
        if (arr.length == 1) return arr;

        for (int i = 0; i < arr.length; i++) {

            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            int temp = 0;
            temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
            System.out.println(JSONObject.toJSONString(arr));
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};

        System.out.println(JSONObject.toJSONString(MySort(arr)));
    }
}
