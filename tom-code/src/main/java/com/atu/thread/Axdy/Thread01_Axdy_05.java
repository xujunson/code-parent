package com.atu.thread.Axdy;

import java.util.concurrent.CountDownLatch;

/**
 * @author: Tom
 * @date: 2021-01-25 13:58
 * @description: CountDownLatch(减法计数器)
 */
public class Thread01_Axdy_05 {
    CountDownLatch countDownLatch12, countDownLatch23;

    public Thread01_Axdy_05() {
        countDownLatch12 = new CountDownLatch(1);
        countDownLatch23 = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        countDownLatch12.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        //等待计数器归零再向下执行
        countDownLatch12.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        countDownLatch23.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        //等待计数器归零再向下执行
        countDownLatch23.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    public static void main(String[] args) {
        final Thread01_Axdy_05 axdy_03 = new Thread01_Axdy_05();


        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_03.third(new Runnable() {
                        public void run() {
                            System.out.println("third");
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
                    axdy_03.first(new Runnable() {
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
                    axdy_03.second(new Runnable() {
                        public void run() {
                            System.out.println("second");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
