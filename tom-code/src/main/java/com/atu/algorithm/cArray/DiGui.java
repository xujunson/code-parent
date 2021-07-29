package com.atu.algorithm.cArray;

/**
 * @Author: Tom
 * @Date: 2021/7/28 7:12 下午
 * @Description:
 */
public class DiGui {
    public static void main(String[] args) {
        digui(5);
    }
    static void digui(int time) {
        if (time == 0) {
        }//time==0不执行
        else {
            System.out.println("bigsai前time: " + time);
            digui(time - 1);
            System.out.println("bigsai后time: " + time);
        }
    }
}
