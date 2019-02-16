package com.chenminhua.neo4jdemo.controller;

import com.chenminhua.neo4jdemo.model.User;
import com.chenminhua.neo4jdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/rest/neo4j")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Collection<User> getAll() {
        return userService.getAll();
    }

}
