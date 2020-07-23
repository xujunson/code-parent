package com.atu.capital.config;

import com.alibaba.dubbo.remoting.TimeoutException;
import com.google.common.collect.Sets;
import com.zaxxer.hikari.HikariDataSource;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Tom
 * @date: 2020-07-21 17:46
 * @description:
 */
public class TccConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DefaultRecoverConfig defaultRecoverConfig() {
        DefaultRecoverConfig defaultRecoverConfig = new DefaultRecoverConfig();
        defaultRecoverConfig.setMaxRetryCount(30);   //最大重试次数
        defaultRecoverConfig.setRecoverDuration(30); //恢复持续时间
        defaultRecoverConfig.setCronExpression("0/30 * * * * ?"); //每30秒检查一次是否需要恢复(检查对应的日志表有无需要恢复的数据)//每30秒检查一次是否需要恢复
        defaultRecoverConfig.setDelayCancelExceptions(Sets.newHashSet(TimeoutException.class));
        return defaultRecoverConfig;
    }

    @Bean("transactionRepository")
    public SpringJdbcTransactionRepository springJdbcTransactionRepository() {
        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();

        springJdbcTransactionRepository.setDomain("CONSUMER");    //domain
        springJdbcTransactionRepository.setTbSuffix("_CONSUMER"); //配置tcc日志表名称后缀：这里为：tcc_transaction_consumer

        //tcc所需分布式事务日志数据源（也可以使用其他数据源框架）
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        springJdbcTransactionRepository.setDataSource(hikariDataSource);

        return springJdbcTransactionRepository;
    }

}

