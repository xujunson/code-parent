package com.atu.senior.singleton.lazy;

/**
 * @author ：mark
 * @date ：Created in 2019/11/18 15:01
 * @description：${description}
 */
public class Singleton_safe {
    private static Singleton_safe instance;

    private Singleton_safe() {

    }

    public synchronized static Singleton_safe getInstance() {
        if (instance == null) {
            instance = new Singleton_safe();
        }
        return instance;
    }
}
