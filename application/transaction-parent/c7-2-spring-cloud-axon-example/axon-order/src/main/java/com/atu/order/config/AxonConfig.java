package com.atu.order.config;

import com.atu.order.OrderManagementSaga;
import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executors;

/**
 * 分布式处理
 */
@Configuration
public class AxonConfig {

    /**
     * 注：在order服务里只会做订单command的处理，不会去做物化视图的处理，所以不需要监听自己的order
     */
    private static final Logger LOG = LoggerFactory.getLogger(AxonConfig.class);

    @Value("${axon.amqp.exchange}")
    private String exchangeName;

    /**
     * 超时配置
     *
     * @param eventBus
     * @param transactionManager
     * @return
     */
    @Bean
    public EventScheduler eventScheduler(EventBus eventBus, TransactionManager transactionManager) {
        return new SimpleEventScheduler(Executors.newScheduledThreadPool(1), eventBus, transactionManager);
    }

    /**
     * saga队列
     *
     * @return
     */
    @Bean
    public Queue sagaQueue() {
        return new Queue("saga", true);
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding sagaQueueBinding() {
        return BindingBuilder.bind(sagaQueue()).to(exchange()).with("#.event.saga.#").noargs();
    }

    /**
     * 监听saga队列 收到消息后做分发
     * Order Saga需要监听事件来处理订单流程，它需要监听ticket、user队列来监听saga流程里面相应的事件。
     * 他反而不需要监听order队列，因为order的事件都是本地处理的。所以我们不需要设置OrderEventProcessor
     */
    @Bean
    public SpringAMQPMessageSource sagaMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @RabbitListener(queues = {"saga"})
            @Override
            @Transactional
            public void onMessage(Message message, Channel channel) throws Exception {
                LOG.debug("Message received: {}", message);
                super.onMessage(message, channel);
            }
        };
    }

//    @Autowired
//    public void configure(EventHandlingConfiguration ehConfig, SpringAMQPMessageSource sagaMessageSource) {
//        // saga类上不能添加EventProcessor
//        ehConfig.registerSubscribingEventProcessor("SagaEventProcessor", c -> sagaMessageSource);
//    }

    /**
     * 给SagaConfiguration注册一个Saga的 manager
     * 指定saga的来源是sagaMessageSource-队列
     *
     * @param sagaMessageSource
     * @param txManager
     * @return
     */
    @Bean
    public SagaConfiguration<OrderManagementSaga> orderManagementSagaConfiguration(SpringAMQPMessageSource sagaMessageSource,
                                                                                   PlatformTransactionManager txManager) {
        return SagaConfiguration.subscribingSagaManager(OrderManagementSaga.class, c -> sagaMessageSource)
                .configureTransactionManager(c -> new SpringTransactionManager(txManager));
    }

}
