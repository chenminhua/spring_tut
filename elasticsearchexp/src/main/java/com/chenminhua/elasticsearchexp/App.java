package com.chenminhua.elasticsearchexp;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired private ElasticsearchOperations es;

    @Autowired private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args) throws Exception {
        pringElasticSearchInfo();

//        userService.save(new User("1001", "chen minhua", "shanghai"));
//        userService.save(new User("1002", "chen shaogang", "shanghai"));
//        userService.save(new User("1003", "wu xiaogang", "shanghai"));
//        userService.save(new User("1004", "wu shaohan", "nanjing"));


        System.out.println("--------name with chen----------");
        List<User> users = userService.findByName("chen");
        users.forEach(System.out::println);

        System.out.println("--------user id 1004----------");
        User csg = userService.findById("1004");
        System.out.println(csg);

        System.out.println("--------user in shanghai----------");
        Page<User> shanghaiUsers = userService.findByCity("shanghai", new PageRequest(0, 10));
        shanghaiUsers.forEach(System.out::println);

    }

    private void pringElasticSearchInfo() {
        System.out.println("--Elasticsearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("<--ElasticSearch--");
    }
}
