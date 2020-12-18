package com.atu.mq.receive;

import com.atu.mq.MQMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author: Tom
 * @date: 2020-12-17 13:59
 * @description: 消息接收端
 */
@Slf4j
@Component
@EnableBinding(MQMessageChannel.class)
public class ReceiveMessageService {

    @StreamListener(MQMessageChannel.ATU_RECEIVE_MESSAGE)
    public void receiveMessage(Message<String> message) {
        log.info("接受到消息队列分发的消息对象：{}", message);

    }
}
