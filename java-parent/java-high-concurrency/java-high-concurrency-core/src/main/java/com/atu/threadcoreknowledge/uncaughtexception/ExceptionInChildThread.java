package com.atu.threadcoreknowledge.uncaughtexception;

/**
 * @author: Tom
 * @date: 2020-03-11 10:01
 * @description: 单线程时，抛出，处理，有异常堆栈
 * 但是多线程时，子线程发生异常会什么不同？
 */
public class ExceptionInChildThread implements Runnable {
    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
