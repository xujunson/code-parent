package com.atu.design.pattern.chainfactory.handler;

/**
 * @Author: Tom
 * @Date: 2022/8/8 15:50
 * @Description:
 */
public abstract class GatewayHandler {

    /**
     * 下一关用当前抽象类来接收
     */
    protected GatewayHandler next;

    public void setNext(GatewayHandler next) {
        this.next = next;
    }

    public abstract int handler();
}