package com.atu.mq.send;

import com.atu.mq.MQMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        //消息延迟 1-1s 2-5s 3-10s 4-30s
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "tagStr");
        headers.put(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4);
        Message msg = MessageBuilder.createMessage(message, new MessageHeaders(headers));
        messageChannel.send(msg);
    }
}
