package com.atu.background;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2020-03-11 14:44
 * @description: 第一种：运行结果出错
 * 演示计数不准确（减少），找出具体出错的位置。
 */
public class MultiThreadsErrorFix1 implements Runnable {

    static MultiThreadsErrorFix1 instance = new MultiThreadsErrorFix1();
    int index = 0;
    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();

    //解决3 引入CyclicBarrier，让我们的线程根据我们的需要在某一个地方等待，直到等待的对象都就绪了，然后一起出发
    //参数2 意思是需要等待的有两个线程
    static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    final boolean[] marked = new boolean[10000000];

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上结果是" + instance.index);
        System.out.println("真正运行的次数" + realIndex.get());
        System.out.println("错误次数" + wrongCount.get());

    }

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0; i < 10000; i++) {
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            //假设两个线程都执行到此处，开始执行下面代码
            //线程1开始执行，执行 index++; realIndex.incrementAndGet();
            //准备执行 if之前，假设此时index=1;在准备把marked[1]标记为true之前，CPU切到线程2,
            //线程2又执行index++; 然后再切回到线程1是，marked[index],index就变成了2；
            /*index++;
            realIndex.incrementAndGet();
            synchronized (instance) {
                if (marked[index]) {
                    System.out.println("发生错误" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }*/

            //解决上述问题，再次加一个栅栏,两个线程同时index++

            //结果：
            //表面上结果是20000
            //真正运行的次数20000
            //错误次数10000

            //问题原因：
            //index计算两次，线程1设置完之后，线程2判断为发生错误，但是实际不是错误，是由于可见性的保证让两个线程的结果可以互相通知。

            /*index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (instance) {
                if (marked[index]) {
                    System.out.println("发生错误" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }*/

            // 调整 判断前一个是否为true 正常 0-2-4-6...
            index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (instance) {
                if (marked[index] && marked[index - 1]) {
                    System.out.println("发生错误" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }
}
