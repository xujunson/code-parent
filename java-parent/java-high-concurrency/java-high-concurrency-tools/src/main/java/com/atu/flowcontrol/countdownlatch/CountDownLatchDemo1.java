package com.atu.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Tom
 * @date: 2020-03-23 20:11
 * @description: 工厂中有质检环节，为了保证质检的可信性，有5个工人都是检查的
 * 所有人都认为通过，才通过
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);//5——构造函数中需要等待的次数
        //CountDownLatch特点就是多个线程之间可以并行检测的——效率较高
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) Math.random() * 10000);
                        System.out.println("No." + no + "完成了检查。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown(); //减1
                    }
                }
            };
            service.submit(runnable);
        }
        System.out.println("等待5个人检查完.....");
        latch.await();
        System.out.println("所有人都完成了工作，进入下一个环节。");
    }
}
