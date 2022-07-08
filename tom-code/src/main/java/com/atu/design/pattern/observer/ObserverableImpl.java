package com.atu.design.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2022/7/8 17:49
 * @Description:
 */
public class ObserverableImpl implements Subject {

    /**
     * 存储被观察者
     */
    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void addServer(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeServer(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String msg) {
        for (Observer observer : observers) {
            observer.update(msg);
        }
    }
}
