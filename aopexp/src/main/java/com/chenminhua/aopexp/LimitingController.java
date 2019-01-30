package com.chenminhua.aopexp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limit")
public class LimitingController {

    @GetMapping("/hello")
    @LimitingAnnotation()
    public String hello() {
        return "hello world";
    }
}
