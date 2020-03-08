package com.atu;

/**
 * @author: Tom
 * @date: 2020-03-08 15:43
 * @description: 可重入粒度测试：3、调用父类方法
 */
public class SynchronizedSuperClass12 {
    public synchronized void doSomething() {
        System.out.println("我是父类方法");
    }
}

class TestClass extends SynchronizedSuperClass12 {
    public synchronized void doSomething() {
        System.out.println("我是子类方法");
        super.doSomething();
    }

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.doSomething();
    }
}