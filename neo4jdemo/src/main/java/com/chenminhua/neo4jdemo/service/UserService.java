package com.chenminhua.neo4jdemo.service;

import com.chenminhua.neo4jdemo.model.User;
import com.chenminhua.neo4jdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Collection<User> getAll() {
        return userRepository.getAllUsers();
    }
}
