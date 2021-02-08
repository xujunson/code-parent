package com.atu.thread.jtdy;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: Tom
 * @date: 2021-02-08 17:04
 * @description: CyclicBarrier 更适合用在循环场景中
 */
public class Thread02_Jtdy_05 {
    private int n;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    volatile boolean fin = true;

    public Thread02_Jtdy_05(int n) {
        this.n = n;
    }

    /**
     * dowait方法中每次都将count减1，减完后立马进行判断看看是否等于0，如果等于0的话就会先去执行之前指定好的任务，
     * 执行完之后再调用nextGeneration方法将栅栏转到下一代，在该方法中会将所有线程唤醒，将计数器的值重新设为parties
     */
    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!fin) {
            }
            printFoo.run();
            fin = false;
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            printBar.run();
            fin = true;
        }
    }

    public static void main(String[] args) {
        final Thread02_Jtdy_05 jtdy_01 = new Thread02_Jtdy_05(10);

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
