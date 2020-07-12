package com.atu.user.config;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Tom
 * @date: 2020-07-12 21:07
 * @description: 一个消息同时发送到两个队列user、 saga
 */
@Configuration
public class AxonConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AxonConfig.class);
    @Value("${axon.amqp.exchange}")
    private String exchangeName;

    /**
     * 生成 exchange对象
     *
     * @return
     */
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    /**
     * 创建user队列
     *
     * @return
     */
    @Bean
    public Queue userQueue() {
        return new Queue("user", true);
    }

    /**
     * Q和exchange绑定
     */
    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue()).to(exchange()).with("com.atu.user.event.#").noargs();
    }

    /**
     * 创建Sage Q
     *
     * @return
     */
    @Bean
    public Queue ticketSagaQueue() {
        return new Queue("saga", true);
    }

    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding ticketSagaQueueBinding() {
        return BindingBuilder.bind(ticketSagaQueue()).to(exchange()).with("com.atu.user.event.saga.#").noargs();
    }

    @Bean
    public SpringAMQPMessageSource userMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @RabbitListener(queues = "user")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                LOG.debug("Message received: {}", message);
                super.onMessage(message, channel);
            }
        };
    }

    /**
     * 设置EventProcessor去使用MessageSource
     *
     * @param ehConfig
     * @param userMessageSource
     * @param ticketMessageSource
     * @param orderMessageSource
     */
    @Autowired
    public void configure(EventHandlingConfiguration ehConfig, SpringAMQPMessageSource userMessageSource,
                          SpringAMQPMessageSource ticketMessageSource, SpringAMQPMessageSource orderMessageSource) {
        ehConfig.registerSubscribingEventProcessor("UserEventProcessor", c -> userMessageSource);
    }
}
