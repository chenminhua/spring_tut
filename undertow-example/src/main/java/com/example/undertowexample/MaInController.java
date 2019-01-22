package com.example.undertowexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class MaInController {

    @GetMapping("/hello")
    public String hello() {
        return "hello undertow\n";
    }
}
