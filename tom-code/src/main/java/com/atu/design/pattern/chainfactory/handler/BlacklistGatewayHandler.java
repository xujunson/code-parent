package com.atu.design.pattern.chainfactory.handler;

/**
 * @Author: Tom
 * @Date: 2022/8/8 16:15
 * @Description:
 */
public class BlacklistGatewayHandler extends GatewayHandler{
    @Override
    public int handler() {
        return next.handler();
    }
}
