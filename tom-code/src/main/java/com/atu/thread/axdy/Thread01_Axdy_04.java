package com.atu.thread.axdy;

import java.util.concurrent.Semaphore;

/**
 * @author: Tom
 * @date: 2021-01-25 11:23
 * @description: semaphore信号量
 */
public class Thread01_Axdy_04 {
    Semaphore semaphore12, semaphore23;

    public Thread01_Axdy_04() {
        //初始的允许请求均设为0
        semaphore12 = new Semaphore(0);
        semaphore23 = new Semaphore(0);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        //释放一个12的信号量
        semaphore12.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        //获取一个12的信号量，没有则阻塞
        semaphore12.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        //释放一个23的信号量
        semaphore23.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        //获取一个23的信号量，没有则阻塞
        semaphore23.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    public static void main(String[] args) {
        final Thread01_Axdy_04 axdy_03 = new Thread01_Axdy_04();


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
