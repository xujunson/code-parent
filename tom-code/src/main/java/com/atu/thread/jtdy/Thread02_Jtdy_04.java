package com.atu.thread.jtdy;

/**
 * @author: Tom
 * @date: 2021-02-08 17:02
 * @description: 无锁版本
 */
public class Thread02_Jtdy_04 {
    private int n;

    volatile boolean fooPrint = true;

    public Thread02_Jtdy_04(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; ) {
            if (fooPrint) {
                printFoo.run();
                i++;
                fooPrint = false;
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; ) {
            if (!fooPrint) {
                printBar.run();
                i++;
                fooPrint = true;
            }
        }
    }

    public static void main(String[] args) {
        final Thread02_Jtdy_04 jtdy_01 = new Thread02_Jtdy_04(10);

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
