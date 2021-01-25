package com.atu.thread.axdy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2021-01-25 10:10
 * @description:
 */
public class Thread01_Axdy_01 {
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public Thread01_Axdy_01() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        if (atomicInteger.get() == 0) {
            printFirst.run();
            atomicInteger.incrementAndGet();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        if (atomicInteger.get() == 1) {
            printSecond.run();
            atomicInteger.incrementAndGet();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        if (atomicInteger.get() == 2) {
            printThird.run();
        }

    }

    public static void main(String[] args) {
        final Thread01_Axdy_01 axdy_01 = new Thread01_Axdy_01();
        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_01.first(new Runnable() {
                        public void run() {
                            System.out.println("first");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_01.second(new Runnable() {
                        public void run() {
                            System.out.println("second");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_01.third(new Runnable() {
                        public void run() {
                            System.out.println("third");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
