package com.atu.sender.kafka;

import com.alibaba.fastjson.JSON;

import com.atu.dto.MySqlRowData;
import com.atu.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 将消息通过kafka，作为一个producer发送到kafka的topic里面。
 */
@Slf4j
@Component
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {

        log.info("ad binlog kafka send rowData......");
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }
}
