package com.atu.mq.send;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Author: Tom
 * @Date: 2021/10/20 11:07 上午
 * @Description:
 */
@Component
public class ProducerSchedule {

    private DefaultMQProducer producer;

    @Value("${rocketmq.producer.producer-group}")
    private String producerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String nameSrvAddr;

    public ProducerSchedule() {

    }

    /**
     * 生产者构造
     *
     * @PostConstruct该注解被用来修饰一个非静态的void（）方法 Bean初始化的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void defaultMQProducer() {
        if (Objects.isNull(this.producer)) {
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.nameSrvAddr);
        }

        try {
            this.producer.start();
            System.out.println("Producer start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息发布
     *
     * @param topic
     * @param messageText
     * @return
     */
    public String send(String topic, String messageText) {
        Message message = new Message(topic, messageText.getBytes());

        /**
         * 延迟消息级别设置
         * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
         */
        message.setDelayTimeLevel(4);

        SendResult result = null;
        try {
            result = this.producer.send(message);
            System.out.println("返回信息：" + JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.getMsgId();
    }
}
