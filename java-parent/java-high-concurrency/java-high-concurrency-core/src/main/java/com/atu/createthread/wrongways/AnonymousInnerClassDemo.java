package com.atu.createthread.wrongways;

/**
 * @author: Tom
 * @date: 2020-03-09 10:44
 * @description: 匿名内部类实现
 */
public class AnonymousInnerClassDemo {
    public static void main(String[] args) {
        //第一种匿名内部类
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

        //第二种匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
