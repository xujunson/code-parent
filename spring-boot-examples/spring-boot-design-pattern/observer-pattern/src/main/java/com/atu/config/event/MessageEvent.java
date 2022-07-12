package com.atu.config.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: Tom
 * @Date: 2022/7/12 11:23
 * @Description:
 */
public class MessageEvent extends ApplicationEvent {

    public MessageEvent(Object source) {
        super(source);
    }
}
