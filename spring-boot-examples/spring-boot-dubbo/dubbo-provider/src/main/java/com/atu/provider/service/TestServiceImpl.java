package com.atu.provider.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @Author: Tom
 * @Date: 2022/3/2 14:36
 * @Description:
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String hello() {
        return "hello";
    }
}
