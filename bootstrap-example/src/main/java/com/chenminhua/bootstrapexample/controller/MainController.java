package com.chenminhua.bootstrapexample.controller;

import com.chenminhua.bootstrapexample.exception.FooException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class MainController {

    @Value("${args:world}")
    private String args;

    @GetMapping("/hello")
    public String hello() {
        return "hello world\n";
    }

    @GetMapping("/foo")
    public String fooRuntimeException() {
        throw new FooException();
    }

    @GetMapping("/args")
    public String argsTest() {
        return "hello " + args;
    }
}
