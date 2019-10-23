package com.atu.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author ：mark
 * @date ：Created in 2019/10/23 14:17
 * @description：队列配置
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue atuQueue() {
        return new Queue("atu");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }
}
