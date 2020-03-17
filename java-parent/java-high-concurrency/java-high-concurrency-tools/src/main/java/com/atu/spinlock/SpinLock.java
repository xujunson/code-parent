package com.atu.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: Tom
 * @date: 2020-03-17 15:11
 * @description: 自旋锁
 */
public class SpinLock {
    private AtomicReference<Thread> sign = new AtomicReference<>();

    //锁住
    public void lock() {
        //拿到当前线程的引用
        Thread current = Thread.currentThread();

        //死循环，直到原子引用被赋为当前线程才会停止
        while (!sign.compareAndSet(null, current)) { //加锁
            System.out.println("自旋获取失败，再次尝试");
        }
    }

    //解锁
    public void unlock() {
        Thread current = Thread.currentThread();
        //把当前线程置为null就代表没有任何人持有这把锁——解锁
        sign.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
                spinLock.lock();
                System.out.println(Thread.currentThread().getName() + "获取到了自旋锁");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    spinLock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放了自旋锁");

                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
