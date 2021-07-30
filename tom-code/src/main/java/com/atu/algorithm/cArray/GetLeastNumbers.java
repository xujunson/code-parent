package com.atu.algorithm.cArray;

import java.util.ArrayList;

/**
 * @Author: Tom
 * @Date: 2021/7/22 5:40 下午
 * @Description: 最小的K个数
 */
public class GetLeastNumbers {
    public ArrayList<Integer> getLeastNumbers(int[] input, int k) {
        //解法1：冒泡排序 每次比较两个相邻的元素，如果它们的顺序错误就把它们交换过来。
        if (input == null) return null;

        // 不能把基本数据类型转化为列表
        ArrayList<Integer> arrayList = new ArrayList<>();
        int temp;
        for (int i = 0; i < input.length - 1; i++) {
            //System.out.println("--------------");
            for (int j = 0; j < input.length - i - 1; j++) {
                //Arrays.stream(input).forEach(System.out::print);
                //System.out.println();
                if (input[j] > input[j + 1]) {
                    temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }

        }
        k = k >= input.length ? input.length : k;
        for (int i = 0; i < k; i++) {
            arrayList.add(input[i]);
        }
        return arrayList;
    }

    public static void main(String[] args) {
        // int[] t = {8, 4, 5, 1, 6, 2, 7, 3};
        int[] t = {8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(new GetLeastNumbers().getLeastNumbers(t, 4));
    }
}
