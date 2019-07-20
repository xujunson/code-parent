package com.atu.senior.Thread.deadlock;

/**
 * @Author: xsy
 * @Date: 2019/3/13 9:19
 * @Description:
 */
public class DeadLock implements Runnable {
    private int i = 0;

    public void run() {
        while (true) {
            if (i % 2 == 0) {
                System.out.println(i+"--------------------1");
                synchronized (LockA.lockA) {
                    System.out.println("if...lockA");
                    synchronized (LockB.lockB) {
                        System.out.println("if...lockB");
                    }
                }
            } else {
                synchronized (LockB.lockB) {
                    System.out.println("else...lockB");
                    synchronized (LockA.lockA) {
                        System.out.println("else...lockA");
                    }
                }
            }
            i++;
        }

    }
}
