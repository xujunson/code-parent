package com.atu.thread.jtdy;

/**
 * @author: Tom
 * @date: 2021-02-08 15:12
 * @description:
 */
public class Thread02_Jtdy_01 {
    private static volatile boolean firstDone = true;

    private int n;

    public Thread02_Jtdy_01(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            while (!firstDone) {
                Thread.yield();
            }
            printFoo.run();
            firstDone = false;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printBar.run() outputs "bar". Do not change or remove this line.
            while (firstDone) {
                Thread.yield();
            }
            printBar.run();
            firstDone = true;

        }
    }

    public static void main(String[] args) {
        final Thread02_Jtdy_01 jtdy_01 = new Thread02_Jtdy_01(10);


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
