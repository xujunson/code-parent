package com.atu.startthread;

/**
 * @author: Tom
 * @date: 2020-03-09 12:11
 * @description: 演示不能两次调用start方法，否则会报错
 */
public class CantStartTwice {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.start();
    }
}
