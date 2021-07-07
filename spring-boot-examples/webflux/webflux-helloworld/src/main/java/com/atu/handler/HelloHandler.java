package com.atu.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * @Author: Tom
 * @Date: 2021/7/2 11:00 上午
 * @Description:
 */
@Component
public class HelloHandler {
    public Mono<ServerResponse> helloWorld(ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello World!"));
    }

    public static void main(String[] args) {
        Flux.just("Hello", "World").subscribe(System.out::println);

        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);

        Flux.empty().subscribe(System.out::println);

        Flux.range(1, 10).subscribe(System.out::println);

        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);


        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);

        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);

        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);

    }
}
