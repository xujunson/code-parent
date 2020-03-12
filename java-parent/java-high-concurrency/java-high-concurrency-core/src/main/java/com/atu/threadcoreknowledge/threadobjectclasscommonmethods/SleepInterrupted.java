package com.atu.threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tom
 * @date: 2020-03-10 21:04
 * @description: 每隔1s 输出当前时间，被中断。
 * Thread.sleep();
 * TimeUnit.SECONDS.sleep();
 */
public class SleepInterrupted implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInterrupted());
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());
            try {

                //写法更优雅
                TimeUnit.HOURS.sleep(3);
                TimeUnit.MINUTES.sleep(25);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("我被中断了！");
                e.printStackTrace();
            }
        }
    }
}
