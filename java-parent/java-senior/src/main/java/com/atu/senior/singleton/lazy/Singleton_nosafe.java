package com.atu.senior.singleton.lazy;

/**
 * @author ：mark
 * @date ：Created in 2019/11/18 14:46
 * @description：懒汉模式---线程不安全
 */
public class Singleton_nosafe {
    private static Singleton_nosafe instance;
    private Singleton_nosafe() {

    }
    public static Singleton_nosafe getInstance() {
        if (instance == null) {
            instance = new Singleton_nosafe();
        }
        return instance;
    }
}
