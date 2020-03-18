package com.atu.cas;

/**
 * @author: Tom
 * @date: 2020-03-18 15:29
 * @description: 模拟CAS操作，等价代码
 */
public class SimulatedCAS {
    private volatile int value;

    //比较和交换
    public synchronized int compareAndSwap(int expectedValue, int newValue) {

        int oldValue = value;
        if(oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
}
