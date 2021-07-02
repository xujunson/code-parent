package com.atu.router;

import com.atu.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author: Tom
 * @Date: 2021/7/2 10:59 上午
 * @Description:
 */
@Configuration
public class HelloWebFlux {
    @Bean
    public RouterFunction<ServerResponse> helloWorld(HelloHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        handler::helloWorld);
    }
}
