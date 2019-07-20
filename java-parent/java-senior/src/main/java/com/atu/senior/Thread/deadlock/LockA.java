package com.atu.senior.Thread.deadlock;

/**
 * @Author: xsy
 * @Date: 2019/3/13 9:18
 * @Description:
 */
public class LockA {
    private LockA() {
    }

    public static final LockA lockA = new LockA();
}
