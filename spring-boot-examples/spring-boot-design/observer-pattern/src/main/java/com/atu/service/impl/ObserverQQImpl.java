package com.atu.service.impl;

import com.atu.service.RegisterObserver;
import org.springframework.stereotype.Service;

/**
 * @Author: Tom
 * @Date: 2022/7/8 18:26
 * @Description:
 */
@Service
public class ObserverQQImpl implements RegisterObserver {
    @Override
    public void sendMsg(String msg) {
        System.out.println("发送QQ消息"+msg);
    }
}
