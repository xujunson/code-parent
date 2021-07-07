package com.atu.demo;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @Author: Tom
 * @Date: 2021/7/7 2:35 下午
 * @Description:
 */
@RestController
public class MonoController {

    @RequestMapping("/optional")
    public Optional<String> hello(@RequestParam(required = false) String str) {
        if (StringUtils.isEmpty(str)) {
            return Optional.empty();
        }
        return Optional.of("optional hello");
    }

    @RequestMapping("/mono")
    public Mono<String> mono(@RequestParam(required = false) String str) {
        if (StringUtils.isEmpty(str)) {
            return Mono.empty();
        }
        return Mono.just("mono");
    }
}