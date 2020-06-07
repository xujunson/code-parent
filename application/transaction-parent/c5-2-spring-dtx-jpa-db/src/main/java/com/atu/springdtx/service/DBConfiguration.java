package com.atu.springdtx.service;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    /**
     * db_user配置
     *
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.ds_user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource userDataSource() {
        return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    /**
     * 手动创建jpa的EntityManagerFactory
     *
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //不需要自动维护数据库
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.atu");
        factory.setDataSource(userDataSource());
        return factory;
    }

    /**
     * 分别创建两个数据库的 transactionManager
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager userTM = new JpaTransactionManager();
        userTM.setEntityManagerFactory(entityManagerFactory().getObject());
        PlatformTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource());

        //参数的位置对执行顺序是由影响的， 参数在前，提交在后
        ChainedTransactionManager tm = new ChainedTransactionManager(orderTM, userTM);
        return tm;
    }

    /**
     * db_order配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.ds_order")
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource orderDataSource() {
        return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource) {
        return new JdbcTemplate(orderDataSource);
    }
}
