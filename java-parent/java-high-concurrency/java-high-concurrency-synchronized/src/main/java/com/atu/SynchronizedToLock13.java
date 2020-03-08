package com.atu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2020-03-08 16:12
 * @description:
 */
public class SynchronizedToLock13 {
    Lock lock = new ReentrantLock();

    public synchronized void method1() {
        System.out.println("我是Synchronized形式的锁");
    }

    public void method2() {
        lock.lock(); //加锁
        try {
            System.out.println("我是lock形式的锁");
        } finally {
            lock.unlock(); //释放锁
        }
    }
    //method1与method2方法等价
    public static void main(String[] args) {
        SynchronizedToLock13 s = new SynchronizedToLock13();
        s.method1();
        s.method2();
    }
}
