package com.atu.threadcoreknowledge.createthread;

/**
 * @author: Tom
 * @date: 2020-03-08 22:23
 * @description: 用Thread 方式实现线程
 */
public class ThreadStyle extends Thread {
    public static void main(String[] args) {
        new ThreadStyle().start();
    }

    @Override
    public void run() {
        System.out.println("用Thread类实现线程");
    }
}
