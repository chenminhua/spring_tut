package com.chenminhua.hadoopexp;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Configuration
public class FileSystemConfig {

    @Bean
    FileSystem getFileSystem() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        return (FileSystem) context.getBean("fileSystem");
    }
}
