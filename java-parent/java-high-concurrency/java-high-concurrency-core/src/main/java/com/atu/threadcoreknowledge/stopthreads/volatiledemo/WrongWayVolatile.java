package com.atu.threadcoreknowledge.stopthreads.volatiledemo;

/**
 * @author: Tom
 * @date: 2020-03-09 18:16
 * @description: 演示用 volatile的局限：part1 看似可行
 */
public class WrongWayVolatile implements Runnable {

    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 100000 && !canceled) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WrongWayVolatile t = new WrongWayVolatile();
        Thread thread = new Thread(t);
        thread.start();
        Thread.sleep(5000);
        t.canceled = true;
    }
}
