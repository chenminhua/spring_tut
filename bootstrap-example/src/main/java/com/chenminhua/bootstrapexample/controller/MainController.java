package com.chenminhua.bootstrapexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world\n";
    }
}
