package com.atu.design.pattern.observer;


/**
 * @Author: Tom
 * @Date: 2022/7/8 17:46
 * @Description:
 */
public interface Subject {

    /**
     * 添加观察者
     * @param observer
     */
    void addServer(Observer observer);

    /**
     * 移除观察者
     * @param observer
     */
    void removeServer(Observer observer);

    /**
     * 通知观察者
     * @param msg
     */
    void notifyAllObservers(String msg);

}