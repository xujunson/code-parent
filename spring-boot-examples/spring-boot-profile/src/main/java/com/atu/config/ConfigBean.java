package com.atu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ Author ：Tom
 * @ 2020/2/18 9:46
 * @ Description：
 */
//通过前缀去找application.properties中的文件
@ConfigurationProperties(prefix = "com.atu")
public class ConfigBean {
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
