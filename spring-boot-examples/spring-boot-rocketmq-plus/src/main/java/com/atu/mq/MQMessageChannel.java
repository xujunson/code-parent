package com.atu.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author: Tom
 * @date: 2020-12-17 11:11
 * @description: 消息管道
 */
public interface MQMessageChannel {
    String ATU_RECEIVE_MESSAGE = "atu_receive_message";
    String ATU_SEND_MESSAGE = "atu_send_message";

    /**
     * 发送消息通道
     *
     * @return
     */
    @Output(ATU_SEND_MESSAGE)
    MessageChannel sendMessage();

    /**
     * 接收消息通道
     *
     * @return
     */
    @Input(ATU_RECEIVE_MESSAGE)
    MessageChannel receiveMessage();
}
