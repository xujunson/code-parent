package com.atu.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * @author: Tom
 * @date: 2020-03-10 21:29
 * @description: 演示join期间被中断的效果
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainThread.interrupt();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("子线程中断");
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        System.out.println("等待子线程运行完毕");

        try {
            thread1.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "主线程中断了");
            thread1.interrupt();
            e.printStackTrace();
        }

        System.out.println("子线程已经运行完毕");
    }
}
