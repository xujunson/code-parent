package com.atu.algorithm.cArray;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

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
        /*int temp = 0;
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
        }*/


        //
        /**
         * int index = 0;
         *         int index_be = 0;
         *         int index_end = arr.length - 1;
         *
         *         int temp = 0;
         *         for (int i = index_end; i >= 0; i--) {
         *             if (arr[index] > arr[i]) {
         *                 for (int j = index_be; j < arr.length; j++) {
         *                     index_be = j;
         *                     if (arr[index] < arr[j]) {
         *                         temp = arr[j];
         *                         arr[j] = arr[i];
         *                         arr[i] = temp;
         *                         break;
         *                     }
         *                     if(j == i) {
         *                         temp = arr[i];
         *                         arr[i] = arr[index];
         *                         arr[index] = temp;
         *                         break;
         *                     }
         *                 }
         *
         *             }
         *             if(index_be == i) {
         *                 break;
         *             }
         *         }
         */
        quick(0, arr.length - 1, arr);
        return arr;
    }

    /**
     * 快排
     * @param left
     * @param right
     * @param arr
     */
    private static void quick(int left, int right, int[] arr) {
        //System.out.println("left:"+left+" ,right: "+right);
        if (left > right) return;
        int temp = 0;
        int j = left;
        int i = right;
        int indexValue = arr[left];
        while (i != j) {
            while (indexValue <= arr[i] && i > j) {
                i--;
            }

            while (indexValue >= arr[j] && i > j) {
                j++;
            }

            if (i > j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[left] = arr[i];
        arr[i] = indexValue;
        //System.out.println("left2:"+left+" ,right2: "+right);
        quick(left, i - 1, arr);
        //System.out.println("left3:"+left+" ,right3: "+right);
        quick(i + 1, right, arr);
        //System.out.println("left4:"+left+" ,right4: "+right);
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        //int[] arr ={3,1,2,5,4,6,9,7,10,8};
        //int[] arr ={2,1,3,5,4,6,9,7,10,8};
        //int[] arr ={1,2,3,5,4,6,9,7,10,8};
        System.out.println(JSONObject.toJSONString(MySort(arr)));
    }
}