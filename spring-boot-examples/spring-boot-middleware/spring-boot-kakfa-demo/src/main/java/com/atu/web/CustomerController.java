package com.atu.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2021/7/27 11:36 上午
 * @Description: 消费者消费消息
 */
@Slf4j
@Component
public class CustomerController {

    /**
     * topics = "demo" 要消费的topic名称
     *
     * @param record
     */
    @KafkaListener(topics = "demo")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("topic是: {}, offset是: {}, value是: {}", record.topic(), record.offset(), record.value());
    }
}


