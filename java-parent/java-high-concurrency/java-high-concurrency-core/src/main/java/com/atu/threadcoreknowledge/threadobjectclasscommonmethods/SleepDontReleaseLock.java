package com.atu.threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2020-03-10 20:58
 * @description: 演示sleep不释放lock（lock需要手动释放）
 */

public class SleepDontReleaseLock implements Runnable {

    private static final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock(); //加锁，如果不手动释放，其他人没办法获取
        System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(5000);
            System.out.println("线程" + Thread.currentThread().getName() + "已经苏醒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //手动释放
        }
    }

    public static void main(String[] args) {
        SleepDontReleaseLock sleepDontReleaseLock = new SleepDontReleaseLock();
        new Thread(sleepDontReleaseLock).start();
        new Thread(sleepDontReleaseLock).start();
    }
}

