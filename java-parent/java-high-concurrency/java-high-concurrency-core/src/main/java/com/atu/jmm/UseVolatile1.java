package com.atu.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2020-03-12 20:51
 * @description: volatile适用的情况1
 */
public class UseVolatile1 implements Runnable {
    volatile boolean done = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        UseVolatile1 r = new UseVolatile1();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(((UseVolatile1) r).done);
        System.out.println(((UseVolatile1) r).realA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            setDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        //赋值跟原来的状态无关即可
        done = true;
    }
}
