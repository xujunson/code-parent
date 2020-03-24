package com.atu.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author: Tom
 * @date: 2020-03-24 11:54
 * @description: 自己用AQS实现一个简单的线程协作器
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    //获取
    public void await() {
        sync.acquireShared(0);
    }

    //释放
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
                System.out.println(Thread.currentThread().getName()+"尝试获取latch，获取失败那就等待");
                oneShotLatch.await();
                System.out.println("开闸放行"+Thread.currentThread().getName()+"继续运行");
            }
        }).start();
    }

}
