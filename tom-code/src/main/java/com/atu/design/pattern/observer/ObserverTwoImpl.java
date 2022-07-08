package com.atu.design.pattern.observer;

/**
 * @Author: Tom
 * @Date: 2022/7/8 17:52
 * @Description:
 */

public class ObserverTwoImpl implements Observer {

    @Override
    public void update(String msg) {
        System.out.println("ObserverTwo is notified,"+msg);
    }
}
