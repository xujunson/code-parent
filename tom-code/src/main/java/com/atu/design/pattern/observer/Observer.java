package com.atu.design.pattern.observer;

/**
 * @Author: Tom
 * @Date: 2022/7/8 17:48
 * @Description:
 */
/**
 * 观察者
 *
 */
public interface Observer {
    /**
     * 更新消息
     * @param msg
     */
    void update(String msg);
}
