package com.atu.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:46
 * @Description:
 */
@Configuration
public class ExecutorPool {

    @Autowired
    private ExecutorsConfig executorsConfig;

    @Bean
    public ThreadPoolTaskExecutor eventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorsConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorsConfig.getMaxPoolSize());
        executor.setKeepAliveSeconds(executorsConfig.getKeepAliveSeconds());
        executor.setQueueCapacity(executorsConfig.getQueueCapacity());
        executor.setThreadNamePrefix(executorsConfig.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}