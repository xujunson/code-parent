package com.atu.senior.Thread.deadlock;

/**
 * @Author: xsy
 * @Date: 2019/3/13 9:20
 * @Description:
 */
public class DeadLockTest {
    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();

        Thread t1 = new Thread(deadLock);
        Thread t2 = new Thread(deadLock);
        t1.start();
        t2.start();

    }
}
