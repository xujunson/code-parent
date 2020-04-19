package com.atu.service;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

/**
 * @author: Tom
 * @date: 2020-04-18 21:01
 * @description: 配置jms
 */
@EnableJms
@Configuration
public class JmsConfig {
    /**
     * 使用jpa的话，TransactionManager是不需要手动创建的
     * 对于jms的transactionManager需要手动创建事务管理器
     *
     * @return
     */
    @Bean
    PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);

        return jmsTemplate;
    }

    @Bean
    JmsListenerContainerFactory<?> msgFactory(ConnectionFactory connectionFactory,
                                              DefaultJmsListenerContainerFactoryConfigurer configurer,
                                              PlatformTransactionManager transactionManager) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setTransactionManager(transactionManager);
        //缓存，因为是共用 防止一个方法关闭后，另一个 出问题
        factory.setCacheLevelName("CACHE_CONNECTION");
        factory.setReceiveTimeout(10000L);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
