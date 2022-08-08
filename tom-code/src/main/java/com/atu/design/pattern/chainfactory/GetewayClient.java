package com.atu.design.pattern.chainfactory;

import com.atu.design.pattern.chainfactory.handler.GatewayHandler;

/**
 * @Author: Tom
 * @Date: 2022/8/8 16:16
 * @Description: 责任链模式
 * @herf https://mp.weixin.qq.com/s/ueIrPMalK81sF3sL3WTWTQ
 */
public class GetewayClient {
    public static void main(String[] args) {
        GatewayHandler firstGetewayHandler = GatewayHandlerEnumFactory.getFirstGatewayHandler();
        firstGetewayHandler.handler();
    }
}
