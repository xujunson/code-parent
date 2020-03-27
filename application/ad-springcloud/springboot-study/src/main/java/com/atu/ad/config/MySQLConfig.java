package com.atu.ad.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ConfigurationProperties
 * SpringBoot 的一个很重要的功能是外部化配置，可以直接访问配置文件（application.yml）中定义的字段值，并能够完成属性绑定。
 * 但是，需要注意：@ConfigurationProperties 并没有把当前类注册成为一个 Spring 的 Bean。所以，我们在使用时都会配合 @Component 注解直接进行注入。
 */

/**
 * 注意到，ConfigurationProperties 的 prefix 属性指定了 adconf.mysql。
 * 所以，它会映射到 application.yml 中对应的字段
 */
@Data
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
public class MySQLConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
}
