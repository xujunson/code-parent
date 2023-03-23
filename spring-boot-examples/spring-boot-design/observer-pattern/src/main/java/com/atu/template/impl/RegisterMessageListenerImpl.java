package com.atu.template.impl;

import com.atu.template.config.event.RegisterMessageEvent;
import com.atu.template.config.listener.IEventListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:24
 * @Description:
 */
@Service
public class RegisterMessageListenerImpl implements IEventListener<RegisterMessageEvent> {

    @Override
    public void handler(RegisterMessageEvent event) {
        System.out.println("用户注册成功register，执行监听事件" + event.getSource() + new Date());
    }
}
