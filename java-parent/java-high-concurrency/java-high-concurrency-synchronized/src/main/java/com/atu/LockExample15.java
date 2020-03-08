package com.atu;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2020-03-08 17:26
 * @description: 展示Lock的方法
 */
public class LockExample15 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock(); //加锁

        lock.unlock(); //解锁
        lock.tryLock(); //尝试获得这把锁
        //lock.tryLock(100, TimeUnit.SECONDS); //根据时间

    }
}
