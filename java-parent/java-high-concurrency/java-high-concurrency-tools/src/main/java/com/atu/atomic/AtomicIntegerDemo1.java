package com.atu.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2020-03-18 10:32
 * @description: 演示AtomicInteger的基本用法，躲避
 */
public class AtomicIntegerDemo1 implements Runnable {
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    private void incrementAtomic() {
        //如果我们仅仅是对某一个数值需要保证线程安全的话，使用atomicInteger是非常合适的
        atomicInteger.getAndIncrement();
    }

    private void decrementAtomic() {
        atomicInteger.getAndDecrement();
    }

    private void getAndAdd() {
        atomicInteger.getAndAdd(10);
    }

    private static volatile int basicCount = 0;

    private synchronized void incrementBasic() {
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 r = new AtomicIntegerDemo1();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("普通变量的结果：" + basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            //incrementAtomic();
            //decrementAtomic();
            getAndAdd();
            incrementBasic();
        }
    }
}
