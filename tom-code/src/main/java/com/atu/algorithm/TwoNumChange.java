package com.atu.algorithm;

/**
 * @Author: Tom
 * @Date: 2021/7/5 4:05 下午
 * @Description: 不使用第三个变量，怎么交换两个变量的值？
 *  异或大法：相同为 0，不同为 1
 */
public class TwoNumChange {
    public static void main(String[] args) {
        numChange(0, 5);
    }

    public static void numChange(int num1, int num2) {
        System.out.println("old num1：" + num1 + "，old num2：" + num2);
        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num2 ^ num1;

        System.out.println("new num1：" + num1 + "，new num2：" + num2);

    }
}
