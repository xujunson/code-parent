package com.atu.design.pattern.observer;

/**
 * @Author: Tom
 * @Date: 2022/7/8 17:51
 * @Description:
 */
public class ObserverOneImpl implements Observer {
    @Override
    public void update(String msg) {
        System.out.println("ObserverOne is notified,"+msg);
    }
}

