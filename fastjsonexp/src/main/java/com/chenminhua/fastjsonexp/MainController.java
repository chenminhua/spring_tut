package com.chenminhua.fastjsonexp;

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

    @GetMapping("/user")
    public User objectTest() {
        return new User("陈敏华", 28);
    }
}
