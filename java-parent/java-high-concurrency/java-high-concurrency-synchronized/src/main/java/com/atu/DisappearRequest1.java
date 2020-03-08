package com.atu;

/**
 * @author: Tom
 * @date: 2020-03-07 22:36
 * @description: 消失的请求-不采用任何手段多线程带来的后果：线程不安全
 */
public class DisappearRequest1 implements Runnable {
    static DisappearRequest1 instance = new DisappearRequest1();
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        //1、线程创建
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        //2.线程启动
        t1.start(); //顺序执行
        t2.start();

        //3、让输出在线程都执行完run函数之后，才执行输出
        t1.join();
        t2.join();
        System.out.println(i); //122386 每次结果都不同

    }

   /* @Override
    public synchronized void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }*/

    /*@Override
    public void run() {
        synchronized (this) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
    }*/

    @Override
    public void run() {
        synchronized (DisappearRequest1.class) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
    }

}
