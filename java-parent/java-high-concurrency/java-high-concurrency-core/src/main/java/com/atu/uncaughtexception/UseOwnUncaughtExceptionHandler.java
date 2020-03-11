package com.atu.uncaughtexception;

/**
 * @author: Tom
 * @date: 2020-03-11 11:22
 * @description: 使用刚才自己写的 MyUncaughtExceptionHandler
 */
public class UseOwnUncaughtExceptionHandler implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("捕获器1"));
        new Thread(new UseOwnUncaughtExceptionHandler(), "MyThread-1").start();
        Thread.sleep(300);
        new Thread(new UseOwnUncaughtExceptionHandler(), "MyThread-2").start();
        Thread.sleep(300);
        new Thread(new UseOwnUncaughtExceptionHandler(), "MyThread-3").start();
        Thread.sleep(300);
        new Thread(new UseOwnUncaughtExceptionHandler(), "MyThread-4").start();
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
