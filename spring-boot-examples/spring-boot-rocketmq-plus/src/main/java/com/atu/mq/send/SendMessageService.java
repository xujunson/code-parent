package com.atu.mq.send;

import com.atu.mq.MQMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-12-17 13:49
 * @description: 消息发送端服务
 */
@Slf4j
@Component
@EnableBinding(MQMessageChannel.class)
public class SendMessageService {
    @Resource(name = MQMessageChannel.ATU_SEND_MESSAGE)
    private MessageChannel messageChannel;

    public void sendMessage(String message) {
        log.info("消息队列即将发送消息:[{}]", message);

        messageChannel.send(MessageBuilder.withPayload(message).build());
    }
}
