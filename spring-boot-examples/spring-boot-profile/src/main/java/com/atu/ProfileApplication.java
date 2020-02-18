package com.atu;

import com.atu.config.ConfigBean;
import com.atu.config.ConfigTestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动原理
 * @Configuration : 表示Application作为spring配置文件存在
 * @EnableAutoConfiguration: 启动spring boot内置的自动配置
 * @ComponentScan : 扫描bean，路径为ProfileApplication类所在package以及package下的子路径，这里为 com.atu，在spring boot中bean都放置在该路径已经子路径下。
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class, ConfigTestBean.class})
public class ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

}
