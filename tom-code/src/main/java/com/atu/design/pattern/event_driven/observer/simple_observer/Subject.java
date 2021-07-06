package com.atu.design.pattern.event_driven.observer.simple_observer;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:26 下午
 * @Description: 被观察者
 */
public interface Subject {
    /**
     * 添加观察者
     *
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 删除观察者
     *
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    void notifyObservers();
}