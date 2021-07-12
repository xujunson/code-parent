package com.atu.demo;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

/**
 * @Author: Tom
 * @Date: 2021/7/6 1:38 下午
 * @Description:
 */
public class ReactorTests {

    @After
    public void after() {
        try {
            sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJust() {
        Flux.just("hello", "world")
                .subscribe(System.out::println);
    }

    @Test
    public void testList() {
        List<String> words = Arrays.asList(
                "hello",
                "reactive",
                "world"
        );

        Flux.fromIterable(words)
                .subscribe(System.out::println);
    }

    @Test
    public void testRange() {
        Flux.range(1, 10)
                .subscribe(System.out::println);
    }

    @Test
    public void testInterval() {
        /*Flux.interval(Duration.ofSeconds(1))
                .subscribe(System.out::println);*/

        List<Integer> list = new ArrayList<>();
        list.add(1);
        Stream<Integer> stream = list.stream()
                .map(a -> a = a + 2)
                .map(a -> a = a / 2).map(a->a*3);
        System.out.println(stream.findFirst().orElse(0));
    }


}
