package com.atu;

import com.atu.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class SpringCloudZuulSeinorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulSeinorApplication.class, args);
    }
    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }
}
