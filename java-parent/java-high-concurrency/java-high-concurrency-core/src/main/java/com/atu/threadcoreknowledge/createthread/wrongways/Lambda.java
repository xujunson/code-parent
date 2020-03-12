package com.atu.threadcoreknowledge.createthread.wrongways;

/**
 * @author: Tom
 * @date: 2020-03-09 10:48
 * @description: lambda表达式创建线程
 */
public class Lambda {
    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();

    }
}
