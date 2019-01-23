package com.chenminhua.mybatisexp;

import com.chenminhua.mybatisexp.dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.chenminhua.mybatisexp.dao")
@EnableScheduling
public class App {

    @Autowired private UserMapper userMapper;

    @Autowired private ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Scheduled(fixedRate = 300)
    public void insert() {
        userMapper.insert(new UserDO("陈敏华", 12));
    }

    @Scheduled(fixedRate = 300)
    public void select() {
        List<UserDO> users = userMapper.listUser();
        for (UserDO user : users) {
            System.out.println(user);
        }
    }


//    public void printSqlSession() {
//        Map<String, SqlSession> map2 = appContext.getBeansOfType(SqlSession.class);
//        for (Map.Entry<String, SqlSession> entry: map2.entrySet()) {
//            System.out.println(entry.getKey());
//        }
//    }
}
