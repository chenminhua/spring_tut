package com.chenminhua.mybatisexp;

public class UserDO {
    private int id;
    private String name;
    private int age;


    public UserDO(int id, int age, String name) {
        this(name, age);
        this.id = id;
    }

    public UserDO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}
