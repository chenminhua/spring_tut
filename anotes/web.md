Register a new servlet

```$java
@Bean
public HelloWorldServlet helloWorld() {
    return new HelloWorldServlet();
}

@Bean
public SpringHelloServletRegistrationBean servletRegistrationBean() {
  
    SpringHelloServletRegistrationBean bean = new SpringHelloServletRegistrationBean(
      new SpringHelloWorldServlet(), "/springHelloWorld/*");
    bean.setLoadOnStartup(1);
    bean.addInitParameter("message", "SpringHelloWorldServlet special message");
    return bean;
}

```

### 将 tomcat 替换为 undertow


### ConfigurationProperties


### 使用自制的properties文件
[教程](https://www.baeldung.com/properties-with-spring)

### SpringBootServletInitializer
可以用 SpringBootServletInitializer 来实现从war包运行spring boot应用。

![教程](https://www.baeldung.com/spring-boot-servlet-initializer)

### 命令行参数
![教程](https://www.baeldung.com/spring-boot-command-line-arguments)

