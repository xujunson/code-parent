package com.atu.createthread;

/**
 * @author: Tom
 * @date: 2020-03-08 22:21
 * @description: 用Runnable方式创建线程
 */
public class RunnableStyle implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("用Runnable方法实现线程");
    }
}
