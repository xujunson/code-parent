package com.atu.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2020-03-16 21:28
 * @description:
 */
public class RecursionDemo {
    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();

        try {
            System.out.println("已经对资源进行了处理");
            if (lock.getHoldCount() < 5) {
                System.out.println(lock.getHoldCount());
                accessResource(); //递归调用
                System.out.println(lock.getHoldCount());

            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}

