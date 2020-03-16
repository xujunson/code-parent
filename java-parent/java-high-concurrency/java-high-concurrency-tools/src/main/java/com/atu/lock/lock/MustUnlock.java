package com.atu.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tom
 * @date: 2020-03-16 16:48
 * @description: Lock不会像synchronized一样，异常的时候自动释放锁
 * 所以最佳实践是在 finally中释放锁，以便保证发生那个异常的时候锁一定被释放
 */
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock(); //加锁
        try {
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName()+"开始执行任务");
        } finally {
            lock.unlock();
        }
    }
}
