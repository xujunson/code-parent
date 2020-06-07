package com.atu.springtx.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 *
 */
@Configuration
public class JmsConfiguration {

    /**
     * 设置JmsTemplate
     *
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsTemplate initJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        //不设置的true 话有时会发送的消息不是在事务中进行的，并且session会自动关闭
        template.setSessionTransacted(true);
        return template;
    }

    /**
     * 配置 connectionFactory,
     * 使用Spring提供的TransactionAwareConnectionFactoryProxy
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.16.124:61617");
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

}
