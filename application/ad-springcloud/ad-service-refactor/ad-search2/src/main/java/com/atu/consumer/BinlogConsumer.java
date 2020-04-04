package com.atu.consumer;

import com.alibaba.fastjson.JSON;
import com.atu.dto.MySqlRowData;
import com.atu.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: Tom
 * @date: 2020-04-04 15:39
 * @description: kafka消费者——消费Binlog Message
 */
@Slf4j
@Component
public class BinlogConsumer {
    @Autowired
    private ISender sender;

    /**
     * kafka consumer
     *
     * @param record
     */
    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMySQLRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            //从kafka得到需要去消费的数据
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }
    }

}
