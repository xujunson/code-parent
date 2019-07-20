package com.atu.senior.Thread.deadlock;

/**
 * @Author: xsy
 * @Date: 2019/3/13 9:18
 * @Description:
 */
public class LockB {
    private LockB() {
    }

    public static final LockB lockB = new LockB();
}
