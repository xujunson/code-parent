package com.atu;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @author: Tom
 * @date: 2020-04-01 14:52
 * @description: 消息发送
 */
public class MyProducer {
    private static KafkaProducer<String, String> producer;

    static {
        //初始化producer
        Properties properties = new Properties();
        //地址，9092——kafka启动默认端口
        properties.put("bootstrap.servers", "127.0.0.1:9092");

        //序列化
        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("partitioner.class", "com.atu.CustomPartitioner");

        producer = new KafkaProducer<>(properties);
    }
    //kafka有三种发送消息方式

    /**
     * 1、只发送，不管返回值
     */
    private static void sendMessageForgetResult() {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "atu-kafka-study", "name", "ForgetResult"
        );
        producer.send(record);
        producer.close();
    }

    /**
     * 2、同步发送代码
     */
    private static void sendMessageSync() throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "atu-kafka-study", "name", "sync"
        );
        RecordMetadata result = producer.send(record).get();
        //基本信息
        System.out.println(result.topic());
        //分区
        System.out.println(result.partition());
        //偏移
        System.out.println(result.offset());

        producer.close();
    }


    private static class MyProducerCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            //打印消息
            System.out.println(recordMetadata.topic());
            //分区
            System.out.println(recordMetadata.partition());
            //偏移
            System.out.println(recordMetadata.offset());
            System.out.println("Coming in MyProducerCallback");
        }
    }

    /**
     * 3、异步发送 等待回调
     */
    private static void sendMessageCallback() {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "atu-kafka-study-x", "name", "callback"
        );
        //回调类——MyProducerCallback
        producer.send(record, new MyProducerCallback());

        record = new ProducerRecord<String, String>(
                "atu-kafka-study-x", "name-x", "callback"
        );
        producer.send(record, new MyProducerCallback());

        record = new ProducerRecord<String, String>(
                "atu-kafka-study-x", "name-y", "callback"
        );
        producer.send(record, new MyProducerCallback());

        record = new ProducerRecord<String, String>(
                "atu-kafka-study-x", "name-z", "callback"
        );
        producer.send(record, new MyProducerCallback());

        producer.close();
    }

    public static void main(String[] args) throws Exception {
        //sendMessageForgetResult();
        //sendMessageSync();
        sendMessageCallback();
    }
}
