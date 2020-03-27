package com.chenminhua.aopexp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.TreeMap;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // 升序排列
        TreeMap<String, String> map = new TreeMap<>((s1, s2) -> s1.compareTo(s2));

        map.put("python", ".py");
        map.put("c++", ".cpp");
        map.put("kotlin", ".kt");
        map.put("golang", ".go");
        map.put("java", ".java");

        // Printing the TreeMap (The keys will be sorted based on the supplied comparator)
        System.out.println(map);

        SpringApplication.run(App.class);
    }
}
