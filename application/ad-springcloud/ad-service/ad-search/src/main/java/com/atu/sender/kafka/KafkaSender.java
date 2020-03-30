package com.atu.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.atu.mysql.dto.MySqlRowData;
import com.atu.sender.ISender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: Tom
 * @date: 2020-03-30 20:26
 * @description: 投放kafka消息队列, 其他服务可以监听topic 然后获取增量数据
 */
@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("$adconf.kafka.topic")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(
                topic, JSON.toJSONString(rowData)
        );
    }

    /**
     * 监听
     * 指定不同的groupId，每个group都会接收到topic中的消息
     *
     * @param record
     */
    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            System.out.println("kafka processMysqlRowData: " +
                    JSON.toJSONString(rowData));
        }
    }
}

