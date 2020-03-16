package com.atu.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2020-03-16 20:42
 * @description:
 */
public class PessimismOptimismLock {
    int a;
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    //悲观锁实现，不同线程不能同时进入
    public synchronized void testMethod() {
        a++;
    }
}
