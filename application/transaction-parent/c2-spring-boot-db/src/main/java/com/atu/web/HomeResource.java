package com.atu.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeResource {

    @RequestMapping("/hello")
    @Secured("ROLE_USER")
    public String hello() {
        return "world";
    }
}
