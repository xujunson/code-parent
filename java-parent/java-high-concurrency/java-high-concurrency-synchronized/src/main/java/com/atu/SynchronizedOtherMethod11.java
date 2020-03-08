package com.atu;

/**
 * @author: Tom
 * @date: 2020-03-08 15:40
 * @description: 可重入粒度测试：2、递归调用本方法
 */
public class SynchronizedOtherMethod11 {
    public static void main(String[] args) {
        SynchronizedOtherMethod11 synchronizedOtherMethod11 = new SynchronizedOtherMethod11();
        synchronizedOtherMethod11.method1();
    }

    public synchronized void method1() {
        System.out.println("我是method1");
        method2();
    }

    private synchronized void method2() {
        System.out.println("我是method2");
    }
}
