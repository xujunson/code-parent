package com.atu.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:31
 * @description: 静态内部类方法，可用
 */
public class Singleton7 {

    private Singleton7() {
    }

    //懒汉
    //JVM 类加载的性质保证了 即便多个线程同时访问这个对象，也不会建造多个实例
    private static class SingletonInstance {
        private static final Singleton7 INSTANCE = new Singleton7();

    }
    public static Singleton7 getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
