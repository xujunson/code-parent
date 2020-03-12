package com.atu.threadcoreknowledge.uncaughtexception;

/**
 * @author: Tom
 * @date: 2020-03-11 10:05
 * @description: 1、不加try catch 抛出4个异常，都带线程名字
 * 2、加了try catch 期望捕获到第一个线程的异常，线程 2 3 4不应该运行，希望看到打印出Caught Exception
 * 3、执行发现，根本没有Caught Exception，线程2 3 4依然运行并抛出异常
 * 说明线程的异常不能用传统方法捕获
 */
public class CantCatchDirectly implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        try {
            new Thread(new CantCatchDirectly(), "MyThread-1").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "MyThread-2").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "MyThread-3").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "MyThread-4").start();

        } catch (RuntimeException e) {
            System.out.println("Caught Exception.");
        }
    }

    @Override
    public void run() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception.");
        }

    }
}
