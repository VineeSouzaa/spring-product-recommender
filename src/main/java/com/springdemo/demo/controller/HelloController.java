package com.springdemo.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final String DEFAULT_NAME = "World";

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = DEFAULT_NAME) String name) {
        return formatGreeting(name);
    }

    private String formatGreeting(String name) {
        return String.format("Hello %s!", name);
    }
}
