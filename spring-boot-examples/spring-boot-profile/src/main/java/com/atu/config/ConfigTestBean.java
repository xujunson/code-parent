package com.atu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ Author ：Tom
 * @ 2020/2/18 10:05
 * @ Description：
 */
@Configuration
@ConfigurationProperties(prefix = "com.zy")
@PropertySource("classpath:test.properties")
public class ConfigTestBean {
    private String name;
    private String want;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWant() {
        return want;
    }

    public void setWant(String want) {
        this.want = want;
    }
    // 省略getter和setter
}
