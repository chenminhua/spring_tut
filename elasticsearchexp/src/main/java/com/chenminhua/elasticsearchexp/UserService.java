package com.chenminhua.elasticsearchexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;

    User save(User user) {
        return userRepo.save(user);
    }

    void delete(User user) {
        userRepo.delete(user);
    }

    User findById(String id) {
        Optional<User> user = userRepo.findById(id);
        return user.get();
    }

    Iterable<User> findAll() {
        return userRepo.findAll();
    }

    Page<User> findByCity(String city, PageRequest pageRequest) {
        return userRepo.findByCity(city, pageRequest);
    }

    List<User> findByName(String name) {
        return userRepo.findByName(name);
    }
}
