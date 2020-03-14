package com.atu.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * @author: Tom
 * @date: 2020-03-10 19:03
 * @description: 两个线程交替打印0~100的奇偶数，用wait和notify
 */
public class WaitNotifyPrintOddEvenWait {
    private static final Object lock = new Object();

    private static int count = 0;

    public static void main(String[] args) {
        new Thread(new TurningRunner()).start();
        new Thread(new TurningRunner()).start();
    }

    //1.拿到锁就打印，不去判断是否为奇偶数
    //2.打印完,唤醒其他线程，自己就休眠

    static class TurningRunner implements Runnable {

        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + "：" + count++);
                    lock.notify();
                    if (count <= 100) {
                        try {
                            //如果任务还没结束，就让出当前的锁，并且自己休眠
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
