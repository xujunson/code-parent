package com.tom.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-03 14:56
 * @description: rabbitMq配置类
 */
@Configuration
public class MQConfig {
    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";

    public static final String HEADERS_QUEUE = "headers.queue";

    /**
     * Direct模式 交换机Exchange
     *
     * @return
     */
    /*@Bean
    public Queue queue() {
        //1、队列名称，2、持久化
        return new Queue(QUEUE, true);
    }*/

    /**
     * Topic模式 交换机Exchange
     *
     * @return
     */
    /*@Bean
    public Queue topicQueue1() {
        //1、队列名称，2、持久化
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        //1、队列名称，2、持久化
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }
*/
    /**
     * Fanout模式-广播模式 交换机Exchange
     *
     * @return
     */
    /*@Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }*/

    /**
     * Header模式 交换机Exchange
     *
     * @return
     */
    /*@Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headersQueue() {
        //1、队列名称，2、持久化
        return new Queue(HEADERS_QUEUE, true);
    }

    @Bean
    public Binding headersBinding() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }*/

    @Bean
    public Queue miaoshaQueue() {
        return new Queue("miaosha.queue", true);
    }
}
