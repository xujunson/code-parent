package com.atu;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author: Tom
 * @date: 2020-04-01 14:52
 * @description: 消费消费
 */
public class MyConsumer {
    private static KafkaConsumer<String, String> consumer;
    private static Properties properties;

    static {
        properties = new Properties();
        //指定kafka集群地址
        properties.put("bootstrap.servers", "127.0.0.1:9092");

        //key反序列化
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        //value反序列化
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        //消费者组
        properties.put("group.id", "KafkaStudy");
    }

    /**
     * 自动提交位移
     */
    private static void generalConsumeMessageAutoCommit() {
        //允许自动提交位移：让consumer自动管理位移，应用本身不需要显示操作
        //当设置为true时，消费者会在poll调用后，每隔5s提交一次位移。
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<String, String>(properties);

        //消费atu-kafka-study-x
        consumer.subscribe(Collections.singleton("atu-kafka-study-x"));

        //拉取循环，使用无限循环去处理数据
        try {
            while (true) {
                boolean flag = true;
                //poll——拉取数据，如果停止拉取kafka认为这个消费者已经死亡，并且会进行重平衡。
                //100——超时时间 指明线程如果没有数据时会等待多长时间
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, value = %s",
                            record.topic(), record.partition(),
                            record.key(), record.value()
                    ));
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                //主动关闭
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手动同步提交位移
     */
    private static void generalConsumeMessageSyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singletonList("atu-kafka-study-x"));

        //死循环 监听kafka producer产生消息
        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            try {
                //同步提交位移
                consumer.commitSync(); //调用此方法 当前线程会阻塞
            } catch (CommitFailedException ex) {
                System.out.println("commit failed error: "
                        + ex.getMessage());
            }

            if (!flag) {
                break;
            }
        }
    }

    /**
     * 异步提交
     */
    private static void generalConsumeMessageAsyncCommit() {

        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("atu-kafka-study-x"));

        while (true) {
            boolean flag = true;

            ConsumerRecords<String, String> records =
                    consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            // commit A, offset 2000 失败
            // commit B, offset 3000 成功
            //如果存在重试 A重新把位移设为2000 可能会导致重复消费
            //优点 提交快，缺点：如果服务器返回失败，异步提交不会进行重试；而同步提交会重试
            consumer.commitAsync(); //不会阻塞

            if (!flag) {
                break;
            }
        }
    }

    /**
     * 异步提交消息位移 带回调函数记录失败情况
     */
    private static void generalConsumeMessageAsyncCommitWithCallback() {

        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("atu-kafka-study-x"));

        while (true) {
            boolean flag = true;

            ConsumerRecords<String, String> records =
                    consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            //位移提交 回调
            /*consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                    if (e != null) {
                        System.out.println("commit failed for offsets: " +
                                e.getMessage());
                    }
                }
            });*/
            //java8 函数式编程
            //此处只是捕获了异常并没有进行重试，如果想进行重试，同时又保证提交顺序的话可以使用 单调递增序号
            //每次发起异步提交时，增加这个序号，并且将这个序号作为参数传递给回调方法。当消息提交失败回调时
            //检查参数中的序号值，与全局的序号值。如果相等可以重试提交否则就可以放弃。因为已经有更新的位移提交了。
            //大部分情况下不用关注位移这些。
            consumer.commitAsync((map, e) -> {
                if (e != null) {
                    System.out.println("commit failed for offsets: " +
                            e.getMessage());
                }
            });

            if (!flag) {
                break;
            }
        }
    }

    /**
     * 混合异步提交与同步提交——大部分采用
     */
    private static void mixSyncAndAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("atu-kafka-study-x"));

        try {
            while (true) {
                ConsumerRecords<String, String> records =
                        consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, " +
                                    "value = %s",
                            record.topic(), record.partition(),
                            record.key(), record.value()
                    ));
                }
                //异步
                consumer.commitAsync();
            }
        } catch (Exception ex) {
            System.out.println("commit async error: " + ex.getMessage());
        } finally {
            try {
                //同步提交保证位移提交成功
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        generalConsumeMessageAutoCommit();
    }

}
