package com.atu.thread.jtdy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2021-02-08 16:46
 * @description: Lock（公平锁）
 */
public class Thread02_Jtdy_03 {
    private int n;
    Lock lock = new ReentrantLock(true);
    volatile boolean fooPrint = true;

    public Thread02_Jtdy_03(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; ) {
            try {
                lock.lock();
                if (fooPrint) {
                    printFoo.run();
                    i++;
                    fooPrint = false;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; ) {
            try {
                lock.lock();
                if (!fooPrint) {
                    printBar.run();
                    i++;
                    fooPrint = true;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final Thread02_Jtdy_03 jtdy_01 = new Thread02_Jtdy_03(10);

        new Thread(new Runnable() {
            public void run() {
                try {
                    jtdy_01.bar(new Runnable() {
                        public void run() {
                            System.out.println("bar");
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
                    jtdy_01.foo(new Runnable() {
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
