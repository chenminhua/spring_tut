package com.chenminhua.jpadataexp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int age;

    User() {}

    User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

}
