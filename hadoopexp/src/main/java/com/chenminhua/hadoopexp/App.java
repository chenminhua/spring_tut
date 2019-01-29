package com.chenminhua.hadoopexp;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired private FileSystem fileSystem;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args)  {
        try {
            fileSystem.mkdirs(new Path("/springhdfs/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
