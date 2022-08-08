package com.atu.design.pattern.chainfactory.handler;

/**
 * @Author: Tom
 * @Date: 2022/8/8 16:14
 * @Description:
 */
public class ApiLimitGatewayHandler extends GatewayHandler{
    @Override
    public int handler() {
        return next.handler();
    }
}
