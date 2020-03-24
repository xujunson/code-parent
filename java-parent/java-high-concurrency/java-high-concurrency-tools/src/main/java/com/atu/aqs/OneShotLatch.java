package com.atu.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author: Tom
 * @date: 2020-03-24 11:54
 * @description: 自己用AQS实现一个简单的线程协作器
 * 起初门闩是关闭的，那么会有很多线程调用await方法，因为门闩是关闭的，谁调用谁就陷入等待状态；
 * 直到后续有一个线程调用signal方法——相当于把门闩打开，此时之前所有的线程都会被释放；
 * 使用state表示门是不是被打开，0——关闭，1——打开
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    //共享锁
    //获取——含义和CountDownLatch一样
    public void await() {
        sync.acquireShared(0); //尝试获取锁
    }

    //释放——同countDown()
    public void signal() {
        sync.releaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            //state = 1代表被打开了，因为默认是0
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "尝试获取latch，获取失败那就等待");

                    oneShotLatch.await();
                    System.out.println("开闸放行" + Thread.currentThread().getName() + "继续运行");
                }
            }).start();
        }
        Thread.sleep(5000);
        oneShotLatch.signal(); //唤醒
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "尝试获取latch，获取失败那就等待");
                oneShotLatch.await();
                System.out.println("开闸放行" + Thread.currentThread().getName() + "继续运行");
            }
        }).start();
    }

}
