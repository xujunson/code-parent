package com.atu.config.event.listener;

import com.atu.config.event.MessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:03
 * @Description:
 */
@Component
public class MessageAsyncListener2 implements ApplicationListener<MessageEvent> {

    @Async //方法异步注解
    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        System.out.println("用户注册成功2，执行监听事件" + messageEvent.getSource() + new Date());
        System.out.println("当前线程名称:" + Thread.currentThread().getName());
    }
}
