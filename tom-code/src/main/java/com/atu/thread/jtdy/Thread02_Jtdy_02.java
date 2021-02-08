package com.atu.thread.jtdy;

import java.util.concurrent.Semaphore;

/**
 * @author: Tom
 * @date: 2021-02-08 16:06
 * @description: Semaphore 在该场景下有点类似红绿灯交替变换的情境，因此信号量成了首选思路：
 */
public class Thread02_Jtdy_02 {
    private int n;
    Semaphore foo = new Semaphore(1);
    Semaphore bar = new Semaphore(0);

    public Thread02_Jtdy_02(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
    public static void main(String[] args) {
        final Thread02_Jtdy_02 jtdy_02 = new Thread02_Jtdy_02(10);


        new Thread(new Runnable() {
            public void run() {
                try {
                    jtdy_02.bar(new Runnable() {
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
                    jtdy_02.foo(new Runnable() {
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
