package com.atu.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author: Tom
 * @date: 2020-03-13 16:55
 * @description: 用 ThreadMXBean 检测死锁
 */
public class ThreadMXBeanDetection implements Runnable {
    int flag = 1;

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection r1 = new ThreadMXBeanDetection();
        ThreadMXBeanDetection r2 = new ThreadMXBeanDetection();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        Thread.sleep(1000);

        //得到实例
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        //利用实例方法 获取陷入死锁的线程
        long[] deadLockedThreads = threadMXBean.findDeadlockedThreads();
        //判断是否发现线程
        if (deadLockedThreads != null && deadLockedThreads.length > 0) {

            for (int i = 0; i < deadLockedThreads.length; i++) {

                //获取线程信息
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadLockedThreads[i]);
                System.out.println("发现死锁：" + threadInfo.getThreadName());
            }
        }

    }

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("线程1成功拿到两把锁");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程2成功拿到两把锁");
                }
            }
        }
    }
}
