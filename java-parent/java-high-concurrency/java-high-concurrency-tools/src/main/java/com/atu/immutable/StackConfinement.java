package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 17:31
 * @description: 演示栈封闭的两种情况，基本变量和对象
 * 先演示线程争抢带来的错误结果，然后把变量放到方法内，情况就变了
 */
public class StackConfinement implements Runnable {
    int index = 0; //线程共享，不具备栈封闭的能力

    public void inThread() { //线程安全
        int neverGoOut = 0; //局部变量，不会有线程安全问题
        for (int i = 0; i < 10000; i++) {
            neverGoOut++;
        }
        System.out.println("栈内保护的数字是线程安全的" + neverGoOut);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement r1 = new StackConfinement();
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(r1.index);
    }
}
