package com.atu.threadcoreknowledge.startthread;

/**
 * @author: Tom
 * @date: 2020-03-09 11:48
 * @description: 对比start和run两种启动线程的方式
 */
public class StartAndRunMethod {
    public static void main(String[] args) {
        Runnable runnable = () ->{
            System.out.println(Thread.currentThread().getName());
        };
        runnable.run(); //main

        new Thread(runnable).start(); //Thread-0
    }
}
