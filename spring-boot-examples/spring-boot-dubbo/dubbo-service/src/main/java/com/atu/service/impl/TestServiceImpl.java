package com.atu.service.impl;

import com.atu.service.ITestService;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author: Tom
 * @date: 2020-04-03 16:57
 * @description:
 */
@Service
public class TestServiceImpl implements ITestService {

    /**
     * 1.dubbo服务发布采用dubbo注解方式，使用 Dubbo 提供的 @Service 注解进行服务的发布
     * 2.Dubbo 提供@Service 将该接口注册到注册中心去
     * 3.Spring 提供@Service 将类注入到Spring容器中去
     */

    @Value("${dubbo.protocol.port}")
    private String dubboPort;

    @Override
    public String getUser() {

        System.out.println("订单服务调用会员服务，服务端口号：" + dubboPort);
        return "订单服务调用会员服务，服务端口号：" + dubboPort;
    }
}