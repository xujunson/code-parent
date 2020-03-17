package com.atu.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Tom
 * @date: 2020-03-17 9:58
 * @description:
 */
public class CinemaReadWrite {
    private static ReentrantReadWriteLock reentrantReadWriteLock
            = new ReentrantReadWriteLock();

    //生成一个读锁
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

    //生成一个写锁
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> read(),"Thread1").start();
        new Thread(() -> read(),"Thread2").start();
        new Thread(() -> write(),"Thread3").start();
        new Thread(() -> write(),"Thread4").start();
    }

}
