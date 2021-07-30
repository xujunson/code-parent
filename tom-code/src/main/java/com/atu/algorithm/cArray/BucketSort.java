package com.atu.algorithm.cArray;

/**
 * @Author: Tom
 * @Date: 2021/7/30 11:31 上午
 * @Description: 桶排序(简化)
 */
public class BucketSort {
    // 5 个同学分别考了 5 分、3 分、5 分、2 分和 8 分（满分10分） 按照从大到小排序
    public static void solution(int[] arr) {
        int[] indexArr = new int[11];
        for (int i = 0; i < arr.length; i++) {
            indexArr[arr[i]] += 1;
        }

        for (int j = 10; j >= 0; j--) {
            if (indexArr[j] == 0) continue;

            for (int m = 1; m <= indexArr[j]; m++) {
                System.out.print(j + " ,");
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 5, 2, 8};
        solution(arr);
    }
}
