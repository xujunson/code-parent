package com.atu.config.event.listener;

import com.atu.config.event.MessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2022/7/12 11:24
 * @Description:
 */
@Component
public class MessageListener implements ApplicationListener<MessageEvent> {
    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        System.out.println("用户注册成功，执行监听事件"+messageEvent.getSource());
    }
}