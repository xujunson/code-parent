package com.atu.thread.axdy;

/**
 * @author: Tom
 * @date: 2021-01-25 11:11
 * @description:
 */
public class Thread01_Axdy_03 {
    private static volatile boolean firstDone = false;
    private static volatile boolean secondDone = false;

    public Thread01_Axdy_03() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.

        printFirst.run();
        firstDone = true;
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        while (!firstDone) {
        }
        printSecond.run();
        secondDone = true;
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        while (!secondDone) {
            Thread.sleep(1000);
        }
        printThird.run();
    }

    public static void main(String[] args) {
        final Thread01_Axdy_03 axdy_03 = new Thread01_Axdy_03();


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

