package com.atu.design.pattern.chainfactory.handler;

/**
 * @Author: Tom
 * @Date: 2022/8/8 16:15
 * @Description:
 */
public class SessionGatewayHandler extends GatewayHandler {
    @Override
    public int handler() {
        if (next != null)
            return next.handler();

        return 0;
    }
}
